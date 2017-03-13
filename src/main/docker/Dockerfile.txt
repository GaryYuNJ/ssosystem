FROM dockerfile/java:oracle-java8

VOLUME /tmp
ADD SpringBootDemo.jar /opt/SpringBootDemo/
EXPOSE 8080
WORKDIR /opt/SpringBootDemo/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx1g", "-jar", "SpringBootDemo.jar"]
