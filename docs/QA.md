# QA Test Scenarios - v3.1.0

Manual QA scenarios for The End The Beginning v3.1.0

## Test Environment Setup

1. Build the project: `mvn clean compile`
2. Run the game: `mvn javafx:run`
3. Clear previous saves: Delete `~/.the-end-the-beginning/` folder

---

## Test Scenario 1: New Game Flow with Difficulty Preview

### Steps:
1. Launch the game
2. Click "Start New Game"
3. Choose "NEW" (not load)
4. Answer "YES" to enter dungeon
5. Select a character class (1, 2, or 3)
6. Select difficulty (1-4)

### Expected Results:
- ✅ Difficulty preview appears showing exact multipliers
- ✅ Text displays Monster HP%, ATK%, DEF bonuses, Healing %
- ✅ Difficulty is set correctly
- ✅ Name prompt appears

### Pass/Fail: ___

---

## Test Scenario 2: Overwrite-Only Text Behavior

### Steps:
1. Start a new game and progress through a few rooms
2. Observe the text area after each action
3. Take multiple actions in sequence

### Expected Results:
- ✅ Text area clears between messages
- ✅ Only the newest message is visible
- ✅ No scrolling history accumulates
- ✅ Text starts at the top of the text area

### Pass/Fail: ___

---

## Summary

**Date:** ___________  
**Tester:** ___________  
**Version:** v3.1.0  

**Overall Status:**
- [ ] All scenarios passed
- [ ] Some scenarios failed (list below)

**Failed Scenarios:**
1. ___________
2. ___________

**Notes:**
___________________________________________________________________________
