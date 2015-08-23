package hibernate.pojo;

/**
 * Sendarea entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Sendarea extends hibernate.pojo.BasePojo implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String sendarea;

	// Constructors

	/** default constructor */
	public Sendarea() {
	}

	/** full constructor */
	public Sendarea(String sendarea) {
		this.sendarea = sendarea;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSendarea() {
		return this.sendarea;
	}

	public void setSendarea(String sendarea) {
		this.sendarea = sendarea;
	}

}