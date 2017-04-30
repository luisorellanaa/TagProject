package tagProject.address.view;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import tagProject.address.MainTag;
import tagProject.address.model.IR;

public class IndexControler {


	@FXML
	private CategoryAxis indexAxis;
	@FXML
	private NumberAxis numDocAxis;
	@FXML
	private BarChart<String, Integer> indexChart;
	@FXML
	private TextField rutaTxt;
	@FXML
	private TextField idIndexTxt;
	@FXML
	private Label indiceSeleccionadoLbl;
	@FXML
	private Button crearIndexBtn;
	@FXML
	private AnchorPane mainIndexPane;
	
	

	@FXML
	private void initialize() {
		
		EventosIndex eventosIndex = new EventosIndex();
		
		rutaTxt.setOnMousePressed(eventosIndex.getEventoRuta());
		
		crearIndexBtn.setOnMousePressed(eventosIndex.getEventoCrearIndex());
		
		//Llenar tabla de indices
		llenarChartIndices(eventosIndex);
	}
	
	
	private void llenarChartIndices(EventosIndex eventosIndex){
		
		indexChart.getData().clear();
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		//Llenar tabla de indices
		IR ir = IR.getInstance();
		
		HashMap<String, String> results = ir.getAllIndex();
		
		Set<String> keys = results.keySet();
		
		for(String nombreIndice : keys){

			String cantidadDocumentos = results.get(nombreIndice).split("\\♠")[1];

			Data<String, Integer> nodo = new XYChart.Data<>(nombreIndice, Integer.parseInt(cantidadDocumentos));
			
			series.getData().add(nodo);
		}
		
		indexChart.getData().add(series);
		
		for (final Data<String, Integer> dt : series.getData()) {
	        final Node n = dt.getNode();
	        
	        //Si ya estaba marcado (color rojo), se mantendra con el mismo estilo 
	        if(dt.getXValue().equalsIgnoreCase(IR.getInstance().getIndexName())){
	        	n.setStyle("-fx-background-color: #ff6666");//rojo
	        }else{
	        	n.setStyle("-fx-background-color: #9f9f9f");//plomo
	        }
	        n.setOnMouseClicked(eventosIndex.getEventoChart(dt, results, series));
	        
		}
	}


	private class EventosIndex {

		private EventHandler<MouseEvent> getEventoChart( Data<String, Integer> dt, HashMap<String, String> results, XYChart.Series<String, Integer> series) {
			
			return new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	            	
	            	IR.getInstance().setIndexName(dt.getXValue());
	            	indiceSeleccionadoLbl.setText("Indice Seleccionado: " + IR.getInstance().getIndexName());
	            	
	            	String ubicacion = results.get(IR.getInstance().getIndexName()).split("\\♠")[0];
	            	
					Notifier.setPopupLocation(null, Pos.CENTER);
					Notifier.setWidth(600);
					Notifier.INSTANCE.setPopupLifetime(new Duration(200));
					Notifier.INSTANCE.notifyInfo("Indice: " + IR.getInstance().getIndexName(), ubicacion);
					
					for (final Data<String, Integer> dt : series.getData()) {
				        dt.getNode().setStyle("-fx-background-color: #9f9f9f");
					}
					
					dt.getNode().setStyle("-fx-background-color: #ff6666");
	            }
	        };
		}
		
		private EventHandler<Event> getEventoRuta() {

			return new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					final DirectoryChooser directoryChooser = new DirectoryChooser();
					final File selectedDirectory = directoryChooser.showDialog(MainTag.mainStage);
					if (selectedDirectory != null) {
						rutaTxt.setText(selectedDirectory.getAbsolutePath());
					}

				}

			};
		}

		private EventHandler<Event> getEventoCrearIndex() {

			return new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					
					IR ir = IR.getInstance();
					
					if(rutaTxt.getText().isEmpty() || idIndexTxt.getText().isEmpty()){
						Notifier.setPopupLocation(null, Pos.CENTER);
						Notifier.INSTANCE.setPopupLifetime(new Duration(200));
						Notifier.INSTANCE.notifyError("Error", "Nombre del indice y ruta son requeridos...");
						return;
					}else{

						HashMap<String, String> results = ir.getAllIndex();
						
						if(results.containsKey(idIndexTxt.getText())){
							Notifier.setPopupLocation(null, Pos.CENTER);
							Notifier.INSTANCE.notifyError("Error", "Ya existe el indice " + idIndexTxt.getText());
							return;	
						}
					}
					
					ir.createIndex(rutaTxt.getText(), idIndexTxt.getText());
					
					//Recalcular tabla indices
					llenarChartIndices(EventosIndex.this);
					//Borrar datos de los campos de texto
					rutaTxt.clear();
					idIndexTxt.clear();
				}

			};
		}
	}

}
