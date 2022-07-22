FROM adoptopenjdk/openjdk12

COPY build/libs/pos-0.0.1-SNAPSHOT.jar application.jar

ENTRYPOINT ["java","-jar","application.jar"]
