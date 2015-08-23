package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Prorepairstatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hibernate.pojo.Prorepairstatus
 * @author MyEclipse Persistence Tools
 */

public class ProrepairstatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProrepairstatusDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Prorepairstatus transientInstance) {
		log.debug("saving Prorepairstatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Prorepairstatus persistentInstance) {
		log.debug("deleting Prorepairstatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Prorepairstatus findById(hibernate.pojo.ProrepairstatusId id) {
		log.debug("getting Prorepairstatus instance with id: " + id);
		try {
			Prorepairstatus instance = (Prorepairstatus) getHibernateTemplate()
					.get("hibernate.pojo.Prorepairstatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Prorepairstatus instance) {
		log.debug("finding Prorepairstatus instance by example");
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
		log.debug("finding Prorepairstatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Prorepairstatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Prorepairstatus instances");
		try {
			String queryString = "from Prorepairstatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Prorepairstatus merge(Prorepairstatus detachedInstance) {
		log.debug("merging Prorepairstatus instance");
		try {
			Prorepairstatus result = (Prorepairstatus) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Prorepairstatus instance) {
		log.debug("attaching dirty Prorepairstatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Prorepairstatus instance) {
		log.debug("attaching clean Prorepairstatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProrepairstatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProrepairstatusDAO) ctx.getBean("ProrepairstatusDAO");
	}
}