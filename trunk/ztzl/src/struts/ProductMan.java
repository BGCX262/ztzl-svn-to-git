package struts;

import hibernate.PageInfo;
import hibernate.pojo.Products;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductMan extends BaseAction {

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
	private String barcode;
	private String boxcode;
	private String lotnum;
	private String procode;
	private Date prodate;
	private String promemo;
	private String prostatus;
	private String proversion;
	private String rejectmark;
	private String repairnum;
	private String typeid;
	private String virtualmark;

	private static String queryString = " from Products where 1=1 ";
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
		} else if (getCommand().equals("reject")) {
			reject();
		} else
			setPi(super.findAll(queryString
					+ " and rejectmark='0' and prostatus ='0' " + getCondi()
					+ order, str2int(getPageNo()), str2int(getPageCount())));
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
	public void reject() {

		List list = super.findAll(queryString + "and id in (" + getIds()
				+ "0 )");
		for (Object obj : list) {
			Products pro = (Products) obj;
			pro.setRejectmark("1");

		}
		super.uporsave(list);
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
			proFlow.setBoxcode(products.getBoxcode());
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

			// if(this.getCount(" from Products where 1=1 " + " and procode='"+
			// this.getProcode() ))

			Products products;

			if (getId().length() > 0) {
				products = (Products) super.findByID(" Products ", Long
						.valueOf(str2int(getId())));
				if (!products.getProcode().trim().equals(this.getProcode().trim())) {
					setRtntxt("产品编码不可更改！");
					return SUCCESS;
				}
			} else {
				// 判断 procode 是否唯一
				if (super.getCount(queryString + " and procode ='"
						+ this.getProcode() + "' ") > 0) {
					setRtntxt("产品编码不可重复！");
					return SUCCESS;
				}

				products = new Products();
			}

			products.setBoxcode(this.getBoxcode());
			products.setBarcode(this.getBarcode());
			products.setLotnum(this.getLotnum());
			products.setProcode(this.getProcode());
			products.setProdate(this.getProdate());
			products.setPromemo(this.getPromemo());
			products.setProversion(this.getProversion());
			products.setTypeid((long) str2int(this.getTypeid()));

			if (getId().length() > 0)
				super.update(products);
			else
				super.save(products);
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBoxcode() {
		return boxcode;
	}

	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public Date getProdate() {
		return prodate;
	}

	public void setProdate(Date prodate) {
		this.prodate = prodate;
	}

	public String getPromemo() {
		return promemo;
	}

	public void setPromemo(String promemo) {
		this.promemo = promemo;
	}

	public String getProstatus() {
		return prostatus;
	}

	public void setProstatus(String prostatus) {
		this.prostatus = prostatus;
	}

	public String getProversion() {
		return proversion;
	}

	public void setProversion(String proversion) {
		this.proversion = proversion;
	}

	public String getRejectmark() {
		return rejectmark;
	}

	public void setRejectmark(String rejectmark) {
		this.rejectmark = rejectmark;
	}

	public String getRepairnum() {
		return repairnum;
	}

	public void setRepairnum(String repairnum) {
		this.repairnum = repairnum;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getVirtualmark() {
		return virtualmark;
	}

	public void setVirtualmark(String virtualmark) {
		this.virtualmark = virtualmark;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

}
