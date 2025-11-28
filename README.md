
# X-O Tournament

A modern, tournament-style take on the classic Tic-Tac-Toe game, built for Android. Play locally with a friend, track your statistics, and compete to become the ultimate tournament champion.

## 📥 Download & Installation

### For End Users (Recommended)

-   **Download the latest release**: Go to the [Releases section](https://github.com/Burden19/XO/releases/tag/v0.1.0) of this repository

-   Download the `.apk` file from the latest release

-   Install on your Android device

-   Enable "Install from unknown sources" if prompted


### For Developers (Debugging & Contribution)

bash

# Clone the repository


# Open in Android Studio
# Or build directly using Gradle
./gradlew build

## 🛠️ Development Setup

### Prerequisites

-   Android Studio Arctic Fox or later

-   Android SDK 21+

-   Java 8 or higher


### Building from Source

1.  Clone the repository:

    ```bash
    
    git clone https://github.com/Burden19/XO
    cd xo-tournament

2.  Open the project in Android Studio

3.  Sync the project with Gradle files

4.  Build the project:

    -   Use `Build > Make Project` in Android Studio

    -   Or run `./gradlew assembleDebug` from command line

5.  Run on emulator or connected device


### Debugging

-   Use Android Studio's debugger

-   Check Logcat for runtime logs

-   The project uses standard Android debugging practices


## ✨ Features

-   **Player-vs-Player Gameplay** - Play classic Tic-Tac-Toe with a friend on the same device

-   **Tournament Mode** - Compete in "best of" series: 3, 5, 7, 10, 15, or 20 games

-   **3D Coin Flip Animation** - A smooth animated coin toss determines who starts each tournament

-   **Statistics Tracking** - Win streaks, loss streaks, win percentage, games played, and more

-   **Game History** - Review past tournaments and track progress over time

-   **Sound Effects** - Audio cues for moves, wins, and draws for a more immersive experience

-   **Modern UI** - Clean layout, vector icons, blue/red theme, and polished interaction design


## 🕹️ How to Play

1.  Choose whether you play as X or O

2.  Select the number of games for the tournament

3.  Tap Play to trigger the coin flip animation and begin

4.  Take turns placing your symbol on the 3×3 grid

5.  After each round:

    -   Winner starts next

    -   If it's a draw, the starting player alternates

6.  After completing all rounds, the player with the most wins becomes the Tournament Champion


## 🛠️ Tech Stack

-   **Language**: Java

-   **Framework**: Android SDK

-   **Min SDK**: 26 (Android 8.0)

-   **UI**: ConstraintLayout / LinearLayout, Material-like VectorDrawables

-   **Animations**: Property Animator (rotationY for coin flip)

-   **Storage**: SharedPreferences (statistics & history)

-   **Build System**: Gradle


## 📂 Project Structure

````text

app/
 ├── java/
 │    └── com.example.xotournament/
 │           ├── MainActivity.java
 │           ├── GameActivity.java
 │           ├── StatisticsActivity.java
 │           ├── HistoryActivity.java
 │           ├── adapters/
 │           ├── models/
 │           └── utils/
 ├── res/
 │    ├── layout/
 │    ├── drawable/
 │    ├── values/
 │    └── anim/
 ├── AndroidManifest.xml
 └── build.gradle
 ````

## 🙋‍♂️ About the Developer

> 👤 **Ahmed M'barek**  
> 📧 [ahmedmbarek61@gmail.com](mailto:ahmedmbarek61@gmail.com)  
> 💼  [GitHub](https://github.com/Burden19)

_Feel free to reach out with feedback, bug reports, or feature requests!_

----------

## 🙌 Contributing

Contributions are welcome! Here’s how to help:

1.  Fork the repository
2.  Create your feature branch:


3.  Commit your changes:

4.  Push to the branch:



5.  Open a **Pull Request**

----------

> ⚠️ **Note**: This is a **local multiplayer** game (no online mode). Designed for two players sharing one device.

----------

Made with ❤️ and Java ☕  
**X-O Tournament © 2025 — Ahmed M'barek**
