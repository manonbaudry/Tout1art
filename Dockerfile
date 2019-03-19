FROM openjdk:8-jdk
RUN apt-get update && apt-get -y install maven && apt-get clean
COPY server /srv/
WORKDIR /srv
RUN mvn package
CMD ["mvn", "exec:java"]
EXPOSE 8080
