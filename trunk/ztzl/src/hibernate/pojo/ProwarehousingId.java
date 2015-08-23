package hibernate.pojo;

/**
 * ProwarehousingId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProwarehousingId extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long rn;
	private String ny;
	private Long typeid;
	private Long cn;

	// Constructors

	/** default constructor */
	public ProwarehousingId() {
	}

	/** full constructor */
	public ProwarehousingId(Long rn, String ny, Long typeid, Long cn) {
		this.rn = rn;
		this.ny = ny;
		this.typeid = typeid;
		this.cn = cn;
	}

	// Property accessors

	public Long getRn() {
		return this.rn;
	}

	public void setRn(Long rn) {
		this.rn = rn;
	}

	public String getNy() {
		return this.ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
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
		if (!(other instanceof ProwarehousingId))
			return false;
		ProwarehousingId castOther = (ProwarehousingId) other;

		return ((this.getRn() == castOther.getRn()) || (this.getRn() != null
				&& castOther.getRn() != null && this.getRn().equals(
				castOther.getRn())))
				&& ((this.getNy() == castOther.getNy()) || (this.getNy() != null
						&& castOther.getNy() != null && this.getNy().equals(
						castOther.getNy())))
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

		result = 37 * result + (getRn() == null ? 0 : this.getRn().hashCode());
		result = 37 * result + (getNy() == null ? 0 : this.getNy().hashCode());
		result = 37 * result
				+ (getTypeid() == null ? 0 : this.getTypeid().hashCode());
		result = 37 * result + (getCn() == null ? 0 : this.getCn().hashCode());
		return result;
	}

}