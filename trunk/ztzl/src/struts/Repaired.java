package struts;

import hibernate.PageInfo;
import hibernate.pojo.Boxnum;
import hibernate.pojo.Products;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repaired extends BaseAction {

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
	private String procode;
	private String typeid;
	private Date senddate;
	private String waremark;
	private String errorview;
	private String errorfor;
	private Date changedate;
	private String changecode;
	private String memo;

	private static String queryString = " from Prorepair where 1=1 and waremark='3' ";
	private static String order = " order by id desc ";

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
		} else if (getCommand().equals("ware")) {
			wareHousing();
		} else if (getCommand().equals("qone")) {
			setPi(super.findAll(queryString + getCondi(), 1, 100));
		} else
			setPi(super.findAll(queryString + getCondi() + order,
					str2int(getPageNo()), str2int(getPageCount())));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		List list = super.findAll(queryString + "and id in (" + getIds()
				+ "0 )");
		for (Object obj : list)
			super.delete(obj);
		setResult(true);

	}

	@SuppressWarnings("unchecked")
	public void wareHousing() {

		//Boxnum bn = (Boxnum) super.findByID(" Boxnum ", 1L);

		List list = super.findAll(queryString + "and id in (" + getIds()
				+ "0 )");

		List list1 = new ArrayList();
		for (Object obj : list) {
			Prorepair prorepair = (Prorepair) obj;

			prorepair.setWaremark("4"); // 4表示已维修

			List t = super.findAll(" from Products where 1=1 and procode='"
					+ prorepair.getProcode() + "' ");

			Products pro = (Products) t.get(0);
			pro.setProstatus("0");

			pro.setRepairnum(pro.getRepairnum() + 1);
			pro.setBoxcode("");

			// 根据编码更新箱号
//			if (pro.getProcode().startsWith("0108")) {
//				long bnz = (long) Math.floor(bn.getBoxz() / 12);
//				pro.setBoxcode("Z" + bnz);
//				bn.setBoxz(bn.getBoxz() + 1);
//			} else {
//				long bnw = (long) Math.floor(bn.getBoxw() / 12);
//				pro.setBoxcode("Z" + bnw);
//				bn.setBoxw(bn.getBoxw() + 1);
//
//			}
			list1.add(pro);

		}

		list1.addAll(list);
		//list1.add(bn);
		super.uporsave(list1);

		this.setResult(true);

	}

	public String getBoxcode(String procode) {
		// TODO:自动生成箱号
		return "0000";
	}

	public String addnew() {
		try {

			// if(this.getCount(" from Products where 1=1 " + " and procode='"+
			// this.getProcode() ))

			Prorepair prorepair;

			if (getId().length() > 0) {
				prorepair = (Prorepair) super.findByID(" Prorepair ", Long
						.valueOf(str2int(getId())));

				prorepair.setErrorfor(this.getErrorfor());
				prorepair.setMemo(this.getMemo());

				super.update(prorepair);
				return null;
			} else {
				this.setRtntxt("错误，返修产品未找到");
				return SUCCESS;
			}

		} catch (Exception e) {
			setRtntxt("保存失败！" + "\n" + e.toString());
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

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public String getWaremark() {
		return waremark;
	}

	public void setWaremark(String waremark) {
		this.waremark = waremark;
	}

	/**
	 * @return the errorview
	 */
	public String getErrorview() {
		return errorview;
	}

	/**
	 * @param errorview
	 *            the errorview to set
	 */
	public void setErrorview(String errorview) {
		this.errorview = errorview;
	}

	public String getErrorfor() {
		return errorfor;
	}

	public void setErrorfor(String errorfor) {
		this.errorfor = errorfor;
	}

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public String getChangecode() {
		return changecode;
	}

	public void setChangecode(String changecode) {
		this.changecode = changecode;
	}

	public static String getQueryString() {
		return queryString;
	}

	public static void setQueryString(String queryString) {
		Repaired.queryString = queryString;
	}

	public static String getOrder() {
		return order;
	}

	public static void setOrder(String order) {
		Repaired.order = order;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
