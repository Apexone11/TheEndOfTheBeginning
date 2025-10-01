# Contributing to The End The Beginning

Thank you for your interest in contributing to **The End The Beginning - Dungeon Escape Game**! This document provides guidelines for contributing to the project.

## 🎯 Quick Start

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/TheEndOfTheBeginning.git`
3. Navigate to project: `cd TheEndOfTheBeginning/TheEndTheBeginning`
4. Build the project: `mvn clean compile`
5. Run the game: `mvn javafx:run`

## 📋 Before Contributing

- Read the [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- Check [TODO.md](TODO.md) for current tasks and bugs
- Review [RELEASE_NOTES.md](RELEASE_NOTES.md) to understand recent changes
- Check existing [Issues](https://github.com/Apexone11/TheEndOfTheBeginning/issues) and [Pull Requests](https://github.com/Apexone11/TheEndOfTheBeginning/pulls)

## 🛠️ Development Setup

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Git**
- A Java IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Building the Project

```bash
cd TheEndTheBeginning
mvn clean compile          # Compile the project
mvn javafx:run            # Run the game
mvn package               # Build JAR file
```

### Project Structure

See [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) for detailed project organization.

## 🎨 Code Style

### Java Code

- Use **Java 17** features
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods and classes
- Keep methods focused and reasonably sized

### Example:

```java
/**
 * Saves the current game state to a file.
 * 
 * @param player The player object to save
 * @param dungeonLevel Current dungeon level
 * @return true if save was successful, false otherwise
 */
public static boolean saveGame(player player, int dungeonLevel) {
    // Implementation
}
```

### CSS Styling

- Follow existing style patterns in `game-style.css`
- Use descriptive class names
- Comment complex style rules

## 🔀 How to Contribute

### Reporting Bugs

1. Check if the bug is already reported in [Issues](https://github.com/Apexone11/TheEndOfTheBeginning/issues)
2. If not, create a new issue using the bug report template
3. Include:
   - Clear description of the bug
   - Steps to reproduce
   - Expected behavior
   - Actual behavior
   - Screenshots (if applicable)
   - Java version and OS

### Suggesting Features

1. Check [TODO.md](TODO.md) to see if it's already planned
2. Check existing feature request issues
3. Create a new issue using the feature request template
4. Explain:
   - What problem does it solve?
   - How should it work?
   - Why is it beneficial?

### Submitting Changes

#### 1. Create a Branch

```bash
git checkout -b feature/your-feature-name
# or
git checkout -b bugfix/issue-number-description
```

#### 2. Make Your Changes

- Write clean, readable code
- Test your changes thoroughly
- Update documentation if needed
- Follow the existing code style

#### 3. Commit Your Changes

Use clear, descriptive commit messages:

```bash
git commit -m "Add sound effects for combat actions"
# or
git commit -m "Fix save file corruption when player level > 50"
```

#### 4. Push to Your Fork

```bash
git push origin feature/your-feature-name
```

#### 5. Create a Pull Request

- Go to the original repository on GitHub
- Click "New Pull Request"
- Select your branch
- Fill out the PR template
- Link related issues (e.g., "Fixes #123")

## 🧪 Testing

### Manual Testing

Before submitting a PR:

1. Build the project: `mvn clean compile`
2. Run the game: `mvn javafx:run`
3. Test your changes thoroughly
4. Test edge cases
5. Verify no regressions in existing features

### Test Coverage

Currently, the project uses manual testing. Automated tests are welcome contributions!

## 📝 Documentation

### When to Update Documentation

Update documentation when you:
- Add new features
- Change existing functionality
- Fix bugs that affect user behavior
- Add dependencies or change build process

### Documentation Files to Consider

- **README.md** - User-facing changes
- **CHANGELOG.md** - Version history
- **TODO.md** - Task updates
- **Code comments** - Inline documentation
- **JavaDoc** - API documentation

## 🎮 FXGL Integration

If you're working with FXGL features:

1. Read [FXGL_INTEGRATION.md](FXGL_INTEGRATION.md)
2. Check [GAME_ENGINES.md](GAME_ENGINES.md) for context
3. Test compatibility with existing JavaFX code
4. Document new FXGL usage patterns

## 🔒 Security

If you discover a security vulnerability:

1. **DO NOT** open a public issue
2. Follow the process in [SECURITY.md](SECURITY.md)
3. Report privately through GitHub Security Advisories

## ✅ Pull Request Checklist

Before submitting your PR, ensure:

- [ ] Code builds successfully (`mvn clean compile`)
- [ ] Game runs without errors (`mvn javafx:run`)
- [ ] Changes are tested manually
- [ ] Code follows project style guidelines
- [ ] Documentation is updated (if needed)
- [ ] Commit messages are clear and descriptive
- [ ] PR description explains what and why
- [ ] Related issues are linked

## 🚀 Good First Issues

Look for issues tagged with:
- `good first issue` - Great for newcomers
- `help wanted` - Community help needed
- `documentation` - Documentation improvements

## 💡 Tips for Success

### For New Contributors

- Start small - fix a typo, improve documentation
- Ask questions if unclear - use GitHub Discussions or Issues
- Be patient - reviews may take time
- Be respectful - follow the Code of Conduct

### For Code Changes

- Keep PRs focused - one feature or bug fix per PR
- Write clear PR descriptions
- Respond to review feedback promptly
- Don't take feedback personally - it's about the code

### For Documentation Changes

- Fix typos and unclear explanations
- Add examples and clarifications
- Improve formatting and organization
- Keep language clear and concise

## 🤝 Community

### Getting Help

- **Issues**: Ask questions or report problems
- **Pull Requests**: Discuss code changes
- **Discussions**: General project discussions (if enabled)

### Recognition

Contributors are recognized in:
- Git commit history
- GitHub contributors page
- Release notes (for significant contributions)

## 📚 Additional Resources

### Project Documentation
- [README.md](README.md) - Project overview
- [CHANGELOG.md](CHANGELOG.md) - Version history
- [RELEASE_NOTES.md](RELEASE_NOTES.md) - Detailed releases
- [TODO.md](TODO.md) - Roadmap and tasks

### External Resources
- [JavaFX Documentation](https://openjfx.io/)
- [FXGL Documentation](https://github.com/AlmasB/FXGL/wiki)
- [Maven Documentation](https://maven.apache.org/)
- [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)

## 🙏 Thank You!

Your contributions help make The End The Beginning better for everyone. Whether you're fixing bugs, adding features, improving documentation, or helping others, your efforts are appreciated!

---

**Happy Coding!** ⚔️🏰
