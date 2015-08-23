package struts;

import hibernate.pojo.Proeng;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.SQLServer;

public class Imp400 extends BaseAction {

	public String sendarea;
	public String principle;
	public Date engdate;
	public String username;
	public String userpass;
	public String dataip;
	public String dataname;

	public boolean notlog;

	public String result;

	public String execute() {
		try {
			// TODO:判断Session
			if (getSession().getAttribute("username") == null) {
				setNotlog(true);
				return SUCCESS;
			}
			//由于导入过程比较长，设置超时为30分钟。
			getSession().setMaxInactiveInterval(1800);
			// 执行请求
			return exe();
		} catch (Exception e) {
			this.setResult("导入数据失败");
			return SUCCESS;
		}
	}

	public String exe() {
		List<Proeng> list = new ArrayList<Proeng>();
		SQLServer ss = new SQLServer();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ss.getConn(this.getDataip(), this.getDataname(), this
					.getUsername(), this.getUserpass());

		} catch (Exception e) {
			setResult("连接数据库错误");
			return SUCCESS;
		}

		// 处理集中器
		String jzqsql = "select distinct linkphone,address,propertyid,transformersn from concentrator "; ///where propertyid is not null 
		try {
			ps = conn.prepareStatement(jzqsql);
			rs = ss.exeSql(ps);
			while (rs.next()) {
				String procode = rs.getString(3).trim();
//				if (procode == null || procode.equals(""))
//					continue;
				Proeng proeng = new Proeng();
				proeng.setPrinciple(this.getPrinciple());
				proeng.setProdate(this.getEngdate());
				proeng.setEngarea(this.getSendarea());
				proeng.setEngaddr(rs.getString(2));
				proeng.setProcode(procode);
				proeng.setRunstatus("1");
				proeng.setProname(rs.getString(4));
//				 if (rs.getString(1).substring(1, 2).equals("1"))
				 proeng.setTypeid(107L); // 设置为无线集中器
//				 else
//				 proeng.setTypeid(106L);
				
//				if (procode == null || procode.equals(""))
//					;
//				else
					list.add(proeng);
			}
		} catch (Exception e) {
			this.setResult("处理集中器数据失败！");
			return SUCCESS;
		}

		// 处理采集器
		String sql = " select distinct a.cjqbh,a.address,a.propertyid,c.areaname,b.transformersn ";
		sql += " from collector a,concentrator b,transformerarea c ";
		sql += " where a.ConcenSN=b.ConcenSN and b.transformersn=c.areasn  "; //and a.propertyid is not null
		try {
			// ps = conn.prepareStatement("select cjqbh,address,propertyid from
			// collector where propertyid is not null ");
			ps = conn.prepareStatement(sql);
			rs = ss.exeSql(ps);
			while (rs.next()) {
				String procode = rs.getString(3);
				
				//如果产品编码不符合要求，设置为111111111
				
				Proeng proeng = new Proeng();
				proeng.setPrinciple(this.getPrinciple());
				proeng.setEngarea(this.getSendarea());
				proeng.setProdate(this.getEngdate());
				proeng.setAreaname(rs.getString(4));
				proeng.setEngaddr(rs.getString(2));
				proeng.setProcode(procode);
				proeng.setRunstatus("1");
				proeng.setProname(rs.getString(5));
				
				 proeng.setTypeid(108L); // 设置为采集器
				
				// TODO: 添加资产编码的有效性判断。 无效编码存入无效资产表
				
					list.add(proeng);
			}
		} catch (Exception e) {
			this.setResult("处理采集器数据失败！");
			return SUCCESS;
		}

		ss.close(rs, ps, conn);
		DealProeng dealEng = new DealProeng();
		if (dealEng.dealEng(this, list) < 0) {
			this.setResult("保存数据时失败！");
			return SUCCESS;
		}

		this.setResult("导入成功！");
		return null;
	}

	public String getSendarea() {
		return sendarea;
	}

	public void setSendarea(String sendarea) {
		this.sendarea = sendarea;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public Date getEngdate() {
		return engdate;
	}

	public void setEngdate(Date engdate) {
		this.engdate = engdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getDataip() {
		return dataip;
	}

	public void setDataip(String dataip) {
		this.dataip = dataip;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
