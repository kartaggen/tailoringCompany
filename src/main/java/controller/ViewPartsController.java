package controller;

import DAO.EmployeeDAO;
import DAO.WorkOrderDAO;
import entity.Employee;
import entity.WorkOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ViewPartsController {

    private ObservableList<PartWithQuantity> listItems;

    @FXML
    public ComboBox<Employee> viewAllPartsByEmployee;

    @FXML
    public Button loadAllPartsBy;

    @FXML
    public TableView allPartsByListTableView;

    @FXML
    public TableColumn allPartsByListTableViewPartId;

    @FXML
    public TableColumn allPartsByListTableViewPartName;

    @FXML
    public TableColumn allPartsByListTableViewQuantity;

    @FXML
    public Label loadError;

    public ViewPartsController() {
    }

    @FXML
    private void initialize() {
        setValuesToWorkOrderEmployeeComboBox();
    }

    @FXML
    public void loadAllPartsByEmployee(ActionEvent actionEvent) {
        viewAllPartsByEmployee.setStyle("-fx-border-color: #6dc5c7 ;");
        loadError.setText("");
        if (viewAllPartsByEmployee.getValue() != null &&
                EmployeeDAO.getEmployeeById(viewAllPartsByEmployee.getValue().getId()) != null) {
            LinkedList<PartWithQuantity> partsQuantities = getPWQList(WorkOrderDAO.getWorkOrdersBy(viewAllPartsByEmployee.getValue().getId()));

            listItems = FXCollections.observableArrayList(partsQuantities);
            allPartsByListTableViewPartId.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, Long>("partId"));
            allPartsByListTableViewPartName.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, String>("name"));
            allPartsByListTableViewQuantity.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, Integer>("quantity"));
            allPartsByListTableView.setItems(listItems);
        } else {
            loadError.setText("Pick Employee!");
            viewAllPartsByEmployee.setStyle("-fx-border-color: #d17171 ;");
        }
    }

    @FXML
    public void loadAllParts(ActionEvent actionEvent) {
        viewAllPartsByEmployee.setStyle("-fx-border-color: #6dc5c7 ;");
        loadError.setText("");

        LinkedList<PartWithQuantity> partsQuantities = getPWQList(WorkOrderDAO.getWorkOrders());
        viewAllPartsByEmployee.setValue(null);

        listItems = FXCollections.observableArrayList(partsQuantities);
        allPartsByListTableViewPartId.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, Long>("partId"));
        allPartsByListTableViewPartName.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, String>("name"));
        allPartsByListTableViewQuantity.setCellValueFactory(new PropertyValueFactory<PartWithQuantity, Integer>("quantity"));
        allPartsByListTableView.setItems(listItems);
    }

    public LinkedList<PartWithQuantity> getPWQList(List<WorkOrder> allWorkOrders) {
        LinkedList<PartWithQuantity> partsQuantities = new LinkedList<>();
        allWorkOrders.sort(new WorkOrderPartComparator());
        int lastPart = 0;
        if (allWorkOrders.size() > 0) {
            WorkOrder firstWO = allWorkOrders.remove(0);
            partsQuantities.add(new PartWithQuantity(firstWO.getPart().getId(), firstWO.getPart().toString(), firstWO.getQuantity()));
            for (WorkOrder wo : allWorkOrders) {
                if (wo.getPart().getId() == partsQuantities.get(lastPart).getPartId()) {
                    partsQuantities.get(lastPart).setQuantity(partsQuantities.get(lastPart).getQuantity() + wo.getQuantity());
                } else {
                    partsQuantities.add(new PartWithQuantity(wo.getPart().getId(), wo.getPart().toString(), wo.getQuantity()));
                    lastPart++;
                }
            }
        }
        return partsQuantities;
    }

    public void setValuesToWorkOrderEmployeeComboBox() {
        viewAllPartsByEmployee.setItems(FXCollections.observableArrayList(EmployeeDAO.getEmployees()));
    }

    public class PartWithQuantity {
        private long partId;
        private String name;
        private int quantity;

        public PartWithQuantity(long partId, String name, int quantity) {
            this.partId = partId;
            this.name = name;
            this.quantity = quantity;
        }

        public long getPartId() {
            return partId;
        }

        public void setPartId(long partId) {
            this.partId = partId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    class WorkOrderPartComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            WorkOrder workOrder1 = (WorkOrder) o1;
            WorkOrder workOrder2 = (WorkOrder) o2;
            return Long.compare(workOrder1.getPart().getId(), workOrder2.getPart().getId());
        }
    }
}
