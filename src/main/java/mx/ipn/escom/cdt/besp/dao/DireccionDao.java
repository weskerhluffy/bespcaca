package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Direccion;
import mx.ipn.escom.cdt.besp.modelo.Programa;


@Singleton
@Named("direccionDao")public class DireccionDao extends HibernateDaoSupport {
	
	public List<Direccion> findAll() {
		return getHibernateTemplate().loadAll(Direccion.class);
	}

	public Direccion findById(Integer id) {
		return getHibernateTemplate().get(Direccion.class, id);
	}

	public Direccion save(Direccion entity) {
		if (entity.getIdUsuario() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Direccion entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")// conversion de tipo List a List<Direccion>
	public List<Direccion> findByExample(Direccion direccion) {
		return getHibernateTemplate().findByExample(direccion);
	}

}