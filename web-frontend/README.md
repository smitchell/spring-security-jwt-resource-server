# WebFrontend

This project is the frontend for this security demonstration. It requires the other two services in this projects parent to be running.

If everything is working correctly, the Angular application detects that you are not signed in and redirects your browser to the login page.


# Building

Run gradle from the root of this project.

```
./gradlew build  
```

# Running

You can run the native angular application or run it from the Spring Boot wrapper.

## Serving Angular

```
cd src/main/webapp
ng build
ng serve
```

## Launching Spring Boot
```
./gradlew build
cd /build/libs
java -jar web-frontend-0.0.1-SNAPSHOT.jar 
```

