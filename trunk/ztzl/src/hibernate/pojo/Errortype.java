package hibernate.pojo;

/**
 * Errortype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Errortype extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String errorview;
	private String errotype;
	private String errorinfo;
	private String inuse="1";

	// Constructors

	/** default constructor */
	public Errortype() {
	}

	/** minimal constructor */
	public Errortype(String errorview) {
		this.errorview = errorview;
	}

	/** full constructor */
	public Errortype(String errorview, String errotype, String errorinfo,
			String inuse) {
		this.errorview = errorview;
		this.errotype = errotype;
		this.errorinfo = errorinfo;
		this.inuse = inuse;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrorview() {
		return this.errorview;
	}

	public void setErrorview(String errorview) {
		this.errorview = errorview;
	}

	public String getErrotype() {
		return this.errotype;
	}

	public void setErrotype(String errotype) {
		this.errotype = errotype;
	}

	public String getErrorinfo() {
		return this.errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public String getInuse() {
		return this.inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

}