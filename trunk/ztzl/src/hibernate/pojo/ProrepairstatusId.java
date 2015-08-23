package hibernate.pojo;

/**
 * ProrepairstatusId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProrepairstatusId extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private String ny;
	private String area;
	private String typeid;
	private Long cn;

	// Constructors

	/** default constructor */
	public ProrepairstatusId() {
	}

	/** full constructor */
	public ProrepairstatusId(String ny, String area, String typeid, Long cn) {
		this.ny = ny;
		this.area = area;
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

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
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
		if (!(other instanceof ProrepairstatusId))
			return false;
		ProrepairstatusId castOther = (ProrepairstatusId) other;

		return ((this.getNy() == castOther.getNy()) || (this.getNy() != null
				&& castOther.getNy() != null && this.getNy().equals(
				castOther.getNy())))
				&& ((this.getArea() == castOther.getArea()) || (this.getArea() != null
						&& castOther.getArea() != null && this.getArea()
						.equals(castOther.getArea())))
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
				+ (getArea() == null ? 0 : this.getArea().hashCode());
		result = 37 * result
				+ (getTypeid() == null ? 0 : this.getTypeid().hashCode());
		result = 37 * result + (getCn() == null ? 0 : this.getCn().hashCode());
		return result;
	}

}