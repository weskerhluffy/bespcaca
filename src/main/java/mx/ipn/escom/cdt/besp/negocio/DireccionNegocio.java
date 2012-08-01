package mx.ipn.escom.cdt.besp.negocio;


import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;


import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.DireccionDao;
import mx.ipn.escom.cdt.besp.modelo.Direccion;

@Singleton
@Named("direccionNegocio")
public class DireccionNegocio {
	private DireccionDao DireccionDao;
    @Transactional
	public List<Direccion> findAll() {
		return DireccionDao.findAll();
	}
    @Transactional
	public Direccion findById(Integer id) {
		return DireccionDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Direccion save(Direccion entidad) {
		Direccion modelo = DireccionDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Direccion entidad) {
		DireccionDao.delete(entidad);
	}

	
    @Transactional
	public List<Direccion> findByExample(Direccion Direccion) {
		return DireccionDao.findByExample(Direccion);
	}
    @Transactional
	public Boolean existe(Integer user) {
		Direccion DireccionEjemplo = new Direccion();
		List<Direccion> Direccions = null;
		DireccionEjemplo.setIdUsuario(user);
		Direccions = findByExample(DireccionEjemplo);
		if (Direccions != null && Direccions.size() > 0) {
			return true;
		}
		return false;
	}
    
    public DireccionDao getDireccionDao() {
		return DireccionDao;
	}

	public void setDireccionDao(DireccionDao DireccionDao) {
		this.DireccionDao = DireccionDao;
	}
}
