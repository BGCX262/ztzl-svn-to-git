package hibernate.pojo;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Products entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see hibernate.pojo.Products
 * @author MyEclipse Persistence Tools
 */

public class ProductsDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProductsDAO.class);
	// property constants
	public static final String PROCODE = "procode";
	public static final String BARCODE = "barcode";
	public static final String LOTNUM = "lotnum";
	public static final String BOXCODE = "boxcode";
	public static final String TYPEID = "typeid";
	public static final String PROVERSION = "proversion";
	public static final String PROMEMO = "promemo";
	public static final String VIRTUALMARK = "virtualmark";
	public static final String REPAIRNUM = "repairnum";
	public static final String REJECTMARK = "rejectmark";
	public static final String PROSTATUS = "prostatus";

	protected void initDao() {
		// do nothing
	}

	public void save(Products transientInstance) {
		log.debug("saving Products instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Products persistentInstance) {
		log.debug("deleting Products instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Products findById(java.lang.Long id) {
		log.debug("getting Products instance with id: " + id);
		try {
			Products instance = (Products) getHibernateTemplate().get(
					"hibernate.pojo.Products", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Products instance) {
		log.debug("finding Products instance by example");
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
		log.debug("finding Products instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Products as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProcode(Object procode) {
		return findByProperty(PROCODE, procode);
	}

	public List findByBarcode(Object barcode) {
		return findByProperty(BARCODE, barcode);
	}

	public List findByLotnum(Object lotnum) {
		return findByProperty(LOTNUM, lotnum);
	}

	public List findByBoxcode(Object boxcode) {
		return findByProperty(BOXCODE, boxcode);
	}

	public List findByTypeid(Object typeid) {
		return findByProperty(TYPEID, typeid);
	}

	public List findByProversion(Object proversion) {
		return findByProperty(PROVERSION, proversion);
	}

	public List findByPromemo(Object promemo) {
		return findByProperty(PROMEMO, promemo);
	}

	public List findByVirtualmark(Object virtualmark) {
		return findByProperty(VIRTUALMARK, virtualmark);
	}

	public List findByRepairnum(Object repairnum) {
		return findByProperty(REPAIRNUM, repairnum);
	}

	public List findByRejectmark(Object rejectmark) {
		return findByProperty(REJECTMARK, rejectmark);
	}

	public List findByProstatus(Object prostatus) {
		return findByProperty(PROSTATUS, prostatus);
	}

	public List findAll() {
		log.debug("finding all Products instances");
		try {
			String queryString = "from Products";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Products merge(Products detachedInstance) {
		log.debug("merging Products instance");
		try {
			Products result = (Products) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Products instance) {
		log.debug("attaching dirty Products instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Products instance) {
		log.debug("attaching clean Products instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProductsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProductsDAO) ctx.getBean("ProductsDAO");
	}
}