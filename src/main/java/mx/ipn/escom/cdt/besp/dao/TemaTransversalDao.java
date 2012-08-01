package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;

@Singleton
@Named("temaTransversalDao")
public class TemaTransversalDao extends HibernateDaoSupport {

	public List<TemaTransversal> findAll() { // TODO:
		return getHibernateTemplate().loadAll(TemaTransversal.class);
	}

	public TemaTransversal findById(Integer id) { // TODO:
		return getHibernateTemplate().get(TemaTransversal.class, id);
	}

	public TemaTransversal save(TemaTransversal entity) {
		if (entity.getIdTema() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(TemaTransversal entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked") // Por la conversión de tipo List a List<TemaTransversal>
	public List<TemaTransversal> findByExample(TemaTransversal temaTransversal) { // TODO:
		return getHibernateTemplate().findByExample(temaTransversal);
	}
}
