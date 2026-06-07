package com.skyfly.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyfly.backend.model.DetailPenumpang;
import com.skyfly.backend.model.KursiDipesan;
import com.skyfly.backend.model.Pesanan;
import com.skyfly.backend.model.User;
import com.skyfly.backend.repository.DetailPenumpangRepository;
import com.skyfly.backend.repository.KursiDipesanRepository;
import com.skyfly.backend.repository.PesananRepository;
import com.skyfly.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/pesanan")
@CrossOrigin(origins = "*") // Izinkan React mengakses API ini
public class PesananController {

    @Autowired
    private PesananRepository pesananRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailPenumpangRepository detailPenumpangRepository;
    @Autowired
    private KursiDipesanRepository kursiDipesanRepository;

    // Format Data JSON
    public static class PesananRequest {
        public Long userId;
        public String email;
        public String asal;
        public String tujuan;
        public String maskapai;
        public String kelas;
        public String tanggalBerangkat;
        public int penumpang;
        public double total;
        public List<String> namaPenumpang;
        public List<String> kursi;
    }

    // ============================================
    // 1. CREATE: MENYIMPAN PESANAN BARU
    // ============================================
    @PostMapping
    public ResponseEntity<?> buatPesanan(@RequestBody PesananRequest req) {
        Optional<User> optUser = userRepository.findById(req.userId);
        if (!optUser.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "User tidak valid."));
        }

        Pesanan p = new Pesanan();
        p.setUser(optUser.get());
        p.setAsal(req.asal);
        p.setTujuan(req.tujuan);
        p.setMaskapai(req.maskapai);
        p.setKelas(req.kelas);
        p.setTanggalBerangkat(req.tanggalBerangkat);
        p.setJumlahPenumpang(req.penumpang);
        p.setTotalHarga(req.total);
        p.setStatus("Menunggu Pembayaran");
        p.setPaid(false);

        Pesanan savedPesanan = pesananRepository.save(p);

        for (String nama : req.namaPenumpang) {
            DetailPenumpang dp = new DetailPenumpang();
            dp.setNamaPenumpang(nama);
            dp.setPesanan(savedPesanan);
            detailPenumpangRepository.save(dp);
        }

        for (String noKursi : req.kursi) {
            KursiDipesan kd = new KursiDipesan();
            kd.setNomorKursi(noKursi);
            kd.setPesanan(savedPesanan);
            kursiDipesanRepository.save(kd);
        }

        return ResponseEntity.ok(Map.of("success", true));
    }

    // ============================================
    // 2. READ: MENGAMBIL DATA UNTUK HALAMAN RIWAYAT (SUDAH DIFILTER!)
    // ============================================
    @GetMapping("/riwayat")
    public ResponseEntity<?> getRiwayat(@RequestParam Long userId) {
        // HANYA mengambil pesanan berdasarkan userId
        List<Pesanan> pesananUser = pesananRepository.findByUserId(userId);
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Pesanan p : pesananUser) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("namaPemesan", p.getUser().getNamaLengkap());
            map.put("asal", p.getAsal());
            map.put("tujuan", p.getTujuan());
            map.put("maskapai", p.getMaskapai());
            map.put("kelas", p.getKelas());
            map.put("tanggalBerangkat", p.getTanggalBerangkat());
            map.put("total", p.getTotalHarga());
            map.put("totalFormatted", "Rp " + String.format("%,.0f", p.getTotalHarga()));
            map.put("status", p.getStatus());
            map.put("paid", p.isPaid());
            responseList.add(map);
        }

        return ResponseEntity.ok(Map.of("success", true, "data", responseList));
    }

    // ============================================
    // 3. UPDATE: BAYAR DAN REFUND TIKET
    // ============================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePesanan(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Optional<Pesanan> opt = pesananRepository.findById(id);
        if (opt.isPresent()) {
            Pesanan p = opt.get();
            if (payload.containsKey("paid")) {
                p.setPaid((Boolean) payload.get("paid"));
            }
            if (payload.containsKey("status")) {
                p.setStatus((String) payload.get("status"));
            }
            pesananRepository.save(p);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.badRequest().body(Map.of("success", false));
    }

    // ============================================
    // 4. DELETE: MENGHAPUS PESANAN
    // ============================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePesanan(@PathVariable Long id) {
        detailPenumpangRepository.deleteByPesananId(id);
        kursiDipesanRepository.deleteByPesananId(id);
        pesananRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true));
    }
    
    // ============================================
    // 5. FITUR ADMIN (MENGAMBIL SEMUA PESANAN)
    // ============================================
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllAdmin() {
        List<Pesanan> semuaPesanan = pesananRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Pesanan p : semuaPesanan) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("namaPemesan", p.getUser().getNamaLengkap());
            map.put("asal", p.getAsal());
            map.put("tujuan", p.getTujuan());
            map.put("maskapai", p.getMaskapai());
            map.put("kelas", p.getKelas());
            map.put("tanggalBerangkat", p.getTanggalBerangkat());
            map.put("total", p.getTotalHarga());
            map.put("totalFormatted", "Rp " + String.format("%,.0f", p.getTotalHarga()));
            map.put("status", p.getStatus());
            map.put("paid", p.isPaid());
            responseList.add(map);
        }

        return ResponseEntity.ok(Map.of("success", true, "data", responseList));
    }
}