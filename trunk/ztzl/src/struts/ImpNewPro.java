package struts;

import hibernate.pojo.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import common.Common;

public class ImpNewPro extends BaseAction {

	public String rtntxt;

	public String uploadFileName;
	public File upload;
	private String uploadContentType;
	private String savePath;
	public boolean notlog;

	public String execute() {
		try {
			// TODO:判断Session
			if (getSession().getAttribute("username") == null) {
				setNotlog(true);
				getResponse().setContentType("text/html; charset=utf-8");
				return SUCCESS;
			}
			// 执行请求
			return exe();
		} catch (Exception e) {

			return SUCCESS;
		}
	}

	public String exe() {
		// String contentType=this.getUploadContentType();
		HttpServletResponse response = getResponse();

		if (getUpload() == null) {
			procExcept("文件名不能为空！");
			return SUCCESS;
		}

		if (!getUploadContentType().equals("application/vnd.ms-excel")) {
			procExcept("该文件不是EXCEL文件！");
			return SUCCESS; // 如果使用json返回类型会出现提示文件下载。
		}

		// 处理excel文件
		int result = processFile();

		switch (result) {
		case 0:
			break;
		case -1:
			procExcept("处理文件失败！");
			break;
		case -2:
			procExcept("保存时出现错误，请检查数据！");
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
			List<Products> list = new ArrayList<Products>();
			InputStream is = new FileInputStream(getUpload());
			Workbook rwb = Workbook.getWorkbook(is);
			// 处理工作表
			Sheet rs = rwb.getSheet(0);
			// 从第二行开始处理，第一行为表头。
			for (int i = 1; i < rs.getRows(); i++) {
				try {
					String pc = rs.getCell(0, i).getContents().trim();
					if (pc == null || pc.equals(""))
						continue;
					Products products = new Products();
					products.setProcode(rs.getCell(0, i).getContents().trim());
					products.setTypeid(Long.valueOf(rs.getCell(1, i)
							.getContents().trim()));
					products.setBoxcode(rs.getCell(2, i).getContents().trim());
					products.setBarcode(rs.getCell(3, i).getContents());
					products.setProdate(Common.str2date(rs.getCell(4, i)
							.getContents()));
					products.setProversion(rs.getCell(5, i).getContents()
							.trim());
					products.setLotnum(rs.getCell(6, i).getContents().trim());
					products.setPromemo(rs.getCell(7, i).getContents().trim());

					// 所有导入信息设置为未入库 暂不判断重复
					products.setProstatus("0");
					products.setVirtualmark("0");
					products.setRepairnum(Long.valueOf(0));
					products.setRejectmark("0");
					list.add(products);
				} catch (Exception e) {
					return i + 1;
				}
			}
			rwb.close();

			// 判断是否有重复。
			for (int i = 0; i < list.size(); i++) {
				String procode = list.get(i).getProcode();

				List<Products> pro = super
						.findAll(" from Products where 1=1 and procode='"
								+ procode + "' ");
				if (pro.size() > 0)
					return i + 2;

			}

			try {
				super.save(list);
			} catch (Exception e) {
				return -2;
			}
		} catch (Exception e) {
			return -1;
		}

		return 0;
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

}
