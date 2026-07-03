<div align="center">

<img src="https://img.shields.io/badge/-%F0%9F%9F%A2%20QuickWa-25D366?style=for-the-badge&logoColor=white" alt="QuickWa" height="50"/>

# QuickWa — Quick WhatsApp Message

**Message any Jordanian number on WhatsApp — without saving it to your contacts.**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue)](https://developer.android.com/about/versions/nougat)
[![Version](https://img.shields.io/badge/Version-1.0.0-brightgreen)](#download)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

[⬇️ Download APK](#download) • [✨ Features](#features) • [🛠️ Build](#build-from-source)

</div>

---

## 📖 About

**QuickWa** is a minimal, beautifully designed Android app that solves a common annoyance: you want to WhatsApp someone, but you don't want to permanently save their number. With QuickWa, you simply paste or type the phone number and tap **Open in WhatsApp** — done.

The app smartly handles all common Jordanian (🇯🇴) number formats, so you don't need to worry about the correct format.

---

## ✨ Features

| Feature | Details |
|---|---|
| 📋 **Smart Paste** | Detects clipboard content and offers one-tap paste |
| 🇯🇴 **Jordanian Number Support** | Accepts all common JO number formats |
| ✅ **Number Validation** | Instantly validates before opening WhatsApp |
| 📱 **WhatsApp Integration** | Opens WhatsApp directly (or browser fallback) |
| 🎨 **Modern UI** | Material You design with smooth animations |
| 🚀 **Splash Screen** | Polished branded launch experience |
| ⚡ **No Ads, No Tracking** | 100% offline, privacy-first |

---

## 📞 Accepted Number Formats

QuickWa accepts all of the following Jordanian mobile number formats:

```
0790000000       ← Local format with leading zero
790000000        ← Local format without leading zero
+962790000000    ← International format with +
00962790000000   ← International format with 00
962790000000     ← International format digits only
```

Numbers with spaces, dashes, or brackets are also supported (e.g. `079 000 0000`, `079-000-0000`).

---

## ⬇️ Download

Grab the latest APK directly from the [**Releases**](../../releases) page:

> **[📦 QuickWa-v1.0.0.apk](../../releases/latest)**

**Installation:**
1. Download the APK file
2. On your Android device, go to **Settings → Security → Install from Unknown Sources** (enable it)
3. Open the downloaded APK and follow the prompts

> ⚠️ Requires Android 7.0 (API 24) or higher

---

## 🛠️ Build From Source

### Prerequisites

- Android Studio Hedgehog (or newer)
- Android SDK with API level 36
- JDK 17

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/QaisarSec/QuickWa.git
cd QuickWa

# 2. Open in Android Studio, or build via Gradle:
./gradlew assembleDebug

# 3. Find the APK at:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material 3 |
| Architecture | Single-Activity |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## 📁 Project Structure

```
QuickWa/
├── app/
│   └── src/main/
│       ├── java/com/quickwa/
│       │   ├── MainActivity.kt       # UI + all Compose screens
│       │   └── JordanPhone.kt        # Phone number normalizer
│       └── res/
│           ├── drawable/             # App icons
│           └── values/               # Strings, themes
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an [issue](../../issues) or submit a [pull request](../../pulls).

---

## 📜 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  Made with ❤️ for the 🇯🇴 community
</div>
