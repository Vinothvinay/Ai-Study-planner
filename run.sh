#!/bin/bash

echo "Building and running Study Planner..."
echo

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven from: https://maven.apache.org/download.cgi"
    echo "Or use an IDE like IntelliJ IDEA or Eclipse"
    exit 1
fi

# Check if Java 17+ is installed
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "ERROR: Java 17 or higher is required (found Java $JAVA_VERSION)"
    echo "Please install Java 17+ from: https://adoptium.net/"
    exit 1
fi

echo "Java and Maven are available. Building project..."
echo

# Clean and compile
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo
echo "Build successful! Running application..."
echo

# Run the application
mvn javafx:run 