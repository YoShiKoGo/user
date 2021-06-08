/**
 * 基础工具包
 */
package usermanage.example.myfeng.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import usermanage.example.myfeng.common.exception.ManageException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 	基础工具包类
 */
public class BaseUtil {


	/** 空的字符串 **/
	public static final String EMPTY_STRING = "";

	/** null字符串 **/
    public static final String NULL_STRING = "null";

    /** 连接字符串  **/
    public static final String CONNECT_STRING = "_";

	/**
	 * 	判断对象是否为空
	 *  @Description    : 方法描述
	 *  @Method_Name    : objectNotNull
	 *  @param object Object
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-5-12 上午9:49:17
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean objectNotNull(Object object) {
		if (null == object) {
			return false;
		}
		return true;
	}

	/**
	 * 	判断字符串是否为空
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringNotNull
	 *  @param string String
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-5-12 上午9:53:33
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean stringNotNull(String string) {
		if ((null == string) || (string.trim().equals(""))) {
			return false;
		}
		return true;
	}


	/**
	 * 	判断list集合是否为空
	 *  @Description    : 方法描述
	 *  @Method_Name    : listNotNull
	 *  @param list List
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-5-12 上午9:54:34
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean listNotNull(List<?> list) {
		if (list != null && list.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 	判断 map是否为空
	 *  @Description    : 方法描述
	 *  @Method_Name    : mapNotNull
	 *  @param map Map
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-5-12 上午10:54:36
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean mapNotNull(Map<?, ?> map) {
		if (map != null && !map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 	string 转 int 类型
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToInteger
	 *  @param string String
	 *  @return
	 *  @throws Exception
	 *  @return         : Integer
	 *  @date  : 2016-5-12 上午11:03:25
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static Integer stringToInteger(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 	string 转 long 类型
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToLong
	 *  @param string String
	 *  @return
	 *  @throws Exception
	 *  @return         : Long
	 *  @date  : 2016-5-12 上午11:05:08
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static Long stringToLong(String string) throws ManageException {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return Long.valueOf(string);

		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 	string 转 short类型
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToShort
	 *  @param string String
	 *  @return
	 *  @throws Exception
	 *  @return         : Short
	 *  @date  : 2016-5-12 上午11:06:31
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static Short stringToShort(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return Short.valueOf(string);

		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 	string 转 bigDecimal类型
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToBigDecimal
	 *  @param string String
	 *  @return
	 *  @throws Exception
	 *  @return         : BigDecimal
	 *  @date  : 2016-5-12 上午11:07:37
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static BigDecimal stringToBigDecimal(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new BigDecimal(string);

		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 	int 转string
	 *  @Description    : 方法描述
	 *  @Method_Name    : integerToString
	 *  @param integer Integer
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-12 上午11:08:47
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String integerToString(Integer integer) {

		if (!objectNotNull(integer)) {

			return null;
		}

		return integer.toString();

	}

	/**
	 * 	long 转 string
	 *  @Description    : 方法描述
	 *  @Method_Name    : longToString
	 *  @param lon Long
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-12 上午11:31:32
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String longToString(Long lon) {

		if (!objectNotNull(lon)) {

			return null;
		}

		return lon.toString();

	}

	/**
	 * 	short 转string
	 *  @Description    : 方法描述
	 *  @Method_Name    : shortToString
	 *  @param shortValue Short
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-12 上午11:36:07
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String shortToString(Short shortValue) {

		if (!objectNotNull(shortValue)) {

			return null;
		}

		return shortValue.toString();

	}

	/**
	 * 	bigdecimal转string
	 *  @Description    : 方法描述
	 *  @Method_Name    : bigDecimalToString
	 *  @param bigDecimal BigDecimal
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-12 上午11:36:18
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String bigDecimalToString(BigDecimal bigDecimal) {

		if (!objectNotNull(bigDecimal)) {

			return null;
		}

		return bigDecimal.toString();
	}

	/**
	 * 	取值判断
	 *  @Description    : 方法描述
	 *  @Method_Name    : isNull
	 *  @param string1 String
	 *  @param string2 String
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-12 上午11:43:39
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String isNull(String string1, String string2) {

		if(stringNotNull(string1)){
			return string1;
		}else{
			return string2;
		}
	}

	/**
	 * 	float 转 string
	 *  @Description    : 方法描述
	 *  @Method_Name    : floatToString
	 *  @param float1 Float
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-17 下午3:53:07
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String floatToString(Float float1) {

		if (BaseUtil.objectNotNull(float1)) {

			return float1.toString();
		}

		return null;
	}

	/**
	 *  string 转 float
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToFloat
	 *  @param string String
	 *  @return
	 *  @throws Exception
	 *  @return         : Float
	 *  @date  : 2016-5-17 下午4:01:47
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static Float stringToFloat(String string){

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new Float(string);

		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 	判断set是否为空
	 *  @Description    : 方法描述
	 *  @Method_Name    : setNotNull
	 *  @param set Set
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-5-17 下午4:22:44
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean setNotNull(Set<?> set) {

		if (set != null && set.size() > 0) {

			return true;
		}

		return false;
	}

	/**
	 * 	字符串 转大写
	 *  @Description    : 方法描述
	 *  @Method_Name    : stringToUpperCase
	 *  @param string String
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-5-17 下午4:26:02
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String stringToUpperCase(String string) {

		if ((string != null) && (!string.trim().equals(""))) {

			return string.toUpperCase();
		} else {

			return string;
		}
	}

	/**
	 * 	获取IP地址
	 *  @Description    : 方法描述
	 *  @Method_Name    : getIpAddress
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-8-10 上午11:26:47
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String getIpAddress() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			if (BaseUtil.objectNotNull(address) && BaseUtil.stringNotNull(address.getHostAddress())) {
				return address.getHostAddress();
			}
		} catch (UnknownHostException e) {

			return null;
		}

		return null;
	}

	/**
	 * 	比较两个Integer对象的int值是否相等
	 *  @Description    : 方法描述
	 *  @Method_Name    : integerEquals
	 *  @param integer1 Integer
	 *  @param integer2 Integer
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-8-10 上午11:27:08
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean integerEquals(Integer integer1, Integer integer2) {
		if (integer1 != null && integer2 != null) {
			return integer1.intValue() == integer2;
		} else {
			return false;
		}
	}

	/**
	 * 	按照指定标记分割字符串
	 *  @Description    : 方法描述
	 *  @Method_Name    : splitString
	 *  @param data 需要分割的字符串
	 *  @param flag 分割标记
	 *  @return
	 *  @return         : List<String> 分割后的list
	 *  @date  : 2016-8-10 上午11:27:37
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static List<String> splitString(String data, String flag) {
		List<String> list = null;
		if (!BaseUtil.stringNotNull(data) || !BaseUtil.stringNotNull(flag)) {
			list = new ArrayList<String>();
			return list;
		}
		list = new ArrayList<String>();
		String[] arr = data.split(flag);
		for (int i = 0; i < arr.length; i++) {
			if (BaseUtil.stringNotNull(arr[i])) {
				list.add(arr[i]);
			}
		}

		return list;
	}

	/**
	 * 	数据四舍五入
	 *  @Description    : 方法描述
	 *  @Method_Name    : doubleToRemain
	 *  @param data 原始数据
	 *  @param remain 保留小数位数
	 *  @return
	 *  @return         : Double
	 *  @date  : 2016-8-10 上午11:28:14
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static Double doubleToRemain(Double data, int remain) {
		BigDecimal bg = new BigDecimal(data);
		double result = bg.setScale(remain, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return result;
	}

	/**
	 * 	判断一个字符串是否为字母
	 *  @Description    : 方法描述
	 *  @Method_Name    : isLetter
	 *  @param letter String
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2016-8-10 上午11:58:36
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean isLetter(String letter) {
		String reg = "[a-zA-Z]";
		return letter.matches(reg);

	}






	/**
	 *
	 * @Description        : 把对象转换成json
	 * @Method_Name        : toJSON
	 * @param object T
	 * @return
	 * @Creation Date      : 2014年7月28日 上午9:21:04
	 * @version            : v1.00
	 * @Author             : Gine
	 * @Update Date        :
	 * @Update Author      : Gine
	 * @Update Description :
	 */
	public static <T> String toJSON(T object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 	json字符串转成Java对象
	 *  @Description    : 方法描述
	 *  @Method_Name    : toJAVA
	 *  @param string String
	 *  @param clz Class
	 *  @return
	 *  @return         : T
	 *  @date  : 2016-8-10 下午12:01:00
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static <T> T toJAVA(String string, Class<T> clz) {
		return JSON.parseObject(string, clz);
	}

	/**
	 * 将一个借口返回的对象直接转为另一个对象
	 * 主要用于commonObject对象中resObj的转换
	 * @param t
	 * @return
	 */
	public static <T> T objToJAVA(Object obj,Class<T> clazz) {
		if(!objectNotNull(obj)){
			return null;
		}
		return JSON.parseObject(toJSON(obj), clazz);
	}

	/**
	 *  字符串转java集合
	 *  @Description    : 方法描述
	 *  @Method_Name    : toList
	 *  @param string
	 *  @param clz
	 *  @return         : List<T>
	 *  @date  : 2017年6月23日 上午10:53:02
	 *  @version        : v1.00
	 *  @author         : WuXiaoLin
	 *  @Update Date    :
	 *  @Update Author  : WuXiaoLin
	 */
	public static <T> List<T> toList(String string, Class<T> clz) {
		return JSONArray.parseArray(string,clz);
	}

	/**
	 *  对象转java集合
	 *  @Description    : 方法描述
	 *  @Method_Name    : toList
	 *  @param string
	 *  @param clz
	 *  @return         : List<T>
	 *  @date  : 2017年6月23日 上午10:53:02
	 *  @version        : v1.00
	 *  @author         : WuXiaoLin
	 *  @Update Date    :
	 *  @Update Author  : WuXiaoLin
	 */
	public static <T> List<T> objToList(Object obj, Class<T> clz) {
		return JSONArray.parseArray(toJSON(obj),clz);
	}





	/**
	 *
	 * @Description        : 是否是ajax请求
	 * @Method_Name        : isAjaxRequest
	 * @param request HttpServletRequest
	 * @return
	 * @Creation Date      : 2014年7月25日 下午3:33:57
	 * @version            : v1.00
	 * @Author             : Gine
	 * @Update Date        :
	 * @Update Author      : Gine
	 * @Update Description :
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
	    boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
	    return isAjax;
	}

	/**
	 *
	 *  @Description    : 判断 字符串 是否是数字
	 *  @Method_Name    : isNumeric
	 *  @param str String
	 *  @return
	 *  @return         : boolean
	 *  @Creation Date  : 2014-12-24 下午1:42:53
	 *  @version        : v3.00
	 *  @Author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static boolean isNumeric(String str){
	   Pattern pattern = Pattern.compile("[0-9]*");
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false;
	   }
	   return true;
	}

	/**
	 * 	获取访问请求的IP地址
	 *  @Description    : 方法描述
	 *  @Method_Name    : getIpAddr
	 *  @param request HttpServletRequest
	 *  @return
	 *  @return         : String
	 *  @date  : 2016-8-2 上午9:09:54
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

	/**
	 * 	获取系统当前时间戳
	 *  @Description    : 方法描述
	 *  @Method_Name    : getTimeStamp
	 *  @return
	 *  @return         : String
	 *  @date  : 2017-2-7 下午1:58:53
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 	产生一个随机数，用于订单号等场景
	 *  @Description    : 方法描述
	 *  @Method_Name    : getRandomNo
	 *  @return
	 *  @return         : String
	 *  @date  : 2017年4月8日 上午11:59:02
	 *  @version        : v1.00
	 *  @author         : LiuHuan

	 *  @Update Date    :
	 *  @Update Author  : LiuHuan
	 */
    public static String getRandomNo(){
        int rado=(int)(Math.random()*(10));//产生2个0-9的随机数
        int radt=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String orderId =String.valueOf(rado)+String.valueOf(radt)+String.valueOf(now);// 订单ID
        return orderId;
    }


    /**
     *  @Description    : 方法描述
     *  @Method_Name    : isEmpty
     *  @param str
     *  @return
     *  @return         : boolean
     *  @date  : 2017年6月8日 下午4:19:04
     *  @version        : v1.00
     *  @author         : cxhu
     *  @Update Date    :
     *  @Update Author  : cxhu
     */
    public static boolean isEmpty(String str)
    {
        if (null == str || NULL_STRING.equalsIgnoreCase(str) || EMPTY_STRING.equals(str)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *  @Description    : 方法描述
     *  @Method_Name    : isNotEmpty
     *  @param str
     *  @return
     *  @return         : boolean
     *  @date  : 2017年6月8日 下午4:58:53
     *  @version        : v1.00
     *  @author         : cxhu
     *  @Update Date    :
     *  @Update Author  : cxhu
     */
    public static boolean isNotEmpty(String str)
    {
        if (isEmpty(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *  @Description    : 方法描述
     *  @Method_Name    : isIncludeEmpty
     *  @param strs
     *  @return
     *  @return         : boolean
     *  @date  : 2017年6月8日 下午4:19:09
     *  @version        : v1.00
     *  @author         : cxhu
     *  @Update Date    :
     *  @Update Author  : cxhu
     */
    public static boolean isIncludeEmpty(String... strs) {
        if (null == strs) {
            return true;
        }
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

   /**
    *  裁剪字符
    *  如有strToTrim: <br/>909-777<br/>,symble:<br/>
    *  	 trimHead:true->909-777 ; false-><br/>909-777
    *  @Description    : 方法描述
    *  @Method_Name    : trim
    *  @param strToTrim 被修剪的字符
    *  @param symble 要裁剪的字符
    *  @param trimHead 是否裁剪字符串头部出现的指定字符
    *  @return
    *  @return         : String
    *  @date  : 2017年7月5日 上午10:57:29
    *  @version        : v1.00
    *  @author         : WuXiaoLin
    *  @Update Date    :
    *  @Update Author  : WuXiaoLin
    */
    public static String trim(String strToTrim, String symble , boolean trimHead){

    	if(!BaseUtil.stringNotNull(strToTrim)){
    		return "";
    	}

    	if(strToTrim.equals(symble)){
    		return "";
    	}

    	String trimedStr = strToTrim;
    	int symbleLen = symble.length();

    	//去除头部的字符
    	if(trimHead){
    		if(strToTrim.startsWith(symble)){
    			trimedStr = trimedStr.substring(symbleLen, trimedStr.length());
    		}
    	}

    	//去除尾部的字符
    	if(strToTrim.endsWith(symble)){
    		trimedStr = trimedStr.substring(0, (strToTrim.length()-symbleLen));
    	}

    	return trimedStr;
    }


    /**
     *  @Description    : 校验传入的字符串是否满足某格式
     *  @Method_Name    : phoneReg
     *  @param string
     *  @return
     *  @return         : boolean
     *  @date  : 2017年6月19日 下午3:25:34
     *  @version        : v1.00
     *  @author         : cxhu
     *  @Update Date    :
     *  @Update Author  : cxhu
     */
    public static boolean strReg(String string,String regStr)
    {

        String STR_REG =regStr;
        boolean tem=false;
        Pattern pattern = Pattern.compile(STR_REG);
        Matcher matcher=pattern.matcher(string);
        tem=matcher.matches();
        return tem;
    }



    /**
     *  @Description    : 判断某字符串是否包含在数组中
     *  @Method_Name    : contains
     *  @param org
     *  @param dsts
     *  @return
     *  @return         : boolean
     *  @date  : 2017年7月5日 下午2:16:34
     *  @version        : v1.00
     *  @author         : cxhu
     *  @Update Date    :
     *  @Update Author  : cxhu
     */
    public static boolean contains(String org, String... dsts)
    {
        if(null == dsts || isEmpty(org))
        {
            return false;
        }

        for(String str : dsts)
        {
            if(org.equals(str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 十以内数字转汉字，i=0时，返回‘一’，以此类推，9的时候返回‘零’，
     *  @Description    : 方法描述
     *  @Method_Name    : numToCharacter
     *  @param i
     *  @return
     *  @return         : String
     *  @date  : 2017年9月28日 下午5:32:02
     *  @version        : v1.00
     *  @author         : WuXiaoLin

     *  @Update Date    :
     *  @Update Author  : WuXiaoLin
     */
	public static String numToCharacter(int i) {
		// TODO Auto-generated method stub
		String[] numCharacter = new String[]{"一","二","三","四","五","六","七","八","九","零"};
		return numCharacter[i];
	}

	/**
	 *  以字典顺序比较两个字符串的大小 true 表示 firstStr>=anotherStr false 表示小于
	 *  @Description    : 方法描述
	 *  @Method_Name    : isGreaterThan
	 *  @param firstStr
	 *  @param anotherStr
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2018年1月24日 下午2:08:53
	 *  @version        : v1.00
	 *  @author         : WuXiaoLin
	 *  @Update Date    :
	 *  @Update Author  : WuXiaoLin
	 */
	public static boolean isGteThan(String firstStr,String anotherStr){
		return firstStr.compareTo(anotherStr)>=0;
	}

	/**
	 *  以字典顺序比较两个字符串的大小 true 表示 firstStr>anotherStr false 表示小于等于
	 *  @Description    : 方法描述
	 *  @Method_Name    : isGreaterThan
	 *  @param firstStr
	 *  @param anotherStr
	 *  @return
	 *  @return         : boolean
	 *  @date  : 2018年1月24日 下午2:08:53
	 *  @version        : v1.00
	 *  @author         : WuXiaoLin
	 *  @Update Date    :
	 *  @Update Author  : WuXiaoLin
	 */
	public static boolean isGreaterThan(String firstStr,String anotherStr){
		return firstStr.compareTo(anotherStr)>0;
	}


	/**
	 *
	 *  @Description    : String转boolean
	 *  @Method_Name    : valueOf
	 *  @param s
	 *  @return
	 *  @return         : Boolean
	 *  @date  : 2018年7月11日 下午12:06:21
	 *  @version        : v1.00
	 *  @author         : huaxinzhou

	 *  @Update Date    :
	 *  @Update Author  : huaxinzhou
	 */
	public static Boolean StringToBoolean(String str) {
	    return (str!= null) && str.equalsIgnoreCase("true");
	}
}
