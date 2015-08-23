package hibernate.pojo;

import java.util.Date;

/**
 * Proflow entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Proflow extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String procode;
	private Long typeid;
	private String curware;
	private String prosource;
	private String sourceoper;
	private Date sourcedate;
	private String sendinfo;
	private String sendto;
	private String sendarea;
	private String receiver;
	private Date receivedate;
	private String receiveinfo;
	private String overmark="0";
	private String returnmark="0";
	private String boxcode;
	private Long preid;
	// Constructors

	/** default constructor */
	public Proflow() {
	}

	/** minimal constructor */
	public Proflow(String procode) {
		this.procode = procode;
	}

	/** full constructor */
	public Proflow(String procode, Long typeid, String curware,
			String prosource, String sourceoper, Date sourcedate,
			String sendinfo, String sendto, String sendarea, String receiver,
			Date receivedate, String receiveinfo, String overmark,
			String returnmark, String boxcode) {
		this.procode = procode;
		this.typeid = typeid;
		this.curware = curware;
		this.prosource = prosource;
		this.sourceoper = sourceoper;
		this.sourcedate = sourcedate;
		this.sendinfo = sendinfo;
		this.sendto = sendto;
		this.sendarea = sendarea;
		this.receiver = receiver;
		this.receivedate = receivedate;
		this.receiveinfo = receiveinfo;
		this.overmark = overmark;
		this.returnmark = returnmark;
		this.boxcode = boxcode;
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

	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public String getCurware() {
		return this.curware;
	}

	public void setCurware(String curware) {
		this.curware = curware;
	}

	public String getProsource() {
		return this.prosource;
	}

	public void setProsource(String prosource) {
		this.prosource = prosource;
	}

	public String getSourceoper() {
		return this.sourceoper;
	}

	public void setSourceoper(String sourceoper) {
		this.sourceoper = sourceoper;
	}

	public Date getSourcedate() {
		return this.sourcedate;
	}

	public void setSourcedate(Date sourcedate) {
		this.sourcedate = sourcedate;
	}

	public String getSendinfo() {
		return this.sendinfo;
	}

	public void setSendinfo(String sendinfo) {
		this.sendinfo = sendinfo;
	}

	public String getSendto() {
		return this.sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getSendarea() {
		return this.sendarea;
	}

	public void setSendarea(String sendarea) {
		this.sendarea = sendarea;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Date getReceivedate() {
		return this.receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public String getReceiveinfo() {
		return this.receiveinfo;
	}

	public void setReceiveinfo(String receiveinfo) {
		this.receiveinfo = receiveinfo;
	}

	public String getOvermark() {
		return this.overmark;
	}

	public void setOvermark(String overmark) {
		this.overmark = overmark;
	}

	public String getReturnmark() {
		return this.returnmark;
	}

	public void setReturnmark(String returnmark) {
		this.returnmark = returnmark;
	}

	public String getBoxcode() {
		return this.boxcode;
	}

	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

	public Long getPreid() {
		return preid;
	}

	public void setPreid(Long preid) {
		this.preid = preid;
	}

}