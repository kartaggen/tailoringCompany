import DAO.EmployeeDAO;
import DAO.MaterialDAO;
import DAO.PartDAO;
import DAO.WorkOrderDAO;
import entity.Employee;
import entity.Material;
import entity.Part;
import entity.WorkOrder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLMain.fxml"));
        primaryStage.setTitle("Tailoring Company Manager");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //addDummyData(); //TODO DUMMY DATA FOR TESTING
        launch(args);
    }

    public static void addDummyData() {
        Material mat1 = new Material("Top Grain Leather", "Green", 49.99);
        Material mat2 = new Material("Embossed Grain", "Black", 42.00);
        MaterialDAO.saveOrUpdateMaterial(mat1);
        MaterialDAO.saveOrUpdateMaterial(mat2);

        Employee emp1 = new Employee("Pesho", "Peshev", true);
        Employee emp2 = new Employee("Toshko", "Toshev", false);
        EmployeeDAO.saveOrUpdateEmployee(emp1);
        EmployeeDAO.saveOrUpdateEmployee(emp2);

        Part part1 = new Part("Car seat", mat1, 4.2, 16, 10, 499.99);
        Part part2 = new Part("Car wheel", mat2, 1.9, 30, 17, 199.99);
        PartDAO.saveOrUpdatePart(part1);
        PartDAO.saveOrUpdatePart(part2);

        WorkOrder workOrder1 = new WorkOrder(emp2, part2, 2, LocalDate.now());
        WorkOrder workOrder2 = new WorkOrder(emp1, part1, 3, LocalDate.now());
        WorkOrder workOrder3 = new WorkOrder(emp2, part2, 2, LocalDate.now());
        WorkOrder workOrder4 = new WorkOrder(emp2, part1, 4, LocalDate.now());
        WorkOrder workOrder5 = new WorkOrder(emp2, part2, 1, LocalDate.now());
        WorkOrder workOrder6 = new WorkOrder(emp1, part1, 5, LocalDate.now());
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder1);
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder2);
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder3);
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder4);
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder5);
        WorkOrderDAO.saveOrUpdateWorkOrder(workOrder6);
    }
}
