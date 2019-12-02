FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/httpSrv-1.0.jar httpSrv-1.0.jar

EXPOSE 8500

CMD java -jar httpSrv-1.0.jar