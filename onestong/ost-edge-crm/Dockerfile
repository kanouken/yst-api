# AlpineLinux open jre 8
FROM alpine:latest
# Install cURL
RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories
RUN apk --update add curl bash openjdk8-jre-base && \
      rm -rf /var/cache/apk/*
# Set environment
ENV JAVA_HOME /usr/lib/jvm/default-jvm
ENV PATH ${PATH}:${JAVA_HOME}/bin
ENV	PROFILE pro
ENV JAVA_OPTS="-server -Xmx300M -Xms300M"
COPY onestong/ost-edge-crm/target/*.jar  /app.jar
#COPY onestong/ost-edge-crm/start.sh /
#RUN chmod 777 /start.sh
RUN bash -c 'touch /app.jar'
VOLUME /tmp
EXPOSE 8000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${PROFILE}"]
