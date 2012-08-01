package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.EvidenciaDao;
import mx.ipn.escom.cdt.besp.modelo.Evidencia;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("evidenciaNegocio")
public class EvidenciaNegocio {

	private EvidenciaDao evidenciaDao;

	@Transactional
	public List<Evidencia> findAll() {
		return evidenciaDao.findAll();
	}

	@Transactional
	public Evidencia findById(Integer id) {
		return evidenciaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Evidencia save(Evidencia entidad) {
		Evidencia modelo = evidenciaDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Evidencia entidad) {
		evidenciaDao.delete(entidad);
	}

	@Transactional
	public List<Evidencia> findByExample(Evidencia evidencia) {
		return evidenciaDao.findByExample(evidencia);
	}

	public EvidenciaDao getEstadoDao() {
		return evidenciaDao;
	}

	public void setEvidenciaDao(EvidenciaDao evidenciaDao) {
		this.evidenciaDao = evidenciaDao;
	}

	@Transactional
	public List<Evidencia> getEvidencias(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Evidencia> evidencias = this.evidenciaDao.getEvidencias(
				idProgramas, idEjes, idTemas, idAreas);
		return evidencias;
	}
}
