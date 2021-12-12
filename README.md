# FFC - Fast & Furious Cinema

[![ffc.jpg](docs/ffc.jpg)](https://unsplash.com/photos/pBrHNFqcX-M)

[![Stability: Experimental](https://masterminds.github.io/stability/experimental.svg)](https://masterminds.github.io/stability/experimental.html)

> Backend service for mobile/web application for a small cinema playing only movies
> from [Fast & Furious](https://en.wikipedia.org/wiki/Fast_%26_Furious) franchise. 
> Some assumptions were made and some shortcuts were implemented. So be warned that 
> this is just an experimental version.

## Build and run

There are some prerequisite to play with the project:
* [Docker](https://docs.docker.com/get-docker/) should be installed - as integration
  tests are using [Testcontainers](https://www.testcontainers.org/) to avoid all those
  in-memory/embedded/mocked solutions, and check if integrations really works.
* [Docker Compose](https://docs.docker.com/compose/install/) should be installed - as 
  project is using MongoDB for persistence, and starting provided `docker-copose.yaml`
  is painless way to run it locally.
* [OMDb API Key](http://www.omdbapi.com/apikey.aspx) is required - as movie details
  are fetched from OMDb API.

You can build the service and run all the tests with `./gradlew clean build`. All boilerplate
REST API code is generated from [OpenAPI](https://www.openapis.org/) specification and all
code for mappers is generated from [MapStruct](https://mapstruct.org/) annotated interfaces, 
so if you decide to import this project to IDE of your choice, some missing classes can be 
reported. Just re-build the project to fix that issues.

If you want to start the application locally, then all you have to do is start 
all dependencies with:
```
docker-compose up --detach
```
And after that start the service with:
```
ADAPTERS_OMDB_API_KEY=<change-me> ./gradlew bootRun
``` 

> **NOTE:** Just remember to use proper API key for OMDb API instead of `<change-me>`.
> And when you are done playing with the application tear down MongoDB
> with `docker-compose down --volumes`.

After application is up and running you can explore the API and play with it using:
* Swagger UI available at http://localhost:8080/swagger-ui/
* GraphQL Playground available at http://localhost:8080/graphql-playground/

Some API methods requires you to be authenticated user. You can use one from table
below. Mind that _STAFF_ role will allow you to execute methods from internal API.

|Username|Password|Roles      |
|--------|--------|-----------|
|kermit  |secret  |STAFF, USER|
|elmo    |secret  |USER       |

## So simple yet so complicated

I have assumed that cinema that is showing only nine rather mediocre movies will not
be operating on daily basis - fixed ticket prices and showing hours. Rather in from
event to event manner. And because of that internal API for showing dates and ticket
prices looks so simple. Staff member can create an event for a given date with list
of screenings and dedicated ticket prices for each of them. Also, that simple PUT for
date method could be easily extended to accept POST-ing an ultimate representation of
everything - Excel spreadsheet (or at least his simpleton brother CSV file).

One of expected results of the challenge was API documentation in OpenAPI/Swagger format.
I have turned that requirement around - I have started with designing 
[specification](src/main/resources/static/public/ffc-api.v1.yaml) and using it as an 
an input for generating REST controllers interfaces and models, and input for validator
of incoming requests.

I was trying to design REST API with Richardson's Maturity Model Level 2 in mind, 
and because of that there are separate resources for showings, movie details and ratings.
I hear whining of front-end developers - and now we have to make multiple api calls to 
get everything what we need. For example to get movie details and its showing hours
for given day. Well... you don't have to. As an extra, GraphQL API was implemented,
so you can use it to get what you need. Following query result will return movie title, 
average rating and showing times in a single server call.

```
{
  movieDetails(id: "tt0232500") {
    title
    averageRating {
      averageRating
      votesCount
    }
    showings(from: "2021-12-06", to: "2021-12-12") {
      date
      showings {
        startAt
      }
    }
  }
}
```

## It compiles - deploy

Hell no - there's still a lot to be done. I think that in Fowler's _Make It Work - Make
It Right - Make It Fast_, current state of the service is just after _It Work_.

At least following should be done:
* Unit tests for all generated mappers, alongside with some programming suggar in form
  of convenient test data builders/fixtures.
* More fine-grained integration tests - now different scenarios are cramped in one place.
  Also some "glue" in form of helper methods should be implemented - for example for
  setting up service state for given scenario.
* Paging in results of API methods returning collections - to avoid returning large payloads
  and crippling service performance.
* Real security config.
* Real cache implementation and configuration - as one currently used can lead to memory
  leaks (no restictions on cache size or time), and return inacurate data as OMDb API 
  responses are cached indefinitely.
* Circuit breaker and fallback movie details provider for case when OMDb API would be 
  unavailable.
* API rate limiting and monitoring - to know if service is performant enough and to know
  when some less naive (non blocking?) implmentation will be required. Not to forget
  about DoS attacks detection.
* Better exception handling.
* Logging - for now code base is so simple, that I have forgotten about this aspect until now.
