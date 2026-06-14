package myjastip.payment;

import java.util.ArrayList;

public interface Payable {
    void pay(EscrowPayment payment) throws InsufficientBalanceException ;
    void refund(Order order);
    ArrayList<EscrowPayment> getPaymentHistory();
}
