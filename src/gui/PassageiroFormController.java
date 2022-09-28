package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Passageiro;
import model.entities.Viagem;
import model.exceptions.ValidationException;
import model.services.PassageiroService;
import model.services.ViagemService;

public class PassageiroFormController implements Initializable {

	private Passageiro entity;

	private PassageiroService service;

	private ViagemService viagemService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtSaindoDe;

	@FXML
	private TextField txtIndoPara;

	@FXML
	private ComboBox<Viagem> comboBoxViagemId;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorTelefone;

	@FXML
	private Label labelErrorSaindoDe;

	@FXML
	private Label labelErrorIndoPara;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	private ObservableList<Viagem> obsList;

	public void setPassageiro(Passageiro entity) {
		this.entity = entity;
	}

	public void setServices(PassageiroService service, ViagemService viagemService) {
		this.service = service;
		this.viagemService = viagemService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();

		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Passageiro getFormData() {
		Passageiro obj = new Passageiro();

		ValidationException exception = new ValidationException("Validation error");
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Field can't be empty");
		}
		obj.setNome(txtNome.getText());

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "Field can't be empty");
		}
		obj.setTelefone(txtTelefone.getText());
		
		if (txtSaindoDe.getText() == null || txtSaindoDe.getText().trim().equals("")) {
			exception.addError("saindo de", "Field can't be empty");
		}
		obj.setTelefone(txtSaindoDe.getText());


		if (txtIndoPara.getText() == null || txtIndoPara.getText().trim().equals("")) {
			exception.addError("indo para", "Field can't be empty");
		}
		obj.setIndoPara(txtIndoPara.getText());
		
		obj.setViagem(comboBoxViagemId.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 100);
		Constraints.setTextFieldMaxLength(txtTelefone, 30);
		Constraints.setTextFieldMaxLength(txtSaindoDe, 100);
		Constraints.setTextFieldMaxLength(txtIndoPara, 100);
				
		initializeComboBoxViagem();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtTelefone.setText(entity.getTelefone());
		txtSaindoDe.setText(entity.getSaindoDe());
		txtIndoPara.setText(entity.getIndoPara());

		if (entity.getViagem() == null) {
			comboBoxViagemId.getSelectionModel().selectFirst();
		} else {
			comboBoxViagemId.setValue(entity.getViagem());
		}
	}

	public void loadAssociatedObjects() {
		if (viagemService == null) {
			throw new IllegalStateException("ViagemService was null");
		}
		List<Viagem> list = viagemService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxViagemId.setItems(obsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorNome.setText((fields.contains("nome") ? errors.get("nome") : ""));
		labelErrorTelefone.setText((fields.contains("telefone") ? errors.get("telefone") : ""));
		labelErrorSaindoDe.setText((fields.contains("saindo de") ? errors.get("saindo de") : ""));
		labelErrorIndoPara.setText((fields.contains("indo para") ? errors.get("indo para") : ""));
		
	}

	private void initializeComboBoxViagem() {
		Callback<ListView<Viagem>, ListCell<Viagem>> factory = lv -> new ListCell<Viagem>() {
			@Override
			protected void updateItem(Viagem item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getData());
			}

		};
		comboBoxViagemId.setCellFactory(factory);
		comboBoxViagemId.setButtonCell(factory.call(null));
	}

}
