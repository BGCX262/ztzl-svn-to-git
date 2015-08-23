package hibernate.pojo;

import java.util.Date;

/**
 * Prorepair entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Prorepair extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String procode;
	private String typeid;
	private Long errorview;
	private Date senddate;
	private Date changedate;
	private String errorfor;
	private String changecode;
	private String memo;
	private String area;
	private String waremark="0";
	private String inuse="1";

	// Constructors

	/** default constructor */
	public Prorepair() {
	}

	/** minimal constructor */
	public Prorepair(String procode) {
		this.procode = procode;
	}

	/** full constructor */
	public Prorepair(String procode, String typeid, Long errorview,
			Date senddate, Date changedate, String errorfor, String changecode,
			String waremark, String inuse) {
		this.procode = procode;
		this.typeid = typeid;
		this.errorview = errorview;
		this.senddate = senddate;
		this.changedate = changedate;
		this.errorfor = errorfor;
		this.changecode = changecode;
		this.waremark = waremark;
		this.inuse = inuse;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcode() {
		return this.procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public Long getErrorview() {
		return this.errorview;
	}

	public void setErrorview(Long errorview) {
		this.errorview = errorview;
	}

	public Date getSenddate() {
		return this.senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public Date getChangedate() {
		return this.changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public String getErrorfor() {
		return this.errorfor;
	}

	public void setErrorfor(String errorfor) {
		this.errorfor = errorfor;
	}

	public String getChangecode() {
		return this.changecode;
	}

	public void setChangecode(String changecode) {
		this.changecode = changecode;
	}

	public String getWaremark() {
		return this.waremark;
	}

	public void setWaremark(String waremark) {
		this.waremark = waremark;
	}

	public String getInuse() {
		return this.inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
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