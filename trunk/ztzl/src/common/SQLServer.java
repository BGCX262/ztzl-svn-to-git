package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLServer {

	public Connection getConn(String ip, String dbname, String user, String pass)
			throws Exception {
		Connection conn = null;
		String connStr = "jdbc:sqlserver://" + ip + ":1433;databasename="
				+ dbname;

		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} catch (Exception e) {
			System.out.println("驱动类没有找到！");
			throw e;
		}

		try {
			conn = DriverManager.getConnection(connStr, user, pass);
		} catch (Exception e) {
			System.out.println("JDBC异常");
			throw e;
		}
		return conn;

	}

	@SuppressWarnings("finally")
	public PreparedStatement getPs(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (Exception e) {

		} finally {
			return ps;
		}

	}

	public ResultSet exeSql(PreparedStatement ps) {
		ResultSet rs = null;
		int num = 0;

		try {
			rs = ps.executeQuery();

		} catch (Exception e) {

		} finally {

		}
		return rs;
	}

	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		SQLServer ss = new SQLServer();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ss.getConn("192.168.1.201", "苏州", "sa", "sa");
			//集中器
			//ps = conn.prepareStatement("select linkphone,address,propertyid from concentrator where propertyid is not null ");
			//采集器
			String sql=" select distinct a.cjqbh,a.address,a.propertyid,c.areaname ";
			sql+=" from collector a,concentrator b,transformerarea c ";
			sql+=" where a.jzqbh=b.jzqbh and b.transformersn=c.areasn and a.propertyid is not null ";
			//ps = conn.prepareStatement("select cjqbh,address,propertyid from collector where propertyid is not null ");
			ps = conn.prepareStatement(sql);
			rs = ss.exeSql(ps);
			while (rs.next()) {
				System.out.print(rs.getString(1)+"   ");
				System.out.print(rs.getString(2)+"   ");
				System.out.print(rs.getString(3)+"    ");
				System.out.print(rs.getString(4)+"    ");
				System.out.println();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ss.close(rs, ps, conn);
		}

	}

}




