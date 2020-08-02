FROM openjdk:14-alpine
COPY exchange-service-*-all.jar exchange-service.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "exchange-service.jar"]
