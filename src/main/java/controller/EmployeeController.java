package controller;

import DAO.EmployeeDAO;
import DAO.WorkOrderDAO;
import entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeController {

    private ObservableList<Employee> listItems;

    @FXML
    private TableView employeesListTableView;

    @FXML
    private TableColumn employeesListTableViewIdColumn;

    @FXML
    public TableColumn employeesListTableViewFirstNameColumn;

    @FXML
    public TableColumn employeesListTableViewLastNameColumn;

    @FXML
    private TableColumn employeesListTableViewExperiencedColumn;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private TextField employeeFirstNameText;

    @FXML
    private TextField employeeLastNameText;

    @FXML
    private CheckBox employeeExperiencedCheckBox;

    @FXML
    private Label employeeErrorMessage;

    public EmployeeController() {
    }

    @FXML
    private void initialize() {
        loadEmployees();
        employeeExperiencedCheckBox.setAllowIndeterminate(false);
    }

    public void loadEmployees() {
        listItems = FXCollections.observableArrayList(EmployeeDAO.getEmployees());
        employeesListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        employeesListTableViewFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        employeesListTableViewLastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        employeesListTableViewExperiencedColumn.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("experienced"));
        employeesListTableView.setItems(listItems);
    }

    @FXML
    public void saveEmployeeAction(ActionEvent actionEvent) {
        Employee employee = new Employee();
        if (employeeIdLabel.getText().length() > 0) {
            employee.setId(Long.parseLong(employeeIdLabel.getText()));
        }
        employeeErrorMessage.setText("");
        if (employeeValidation()) {
            employee.setFirstName(employeeFirstNameText.getText());
            employee.setLastName(employeeLastNameText.getText());
            employee.setExperienced(employeeExperiencedCheckBox.isSelected());
            EmployeeDAO.saveOrUpdateEmployee(employee);
            loadEmployees();
            clearEmployeeForm();
        }
    }

    @FXML
    public void editEmployeeAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = employeesListTableView.getSelectionModel();
        clearEmployeeForm();
        if (!selectionModel.isEmpty()) {
            Employee employee = selectionModel.getSelectedItem();
            employeeIdLabel.setText(Long.toString(employee.getId()));
            employeeFirstNameText.setText(employee.getFirstName());
            employeeLastNameText.setText(employee.getLastName());
            employeeExperiencedCheckBox.setSelected(employee.isExperienced());
        } else employeeErrorMessage.setText("An employee must be selected before clicking edit!");
    }


    @FXML
    public void deleteEmployeeAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = employeesListTableView.getSelectionModel();
        Employee employee = selectionModel.getSelectedItem();
        if (employee != null && employee.getId() > 0) {
            if (WorkOrderDAO.getWorkOrdersBy(employee.getId()).isEmpty()) {
                EmployeeDAO.deleteEmployee(employee);
                employeesListTableView.getItems().remove(employee);
            } else employeeErrorMessage.setText("All of the employee's work orders must be deleted first!");
        } else employeeErrorMessage.setText("An employee must be selected before clicking delete!");
    }

    @FXML
    public void clearEmployeeForm() {
        employeeErrorMessage.setText("");
        employeeIdLabel.setText("");
        employeeFirstNameText.clear();
        employeeLastNameText.clear();
        employeeExperiencedCheckBox.setSelected(false);

        employeeFirstNameText.setStyle("-fx-border-color: #6dc5c7 ;");
        employeeLastNameText.setStyle("-fx-border-color: #6dc5c7 ;");
    }

    public boolean employeeValidation() {
        boolean test1 = employeeNameValidation(employeeFirstNameText);
        boolean test2 = employeeNameValidation(employeeLastNameText);
        return test1 && test2;
    }

    public boolean employeeNameValidation(TextField nameField) {
        nameField.setStyle("-fx-border-color: #6dc5c7 ;");
        if (nameField.getText().isEmpty() || nameField.getText().length() > 20) {
            nameField.setStyle("-fx-border-color: #d17171 ;");
            addErrorText(nameField.getPromptText() + " cannot be blank and has to be up to 20 characters!");
            return false;
        } else return true;
    }

    private void addErrorText(String newError) {
        if (employeeErrorMessage.getText().length() > 0)
            employeeErrorMessage.setText(employeeErrorMessage.getText() + "\n");
        employeeErrorMessage.setText(employeeErrorMessage.getText() + newError);
    }
}
