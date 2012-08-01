package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;

@Singleton
@Named("tipoContactoDao")
public class TipoContactoDao extends HibernateDaoSupport {

	public List<TipoContacto> findAll() {
		return getHibernateTemplate().loadAll(TipoContacto.class);
	}

	public TipoContacto findById(Integer id) {
		return getHibernateTemplate().get(TipoContacto.class, id);
	}

	public TipoContacto save(TipoContacto entity) {
		if (entity.getIdTipoContacto() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(TipoContacto entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")// conversion de tipo List a List<Grupo>
	public List<TipoContacto> findByExample(TipoContacto grupo) {
		return getHibernateTemplate().findByExample(grupo);
	}
}
