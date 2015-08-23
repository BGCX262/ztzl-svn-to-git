package hibernate.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Errorfor entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see hibernate.pojo.Errorfor
  * @author MyEclipse Persistence Tools 
 */

public class ErrorforDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(ErrorforDAO.class);
	//property constants
	public static final String ERRORVIEW = "errorview";
	public static final String ERROTYPE = "errotype";
	public static final String ERRORINFO = "errorinfo";
	public static final String INUSE = "inuse";



	protected void initDao() {
		//do nothing
	}
    
    public void save(Errorfor transientInstance) {
        log.debug("saving Errorfor instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Errorfor persistentInstance) {
        log.debug("deleting Errorfor instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Errorfor findById( java.lang.Long id) {
        log.debug("getting Errorfor instance with id: " + id);
        try {
            Errorfor instance = (Errorfor) getHibernateTemplate()
                    .get("hibernate.pojo.Errorfor", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Errorfor instance) {
        log.debug("finding Errorfor instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Errorfor instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Errorfor as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByErrorview(Object errorview
	) {
		return findByProperty(ERRORVIEW, errorview
		);
	}
	
	public List findByErrotype(Object errotype
	) {
		return findByProperty(ERROTYPE, errotype
		);
	}
	
	public List findByErrorinfo(Object errorinfo
	) {
		return findByProperty(ERRORINFO, errorinfo
		);
	}
	
	public List findByInuse(Object inuse
	) {
		return findByProperty(INUSE, inuse
		);
	}
	

	public List findAll() {
		log.debug("finding all Errorfor instances");
		try {
			String queryString = "from Errorfor";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Errorfor merge(Errorfor detachedInstance) {
        log.debug("merging Errorfor instance");
        try {
            Errorfor result = (Errorfor) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Errorfor instance) {
        log.debug("attaching dirty Errorfor instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Errorfor instance) {
        log.debug("attaching clean Errorfor instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static ErrorforDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (ErrorforDAO) ctx.getBean("ErrorforDAO");
	}
}