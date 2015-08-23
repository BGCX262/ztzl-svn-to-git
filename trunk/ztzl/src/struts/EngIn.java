package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class EngIn extends BaseAction {

	// public List list;
	public PageInfo pi;
	public String pageNo;
	public String pageCount;
	public String condi; // 查询条件
	public String command;
	public boolean notlog;

	// 选中的主键结果集
	public String ids;

	// 返回结果
	public boolean result;
	public String rtntxt;
	public static String queryString = " from Proflow where 1=1 and returnmark='0' and receiver=null and sendto='3' ";
	public static String order = " order by id desc ";

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
			setRtntxt(e.toString());
			return SUCCESS;
		}
	}

	public String exe() {

		if (getCommand() == null)
			setCommand("");

		if (getCommand().equals("ware")) {
			wareHousing();
		} else if (getCommand().equals("back")) {
			backwards();
		} else
			setPi(findAll(queryString + getCondi() + order,
					str2int(getPageNo()), str2int(getPageCount())));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void backwards() {

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");
		List list1 = new ArrayList();

		for (Object o : list) {
			Proflow proFlow = (Proflow) o;
			proFlow.setReturnmark("1");

			Proflow pf = (Proflow) super.findAll(
					" from Proflow where 1=1 and id=" + proFlow.getPreid())
					.get(0);
			pf.setOvermark("0");
			pf.setSendinfo("工程部退回");
			list1.add(pf);

		}
		list1.addAll(list);
		super.uporsave(list1);
	}

	@SuppressWarnings("unchecked")
	public void wareHousing() {

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");

		for (Object o : list) {
			Proflow proFlow = (Proflow) o;
			proFlow.setCurware("3"); // 设置当前所在为仓库
			// proFlow.setReceiver("ck"); //TODO： 修改为操作用户
			proFlow.setReceiver(getSession().getAttribute("username")
					.toString());
			proFlow.setReceiveinfo("工程部接收");
			proFlow.setReceivedate(new Date());

		}

		super.uporsave(list);

		this.setResult(true);

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

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

}
