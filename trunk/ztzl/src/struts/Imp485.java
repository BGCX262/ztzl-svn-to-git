package struts;

import hibernate.pojo.Proeng;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class Imp485 extends BaseAction {

	public String rtntxt;

	public String uploadFileName;
	public File upload;
	private String uploadContentType;
	private String savePath = ".";
	public boolean notlog;

	private String sendarea;
	private String principle;
	private Date engdate;

	private static final int BUFFER_SIZE = 16 * 1024;

	public String execute() {
		try {
			// TODO:判断Session
			if (getSession().getAttribute("username") == null) {
				setNotlog(true);
				getResponse().setContentType("text/html; charset=utf-8");
				return SUCCESS;
			}
			// 由于导入过程比较长，设置超时为30分钟。
			getSession().setMaxInactiveInterval(1800);
			// 执行请求
			return exe();
		} catch (Exception e) {

			return SUCCESS;
		}
	}

	/**
	 * @return the notlog
	 */
	public boolean isNotlog() {
		return notlog;
	}

	/**
	 * @param notlog
	 *            the notlog to set
	 */
	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

	public String exe() {
		// String contentType=this.getUploadContentType();
		HttpServletResponse response = getResponse();

		if (getUpload() == null) {
			procExcept("文件名不能为空！");
			return null;
		}

		if (!getUploadContentType().equals("application/octet-stream")) {
			procExcept("该文件不是paradox数据库文件！");
			return null; // 如果使用json返回类型会出现提示文件下载。
		}

		// 处理excel文件
		int result = processFile();

		switch (result) {
		case 0:
			break;
		case -1:
			procExcept("文件名请选择fenji.db 或zhongji.db！");
			break;
		case -2:
			procExcept("数据库错误！请检查文件数据");
			break;
		case -3:
			procExcept("保存数据时失败！请检查文件数据");
			break;
		default:
			procExcept("第" + result + "行数据有重复或出现错误！");
			break;
		}

		response.setContentType("text/html; charset=utf-8");
		return null;

	}

	public void procExcept(String msg) {
		HttpServletResponse response = getResponse();
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write("{rtntxt:'" + msg + "'}");
		} catch (Exception e) {

		}
	}

	/**
	 * 处理上传EXCEL 转换为对象
	 * 
	 * @return 处理成功返回 true 异常 false
	 */
	public int processFile() {
		try {
			Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

			String url = "jdbc:Paradox:///";

			String dstPath = ServletActionContext.getServletContext()
					.getRealPath(this.getSavePath());
			File dstFile = new File(dstPath + "\\" + this.getUploadFileName());
			// 复制数据库文件
			copy(this.getUpload(), dstFile);
			// 连接到数据库文件
			Connection con = DriverManager.getConnection(url + dstPath, "", "");
			Statement stmt = con.createStatement();
			stmt.setFetchSize(10);

			String dbname = this.getUploadFileName();
			List<Proeng> list;

			// String s=dbname.toLowerCase();

			if (dbname.toLowerCase().equals("fenji.db"))
				list = processterminal(stmt);
			else if (dbname.toLowerCase().equals("zhongji.db"))
				list = processzhongji(stmt);
			else
				return -1;

			if (new DealProeng().dealEng(this, list) < 0)
				return -3;

			// String sql = "select distinct ZDBH,ZDDZ from "
			// + this.getUploadFileName().replaceAll(".DB", " ") +"where ZDBH is
			// not null";
			//
			//			
			//
			// ResultSet rs = stmt.executeQuery(sql);
			//			
			//			
			//			
			// ResultSetMetaData resultSetMetaData = rs.getMetaData();
			// int iNumCols = resultSetMetaData.getColumnCount();
			// for (int i = 1; i <= iNumCols; i++) {
			// System.out.println(resultSetMetaData.getColumnLabel(i)
			// + " " +
			// resultSetMetaData.getColumnTypeName(i));
			// }
			// //
			// Object colval;
			// while (rs.next()) {
			// for (int i = 1; i <= iNumCols; i++) {
			// colval = rs.getObject(i);
			// System.out.print(colval + " ");
			// }
			// System.out.println();
			// }
			// //
			// rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			this.setRtntxt("连接数据失败" + e.toString());
			return -2;
		}
		return 0;
	}

	public List<Proeng> processterminal(Statement stmt) {
		List<Proeng> list = new ArrayList<Proeng>();
		try {
			String sql = "select distinct ZDBH,ZDDZ from fenji.db where ZDBH is not null and ZDBH <>'空白'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String procode;
				String engAddr;
				procode =  rs.getString(1);
				engAddr = rs.getString(2);
			
				Proeng proeng = new Proeng();
				proeng.setProcode(procode);
				proeng.setEngaddr(engAddr);
				proeng.setEngarea(this.getSendarea());
				proeng.setPrinciple(this.getPrinciple());
				proeng.setProdate(this.getEngdate());
				proeng.setRunstatus("1");
				proeng.setTypeid(99L);

				list.add(proeng);
			}
			rs.close();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Proeng> processzhongji(Statement stmt) {
		List<Proeng> list = new ArrayList<Proeng>();
		try {
			String sql = "select distinct ZCBH,Unit_addr from zhongji.db where ZCBH is not null ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Proeng proeng = new Proeng();
				proeng.setAreaname(this.getSendarea());
				proeng.setProcode(rs.getObject(1).toString());
				proeng.setEngaddr(rs.getObject(2).toString());
				proeng.setPrinciple(this.getPrinciple());
				proeng.setProdate(this.getEngdate());
				proeng.setRunstatus("1");
				proeng.setTypeid(88L);
				list.add(proeng);
			}
			rs.close();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	// 自己封装的一个把源文件对象复制成目标文件对象
	private static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getRtntxt() {
		return rtntxt;
	}

	public void setRtntxt(String rtntxt) {
		this.rtntxt = rtntxt;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
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

}
