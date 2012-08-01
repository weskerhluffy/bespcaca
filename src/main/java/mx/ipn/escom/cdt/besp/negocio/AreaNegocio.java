package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.AreaDao;
import mx.ipn.escom.cdt.besp.modelo.Area;

@Singleton
@Named("areaNegocio")
public class AreaNegocio {
	private AreaDao areaDao;
	private Logger logger = Logger.getLogger(AreaNegocio.class);
	/*
	 * (GestionarArea 8) (GestionarArea 12)
	 */
	@Transactional
	public List<Area> findAll() {
		return areaDao.findAll();
	}

	@Transactional
	public Area findById(Integer id) { // TODO:
		return areaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Area save(Area entidad) { // TODO:
		Area modelo = areaDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Area entidad) { // TODO:
		areaDao.delete(entidad);
	}

	@Transactional
	public List<Area> findByExample(Area area) {
		return areaDao.findByExample(area);
	}

	@Transactional
	public Boolean existe(String nombre) { // TODO:
		Area areaEjemplo = new Area();
		List<Area> areas = null;
		areaEjemplo.setNombre(nombre);
		areas = findByExample(areaEjemplo);
		if (areas != null && areas.size() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public Boolean estaAsociado(Area area) {
		logger.trace("El usuario del area "+area.getNombre()+" trae"+area.getUsuarios().size());
		System.out.println("Mierda");
		return area.getUsuarios().size() > 0;
	}
     
	
	public AreaDao getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

}
