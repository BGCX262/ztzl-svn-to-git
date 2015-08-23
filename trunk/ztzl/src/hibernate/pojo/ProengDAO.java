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
 * Proeng entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see hibernate.pojo.Proeng
 * @author MyEclipse Persistence Tools
 */

public class ProengDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProengDAO.class);
	// property constants
	public static final String PROCODE = "procode";
	public static final String PRONAME = "proname";
	public static final String TYPEID = "typeid";
	public static final String ENGAREA = "engarea";
	public static final String AREANAME = "areaname";
	public static final String ENGADDR = "engaddr";
	public static final String PRINCIPLE = "principle";
	public static final String RUNSTATUS = "runstatus";
	public static final String INUSE = "inuse";

	protected void initDao() {
		// do nothing
	}

	public void save(Proeng transientInstance) {
		log.debug("saving Proeng instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Proeng persistentInstance) {
		log.debug("deleting Proeng instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Proeng findById(java.lang.Long id) {
		log.debug("getting Proeng instance with id: " + id);
		try {
			Proeng instance = (Proeng) getHibernateTemplate().get(
					"hibernate.pojo.Proeng", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Proeng instance) {
		log.debug("finding Proeng instance by example");
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
		log.debug("finding Proeng instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proeng as model where model."
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

	public List findByProname(Object proname) {
		return findByProperty(PRONAME, proname);
	}

	public List findByTypeid(Object typeid) {
		return findByProperty(TYPEID, typeid);
	}

	public List findByEngarea(Object engarea) {
		return findByProperty(ENGAREA, engarea);
	}

	public List findByAreaname(Object areaname) {
		return findByProperty(AREANAME, areaname);
	}

	public List findByEngaddr(Object engaddr) {
		return findByProperty(ENGADDR, engaddr);
	}

	public List findByPrinciple(Object principle) {
		return findByProperty(PRINCIPLE, principle);
	}

	public List findByRunstatus(Object runstatus) {
		return findByProperty(RUNSTATUS, runstatus);
	}

	public List findByInuse(Object inuse) {
		return findByProperty(INUSE, inuse);
	}

	public List findAll() {
		log.debug("finding all Proeng instances");
		try {
			String queryString = "from Proeng";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Proeng merge(Proeng detachedInstance) {
		log.debug("merging Proeng instance");
		try {
			Proeng result = (Proeng) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proeng instance) {
		log.debug("attaching dirty Proeng instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proeng instance) {
		log.debug("attaching clean Proeng instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProengDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProengDAO) ctx.getBean("ProengDAO");
	}
}