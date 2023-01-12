FROM openjdk:11
ARG JAR_FILE=build/lib/application.jar
COPY ${JAR_FILE} ./
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "application.jar"]
