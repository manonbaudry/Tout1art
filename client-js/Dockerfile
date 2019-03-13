FROM node

ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update \
    && apt-get install -y \
      build-essential \
    && apt-get clean \
    && mkdir /var/run/sshd

WORKDIR /workspace

COPY . /workspace/
