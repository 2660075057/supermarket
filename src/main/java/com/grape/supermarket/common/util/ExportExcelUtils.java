package com.grape.supermarket.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出excel工具类
 * 
 * @author zengtp 2014-6-11
 */
public class ExportExcelUtils {
	/**
	 * 工具类
	 * 
	 * @param title
	 *            标题
	 * @param headers
	 *            头文件必须是map集合
	 * @param dataset
	 *            内容 可以是bean也可以是map，如果是bean,bean字段必须和headers里面的键相匹配。
	 *            如果是map则key必须和headers里面的键相匹配
	 * @param out
	 *            输出流
	 */
	@SuppressWarnings("unchecked")  
	public static <T> void exportExcel(String title, Map<String, String> headers,

	List<T> dataset, OutputStream out) {
		OutputStream fileOut = null;
		FileInputStream fileInputStream = null;
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);

			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 15);

			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			HSSFRow row = sheet.createRow(0);
			// 产生表格标题行
			int j = 0;
			for (Entry<String, String> header : headers.entrySet()) {
				HSSFCell cell = row.createCell(j++);
				HSSFRichTextString text = new HSSFRichTextString(header.getValue());
				cell.setCellValue(text);
			}

			// 遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			int index = 0;
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				if (t instanceof Map) {
					// 如果泛型为map
					Map map = (Map) t;
					int i = 0;
					for (Entry<String, String> header : headers.entrySet()) {
						HSSFCell cell = row.createCell(i++);
						Object value = map.get(header.getKey());
						judgeType(cell, value);
					}
				} else {
					// 利用反射设置值
					Class clazz = t.getClass();
					int i = 0;
					for (Entry<String, String> header : headers.entrySet()) {
						HSSFCell cell = row.createCell(i++);
						Field field = clazz.getDeclaredField(header.getKey());
						field.setAccessible(true);
						Object value = field.get(t);
						judgeType(cell, value);
					}
				}
			}

			// 导出excel
			workbook.write(out);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }
	
	public String[] readExcelTitle(InputStream is) {
		POIFSFileSystem fs =null;
		HSSFWorkbook wb  =null;
		HSSFSheet sheet =null;
		HSSFRow row =null;
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getStringCellValue(row.getCell((short) i));
        }
        return title;
    }
	
	/**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public static Map<Integer, String> readExcelContent(InputStream is) {
    	POIFSFileSystem fs =null;
		HSSFWorkbook wb  =null;
		HSSFSheet sheet =null;
		HSSFRow row =null;
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                str += getStringCellValue(row.getCell((short) j)).trim() + "|";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }
	/**
	 * 判断值的类型已进行相应的转换
	 * 
	 * @param cell
	 * @param value
	 */
	private static void judgeType(HSSFCell cell, Object value) {
		if (value == null) {
			return;
		}

		Pattern p = Pattern.compile("^\\d+?$");
		// 如果为string直接设置值
		if (value instanceof String) {
			// 如果匹配为数字也进行转换
			Matcher matcher = p.matcher(value.toString());
			if (matcher.matches()) {
				cell.setCellValue(Double.parseDouble(value.toString()));
			} else {
				cell.setCellValue(value.toString());
			}

		} else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
			cell.setCellValue(Double.parseDouble(value.toString()));
		} else {
			cell.setCellValue(value.toString());
		}
	}

}