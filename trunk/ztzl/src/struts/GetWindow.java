package struts;

public class GetWindow extends BaseAction{
	
	private Object pris;
	private String id;
	
	//程序入口
	public String execute()
	{
		pris=super.findByID("Pris", Long.valueOf(getId()));
		return SUCCESS;
	}

	public Object getPris() {
		return pris;
	}

	public void setPris(Object pris) {
		this.pris = pris;
	}

	public String getId() {
		if(id==null)
			return "0";
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
