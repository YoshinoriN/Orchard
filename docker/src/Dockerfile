FROM yoshinorin/docker-sbt:1.2.8

MAINTAINER YoshinoriN

RUN mkdir -p /usr/opt

WORKDIR /usr/opt

RUN apt-get update -y \
 && apt-get install git -y \
 && apt-get autoremove \
 && apt-get autoclean

RUN git clone https://github.com/YoshinoriN/Selfouettie.git \
  && cd Selfouettie \
  && rm -rf selfouettie-frontend .git \
  && rm .editorconfig \
  && cd selfouettie-server \
  && rm -rf docs

EXPOSE 9001

WORKDIR /usr/opt/Selfouettie/selfouettie-server

COPY docker-entrypoint.sh .

ENTRYPOINT "/usr/opt/Selfouettie/selfouettie-server/docker-entrypoint.sh"
