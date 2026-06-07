package com.skyfly.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfly.backend.model.KursiDipesan;

import jakarta.transaction.Transactional;

public interface KursiDipesanRepository extends JpaRepository<KursiDipesan, Long> {
    // Fungsi khusus agar saat pesanan dihapus, kursi juga batal dipesan
    @Transactional
    void deleteByPesananId(Long pesananId);
}