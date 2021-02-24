# ADND
Project #4 Build the structure for a Media Player app
=======
## 1.  Basic Setup
- [x] Add ViewPager + Fragment for navigation header
- [x] Add necesarry RecyclerViewers to display lists of songs/artists/albums
- [x] Add model classes for Song/Album/Artist, as well as a Library object to manage all of the lists of objects.
## 2. Implement MediaStore API
- [x] Read all songs off of users device and add them to a list of songs
- [x] Build the Library object from list of songs. (We want to end up with a list of Artists, that has a list of all of it's albums, each of which has a list of all of the songs.) 
  -  Solved by using Java Streams; not availible on older API versions, but it has far cleaner code than the original implementation.
- [x] Display data to RecyclerViews
## 3. Now Playing Fragment
- [ ] Build the layout for the now playing screen
- [ ] Add click Listeners for RecyclerViews
  - [ ] Clicking on Artist brings up list of albums/songs
  - [ ] Clicking on Album brings up list of songs
  - [ ] Clicking on Song will scroll to Now Playing fragment and populate views with song data
## 4. Settings Activity
- [ ] Grading rubric requires us to use more than one activity.  Since we used VP + Fragment setup, we'll make a light implementation of a settings activity to check off this box.
