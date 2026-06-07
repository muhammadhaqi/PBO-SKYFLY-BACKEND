package com.skyfly.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfly.backend.model.Pesanan;

public interface PesananRepository extends JpaRepository<Pesanan, Long> {
    
    // Fitur Ajaib: Mengambil semua riwayat pesanan milik 1 penumpang saja
    List<Pesanan> findByUserId(Long userId);
}