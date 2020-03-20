# Taco API

## Building

To build simply run

```bash
./gradlew build
```

if you want to containerize your build run

```bash
docker-compose up build
```

## Image

if you would like to Image for production simply run

```bash
docker build -f prod.dockerfile -t taco-api:semantic_version .
```

remember to replace `semantic_version` with an actual version

## Run In Production

to run in production you can simply use docker

```bash
docker run -p 80:8080 znackasha/taco-api
```

or you can run the jar directly

```bash
PORT=80 java -jar ./build/libs/taco-api-0.1-all.jar
```

## Run for development

if you want to containerize your run

```bash
docker-compose up run
```

if you want to run on host simply run

```bash
./gradlew run
```

## Run test

if you want to containerize your run

```bash
docker-compose run run ./gradlew --no-daemon test
```

if you want to run on host simply run

```bash
./gradlew test
```

## View the api

to view the api endpoints of the app simply go to `localhost/swagger-ui` while the app is running.

you can also view the api using `localhost/redoc` and `localhost/rapidoc`
