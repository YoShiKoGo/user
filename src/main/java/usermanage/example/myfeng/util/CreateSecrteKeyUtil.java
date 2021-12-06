package usermanage.example.myfeng.util;

import com.travelsky.component.encrypt.common.RSAConstant;
import com.travelsky.component.encrypt.common.exeption.EncryptComponentException;
import com.travelsky.component.encrypt.rsa.KeyGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
@Slf4j
public class CreateSecrteKeyUtil {
    public class Keys {

    }
    // 初始化公钥私钥对
    private static Map<String,Key> map;

    static {
        try {
            map = KeyGeneratorUtils.initKey(RSAConstant.KEY1024);
        } catch (EncryptComponentException e) {
            log.error("生成RSA公私秘钥失败,错误信息:",e);
        }
    }

    //获得公钥
    public static String getPublicKey() throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) map.get("publicKey");
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey() throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) map.get("privateKey");
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }


    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }


    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
    public static void main(String[] args) {
        try {
            String publicKey = getPublicKey();
            String psw = encrypt("hhhhh",publicKey);
            String psw2 = decrypt(psw,getPrivateKey());
            System.out.println(psw+"\n");

            String privateKey = getPrivateKey();
            System.out.println(psw2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
