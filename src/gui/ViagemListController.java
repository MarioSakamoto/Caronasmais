 package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
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
	Button btNovaViagem;
	
	private ObservableList<Viagem> obsList;
	
	
	@FXML
	public void onBtNovaViagemAction() {
		System.out.println("onBtNovaViagemAction");
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
}
