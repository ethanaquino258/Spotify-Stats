# Getting started

- create an application through the [spotify developer dashboard](developer.spotify.com)
- Install Java 17
- Install mySQL

# Setting up
- create an application.properties file and add *client secret* and *client id* from your spotify application as `client.id` and `client.secret`

# local debugging
- to start the backend, type `./gradlew bootRun` into terminal. Make sure you're in the `stats-backend` api directory
- to make a request, first determine which [authorization scope](https://developer.spotify.com/documentation/web-api/concepts/scopes) you'll need for the request. 
- login to the spotify api via a `curl` request in the following format: `curl localhost:8080/login?scope=yourScope`
- proceed to the link provided in the response to complete authorization
- you may now execute any request via `curl` in the backend provided you have the appropriate scope. If not, re-login with the necessary scope
