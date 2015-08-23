package struts.query;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proeng;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import struts.BaseAction;

public class AreaEngQuery extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7663157097338529375L;
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
	private String proname;
	private Long typeid;
	private Date prodate;
	private String engarea;
	private String areaname;
	private String engaddr;
	private String principle;
	private String runstatus;
	private String inuse;

	private static String queryString = " from Proeng where 1=1 ";
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
		
		String userarea=getSession().getAttribute("userarea").toString().trim();
		String queryString1=AreaEngQuery.queryString + " and engarea= '"+ userarea +"' ";

		if (getCommand() == null)
			setCommand("");

		if (getCommand().equals("dele"))
			delete();
		else if (getCommand().equals("add")) {
			return addnew();
		} else if (getCommand().equals("qone")) {
			setPi(super.findAll(queryString1 + getCondi(), 1, 100));
		} else
			setPi(super.findAll(queryString1 + getCondi()+ order, str2int(getPageNo()),
					str2int(getPageCount())));
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
		List list = super.findAll(queryString + "and id in (" + getIds()
				+ "0 )");
		@SuppressWarnings("unused")
		List list1 = new ArrayList();
		for (Object obj : list) {
			Products products = (Products) obj;
			products.setProstatus("1");

			Proflow proFlow = new Proflow();

			proFlow.setProcode(products.getProcode());
			proFlow.setTypeid(products.getTypeid());
			proFlow.setCurware("1"); // 当前所在,未入库前为1 表示生产部
			proFlow.setProsource("1"); // 1表示生产部
			proFlow.setSendinfo("入库");
			proFlow.setSourceoper(super.getSession().getAttribute("username")
					.toString());
			// proFlow.setSendoper(((Operators) (super.getSession()
			// .getAttribute("oper"))).getUsername());

			proFlow.setSendto("2");
			proFlow.setSourcedate(new Date());

			proFlow.setOvermark("0");

			list1.add(proFlow);

		}

		super.save(list1);
		super.uporsave(list);

		this.setResult(true);

	}

	public String addnew() {
		try {
			// 判断该产品是否存在
			// if(super.getCount(" from Products where 1=1 and prostatus ='1' "
			// + " and procode='"+
			// this.getProcode()+ "' " )<1)
			String sql = " from Proflow where 1=1 and prosource='2' and sendto='3'  "
					+ " and procode='" + this.getProcode() + "' ";
			if (super.getCount(sql) < 1) {
				this.setRtntxt("该产品在系统中不存在.请检查或录入产品信息!");
				this.setResult(false);
				return SUCCESS;
			}

			Proeng proeng;

			if (getId().length() > 0) {
				proeng = (Proeng) super.findByID(" Products ", Long
						.valueOf(str2int(getId())));
			} else {
				// 判断 procode 是否唯一
				if (super.getCount(queryString + " and procode ='"
						+ this.getProcode() + "' ") > 0) {
					setRtntxt("产品编码不可重复！");
					return SUCCESS;
				}

				proeng = new Proeng();
			}

			proeng.setProcode(this.getProcode());
			proeng.setProdate(this.getProdate());
			proeng.setProname(this.getProname());
			proeng.setRunstatus(this.getRunstatus());
			proeng.setTypeid(this.getTypeid());
			proeng.setAreaname(this.getAreaname());
			proeng.setEngaddr(this.getEngaddr());
			proeng.setEngarea(this.getEngarea());
			proeng.setPrinciple(this.getPrinciple());
			if (getId().length() > 0)
				super.update(proeng);
			else
				super.save(proeng);
			return null;
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

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public Long getTypeid() {
		return typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public Date getProdate() {
		return prodate;
	}

	public void setProdate(Date prodate) {
		this.prodate = prodate;
	}

	public String getEngarea() {
		return engarea;
	}

	public void setEngarea(String engarea) {
		this.engarea = engarea;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getEngaddr() {
		return engaddr;
	}

	public void setEngaddr(String engaddr) {
		this.engaddr = engaddr;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public String getRunstatus() {
		return runstatus;
	}

	public void setRunstatus(String runstatus) {
		this.runstatus = runstatus;
	}

	public String getInuse() {
		return inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

	public static String getQueryString() {
		return queryString;
	}

	public static void setQueryString(String queryString) {
		AreaEngQuery.queryString = queryString;
	}

	public static String getOrder() {
		return order;
	}

	public static void setOrder(String order) {
		AreaEngQuery.order = order;
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
