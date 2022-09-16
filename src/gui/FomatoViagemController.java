package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FomatoViagemController implements Initializable{
	
	@FXML
	private TextField txtDia;
	
	@FXML
	private TextField txtHora;
	
	@FXML
	private TextField txtSaindoDe;
	
	@FXML
	private TextField txtIndoPara;
	
	@FXML
	private Button btSave;
	
	@FXML 
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	 private void initializeNodes() {
		 Constraints.setTextFieldMaxLength(txtDia, 30);
	 }
}
