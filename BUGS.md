# Known Bugs and Performance Issues

**Last Updated:** September 30, 2024  
**Version:** 3.0

## 🐛 Known Bugs

### High Priority

1. **No Window Icon Resource**
   - **Description:** Application icon is referenced but image file doesn't exist yet
   - **Location:** `TheEndTheBeginning.java` line ~88
   - **Impact:** Application runs with default Java icon
   - **Fix Required:** Create and add game-icon.png to resources/icons/

2. **GameState Synchronization**
   - **Description:** Some edge cases may cause desync between player object and GameState legacy system
   - **Location:** `MainControllerNew.java` syncPlayerToGameState method
   - **Impact:** Stats display may not always match actual player stats
   - **Fix Required:** Consider migrating fully away from GameState to use only Player class

### Medium Priority

3. **Save File Location**
   - **Description:** Save files are stored in user's home directory without asking permission
   - **Location:** `SaveManager.java` SAVE_DIR constant
   - **Impact:** May cause issues on systems with restricted home directory access
   - **Fix Required:** Add option to choose save location or use system temporary directory as fallback

4. **No Save Confirmation**
   - **Description:** Auto-save happens silently, players may lose progress if they load old save
   - **Location:** `MainControllerNew.java` autoSave method
   - **Impact:** Potential for data loss if player wanted to restart
   - **Fix Required:** Add manual save option and/or save slot system

5. **Monster Scaling**
   - **Description:** Monster difficulty doesn't scale well for levels 30-50
   - **Location:** `MainControllerNew.java` encounterMonster method
   - **Impact:** Game may become too easy or too hard in late game
   - **Fix Required:** Implement better scaling formula for late-game monsters

### Low Priority

6. **CSS Platform-Specific Issues**
   - **Description:** Some CSS effects may not render the same on all platforms (Windows/Mac/Linux)
   - **Location:** `game-style.css` drop-shadow effects
   - **Impact:** Visual inconsistencies across platforms
   - **Fix Required:** Test on multiple platforms and adjust CSS accordingly

7. **Input Validation**
   - **Description:** Some input fields accept empty strings or very long inputs
   - **Location:** Various input handlers in `MainControllerNew.java`
   - **Impact:** Potential for display issues or crashes with unusual input
   - **Fix Required:** Add input length validation and sanitization

8. **Memory Management**
   - **Description:** Long game sessions may accumulate text in gameTextArea without limit
   - **Location:** `MainControllerNew.java` appendToGameText method
   - **Impact:** Potential memory issues in very long play sessions
   - **Fix Required:** Implement text buffer limit with rolling window

## ⚡ Performance Issues

### Identified Issues

1. **Text Area Performance**
   - **Description:** Appending large amounts of text can cause UI lag
   - **Severity:** Low
   - **Workaround:** Limit text appended per game event
   - **Optimization:** Use StringBuilder for batch text operations

2. **Save/Load I/O**
   - **Description:** Save operations happen synchronously on UI thread
   - **Severity:** Low (saves are small and fast)
   - **Optimization:** Move to background thread for larger save files

3. **No Lazy Loading**
   - **Description:** All game resources loaded at startup
   - **Severity:** Very Low (current resources are minimal)
   - **Optimization:** Implement lazy loading for future content expansion

## 🔮 Future Improvements

### Quality of Life
- Add keyboard shortcuts for common actions
- Implement combat log filtering
- Add sound effects and background music
- Create tutorial/help system for new players
- Add difficulty scaling options during gameplay

### Technical Debt
- Remove GameState class entirely, use only Player
- Implement proper MVC architecture with separated concerns
- Add unit tests for game logic
- Implement proper logging framework
- Add configuration file for game settings

### Content
- More monster varieties for levels 30-50
- Boss monsters at milestone levels (10, 25, 50)
- More item types and rarities
- Character customization options
- Achievement rewards system

## 📝 Testing Notes

### Not Yet Tested
- Multi-hour gameplay sessions (memory usage)
- Save/load with corrupted save files
- Window resizing behavior on all platforms
- Performance with very long player names
- Multiple concurrent instances of the game

### Test Results
- ✅ Basic gameplay loop works correctly
- ✅ Level progression from 1-50 functional
- ✅ Save/load basic functionality working
- ✅ Class selection and stat tracking working
- ✅ Combat system functional

## 🚀 Optimization Opportunities

1. **Text Rendering**: Consider using TextFlow for better formatted text display
2. **Animation**: Add smooth transitions between game states
3. **Caching**: Cache commonly used strings and calculations
4. **Resource Loading**: Preload images and resources asynchronously
5. **Threading**: Move heavy operations to background threads

---

**Note:** This document will be updated as more issues are discovered and resolved.
Please report any bugs or performance issues not listed here.
