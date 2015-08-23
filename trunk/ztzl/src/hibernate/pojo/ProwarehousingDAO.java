package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Prowarehousing entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hibernate.pojo.Prowarehousing
 * @author MyEclipse Persistence Tools
 */

public class ProwarehousingDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProwarehousingDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Prowarehousing transientInstance) {
		log.debug("saving Prowarehousing instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Prowarehousing persistentInstance) {
		log.debug("deleting Prowarehousing instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Prowarehousing findById(hibernate.pojo.ProwarehousingId id) {
		log.debug("getting Prowarehousing instance with id: " + id);
		try {
			Prowarehousing instance = (Prowarehousing) getHibernateTemplate()
					.get("hibernate.pojo.Prowarehousing", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Prowarehousing instance) {
		log.debug("finding Prowarehousing instance by example");
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
		log.debug("finding Prowarehousing instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Prowarehousing as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Prowarehousing instances");
		try {
			String queryString = "from Prowarehousing";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Prowarehousing merge(Prowarehousing detachedInstance) {
		log.debug("merging Prowarehousing instance");
		try {
			Prowarehousing result = (Prowarehousing) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Prowarehousing instance) {
		log.debug("attaching dirty Prowarehousing instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Prowarehousing instance) {
		log.debug("attaching clean Prowarehousing instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProwarehousingDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProwarehousingDAO) ctx.getBean("ProwarehousingDAO");
	}
}