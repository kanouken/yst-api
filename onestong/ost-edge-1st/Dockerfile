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

COPY onestong/ost-edge-1st/target/*.jar  /app.jar
RUN bash -c 'touch /app.jar'
#构建应用
#RUN cd  /tmp/build/ && mvn -q -DskipTests=true  package -pl ost-edge-1st/ -am \
#        #拷贝编译结果到指定目录
#        && mv ost-edge-1st/target/*.jar /app.jar \
#        #清理编译痕迹
#        && cd / && rm -rf /tmp/build
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]