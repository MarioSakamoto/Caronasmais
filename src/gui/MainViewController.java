package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemViagem;
	
	@FXML
	private MenuItem menuItemPassageiro;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemViagemAction() {
		System.out.println("onMenuItemViagemAction");
	}
	
	@FXML
	public void onMenuItemPassageiroAction() {
		System.out.println("onMenuItemPassageiroAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("onMenuItemAboutAction");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	
		
	}

}
