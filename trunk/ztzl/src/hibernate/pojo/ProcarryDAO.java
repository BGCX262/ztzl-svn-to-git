package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Procarry entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see hibernate.pojo.Procarry
 * @author MyEclipse Persistence Tools
 */

public class ProcarryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProcarryDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Procarry transientInstance) {
		log.debug("saving Procarry instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Procarry persistentInstance) {
		log.debug("deleting Procarry instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Procarry findById(hibernate.pojo.ProcarryId id) {
		log.debug("getting Procarry instance with id: " + id);
		try {
			Procarry instance = (Procarry) getHibernateTemplate().get(
					"hibernate.pojo.Procarry", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Procarry instance) {
		log.debug("finding Procarry instance by example");
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
		log.debug("finding Procarry instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Procarry as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Procarry instances");
		try {
			String queryString = "from Procarry";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Procarry merge(Procarry detachedInstance) {
		log.debug("merging Procarry instance");
		try {
			Procarry result = (Procarry) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Procarry instance) {
		log.debug("attaching dirty Procarry instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Procarry instance) {
		log.debug("attaching clean Procarry instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProcarryDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProcarryDAO) ctx.getBean("ProcarryDAO");
	}
}