package hibernate.dao.impl;

import hibernate.PageInfo;
import hibernate.dao.BaseDAOI;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDAO extends HibernateDaoSupport implements BaseDAOI {

	private static final Log log = LogFactory.getLog(BaseDAO.class);

	public void delete(Object obj) {
		log.debug("deleting Pris instance");
		try {
			getHibernateTemplate().delete(obj);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public List findAll(String queryString) {
		log.debug("finding all Object instances");
		try {
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List findByComplex(String clazz, String[] property, Object[] value) {
		log.debug("finding Object instance with property: " + property
				+ ", value: " + value);
		try {
			String queryString = "from " + clazz + " as model where model."
					+ property + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> findByExample(Object obj) {
		log.debug("finding Object instance by example");
		try {
			List<Object> results = getHibernateTemplate().findByExample(obj);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Object findByID(String clazz, Long id) {
		log.debug("getting Object instance with id: " + id);
		try {
			Object obj = getHibernateTemplate().get(
					"hibernate.pojo." + clazz.trim(), id);
			return obj;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> findByProperty(String clazz, String property,
			Object provalue) {
		log.debug("finding Object instance with property: " + property
				+ ", value: " + provalue);
		try {
			String queryString = "from " + clazz + " as model where model."
					+ property + "= ?";
			return getHibernateTemplate().find(queryString, provalue);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}

	}

	public Object merge(Object obj) {
		log.debug("merging Object instance");
		try {
			Object rs = getHibernateTemplate().merge(obj);
			log.debug("merge successful");
			return rs;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}

	}

	public void save(Object obj) {
		log.debug("saving Object instance");
		try {
			getHibernateTemplate().save(obj);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}

	}

	public void uporsave(Object obj) {
		log.debug("attaching dirty Object instance");
		try {
			getHibernateTemplate().saveOrUpdate(obj);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}

	public void update(Object obj) {
		log.debug("merging Object instance");
		try {
			getHibernateTemplate().update(obj);
			log.debug("merge successful");
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public boolean save(List list) {

		log.debug("merging Object instance");
		try {
			for (Object o : list)
				if (o != null)
					getHibernateTemplate().save(o);
			log.debug("merge successful");

			return true;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;

		}
	}

	@SuppressWarnings("unchecked")
	public boolean saveorupdate(List list) {

		log.debug("merging Object instance");
		try {
			for (Object o : list)
				if (o != null)
					getHibernateTemplate().saveOrUpdate(o);
			log.debug("merge successful");

			return true;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;

		}
	}

	// 返回根据每页记录数和页数查询的结果
	@SuppressWarnings("unchecked")
	public PageInfo findAll(String queryString, int pageNo, int pageCount) {

		// 处理页数和记录数非法的情况
		if (pageCount < 10)
			pageCount = 10;

		PageInfo pi = new PageInfo();
		pi.setTotalCount(getCount(queryString));
		pi.setPageCount(pageCount);

		// 页数计算
		if (pi.getTotalCount() % pi.getPageCount() == 0) {
			pi.setTotalPage(pi.getTotalCount() / pi.getPageCount());
			if (pi.getTotalPage() == 0)
				pi.setTotalPage(1);
		} else
			pi.setTotalPage(pi.getTotalCount() / pi.getPageCount() + 1);

		// 设置当前页
		if (pageNo < 1)
			pi.setPageNo(1);
		else if (pageNo > pi.getTotalPage())
			pi.setPageNo(pi.getTotalPage());
		else
			pi.setPageNo(pageNo);

		// 设置查询记录集合
		pi.setList(findRec(queryString, (pi.getPageNo() - 1)
				* pi.getPageCount(), pi.pageCount));

		return pi;
	}

	// 按指定分页记录查询的方法
	@SuppressWarnings("unchecked")
	public List findRec(final String hql, final int firstResult,
			final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {

				Query query = s.createQuery(hql);
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				List list = query.list();
				return list;
			}
		});
	}

	// 根据hql 取得记录数
	public int getCount(String queryString) {
		String newHql = " select count(*) "
				+ queryString.substring(queryString.indexOf("from"));

		Long i = (Long) findAll(newHql).get(0);
		return i.intValue();
	}

	public boolean exeProc(String proc) {
		try {

		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
