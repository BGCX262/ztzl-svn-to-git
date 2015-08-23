package struts;

import hibernate.PageInfo;
import hibernate.dao.BaseDAOI;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zhangzhw 所有Action的基类，默认处理用户进程及错误
 */
public class BaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2537910421815160605L;
	protected final String SUCCESS = "success";
	ApplicationContext ctx;

	private BaseDAOI baseDAO;

	public boolean notlog;

	public String execute() {
		try {
			// TODO:判断Session
			if (getSession().getAttribute("username") == null) {
				setNotlog(true);
				return SUCCESS;
			}
			// 执行请求
			return exe();
		} catch (Exception e) {

			return SUCCESS;
		}
	}

	public String exe() {
		return SUCCESS;
	}

	public static int str2int(String string) {
		if (string == null)
			return 0;
		else
			return Integer.valueOf(string);
	}

	// 取得request对象
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 取得response对象
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 取得session对象
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	// 取得 applicationContext对象
	public ApplicationContext getCtx() {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getServletContext());

	}

	// 取得bean
	public Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	public String getUrl() {
		String url = getRequest().getRequestURI();
		return url;
	}

	// 保存
	public void save(Object obj) {
		getBaseDAO().save(obj);
	}

	// 删除
	public void delete(Object obj) {
		getBaseDAO().delete(obj);
	}

	// 更新
	public void update(Object obj) {
		getBaseDAO().update(obj);
	}

	// 自动保存或更新
	public void uporsave(Object obj) {
		getBaseDAO().uporsave(obj);
	}

	// 批量保存
	@SuppressWarnings("unchecked")
	@Transactional(rollbackForClassName = "Exception")
	public boolean save(List list) {
		return getBaseDAO().save(list);
	}

	// 批量自动更新
	@SuppressWarnings("unchecked")
	public boolean uporsave(List list) {
		return getBaseDAO().saveorupdate(list);
	}

	// 查询
	@SuppressWarnings("unchecked")
	public List findAll(String queryString) {
		return getBaseDAO().findAll(queryString);
	}

	// 分页查询
	public PageInfo findAll(String queryString, int pageNo, int pageCount) {
		return getBaseDAO().findAll(queryString, pageNo, pageCount);
	}

	// 根据主键查询
	public Object findByID(String clazz, Long id) {
		return getBaseDAO().findByID(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List findByExample(Object obj) {
		return getBaseDAO().findByExample(obj);
	}

	public int getCount(String queryString) {
		return baseDAO.getCount(queryString);
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAOI getBaseDAO() {
		return baseDAO;
	}

	/**
	 * @param baseDAO
	 *            the baseDAO to set
	 */
	public void setBaseDAO(BaseDAOI baseDAO) {
		this.baseDAO = baseDAO;
	}

	public boolean isNotlog() {
		return notlog;
	}

	public void setNotlog(boolean notlog) {
		this.notlog = notlog;
	}

}
