package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Viagem;

public class FomatoViagemController implements Initializable{
	
	private Viagem entity;
	
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
	
	public void setViagem(Viagem entity) {
		this.entity = entity;
	}
	
	
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
	 
	 public void updateFormData() {
		 if(entity == null) {
			 throw new IllegalStateException("Entity was null");
		 }
		 txtDia.setText(entity.getDia());
		 txtHora.setText(entity.getHora());
		 txtSaindoDe.setText(entity.getSaindoDe());
		 txtIndoPara.setText(entity.getIndoPara());
	 }
}
