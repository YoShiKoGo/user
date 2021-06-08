package usermanage.example.myfeng.service;

import com.travelsky.component.encrypt.common.exeption.EncryptComponentException;
import com.travelsky.component.encrypt.sha.SHAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.common.security.UserRoleCn;
import usermanage.example.myfeng.dao.IOperatLogDao;
import usermanage.example.myfeng.dao.IPlatformDao;
import usermanage.example.myfeng.dao.IUserDao;
import usermanage.example.myfeng.form.OperatorForm;
import usermanage.example.myfeng.po.OperatLogPo;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.*;
import usermanage.example.myfeng.vo.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project : user-manage
 */
@Service
public class UserServiceImpl implements IUserService {

    /**
     * Logger
     */
    private static final Logger lg = LoggerFactory.getLogger(UserServiceImpl.class);


    //注入用户持久层
    @Autowired
    private IUserDao userDao;

    //注入平台持久层
    @Autowired
    private IPlatformDao platformDao;

    //注入操作记录
    @Autowired
    private IOperatLogDao operatLogDao;

    //注入redis
    @Autowired
    private IRedisService redisService;


    /**
     * 登录用户
     *
     * @Method_Name : login
     */
    @Override
    public CommonResObject login(String userName, String password, String ip) throws ManageException, EncryptComponentException {
        CommonResObject commonResObject = new CommonResObject();
        //获取用户 通过用户名
        UserPo userPo = userDao.findByName(userName);
        //用户不存在
        if (userPo == null) {
            commonResObject.setResCode("0");
            commonResObject.setResMsg("用户名或密码错误");
            return commonResObject;
        }
        if (userPo.getStatus() != null && userPo.getStatus() == 0) {
            throw new ManageException("该用户已经被禁止使用！");
        }
        try {
            //MD5加密密码
            password = DigestUtil.encryptPassword(password);
            //加盐 哈希算法再加密
            password = SHAUtil.encryptByOldPassword(password, SHAUtils.SHA256,
                    userPo.getPassword(), SHAUtil.DEFAULT);
        } catch (EncryptComponentException e) {
            lg.info("登陆加密失败");
        }
        //密码判断
        if (!userPo.getPassword().equals(password)) {
            commonResObject.setResCode("0");
            commonResObject.setResMsg("密码错误");
            return commonResObject;
        }

        //更新用户最后登陆时间
        userPo.setLastLogin_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));

        //用户最后登陆的IP
        userPo.setLastLogin_ip(ip);
        //更新用户
        userDao.updateLogin(userPo);
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(userPo.getAccount());
        //操作人
        operatLogPo.setRole(userPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(RoleChangeUtil.change(userPo.getLevel()) + "_" + userPo.getAccount() + "在" + operatLogPo.getDateStr() +
                "登录了系统");
        lg.info("用户登录操作：" + operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        commonResObject.setResMsg("登录成功！");
        commonResObject.setResObj(userPo);
        return commonResObject;
    }

    /**
     * 查询用户级别所有用户
     */
    @Override
    public CommonResObject getAllByLevel(UserPo operatUserPo, String... strings) {
        CommonResObject commonResObject = new CommonResObject();
        try {
            List<UserPo> userPos = userDao.findAllByUserRole(operatUserPo);
            List<UserPo> userPoReturn = new ArrayList<>();
            //循环设置用户role名称
            for (int i = 0; i < userPos.size(); i++) {
                UserPo userPo = userPos.get(i);
                String roleName = RoleChangeUtil.change(userPo.getLevel());
                userPo.setRole(roleName);
                if (!userPo.get_id().equals(operatUserPo.get_id())) {
                    userPoReturn.add(userPo);
                }
            }
            lg.info("获取所有用户成功");
            commonResObject.setResObj(userPoReturn);
            commonResObject.setResMsg("查找成功！");
        } catch (Exception e) {
            lg.debug("userDao报错，错误信息：" + e.getMessage());
            e.printStackTrace();
            commonResObject.setResCode("0");
            commonResObject.setResMsg("查找失败，请重试！");
        } finally {
            return commonResObject;
        }

    }

    /**
     * 查询所有用户
     */
    @Override
    public List<UserPo> getAll() {
        return userDao.findAll();
    }

    /**
     * 更新操作 status = 0
     */
    @Override
    public CommonResObject delUserById(UserPo operatUserPo, String id) throws ManageException {
        CommonResObject resObject = new CommonResObject();
        UserPo userPo = userDao.selectById(id);
        if (!BaseUtil.objectNotNull(userPo)){
            lg.info("用户不存在");
            throw new ManageException("用户不存在");
        }
        if (operatUserPo.getLevel().equals("2")||userPo.getPlatformId().equals(operatUserPo.getPlatformId())){
            lg.info("越权操作");
            throw new ManageException("删除用户失败");
        }
        userDao.delUserUpdate(id);
        resObject.setResCode("1");
        resObject.setResMsg("删除成功");
        return resObject;
    }


    /**
     * @param [password]
     * @return :void
     * @Description : 加密密码
     * @Method_Name : enCryptPassword
     * @date : 2019/8/19 15:23
     * @version : v1.00
     * @author : weiJ
     */
    private String enCryptPassword(String password) throws EncryptComponentException {
        //md5加密
        String md5Password = DigestUtil.encryptPassword(password);
        //加盐 在加密
        return SHAUtil.encrypt(md5Password, SHAUtils.SHA256, SHAUtil.DEFAULT);
    }

    /**
     * 通过平台id查找用户
     */
    @Override
    public CommonResObject getUserByPlatFormName(String platFormId) {
        List<UserPo> userPos = userDao.findAllByPlatformId(platFormId);
        return creatOperatorListVo(userPos);
    }


    /**
     * 封装前端用户vo
     *
     * @return
     */
    public CommonResObject creatOperatorListVo(List<UserPo> userPos) {
        CommonResObject commonResObject = new CommonResObject();
        if (userPos.size() != 0) {
            List<OperatorListVo> operatorListVo = new ArrayList<>();
            //循环设置用户role名称
            for (int i = 0; i < userPos.size(); i++) {
                UserPo userPo = userPos.get(i);
                if (BaseUtil.objectNotNull(userPo)) {
                    String roleName = RoleChangeUtil.change(userPo.getLevel());
                    userPo.setRole(roleName);
                    OperatorListVo vo = new OperatorListVo(userPo);
                    operatorListVo.add(vo);
                }
            }
            commonResObject.setResObj(operatorListVo);
        }
        commonResObject.setResMsg("查找成功！");
        return commonResObject;
    }

    @Override
    public CommonResObject getUserStats() {
        CommonResObject resObject = new CommonResObject();
        //系统数据存放对象
        SystemStats systemStats = new SystemStats();
        //查询所有用户
        List<UserPo> userPos = userDao.findAll();
        List<UserStats> userStatsList = new ArrayList<>();
        //当前时间
        String dayStr = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String mouthStr = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMM);
        for (UserPo u : userPos
        ) {
            UserStats userStats = new UserStats();
            //账号名
            userStats.setAccount(u.getAccount());
            //用户角色
            userStats.setRole(RoleChangeUtil.change(u.getLevel()));
            //查出当天的登录次数
            Integer dayCount = (Integer) redisService.getValue(u.getAccount() + "_" + dayStr);
            if (dayCount == null) {
                dayCount = 0;
            }
            //查出本月登录次数
            Integer mouthCount = (Integer) redisService.getValue(u.getAccount() + "_" + mouthStr);
            if (mouthCount == null) {
                mouthCount = 0;
            }
            userStats.setDayCount(dayCount);
            userStats.setMonth(mouthCount);
            //加入集合
            userStatsList.add(userStats);
        }
        //查出当天的系统次数
        Integer sysDayCount = (Integer) redisService.getValue("sysCount_" + dayStr);
        //查出当月的系统次数
        Integer sysMouthCount = (Integer) redisService.getValue("sysCount_" + mouthStr);
        //按月登陆数排序
        Collections.sort(userStatsList, new Comparator<UserStats>() {
            public int compare(UserStats o1, UserStats o2) {
                if (null == o1.getMonth()) {
                    return -1;
                }
                if (null == o2.getMonth()) {
                    return 1;
                }
                return o2.getMonth().compareTo(o1.getMonth());
            }
        });
        //系统登录记录
        systemStats.setUserStatsList(userStatsList);

        //系统登录次数
        systemStats.setSysDayCount(sysDayCount);
        //系统月登录次数
        systemStats.setSysMouthCount(sysMouthCount);
        //查询操作记录
        //List<OperatLog> operatLogs = operatLogDao.findTopTen();
        //systemStats.setOperateLogs(operatLogs);
        //存入返回对象
        resObject.setResObj(systemStats);
        resObject.setResMsg("查询成功");
        return resObject;
    }

    @Override
    public CommonResObject getUserLog(int pageSize, int currentPage) {
        CommonResObject resObject = new CommonResObject();
        //查询操作记录
        List<OperatLogPo> operatLogPos = operatLogDao.findTopTen(pageSize, currentPage);
        for (OperatLogPo operatLogPo : operatLogPos) {
            if (BaseUtil.stringNotNull(operatLogPo.getRole())) {
                operatLogPo.setRole(RoleChangeUtil.changeByRole(operatLogPo.getRole()));
            }
        }
        resObject.setResObj(operatLogPos);
        return resObject;
    }

    @Override
    public CommonResObject getTotalUserLog() {
        CommonResObject resObject = new CommonResObject();
        //查询操作记录
        List<OperatLogPo> operatLogPos = operatLogDao.getTotalUserLog();
        resObject.setResObj(operatLogPos.size());
        return resObject;
    }

    /**
     * 添加用户
     * @param onlineUserPo 操作者
     * @param operatorForm 添加的用户
     * @return
     * @throws Exception
     */
    @Override
    public CommonResObject creatUser(UserPo onlineUserPo, OperatorForm operatorForm) throws Exception {
        CommonResObject commonResObject = new CommonResObject();
        if (operatorForm.getRole().equals("公司管理员")) {
            //添加公司管理员
            if (!onlineUserPo.getLevel().equals("1")) {
                throw new ManageException("无权限");
            }
            //管理员所在平台
            String platformId = operatorForm.getPlatformId();
            if (!BaseUtil.stringNotNull(platformId)) {
                throw new ManageException("添加管理员平台不能为空");
            }
            //获取所有管理员
            List<UserPo> companys = userDao.findAllByLevel("2");
            for (UserPo company : companys
            ) {
                if (company.getPlatformId() != null && company.getPlatformId().equals(platformId)) {
                    throw new ManageException("该平台已经有管理员");
                }
            }
            operatorForm.setRole(RoleChangeUtil.changeReturn(operatorForm.getRole()));
            //设置用户级别
            operatorForm.setLevel("2");
        } else {
            //添加普通用户
            //设置用户级别
            if (onlineUserPo.getLevel().equals("2") && !onlineUserPo.getPlatformId().
                    equals(operatorForm.getPlatformId())) {
                throw new ManageException("无权限");
            }
            operatorForm.setLevel("3");
            operatorForm.setRole(RoleChangeUtil.changeReturn(operatorForm.getRole()));
        }
        UserPo userPo = new UserPo();
        //密码加密存储
        //通过私钥对前端加密的密码解密
        String accountPwd = CreateSecrteKeyUtil.decrypt(operatorForm.getPassword(), CreateSecrteKeyUtil.getPrivateKey());
        //校验密码长度
        validatePassWord(accountPwd);
        //弱密码校验
        boolean isStrongPassword = checkWeakPassword(operatorForm.getAccount(), accountPwd);
        if (!isStrongPassword) {
            lg.info("该密码不为强密码");
            throw new ManageException("该密码不为强密码");
        }
        try {
            accountPwd = enCryptPassword(accountPwd);
        }catch (EncryptComponentException e){
            lg.info("加密失败");
            throw new ManageException("添加用户失败");
        }
        operatorForm.setPassword(accountPwd);
        //设置用户平台
        PlatformPo platformPo = platformDao.findById(operatorForm.getPlatformId());
        operatorForm.setPlatformName(platformPo.getPlatformName());
        //设置创建时间
        userPo.setCreate_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //设置更新时间
        userPo.setLastUpdate_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //设置正常状态
        userPo.setStatus(1);
        BeanUtils.copyProperties(operatorForm, userPo);
        userDao.save(userPo);
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(onlineUserPo.getAccount());
        //操作人
        operatLogPo.setRole(onlineUserPo.getRole());
        //操作人
        operatLogPo.setRole(onlineUserPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(onlineUserPo.getLevel()))
                .append("_").append(onlineUserPo.getAccount()).append("在")
                .append(operatLogPo.getDateStr()).append(
                        "添加了").append(RoleChangeUtil.change(userPo.getLevel())).append(userPo.getAccount()).toString());
        lg.info("添加人员操作：" + operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        commonResObject.setResMsg("添加成功");
        return commonResObject;
    }

    /**
     * 校验强弱密码
     */
    private boolean checkWeakPassword(String account, String password) throws ManageException {
        // 判断密码中是否含有用户名
        boolean containName = stringContains(password, account);
        // 判断密码中是否含有三个字符重复的子字符串
        boolean continueSir = isRepeatPassword(password);
        //判断三个相同字符
        boolean result = containsThreeConsecutiveIdentical(password);
        //判断三个连续字符
        boolean threeSame = containsThreeConsecutiveValue(password);
        if (result || threeSame || containName || continueSir) {
            return false;
        }
        //判断是否全为数字或字母
        boolean letter = allLetterOrNum(password);
        if (!letter) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否全为数字或字母
     * @param password
     * @return
     */
    private boolean allLetterOrNum(String password) {
        String REG_LETTER = "^[a-zA-Z]+$";
        String REG_LETTERU = "^[a-z]+$";
        String REG_LETTERL = "^[A-Z]+$";
        String REG_NUM = "^[0-9]+$";
        String REG_LENGTH = "^[\\S]{8,16}$";
        if(BaseUtil.strReg(password, REG_LETTER) ||
                BaseUtil.strReg(password, REG_LETTERU)||
                BaseUtil.strReg(password, REG_LETTERL)||
                BaseUtil.strReg(password, REG_NUM)){
            return false;
        }
        if(!BaseUtil.strReg(password, REG_LENGTH)){
            return false;
        }
        return true;
    }

    /**
     * 判断三个连续字符
     *
     * @param password
     * @return
     */
    private boolean containsThreeConsecutiveValue(String password) {
        int length = password.length();
        for (int i = 0; i < length - 2;) {
            char c2 = password.charAt(i + 1);

            if ((Character.isDigit(c2)) || (Character.isLowerCase(c2))
                    || (Character.isUpperCase(c2))) {
                char c1 = password.charAt(i);
                char c3 = password.charAt(i + 2);
                if ((c2 * '\2' == c1 + c3)
                        && (((c2 == c1 + '\1') || (c2 == c3 + '\1')))) {
                    return true;
                }
                i += 1;
            } else {
                i += 2;
            }
        }
        return false;
    }

    /**
     * 判断三个相同字符
     * @param password 密码
     * @return
     */
    private boolean containsThreeConsecutiveIdentical(String password) {
        int length = password.length();
        for (int i = 0; i < length - 2;) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            char c3 = password.charAt(i + 2);
            if (c3 != c2)
                i += 2;
            else if (c1 != c2)
                i += 1;
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断密码中是否含有三个字符重复的子字符串
     * @param password
     * @return
     */
    private boolean isRepeatPassword(String password) {
        boolean result = false;
        int lena = password.length();
        for (int i = 0; i < lena - 2; i++) {
            String c = password.substring(i, i + 3);
            int t = password.indexOf(c, i + 3);
            if (t >= 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 判断密码中是否含有用户名
     *
     * @param string1 账户
     * @param string2 密码
     * @return
     */
    private boolean stringContains(String string1, String string2) {
        int a = string1.indexOf(string2);
        if (a >= 0) {
            return true;
        }
        return false;
    }


    /**
     * 校验密码
     */
    private void validatePassWord(String passWord) throws ManageException {

        //验证密码长度
        String regPassword = "^[\\S]{8,16}$";
        if (!BaseUtil.strReg(passWord, regPassword)) {
            lg.error("密码长度不合法");
            throw new ManageException("密码长度不合法");
        }
    }

    /**
     * 管理员修改用户资料
     */
    @Override
    public CommonResObject updateUser(UserPo operatUserPo, OperatorForm operatorForm) throws ManageException {
        CommonResObject resObject = new CommonResObject();
        //设置用户级别
        if (operatorForm.getRole().equals(UserRoleCn.公司管理员.toString())) {
            operatorForm.setLevel("2");
            //获取所有管理员
            List<UserPo> companys = userDao.findAllByLevel("2");
            for (UserPo company : companys
            ) {
                if (company.getPlatformId() != null &&
                        company.getPlatformId().equals(operatorForm.getPlatformId())) {
                    resObject.setResMsg("该平台已经有管理员");
                    throw new ManageException("该平台已经有管理员");
                }
            }
        } else if (operatorForm.getRole().equals(UserRoleCn.普通操作员.toString())) {
            operatorForm.setLevel("3");
        }
        operatorForm.setRole(RoleChangeUtil.changeReturn(operatorForm.getRole()));
        try {
            UserPo userPo = new UserPo();
            BeanUtils.copyProperties(operatorForm, userPo);
            userDao.updateByModify(userPo);
        } catch (Exception e) {
            lg.info("修改用户信息失败");
            throw new ManageException("修改用户信息失败");
        }
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(operatUserPo.getAccount());
        //操作人
        operatLogPo.setRole(operatUserPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(operatUserPo.getLevel()))
                .append("_").append(operatUserPo.getAccount()).append("在")
                .append(operatLogPo.getDateStr()).append(
                        "修改了用户").append(operatorForm.getAccount()).append("的资料").toString());
        lg.info("修改用户资料：" + operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        resObject.setResMsg("修改成功");
        return resObject;
    }

    /**
     * 通过主键查找一个用户
     */
    @Override
    public CommonResObject getUserById(String _id, UserPo userPo) throws ManageException {
        CommonResObject commonResObject = new CommonResObject();
        UserPo operatorUserPo = userDao.selectById(_id);
        //判断操作用户是否有权限修改用户信息
        isAuthority(operatorUserPo, userPo);
        //封装前端所需要的vo
        operatorUserPo.setRole(RoleChangeUtil.change(operatorUserPo.getLevel()));
        OperatorListVo operatorListVo = new OperatorListVo(operatorUserPo);
        commonResObject.setResCode("1");
        commonResObject.setResMsg("查找成功");
        commonResObject.setResObj(operatorListVo);
        return commonResObject;
    }

    /**
     * 判断操作用户是否有权限修改用户信息
     */
    private void isAuthority(UserPo operatorUserPo, UserPo userPo) throws ManageException {
        if (!BaseUtil.objectNotNull(operatorUserPo)) {
            throw new ManageException("该用户不存在");
        }
        if (userPo.getLevel().equals("2") && !userPo.getPlatformId().equals(operatorUserPo.getPlatformId())) {
            throw new ManageException("非法访问");
        } else if (userPo.getLevel().equals("3") && userPo.get_id().equals(operatorUserPo.get_id())) {
            throw new ManageException("非法访问");
        }
    }

    /**
     * 用户更新个人资料
     */
    @Override
    public CommonResObject updateSomeInfo(OperatorForm operatorForm, UserPo userPo) throws ManageException {
        CommonResObject commonResObject = new CommonResObject();
        operatorForm.setRole(RoleChangeUtil.changeReturn(operatorForm.getRole()));
        //通过账户查找是否有该用户
        UserPo operatorUserPo = userDao.selectByAccount(operatorForm.getAccount());
        //判断操作用户是否有权限修改用户信息
        isAuthority(operatorUserPo, userPo);
        try {
            //更新最后修改时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
            userPo.setLastUpdate_time(sdf.format(date));
            //更改信息
            BeanUtils.copyProperties(operatorUserPo, operatorForm);
            userDao.updatePersonal(operatorUserPo);
            commonResObject.setResCode("1");
            commonResObject.setResMsg("更新成功");
            //设置操作记录
            OperatLogPo operatLogPo = new OperatLogPo();
            //操作人
            operatLogPo.setAccount(userPo.getAccount());
            //操作人
            operatLogPo.setRole(userPo.getRole());
            //操作时间
            operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                    DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            //操作内容
            operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(userPo.getLevel()))
                    .append("_").append(userPo.getAccount()).append("在")
                    .append(operatLogPo.getDateStr()).append(
                            "更新了个人资料").append(userPo.getAccount()).toString());
            lg.info("更新个人资料操作：" + operatLogPo.getLogContent());
        } catch (Exception e) {
            lg.debug(e.getMessage());
            throw new ManageException("操作错误，请重试！");
        } finally {
            return commonResObject;
        }
    }


}
