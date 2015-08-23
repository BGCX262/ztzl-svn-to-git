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
 * Proflow entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see hibernate.pojo.Proflow
 * @author MyEclipse Persistence Tools
 */

public class ProflowDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProflowDAO.class);
	// property constants
	public static final String PROCODE = "procode";
	public static final String TYPEID = "typeid";
	public static final String CURWARE = "curware";
	public static final String PROSOURCE = "prosource";
	public static final String SOURCEOPER = "sourceoper";
	public static final String SENDINFO = "sendinfo";
	public static final String SENDTO = "sendto";
	public static final String SENDAREA = "sendarea";
	public static final String RECEIVER = "receiver";
	public static final String RECEIVEINFO = "receiveinfo";
	public static final String OVERMARK = "overmark";
	public static final String RETURNMARK = "returnmark";
	public static final String BOXCODE = "boxcode";

	protected void initDao() {
		// do nothing
	}

	public void save(Proflow transientInstance) {
		log.debug("saving Proflow instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Proflow persistentInstance) {
		log.debug("deleting Proflow instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Proflow findById(java.lang.Long id) {
		log.debug("getting Proflow instance with id: " + id);
		try {
			Proflow instance = (Proflow) getHibernateTemplate().get(
					"hibernate.pojo.Proflow", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Proflow instance) {
		log.debug("finding Proflow instance by example");
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
		log.debug("finding Proflow instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proflow as model where model."
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

	public List findByTypeid(Object typeid) {
		return findByProperty(TYPEID, typeid);
	}

	public List findByCurware(Object curware) {
		return findByProperty(CURWARE, curware);
	}

	public List findByProsource(Object prosource) {
		return findByProperty(PROSOURCE, prosource);
	}

	public List findBySourceoper(Object sourceoper) {
		return findByProperty(SOURCEOPER, sourceoper);
	}

	public List findBySendinfo(Object sendinfo) {
		return findByProperty(SENDINFO, sendinfo);
	}

	public List findBySendto(Object sendto) {
		return findByProperty(SENDTO, sendto);
	}

	public List findBySendarea(Object sendarea) {
		return findByProperty(SENDAREA, sendarea);
	}

	public List findByReceiver(Object receiver) {
		return findByProperty(RECEIVER, receiver);
	}

	public List findByReceiveinfo(Object receiveinfo) {
		return findByProperty(RECEIVEINFO, receiveinfo);
	}

	public List findByOvermark(Object overmark) {
		return findByProperty(OVERMARK, overmark);
	}

	public List findByReturnmark(Object returnmark) {
		return findByProperty(RETURNMARK, returnmark);
	}

	public List findByBoxcode(Object boxcode) {
		return findByProperty(BOXCODE, boxcode);
	}

	public List findAll() {
		log.debug("finding all Proflow instances");
		try {
			String queryString = "from Proflow";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Proflow merge(Proflow detachedInstance) {
		log.debug("merging Proflow instance");
		try {
			Proflow result = (Proflow) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proflow instance) {
		log.debug("attaching dirty Proflow instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proflow instance) {
		log.debug("attaching clean Proflow instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProflowDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProflowDAO) ctx.getBean("ProflowDAO");
	}
}