package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Viagem;
import model.exceptions.ValidationException;
import model.services.ViagemService;

public class ViagemFormController implements Initializable {

	private Viagem entity;

	private ViagemService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtTrajeto;

	@FXML
	private DatePicker dpData;

	@FXML
	private TextField txtHora;

	@FXML
	private Label labelErrorData;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	public void setViagem(Viagem entity) {
		this.entity = entity;
	}

	public void setViagemService(ViagemService service) {
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
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

	private Viagem getFormData() {

		Viagem obj = new Viagem();

		ValidationException exception = new ValidationException("Validation error");
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtTrajeto == null) {
			exception.addError("Trajeto", "Trajeto n�o pode ser nulo!");
		
		} else {
			obj.setTrajeto(txtTrajeto.getText());
		}

		if (dpData == null) {
			exception.addError("Data", "Data n�o pode ficar vazia!");

		} else {
			Instant instant = Instant.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setData(Date.from(instant));
		}

		if (txtHora == null) {
			exception.addError("Hora", "Hora n�o pode ficar vazia!");

		} else {
			obj.setHora(LocalTime.parse(txtHora.getText()));
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entidade nula!");
		}

		if (entity.getId() != null) {
			txtId.setText(String.valueOf(entity.getId()));
		}

		if (entity.getTrajeto() != null) {
			txtTrajeto.setText(entity.getTrajeto());
		}

		if (entity.getData() != null) {
			dpData.setValue(LocalDate.ofInstant(entity.getData().toInstant(), ZoneId.systemDefault()));
		}

		if (entity.getHora() != null) {
			txtHora.setText(entity.getHora().toString());
		}
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("data")) {
			labelErrorData.setText(errors.get("data"));
		}
	}
}
