# Peminjaman Ruang (Android)

## Catatan perubahan terbaru

| Area | Perubahan |
|------|-----------|
| **Logo** | `res/drawable/logo.png` diganti menjadi versi **bulat** dengan area luar **transparan** (sumber sebelumnya `logo.jpeg` dihapus agar tidak bentrok nama resource). Tetap dipakai sebagai `@drawable/logo` di `ImageView`. |
| **Biometrik** | Seluruh fitur **BiometricPrompt / fingerprint / face unlock** dan penyimpanan kredensial terenkripsi untuk login cepat **dihapus**. Dependency `androidx.biometric` dan `security-crypto` dilepas; kelas `SavedLoginStore` dihapus. |
| **Dashboard** | Layout **dashboard user & admin** memakai tema **biru gelap** (`#0D1B2A` dan gradasi terkait) dengan aksen **emas** (`#FFD700`). Kartu, navigasi bawah, tombol, dan teks disesuaikan kontrasnya. |
| **Tema global** | **`Theme.PeminjamanRuang`** kini memakai **Material 3 Dark** dengan token dashboard (`colorBackground`, `colorSurface`, `colorPrimary` / `colorSecondary` = emas, teks lewat `textColorPrimary` / `textColorSecondary`). Semua **`activity_*.xml`** dan **`item_*.xml`** memakai `?android:attr/colorBackground`, teks dari atribut tema, kartu/tombol dari **`styles.xml`** (`Widget.PeminjamanRuang.Card`, `Button.Primary`, `Button.Ghost`, dll.). Logo di layar utama dipamerkan dengan **`bg_dashboard_logo_circle`** + **`centerCrop`** agar konsisten dengan dashboard. |

**Commit message disarankan:**

```
feat(theme): align entire app with dashboard dark blue + gold

- Theme.Material3.Dark: dashboard palette + reusable card/button/text styles
- All activity/item layouts use theme color attrs; shared chip/input/hero drawables
- Logo framing consistent (circular PNG + ring where shown)
```

## Build

Buka proyek di Android Studio dan jalankan **Build > Make Project**, atau:

```bash
./gradlew :app:assembleDebug
```
