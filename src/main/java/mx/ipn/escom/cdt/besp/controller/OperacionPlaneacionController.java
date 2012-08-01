package mx.ipn.escom.cdt.besp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.opensymphony.xwork2.ActionContext;

@Named
@RemoteProxy
public class OperacionPlaneacionController extends ActionSupport implements
		ModelDriven<Proyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4263022554194532360L;
	private ProyectoNegocio proyectoNegocio;
	private Integer idSel;
	private Proyecto proyecto;
	private Logger logger = Logger
			.getLogger(OperacionPlaneacionController.class);
	private Date fechaFin;
	private List<IndicadorFinanciero> list = null;
	
	
	@Override
	public Proyecto getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@SkipValidation
	public HttpHeaders index() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, 4);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String show() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, 4);
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		return "show";
	}

	@RemoteMethod
	public Nodo getNodos(Integer idSel) {
		Nodo raiz;
		// Usuario us = new Usuario();

		// WebContext ctx = WebContextFactory.get();
		// HttpServletRequest req = ctx.getHttpServletRequest();
		// us = (Usuario) req.getSession().getAttribute(
		// NombreObjetosSesion.USUARIO);
		// logger.trace("El usuario loggueado es " + us);

		// proyecto =
		// proyectoNegocio.findById(us.getProyectos().get(0).getId());
		logger.trace(idSel);
		proyecto = proyectoNegocio.findById(idSel);
		if(proyecto.getPeriodo().getFechaFin() == null && proyecto.getPeriodo().getDuracion() != null)
		{
			Calendar cal = Calendar.getInstance();
			fechaFin = new Date();
			cal.setTime(proyecto.getPeriodo().getFechaInicio());
			cal.add(Calendar.DATE, proyecto.getPeriodo().getDuracion());
			fechaFin = cal.getTime();
			logger.trace(fechaFin);
			proyecto.getPeriodo().setFechaFin(fechaFin);
		}
		raiz = proyecto;
		// logger.trace("Aventando las estructuras de " + programa);
		proyecto.getNodosHijo();
		// logger.trace("Que son " + programa.getNodosHijo().size());
		return raiz;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<IndicadorFinanciero> getList() {
		return list;
	}

	public void setList(List<IndicadorFinanciero> list) {
		this.list = list;
	}
	
	
}