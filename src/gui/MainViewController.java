package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.PassageiroService;
import model.services.ViagemService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemPassageiro;
	
	@FXML
	private MenuItem menuItemViagem;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemPassageiroAction() {
		loadView("/gui/PassageiroList.fxml", (PassageiroListController controller) -> {
			controller.setPassageiroService(new PassageiroService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemViagemAction() {
		loadView("/gui/ViagemList.fxml", (ViagemListController controller) -> {
			controller.setViagemService(new ViagemService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemInformacoesAction() {
		loadView("/gui/Informacoes.fxml", x -> {});
	}
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer <T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
		
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
		
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
}
