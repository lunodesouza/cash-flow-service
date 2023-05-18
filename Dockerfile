FROM amazoncorretto:17.0.7

COPY target/*.jar /opt/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
HEALTHCHECK --interval=25s --timeout=3s --retries=2 CMD wget --spider http://localhost:8080/actuator/health || exit 1