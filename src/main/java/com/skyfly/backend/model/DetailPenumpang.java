package com.skyfly.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detail_penumpang")
public class DetailPenumpang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaPenumpang;

    // Relasi balik ke Pesanan (Foreign Key ke pesanan_id)
    @ManyToOne
    @JoinColumn(name = "pesanan_id", nullable = false)
    private Pesanan pesanan;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNamaPenumpang() { return namaPenumpang; }
    public void setNamaPenumpang(String namaPenumpang) { this.namaPenumpang = namaPenumpang; }

    public Pesanan getPesanan() { return pesanan; }
    public void setPesanan(Pesanan pesanan) { this.pesanan = pesanan; }
}