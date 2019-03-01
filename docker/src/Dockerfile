FROM yoshinorin/docker-sbt:1.2.8

MAINTAINER YoshinoriN

RUN mkdir -p /usr/opt

WORKDIR /usr/opt

RUN apt-get update -y \
 && apt-get install git -y \
 && apt-get autoremove \
 && apt-get autoclean

RUN git clone https://github.com/YoshinoriN/Orchard.git \
  && cd Orchard \
  && rm -rf orchard-frontend .git \
  && rm .editorconfig \
  && cd orchard-server \
  && rm -rf docs \
  && sbt exit

EXPOSE 9001

WORKDIR /usr/opt/Orchard/orchard-server

CMD ["sbt", "runMain net.yoshinorin.orchard.BootStrap"]
