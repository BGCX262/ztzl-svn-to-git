package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Producttype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hibernate.pojo.Producttype
 * @author MyEclipse Persistence Tools
 */

public class ProducttypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProducttypeDAO.class);
	// property constants
	public static final String TYPENAME = "typename";
	public static final String FUNCTYPE = "functype";
	public static final String TYPEINFO = "typeinfo";
	public static final String INUSE = "inuse";

	protected void initDao() {
		// do nothing
	}

	public void save(Producttype transientInstance) {
		log.debug("saving Producttype instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Producttype persistentInstance) {
		log.debug("deleting Producttype instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Producttype findById(java.lang.Long id) {
		log.debug("getting Producttype instance with id: " + id);
		try {
			Producttype instance = (Producttype) getHibernateTemplate().get(
					"hibernate.pojo.Producttype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Producttype instance) {
		log.debug("finding Producttype instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Producttype instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Producttype as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypename(Object typename) {
		return findByProperty(TYPENAME, typename);
	}

	public List findByFunctype(Object functype) {
		return findByProperty(FUNCTYPE, functype);
	}

	public List findByTypeinfo(Object typeinfo) {
		return findByProperty(TYPEINFO, typeinfo);
	}

	public List findByInuse(Object inuse) {
		return findByProperty(INUSE, inuse);
	}

	public List findAll() {
		log.debug("finding all Producttype instances");
		try {
			String queryString = "from Producttype";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Producttype merge(Producttype detachedInstance) {
		log.debug("merging Producttype instance");
		try {
			Producttype result = (Producttype) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Producttype instance) {
		log.debug("attaching dirty Producttype instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Producttype instance) {
		log.debug("attaching clean Producttype instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProducttypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProducttypeDAO) ctx.getBean("ProducttypeDAO");
	}
}