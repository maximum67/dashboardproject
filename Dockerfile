FROM openjdk:17
ADD /target/dashboardproject-0.0.1-SNAPSHOT.jar dashboard.jar
ENTRYPOINT ["java","-jar","dashboard.jar"]