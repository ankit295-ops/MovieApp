App Description:

MovieApp is a movie browsing app designed to showcase trending movies and detailed information about your favorite films. Explore the latest trends in cinema, discovering movie details, and watching trailers.

Features Overview:
Home Screen:
Browse Trending Movies in a horizontal scroll (daily updates).
Explore categorized movies: Now Playing, Popular, Top-Rated, and Upcoming, displayed in a visually appealing grid format.
Click on any movie to navigate to its detailed view.

Movie Details Screen:
View an immersive layout showcasing a movie's banner, poster, title, description, release date, duration, and genre.
Switch between tabs for:
About Movie: Get a full description of the movie.
Cast: Browse a list of cast members, complete with profile pictures and names.

Video Player Integration:
Watch movie trailers directly within the app.
Responsive video player supporting landscape mode, play/pause controls, seek bar, and full-screen toggle.

Build/Run Instructions:
Make sure that the app gradle is in sync. wait for the dependencies to download. To run the app just click on run button.

Clean & Modular Architecture:
Built using MVVM architecture for scalability and maintainability.
Kotlin language ensures modern, robust, and production-grade code.

components of architecture:
Repository - The repository does the network call and fetches the response from the server through api.
viewmodel - the will model triggers the repository suspend function and waits for the response once the response is received the postValue is triggered on the variable.
UI - the activity/fragment contains the observer which observes for any data change in the variable once the data is changed the ui gets updated.

Libraries and Design Rational:
Retrofit library for seamless API communication.
Picasso for image display.
viewmodel libraries are used

Known issues or limitations:
images are showing images size not supported error so default placeholder image is shown
progress bars are not shown
Failures are not handled
internet connection not checked.