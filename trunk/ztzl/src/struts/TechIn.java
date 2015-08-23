package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proeng;
import hibernate.pojo.Proflow;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class TechIn extends BaseAction {

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
	public static String queryString = " from Proflow where 1=1 and returnmark='0' and receiver is null and sendto='4' ";
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
			setRtntxt("接收程序出错，请与管理员联系！");
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

			Prorepair pp = (Prorepair) super.findAll(
					" from Prorepair where 1=1 and procode='"
							+ proFlow.getProcode() + "'").get(0);
			pp.setWaremark("0");
			pp.setMemo("生技部退回");
			list1.add(pp);

		}
		list1.addAll(list);
		super.uporsave(list1);
	}

	@SuppressWarnings("unchecked")
	public void wareHousing() {

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");
		List list1 = new ArrayList();
		for (Object o : list) {
			// 修改返修产品状态
			Proflow proFlow = (Proflow) o;

			
			String typeid = "0";

			List t = super
					.findAll(" from Prorepair where 1=1 and waremark='1' and  procode='"
							+ proFlow.getProcode() + "' ");

			Prorepair pp = (Prorepair) t.get(0);
			pp.setWaremark("2");
			typeid=pp.getTypeid();
			list1.add(pp);

			proFlow.setCurware("4"); // 设置当前所在为技术部
			// proFlow.setReceiver("ck"); 
			proFlow.setReceiver(getSession().getAttribute("username")
					.toString());
			proFlow.setReceiveinfo("生技部检验");
			proFlow.setReceivedate(new Date());
			if (typeid.equals("0"))
				proFlow.setTypeid(Long.valueOf(pp.getTypeid()));
			else
				proFlow.setTypeid(Long.valueOf(typeid));
			
			// 如果有安装记录则 生成替换产品安装信息、
			t = super
					.findAll(" from Proeng where 1=1 and runstatus='1' and procode='"
							+ proFlow.getProcode() + "' ");

			if (t.size() > 0) {
				Proeng peold = (Proeng) t.get(0);
				Proeng penew = new Proeng(peold);
				peold.setRunstatus("0");
				list1.add(peold);
				list1.add(penew);
			} else {
				Proeng peold = new Proeng();
				peold.setProcode(proFlow.getProcode());
				peold.setRunstatus("0");
				peold.setEngaddr("virtual");
				peold.setEngarea("virtual");
				peold.setPrinciple("virtual");
				peold.setTypeid(Long.valueOf(typeid));

				list1.add(peold);
				List p = super.findAll("from Products where 1=1 and procode='"
						+ peold.getProcode() + "' ");
				if (p.size() < 1)
					list1.addAll(new DealProeng().dealVirtual(peold));
				

			}

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
