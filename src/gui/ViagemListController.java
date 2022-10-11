package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
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
import model.entities.Viagem;
import model.services.ViagemService;

public class ViagemListController implements Initializable, DataChangeListener {

	private ViagemService service;

	@FXML
	private TableView<Viagem> tableViewViagem;

	@FXML
	private TableColumn<Viagem, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Viagem, String> tableColumnTrajeto;

	@FXML
	private TableColumn<Viagem, Date> tableColumnData;
	
	@FXML
	private TableColumn<Viagem, LocalTime> tableColumnHora;

	@FXML
	private TableColumn<Viagem, Viagem> tableColumnEditar;

	@FXML
	private TableColumn<Viagem, Viagem> tableColumnRemover;

	@FXML
	private Button btNovaViagem;

	private ObservableList<Viagem> obsList;

	@FXML
	public void onBtNovaViagemAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Viagem obj = new Viagem();
		createDialogForm(obj, "/gui/ViagemForm.fxml", parentStage);
	}

	public void setViagemService(ViagemService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnTrajeto.setCellValueFactory(new PropertyValueFactory<>("Trajeto"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("Data"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		tableColumnHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewViagem.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Viagem> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewViagem.setItems(obsList);
		initEditButton();
		initRemoveButton();
	}

	private void createDialogForm(Viagem obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ViagemFormController controller = loader.getController();
			controller.setViagem(obj);
			controller.setViagemService(new ViagemService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Escolha a data da viagem");
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

	private void initEditButton() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Viagem, Viagem>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Viagem obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ViagemForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButton() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Viagem, Viagem>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Viagem obj, boolean empty) {
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

	private void removeEntity(Viagem obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirme", "Are you sure to delete?");

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
