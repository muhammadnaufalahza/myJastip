package myjastip.dispute;

public class DisputeMain {
    public static void main(String[] args) {
        // Inisialisasi Sistem Pusat Resolusi
        ResolutionCenter center = new ResolutionCenter();
        System.out.println("=== KASUS 1: BARANG TITIPAN RUSAK ===");
        // 1. Buyer mengajukan sengketa karena barang rusak sesampainya di rumah
        center.openDispute("DISP-001", "TX-10922", "BUYERANDI", "SHOPPER_BUDI", "Sepatu robek di sisi kanan.");
        // 2. Buyer menyertakan bukti video unboxing
        center.submitEvidence("DISP-001", "BUYER ANDI", "Video unboxing saat paket pertama dibuka");

        // 3. Admin merespons dan memeriksa kasus
        center.reviewCase("DISP-001");

        // 4. Keputusan Akhir: Pengembalian dana penuh ke pembeli karena bukti sah
        center.resolveDispute( "DISP-001", DisputeStatus.RESOLVED_REFUND);
    }
}
