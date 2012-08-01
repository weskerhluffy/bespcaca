package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;
import java.util.Map;

import javax.inject.*;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import mx.ipn.escom.cdt.besp.dao.*;
import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

@Singleton
@Named("indicadorNegocio")
public class IndicadorNegocio {
	private IndicadorDao indicadorDao;
	private Logger logger = Logger.getLogger(IndicadorNegocio.class);

	/*
	 * (GestionarIndicador 8) (GestionarIndicador 12)
	 */
	@Transactional
	public List<Indicador> findAll() {
		return indicadorDao.findAll();
	}

	@Transactional
	public Indicador findById(Integer id) {
		return indicadorDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Indicador save(Indicador entidad) {
		Indicador modelo = indicadorDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Indicador entidad) {
		indicadorDao.delete(entidad);
	}

	@Transactional
	public List<Indicador> findByExample(Indicador Indicador) {
		return indicadorDao.findByExample(Indicador);
	}

	public Boolean existe(String descripcion) {
		Indicador indicadorEjemplo = new Indicador();
		List<Indicador> indi = null;
		indicadorEjemplo.setDescripcion(descripcion);
		indi = findByExample(indicadorEjemplo);
		if (indi != null && indi.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param indicador
	 * @return true en caso de que el campo de descripción del indicador de una
	 *         accion no es igual al el campo descripcion de los indicadores de
	 *         la misma acción. Se da por hecho que el indicador ya pertenece a
	 *         una acción. Esto obedece a la regla de negocio 53.
	 */
	@Transactional
	public boolean validaIndicador(Indicador indicador) {
		// Creo un objeto Accion para poder obtener todos los indicadores que
		// pertenecen a una accion
		Accion accion;
		accion = indicador.getAccion();

		// Creo una lista para poder tener todos los indicadores que contiene la
		// accion
		List<Indicador> indicadores = null;

		// Recibo los indicadores de la accion
		indicadores = accion.getIndicadores();

		// Si no tiene indicadores retorno un false (Preguntarle a gaspar esto)
		if (indicadores.isEmpty() == true) {
			return true;
		} else {
			// Recorro la lista de indicadores
			for (Indicador i1 : indicadores) {
				// Esto compara todos los indicadores de la accion consigo
				// mismos (Preguntar si es esto o no, por el momento estara
				// comentado)
				/*
				 * for(Indicador i2 : indicadores) { //Debo obtener los campos
				 * de ds_indicador y comparar si no son los mismos entre todos
				 * if(i1.getIdIndicador()!=i2.getIdIndicador() &&
				 * i1.getDescripcion().matches(i2.getDescripcion())) { return
				 * false; } }
				 */
				// System.out.println(i1.getDescripcion()+" -- "+indicador.getDescripcion());
				// Comparo la descripcion del indicador que tengo con los demas
				if (i1.getDescripcion().matches(indicador.getDescripcion())
						&& !i1.getIdIndicador().equals(
								indicador.getIdIndicador())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validación regla de negocio 54
	 * 
	 * @param Indicador
	 * 
	 * @return true cuando la suma de los pesos de los indicaroes de la accion
	 *         menor o igual a 100 false en caso contrario.
	 * 
	 */

	@Transactional
	public Boolean validaPesoMayorACero(Indicador indicador) {
		if (indicador.getPeso() != null) {
			return (indicador.getPeso() > 0) ? true : false;
		}
		return false;
	}

	@Transactional
	public Boolean validaPesoIndicador(Indicador indicador) {
		List<Indicador> listIndicadores;
		Indicador indiEjemplo;
		indiEjemplo = new Indicador();
		Accion accEjemplo;
		accEjemplo = indicador.getAccion();
		int peso = 0;
		indiEjemplo.setIdAccion(accEjemplo.getIdAccion());

		listIndicadores = this.findByExample(indiEjemplo);

		int maximo = 0;

		if (indicador.getIdIndicador() != null) {
			Indicador indicador2 = this.findById(indicador.getIdIndicador());
			peso = indicador2.getPeso();
		}
		for (Indicador ind : listIndicadores) {
			maximo += ind.getPeso();
		}
		System.out.println(peso + " " + maximo);

		if ((maximo + indicador.getPeso() - peso) > 100) {
			return false;// Se muestra texto de errorRN54
		}

		return true;
	}

	/**
	 * Regla de negocio 55
	 * 
	 * @param ind
	 *            -> Esta es la referencia al indicador con el avance que se
	 *            modifico
	 * @param avanceViejo
	 *            -> Este es el avance antes de la modificacion
	 * @return regresa true cuando el avance nuevo es menor al avance viejo
	 *         regresa false cuando el avance nuevo es mayor al avance viejo
	 * @Transactional public Boolean sePuedeModificar(Indicador ind, Integer
	 *                avanceViejo) { if ((avanceViejo != null) && (avanceViejo >
	 *                0)) { if (ind.getAvance() < avanceViejo) { return true; }
	 *                else { return false; } } return true; } /*
	 */

	@Transactional
	public Boolean validaMetaAvance(Indicador ind) {
		if (ind.getAvance() != null) {
			if ((!(ind.getMeta() >= ind.getAvance()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param idIndicador
	 * @return Validación RN56 Eliminar Indicador sin avances
	 */
	@Transactional
	public Boolean validarIndicadorSinAvances(Integer idIndicador) {
		Indicador indicador;
		indicador = findById(idIndicador);

		if (indicador.getAvance() == null || indicador.getAvance().equals(0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida la RN71
	 * 
	 * @param indicadores
	 *            La lista de todos los indicadores
	 * @return Si todos los indicadores tienen un avance de al menos una unidad
	 */
	@Transactional
	public Boolean validaAvances(List<Indicador> indicadores) {
		Indicador indicadorOriginal;
		for (Indicador indicador : indicadores) {
			indicadorOriginal = findById(indicador.getIdIndicador());
			logger.trace("Comparando " + indicador + " con "
					+ indicadorOriginal);
			if (indicador.getAvance() == null) {
				indicador.setAvance(0);
			}
			if (indicadorOriginal.getAvance() == null) {
				indicadorOriginal.setAvance(0);
			}
			if ((indicador.getAvance() - indicadorOriginal.getAvance()) < 1) {
				return false;
			}
		}
		return true;
	}

	public Boolean validaAvancesValorMaximo(
			List<Indicador> indicadoresAModificar) {
		for (Indicador indicador : indicadoresAModificar) {
			if (indicador.getAvance() > indicador.getMeta()) {
				return false;
			}
		}
		return true;
	}

	public IndicadorDao getIndicadorDao() {
		return indicadorDao;
	}

	public void setIndicadorDao(IndicadorDao IndicadorDao) {
		this.indicadorDao = IndicadorDao;
	}

}