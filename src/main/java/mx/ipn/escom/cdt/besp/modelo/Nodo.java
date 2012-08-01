package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

public interface Nodo {

	public <NODO extends Nodo> List<NODO> getNodosHijo();

	public <NODO extends Nodo> List<NODO> getNodosHijoInicializar();

	public Nodo getNodoPadre();

	public Periodo getPeriodo();

	public Integer getId();

	public void setNodoPadre(Nodo nodo);

	public String getNombre();
	
	public Integer getAvance();

	public String getTipoNodo();
}
