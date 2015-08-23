package struts;

import hibernate.pojo.Operators;

import java.util.List;

import common.MD5Sec;

public class Login extends BaseAction {

	public String username;
	public String userpass;

	public String result;

	public String execute() {

		if (getUsername().length() < 1) {
			setResult("用户名不能为空！");
		}

		List<Object> list = findAll(" from Operators where 1=1 and username='"
				+ getUsername() + "' ");

		if (list.size() < 1) {
			setResult("该用户名不存在，请检查！");
			return SUCCESS;
		}

		Operators user = (Operators) list.get(0);
		String pass = user.getUserpass().trim();

		if (pass == null || !pass.equals(MD5Sec.MD5(getUserpass().trim()))) {
			setResult("用户密码错误!请重新输入．");
			return SUCCESS;
		}

		getSession().setAttribute("username", getUsername());
		getSession().setAttribute("userid", user.getId());
		if (user.getUserarea() != null) {
			getSession().setAttribute("userarea", user.getUserarea().trim());
		}

		//		  		 
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
