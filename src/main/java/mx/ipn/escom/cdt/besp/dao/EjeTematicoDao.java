package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.modelo.Programa;

@Singleton
@Named("ejeTematicoDao")

public class EjeTematicoDao extends HibernateDaoSupport {
	//Devuelve la lista de TipoArea a AreasNegocio después de realizar la consulta a SQL (Gestionar Area 9,10,11)
	public List<EjeTematico> findAll() { 
		return getHibernateTemplate().loadAll(EjeTematico.class);
	}

	public EjeTematico findById(Integer id) { 
		return getHibernateTemplate().get(EjeTematico.class, id);
	}

	public EjeTematico save(EjeTematico entity) {
		if (entity.getIdEje() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(EjeTematico entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	@SuppressWarnings("unchecked") // Por la convesrión de tipo List a List<Area>
	public List<EjeTematico> findByExample(EjeTematico ejetematico) { // TODO:
		return getHibernateTemplate().findByExample(ejetematico);
	}

}
