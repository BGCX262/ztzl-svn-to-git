package hibernate.pojo;

/**
 * Producttype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Producttype extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String typename;
	private String functype;
	private String typeinfo;
	private String inuse;

	// Constructors

	/** default constructor */
	public Producttype() {
	}

	/** minimal constructor */
	public Producttype(String typename) {
		this.typename = typename;
	}

	/** full constructor */
	public Producttype(String typename, String functype, String typeinfo,
			String inuse) {
		this.typename = typename;
		this.functype = functype;
		this.typeinfo = typeinfo;
		this.inuse = inuse;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getFunctype() {
		return this.functype;
	}

	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public String getTypeinfo() {
		return this.typeinfo;
	}

	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}

	public String getInuse() {
		return this.inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

}