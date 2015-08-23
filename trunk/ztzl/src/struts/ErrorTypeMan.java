package struts;

import hibernate.PageInfo;
import hibernate.pojo.Errortype;
import hibernate.pojo.Operators;

import java.util.List;

import common.MD5Sec;

public class ErrorTypeMan extends BaseAction {

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
	private String errorinfo;
	private String errotype;
	private String errorview;
	private String inuse;

	private static String queryString = " from Errortype where 1=1 ";
	private static String order = " order by id desc";

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
			setPi(super.findAll(queryString + getCondi()+ order, str2int(getPageNo()),
					str2int(getPageCount())));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		
		List list = super.findAll(queryString+"and id in (" + getIds() + "0 )");
		for (Object obj : list)
			super.delete(obj);
		setResult(true);

	}

	public String addnew() {
		try {

			Errortype errortype;

			if (getId().length() > 0) {
				errortype = (Errortype) super.findByID("Errortype", Long
						.valueOf(str2int(getId())));
			} else
				errortype = new Errortype();

			errortype.setErrotype(this.getErrotype());
			errortype.setErrorview(this.getErrorview());
			errortype.setErrorinfo(this.getErrorinfo());
			errortype.setInuse(this.getInuse());

			if (getId().length() > 0)
				super.update(errortype);
			else
				super.save(errortype);
			return null;
		} catch (Exception e) {
			setRtntxt("保存失败！");
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

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public String getErrotype() {
		return errotype;
	}

	public void setErrotype(String errotype) {
		this.errotype = errotype;
	}

	public String getErrorview() {
		return errorview;
	}

	public void setErrorview(String errorview) {
		this.errorview = errorview;
	}

	public String getInuse() {
		return inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

}
