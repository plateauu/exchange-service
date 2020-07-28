FROM openjdk:14-alpine
COPY build/libs/bank-*-all.jar bank.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "bank.jar"]