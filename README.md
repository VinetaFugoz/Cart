# 🛒 Cart

> Android app to view and manage cart lists with products.
> Built with **Kotlin**, **Jetpack Compose**, modular architecture and **MVI** pattern.

**Kotlin** · **Android 26+** · **Jetpack Compose**
 
---

## 📱 Preview

<div align="center">

|                                                                                                             Light                                                                                                              |                                                                                                                                   Dark                                                                                             |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://raw.githubusercontent.com/VinetaFugoz/Cart/main/screenshots/list.png" width="280" alt="Cart list (light)" style="border-radius: 12px; border: 1px solid #e1e4e8; box-shadow: 0 4px 12px rgba(0,0,0,0.1);" /> | <img src="https://raw.githubusercontent.com/VinetaFugoz/Cart/main/screenshots/list_dark.png" width="280" alt="Cart list (dark)" style="border-radius: 12px; border: 1px solid #e1e4e8; box-shadow: 0 4px 12px rgba(0,0,0,0.1);" /> |

</div>

---
## 📖 About the project

**Cart** displays a list of carts loaded from a JSON file (in assets). Each cart contains multiple products with **image**, **name**, **price** and **quantity**. You can remove a cart with a confirmation dialog. The UI is 100% **Jetpack Compose** and state is handled with **MVI** and **StateFlow**.

### Features

- List of multiple carts
- Cards per cart with product list and images (Coil)
- Remove cart with confirmation dialog
- Loading and error handling on screen
- Layered architecture (domain, data, feature, app)

### Stack

| Layer      | Technologies                     |
|------------|----------------------------------|
| **UI**     | Jetpack Compose, Material 3      |
| **State**  | MVI, StateFlow, ViewModel        |
| **Images** | Coil 3 + OkHttp                  |
| **DI**     | Koin                             |
| **Data**   | JSON in assets, repository layer |

### Architecture

```
app     → Application, theme, Coil singleton
feature → UI (CartListScreen), ViewModel, MVI (Intent/State)
domain  → models (Cart, Product), use cases, repository contract
data    → repository, DTOs, mapping, JSON loading
```

---

## 🚀 How to run

1. Clone the repository:
   ```bash
   git clone https://github.com/VinetaFugoz/Cart.git
   cd Cart
   ```
2. Open the project in **Android Studio** (Hedgehog or newer).
3. Sync Gradle and run on an emulator or device (minSdk 26).

---

## 📁 Project structure

```
Cart/
├── app/          # Application, MainActivity, theme
├── feature/      # CartListScreen, ViewModel, components (CartCard, ProductCard)
├── domain/       # Cart, Product, use cases, CartListRepository
├── data/         # CartListRepositoryImpl, DTOs, mapping, cart_list.json
└── screenshots/  # Images for the README
```

---

*Built with Kotlin and Jetpack Compose.*