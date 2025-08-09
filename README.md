# Study Planner - JavaFX Application

A modern, responsive study planner built with JavaFX featuring dark/light theme switching and smooth animations.

## Prerequisites

Before running this application, you need to install:

### 1. Java 17 or Higher
- Download from: [Eclipse Temurin](https://adoptium.net/)
- Or use: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- Make sure `java` and `javac` are in your PATH

### 2. Maven 3.6+
- Download from: [Maven Official Site](https://maven.apache.org/download.cgi)
- Or use package managers:
  - **Windows**: `choco install maven` (with Chocolatey)
  - **macOS**: `brew install maven` (with Homebrew)
  - **Ubuntu/Debian**: `sudo apt install maven`

## Quick Start

### Option 1: Using the Run Scripts (Recommended)

#### Windows
```bash
run.bat
```

#### Linux/macOS
```bash
chmod +x run.sh
./run.sh
```

### Option 2: Manual Maven Commands

1. **Build the project:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn javafx:run
   ```

3. **Create a runnable JAR:**
   ```bash
   mvn clean package
   java -jar target/study-planner-1.0.0.jar
   ```

## Project Structure

```
study-planner/
├── src/
│   └── com/studyplannerfx/
│       ├── App.java              # Main application entry point
│       ├── DashboardView.java    # Main dashboard UI
│       ├── model/                # Data models
│       ├── services/             # Business logic
│       └── util/                 # Utility classes
├── resources/
│   └── styles/
│       ├── base.css              # Base styles
│       ├── light.css             # Light theme
│       ├── dark.css              # Dark theme
│       └── components.css        # Component styles
├── pom.xml                       # Maven configuration
├── run.bat                       # Windows run script
├── run.sh                        # Unix run script
└── README.md                     # This file
```

## Features

- ✨ Modern JavaFX UI with smooth animations
- 🌓 Dark/Light theme switching
- 📱 Responsive design
- 🎯 Study planning and management
- 💾 In-memory data storage (no database required)

## Troubleshooting

### Common Issues

1. **"JavaFX runtime components are missing"**
   - Make sure you're using Java 17+ and the Maven JavaFX plugin
   - Run with `mvn javafx:run` instead of `java -jar`

2. **"Maven not found"**
   - Install Maven and add it to your PATH
   - Or use an IDE like IntelliJ IDEA or Eclipse

3. **"Java version too old"**
   - Update to Java 17 or higher
   - Check with: `java -version`

4. **Build errors**
   - Run `mvn clean` first
   - Check that all dependencies are downloaded

### IDE Setup

#### IntelliJ IDEA
1. Open the project folder
2. Import as Maven project
3. Run `App.java` directly

#### Eclipse
1. Import as Maven project
2. Run as Java Application on `App.java`

#### VS Code
1. Install Java Extension Pack
2. Open the project folder
3. Use Maven sidebar to run goals

## Development

### Adding New Features
1. Create new classes in appropriate packages
2. Update the UI in `DashboardView.java`
3. Add any new CSS styles to the stylesheets

### Building for Distribution
```bash
mvn clean package
```
This creates a runnable JAR in the `target/` folder.

## License

This project is open source and available under the MIT License.

## Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Ensure all prerequisites are installed
3. Try running with the provided scripts
4. Check that Java 17+ and Maven are properly configured 