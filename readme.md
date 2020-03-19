# Taco APi

## Building

To build simply run 
```
./gradlew build
```

if you want to containerize your build run 
```
docker-compose up build
```

## Image

if you would like to Image for production simply run 
```
docker build -f prod.dockerfile -t taco-api:semantic_version
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

if you want to containerize your run the following first 
```
docker-compose up build bash
```

To run simply run 
```
./gradlew run
```
