package myjastip.payment;

import java.util.ArrayList;
import java.util.List;

public interface Payable {
    void pay(EscrowPayment payment) throws InsufficientBalanceException ;
    List<EscrowPayment> getPaymentHistory();
}
