package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("indicadorDao")
public class IndicadorDao extends HibernateDaoSupport {
	
	public List<Indicador> findAll() { // TODO:
		return getHibernateTemplate().loadAll(Indicador.class);
	}

	public Indicador findById(Integer id) { // TODO:
		return getHibernateTemplate().get(Indicador.class, id);
	}

	public Indicador save(Indicador entity) {
		if (entity.getIdIndicador() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Indicador entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Area>
	public List<Indicador> findByExample(Indicador indicador) { // TODO:
		return getHibernateTemplate().findByExample(indicador);
	}

}
