package struts;

import hibernate.PageInfo;
import hibernate.pojo.Operators;

import java.util.List;

import common.MD5Sec;

public class UserMan  extends BaseAction {

	// public List list;
	public PageInfo pi;
	public String pageNo;
	public String pageCount;
	public String condi; // 查询条件
	public String command;
	public boolean notlog;

	// 删除使用的主键结果集
	public String ids;

	// 返回结果
	public boolean result;
	public String rtntxt;
	
	// 维护form需要的字段
	private String id;
	private String username;
	private String userpass;
	private String repass;
	private String memo;
	private String userarea;
	private static String queryString=" from Operators where 1=1 ";
	private static String order=" order by id desc";
	


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

	public String exe() {

		if (getCommand() == null)
			setCommand("");

		if (getCommand().equals("dele"))
			delete();
		else if (getCommand().equals("add")) {
			return addnew();			
		} else if (getCommand().equals("qone")) {
			setPi(super.findAll(queryString + getCondi(), 1, 100));
		}else
			setPi(super.findAll(queryString + getCondi()+ order,
					str2int(getPageNo()), str2int(getPageCount())));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void delete() {

		List list = super.findAll(queryString+"and id in (" + getIds() + "0 )");
		for (Object obj : list)
			super.delete(obj);
		setResult(true);

	}

	public String addnew() {
		try {
			
			Operators operators;

			if (getId().length() > 0) {
				operators = (Operators) super.findByID("Operators",
						Long.valueOf(str2int(getId())));
			} else
				operators = new Operators();
			
			
			operators.setUsername(this.getUsername());
			operators.setUserpass(MD5Sec.MD5(this.getUserpass()));
			operators.setMemo(this.getMemo());
			operators.setUserarea(this.getUserarea());
			

			if (getId().length() > 0)
				super.update(operators);
			else
				super.save(operators);
			return null;
		} catch (Exception e) {
			setRtntxt("保存失败！");
			// 返回一个错误信息
			return SUCCESS;
		}
	}

	public PageInfo getPi() {
		return pi;
	}

	public void setPi(PageInfo pi) {
		this.pi = pi;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getCondi() {
		return condi;
	}

	public void setCondi(String condi) {
		this.condi = condi;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getRtntxt() {
		return rtntxt;
	}

	public void setRtntxt(String rtntxt) {
		this.rtntxt = rtntxt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the notlog
	 */
	public boolean isNotlog() {
		return notlog;
	}

	/**
	 * @param notlog the notlog to set
	 */
	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

	public String getUserarea() {
		return userarea;
	}

	public void setUserarea(String userarea) {
		this.userarea = userarea;
	}

	
	
}
