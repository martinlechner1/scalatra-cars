# Copy of https://github.com/hseeberger/scala-sbt

# Pull base image
FROM  openjdk:8

ENV SCALA_VERSION 2.11.11
ENV SBT_VERSION 0.13.15

# Scala expects this file
RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

# Install Scala
## Piping curl directly in tar
RUN \
  curl -fsL http://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo 'export PATH=~/scala-$SCALA_VERSION/bin:$PATH' >> /root/.bashrc

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /usr/src/app

# use https://github.com/vishnubob/wait-for-it to wait for database to spin up
RUN curl -L -o wait-for-it https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it

#--------------------------------------#
# Our build
# Define working directory

COPY . /usr/src/app
RUN sbt clean assembly
CMD ./wait-for-it $DB_HOST:$DB_PORT -t 30 -- java -jar target/scala-2.11/CarApi-assembly-0.1.0-SNAPSHOT.jar