package hibernate.pojo;

/**
 * ProengsatusId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProengsatusId extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private String ny;
	private String engarea;
	private Long typeid;
	private Long cn;

	// Constructors

	/** default constructor */
	public ProengsatusId() {
	}

	/** full constructor */
	public ProengsatusId(String ny, String engarea, Long typeid, Long cn) {
		this.ny = ny;
		this.engarea = engarea;
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

	public String getEngarea() {
		return this.engarea;
	}

	public void setEngarea(String engarea) {
		this.engarea = engarea;
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
		if (!(other instanceof ProengsatusId))
			return false;
		ProengsatusId castOther = (ProengsatusId) other;

		return ((this.getNy() == castOther.getNy()) || (this.getNy() != null
				&& castOther.getNy() != null && this.getNy().equals(
				castOther.getNy())))
				&& ((this.getEngarea() == castOther.getEngarea()) || (this
						.getEngarea() != null
						&& castOther.getEngarea() != null && this.getEngarea()
						.equals(castOther.getEngarea())))
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
				+ (getEngarea() == null ? 0 : this.getEngarea().hashCode());
		result = 37 * result
				+ (getTypeid() == null ? 0 : this.getTypeid().hashCode());
		result = 37 * result + (getCn() == null ? 0 : this.getCn().hashCode());
		return result;
	}

}