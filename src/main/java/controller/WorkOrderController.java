package controller;

import DAO.EmployeeDAO;
import DAO.PartDAO;
import DAO.WorkOrderDAO;
import entity.Employee;
import entity.Part;
import entity.WorkOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;

public class WorkOrderController {

    private ObservableList<WorkOrder> listItems;

    @FXML
    private TableView workOrdersListTableView;

    @FXML
    private TableColumn workOrdersListTableViewIdColumn;


    @FXML
    private TableColumn workOrdersListTableViewEmployeeColumn;

    @FXML
    private TableColumn workOrdersListTableViewPartColumn;

    @FXML
    private TableColumn workOrdersListTableViewQuantityColumn;

    @FXML
    private TableColumn workOrdersListTableViewCompletionColumn;

    @FXML
    private Label workOrderIdLabel;

    @FXML
    private ComboBox<Employee> workOrderEmployeeComboBox;

    @FXML
    private ComboBox<Part> workOrderPartComboBox;

    @FXML
    private TextField workOrderQuantityText;

    @FXML
    private DatePicker workOrderCompletionDatePicker;

    @FXML
    private Label workOrderErrorMessage;

    public WorkOrderController() {
    }

    @FXML
    private void initialize() {
        loadWorkOrders();
        setValuesToWorkOrderEmployeeComboBox();
        setValuesToWorkOrderPartComboBox();
        completionDatePickerDisableFutureDates();
        workOrderCompletionDatePicker.setValue(LocalDate.now());
    }

    public void loadWorkOrders() {
        listItems = FXCollections.observableArrayList(WorkOrderDAO.getWorkOrders());
        workOrdersListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<WorkOrder, Long>("id"));
        workOrdersListTableViewEmployeeColumn.setCellValueFactory(new PropertyValueFactory<WorkOrder, String>("employee"));
        workOrdersListTableViewPartColumn.setCellValueFactory(new PropertyValueFactory<WorkOrder, String>("part"));
        workOrdersListTableViewQuantityColumn.setCellValueFactory(new PropertyValueFactory<WorkOrder, Integer>("quantity"));
        workOrdersListTableViewCompletionColumn.setCellValueFactory(new PropertyValueFactory<WorkOrder, LocalDate>("completionDate"));
        workOrdersListTableView.setItems(listItems);
    }

    @FXML
    public void saveWorkOrderAction(ActionEvent actionEvent) {
        WorkOrder workOrder = new WorkOrder();
        if (workOrderIdLabel.getText().length() > 0) {
            workOrder.setId(Long.parseLong(workOrderIdLabel.getText()));
        }
        workOrderErrorMessage.setText("");
        if (workOrderValidation()) {
            workOrder.setEmployee(workOrderEmployeeComboBox.getValue());
            workOrder.setPart(workOrderPartComboBox.getValue());
            workOrder.setQuantity(Integer.parseInt(workOrderQuantityText.getText()));
            workOrder.setCompletionDate(workOrderCompletionDatePicker.getValue());
            WorkOrderDAO.saveOrUpdateWorkOrder(workOrder);
            loadWorkOrders();
            clearWorkOrderForm();
        }
    }

    @FXML
    public void editWorkOrderAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<WorkOrder> selectionModel = workOrdersListTableView.getSelectionModel();
        clearWorkOrderForm();
        if (!selectionModel.isEmpty()) {
            WorkOrder workOrder = selectionModel.getSelectedItem();
            workOrderIdLabel.setText(Long.toString(workOrder.getId()));
            workOrderEmployeeComboBox.setValue(workOrder.getEmployee());
            workOrderPartComboBox.setValue(workOrder.getPart());
            workOrderQuantityText.setText(workOrder.getQuantity() + "");
            workOrderCompletionDatePicker.setValue(workOrder.getCompletionDate());
        } else workOrderErrorMessage.setText("A work order must be selected before clicking edit!");
    }

    @FXML
    public void deleteWorkOrderAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<WorkOrder> selectionModel = workOrdersListTableView.getSelectionModel();
        WorkOrder workOrder = selectionModel.getSelectedItem();
        if (workOrder != null && workOrder.getId() > 0) {
            WorkOrderDAO.deleteWorkOrder(workOrder);
            workOrdersListTableView.getItems().remove(workOrder);
        } else workOrderErrorMessage.setText("A work order must be selected before clicking delete!");
    }

    @FXML
    public void clearWorkOrderForm() {
        workOrderErrorMessage.setText("");
        workOrderIdLabel.setText("");
        workOrderEmployeeComboBox.setValue(null);
        workOrderPartComboBox.setValue(null);
        workOrderQuantityText.clear();
        workOrderCompletionDatePicker.setValue(LocalDate.now());

        workOrderEmployeeComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        workOrderPartComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        workOrderQuantityText.setStyle("-fx-border-color: #6dc5c7 ;");
        workOrderCompletionDatePicker.setStyle("-fx-border-color: #6dc5c7 ;");
    }

    public boolean workOrderValidation() {
        boolean test1 = workOrderEmployeeValidation();
        boolean test2 = workOrderPartValidation();
        boolean test3 = workOrderQuantityValidation();
        boolean test4 = workOrderCompletionDateValidation();
        return test1 && test2 && test3 && test4;
    }

    public boolean workOrderEmployeeValidation() {
        workOrderEmployeeComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        if (workOrderEmployeeComboBox.getValue() != null) {
            if (EmployeeDAO.getEmployeeById(workOrderEmployeeComboBox.getValue().getId()) != null)
                return true;
        }
        addErrorText("Employee cannot be blank and has to exist!");
        workOrderEmployeeComboBox.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean workOrderPartValidation() {
        workOrderPartComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        if (workOrderPartComboBox.getValue() != null) {
            if (PartDAO.getPartById(workOrderPartComboBox.getValue().getId()) != null)
                return true;
        }
        addErrorText("Part cannot be blank and has to exist!");
        workOrderPartComboBox.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean workOrderQuantityValidation() {
        workOrderQuantityText.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!workOrderQuantityText.getText().isEmpty()) {
            try {
                int make = Integer.parseInt(workOrderQuantityText.getText());
                if (make > 0 && make <= 100) return true;
                else
                    addErrorText("Quantity should be at least 1 and up to 100! Example: 5");
            } catch (Exception ex) {
                addErrorText("Quantity should be a whole number! Example: 5");
            }
        } else
            addErrorText("Quantity cannot be blank!");
        workOrderQuantityText.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean workOrderCompletionDateValidation() {
        if (workOrderCompletionDatePicker.getValue() != null)
            if (workOrderCompletionDatePicker.getValue().isBefore(LocalDate.now()) ||
                    workOrderCompletionDatePicker.getValue().isEqual(LocalDate.now()))
                return true;
            else addErrorText("Completion Date cannot be in the future!");
        else addErrorText("Completion Date cannot be blank!");
        workOrderCompletionDatePicker.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public void setValuesToWorkOrderEmployeeComboBox() {
        workOrderEmployeeComboBox.setItems(FXCollections.observableArrayList(EmployeeDAO.getEmployees()));
    }

    public void setValuesToWorkOrderPartComboBox() {
        workOrderPartComboBox.setItems(FXCollections.observableArrayList(PartDAO.getParts()));
    }

    public void completionDatePickerDisableFutureDates() {
        workOrderCompletionDatePicker.setDayCellFactory(getDayCellFactory());
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: grey");
                        }
                    }
                };
            }
        };
    }

    private void addErrorText(String newError) {
        if (workOrderErrorMessage.getText().length() > 0)
            workOrderErrorMessage.setText(workOrderErrorMessage.getText() + "\n");
        workOrderErrorMessage.setText(workOrderErrorMessage.getText() + newError);
    }
}
