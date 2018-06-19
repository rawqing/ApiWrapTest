package yq.test.handler.UtilsJ;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class SignUtil {
	private static final Logger log = LoggerFactory.getLogger("SignUtil");
	public static Map<String, String> params = new TreeMap<String, String>();
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


	/**
	 * 签名参数
	 * @param param 参数map
	 * @param addToParams 是否添加到参数列表
	 * @param key 添加到列表时指定的key
	 * @return 加入签名字符串的参数
	 */
	public static Map<String,String> sign(TreeMap<String,String> param, boolean addToParams,  String... key) {
		String sortstr=SignUtil.getParamString(param, "utf-8");
		String signed = SignUtil.sign(sortstr);
		if (addToParams) {
			String name = null;
			if (key.length >= 1) name = key[0];
			else name = "sign";

			param.put(name, signed);
		}
		return param;
	}
	public static Map<String,String> sign(Map<String,String> param){
		TreeMap<String, String> treeMap = new TreeMap<>(param);
		return sign(treeMap, true);
	}


	public static String sign(String toSign) {
		String app_sign="";
		try {
			app_sign = md5(toSign + "V5rRI65jvGBFNG23aZHZGxOUQnmIEPyU");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return app_sign;
	}
	/**
	 * MD5运算
	 * @param s 传入明文
	 * @return String 返回密文 小写
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			String stored = toHexString(messageDigest);
			return stored;
		} catch (NoSuchAlgorithmException e) {
			log.error("加密字符串'{}'失败.\\n",s,e);
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 返回大写的MD5密文
	 * @param s 明文
	 * @return 大写的MD5密文
	 */
	public static String MD5(String s){
		String stored = md5(s).toUpperCase();
		log.debug("成功加密字符串'{}' -> '{}'" ,s ,stored);
		return stored;
	}
	/**
	 * MD5
	 * @param b byte数组
	 * @return String byte数组处理后字符串
	 */
	public static String toHexString(byte[] b) {// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static String getParamString(Map<String, String> params,String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            if (encodedParams.length() != 0) {
                encodedParams.deleteCharAt(encodedParams.length() - 1);
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
	public static TreeMap<String,String> getListToMap(List<NameValuePair> paramList){
		TreeMap<String,String> map=new TreeMap<>();
		for(int i=0;i<paramList.size();i++){
			map.put(paramList.get(i).getName(),paramList.get(i).getValue() );
		}
		return map;
		
	}

	/**
	 * 获取手机验证码
	 * @param phone
	 * @return
	 */
	 public static String getSmsCode(String phone){
		//从数据库中获取验证码信息
        DBUtil db=new DBUtil();
        PreparedStatement ps =null;
        Connection conn=db.getCon();
        String code="";
        try {
            ps = conn.prepareStatement("select code from t_sent_message where phone=?");
            ps.setString(1, phone);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                 code=rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
		 
	 }
	/**
	 * 查询验证码更新时间
	 * @return 时间格式为：2018-04-17 15:39:53
	 */
    public static String getLastSmsGetTime(String phone){
        String time= null;
        DBUtil db=new DBUtil();
        PreparedStatement ps =null;
        Connection conn=db.getCon();
        try {
            ps = conn.prepareStatement("select UPDATE_DATE from t_sent_message where phone=?");
            ps.setString(1, phone);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                time=rs.getString("UPDATE_DATE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  time ;
    }

}
