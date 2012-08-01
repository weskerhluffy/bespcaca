package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.modelo.Programa;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("indicadorFinancieroDao")
public class IndicadorFinancieroDao extends HibernateDaoSupport {

	public List<IndicadorFinanciero> findAll() {
		return getHibernateTemplate().loadAll(IndicadorFinanciero.class);
	}

	public IndicadorFinanciero findById(Integer id) {
		return getHibernateTemplate().get(IndicadorFinanciero.class, id);
	}

	public IndicadorFinanciero save(IndicadorFinanciero entity) {
		if (entity.getIdIndicadorFinanciero() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(IndicadorFinanciero entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	@SuppressWarnings("unchecked")
	public List<IndicadorFinanciero> findByExample(IndicadorFinanciero IndicadorFinanciero) {
		return getHibernateTemplate().findByExample(IndicadorFinanciero);
	}

}
