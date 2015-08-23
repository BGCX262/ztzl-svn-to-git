package struts;


import hibernate.pojo.Pris;

import java.util.ArrayList;
import java.util.List;

public class TreeTest extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3113667246176203438L;

	public String id;
	
	public boolean notlog;
	
	@SuppressWarnings("unchecked")
	public List tl=new ArrayList();
	
	public String execute()
	{
		return exe();
	}
	
	@SuppressWarnings("unchecked")
	public String exe()
	{
//		int treeId=Integer.parseInt(getId());
//		if(treeId<1)
//			treeId=5;
//		for(int i=1;i<treeId;i++){
//		TreeNode t=new TreeNode();
//		t.setId(Integer.toString(i));
//		t.setName(Integer.toString(i));
//		t.setLeaf(treeId>9);
//		tl.add(t);
//		}
		
//		String hql= " from Pris where 1=1 ";
//		hql+= " and uppriid = " + getId();
//		hql+=" order by prigrade ";
		
		if(getSession().getAttribute("username")==null)
		{
			this.setNotlog(true);
			return SUCCESS;
		}
		
		
		String hql=" select distinct d from  Operatorrole b, Rolepri c, Pris d ";
		hql+= " where 1=1  and b.roleid=c.roleid and c.priid=d.id ";
		hql+= " and b.userid= " +  getSession().getAttribute("userid") ;
		hql+= " and d.uppriid =" + getId() ;
		hql+= " order by prigrade ";
		
		List list=super.findAll(hql);
		
		
		if(list.size()>0)
		for(int i=0;i<list.size();i++)
		{
			TreeNode t=new TreeNode();
			Pris pri=(Pris)list.get(i);
			t.setId(pri.getId().toString());
			//t.setName(pri.getPriurl());
			t.setName(pri.getPriname());
			if(pri.getOpentype().equals("1"))
				t.setLeaf(true);
			else 
				t.setLeaf(false);
				
			tl.add(t);
			
		}
	
		return "success";
	}
	
	

	public String getId() {		
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public List getTl() {
		return tl;
	}

	@SuppressWarnings("unchecked")
	public void setTl(List tl) {
		this.tl = tl;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

	
	
	
	

}
