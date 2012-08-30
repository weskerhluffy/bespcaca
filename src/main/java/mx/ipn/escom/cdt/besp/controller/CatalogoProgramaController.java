package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.*;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.*; //RequiredStringValidator, Validations, ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-programa" }),
		@Result(name = "input", location = "catalogo-programa/index-deleteConfirm.jsp") })
public class CatalogoProgramaController extends ActionSupport implements
		ModelDriven<Programa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -784454592546747595L;
	private Logger logger = Logger.getLogger(CatalogoProgramaController.class);
	private static final int BANDERA_DURACION = 1;
	private static final int BANDERA_FECHA = 2;
	private static final int BANDERA_INDEFINIDO = 3;
	private Integer banderaTipoPeriodo;

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private ProgramaNegocio programaNegocio;
	private PeriodoNegocio periodoNegocio;

	/**
	 * Referencia el Tipo de unidad con el que se est� trabajando, para el
	 * caso de nuevo, editar y eliminar.
	 */
	private Programa model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Contiene todos los Tipos de Unidades que se encontraron el la BD.
	 */
	private List<Programa> list = null;

	@SkipValidation
	public String editNew() {
		logger.trace("Si estalla el mundo, sera por que te amo");
		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.ES_SECTORIAL,
						programaNegocio.esSectorial());
		ActionContext.getContext().getSession().put("model", model);
		return "editNew";
	}

	public void validateCreate() {
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}
		if (programaNegocio.existe(model)) {
			addActionError(getText("campoRepetidoPrograma"));
		}
		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				} else {
					if (model.getPeriodo().getDuracion() < 0)
						addActionError("La duración no debe ser negativa");
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser indroducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser indroducida");
				}

				if (model.getPeriodo().getFechaInicio() != null
						&& model.getPeriodo().getFechaFin() != null
						&& (model.getPeriodo().getFechaInicio().getTime() > model
								.getPeriodo().getFechaFin().getTime())) {
					addActionError("La fecha inicial debe ser menor a la fecha final");
				}
				break;
			case BANDERA_INDEFINIDO:

				break;
			default:
				addActionError(getText("banderaTipoPeriodo"));
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.siglas", type = ValidatorType.FIELD, key = "Datos incompletos"),
			@RequiredStringValidator(fieldName = "model.siglas", type = ValidatorType.FIELD, key = "introSiglas"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombreProy"),
			@RequiredStringValidator(fieldName = "model.resumen", type = ValidatorType.FIELD, key = "introRes") })
	public HttpHeaders create() {
		Periodo periodo;
		Usuario usuario;

		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		periodo = periodoNegocio.save(model.getPeriodo());//
		model.setIdPeriodo(periodo.getIdPeriodo());
		model.setIdUsuario(usuario.getIdUsuario());
		// model.setIdUsuario(1);
		model.setSectorial(model.getSectorial() == null ? false : model
				.getSectorial());
		model = programaNegocio.save(model);
		addActionMessage(getText("programaAgre"));
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdPrograma());
	}

	@SkipValidation
	public String edit() {

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.HAY_SECTORIAL,
						programaNegocio.esSectorial());

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.ES_SECTORIAL, model.getSectorial());

		if (model.getPeriodo().getDuracion() != null) {
			banderaTipoPeriodo = BANDERA_DURACION;
		} else if ((model.getPeriodo().getFechaInicio() != null)
				&& (model.getPeriodo().getFechaFin() != null)) {
			banderaTipoPeriodo = BANDERA_FECHA;
		} else {
			banderaTipoPeriodo = BANDERA_INDEFINIDO;
		}
		addActionMessage(getText("modificarConf"));
		return "edit";
	}

	public void validateUpdate() {
		/**
		 * valida si esta bien formado el periodo
		 */
		logger.trace("Validando programa " + model);

		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}
		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				} else {
					if (model.getPeriodo().getDuracion() < 0)
						addActionError("La duración no debe ser negativa");
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser indroducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser indroducida");
				}

				if (model.getPeriodo().getFechaInicio() != null
						&& model.getPeriodo().getFechaFin() != null
						&& (model.getPeriodo().getFechaInicio().getTime() > model
								.getPeriodo().getFechaFin().getTime())) {
					addActionError("La fecha inicial debe ser menor a la fecha final");
				}
				break;
			case BANDERA_INDEFINIDO:

				break;
			default:
				addActionError(getText("banderaTipoPeriodo"));
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.resumen", type = ValidatorType.FIELD, key = "introRes") })
	public String update() {
		periodoNegocio.save(model.getPeriodo());
		programaNegocio.save(model);
		addActionMessage(getText("programaEdit"));
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		/*
		 * if (programaNegocio.validarSectorial(model)) {
		 * addActionError("No se puede eliminar programa " + model.getNombre() +
		 * " ya que es un programa sectorial"); }
		 */
		if (programaNegocio.validarProyectos(model)) {
			addActionError("No se puede eliminar programa " + model.getNombre()
					+ " ya que tiene un proyecto en ejecución");
		}
		if (programaNegocio.tieneNiveles(model)) {
			addActionError("No se puede eliminar programa " + model.getNombre()
					+ " ya que tiene niveles asociados");
		}

	}

	public String destroy() {
		programaNegocio.borraPrograma(model);
		addActionMessage(getText("programaElim"));
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() {
		list = programaNegocio.findAll();

		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = programaNegocio.findById(idSel);
		}
	}

	public Programa getModel() {
		if (model == null) {
			model = new Programa();
		}

		return model;
	}

	public List<Programa> getList() {
		return list;
	}

	public void setList(List<Programa> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Programa model) {
		this.model = model;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

	public Integer getBanderaTipoPeriodo() {
		return banderaTipoPeriodo;
	}

	public void setBanderaTipoPeriodo(Integer banderaTipoPeriodo) {
		this.banderaTipoPeriodo = banderaTipoPeriodo;
	}

}
