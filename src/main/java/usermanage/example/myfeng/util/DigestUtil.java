package usermanage.example.myfeng.util;



import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static io.netty.util.internal.StringUtil.byteToHexString;
@Slf4j
public class DigestUtil {

    private final static char[] C ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static MessageDigest md5 = null;

    public final static String KEY_AES = "AES";
    private final static String  defaultCharset = "UTF-8";

    /**
     *  md5加密
     * @param bytes
     * @return
     */
    public  static  String  md5(byte[] bytes){
        //用于储存加密后的字符串
        StringBuffer sb = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            byte[] digest =  md5.digest(bytes);
            //处理为16进制的字符串
            for(byte bb:digest) {
                //15的8进制是0000,1111
                sb.append(C[(bb >> 4) & 15]);
                sb.append(C[bb & 15]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     *  @Description    :只加密密码方法
     *  @Method_Name    : encryptPassword
     *  @param password  用户输入的密码
     */
    public static String encryptPassword(String password)
    {
        String resultString = password;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException ex)
        {
            resultString = "";
            log.error("NoSuchAlgorithmException异常。{}", ex);
        } catch (UnsupportedEncodingException e)
        {
            resultString = "";
            log.error("UnsupportedEncodingException异常。{}", e);
        }
        return resultString;
    }

    /**
     *  @Description    : 转换字节数组为16进制字串
     *  @Method_Name    : byteArrayToHexString
     */
    private static String byteArrayToHexString(byte[] b)
    {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++)
        {
            // 若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    public static void main(String[] args) {
        System.out.println(md5("yH8@aL0z".getBytes()));
    }



    /**
     * @description 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * @description 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}
