package mx.ipn.escom.cdt.besp.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Estado;
import mx.ipn.escom.cdt.besp.modelo.Indicador;
import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName",
		"operacion-revisar-avances/%{idSel}" }) })
// FIXME arreglar la redireccion exitosa
public class CerrarProyectoController extends ActionSupport implements
		ModelDriven<Proyecto> {

	private static final long serialVersionUID = 5747915121682912524L;

	private ProyectoNegocio proyectoNegocio;
	private List<Proyecto> listaProyectos;
	private Proyecto proyecto;
	private Integer idProyectoSeleccionado;
	private Proyecto model = null;
	private Integer idSel = null;
	private Logger logger;

	private AccionNegocio accionNegocio;
	private List<Accion> acciones;
	private Accion accionEjemplo;

	private IndicadorNegocio indicadorNegocio;
	private List<Indicador> indicadores;
	// private List<Indicador> indicadoresIncompletos;
	private Indicador indicadorEjemplo;
	//variable para verificar la existencia de economias para este proyecto
	Boolean Sobrante=null;
	private Boolean economias=false;
	
	private boolean checkMe;
	

	public HttpHeaders index() {
		logger.trace("Dentro del index");

		listaProyectos = proyectoNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String edit() {
		///////////////////verifica si sobran montos por erogar//////////////////
		int x,y,z;
		List<IndicadorFinanciero> list = null;
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		x=0;
		y=0;
		z=list.size();
		for(x=0;x<z;x++){
			y+=list.get(x).getMontoErogado();
		}
		Sobrante = (y<proyecto.getCostoTotal());
		ActionContext.getContext().getSession()
		.put("Sobrante", Sobrante);
		//////////////////////////////////
		return "edit";
	}

	public void validateUpdate() {
		if (proyectoNegocio.validaAccionesCompletas(model) == false)
			addActionError(getText("accionesCompletas"));
	}

	public String create() {
		return "create";
	}

	public String update() {
		int x,y,z;
		List<IndicadorFinanciero> list = null;
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		x=0;
		y=0;
		z=list.size();
		for(x=0;x<z;x++){
			y+=list.get(x).getMontoErogado();
		}
		Sobrante = (y<proyecto.getCostoTotal());
		//si hay sobrante 
		if(Sobrante){
		if(checkMe){//y hay economias cierra el proyecto
			model.setIdEstado(Estado.CERRADO);
			proyectoNegocio.save(model);
		}
		else{//si no entonces no puede cerrarse
			addActionError(getText("presupuestoSinEjercer"));
		}
		}
		else{//si no hay sobrante entonces cierra proyecto
			model.setIdEstado(Estado.CERRADO);
			proyectoNegocio.save(model);
		}
		return "success";
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);
			obtenCosas(model);
		}
	}

	public void obtenCosas(Proyecto proy) {
		accionEjemplo.setIdProyecto(idSel);
		acciones = accionNegocio.findByExample(accionEjemplo);
	}

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getIdProyectoSeleccionado() {
		return idProyectoSeleccionado;
	}

	public void setIdProyectoSeleccionado(Integer idProyectoSeleccionado) {
		this.idProyectoSeleccionado = idProyectoSeleccionado;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Proyecto getModel() {
		if (model == null) {
			model = new Proyecto();
		}

		return model;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public List<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	public Accion getAccionEjemplo() {
		return accionEjemplo;
	}

	public void setAccionEjemplo(Accion accionEjemplo) {
		this.accionEjemplo = accionEjemplo;
	}

	public IndicadorNegocio getIndicadorNegocio() {
		return indicadorNegocio;
	}

	public void setIndicadorNegocio(IndicadorNegocio indicadorNegocio) {
		this.indicadorNegocio = indicadorNegocio;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public Indicador getIndicadorEjemplo() {
		return indicadorEjemplo;
	}

	public void setIndicadorEjemplo(Indicador indicadorEjemplo) {
		this.indicadorEjemplo = indicadorEjemplo;
	}

	public Boolean getSobrante() {
		if(Sobrante == null){
			Sobrante = (Boolean) ActionContext.getContext().get("Sobrante");
		}
		return Sobrante;
	}

	public void setSobrante(Boolean sobrante) {
		Sobrante = sobrante;
	}

	public Boolean getEconomias() {
		if(economias==null)
		economias = (Boolean) ActionContext.getContext().getSession().get("economias");
		return economias;
	}

	public void setEconomias(Boolean economias) {
		this.economias = economias;
	}
	 
		public boolean isCheckMe() {
			return checkMe;
		}
	 
		public void setCheckMe(boolean checkMe) {
			this.checkMe = checkMe;
		}
}
