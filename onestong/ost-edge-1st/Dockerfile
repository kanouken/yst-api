FROM maven:3.3.3
COPY onestong /tmp/build/
RUN ls
        #构建应用
RUN cd  /tmp/build/ && mvn -q -DskipTests=true  package -pl ost-edge-1st/ -am \
        #拷贝编译结果到指定目录
        && mv ost-edge-1st/target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build
		
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]