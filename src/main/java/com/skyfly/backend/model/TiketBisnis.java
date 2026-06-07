package com.skyfly.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BISNIS")
public class TiketBisnis extends Tiket {

    @Override
    public double hitungHargaAkhir() {
        // Kelas Bisnis lebih mahal, harga dasar dikali 1.8 sesuai kodemu di React!
        return getHargaDasar() * 1.8; 
    }
}