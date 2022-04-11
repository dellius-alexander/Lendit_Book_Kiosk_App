FROM openjdk:11.0.12-jdk
RUN mkdir -p /usr/src/${appname} /tmp/${appname}
COPY maven/* /usr/src/${appname}/
VOLUME [ "/usr/src/${appname}/data/" ]
WORKDIR /usr/src/${appname}
RUN echo "${project.build.finalName}.${project.packaging}:lib/*"
# ADD lendit_book_kiosk.jar /opt/lendit_book_kiosk/
EXPOSE 8081
# WORKDIR /opt/lendit_book_kiosk/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx1g", "-jar", "lendit_book_kiosk.jar"]
