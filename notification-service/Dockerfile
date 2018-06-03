FROM openjdk:slim
RUN mkdir /app
COPY target/*.jar /app/service.jar
WORKDIR /app
ENTRYPOINT ["java"]
CMD ["-Xms256m", "-Xmx1024m","-jar","service.jar"]
#VOLUME /app
EXPOSE 8080
