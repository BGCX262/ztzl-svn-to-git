package struts;

import hibernate.PageInfo;
import hibernate.pojo.Producttype;

import java.util.List;

import common.MD5Sec;

public class ProducttypeMan extends BaseAction {

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
	private String typename;
	private String functype;
	private String typeinfo;
	private String inuse;

	public static String queryString = " from Producttype where 1=1 ";
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

		if (getCommand().equals("dele"))
			delete();
		else if (getCommand().equals("add")) {
			return addnew();

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

	public String addnew() {
		try {
			Producttype producttype;

			if (getId().length() > 0) {
				producttype = (Producttype) super.findByID("Producttype", Long
						.valueOf(str2int(getId())));
			} else
				producttype = new Producttype();

			producttype.setFunctype(this.getFunctype());
			producttype.setTypename(this.getTypename());
			producttype.setTypeinfo(this.getTypeinfo());
			producttype.setInuse(this.getInuse());

			if (getId().length() > 0)
				super.update(producttype);
			else
				super.save(producttype);
			return null;
		} catch (Exception e) {
			setRtntxt("保存失败！");
			// 返回一个错误信息
			return SUCCESS;
		}
	}

	// getters and setters
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

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getFunctype() {
		return functype;
	}

	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public String getTypeinfo() {
		return typeinfo;
	}

	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}

	public String getInuse() {
		return inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

}
