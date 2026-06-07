package com.skyfly.backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyfly.backend.model.User;
import com.skyfly.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // PENTING: Agar React bisa berkomunikasi dengan Java tanpa diblokir (CORS)
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Class bantuan untuk menangkap data JSON dari React
    public static class AuthRequest {
        public String identifier;
        public String namaLengkap;
        public String password;
    }

    // Menangani Register (Sesuai dengan fetch di RegisterPage.jsx)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        // Cek apakah email/nomor hp sudah terdaftar
        if (userRepository.findByIdentifier(req.identifier).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email atau Nomor HP sudah terdaftar."));
        }

        // Buat user baru menggunakan kelas PBO kita
        User newUser = new User();
        newUser.setIdentifier(req.identifier);
        newUser.setNamaLengkap(req.namaLengkap);
        newUser.setPassword(req.password);
        newUser.setRole("penumpang"); // Default role untuk pendaftar baru

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "Akun berhasil dibuat"));
    }

    // Menangani Login (Sesuai dengan fetch di LoginPage.jsx)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Optional<User> optUser = userRepository.findByIdentifier(req.identifier);

        // Cek apakah user ada dan passwordnya cocok
        if (optUser.isPresent() && optUser.get().getPassword().equals(req.password)) {
            User user = optUser.get();
            
            // React milikmu membutuhkan token JWT. 
            // Untuk sementara, kita buat token sederhana (dummy) agar React-nya bisa jalan
            String dummyToken = UUID.randomUUID().toString();

            Map<String, Object> response = new HashMap<>();
            response.put("token", dummyToken);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Login gagal. Periksa kredensial Anda."));
        }
    }
}