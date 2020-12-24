# ADND
Project #3 Build a Quiz App on a topic you are knowledgable about.
=======
## 1.  First project using view binding.
- [ ] Setup and experiment before diving into project.
## 2. Design the basic layout for title screen and quiz screen
- [ ] Title Screen
- [ ] Quiz screen
## 3. Implement Fragments
- [ ] Fragment for title screen
- [ ] Fragment for Quiz Screen.  Need to experiment on whether  I'll need a different fragment for each type of question. (Radio/Checkbox/Manual Entry)
## 4. Core Logic
- [ ] Function that loads the next question into the fragment.
- [ ] Function that randomizes the answer choice order (May be able to combine with the above, but we'll start seperate until an implementation is made.)
- [ ] Function that grades the users answers.
  - [ ] This should be run before loading the next fragment.  If a user gets 3 questions wrong, then they lose the game.
- [ ] Function to return back to the title screen fragment.
## 5. Visual Polish
- [ ] Would like to use a nice image on title screen. Will revisit once I've implemented fragments and have more of a grasp on what I can do with them.
- [ ] Color scheme, text, all that fun jazz.
## 6. Optional Features
- [ ] Timer for each question.  If timer runs out, answer is marked as wrong. If answered correctly, the user is awarded points based on how much time is left.
  - [ ] Track in ms.  1 point per ms.  So if 4.342 was left on the timer, the user would get 4,342 points.
  - [ ] TIME SCORE LEFT x STRIKES LEFT x DIFFICULTY = SCORE
