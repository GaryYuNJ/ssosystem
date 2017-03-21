FROM java:openjdk-8-jre

#VOLUME /tmp #这是挂在单个目录；多个目录用下面方式；容器启动后，使用 docker inspect -f {{.Volumes}} container_id 命令找到Volume在主机上的存储位置
VOLUME ["/tmp","/opt/AllinOneUserManageSys"]
ADD AllinOneUserManageSys.jar /opt/AllinOneUserManageSys/
EXPOSE 8080
WORKDIR /opt/AllinOneUserManageSys/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx2g", "-jar", "AllinOneUserManageSys.jar"]
