package controller;

import entity.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

import static DAO.CompanyDAO.getCompany;
import static DAO.CompanyDAO.saveOrUpdateCompany;

public class CompanyController {

    private Company company;

    @FXML
    public Label companyNameText;
    @FXML
    public Label incomeTaxText;
    @FXML
    public Label salaryExpText;
    @FXML
    public Label salaryInexpText;

    @FXML
    public TextField companyName;
    @FXML
    public TextField companyIncomeTax;
    @FXML
    public TextField salaryExperienced;
    @FXML
    public TextField salaryInexperienced;
    @FXML
    public Label errorMessage;

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private URL location;

    public CompanyController() {
    }

    @FXML
    private void initialize() {
        company = getCompany(1);
        if (location.getFile().contains("ShowCompany")) {
            companyNameText.setText(company.getName());
            incomeTaxText.setText(company.getProfitTax() + "%");
            salaryExpText.setText(company.getSalaryExp() + "");
            salaryInexpText.setText(company.getSalaryInexp() + "");
        } else {
            companyName.setText(company.getName());
            companyIncomeTax.setText(company.getProfitTax() + "");
            salaryExperienced.setText(company.getSalaryExp() + "");
            salaryInexperienced.setText(company.getSalaryInexp() + "");
        }
    }

    @FXML
    public void cancelEditDetails(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/companyViews/FXMLShowCompany.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    @FXML
    public void applyEditDetails(ActionEvent event) throws IOException {
        errorMessage.setText("");
        if (companyValidation()) {
            company.setName(companyName.getText());
            company.setProfitTax(Double.parseDouble(companyIncomeTax.getText()));
            company.setSalaryExp(Double.parseDouble(salaryExperienced.getText()));
            company.setSalaryInexp(Double.parseDouble(salaryInexperienced.getText()));
            saveOrUpdateCompany(company);

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/companyViews/FXMLShowCompany.fxml"));
            rootBorderPane.setCenter(anchorPane);
        }
    }

    public boolean companyValidation() {
        boolean test1 = companyNameValidation();
        boolean test2 = companyIncomeTaxValidation();
        boolean test3 = companySalaryValidation(salaryExperienced);
        boolean test4 = companySalaryValidation(salaryInexperienced);
        return test1 && test2 && test3 && test4;
    }

    public boolean companyNameValidation() {
        companyName.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!companyName.getText().isEmpty() && companyName.getText().length() <= 45) return true;
        companyName.setStyle("-fx-border-color: #d17171 ;");
        addErrorText("Company name cannot be blank!");
        return false;
    }

    public boolean companyIncomeTaxValidation() {
        companyIncomeTax.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!companyIncomeTax.getText().isEmpty()) {
            try {
                double incTax = Double.parseDouble(companyIncomeTax.getText());
                if ((incTax + "").substring((incTax + "").indexOf(".")).length() > 3)
                    addErrorText("Income tax should have only 2 digits after decimal point! Example: 100.00");
                else if (incTax >= 0 && incTax < 100) {
                    return true;
                } else
                    addErrorText("Company income tax should be a between 0 and 100! Example: 20.00");
            } catch (NumberFormatException exception) {
                addErrorText("Company income tax should be a number! Example: 20.00");
            }
        } else addErrorText("Company income tax cannot be blank!");
        companyIncomeTax.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    public boolean companySalaryValidation(TextField salaryField) {
        salaryField.setStyle("-fx-border-color: #6dc5c7 ;");
        if (!salaryField.getText().isEmpty()) {
            try {
                double salary = Double.parseDouble(salaryField.getText());
                if ((salary + "").substring((salary + "").indexOf(".")).length() > 3)
                    addErrorText("Salary should have only 2 digits after decimal point! Example: 100.00");
                else if (salary > 0 && salary < 100000) {
                    return true;
                } else
                    addErrorText(salaryField.getPromptText() + " should be between 0 and 100000! Example: 1200.00");
            } catch (NumberFormatException exception) {
                addErrorText(salaryField.getPromptText() + " should be a number! Example: 1200.00");
            }
        } else addErrorText(salaryField.getPromptText() + " cannot be blank!");
        salaryField.setStyle("-fx-border-color: #d17171 ;");
        return false;
    }

    private void addErrorText(String newError) {
        if (errorMessage.getText().length() > 0)
            errorMessage.setText(errorMessage.getText() + "\n");
        errorMessage.setText(errorMessage.getText() + newError);
    }
}
