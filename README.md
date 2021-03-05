# ADND
Project #4 Build the structure for a Media Player app
=======
## 1.  Basic Setup
- [x] Add ViewPager + Fragment for navigation header
  - [x] Build Layout for the SingleListFragment. (This required to test MediaStore Logic.  We will build the other fragments later.)
- [x] Add necesarry RecyclerViewers to display lists of songs/artists/albums
- [x] Add model classes for Song/Album/Artist, as well as a Library object to manage all of the lists of objects.
## 2. Implement MediaStore API
- [x] Request Storage Permissions!
- [x] Read all songs off of users device and add them to a list of songs
- [x] Build the Library object from list of songs. (We want to end up with a list of Artists, that has a list of all of it's albums, each of which has a list of all of the songs.) 
  -  Solved by using Java Streams; not availible on older API versions, but it has far cleaner code than the original implementation.
- [x] Display data to RecyclerViews
## 3. NowPlayingFragment, DualListFragment & Click Logic
- [x] Build the layout for the NowPlayingFragment
- [ ] ~Build the layout for the DualListFragment~
- [ ] ~Add click Listeners for RecyclerViews~
  - [ ] ~Clicking on Artist brings up list of albums/songs~
  - [ ] ~Clicking on Album brings up list of songs~
  - [ ] ~Clicking on Song will scroll to Now Playing fragment and populate views with song data~
-  Crossed out features were out of scope for the project, and increased the complexity significantly. Same concept is required on next project, so I'll revisit the features there.
## 4. Settings Activity
- [x] Grading rubric requires us to use more than one activity.  Since we used VP + Fragment setup, we'll make a light implementation of a settings activity to check off this box.
## 5. Clean up
- [x] Touch up comments, formatting.
- [x] Double check styles and dimens.
