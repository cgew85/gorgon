[![codecov](https://codecov.io/gh/cgew85/Gorgon/branch/master/graph/badge.svg)](https://codecov.io/gh/cgew85/Gorgon)
[![Build Status](https://travis-ci.org/cgew85/Gorgon.svg?branch=master)](https://travis-ci.org/cgew85/Gorgon)
# Gorgon
### A small app to manage your movie collection

This app uses a MongoDB hosted over at mlab.com. It runs on Java 8, Spring Boot and Maven.
The UI is done in Vaadin. You can also fetch additional information for a movie from omdb.
I used OpenFeign for consuming their REST api.

If you want to give it your own shot you need to change the settings in the application.properties file
and supply your own credentials via your IDE such as username and password.
