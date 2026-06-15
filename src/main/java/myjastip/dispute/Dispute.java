package myjastip.dispute;

import java.util.ArrayList;
import java.util.List;

public class Dispute {
    private String disputeId;
    private String paymentId;
    private String customerId;
    private String jastiperId;
    private String reason;
    private DisputeStatus status;
    private List<Evidence> evidences;

    public Dispute(String disputeId, String paymentId, String customerId, String jastiperId, String reason) {
        this.disputeId = disputeId;
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.jastiperId = jastiperId;
        this.reason = reason;
        this.status = DisputeStatus.OPEN;
        this.evidences = new ArrayList<>();
    }


    public String getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(String disputeId) {
        this.disputeId = disputeId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DisputeStatus getStatus() {
        return status;
    }

    public void setStatus(DisputeStatus status) {
        this.status = status;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public String getJastiperId() {
        return jastiperId;
    }

    public void setJastiperId(String jastiperId) {
        this.jastiperId = jastiperId;
    }
}
