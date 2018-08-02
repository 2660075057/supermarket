package com.grape.supermarket.common.util;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 字符串验证工具类
 * @author LXP
 * @version 创建时间：2017年2月24日 下午5:30:22
 */
public final class StringUtils {
    private final static String randomCodes  = "123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    private StringUtils(){}
	/**
	 * 字符串是否为空
	 * @param str
	 * @return null或者length==0返回true，其他返回false
	 */
	public static boolean isEmpty(String str){
		if(str == null){
			return true;
		}
		return str.isEmpty();
	}
	
	/**
	 * 是否为15或者18位身份证号
	 * @param str
	 * @return 不为null且符合身份证格式返回true，否则返回false
	 */
	public static boolean isIdNumber(String str){
		if(str == null){
			return false;
		}
		if(str.length() == 18){
			return str.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");
		}
		if(str.length() == 15){
			return str.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		}
		return false;
	}
	
	/**
	 * 是否为手机号
	 * @param str 不为null且符合手机号格式返回true，否则返回false
	 * @return
	 */
	public static boolean isMobile(String str){
		if(str == null){
			return false;
		}
		return str.matches("^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}$");
	}
	
	/**
	 * 是否为邮箱
	 * @param str 不为null且符合邮箱格式返回true，否则返回false
	 * @return
	 */
	public static boolean isEmail(String str){
		if(str == null){
			return false;
		}
		return str.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
	}
	
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}
	
	/**
	 * 判断两个对象是否相等
	 * @param o1
	 * @param o2
	 * @return 若o1==null && o2==null return true;
	 * 若o1!=null && o2 != null && o1.equal(o2) return true;
	 * other return false;
	 */
	public static boolean isEqual(Object o1,Object o2){
		if(o1==null && o2==null){
			return true;
		}
		if(o1!=null && o2!=null&&o1.equals(o2)){
			return true;
		}
		return false;
	}
	
	/**
	 * 创建一个验证码
	 * @param length 验证码长度
	 * @return 验证码
	 */
	public static String createVcode(int length){
		if(length < 1){
			throw new IllegalArgumentException("length must>0");
		}
		String codes  = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
		Random random = new Random();
		char[] vcode = new char[length];
		for(int i = 0;i<length;++i){
			vcode[i] = codes.charAt(random.nextInt(codes.length()));
		}
		return new String(vcode);
	}

    /**
     * 创建一个验证码
     * @param length 验证码长度
     * @return 验证码
     */
    public static String createNumVcode(int length){
        if(length < 1){
            throw new IllegalArgumentException("length must>0");
        }
        String codes  = "1234567890";
        Random random = new Random();
        char[] vcode = new char[length];
        for(int i = 0;i<length;++i){
            vcode[i] = codes.charAt(random.nextInt(codes.length()));
        }
        return new String(vcode);
    }

	/**
	 * 根据splitStr拆分字符串
	 * @param s
	 * @param splitStr
	 * @return 拆分后的数组
	 */
	public static String[] split(String s,String splitStr){
		if(s == null || splitStr == null){
			new IllegalArgumentException("s and splitStr must not null!");
		}
		int startIndex = 0;
		int endIndex = s.indexOf(splitStr);
		if(endIndex == -1){
			return new String[]{s};
		}
		List<String> ss = new ArrayList<>();
		ss.add(s.substring(startIndex, endIndex));
		
		startIndex=endIndex+splitStr.length();
		while((endIndex = s.indexOf(splitStr,startIndex)) != -1){
			ss.add(s.substring(startIndex, endIndex));
			startIndex=endIndex+splitStr.length();
		}
		if(startIndex != s.length()){
			ss.add(s.substring(startIndex));
		}else{
			ss.add("");
		}
		return ss.toArray(new String[ss.size()]);
	}
	
	/**
    *
    * @param plainText
    * @param model true为32位 false为16为md5
    * @param isLow 是否为小写串
    * @return
    */
   public static String getMd5(String plainText,boolean model/*true为32位 false为16为md5*/,boolean isLow/*是否为小写串*/) {
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }
           if(model){// 32位的加密
               return isLow?buf.toString().toLowerCase():buf.toString().toUpperCase();
           }else{// 16位的加密
               return isLow?buf.toString().substring(8, 24).toLowerCase():buf.toString().substring(8, 24).toUpperCase();
           }

       } catch (Exception e) {
           return null;
       }
   }

    /**
     * 生成随机字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomStr(int length) {
        assert length > 0:"StringUtils.randomStr length must > 0";
        Random r = new Random(System.currentTimeMillis() / 2 + 031711);
        int codesLength = randomCodes.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomCodes.charAt(r.nextInt(codesLength)));
        }
        return sb.toString();
    }
}
