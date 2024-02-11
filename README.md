# otso
otso is an app where you can follow the latest news from the world of music. The app features news, recently released new albums, currently popular Youtube videos.

## Project Setup

1. Clone repository, open project in the latest version of Android Studio.
2. Generate and import `google-services.json` file and put it in the `/app`.
3. Create `local.properties` and import it into gradle.
4. Add your [News](https://newsapi.org/) NEWS_API_KEY, [Spotify](https://developer.spotify.com/) SPOTIFY_CLIENT_ID & SPOTIFY_CLIENT_SECRET, [Youtube](https://console.cloud.google.com/) YOUTUBE_API_KEY,
[MusixMatch](https://developer.musixmatch.com/) MUSIXMATCH_API_KEY into local.properties
```
NEWS_API_KEY = "YOUR_NEWS_API_KEY"
SPOTIFY_CLIENT_ID = "YOUR_SPOTIFY_CLIENT_ID"
SPOTIFY_CLIENT_SECRET = "YOUR_SPOTIFY_CLIENT_SECRET"
YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY"
MUSIXMATCH_API_KEY - "YOUR_MUSIXMATCH_API_KEY"
```

## TODO
- [ ] - UI/UX Improvement
- [ ] - Add a database to save information
- [ ] - Add search
- [ ] - Create a tools screen and their functions 
