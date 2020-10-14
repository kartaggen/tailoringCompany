package controller;

import DAO.MaterialDAO;
import DAO.PartDAO;
import entity.Material;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaterialController {

    private ObservableList<Material> listItems;

    @FXML
    private TableView materialsListTableView;

    @FXML
    private TableColumn materialsListTableViewIdColumn;

    @FXML
    public TableColumn materialsListTableViewNameColumn;

    @FXML
    public TableColumn materialsListTableViewColorNameColumn;

    @FXML
    public TableColumn materialsListTableViewPricePerSqmColumn;

    @FXML
    private Label materialIdLabel;

    @FXML
    private TextField materialNameText;

    @FXML
    private TextField materialColorText;

    @FXML
    private TextField materialPricePerSqmText;

    @FXML
    private Label materialErrorMessage;

    public MaterialController() {
    }

    @FXML
    private void initialize() {
        loadMaterials();
    }

    public void loadMaterials() {
        listItems = FXCollections.observableArrayList(MaterialDAO.getMaterials());
        materialsListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Material, Long>("id"));
        materialsListTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
        materialsListTableViewColorNameColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("color"));
        materialsListTableViewPricePerSqmColumn.setCellValueFactory(new PropertyValueFactory<Material, Double>("pricePerSqM"));
        materialsListTableView.setItems(listItems);
    }

    @FXML
    public void saveMaterialAction(ActionEvent actionEvent) {
        Material material = new Material();
        if (materialIdLabel.getText().length() > 0) {
            material.setId(Long.parseLong(materialIdLabel.getText()));
        }
        materialErrorMessage.setText("");
        if (materialValidation()) {
            material.setName(materialNameText.getText());
            material.setColor(materialColorText.getText());
            material.setPricePerSqM(Double.parseDouble(materialPricePerSqmText.getText()));
            MaterialDAO.saveOrUpdateMaterial(material);
            loadMaterials();
            clearMaterialForm();
        }
    }

    @FXML
    public void editMaterialAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Material> selectionModel = materialsListTableView.getSelectionModel();
        clearMaterialForm();
        if (!selectionModel.isEmpty()) {
            Material material = selectionModel.getSelectedItem();
            materialIdLabel.setText(Long.toString(material.getId()));
            materialNameText.setText(material.getName());
            materialColorText.setText(material.getColor());
            materialPricePerSqmText.setText(material.getPricePerSqM() + "");
        } else materialErrorMessage.setText("A material must be selected before clicking edit!");
    }

    @FXML
    public void deleteMaterialAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Material> selectionModel = materialsListTableView.getSelectionModel();
        Material material = selectionModel.getSelectedItem();
        if (material != null && material.getId() > 0) {
            if (PartDAO.getPartsBy(material.getId()).isEmpty()) {
                MaterialDAO.deleteMaterial(material);
                materialsListTableView.getItems().remove(material);
            } else materialErrorMessage.setText("All parts consisting of this material must be deleted first!");
        } else materialErrorMessage.setText("A material must be selected before clicking delete!");
    }

    @FXML
    public void clearMaterialForm() {
        materialErrorMessage.setText("");
        materialIdLabel.setText("");
        materialNameText.clear();
        materialColorText.clear();
        materialPricePerSqmText.clear();

        materialNameText.setStyle("-fx-border-color: #6dc5c7 ;");
        materialColorText.setStyle("-fx-border-color: #6dc5c7 ;");
        materialPricePerSqmText.setStyle("-fx-border-color: #6dc5c7 ;");
    }

    public boolean materialValidation() {
        boolean test1 = materialNameValidation(materialNameText);
        boolean test2 = materialNameValidation(materialColorText);
        boolean test3 = materialPricePerSqmValidation();
        return test1 && test2 && test3;
    }

    public boolean materialNameValidation(TextField textField) {
        textField.setStyle("-fx-border-color: #6dc5c7 ;");
        if (textField.getText().isEmpty() || textField.getText().length() > 20) {
            textField.setStyle("-fx-border-color: #d17171 ;");
            addErrorText(textField.getPromptText() + " cannot be blank and has to be up to 20 characters!");
            return false;
        } else return true;
    }

    public boolean materialPricePerSqmValidation() {
        materialPricePerSqmText.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!materialPricePerSqmText.getText().isEmpty()) {
            try {
                double price = Double.parseDouble(materialPricePerSqmText.getText());
                if ((price + "").substring((price + "").indexOf(".")).length() > 3)
                    addErrorText("Price per sq.m. should have only 2 digits after decimal point! Example: 100.00");
                else if (price > 0 && price <= 10000) {
                    return true;
                } else
                    addErrorText("Price per sq.m. should be between 0 and 10000! Example: 100.00");
            } catch (NumberFormatException exception) {
                addErrorText("Price per sq.m. should be a number! Example: 100.00");
            }
        } else addErrorText("Price per sq.m. cannot be blank!");
        materialPricePerSqmText.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    private void addErrorText(String newError) {
        if (materialErrorMessage.getText().length() > 0)
            materialErrorMessage.setText(materialErrorMessage.getText() + "\n");
        materialErrorMessage.setText(materialErrorMessage.getText() + newError);
    }
}
