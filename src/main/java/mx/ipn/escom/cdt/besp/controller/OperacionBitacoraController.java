package mx.ipn.escom.cdt.besp.controller;

import java.util.*;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
	@Result(name = "success", type = "redirectAction", params = {
			"actionName", "operacion-bitacora/%{idSel}" })})
public class OperacionBitacoraController extends ActionSupport implements ModelDriven<Bitacora> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -707216650810322602L;
	private String titulo = "Bitácora del seguimiento del proyecto";
	private BitacoraNegocio bitacoraNegocio;
	private ProyectoNegocio proyectoNegocio;
	private ProgramaNegocio programaNegocio;
	private UsuarioNegocio usuarioNegocio;
	private Bitacora model = null;
	private Date fecha = new Date();
	private String descripcion, sugerenciaRestriccion, descripcionRestriccion , accionForma, detallesAccionRestriccion, estadoEdicion;
	private int idRemitenteSel, idDestinatarioSel, idRestriccionSel;
	private Usuario usuarioPara;
	private Integer idSel;
	private List<List<Bitacora>> registrosBitacora;

	private Proyecto proyecto, proyectoSel;
	private List<Programa> programas;
	private Usuario usuarioLogeado, usuarioSecretaria;
	private Bitacora bitacora;
	
	
	public String show() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, 2);
		//Obtengo el tipo de usuario para saber que operacion realizar
		usuarioLogeado = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		//Si el usuario es coordinador
		if(usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.COORDINADOR))
		{
			//Obtengo los registros de la bitacora asociados al proyecto seleccionado
			proyectoSel = proyectoNegocio.findById(idSel);
			registrosBitacora = bitacoraNegocio.getRegistrosBitacoraProyecto(idSel);

			//Obtengo el id del usuario responsable del programa asociado al proyecto seleccionado
			this.idDestinatarioSel = proyectoSel.getProgramaPrincipal().getIdUsuario();//.getIdResponsable();
			usuarioPara = usuarioNegocio.findById(this.idDestinatarioSel);
			System.out.println("Nombre::>" + usuarioPara.getNombre());
		}
		else
		{
			System.out.println("ID Proyecto: "+idSel);
			//Obtengo los registros de la bitacora asociados a los proyecto asociados al programa seleccionado
			registrosBitacora = bitacoraNegocio.getRegistrosBitacoraProyecto(idSel);
			
		}

		/*
		 * Esto ya no es necesario pero lo dejo por si las dudas
		 *
		 */
		//Si el usuario es gerente
		/*if(usuarioLogeado.getIdPerfilUsuario().equals(perfilUsuario.GERENTE))
		{
			//Obtengo los registros de la bitacora asociados a los proyecto asociados al programa seleccionado
			registrosBitacora = bitacoraNegocio.getRegistrosBitacoraPrograma(idSel);
		}

		//Si el usuario es secretaria
		if(usuarioLogeado.getIdPerfilUsuario().equals(perfilUsuario.SECRETARIO))
		{
			//Obtengo los registros de la bitacora asociados a los proyecto asociados al programa seleccionado
			registrosBitacora = bitacoraNegocio.getRegistrosBitacoraPrograma(idSel);
		}*/

		return "show";
	}

	public HttpHeaders index() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, 2);
		//Obtengo el tipo de usuario para saber que operacion realizar
		usuarioLogeado = (Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);

		registrosBitacora = new ArrayList<List<Bitacora>>();
		programas = new ArrayList<Programa>();

		//Si el usuario es gerente
		if(usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.GERENTE))
		{
			//Obtengo la lista de programas asociados al usuario que esta logeado
			programas = usuarioLogeado.getProgramas();

			//Obtengo los registros de la bitacora asociados a los proyecto asociados a los programas obtenidos
			for(Programa programaSel : programas)
			{
				//System.out.println("ID Programa: "+programaSel.getIdPrograma());
				registrosBitacora.addAll(bitacoraNegocio.getRegistrosBitacoraPrograma(programaSel.getIdPrograma()));
			}
		}

		//Si el usuario es secretario
		if(usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.SECRETARIO))
		{
			//Obtengo la lista de programas asociados al usuario que esta logeado
			programas = programaNegocio.findAll();

			//Obtengo los registros de la bitacora asociados a los proyecto asociados a los programas obtenidos
			for(Programa programaSel : programas)
			{
				//System.out.println("ID Programa: "+programaSel.getIdPrograma());
				registrosBitacora.addAll(bitacoraNegocio.getRegistrosBitacoraPrograma(programaSel.getIdPrograma()));
			}	
		}

		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void validateCreate() { // TODO
		//Obtengo el usuario que ha iniciado sesion
		usuarioLogeado = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		//Si el usuario es coordinador
		if(usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.COORDINADOR))
		{
			//Valido si la descripcion o la sugerencia se obtienen 
			if(descripcionRestriccion.equals("") || sugerenciaRestriccion.equals(""))
			{
				addActionError("Favor de ingresar todos los datos requeridos");
			}
			
			if(descripcionRestriccion.length() > 120 || sugerenciaRestriccion.length() > 120 )
			{
				addActionError("Debe ingresar solo 120 carateres en la descripcion/sugerencia");
			}

		}
		//Si el usuario es gerente
		if(usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.GERENTE) || usuarioLogeado.getIdPerfilUsuario().equals(PerfilUsuario.SECRETARIO))
		{
			if(detallesAccionRestriccion.equals(""))
			{
				addActionError("Favor de ingresar todos los datos requeridos");
			}
			if(detallesAccionRestriccion.length() > 255)
			{
				addActionError("Debe ingresar solo 255 carateres en el mensaje");
			}
		}
	}

	@Validations(/*requiredStrings = {
			@RequiredStringValidator(fieldName = "descripcionRestriccion", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "sugerenciaRestriccion", type = ValidatorType.FIELD, key = "introSugerencia") },*/ regexFields = {
					@RegexFieldValidator(fieldName = "descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,100}", key = "descripcionError.max100"),
					@RegexFieldValidator(fieldName = "sugerencia", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,100}", key = "descripcionError.max100") })
	public HttpHeaders create() {
		String mensaje ="";
		model.setFechaRegistro(fecha);

		//Si la accion que envia la forma es nueva restriccion
		if(accionForma.equals("nuevaRestriccion"))
		{
			//Se  registra el id del proyecto, el tipo de restriccion, el ID del remitente, el ID del destinatario, el contenido de la retriccion y se guarda en BD
			model.setIdProyecto(idSel);
			model.setIdTipoRegistro(TipoRegistro.RESTRICCION);
			model.setIdRemitente(idRemitenteSel);
			model.setIdDestinatario(idDestinatarioSel);
			model.setDescripcion(descripcionRestriccion+":desc:"+sugerenciaRestriccion);
			bitacoraNegocio.save(model);
			mensaje = "Restriccion registrada satisfactoriamente";
		}

		//Si la accion que envia la forma es una atencion inmediata
		if(accionForma.equals("atencionRestriccionInmediata"))
		{
			//Obtengo los datos de la restriccion obteniendolos con el id que tiene en la bitacora
			bitacora = bitacoraNegocio.findById(idRestriccionSel);
			//Obtengo los datos del proyecto obteniendo el proyecto que se tiene en la restriccion de la bitacora
			proyecto = proyectoNegocio.findById(bitacora.getIdProyecto());
			//Obtengo el responsable
			idDestinatarioSel = proyecto.getIdResponsable();
			//Se  registra el id del proyecto, el tipo de restriccion, el ID del remitente, el ID del destinatario, el contenido de la retriccion y se guarda en BD
			model.setIdProyecto(bitacora.getIdProyecto());
			model.setIdRegistroReferencia(bitacora.getIdRegistro());
			model.setIdTipoRegistro(TipoRegistro.ATENCION_INMEDIATA);
			model.setIdRemitente(idRemitenteSel);
			model.setIdDestinatario(idDestinatarioSel);
			model.setDescripcion(detallesAccionRestriccion);
			bitacoraNegocio.save(model);
			//Si se habilito la opcion para habilitar la edicion
			if(estadoEdicion.equals("habilitarEdicion"))
			{
				//Pongo su estado en edicion y guardo
				proyecto.setIdEstado(Estado.EDICION);
				proyectoNegocio.save(proyecto);
			}
			mensaje = "Restriccion atendida satisfactoriamente";
		}

		//Si la accion que envia la forma es turnar una restriccion
		if(accionForma.equals("turnarRestriccion"))
		{
			//Obtengo los datos de la restriccion obteniendolos con el id que tiene en la bitacora
			bitacora = bitacoraNegocio.findById(idRestriccionSel);
			//Obtengo los datos del proyecto obteniendo el proyecto que se tiene en la restriccion de la bitacora
			proyecto = proyectoNegocio.findById(bitacora.getIdProyecto());
			//Obtengo el ID de secretaria para enviarle la restriccion
			usuarioSecretaria = usuarioNegocio.getUsuarioSecretaria(); 
			idDestinatarioSel = usuarioSecretaria.getIdUsuario();
			//Se  registra el id del proyecto, el tipo de restriccion, el ID del remitente, el ID del destinatario, el contenido de la retriccion y se guarda en BD
			model.setIdProyecto(bitacora.getIdProyecto());
			model.setIdRegistroReferencia(bitacora.getIdRegistro());
			model.setIdTipoRegistro(TipoRegistro.DERIVACION);
			model.setIdRemitente(idRemitenteSel);
			model.setIdDestinatario(idDestinatarioSel);
			model.setDescripcion(detallesAccionRestriccion);
			bitacoraNegocio.save(model);
			mensaje = "Restriccion turnada satisfactoriamente";
		}

		//Si la accion que envia la forma es una atencion a restriccion turnada
		if(accionForma.equals("atencionRestriccionTurnada"))
		{
			//Obtengo los datos de la restriccion obteniendolos con el id que tiene en la bitacora
			bitacora = bitacoraNegocio.findById(idRestriccionSel);
			//Obtengo los datos del proyecto obteniendo el proyecto que se tiene en la restriccion de la bitacora
			proyecto = proyectoNegocio.findById(bitacora.getIdProyecto());
			//Obtengo el ID del gerente encargado del programa para enviarle la restriccion
			this.idDestinatarioSel = proyecto.getProgramaPrincipal().getIdUsuario();
			//Se  registra el id del proyecto, el tipo de restriccion, el ID del remitente, el ID del destinatario, el contenido de la retriccion y se guarda en BD
			model.setIdProyecto(bitacora.getIdProyecto());
			model.setIdRegistroReferencia(bitacora.getIdRegistroReferencia());
			model.setIdTipoRegistro(TipoRegistro.ATENCION_DIRECTOR);
			model.setIdRemitente(idRemitenteSel);
			model.setIdDestinatario(idDestinatarioSel);
			model.setDescripcion(detallesAccionRestriccion);
			bitacoraNegocio.save(model);
			mensaje = "Restriccion atendida satisfactoriamente";
		}

		//Si la accion que envia la forma es una atencion a una restriccion atendida por la secretaria
		if(accionForma.equals("atencionRestriccionSecretaria"))
		{
			//Obtengo los datos de la restriccion obteniendolos con el id que tiene en la bitacora
			bitacora = bitacoraNegocio.findById(idRestriccionSel);
			//Obtengo los datos del proyecto obteniendo el proyecto que se tiene en la restriccion de la bitacora
			proyecto = proyectoNegocio.findById(bitacora.getIdProyecto());
			//Obtengo el ID del gerente encargado del programa para enviarle la restriccion
			this.idDestinatarioSel = proyecto.getProgramaPrincipal().getIdUsuario();
			//Se  registra el id del proyecto, el tipo de restriccion, el ID del remitente, el ID del destinatario, el contenido de la retriccion y se guarda en BD
			model.setIdProyecto(bitacora.getIdProyecto());
			model.setIdRegistroReferencia(bitacora.getIdRegistroReferencia());
			model.setIdTipoRegistro(TipoRegistro.ATENCION_TURNADA);
			model.setIdRemitente(idRemitenteSel);
			model.setIdDestinatario(idDestinatarioSel);
			model.setDescripcion(detallesAccionRestriccion);
			bitacoraNegocio.save(model);
			//Si se habilito la opcion para habilitar la edicion
			if(estadoEdicion.equals("habilitarEdicion"))
			{
				//Pongo su estado en edicion y guardo
				proyecto.setIdEstado(Estado.EDICION);
				proyectoNegocio.save(proyecto);
			}
			mensaje = "Restriccion atendida satisfactoriamente";
		}
		addActionMessage(mensaje);
		return new DefaultHttpHeaders("success");
	}

	public Bitacora getModel() { 
		if (model == null) {
			model = new Bitacora();
		}
		return model;
	}

	public void setModel(Bitacora model) {
		this.model = model;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/*public String getSugerencia() {
		return sugerencia;
	}

	public void setSugerencia(String sugerencia) {
		String[] temp = this.descripcion.split(":desc:",2);
		this.descripcion = temp[0];
		this.sugerencia = temp[1];
		//this.sugerencia = sugerencia;
	}*/

	public String getSugerenciaRestriccion() {
		return sugerenciaRestriccion;
	}

	public void setSugerenciaRestriccion(String sugerenciaRestriccion) {
		this.sugerenciaRestriccion = sugerenciaRestriccion;
	}

	public String getDescripcionRestriccion() {
		return descripcionRestriccion;
	}

	public void setDescripcionRestriccion(String descripcionRestriccion) {
		this.descripcionRestriccion = descripcionRestriccion;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public BitacoraNegocio getBitacoraNegocio() {
		return bitacoraNegocio;
	}

	public void setBitacoraNegocio(BitacoraNegocio bitacoraNegocio) {
		this.bitacoraNegocio = bitacoraNegocio;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<List<Bitacora>> getRegistrosBitacora() {
		return registrosBitacora;
	}

	public void setRegistrosBitacora(List<List<Bitacora>> registrosBitacora) {
		this.registrosBitacora = registrosBitacora;
	}

	public String getAccionForma() {
		return accionForma;
	}

	public void setAccionForma(String accionForma) {
		this.accionForma = accionForma;
	}

	public int getIdRemitenteSel() {
		return idRemitenteSel;
	}

	public void setIdRemitenteSel(int idRemitenteSel) {
		this.idRemitenteSel = idRemitenteSel;
	}

	public int getIdDestinatarioSel() {
		return idDestinatarioSel;
	}

	public void setIdDestinatarioSel(int idDestinatarioSel) {
		this.idDestinatarioSel = idDestinatarioSel;
	}

	public String getDetallesAccionRestriccion() {
		return detallesAccionRestriccion;
	}

	public void setDetallesAccionRestriccion(String detallesAccionRestriccion) {
		this.detallesAccionRestriccion = detallesAccionRestriccion;
	}

	public int getIdRestriccionSel() {
		return idRestriccionSel;
	}

	public void setIdRestriccionSel(int idRestriccionSel) {
		this.idRestriccionSel = idRestriccionSel;
	}

	public Bitacora getBitacora() {
		return bitacora;
	}

	public void setBitacora(Bitacora bitacora) {
		this.bitacora = bitacora;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public String getEstadoEdicion() {
		return estadoEdicion;
	}

	public void setEstadoEdicion(String estadoEdicion) {
		this.estadoEdicion = estadoEdicion;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}

}
