package controller;

import entity.Company;
import entity.WorkOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.List;

import static DAO.CompanyDAO.getCompany;
import static DAO.WorkOrderDAO.getWorkOrders;

public class AccountingController {

    @FXML
    public Label incomeText;

    @FXML
    public Label expensesSalariesText;

    @FXML
    public Label expensesMaterialsText;

    @FXML
    public Label profitText;

    @FXML
    public Label netProfitText;

    public AccountingController() {
    }

    @FXML
    private void initialize() {
        Company company = getCompany(1);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double income = 0;
        double expensesSalaries = 0;
        double expensesMaterials = 0;

        List<WorkOrder> allWorkOrders = getWorkOrders();
        for (WorkOrder wo : allWorkOrders) {
            income += wo.getQuantity() * wo.getPart().getSellPrice();
            if (wo.getEmployee().isExperienced())
                expensesSalaries += (double) wo.getQuantity() / wo.getPart().getExpMakePerMonth() * company.getSalaryExp();
            else
                expensesSalaries += (double) wo.getQuantity() / wo.getPart().getInexpMakePerMonth() * company.getSalaryInexp();
            expensesMaterials += wo.getQuantity() * wo.getPart().getMaterialNeededSqm() * wo.getPart().getMaterial().getPricePerSqM();
        }

        double profit = income - (expensesSalaries + expensesMaterials);
        double netProfit = profit * (100 - company.getProfitTax()) / 100;

        incomeText.setText(df.format(income) + "");
        expensesSalariesText.setText(df.format(expensesSalaries) + "");
        expensesMaterialsText.setText(df.format(expensesMaterials) + "");
        profitText.setText(df.format(profit) + "");
        netProfitText.setText(df.format(netProfit) + "");
    }
}
