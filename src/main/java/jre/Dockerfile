FROM ubuntu:22.04 AS builder
RUN apt-get update && apt-get install -y openjdk-8-jdk
WORKDIR /app
ADD HelloWorld.java .

RUN javac -source 8 -target 8 HelloWorld.java -d .
RUN ls .

FROM ubuntu/jre:8-22.04_edge

WORKDIR /
COPY --from=builder /app/jre/HelloWorld.class ./jre/

CMD [ "jre.HelloWorld" ]
