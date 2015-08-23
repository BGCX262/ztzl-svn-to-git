package struts.charts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthRepairPie extends struts.BaseAction {

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

	private static String queryString = " select errorview as mn ,count(*) as cn from Prorepair where 1=1 ";
	private static String order = " group by errorview  ";

	public String execute() {
		try {

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

		if (getCondi() == null || getCondi().equals(""))
			setCondi(" and senddate between add_months(sysdate,-6) and sysdate ");

		setPi(super.findAll(queryString + getCondi() + order, 1, 100));
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

	public static String getQueryString() {
		return queryString;
	}

	public static void setQueryString(String queryString) {
		MonthRepairPie.queryString = queryString;
	}

	public static String getOrder() {
		return order;
	}

	public static void setOrder(String order) {
		MonthRepairPie.order = order;
	}

	/**
	 * @return the notlog
	 */
	public boolean isNotlog() {
		return notlog;
	}

	/**
	 * @param notlog
	 *            the notlog to set
	 */
	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

}


