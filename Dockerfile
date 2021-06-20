FROM openjdk:8-jdk-alpine
EXPOSE 8083
COPY target/soft-ib-account-manager-0.0.1-SNAPSHOT.war soft-ib-account-manager-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/soft-ib-account-manager-0.0.1-SNAPSHOT.war"]