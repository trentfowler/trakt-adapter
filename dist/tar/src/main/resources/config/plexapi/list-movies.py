#!/usr/bin/env python3
from plexapi.myplex import MyPlexAccount
account = MyPlexAccount('PLEX_EMAIL_GOES_HERE', 'PLEX_PW_GOES_HERE')
plex = account.resource('PLEX_RESOURCE_NAME_GOES_HERE').connect()
movies = plex.library.section('Movies')
for video in movies.search():
    print(video.title)
