package usermanage.example.myfeng.common.security;


import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.po.UserPo;

import java.util.List;
/**
 * 对方法进行权限检查
 */
public class AuthChecker {
	volatile String j;

	/**
	 *  权限检查
	 */
	public static void check(List<String> roles, UserPo userPo) throws ManageException {
		//无需任何权限即可访问
		if(roles.contains(UserRole.ROLE_ANY.toString())){
			return;
		}else if (roles.contains(userPo.getRole())){
			return;
		}else {
			throw new ManageException("越权访问！");
		}
	}
}