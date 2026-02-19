# News Feed Simulator

**Nama:** Muhamad Arif Ardani  
**NIM:** 123140186  
**Mata Kuliah:** Pengembangan Aplikasi Mobile RB

---

## 📝 Deskripsi
Aplikasi **News Feed Simulator** adalah sebuah simulasi aplikasi berita yang dibangun menggunakan Kotlin Multiplatform (Compose). Proyek ini berfokus pada implementasi pemrograman asinkron tingkat lanjut menggunakan **Kotlin Coroutines**, **Flow**, dan **StateFlow** untuk mengelola aliran data berita secara real-time.

---

## 📸 Screenshot Aplikasi
*<img width="522" height="1158" alt="Image" src="https://github.com/user-attachments/assets/e3164e68-7c8e-4317-a66d-d7939bfe1891" />*

---

## ✨ Fitur Aplikasi
1. **Flow Stream**: Menggunakan `flow {}` builder dan `emit()` untuk mengirim data berita setiap 2 detik.
2. **Flow Operators**: 
    - `filter`: Menyaring berita berdasarkan kategori (Tech, Sports, Politics).
    - `map`: Mengubah format konten berita secara dinamis.
    - `onEach`: Melakukan logging setiap kali berita diproses.
3. **StateFlow**: Manajemen state untuk menghitung jumlah berita yang telah dibaca oleh pengguna secara reaktif.
4. **Coroutines**: Simulasi pengambilan detail berita menggunakan `async` dan `await` dengan `Dispatchers.Default` untuk performa optimal.
5. **Modern UI**: Antarmuka berbasis Material 3 dengan tema **Emerald Green** yang bersih dan minimalis.

---

## 🏗️ Struktur Aplikasi
- **NewsRepository.kt**: Menangani sumber data, Flow builder, operator, dan simulasi network call (async/await).
- **NewsViewModel.kt**: Mengelola StateFlow untuk kategori yang dipilih dan counter berita dibaca.
- **App.kt**: Layer UI yang melakukan `collect` pada Flow dan menampilkan data menggunakan Jetpack Compose.

---

## 🚀 Cara Menjalankan Project
1. Buka project menggunakan **Android Studio (Ladybug atau versi terbaru)**.
2. Pastikan koneksi internet stabil untuk sinkronisasi Gradle.
3. Klik tombol **Sync Project with Gradle Files**.
4. Pilih target perangkat (**Emulator** atau **Physical Device Android**).
5. Klik tombol **Run 'composeApp'** (ikon Play hijau).
6. Buka **Logcat** untuk melihat proses `onEach` logging saat berita di-emit.
