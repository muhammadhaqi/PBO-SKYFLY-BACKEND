package com.skyfly.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "penerbangan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kelas_penerbangan", discriminatorType = DiscriminatorType.STRING)
public abstract class Tiket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maskapai;
    private String asal;
    private String tujuan;
    private String tanggalBerangkat;
    private double hargaDasar;

    // --- PILAR 1: ENKAPSULASI (Getter & Setter) ---
    // Menyembunyikan data agar tidak bisa diubah sembarangan dari luar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaskapai() { return maskapai; }
    public void setMaskapai(String maskapai) { this.maskapai = maskapai; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }

    public String getTujuan() { return tujuan; }
    public void setTujuan(String tujuan) { this.tujuan = tujuan; }

    public String getTanggalBerangkat() { return tanggalBerangkat; }
    public void setTanggalBerangkat(String tanggalBerangkat) { this.tanggalBerangkat = tanggalBerangkat; }

    public double getHargaDasar() { return hargaDasar; }
    public void setHargaDasar(double hargaDasar) { this.hargaDasar = hargaDasar; }

    // --- PILAR 2: ABSTRAKSI ---
    // Method ini tidak punya isi di sini, tapi WAJIB diisi rumusnya oleh class anaknya nanti.
    public abstract double hitungHargaAkhir();
}