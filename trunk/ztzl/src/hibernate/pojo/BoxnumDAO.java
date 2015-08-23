package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Boxnum entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see hibernate.pojo.Boxnum
 * @author MyEclipse Persistence Tools
 */

public class BoxnumDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BoxnumDAO.class);
	// property constants
	public static final String BOXW = "boxw";
	public static final String BOXZ = "boxz";

	protected void initDao() {
		// do nothing
	}

	public void save(Boxnum transientInstance) {
		log.debug("saving Boxnum instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Boxnum persistentInstance) {
		log.debug("deleting Boxnum instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Boxnum findById(java.lang.Long id) {
		log.debug("getting Boxnum instance with id: " + id);
		try {
			Boxnum instance = (Boxnum) getHibernateTemplate().get(
					"hibernate.pojo.Boxnum", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Boxnum instance) {
		log.debug("finding Boxnum instance by example");
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
		log.debug("finding Boxnum instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Boxnum as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBoxw(Object boxw) {
		return findByProperty(BOXW, boxw);
	}

	public List findByBoxz(Object boxz) {
		return findByProperty(BOXZ, boxz);
	}

	public List findAll() {
		log.debug("finding all Boxnum instances");
		try {
			String queryString = "from Boxnum";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Boxnum merge(Boxnum detachedInstance) {
		log.debug("merging Boxnum instance");
		try {
			Boxnum result = (Boxnum) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Boxnum instance) {
		log.debug("attaching dirty Boxnum instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Boxnum instance) {
		log.debug("attaching clean Boxnum instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BoxnumDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BoxnumDAO) ctx.getBean("BoxnumDAO");
	}
}