# 🎓 AI Study Planner

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

> **Live Demo & Documentation: [https://Vinothvinay.github.io/study-planner](https://Vinothvinay.github.io/study-planner)**

A sophisticated desktop application built with JavaFX that helps students create intelligent study schedules using AI integration. The application generates personalized study plans based on subjects, difficulty levels, and available study time.

## ✨ Features

- 🤖 **AI-Powered Planning**: Intelligent timetable generation using Hugging Face AI models
- 📅 **Interactive Calendar**: Visual calendar interface with drag-and-drop functionality
- 📊 **Progress Tracking**: Real-time progress monitoring with pie charts and completion status
- 🎨 **Theme Support**: Light and dark theme toggle for comfortable viewing
- 📱 **Modern UI**: Responsive JavaFX interface with smooth animations
- 📤 **Export Functionality**: Export study plans and progress reports

## 🚀 Live Demo

- **🌐 GitHub Pages**: [https://Vinothvinay.github.io/study-planner](https://Vinothvinay.github.io/study-planner)
- **📱 Documentation**: Complete project documentation and screenshots
- **⬇️ Downloads**: Latest releases for all platforms

## 🛠️ Technology Stack

- **Backend**: Java 17, Maven
- **Frontend**: JavaFX 17.0.2
- **AI Integration**: Hugging Face API
- **Styling**: Custom CSS with theme support
- **Build**: Maven with shade plugin for executable JARs

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Hugging Face API token (for AI features)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Vinothvinay/study-planner.git
cd study-planner
```

### 2. Set Environment Variables
```bash
# Set your Hugging Face API token
export HF_TOKEN=your_token_here

# On Windows (Command Prompt)
set HF_TOKEN=your_token_here

# On Windows (PowerShell)
$env:HF_TOKEN="your_token_here"
```

### 3. Build the Project
```bash
mvn clean package
```

### 4. Run the Application
```bash
# Using the generated JAR
java -jar target/study-planner-1.0.0.jar

# Using provided scripts
# Windows
run.bat

# Linux/Mac
./run.sh
```

## 📦 Installation Options

### Option 1: Download Latest Release
1. Go to [Releases](https://github.com/Vinothvinay/study-planner/releases)
2. Download the latest release package for your platform
3. Extract and run using the provided scripts

### Option 2: Build from Source
1. Follow the Quick Start guide above
2. Customize the application as needed
3. Build your own executable

## 🎯 Usage

### Adding Subjects
1. Click "Add Subject" in the left sidebar
2. Enter subject name, difficulty (1-5), target hours, and exam date
3. Click "Save" to add the subject

### Generating AI Timetable
1. Ensure you have subjects added
2. Set your daily study hours using the spinner
3. Click "Generate Timetable (AI)" button
4. Wait for AI to generate your personalized schedule

### Managing Tasks
- **Drag & Drop**: Move tasks between different days
- **Mark Complete**: Check the checkbox to mark tasks as done
- **Progress Tracking**: View completion progress in the pie chart

### Theme Switching
- Toggle between light and dark themes using the "Dark Mode" button

## 🔧 Configuration

### Hugging Face API Setup
1. Visit [Hugging Face](https://huggingface.co/settings/tokens)
2. Create a new access token
3. Set the token as an environment variable: `HF_TOKEN=your_token_here`

### Customization
- Modify CSS files in `src/main/resources/styles/` for custom themes
- Adjust AI parameters in `AIService.java`
- Customize the calendar layout in `DashboardView.java`

## 📁 Project Structure

```
study-planner/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/studyplannerfx/
│   │   │       ├── App.java              # Main application entry point
│   │   │       ├── DashboardView.java    # Main UI controller
│   │   │       └── ...
│   │   └── resources/
│   │       └── styles/                   # CSS theme files
│   └── test/                             # Test files
├── docs/                                 # GitHub Pages documentation
├── .github/workflows/                    # GitHub Actions
├── pom.xml                              # Maven configuration
└── README.md                            # This file
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- JavaFX team for the excellent UI framework
- Hugging Face for AI model integration
- Maven community for build tools
- All contributors and users of this project

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/Vinothvinay/study-planner/issues)
- **Discussions**: [GitHub Discussions](https://github.com/Vinothvinay/study-planner/discussions)
- **Documentation**: [Live Demo](https://Vinothvinay.github.io/study-planner)

---

⭐ **Star this repository if you find it helpful!**

Made with ❤️ using JavaFX 