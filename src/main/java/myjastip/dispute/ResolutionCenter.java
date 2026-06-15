package myjastip.dispute;

import myjastip.db.DatabaseUtil;

import java.util.HashMap;
import java.util.Map;

public class ResolutionCenter {
    private Map<String, Dispute> disputeRegistry;

    public ResolutionCenter() {
        this.disputeRegistry = new HashMap<>();
    }

    // Alur 1: Membuka Kasus Sengketa Baru
    public void openDispute(String disputeId, String paymentId, String customerId, String jastiperId, String reason) {
        Dispute dispute = new Dispute(disputeId, paymentId, customerId, jastiperId, reason);
        disputeRegistry.put(disputeId, dispute);
        System.out.println("[SYSTEM]: Kasus "+ disputeId + " berhasil dibat oleh (" + customerId + ").");
    }

    // Alur 2
    public void submitEvidence(String disputeId, String customerId, String description) {
        Evidence evidence = new Evidence(disputeId, customerId, description);
    }

    // Alur 3
    public void reviewCase(String disputeId) {
        Dispute dispute = disputeRegistry.get(disputeId);
        if (dispute != null && dispute.getStatus() == DisputeStatus.OPEN) {
            dispute.setStatus(DisputeStatus.UNDER_REVIEW);
            System.out.println("[SYSTEM]: Status kasus " + disputeId + " berubah menjadi UNDER REVIEW");
        }
    }

    // Alur 4
    public void resolveDispute (String disputeId, DisputeStatus decision) {
        Dispute dispute = disputeRegistry.get(disputeId);
        if (dispute == null) {
            System.out.println("[ERROR]: Kasus tidak ditemukan.");
            return;
        }

        dispute.setStatus(decision);

        System.out.println("KEPUTUSAN PUSAT RESOLUSI");
        System.out.println("Dispute ID : " + disputeId);
        System.out.println("Hasil Keputusan : " + decision);

        // Logika Pengaliran Dana Berdasarkan Hasil Keputusan Sengketa
        if (decision == DisputeStatus.RESOLVED_REFUND) {
            System.out.println("[ESCROW ACTION] : Dana 100% DIKEMBALIKAN ke Rekening Pembeli (" + dispute.getCustomerId() + ")");
        } else if (decision == DisputeStatus.RESOLVED_RELEASE) {
            System.out.println("[ESCROW ACTION] : Komplain ditolak. Dana DICAIIRKAN ke Rekening Jastiper (" + dispute.getJastiperId() + ")");
        }
    }
}

