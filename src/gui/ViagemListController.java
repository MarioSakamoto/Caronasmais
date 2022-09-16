package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Viagem;
import model.services.ViagemService;

public class ViagemListController implements Initializable{

	private ViagemService service;
	
	@FXML
	private TableView<Viagem> tableViewViagem;  
	
	@FXML
	private TableColumn<Viagem, String> tableColumnRota;
	
	@FXML
	private TableColumn<Viagem, DataFormat> tableColumnData;
	
	@FXML
	private TableColumn<Viagem, DataFormat> tableColumnHora;
	
	@FXML
	private TableColumn<Viagem, String> tableColumnPassageiro;
	
	@FXML
	private Button btNovaViagem;
	
	private ObservableList<Viagem> obsList;
	
	
	@FXML
	public void onBtNovaViagemAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Viagem obj = new Viagem();
		createDialogForm(obj, "/gui/FormatoViagem.fxml", parentStage);
	}
	
	public void setViagemService(ViagemService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnRota.setCellValueFactory(new PropertyValueFactory<>("rota"));
		tableColumnRota.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnRota.setCellValueFactory(new PropertyValueFactory<>("hora"));
		tableColumnRota.setCellValueFactory(new PropertyValueFactory<>("passageiro"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewViagem.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Viagem> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewViagem.setItems(obsList);	
	}
	
	private void createDialogForm(Viagem obj, String absoluteName, Stage parentStage) {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			 Pane pane = loader.load();
			 
			 FomatoViagemController controller = loader.getController();
			 controller.setViagem(obj);
			 controller.updateFormData();
			 
			 Stage dialogStage = new Stage();
			 dialogStage.setTitle("Entre com os dados da Viagem");
			 dialogStage.setScene(new Scene(pane));
			 dialogStage.setResizable(false);
			 dialogStage.initOwner(parentStage);
			 dialogStage.initModality(Modality.WINDOW_MODAL);
			 dialogStage.showAndWait();
			 
		}
		catch(IOException e) { 
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
