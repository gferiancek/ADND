# ADND
Project #3 Build a Quiz App on a topic you are knowledgable about.
=======
## 1.  First project using view binding.
- [x] Setup and experiment before diving into project.
## 2. Design the basic layout for title screen and quiz screen
- [x] Title Screen
- [x] Quiz screen
## 3. Implement Fragments
- [x] Fragment for Quiz Screen.  Need to experiment on whether  I'll need a different fragment for each type of question. (Radio/Checkbox/Manual Entry)
- [x] Fragment for Results screen
## 4. Core Logic
- [x] Function that loads the next question into the fragment.
- [x] Function that randomizes the answer choice order (May be able to combine with the above, but we'll start seperate until an implementation is made.)
- [x] Function that grades the users answers.
  - [x] This should be run before loading the next fragment.  If a user gets 3 questions wrong, then they lose the game.
- [x] Function to return back to the title screen fragment.
## 5. Visual Polish
- [x] Would like to use a nice image on title screen. Will revisit once I've implemented fragments and have more of a grasp on what I can do with them.
- [x] Color scheme, text, all that fun jazz.
## 6. Optional Features
- [x] Timer for each question.  If timer runs out, answer is marked as wrong. If answered correctly, the user is awarded points based on how much time is left.
  - [x] Track in ms.  1 point per ms.  So if 4.342 was left on the timer, the user would get 4,342 points.
  - [x] TIME SCORE LEFT x STRIKES LEFT x DIFFICULTY = SCORE
