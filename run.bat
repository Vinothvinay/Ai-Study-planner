@echo off
echo Building and running Study Planner...
echo.

REM Check if Maven is installed
mvn --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    echo Or use an IDE like IntelliJ IDEA or Eclipse
    pause
    exit /b 1
)

REM Check if Java 17+ is installed
java -version 2>&1 | findstr "version \"17" >nul
if errorlevel 1 (
    java -version 2>&1 | findstr "version \"18" >nul
    if errorlevel 1 (
        java -version 2>&1 | findstr "version \"19" >nul
        if errorlevel 1 (
            java -version 2>&1 | findstr "version \"20" >nul
            if errorlevel 1 (
                java -version 2>&1 | findstr "version \"21" >nul
                if errorlevel 1 (
                    echo ERROR: Java 17 or higher is required
                    echo Please install Java 17+ from: https://adoptium.net/
                    pause
                    exit /b 1
                )
            )
        )
    )
)

echo Java and Maven are available. Building project...
echo.

REM Clean and compile
mvn clean compile

if errorlevel 1 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Build successful! Running application...
echo.

REM Run the application
mvn javafx:run

pause 