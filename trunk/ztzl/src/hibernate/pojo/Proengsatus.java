package hibernate.pojo;

/**
 * Proengsatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Proengsatus extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private ProengsatusId id;

	// Constructors

	/** default constructor */
	public Proengsatus() {
	}

	/** full constructor */
	public Proengsatus(ProengsatusId id) {
		this.id = id;
	}

	// Property accessors

	public ProengsatusId getId() {
		return this.id;
	}

	public void setId(ProengsatusId id) {
		this.id = id;
	}

}