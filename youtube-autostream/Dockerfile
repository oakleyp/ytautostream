FROM eclipse-temurin:8-alpine

RUN cd && \
apk update && \
apk upgrade && \
apk add ffmpeg

WORKDIR /app/

VOLUME /tmp
COPY ./target/youtube-autostream-0.0.1-SNAPSHOT.jar /app/app.jar
RUN sh -c 'touch /app/app.jar'
ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]