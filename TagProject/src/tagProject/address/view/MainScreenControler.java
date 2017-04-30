package tagProject.address.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class MainScreenControler {

	@FXML
	private Tab idTabIndice;
	@FXML
	private Tab idTabBusqueda;

	@FXML
	private void initialize() {
		
		//idTabIndice.setText("aaa");
	}
	
	public void setTabContent(Node n){
		idTabIndice.setContent(n);
	}
}
