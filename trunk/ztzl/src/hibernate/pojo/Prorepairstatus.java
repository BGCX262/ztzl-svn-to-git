package hibernate.pojo;

/**
 * Prorepairstatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Prorepairstatus extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private ProrepairstatusId id;

	// Constructors

	/** default constructor */
	public Prorepairstatus() {
	}

	/** full constructor */
	public Prorepairstatus(ProrepairstatusId id) {
		this.id = id;
	}

	// Property accessors

	public ProrepairstatusId getId() {
		return this.id;
	}

	public void setId(ProrepairstatusId id) {
		this.id = id;
	}

}