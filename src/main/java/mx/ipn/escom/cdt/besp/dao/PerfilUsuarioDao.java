package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Programa;

@Singleton
@Named("perfilUsuarioDao")
public class PerfilUsuarioDao extends HibernateDaoSupport {

	public List<PerfilUsuario> findAll() { // TODO:
		return getHibernateTemplate().loadAll(PerfilUsuario.class);
	}

	public PerfilUsuario findById(Integer id) { // TODO:
		return getHibernateTemplate().get(PerfilUsuario.class, id);
	}

	public PerfilUsuario save(PerfilUsuario entity) {
		if (entity.getIdPerfilUsuario() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(PerfilUsuario entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked") // Por la convesri�n de tipo List a List<TipoUnidad>
	public List<PerfilUsuario> findByExample(PerfilUsuario perfilUsuario) { // TODO:
		return getHibernateTemplate().findByExample(perfilUsuario);
	}
}
