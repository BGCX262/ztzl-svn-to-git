package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Warehousing extends BaseAction {

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
	public static String queryString = " from Proflow where 1=1 and returnmark='0' and receiver=null and sendto='2' ";
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
	public void wareHousing() {

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");

		for (Object o : list) {
			Proflow proFlow = (Proflow) o;
			proFlow.setCurware("2"); // 设置当前所在为仓库
			// proFlow.setReceiver("ck"); //TODO： 修改为操作用户
			proFlow.setReceiver(getSession().getAttribute("username")
					.toString());
			proFlow.setReceiveinfo("仓库接收");
			proFlow.setReceivedate(new Date());

		}

		super.uporsave(list);

		this.setResult(true);

	}

	@SuppressWarnings("unchecked")
	public void backwards() {

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");
		List list1 = new ArrayList();
		List tmp = new ArrayList();
		for (Object o : list) {
			Proflow proFlow = (Proflow) o;
			proFlow.setReturnmark("1");
			// 来源为生产车间的退回生产车间
			if (proFlow.getProsource().equals("1")) {
				Products pro = (Products) super.findAll(
						" from Products where 1=1 and procode='"
								+ proFlow.getProcode() + "' ").get(0);
				pro.setProstatus("0");
				pro.setPromemo("仓库退回");
				list1.add(pro);
			}
			// 来源为生技部的退回生技部   来源为工程部的返库产品 退回到工程部 
			else {
				Proflow pf = (Proflow) super.findAll(
						" from Proflow where 1=1 and id=" + proFlow.getPreid())
						.get(0);
				pf.setOvermark("0");
				pf.setSendinfo("仓库退回");
				list1.add(pf);
			}

		}
		list1.addAll(list);
		super.uporsave(list1);
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
