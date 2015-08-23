package hibernate.pojo;

/**
 * Procarry entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Procarry extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private ProcarryId id;

	// Constructors

	/** default constructor */
	public Procarry() {
	}

	/** full constructor */
	public Procarry(ProcarryId id) {
		this.id = id;
	}

	// Property accessors

	public ProcarryId getId() {
		return this.id;
	}

	public void setId(ProcarryId id) {
		this.id = id;
	}

}