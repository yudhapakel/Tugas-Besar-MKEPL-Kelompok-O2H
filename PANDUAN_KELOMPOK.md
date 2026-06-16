# Panduan Kerja Kelompok - Tugas Besar MKEPL

Repositori ini telah dikonfigurasi menggunakan **Java Spring Boot (Maven)** untuk REST API **Product Catalog** sederhana yang terintegrasi dengan database in-memory H2. 

Pipeline CI/CD lengkap juga sudah dikonfigurasi di `.github/workflows/ci.yml`.

---

## Pembagian Peran Anggota Kelompok

Setiap anggota kelompok bertanggung jawab untuk menjelaskan dan mendemonstrasikan **satu komponen pipeline** pada video presentasi.

### 👤 Anggota 1: Continuous Integration (CI)
* **Fokus:** Melakukan kompilasi program dan pembersihan dependensi.
* **Script Workflow:** Job `continuous-integration`
* **Cara Kerja:**
  * Melakukan checkout repository menggunakan `actions/checkout@v4`.
  * Setup Java 17 Temurin dan mengaktifkan caching Maven `cache: 'maven'` agar build berikutnya lebih cepat.
  * Menjalankan perintah `mvn clean compile` untuk memastikan kode terkompilasi dengan sukses tanpa error.

### 👤 Anggota 2: Continuous Testing (CT)
* **Fokus:** Menjalankan pengujian otomatis dan analisis cakupan kode (coverage).
* **Script Workflow:** Job `continuous-testing`
* **Cara Kerja:**
  * Menjalankan perintah unit test otomatis dengan `mvn clean test`.
  * Unit test menggunakan JUnit 5 pada file `ProductServiceTest.java`.
  * Menghasilkan laporan coverage menggunakan plugin **JaCoCo** (`jacoco-maven-plugin` yang dikonfigurasi di `pom.xml`).
  * Mengunggah laporan JaCoCo sebagai artifact pipeline menggunakan action `actions/upload-artifact@v4` agar bisa diunduh dan diperiksa di tab *Actions* GitHub.

### 👤 Anggota 3: Continuous Inspection
* **Fokus:** Melakukan verifikasi kualitas dan keamanan kode (security/vulnerability scan).
* **Script Workflow:** Job `continuous-inspection`
* **Cara Kerja:**
  * Menjalankan vulnerability scanner **Trivy** (`aquasecurity/trivy-action@v0.36.0`) pada filesystem proyek.
  * Memindai jika ada dependensi library Java yang memiliki celah keamanan kritis/tinggi/menengah.
  * Mengeluarkan output tabel hasil scan di logs GitHub Actions.

### 👤 Anggota 4: Continuous Deployment/Delivery (CD)
* **Fokus:** Melakukan otomatisasi rilis program.
* **Script Workflow:** Job `continuous-deployment`
* **Cara Kerja:**
  * Job ini hanya berjalan jika job CT dan Inspection sukses, berada di branch `main`, dan triggered via push event.
  * Melakukan build paket final executable JAR dengan perintah `mvn clean package -DskipTests`.
  * Menggunakan tool `softprops/action-gh-release@v2` untuk membuat entri **GitHub Releases** secara otomatis dengan tag dinamis berdasarkan timestamp tanggal/waktu rilis (misal `v2026.06.12.xxxx`).
  * Mengunggah file `.jar` hasil build ke dalam aset GitHub Releases sehingga siap diunduh dan dijalankan.

---

## Cara Berkontribusi Secara Git (Best Practice MKEPL)

Untuk menunjukkan penggunaan Git yang matang saat presentasi:
1. **Jangan push langsung ke `main`.**
2. Buat branch baru untuk setiap fitur/perbaikan:
   ```bash
   git checkout -b feature/tambah-fitur-baru
   ```
3. Lakukan commit dengan pesan deskriptif:
   ```bash
   git commit -m "feat: tambahkan endpoint update produk"
   ```
4. Push branch ke GitHub dan buat **Pull Request (PR)** ke branch `main`.
5. Amati jalannya pipeline GitHub Actions pada halaman Pull Request. Tunjukkan pada video presentasi bahwa pipeline mendeteksi PR tersebut dan melakukan tes otomatis untuk menjamin tidak ada kode yang merusak sistem sebelum di-merge.     






