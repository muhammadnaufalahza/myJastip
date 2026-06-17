package myjastip.app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myjastip.payment.EscrowPayment;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;

public class MyJastipWindow extends Application {

    private Stage primaryStage;
    private AuthView authView;
    private DashboardView dashboardView;
    private CustomerDashboardView customerDashboardView;
    private JastiperDashboardView jastiperDashboardView;
    private StoreView storeView;
    private CustomerOrdersView customerOrdersView;
    private JastiperOrderView jastiperOrderView;
    private PaymentView paymentView;
    private PaymentHistoryView paymentHistoryView;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("myJastip");

        this.authView = new AuthView(this);
        this.dashboardView = new DashboardView(this);
        this.customerDashboardView = new CustomerDashboardView(this);
        this.jastiperDashboardView = new JastiperDashboardView(this);
        this.storeView = new StoreView(this);
        this.customerOrdersView = new CustomerOrdersView(this);
        this.jastiperOrderView = new JastiperOrderView(this);
        this.paymentView = new PaymentView(this);
        this.paymentHistoryView = new PaymentHistoryView(this);

        showLoginScene();
        this.primaryStage.show();
    }

    public void showLoginScene() {
        primaryStage.setScene(authView.getLoginScene());
    }

    public void showRegisterScene() {
        primaryStage.setScene(authView.getRegisterScene());
    }

    public void showDashboardScene(User user) {
        dashboardView.setUser(user);
        primaryStage.setScene(dashboardView.getDashboardScene());
    }

    public void showCustomerDashboardScene(Customer customer) {
        customerDashboardView.setCustomer(customer);
        primaryStage.setScene(customerDashboardView.getDashboardScene());
    }

    public void showJastiperDashboardScene(Jastiper jastiper) {
        jastiperDashboardView.setJastiper(jastiper);
        primaryStage.setScene(jastiperDashboardView.getDashboardScene());
    }


    public void showStoreScene(Customer customer) {
        storeView.setCustomer(customer);
        primaryStage.setScene(storeView.getStoreScene());
    }

    public void showCustomerOrdersScene(Customer customer) {
        customerOrdersView.setCustomer(customer);
        primaryStage.setScene(customerOrdersView.getCustomerOrdersScene());
    }

    public void showJastiperOrderScene(Jastiper jastiper) {
        jastiperOrderView.setJastiper(jastiper);
        primaryStage.setScene(jastiperOrderView.getJastiperOrderScene());
    }

    public void showPaymentScene(Customer customer, EscrowPayment payment) {
        paymentView.setCustomer(customer);
        paymentView.setPayment(payment);
        primaryStage.setScene(paymentView.getPaymentScene());
    }

    public void showPaymentHistoryScene(Customer customer) {
        paymentHistoryView.setCustomer(customer);
        primaryStage.setScene(paymentHistoryView.getPaymentHistoryScene());
    }

}
