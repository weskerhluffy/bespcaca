package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-editar-datos-proyecto/%{idSel}" }) })
public class AprobarProyectoController extends ActionSupport implements
		ModelDriven<Proyecto> {

	private static final long serialVersionUID = -1607905446228964721L;

	private ProyectoNegocio proyectoNegocio;
	private List<Proyecto> listaProyectos;
	private Proyecto proyecto;
	private Integer idProyectoSeleccionado;
	private Proyecto model = null;
	private Integer idSel = null;
	private Logger logger;
	private Bitacora bitacora;
	private BitacoraNegocio bitacoraNegocio;
	private TipoRegistro tipoRegistro;
	private Usuario usuarioLogeado;
	private Estado estado;

	private String observacion;

	public HttpHeaders index() {
		// logger.trace("Dentro del index");
		listaProyectos = proyectoNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String edit() {
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTO, model);
		return "edit";
	}

	public void validateUpdate() {
		if (model.getIdEstado().equals(estado.EDICION)
				&& observacion.equals("")) {
			addActionError("Favor de ingresar una observacion");
		}
	}

	public String update() {
		// ejecutar la actualizacion
		usuarioLogeado = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		bitacora = new Bitacora();
		if (model.getIdEstado().equals(estado.EDICION)) {
			bitacora.setIdProyecto(model.getIdProyecto());
			bitacora.setIdTipoRegistro(tipoRegistro.OBSERVACION);
			bitacora.setIdRemitente(usuarioLogeado.getIdUsuario());
			bitacora.setIdDestinatario(model.getIdResponsable());
			bitacora.setDescripcion(observacion);
			bitacoraNegocio.save(bitacora);
		}
		proyectoNegocio.save(model);
		return "success";
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);
		}
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Bitacora getBitacora() {
		return bitacora;
	}

	public void setBitacora(Bitacora bitacora) {
		this.bitacora = bitacora;
	}

	public BitacoraNegocio getBitacoraNegocio() {
		return bitacoraNegocio;
	}

	public void setBitacoraNegocio(BitacoraNegocio bitacoraNegocio) {
		this.bitacoraNegocio = bitacoraNegocio;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
