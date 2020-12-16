# ADND
Project #2  Build an app to track metrics for a sport/game of your choice
=======
## 1. Scoreboard is going to be the biggest task, get the framework out of the way first.
  - [x] Create general layout for scoreboard.
  - [x] Implement RecyclerViews in order to make handling changes to scoreboard easier.
  - [x] Test the waters with inning highliting feature; basic funcionality implemented.
## 2. Build XML Layout and general framework for the rest of the app.
- [x] Layout xml and basic onclick functions
## 3. Core Java Logic
- [x] Implement function to extract data from textviews and post to the scoreboard.
- [x] Implement functions to detect and respond to various game states.
    - If a user gets three strikes, strikes should reset to 0 and an out should be added.
    - Similar logic for balls/outs.
    - Function that determines when the game is over and who is the winner.
- [x] Implement functions to transition to the next inning:
    - Needs to use postToScoreboard function to save stats
    - Shift focus to proper inning
    - Reset data back to 0 to track the next inning's statistics
## 4. Visual Polish and Code Clean Up
- [x] Done
## Optional Features
- [ ] Implement Function to detect if game needs to add Extra Innings for Tie Breakers.
    - Should follow rules of no more than 9 extra innings, and update the scoreboard to show extra innings.
    
Decided this feature was way out of scope for the project and did not implement it.
