package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Passageiro;
import model.services.ViagemService;
import model.services.PassageiroService;

public class PassageiroListController implements Initializable, DataChangeListener {

	private PassageiroService service;

	@FXML
	private TableView<Passageiro> tableViewPassageiro;

	@FXML
	private TableColumn<Passageiro, Integer> tableColumnId;

	@FXML
	private TableColumn<Passageiro, String> tableColumnNome;
	
	@FXML
	private TableColumn<Passageiro, String> tableColumnTelefone;
	
	@FXML
	private TableColumn<Passageiro, String> tableColumnSaindoDe;
	
	@FXML
	private TableColumn<Passageiro, String> tableColumnIndoPara;
	
	@FXML
	private TableColumn<Passageiro, String> tableColumnViagemId;

	@FXML
	private TableColumn<Passageiro, Passageiro> tableColumnEditar;

	@FXML
	private TableColumn<Passageiro, Passageiro> tableColumnRemover;

	@FXML
	private Button btAddNovo;

	private ObservableList<Passageiro> obsList;

	@FXML
	public void onBtAddNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Passageiro obj = new Passageiro();
		createDialogForm(obj, "/gui/PassageiroForm.fxml", parentStage);
	}

	public void setPassageiroService(PassageiroService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnSaindoDe.setCellValueFactory(new PropertyValueFactory<>("saindo de"));
		tableColumnIndoPara.setCellValueFactory(new PropertyValueFactory<>("indo para"));
		tableColumnViagemId.setCellValueFactory(new PropertyValueFactory<>("viagem id"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPassageiro.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Passageiro> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPassageiro.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Passageiro obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PassageiroFormController controller = loader.getController();
			controller.setPassageiro(obj);
			controller.setServices(new PassageiroService(), new ViagemService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do passageiro!");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Passageiro, Passageiro>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Passageiro obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/PassageiroForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Passageiro, Passageiro>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Passageiro obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Passageiro obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirme", "Você tem certeza que quer deletar?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);

			}
		}
	}

}
