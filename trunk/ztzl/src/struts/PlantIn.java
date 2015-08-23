package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class PlantIn extends BaseAction {

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
	public static String queryString = " from Proflow where 1=1 and returnmark='0' and receiver is null and sendto='1' ";
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
			setPi(findAll(queryString + order, str2int(getPageNo()),
					str2int(getPageCount())));
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
			pf.setSendinfo("车间退回");
			list1.add(pf);

		}
		list1.addAll(list);
		super.uporsave(list1);
	}


	@SuppressWarnings("unchecked")
	public void wareHousing() {

		List list = super.findAll(queryString + getCondi() + " and id in ("
				+ getIds() + "0 ) ");
		List list1 = new ArrayList();
		for (Object o : list) {
			Proflow proFlow = (Proflow) o;

			List t = super.findAll(" from Prorepair where 1=1 and procode='"
					+ proFlow.getProcode() + "' ");

			Prorepair pp = (Prorepair) t.get(0);
			pp.setWaremark("3");
			list1.add(pp);
			t = super.findAll(" from Products where 1=1 and procode='"
					+ proFlow.getProcode() + "' ");

			Products pr = (Products) t.get(0);
			//pr.setProstatus("0");
			//pr.setRepairnum(pr.getRepairnum() + 1);
			list1.add(pr);

			proFlow.setCurware("1"); // 设置当前所在为车间
			// proFlow.setReceiver("ck"); //TODO： 修改为操作用户
			proFlow.setReceiver(getSession().getAttribute("username")
					.toString());
			proFlow.setReceiveinfo("车间接收");
			proFlow.setReceivedate(new Date());

		}

		list1.addAll(list);
		super.uporsave(list1);

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
