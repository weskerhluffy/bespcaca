package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Evidencia;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;

@Singleton
@Named("proyectoDao")
public class ProyectoDao extends HibernateDaoSupport {
	public List<Proyecto> findAll() {
		return getHibernateTemplate().loadAll(Proyecto.class);
	}

	public Proyecto findById(Integer id) {
		return getHibernateTemplate().get(Proyecto.class, id);
	}

	public Proyecto save(Proyecto entity) {
		if (entity.getIdProyecto() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Proyecto entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la conversion de tipo List a List<Proyecto>
	public List<Proyecto> findByExample(Proyecto proyecto) {
		return getHibernateTemplate().findByExample(proyecto);
	}
	
	public List<Proyecto> getProyectos(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Proyecto> proyectos = null;
		Query query;
		boolean band=false;
		String sentencia = "select distinct p from Proyecto p join fetch p.temaTransversales tt join p.ejeTematicos et join p.estructuras es where ";
		if (idAreas != null || idProgramas != null || idEjes != null
				|| idTemas != null) {
			if (idAreas != null){	
				sentencia = sentencia
						+ " p.responsable.idArea in(:idAreas)";
			    band=true;
			}
				if (idTemas != null){
					if(band)
						sentencia=sentencia+" and";
					else
						band=true;
				sentencia = sentencia
						+ " tt.idTema in(:idTemas)";
				}
					if (idEjes != null){
						if(band)
							sentencia=sentencia+" and";
						else
							band=true;
				sentencia = sentencia
						+ " et.idEje in(:idEjes)";
					}
						if (idProgramas != null){
							if(band)
								sentencia=sentencia+" and";
				sentencia = sentencia
						+ " es.idPrograma in(:idProgramas)";
						}
            System.out.println("****************************"+sentencia);
            System.out.println();
			query = getSession().createQuery(sentencia);

			if (idAreas != null)
				query.setParameterList("idAreas", idAreas);
			if (idTemas != null)
				query.setParameterList("idTemas", idTemas);
			if (idEjes != null)
				query.setParameterList("idEjes", idEjes);
			if (idProgramas != null)
				query.setParameterList("idProgramas", idProgramas);

			proyectos = query.list();
		} else
			proyectos = this.findAll();
		return proyectos;
	}
}
