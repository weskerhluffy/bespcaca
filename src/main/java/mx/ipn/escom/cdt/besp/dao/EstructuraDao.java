package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.Estructura;

import org.apache.log4j.Logger;
import org.hibernate.ReplicationMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("estructuraDao")
public class EstructuraDao extends HibernateDaoSupport {
	// Devuelve la lista de TipoArea a AreasNegocio después de realizar la
	// consulta a SQL (Gestionar Area 9,10,11)
	private Logger logger = Logger.getLogger(EstructuraDao.class);

	public List<Estructura> findAll() {
		return getHibernateTemplate().loadAll(Estructura.class);
	}

	public Estructura findById(Integer id) {
		return getHibernateTemplate().get(Estructura.class, id);
	}

	public Estructura save(Estructura entity) {
		if (entity.getIdEstructura() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getHibernateTemplate().saveOrUpdate(entity);
		logger.trace("Se salvo la entidad " + entity + " esta en sesion "
				+ getHibernateTemplate().contains(entity));
		return entity;
	}

	public void delete(Estructura entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Area>
	public List<Estructura> findByExample(Estructura estructura) {
		return getHibernateTemplate().findByExample(estructura);
	}

	public void reasoaciarASesion(Estructura estructura) {
		getHibernateTemplate().replicate(estructura,
				ReplicationMode.LATEST_VERSION);
	}
}
