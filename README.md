# RUANG SEHATI

## Sistem Peminjaman Ruang Kelas Berbasis Android

RUANG SEHATI merupakan aplikasi peminjaman ruang kelas berbasis Android yang dikembangkan untuk membantu proses pengelolaan dan peminjaman ruang kelas secara digital. Sistem ini memungkinkan pengguna melakukan pengajuan peminjaman ruang, melihat status peminjaman, serta mengakses riwayat peminjaman. Admin dapat mengelola data gedung, ruang kelas, dan melakukan validasi terhadap pengajuan peminjaman.

---

## Latar Belakang

Proses peminjaman ruang kelas yang masih dilakukan secara manual sering menimbulkan berbagai kendala seperti bentroknya jadwal penggunaan ruang, kesalahan pencatatan, serta sulitnya melakukan monitoring data peminjaman. Oleh karena itu dikembangkan aplikasi **RUANG SEHATI** sebagai solusi digital yang lebih efektif dan terstruktur.

---

## Fitur Utama

### Admin
- Login Admin
- Kelola Data Gedung
- Kelola Data Ruang Kelas
- Kelola Data Pengguna
- Melihat Data Peminjaman
- Menyetujui atau Menolak Peminjaman
- Mengelola Status Peminjaman

### User
- Login dan Registrasi
- Melihat Daftar Gedung
- Melihat Daftar Ruang Kelas
- Mengajukan Peminjaman Ruang
- Melihat Status Peminjaman
- Melihat Riwayat Peminjaman

---

## Teknologi yang Digunakan

### Frontend
- Android Studio
- Java
- XML Layout
- RecyclerView
- Material Design

### Backend
- PHP
- MySQL
- REST API

### Library
- Retrofit
- Gson
- Firebase Cloud Messaging (FCM)

---

## Struktur Database

Tabel utama yang digunakan:

- users
- gedung
- ruang
- peminjaman

---

## Cara Menjalankan Proyek

### 1. Clone Repository

```bash
git clone https://github.com/elvandojhonny/ruangsehati.git
```

### 2. Buka Project

Buka project menggunakan Android Studio.

### 3. Konfigurasi Database

Import database MySQL ke XAMPP atau server lokal yang digunakan.

### 4. Konfigurasi API

Sesuaikan URL API pada aplikasi Android dengan alamat server yang digunakan.

### 5. Jalankan Aplikasi

Hubungkan perangkat Android atau emulator kemudian jalankan aplikasi.

---

## Struktur Proyek

```text
ruangsehati/
├── app/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

---

## Tim Pengembang

**Proyek Akhir Semester**

Program Studi Informatika

Universitas Muhammadiyah Kalimantan Timur (UMKT)

---

## Lisensi

Repository ini dibuat untuk kebutuhan pembelajaran dan Proyek Akhir Semester.
