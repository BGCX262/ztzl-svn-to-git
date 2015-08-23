package struts.report;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prowarehousing extends struts.BaseAction {

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
	private String cn;
	private String ny;
	private String typeid;
	private String rn;

	private static String queryString = "select a.id from Prowarehousing a where 1=1   ";
	private static String order = "  ";

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
			this.setRtntxt("运行错误！");
			return SUCCESS;
		}
	}

	public String exe() {

		
			setPi(super.findAll(queryString + getCondi() + order,
					str2int(getPageNo()), str2int(getPageCount())));
		return SUCCESS;
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

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
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

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getNy() {
		return ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public static String getOrder() {
		return order;
	}

	public static void setOrder(String order) {
		Prowarehousing.order = order;
	}

	

	
}
