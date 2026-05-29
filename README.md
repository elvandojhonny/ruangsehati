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

MIT License

Copyright (c) 2026 AI  BOT

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
