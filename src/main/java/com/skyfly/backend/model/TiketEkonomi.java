package com.skyfly.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// --- PILAR 3: INHERITANCE (Pewarisan) ---
// Kelas ini mewarisi semua sifat dari kelas Tiket
@Entity
@DiscriminatorValue("EKONOMI")
public class TiketEkonomi extends Tiket {

    // --- PILAR 4: POLIMORFISME (Overriding) ---
    @Override
    public double hitungHargaAkhir() {
        // Kelas Ekonomi tidak ada biaya tambahan (dikali 1)
        return getHargaDasar() * 1.0; 
    }
}