# Tank Game

A two-player desktop tank battle game built with Java Swing, featuring real-time gameplay, collision detection, and resource management.

## Overview

Tank Game is a Java-based multiplayer game that demonstrates solid software engineering practices including object-oriented design, game loop architecture, animation systems, and resource pooling. The game runs at 1204x700 resolution with smooth gameplay and interactive menus.

## Project Details

- **Developer:** Claudia Wormley
- **Language:** Java
- **Framework:** Java Swing (JFrame, JPanel)
- **Architecture:** Custom game engine with MVC-style separation
- **Game World:** 2000x1000 world space with camera viewport

## Key Features

- **Two-Player Gameplay** - Local multiplayer tank combat
- **Real-Time Rendering** - Dedicated game loop on separate thread
- **Physics & Collision** - Wall collision detection and bullet mechanics
- **Animation System** - Sprite-based animations including explosions
- **Resource Management** - Object pooling for bullets and game entities
- **Interactive Menus** - Start and end game menu screens
- **Map System** - CSV-based level loading with map1.csv included
- **Audio** - Sound effects integration
- **Power-ups** - Gameplay enhancement items

## Technical Architecture

### Core Components

- **GameWorld** - Main game loop and world state management
- **Tank** - Player-controlled tank with rotation and shooting mechanics
- **Bullet** - Projectile system with pooling for performance
- **Wall** - Static obstacles and level barriers (wall types: stone, concrete)
- **Animation** - Sprite animation handler for visual effects
- **ResourcePool** - Generic object pooling implementation for optimization
- **ResourceManager** - Asset and resource loading system

### Design Patterns

- **Object Pooling** - Efficient memory usage for frequently created/destroyed objects
- **Card Layout** - Menu state management using Java Swing's CardLayout
- **Separation of Concerns** - Game logic, rendering, and UI separated into distinct modules

## Technical Stack

- **Java Version:** Java 8+
- **IDE:** IntelliJ IDEA / Eclipse (compatible with both)
- **Build System:** Maven / Direct compilation
- **Graphics:** Java 2D with Swing
- **Threading:** Multi-threaded game loop architecture

## Getting Started

### Prerequisites

- Java Development Kit (JDK 8 or higher)
- Maven (optional, for building)
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans)

### Building the Project

```bash
# Using Maven (if configured)
mvn clean compile

# Or compile directly
javac -d bin src/tankrotationexample/**/*.java
```

### Running the Game

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="tankrotationexample.Launcher"

# Or run directly from IDE
# Right-click Launcher.java → Run
```

## Game Controls

| Action         | Player 1 | Player 2 |
|----------------|----------|----------|
| Move Forward   | W        | ↑        |
| Move Backward  | S        | ↓        |
| Rotate Left    | A        | ←        |
| Rotate Right   | D        | →        |
| Shoot          | Space    | Ctrl     |

## Project Structure

```
TankGame/
├── src/tankrotationexample/
│   ├── GameConstants.java           # Screen and world size definitions
│   ├── Launcher.java                # Application entry point
│   ├── ResourceManager.java         # Asset loading and management
│   ├── ResourcePools.java           # Object pool initialization
│   ├── game/                        # Core game engine
│   │   ├── GameWorld.java           # Main game loop
│   │   ├── GameObject.java          # Base entity class
│   │   ├── Tank.java                # Player tank with controls
│   │   ├── Bullet.java              # Projectile system
│   │   ├── Wall.java & BWall.java   # Collision barriers
│   │   ├── PowerUp.java             # Power-up items
│   │   ├── Animation.java           # Sprite animation
│   │   ├── Sound.java               # Audio system
│   │   ├── TankControl.java         # Input handling
│   │   ├── Poolable.java            # Pooling interface
│   │   └── ResourcePool.java        # Generic object pool
│   └── menus/                       # UI screens
│       ├── StartMenuPanel.java      # Main menu
│       └── EndGamePanel.java        # Game over screen
├── resources/
│   ├── map1.csv                     # Level layout data
│   └── explosion_sm/                # Animation sprites
└── jar/                             # Compiled JAR output
```

## Code Quality

- Object-oriented design with proper encapsulation
- Efficient resource management through object pooling
- Separation of game logic from rendering
- Multi-threaded architecture for responsive UI
- Event-driven input handling

## Performance Considerations

- **Object Pooling** - Reuses bullet and game entity objects to minimize garbage collection
- **Dedicated Game Thread** - Prevents UI freezing during gameplay
- **Viewport System** - Renders only visible game world portion
- **Resource Caching** - Pre-loaded assets and sound effects

## Future Enhancements

- Network multiplayer support
- Additional weapon types and special abilities
- Multiple map levels with increasing difficulty
- Leaderboard and scoring system
- Sound volume and graphics quality settings
- Mobile touch controls

## Author

**Claudia Wormley**  
cwormley@sfsu.edu

## License

See LICENSE file for details
