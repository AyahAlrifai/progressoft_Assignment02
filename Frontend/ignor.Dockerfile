#base image
FROM ubuntu:latest
COPY . /ayah
WORKDIR /ayah 

RUN apt-get update \
 && apt-get install -y sudo
RUN adduser --disabled-password --gecos '' docker
RUN adduser docker sudo
RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER docker

RUN sudo apt-get install make 
RUN sudo apt-get install maven -y
RUN sudo apt install mysql-server -y
RUN sudo apt install systemctl -y
#RUN make