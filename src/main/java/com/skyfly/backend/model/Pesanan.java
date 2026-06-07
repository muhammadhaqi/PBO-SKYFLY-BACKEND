package com.skyfly.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pesanan")
public class Pesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELASI PBO: Many-to-One ---
    // Banyak pesanan bisa dibuat oleh 1 User (Foreign Key ke tabel users)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String asal;
    private String tujuan;
    private String maskapai;
    private String kelas;
    private String tanggalBerangkat;
    private int jumlahPenumpang;
    private double totalHarga;
    private boolean paid;
    private String status;

    // --- ENKAPSULASI (Getter & Setter) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }

    public String getTujuan() { return tujuan; }
    public void setTujuan(String tujuan) { this.tujuan = tujuan; }

    public String getMaskapai() { return maskapai; }
    public void setMaskapai(String maskapai) { this.maskapai = maskapai; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getTanggalBerangkat() { return tanggalBerangkat; }
    public void setTanggalBerangkat(String tanggalBerangkat) { this.tanggalBerangkat = tanggalBerangkat; }

    public int getJumlahPenumpang() { return jumlahPenumpang; }
    public void setJumlahPenumpang(int jumlahPenumpang) { this.jumlahPenumpang = jumlahPenumpang; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}