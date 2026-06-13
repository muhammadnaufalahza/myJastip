package myjastip.payment;

import java.util.ArrayList;

public interface Payable {
    void payment(double amount);
    void refund (long orderId);
    ArrayList<Payment> getPaymentHistory();
}
