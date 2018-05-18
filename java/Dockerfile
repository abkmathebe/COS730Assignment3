FROM alpine:3.2

ENV WILDFLY_VERSION 9.0.2.Final
ENV JBOSS_HOME /opt/jboss/wildfly
ENV JAVA_VERSION_MAJOR 8
ENV JAVA_VERSION_MINOR 171
ENV JAVA_VERSION_BUILD 17
ENV JAVA_HOME /opt/jdk
ENV PATH ${PATH}:${JAVA_HOME}/bin
ENV POSTGRESQL_VERSION 9.4-1201-jdbc41

# Install cURL, tar, and setup home directory
RUN apk --update add curl ca-certificates tar && \
    curl -Ls https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.21-r2/glibc-2.21-r2.apk > /tmp/glibc-2.21-r2.apk && \
    apk add --allow-untrusted /tmp/glibc-2.21-r2.apk && \
    ln -s /lib/libc.musl-x86_64.so.1 /usr/lib/libc.musl-x86_64.so.1 && \
    ln -s /lib/libz.so.1 /usr/lib/libz.so.1 && \
    rm -rf /var/cache/apk/* && \
    rm /tmp/glibc*


# Install Java8 from oracle (from https://developer.atlassian.com/blog/2015/08/minimal-java-docker-containers/)
RUN mkdir /opt && \
  curl -jksSLH "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u171-b11/512cd62ec5174c3487ac17c61aaa89e8/jdk-${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-linux-x64.tar.gz | tar -xzf - -C /opt && \
  ln -s /opt/jdk1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} /opt/jdk && \
  rm -rf /opt/jdk/*src.zip \
       /opt/jdk/lib/missioncontrol \
       /opt/jdk/lib/visualvm \
       /opt/jdk/lib/*javafx* \
       /opt/jdk/jre/lib/plugin.jar \
       /opt/jdk/jre/lib/ext/jfxrt.jar \
       /opt/jdk/jre/bin/javaws \
       /opt/jdk/jre/lib/javaws.jar \
       /opt/jdk/jre/lib/desktop \
       /opt/jdk/jre/plugin \
       /opt/jdk/jre/lib/deploy* \
       /opt/jdk/jre/lib/*javafx* \
       /opt/jdk/jre/lib/*jfx* \
       /opt/jdk/jre/lib/amd64/libdecora_sse.so \
       /opt/jdk/jre/lib/amd64/libprism_*.so \
       /opt/jdk/jre/lib/amd64/libfxplugins.so \
       /opt/jdk/jre/lib/amd64/libglass.so \
       /opt/jdk/jre/lib/amd64/libgstreamer-lite.so \
       /opt/jdk/jre/lib/amd64/libjavafx*.so \
       /opt/jdk/jre/lib/amd64/libjfx*.so && \
 echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf

ENV MAVEN_VERSION 3.5.2
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# Install Wildfly and add an admin user (password admin)
RUN cd /tmp && \
  curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
  tar xf wildfly-$WILDFLY_VERSION.tar.gz && \
  mkdir -p $JBOSS_HOME && \
  mv /tmp/wildfly-$WILDFLY_VERSION/* $JBOSS_HOME/ && \
  rm -r wildfly-* && \
  $JBOSS_HOME/bin/add-user.sh admin -p admin -s

# Install postgres drivers and datasource
RUN /bin/sh -c '$JBOSS_HOME/bin/standalone.sh &' && \
  sleep 10 && \
  cd /tmp && \
  curl --location --output postgresql-${POSTGRESQL_VERSION}.jar --url http://search.maven.org/remotecontent?filepath=org/postgresql/postgresql/${POSTGRESQL_VERSION}/postgresql-${POSTGRESQL_VERSION}.jar && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command="module add --name=org.postgres --resources=/tmp/postgresql-${POSTGRESQL_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command="/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)" && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command="data-source add --jndi-name=java:/reportsDB --name=PostgresPool --connection-url=jdbc:postgresql://postgres:5432/reportsDB --driver-name=postgres --user-name=postgres --password=P@55w0rD" && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command="/subsystem=naming/binding=java\:global\/env\/useMock:add(binding-type=simple, type=boolean, value=true)" && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command="/subsystem=naming/binding=java\:global\/env\/executionUrl:add(binding-type=simple, type=java.lang.String, value="localhost:8080")" && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command=:shutdown && \
  rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
  rm /tmp/postgresql-9.4*.jar && \
  rm -rf /tmp/postgresql-*.jar

#ADD ./create.sql /docker-entrypoint-initdb.d/

ADD ./quantumup/. /usr/src/quantumup/
WORKDIR /usr/src/quantumup/
RUN mvn clean install
RUN cp /usr/src/quantumup/target/quantumup.war /opt/jboss/wildfly/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
