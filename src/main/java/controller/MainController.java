package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane rootBorderPane;

    public MainController() {
    }

    @FXML
    private void initialize() {
        AnchorPane anchorPane;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/companyViews/FXMLShowCompany.fxml"));
            rootBorderPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadShowCompanyAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/companyViews/FXMLShowCompany.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadEditCompanyAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/companyViews/FXMLEditCompany.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadMenuEmployeesAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/revisionViews/FXMLMenuEmployees.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadMenuWorkOrdersAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/revisionViews/FXMLMenuWorkOrders.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadMenuPartsAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/revisionViews/FXMLMenuParts.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadMenuMaterialsAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/revisionViews/FXMLMenuMaterials.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadViewAccountingAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reportViews/FXMLViewAccounting.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void loadViewAllPartsAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reportViews/FXMLViewAllParts.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

}
