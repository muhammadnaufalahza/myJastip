package myjastip.payment;

import java.util.ArrayList;

public interface Payable {
    public void payment(double amount);
    public void refund (long orderId);
    ArrayList<Payment> getPaymentHistory();
}
