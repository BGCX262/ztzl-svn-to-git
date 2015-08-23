package struts;

import hibernate.PageInfo;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ReturnPlant extends BaseAction {

	// public List list;
	public PageInfo pi;
	public String pageNo;
	public String pageCount;
	public String condi; // 查询条件
	public String command;
	public boolean notlog;

	// 选中的主键结果集
	public String ids;

	// form 变量
	public String sendarea;

	// 返回结果
	public boolean result;
	public String rtntxt;
	
	//来源为生技部的返修品 
	public static String queryString = " from Proflow where 1=1 and overmark='0' and curware='2' and prosource ='4' ";
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

		if (getCommand().equals("carry")) {
			carryOut();
		} else
			setPi(findAll(queryString + getCondi()+ order, str2int(getPageNo()),
					str2int(getPageCount())));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void carryOut() {
		
		

		List list = super.findAll(queryString + " and id in (" + getIds()
				+ "0 ) ");

		List list1 = new ArrayList();
		for (Object o : list) {
			Proflow source = (Proflow) o;
			source.setOvermark("1"); // 标志原入库流程已完成
			list1.add(source);

			Proflow proFlow = new Proflow();
			proFlow.setProcode(source.getProcode());
			proFlow.setTypeid(source.getTypeid());
			proFlow.setBoxcode(source.getBoxcode());
			proFlow.setSourcedate(new Date());
			proFlow.setCurware("2"); // 当前所在,未出库前,2表示仓库
			proFlow.setProsource("2"); // 1表示生产部,2表示仓库
			proFlow.setSourceoper(getSession().getAttribute("username")
					.toString());
			proFlow.setSendarea(this.getSendarea());
			proFlow.setSendinfo("返修品");

			proFlow.setSendto("1");
			proFlow.setOvermark("0");
			proFlow.setPreid(source.getId());

			list1.add(proFlow);

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

	public String getSendarea() {
		return sendarea;
	}

	public void setSendarea(String sendarea) {
		this.sendarea = sendarea;
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

}
