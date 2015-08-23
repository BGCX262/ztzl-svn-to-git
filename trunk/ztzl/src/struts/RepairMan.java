package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;
import hibernate.pojo.Prorepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepairMan extends BaseAction {

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
	private String memo;
	private Date changedate;
	private String changecode;
	private String area;

	private static String queryString = " from Prorepair where 1=1 and waremark='0' ";
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
			this.setRtntxt("运行错误！");
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
		List list = super.findAll(queryString + "and id in (" + getIds()
				+ "0 )");
		@SuppressWarnings("unused")
		List list1 = new ArrayList();
		for (Object obj : list) {
			Prorepair prorepair = (Prorepair) obj;

			prorepair.setWaremark("1");

			Proflow proFlow = new Proflow();

			//如果是现存产品，自动取产品类别 直接取填写类别
			List t = super.findAll(" from Products where procode='"
					+ prorepair.getProcode() + "'");
			if (t.size() > 0) {
				Products products = (Products) t.get(0);
				proFlow.setTypeid(products.getTypeid());
			} else		
			{
				//if(this.getTypeid()!=null)
				proFlow.setTypeid(Long.valueOf(prorepair.getTypeid()));
			}

			proFlow.setProcode(prorepair.getProcode());
			proFlow.setCurware("3"); // 当前所在,未入库前为1 表示生产部
			proFlow.setProsource("3"); // 1表示生产部
			proFlow.setSendinfo("送检");
			proFlow.setSourceoper(super.getSession().getAttribute("username")
					.toString());
			// proFlow.setSendoper(((Operators) (super.getSession()
			// .getAttribute("oper"))).getUsername());
			proFlow.setSendto("4");
			proFlow.setSourcedate(new Date());

			proFlow.setOvermark("0");
			

			list1.add(proFlow);

		}

		list1.addAll(list);
		super.uporsave(list1);

		this.setResult(true);

	}

	public String addnew() {
		try {

			// if(this.getCount(" from Products where 1=1 " + " and procode='"+
			// this.getProcode() ))

			Prorepair prorepair;

			if (getId().length() > 0) {
				prorepair = (Prorepair) super.findByID(" Prorepair ", Long
						.valueOf(str2int(getId())));
			} else {
				// 判断 procode 是否唯一
				prorepair = new Prorepair();
			}

			prorepair.setProcode(this.getProcode());
			prorepair.setSenddate(this.getSenddate());
			prorepair.setChangedate(this.getChangedate());
			prorepair.setTypeid(this.getTypeid());
			prorepair.setErrorview((long) str2int(this.getErrorview()));
			
			prorepair.setChangecode(this.getChangecode());			
			prorepair.setMemo(this.getMemo());
			prorepair.setArea(this.getArea());

			if (getId().length() > 0)
				super.update(prorepair);
			else
				super.save(prorepair);
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
		RepairMan.queryString = queryString;
	}

	public static String getOrder() {
		return order;
	}

	public static void setOrder(String order) {
		RepairMan.order = order;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
