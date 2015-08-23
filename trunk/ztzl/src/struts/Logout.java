package struts;

import hibernate.pojo.Operators;

import java.util.List;

import common.MD5Sec;

public class Logout extends BaseAction {


	public String execute() {

		getSession().removeAttribute("username");
		getSession().removeAttribute("userid");
		return SUCCESS;
	}



}
