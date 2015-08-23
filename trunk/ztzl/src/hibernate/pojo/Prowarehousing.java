package hibernate.pojo;

/**
 * Prowarehousing entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Prowarehousing extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private ProwarehousingId id;

	// Constructors

	/** default constructor */
	public Prowarehousing() {
	}

	/** full constructor */
	public Prowarehousing(ProwarehousingId id) {
		this.id = id;
	}

	// Property accessors

	public ProwarehousingId getId() {
		return this.id;
	}

	public void setId(ProwarehousingId id) {
		this.id = id;
	}

}