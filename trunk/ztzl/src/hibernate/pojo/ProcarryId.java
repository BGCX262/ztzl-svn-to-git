package hibernate.pojo;

/**
 * ProcarryId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProcarryId extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private String ny;
	private String sendarea;
	private Long typeid;
	private Long cn;

	// Constructors

	/** default constructor */
	public ProcarryId() {
	}

	/** full constructor */
	public ProcarryId(String ny, String sendarea, Long typeid, Long cn) {
		this.ny = ny;
		this.sendarea = sendarea;
		this.typeid = typeid;
		this.cn = cn;
	}

	// Property accessors

	public String getNy() {
		return this.ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
	}

	public String getSendarea() {
		return this.sendarea;
	}

	public void setSendarea(String sendarea) {
		this.sendarea = sendarea;
	}

	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public Long getCn() {
		return this.cn;
	}

	public void setCn(Long cn) {
		this.cn = cn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProcarryId))
			return false;
		ProcarryId castOther = (ProcarryId) other;

		return ((this.getNy() == castOther.getNy()) || (this.getNy() != null
				&& castOther.getNy() != null && this.getNy().equals(
				castOther.getNy())))
				&& ((this.getSendarea() == castOther.getSendarea()) || (this
						.getSendarea() != null
						&& castOther.getSendarea() != null && this
						.getSendarea().equals(castOther.getSendarea())))
				&& ((this.getTypeid() == castOther.getTypeid()) || (this
						.getTypeid() != null
						&& castOther.getTypeid() != null && this.getTypeid()
						.equals(castOther.getTypeid())))
				&& ((this.getCn() == castOther.getCn()) || (this.getCn() != null
						&& castOther.getCn() != null && this.getCn().equals(
						castOther.getCn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getNy() == null ? 0 : this.getNy().hashCode());
		result = 37 * result
				+ (getSendarea() == null ? 0 : this.getSendarea().hashCode());
		result = 37 * result
				+ (getTypeid() == null ? 0 : this.getTypeid().hashCode());
		result = 37 * result + (getCn() == null ? 0 : this.getCn().hashCode());
		return result;
	}

}