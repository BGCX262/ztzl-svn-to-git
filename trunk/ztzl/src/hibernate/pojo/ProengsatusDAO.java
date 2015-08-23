package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Proengsatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hibernate.pojo.Proengsatus
 * @author MyEclipse Persistence Tools
 */

public class ProengsatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProengsatusDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Proengsatus transientInstance) {
		log.debug("saving Proengsatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Proengsatus persistentInstance) {
		log.debug("deleting Proengsatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Proengsatus findById(hibernate.pojo.ProengsatusId id) {
		log.debug("getting Proengsatus instance with id: " + id);
		try {
			Proengsatus instance = (Proengsatus) getHibernateTemplate().get(
					"hibernate.pojo.Proengsatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Proengsatus instance) {
		log.debug("finding Proengsatus instance by example");
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
		log.debug("finding Proengsatus instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proengsatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Proengsatus instances");
		try {
			String queryString = "from Proengsatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Proengsatus merge(Proengsatus detachedInstance) {
		log.debug("merging Proengsatus instance");
		try {
			Proengsatus result = (Proengsatus) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proengsatus instance) {
		log.debug("attaching dirty Proengsatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proengsatus instance) {
		log.debug("attaching clean Proengsatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProengsatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProengsatusDAO) ctx.getBean("ProengsatusDAO");
	}
}