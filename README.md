# 👾 PAC-MAN — Java, Design Patterns & Testing

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white) ![JUnit](https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white) ![Grade](https://img.shields.io/badge/Grade-18%2F20-green)

A text-based **PAC-MAN** game built with the Lanterna library. The focus is on **clean architecture, design patterns and automated testing**.

> Project for **Software Design and Testing Lab (LDTS)** — BSc in Informatics and Computing Engineering (L.EIC), FEUP, 2025/2026. **Final grade: 18/20.**

<p align="center">
  <img src="Docs/IMG/Videos/Level1.gif" width="380" alt="Level 1"/>
  <img src="Docs/IMG/Menus/Menu%20inicial.png" width="380" alt="Main menu"/>
</p>

## ✨ Features
- Continuous PAC-MAN movement with wall collisions
- Ghosts that chase the player, and **power pellets** that give temporary immunity
- **3 levels** with different layouts and increasing difficulty
- Main menu, level selection and instructions screens

## 🧩 Design patterns
| Pattern | Where it is used |
|---|---|
| **MVC** | Clear split between game state (Model), rendering (View) and input/logic (Controller) |
| **State** | Main menu, game, instructions and level select are separate states |
| **Factory Method + Builder** | One `LevelBuilder` per level (walls, ghosts, speed) |
| **Adapter** | `GraphicInterface` hides Lanterna, so views can be tested with mocks |
| **Command / Observer** | Menu buttons and input listeners |
| **Game Loop** | Fixed frame time, independent of machine speed |

Full rationale and UML diagrams: [README_PT.md](README_PT.md).

## 🧪 Testing
- **Unit tests with JUnit and Mockito**, using mocks for complex interactions
- **Mutation testing with PITest**, to measure how good the tests really are
- Code coverage report

## ▶️ How to run
```bash
./gradlew run
./gradlew test
```

## 👤 My contribution
- Game controller and arena logic: continuous movement, fixing the "tunneling" collision bug
- Views and game elements (walls, points, PAC-MAN, ghosts)
- **Unit tests with mocks and PITest mutation testing**
- Documentation of the MVC architecture and the design patterns

## 👥 Team
André Rocha · Catarina Mendes · Rodrigo Monteiro
