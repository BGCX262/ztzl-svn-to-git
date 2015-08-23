package struts;

import hibernate.pojo.Operators;

import java.util.List;

import common.MD5Sec;

public class ChPass extends BaseAction {

	public String userpass;
	public String newpass;
	public String repass;

	public boolean notlog;

	public boolean success;
	
	public String msg;

	public String execute() {

		try {
			// TODO:判断Session
			if (getSession().getAttribute("username") == null) {
				setNotlog(true);
				return SUCCESS;
			}
			// 执行请求
			return exe();
		} catch (Exception e) {

			return SUCCESS;
		}
	}

	@SuppressWarnings("unchecked")
	public String exe() {

		if (!this.getNewpass().trim().equals(this.getRepass().trim())) {
			setMsg("两次输入密码不一致！");
			return SUCCESS;
		}

		List list = super.findAll(" from Operators where 1=1 and username='"
				+ getSession().getAttribute("username") + "' ");
		if (list.size() < 1) {
			setMsg("操作失败！该用户可能已被删除。");
			return SUCCESS;
		}

		Operators op = (Operators) list.get(0);
		if (!common.MD5Sec.MD5(this.getUserpass()).equals(op.getUserpass())) {
			setMsg("用户旧密码错误！");
			return SUCCESS;
		}

		op.setUserpass(common.MD5Sec.MD5(getNewpass().trim()));

		super.update(op);

		setSuccess(true);
		return null;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
