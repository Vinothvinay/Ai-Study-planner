#!/bin/bash

echo "🚀 AI Study Planner - GitHub Live Link Setup"
echo "=============================================="
echo ""

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "❌ Git is not installed. Please install Git first."
    exit 1
fi

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "❌ Not in a git repository. Please run this script from your project directory."
    exit 1
fi

echo "📋 Current Git Status:"
echo "----------------------"
git remote -v
echo ""

echo "🔧 Setup Steps:"
echo "==============="
echo ""

echo "1️⃣  Update GitHub Repository URLs"
echo "   Replace 'yourusername' with your actual GitHub username in these files:"
echo "   - docs/index.html"
echo "   - docs/demo.html"
echo "   - README.md"
echo "   - .github/workflows/build.yml"
echo ""

echo "2️⃣  Enable GitHub Pages"
echo "   - Go to your repository on GitHub"
echo "   - Click Settings → Pages"
echo "   - Source: Deploy from a branch"
echo "   - Branch: main, folder: /docs"
echo "   - Click Save"
echo ""

echo "3️⃣  Enable GitHub Actions"
echo "   - Go to Actions tab in your repository"
echo "   - Click 'Enable Actions' if prompted"
echo "   - The workflows will run automatically on pushes"
echo ""

echo "4️⃣  Create Your First Release"
echo "   - Tag your release: git tag v1.0.0"
echo "   - Push the tag: git push origin v1.0.0"
echo "   - This will trigger the build workflow"
echo ""

echo "5️⃣  Push Your Changes"
echo "   - git add ."
echo "   - git commit -m 'Setup live demo and documentation'"
echo "   - git push origin main"
echo ""

echo "🌐 Your Live Links Will Be:"
echo "==========================="
echo "📚 Documentation: https://yourusername.github.io/study-planner"
echo "🎮 Web Demo: https://yourusername.github.io/study-planner/demo.html"
echo "📦 Releases: https://github.com/yourusername/study-planner/releases"
echo ""

echo "⚠️  Important Notes:"
echo "==================="
echo "• GitHub Pages may take a few minutes to deploy after pushing"
echo "• Make sure your repository is public for GitHub Pages to work"
echo "• The web demo is a simplified version - full features are in the desktop app"
echo ""

echo "🎯 Next Steps:"
echo "=============="
echo "1. Update all 'yourusername' references with your actual GitHub username"
echo "2. Push your changes to GitHub"
echo "3. Enable GitHub Pages in repository settings"
echo "4. Create a release tag to trigger the build workflow"
echo "5. Share your live demo links!"
echo ""

echo "✅ Setup complete! Follow the steps above to get your live links working."
echo ""

# Check if files need username updates
echo "🔍 Checking for username placeholders..."
if grep -r "yourusername" . --exclude-dir=.git --exclude=deploy-setup.sh; then
    echo ""
    echo "⚠️  Found 'yourusername' placeholders that need to be updated!"
    echo "   Please replace them with your actual GitHub username."
else
    echo "✅ No username placeholders found."
fi

echo ""
echo "🎉 Happy coding and good luck with your project!"
