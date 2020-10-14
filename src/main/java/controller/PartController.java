package controller;

import DAO.MaterialDAO;
import DAO.PartDAO;
import DAO.WorkOrderDAO;
import entity.Material;
import entity.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PartController {

    private ObservableList<Part> listItems;

    @FXML
    private TableView partsListTableView;

    @FXML
    private TableColumn partsListTableViewIdColumn;

    @FXML
    public TableColumn partsListTableViewNameColumn;

    @FXML
    public TableColumn partsListTableViewMaterialColumn;

    @FXML
    public TableColumn partsListTableViewNeededSqmColumn;

    @FXML
    public TableColumn partsListTableViewExpMakePerMonthColumn;

    @FXML
    public TableColumn partsListTableViewInexpMakePerMonthColumn;

    @FXML
    public TableColumn partsListTableViewSellPriceColumn;

    @FXML
    private Label partIdLabel;

    @FXML
    private TextField partNameText;

    @FXML
    private ComboBox<Material> partMaterialComboBox;

    @FXML
    private TextField partNeededSqmText;

    @FXML
    private TextField partExpPerMonthText;

    @FXML
    private TextField partInexpPerMonthText;

    @FXML
    private TextField partSellPriceText;

    @FXML
    private Label partErrorMessage;

    public PartController() {
    }

    @FXML
    private void initialize() {
        loadParts();
        setValuesToPartMaterialComboBox();
    }

    public void loadParts() {
        listItems = FXCollections.observableArrayList(PartDAO.getParts());
        partsListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Long>("id"));
        partsListTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsListTableViewMaterialColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("material"));
        partsListTableViewNeededSqmColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("materialNeededSqm"));
        partsListTableViewExpMakePerMonthColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("expMakePerMonth"));
        partsListTableViewInexpMakePerMonthColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inexpMakePerMonth"));
        partsListTableViewSellPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("sellPrice"));
        partsListTableView.setItems(listItems);
    }

    @FXML
    public void savePartAction(ActionEvent actionEvent) {
        Part part = new Part();
        if (partIdLabel.getText().length() > 0) {
            part.setId(Long.parseLong(partIdLabel.getText()));
        }
        partErrorMessage.setText("");
        if (partValidation()) {
            part.setName(partNameText.getText());
            part.setMaterial(partMaterialComboBox.getValue());
            part.setMaterialNeededSqm(Double.parseDouble(partNeededSqmText.getText()));
            part.setExpMakePerMonth(Integer.parseInt(partExpPerMonthText.getText()));
            part.setInexpMakePerMonth(Integer.parseInt(partInexpPerMonthText.getText()));
            part.setSellPrice(Double.parseDouble(partSellPriceText.getText()));
            PartDAO.saveOrUpdatePart(part);
            loadParts();
            clearPartForm();
        }
    }

    @FXML
    public void editPartAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Part> selectionModel = partsListTableView.getSelectionModel();
        clearPartForm();
        if (!selectionModel.isEmpty()) {
            Part part = selectionModel.getSelectedItem();
            partIdLabel.setText(Long.toString(part.getId()));
            partNameText.setText(part.getName());
            partMaterialComboBox.setValue(part.getMaterial());
            partNeededSqmText.setText(part.getMaterialNeededSqm() + "");
            partExpPerMonthText.setText(part.getExpMakePerMonth() + "");
            partInexpPerMonthText.setText(part.getInexpMakePerMonth() + "");
            partSellPriceText.setText(part.getSellPrice() + "");

        } else partErrorMessage.setText("A part must be selected before clicking edit!");
    }

    @FXML
    public void deletePartAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Part> selectionModel = partsListTableView.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        if (part != null && part.getId() > 0) {
            if (WorkOrderDAO.getWorkOrdersWith(part.getId()).isEmpty()) {
                PartDAO.deletePart(part);
                partsListTableView.getItems().remove(part);
            } else partErrorMessage.setText("All work orders with this part must be deleted first!");
        } else partErrorMessage.setText("A part must be selected before clicking delete!");
    }

    @FXML
    public void clearPartForm() {
        partErrorMessage.setText("");
        partIdLabel.setText("");
        partNameText.clear();
        partMaterialComboBox.setValue(null);
        partNeededSqmText.clear();
        partExpPerMonthText.clear();
        partInexpPerMonthText.clear();
        partSellPriceText.clear();

        partNameText.setStyle("-fx-border-color: #6dc5c7 ;");
        partMaterialComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        partNeededSqmText.setStyle("-fx-border-color: #6dc5c7 ;");
        partExpPerMonthText.setStyle("-fx-border-color: #6dc5c7 ;");
        partInexpPerMonthText.setStyle("-fx-border-color: #6dc5c7 ;");
        partSellPriceText.setStyle("-fx-border-color: #6dc5c7 ;");
    }

    public void setValuesToPartMaterialComboBox() {
        partMaterialComboBox.setItems(FXCollections.observableArrayList(MaterialDAO.getMaterials()));
    }

    public boolean partValidation() {
        boolean test1 = partNameValidation();
        boolean test2 = partMaterialValidation();
        boolean test3 = partDoubleValidation(partNeededSqmText, 100);
        boolean test4 = partIntegerValidation(partExpPerMonthText);
        boolean test5 = partIntegerValidation(partInexpPerMonthText);
        boolean test6 = partDoubleValidation(partSellPriceText, 10000);
        return test1 && test2 && test3 && test4 && test5 && test6;
    }

    public boolean partNameValidation() {
        partNameText.setStyle("-fx-border-color: #6dc5c7 ;");
        if (partNameText.getText().isEmpty() || partNameText.getText().length() > 45) {
            partNameText.setStyle("-fx-border-color: #d17171 ;");
            addErrorText("Part name cannot be blank and has to be up to 45 characters!");
            return false;
        } else return true;
    }

    public boolean partMaterialValidation() {
        partMaterialComboBox.setStyle("-fx-border-color: #6dc5c7 ;");
        if (partMaterialComboBox.getValue() != null) {
            if (MaterialDAO.getMaterialById(partMaterialComboBox.getValue().getId()) != null)
                return true;
        }
        addErrorText("Material cannot be blank and has to exist!");
        partMaterialComboBox.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean partDoubleValidation(TextField doubleField, int max) {
        doubleField.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!doubleField.getText().isEmpty()) {
            try {
                double num = Double.parseDouble(doubleField.getText());
                if ((num + "").substring((num + "").indexOf(".")).length() > 3)
                    addErrorText(doubleField.getPromptText() + " should have only 2 digits after decimal point!");
                else if (num > 0 && num <= max) {
                    return true;
                } else
                    addErrorText(doubleField.getPromptText() + " should be between 0 and " + max + "! Example: 42.00");
            } catch (NumberFormatException exception) {
                addErrorText(doubleField.getPromptText() + " should be a number! Example: 42.00");
            }
        } else
            addErrorText(doubleField.getPromptText() + " cannot be blank!");
        doubleField.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean partIntegerValidation(TextField makeField) {
        makeField.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!makeField.getText().isEmpty()) {
            try {
                int make = Integer.parseInt(makeField.getText());
                if (make > 0 && make <= 1000) return true;
                else
                    addErrorText(makeField.getPromptText() + " should be from 1 to 1000! Example: 42");
            } catch (Exception ex) {
                addErrorText(makeField.getPromptText() + " should be a whole number! Example: 42");
            }
        } else
            addErrorText(makeField.getPromptText() + " cannot be blank!");
        makeField.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    private void addErrorText(String newError) {
        if (partErrorMessage.getText().length() > 0)
            partErrorMessage.setText(partErrorMessage.getText() + "\n");
        partErrorMessage.setText(partErrorMessage.getText() + newError);
    }
}
