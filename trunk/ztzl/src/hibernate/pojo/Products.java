package hibernate.pojo;

import java.util.Date;

/**
 * Products entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Products extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String procode;
	private Date prodate;
	private String barcode;
	private String lotnum;
	private String boxcode;
	private Long typeid;
	private String proversion;
	private String promemo;
	private String virtualmark="0";
	private Long repairnum=0L;
	private String rejectmark="0";
	private String prostatus="0";

	// Constructors

	/** default constructor */
	public Products() {
	}

	/** minimal constructor */
	public Products(String procode) {
		this.procode = procode;
	}

	/** full constructor */
	public Products(String procode, Date prodate, String barcode,
			String lotnum, String boxcode, Long typeid, String proversion,
			String promemo, String virtualmark, Long repairnum,
			String rejectmark, String prostatus) {
		this.procode = procode;
		this.prodate = prodate;
		this.barcode = barcode;
		this.lotnum = lotnum;
		this.boxcode = boxcode;
		this.typeid = typeid;
		this.proversion = proversion;
		this.promemo = promemo;
		this.virtualmark = virtualmark;
		this.repairnum = repairnum;
		this.rejectmark = rejectmark;
		this.prostatus = prostatus;
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

	public Date getProdate() {
		return this.prodate;
	}

	public void setProdate(Date prodate) {
		this.prodate = prodate;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getLotnum() {
		return this.lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getBoxcode() {
		return this.boxcode;
	}

	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public String getProversion() {
		return this.proversion;
	}

	public void setProversion(String proversion) {
		this.proversion = proversion;
	}

	public String getPromemo() {
		return this.promemo;
	}

	public void setPromemo(String promemo) {
		this.promemo = promemo;
	}

	public String getVirtualmark() {
		return this.virtualmark;
	}

	public void setVirtualmark(String virtualmark) {
		this.virtualmark = virtualmark;
	}

	public Long getRepairnum() {
		return this.repairnum;
	}

	public void setRepairnum(Long repairnum) {
		this.repairnum = repairnum;
	}

	public String getRejectmark() {
		return this.rejectmark;
	}

	public void setRejectmark(String rejectmark) {
		this.rejectmark = rejectmark;
	}

	public String getProstatus() {
		return this.prostatus;
	}

	public void setProstatus(String prostatus) {
		this.prostatus = prostatus;
	}

}