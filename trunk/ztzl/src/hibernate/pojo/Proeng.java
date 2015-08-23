package hibernate.pojo;

import java.util.Date;

/**
 * Proeng entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Proeng extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String procode;
	private String proname;
	private Long typeid;
	private Date prodate;
	private String engarea;
	private String areaname;
	private String engaddr;
	private String principle;
	private String runstatus="0";
	private String inuse="1";

	// Constructors

	/** default constructor */
	public Proeng() {
	}

	/** minimal constructor */
	public Proeng(String procode) {
		this.procode = procode;
	}

	/** full constructor */
	public Proeng(String procode, String proname, Long typeid, Date prodate,
			String engarea, String areaname, String engaddr, String principle,
			String runstatus, String inuse) {
		this.procode = procode;
		this.proname = proname;
		this.typeid = typeid;
		this.prodate = prodate;
		this.engarea = engarea;
		this.areaname = areaname;
		this.engaddr = engaddr;
		this.principle = principle;
		this.runstatus = runstatus;
		this.inuse = inuse;
	}
	
	public Proeng(Proeng pe)
	{
		this.procode = pe.getProcode();
		this.proname = pe.getProname();
		this.typeid = pe.getTypeid();
		this.prodate = pe.getProdate();
		this.engarea = pe.getEngarea();
		this.areaname = pe.getAreaname();
		this.engaddr = pe.getEngaddr();
		this.principle = pe.getPrinciple();
		this.runstatus = pe.getRunstatus();
		this.inuse = pe.getInuse();
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

	public String getProname() {
		return this.proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public Date getProdate() {
		return this.prodate;
	}

	public void setProdate(Date prodate) {
		this.prodate = prodate;
	}

	public String getEngarea() {
		return this.engarea;
	}

	public void setEngarea(String engarea) {
		this.engarea = engarea;
	}

	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getEngaddr() {
		return this.engaddr;
	}

	public void setEngaddr(String engaddr) {
		this.engaddr = engaddr;
	}

	public String getPrinciple() {
		return this.principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public String getRunstatus() {
		return this.runstatus;
	}

	public void setRunstatus(String runstatus) {
		this.runstatus = runstatus;
	}

	public String getInuse() {
		return this.inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

}