FROM maven:3.6.3 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn clean package

FROM tomcat:9.0-jdk11
WORKDIR /usr/local/tomcat/webapps/
COPY --from=maven /usr/src/app/target/parking-lot-api.war /usr/local/tomcat/webapps/parking-lot-api.war
RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
