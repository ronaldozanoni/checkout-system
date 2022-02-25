# checkout-api

Basic API providing backend to a checkout system.

## Requirements

For building and running the application you need:

- JDK 17
- MongoDB (or you could use a Docker container instead)

## Running the application locally

Assuming that the MongoDB is running and listening to the default port, then all you need to do is:
```shell
$ ./gradlew bootRun
```

## Running tests

You can either run:
```shell
$ ./gradlew test
```

or run them with the IDE of your choice. If that's the case, remember to configure your IDE's `gradle` to use Java 17.

## Build the docker image

First, you'll need to build the app:
```shell
$ ./gradlew clean build
```

After that, you can both build the Dockerfile manually with `docker build .` or rely on the `docker-compose.yaml` file in parent directory to do so.
