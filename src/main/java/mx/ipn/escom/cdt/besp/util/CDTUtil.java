package mx.ipn.escom.cdt.besp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class CDTUtil {
	static SimpleDateFormat formateadorFecha = new SimpleDateFormat(
			"yyyy-MM-dd");
	static SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");
	static Calendar calendario = GregorianCalendar.getInstance();
	static Logger logger = Logger.getLogger(CDTUtil.class);
	

	/**
	 * 
	 * @param fechaFormatoCorto
	 * @return Un objeto Date que representa la fecha dada por
	 *         <code>fechaFormatoCorto</code> si esta se encuentra en el formato
	 *         "yyyy-MM-dd"
	 */
	public static Date getFechaDeFormatoCorto(String fechaFormatoCorto) {
		Date fecha = null;
		try {
			fecha = formateadorFecha.parse(fechaFormatoCorto);
		} catch (ParseException pe) {
			logger.error("No se pudo parsear la cadena " + fechaFormatoCorto
					+ " para fecha");
			logger.error(ExceptionUtils.getStackTrace(pe));
		}
		return fecha;
	}
	
	public static boolean validaProyectoAlineado(Proyecto proyecto)  {
		/*Proyecto proyecto = new Proyecto();
		ProyectoNegocio proyectoNegocio = new ProyectoNegocio();
		proyecto = proyectoNegocio.findById(idProyecto);*/
		List<Estructura> estructuras;
		estructuras = proyecto.getEstructuras();
		Boolean ep1n1;
		Boolean ep1n2;
		logger.trace("Ya que el amor, es musica ligera");
		if (estructuras.size() == 1) {
			if (estructuras.get(0).getPrograma().getSectorial()) {
				logger.trace("alineado con programa sectorial");
			} else {
				logger.trace("alineado con un programa 1n");
			}
			return true;

		} else if (estructuras.size() == 2) {
			ep1n1 = estructuras.get(0).getPrograma().getSectorial();
			ep1n2 = estructuras.get(1).getPrograma().getSectorial();
			if ((ep1n1 == true && ep1n2 == false)
					|| (ep1n1 == false && ep1n2 == true)) {
				logger.trace("alineado con programa sectorial y un programa 1n");
				return true;
			} else {
				logger.trace("no se puede alinear");
				return false;
			}
		} else {
			return false;
		}
	}
	

	/**
	 * 
	 * @param fecha
	 * @return La fecha en formato yyyy-MM-dd
	 */
	public static String getFechaFormateadaCorta(Date fecha) {
		return formateadorFecha.format(fecha);
	}
}
