package tagProject.address.model.beans;

import javafx.beans.property.SimpleStringProperty;

public class IndexDataTable {

	private final SimpleStringProperty nombreIndice;
	private final SimpleStringProperty ubicacion;
	private final SimpleStringProperty cantidadDocumentos;
	
	public IndexDataTable(SimpleStringProperty nombreIndice, SimpleStringProperty ubicacion, SimpleStringProperty cantidadDocumentos){
		this.nombreIndice = nombreIndice;
		this.ubicacion = ubicacion;
		this.cantidadDocumentos = cantidadDocumentos;
	}

	public String getNombreIndice() {
		return nombreIndice.get();
	}

	public String getUbicacion() {
		return ubicacion.get();
	}

	public String getCantidadDocumentos() {
		return cantidadDocumentos.get();
	}

	public void setNombreIndice(String nombreIndice) {
		this.nombreIndice.set(nombreIndice);
	}
	
	public void setUbicacion(String ubicacion) {
		this.ubicacion.set(ubicacion);
	}
	
	public void seCantidadDocumentos(String cantidadDocumentos) {
		this.cantidadDocumentos.set(cantidadDocumentos);
	}
	
}
