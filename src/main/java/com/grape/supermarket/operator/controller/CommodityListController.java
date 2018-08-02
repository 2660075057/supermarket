package com.grape.supermarket.operator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.AbstractLogger;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.ImportResult;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.UploadFileInfo;
import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.PictureEntity;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.param.CommodityParam;
import com.grape.supermarket.operator.service.CommodityService;
import com.grape.supermarket.operator.service.ImportCallback;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LQW on 2017/11/2.
 */
@Controller
@RequestMapping("/operator/commodity")
public class CommodityListController extends AbstractLogger {

    @Autowired
    private CommodityService commodityService;

    @RequestMapping("/commodityList")
    @ResponseBody
    public List<CommodityPageEntity> commodityList(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<CommodityPageEntity> list = new ArrayList<>();
        try {
            CommodityParam param = JsonUtils.fromJson(req,CommodityParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  commodityService.commodityList(param,p);
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }

        return list;
    }
    @RequestMapping("/countCommodity")
    @ResponseBody
    public int countCommodity(HttpServletRequest request, HttpServletResponse response,CommodityParam param) {
        int count =  commodityService.countCommodity(param);
        return count;
    }

    @RequestMapping("/addCommodity")
    @ResponseBody
    public boolean addCommodity(String req, String attrIds,String imgData) {
        boolean flg = false;
        try {
            CommodityEntity param = JsonUtils.fromJson(req,CommodityEntity.class);
            List<CommodityAttrEntity> list = new ArrayList<>();
            JSONArray jarr = JSONArray.fromObject(attrIds);
            for(int i=0,z=jarr.size();i<z;i++){
                JSONObject json = jarr.getJSONObject(i);
                CommodityAttrEntity entity = JsonUtils.fromJson(json.toString(),CommodityAttrEntity.class);
                list.add(entity);
            }
            List<String> temp = JsonUtils.fromJson(imgData, new TypeReference<List<String>>() {
            });
            List<PictureEntity> imgList = new ArrayList<>();
            for (String s : temp) {
                PictureEntity e = new PictureEntity();
                e.setImgUrl(s);
                imgList.add(e);
            }
            flg =  commodityService.addCommodity(param,list,imgList);
        }catch (Exception e){
            return flg;
        }
        return flg;
    }

    @RequestMapping("/deleteCommodity")
    @ResponseBody
    public boolean deleteCommodity(Integer commId) {
        return commodityService.deleteCommodity(commId);
    }

    @RequestMapping("/updateCommodity")
    @ResponseBody
    public boolean updateCommodity(String req, String attrIds,String imgData) {
        boolean flg = false;
        try {
            CommodityEntity param = JsonUtils.fromJson(req,CommodityEntity.class);
            List<CommodityAttrEntity> list = new ArrayList<>();
            JSONArray jarr = JSONArray.fromObject(attrIds);
            for(int i=0,z=jarr.size();i<z;i++){
                JSONObject json = jarr.getJSONObject(i);
                CommodityAttrEntity entity = JsonUtils.fromJson(json.toString(),CommodityAttrEntity.class);
                list.add(entity);
            }
            List<String> temp = JsonUtils.fromJson(imgData, new TypeReference<List<String>>() {
            });
            List<PictureEntity> imgList = new ArrayList<>();
            for (String s : temp) {
                PictureEntity e = new PictureEntity();
                e.setImgUrl(s);
                imgList.add(e);
            }
            flg =  commodityService.updateCommodity(param,list,imgList);
        }catch (Exception e){
            return flg;
        }
        return flg;
    }

    @RequestMapping("/selectByPrimaryKey")
    @ResponseBody
    public CommodityPageEntity selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response,Integer commId) {
        return commodityService.selectByPrimaryKey(commId);
    }

    @RequestMapping("/importCommodity")
    @ResponseBody
    public ResultBean<String> importCommodity(HttpSession session) {
        ResultBean<String> rb = new ResultBean<>();

        UploadFileInfo fileInfo = (UploadFileInfo) session.getAttribute("importCommodity");
        if (fileInfo != null
                && (fileInfo.getFileType() == UploadFileInfo.FILE_TYPE.XLS
                || fileInfo.getFileType() == UploadFileInfo.FILE_TYPE.XLSX)) {
            String fileType = fileInfo.getFileType() == UploadFileInfo.FILE_TYPE.XLS ? "xls" : "xlsx";
            try {
                final List<ImportResult> result = new ArrayList<>(30);
                ImportCallback callback = new ImportCallback() {
                    @Override
                    public void handle(ImportResult importResult) {
                        result.add(importResult);
                    }
                };
                commodityService.importCommodityByExcel(fileType,fileInfo.getFile().getInputStream(),callback);

                //分析结果
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream(2 * 1024 * 1024);
                int fail = 0;
                for (ImportResult importResult : result) {
                    if(!importResult.isState()){
                        String failMessage = importResult.getFailMessage();
                        if(failMessage != null && !failMessage.isEmpty()) {
                            resultStream.write(failMessage.getBytes("UTF-8"));
                            resultStream.write('\r');
                            resultStream.write('\n');
                        }
                        fail++;
                    }
                }
                if (resultStream.size() == 0) {
                    rb.setData("success");
                } else {
                    //加入标题
                    String title = "处理总数：" + result.size() + " 成功数：" + (result.size() - fail) + "失败数:" + fail + "\r\n";
                    byte[] titleByte = title.getBytes("UTF-8");
                    byte[] bytes = resultStream.toByteArray();
                    byte[] r = Arrays.copyOf(titleByte, titleByte.length + bytes.length);
                    System.arraycopy(bytes, 0, r, titleByte.length, bytes.length);

                    session.setAttribute("import_fail_file", r);
                    rb.setData("has_fail");
                }
            } catch (IOException e) {
                rb.setData("read_error");//读取文件失败
                rb.setMessage("读取文件错误");
                e.printStackTrace();
            }
        } else {
            rb.setData("unknow_file");//未上传文件，或上传的文件类型错误
            rb.setMessage("文件错误");
        }
        return rb;
    }

    @RequestMapping("/importCommodityResult")
    public void importCommodityResult(HttpSession session, HttpServletResponse response) {
        byte[] failByte = (byte[]) session.getAttribute("import_fail_file");
        if (failByte == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String name = "结果文件";
        String fileName = encodingFileName(name);
        response.setContentType("application/x-download; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".txt");

        try (OutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            out.write(failByte);
        } catch (IOException e) {
            getLogger().info("importCommodityResult", e);
        }
    }

    //解决中文乱码问题
  	private static String encodingFileName(String fileName) {
          String returnFileName = "";
          try {
              returnFileName = URLEncoder.encode(fileName, "UTF-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
          return returnFileName;
      }
  	@RequestMapping("/selectType")
    @ResponseBody
  	public  List<CommodityTypeEntity> selectType() {
  		List<CommodityTypeEntity> list = commodityService.selectType();
  		if (list.size()>0) {
			return list;
		}
		return null;
		
	}
  	@RequestMapping("/ziselectType")
    @ResponseBody
  	public  List<CommodityTypeEntity> ziselectType(String typeid) {
  		List<CommodityTypeEntity> list = commodityService.ziselectType(typeid);
  		if (list.size()>0) {
			return list;
		}
		return null;
		
	}
}
