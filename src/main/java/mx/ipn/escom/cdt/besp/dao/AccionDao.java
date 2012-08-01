package mx.ipn.escom.cdt.besp.dao;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;

@Singleton
@Named("accionDao")
public class AccionDao extends HibernateDaoSupport {
	
	public List<Accion> findAll() {
		return getHibernateTemplate().loadAll(Accion.class);
	}

	public Accion findById(Integer id) {
		return getHibernateTemplate().get(Accion.class, id);
	}

	public Accion save(Accion entity) {
		if (entity.getIdAccion() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Accion entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<Accion> findByExample(Accion accion) {
		return getHibernateTemplate().findByExample(accion);
	}

}
