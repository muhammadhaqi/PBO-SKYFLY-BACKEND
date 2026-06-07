package com.skyfly.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfly.backend.model.DetailPenumpang;

import jakarta.transaction.Transactional;

public interface DetailPenumpangRepository extends JpaRepository<DetailPenumpang, Long> {
    // Fungsi khusus agar saat pesanan dihapus, nama penumpang juga ikut terhapus otomatis
    @Transactional
    void deleteByPesananId(Long pesananId);
}