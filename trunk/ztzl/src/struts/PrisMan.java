package struts;

import hibernate.PageInfo;
import hibernate.pojo.Operators;
import hibernate.pojo.Pris;

import java.util.List;

public class PrisMan  extends BaseAction {

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
	private String priname;
	private String prigrade;
	private String opentype;
	private String priurl;
	private Long uppriid;
	private String priinfo;
	private static String queryString=" from Pris where 1=1 ";
	private static String order=" order by id desc";
	


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
		} else if (getCommand().equals("qone")) {
			setPi(super.findAll(queryString + getCondi(), 1, 100));
		}else
			setPi(super.findAll(queryString + getCondi()+ order,
					str2int(getPageNo()), str2int(getPageCount())));
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
			
			Pris pris;

			if (getId().length() > 0) {
				pris = (Pris) super.findByID("Pris",
						Long.valueOf(str2int(getId())));
			} else
			{
				pris = new Pris();
				int i=super.getCount("from Pris where 1=1 and priname='"+ this.getPriname() +"'");
				if(i>0)
				{
					this.setRtntxt("权限名不能重复！");
				}
			}
			
			
			pris.setOpentype(this.getOpentype());
			pris.setOpentype(this.getPrigrade());
			pris.setPriinfo(this.getPriinfo());
			pris.setPriname(this.getPriname());
			pris.setPriurl(this.getPriurl());
			pris.setUppriid(this.getUppriid());
			
			

			if (getId().length() > 0)
				super.update(pris);
			else
				super.save(pris);
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

	

	/**
	 * @return the priname
	 */
	public String getPriname() {
		return priname;
	}

	/**
	 * @param priname the priname to set
	 */
	public void setPriname(String priname) {
		this.priname = priname;
	}

	/**
	 * @return the prigrade
	 */
	public String getPrigrade() {
		return prigrade;
	}

	/**
	 * @param prigrade the prigrade to set
	 */
	public void setPrigrade(String prigrade) {
		this.prigrade = prigrade;
	}

	/**
	 * @return the opentype
	 */
	public String getOpentype() {
		return opentype;
	}

	/**
	 * @param opentype the opentype to set
	 */
	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}

	/**
	 * @return the priurl
	 */
	public String getPriurl() {
		return priurl;
	}

	/**
	 * @param priurl the priurl to set
	 */
	public void setPriurl(String priurl) {
		this.priurl = priurl;
	}

	/**
	 * @return the uppriid
	 */
	public Long getUppriid() {
		return uppriid;
	}

	/**
	 * @param uppriid the uppriid to set
	 */
	public void setUppriid(Long uppriid) {
		this.uppriid = uppriid;
	}

	/**
	 * @return the priinfo
	 */
	public String getPriinfo() {
		return priinfo;
	}

	/**
	 * @param priinfo the priinfo to set
	 */
	public void setPriinfo(String priinfo) {
		this.priinfo = priinfo;
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
