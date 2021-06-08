package usermanage.example.myfeng.util;/**
 * Created by wxl on 2019/8/13.
 *
 * @className
 * @author wxl
 * @return return
 */

import com.travelsky.component.encrypt.common.exeption.EncryptComponentException;
import com.travelsky.component.encrypt.sha.SHAUtils;

/**
 * 密码加密工具类
 * md5加密-->加入动态盐-->哈希算法加密
 * @author wxl
 * @create 2019-08-13 9:43
 **/
public class SHAUtil {

    private static final SHAHandler HANDLER = new SHAHandler() {};

    //默认使用的加密处理器
    public static final SHAHandler DEFAULT= new DefaultHandler();

    //盐 字符串长度
    public static final int saltLength = 15;
    /**
     * sha 加密处理
     * @param content 加密内容
     * @param pattern 加密方式
     * @return
     * @throws EncryptComponentException
     */
    public static String encrypt(String content, String pattern) throws EncryptComponentException {
        return doEncrypt(content,pattern,HANDLER);
    }

    /**
     * 接受一个处理器 定义盐的生成以及sha密码生成后的处理逻辑
     * @param content
     * @param pattern
     * @param handler
     * @return
     * @throws EncryptComponentException
     */
    public static String encrypt(String content, String pattern,SHAHandler handler) throws EncryptComponentException {
        SHAHandler shaHandler = handler;
        if(!BaseUtil.objectNotNull(handler)){
            shaHandler =HANDLER;
        }
        //默认 SHA256
        if(!BaseUtil.stringNotNull(pattern)){
            pattern = SHAUtils.SHA256;
        }
        return doEncrypt(content, pattern, shaHandler);
    }


    /**
     *  
     *  @Description    : 接受一个处理器 传入盐 sha密码生成后的处理逻辑
     *  @Method_Name    : encrypt
     *  @param  [content, pattern, salt, handler]
     *  @return :java.lang.String
     *  @date  : 2019/8/19 11:25 
     *  @version        : v1.00
     *  @author         : weiJ
     */
    public static String encrypt(String content, String pattern,String salt,SHAHandler handler) throws EncryptComponentException {
        SHAHandler shaHandler = handler;
        if(!BaseUtil.objectNotNull(handler)){
            shaHandler =HANDLER;
        }
        //默认 SHA256
        if(!BaseUtil.stringNotNull(pattern)){
            pattern = SHAUtils.SHA256;
        }
        return doEncrypt(content, pattern, salt, shaHandler);
    }

    /**
     *  
     *  @Description    : 加密密码
     *  @Method_Name    : doEncrypt
     *  @param  [content, pattern, shaHandler]
     *  @return :java.lang.String
     *  @date  : 2019/8/19 10:15 
     *  @version        : v1.00
     *  @author         : weiJ
     */
    private static String doEncrypt(String content, String pattern, SHAHandler shaHandler) throws EncryptComponentException {
        String shaEncrypt;
        //获取盐
        String salt = shaHandler.generateSalt();

        if (BaseUtil.stringNotNull(salt)){
            //盐不为空则 加盐加密
            shaEncrypt= SHAUtils.encrypt(content, salt,pattern);
            return shaHandler.postSha(salt,shaEncrypt);
        }else{
            //盐为空则 直接加密
            return SHAUtils.encrypt(content,pattern);
        }
    }

    /**
     *  
     *  @Description    : 通过加密后的密码得到盐 加密密码
     *  @Method_Name    : doEncryptByOldPassword
     *  @param  []
     *  @return :java.lang.String
     *  @date  : 2019/8/19 14:04 
     *  @version        : v1.00
     *  @author         : weiJ
     */
    public  static String encryptByOldPassword(String content, String pattern, String password, SHAHandler shaHandler) throws EncryptComponentException {
        if(!BaseUtil.objectNotNull(shaHandler)){
            shaHandler =HANDLER;
        }
        //默认 SHA256
        if(!BaseUtil.stringNotNull(pattern)){
            pattern = SHAUtils.SHA256;
        }
        //获取盐
        String salt = getSaltByPassword(password);
        //加密密码
        return doEncrypt(content,pattern,salt,shaHandler);
    }


    /**
     *
     *  @Description    :
     *  @Method_Name    : doEncrypt
     *  @param  [content, pattern, shaHandler]
     *  @return :java.lang.String
     *  @date  : 2019/8/19 10:15
     *  @version        : v1.00
     *  @author         : weiJ
     */
    private static String doEncrypt(String content, String pattern, String salt, SHAHandler shaHandler) throws EncryptComponentException {
        String shaEncrypt = null;
        if (BaseUtil.stringNotNull(salt)){
            //盐不为空则 加盐加密
            shaEncrypt= SHAUtils.encrypt(content, salt,pattern);
            return shaHandler.postSha(salt,shaEncrypt);
        }else{
            //盐为空则 直接加密
            return SHAUtils.encrypt(content,pattern);
        }
    }

   /**
    *  
    *  @Description    : 通过加密后的密码得到盐
    *  @Method_Name    : getSaltByPassword
    *  @param  [password]
    *  @return :java.lang.String
    *  @date  : 2019/8/19 13:51 
    *  @version        : v1.00
    *  @author         : weiJ
    */
    private static String getSaltByPassword(String password){
        //盐
        StringBuilder saltString = new StringBuilder();
        //获取盐
        for (int i=1,j=0;j<saltLength;i+=2,j++){
            saltString.append(password.charAt(i));
        }
        return saltString.toString();
    }

    /**
     *  
     *  @Description    : 密码加密 策略
     *  @Method_Name    :
     *  @param  
     *  @return :
     *  @date  : 2019/8/19 10:20 
     *  @version        : v1.00
     *  @author         : weiJ
     */
    interface SHAHandler {

        /**
         * 盐生成策略 默认自动生成
         * @return
         */
        default String generateSalt(){
            return null;
        };

        /**
         * 加密字符串后处理器 默认不做任何处理
         * @param salt
         * @param shaStr
         * @return
         */
        default String postSha(String salt,String shaStr){
            return shaStr;
        };
    }


    
    /**
     *  
     *  @Description    : 默认加密处理器  默认生成动态盐
     *  @Method_Name    :
     *  @param  
     *  @return :
     *  @date  : 2019/8/19 10:13 
     *  @version        : v1.00
     *  @author         : weiJ
     */
    static class DefaultHandler implements SHAHandler{
        /**
         *  
         *  @Description    : 生成动态盐 随机数+时间戳
         *  @Method_Name    : generateSalt
         *  @param  []
         *  @return :java.lang.String
         *  @date  : 2019/8/19 10:17 
         *  @version        : v1.00
         *  @author         : weiJ
         */
        @Override
        public String generateSalt() {
            return BaseUtil.getRandomNo();
        }

        /**
         *  
         *  @Description    : 将动态盐 插入加密后的字符串中
         *  @Method_Name    : postSha
         *  @param  [salt, shaStr]
         *  @return :java.lang.String
         *  @date  : 2019/8/19 10:17 
         *  @version        : v1.00
         *  @author         : weiJ
         */
        @Override
        public String postSha(String salt, String shaStr) {
            StringBuilder stringBuilder = new StringBuilder(shaStr);
            //转化成字符数组
            char[] saltChars = salt.toCharArray();
            //遍历字符数组，将字符隔一位插入md5加密后的字符串中
            for (int i=0,j=1;i<saltChars.length;i++,j+=2){
                stringBuilder.insert(j,saltChars[i]);
            }
            return stringBuilder.toString();
        }
    }
}
