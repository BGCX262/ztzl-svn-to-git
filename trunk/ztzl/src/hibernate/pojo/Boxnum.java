package hibernate.pojo;

/**
 * Boxnum entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Boxnum extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Long boxw;
	private Long boxz;

	// Constructors

	/** default constructor */
	public Boxnum() {
	}

	/** full constructor */
	public Boxnum(Long boxw, Long boxz) {
		this.boxw = boxw;
		this.boxz = boxz;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoxw() {
		return this.boxw;
	}

	public void setBoxw(Long boxw) {
		this.boxw = boxw;
	}

	public Long getBoxz() {
		return this.boxz;
	}

	public void setBoxz(Long boxz) {
		this.boxz = boxz;
	}

}