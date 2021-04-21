FROM nexus-imagens.pbh.gov.br/prodabel/debian-stretch-slim_jre-8u181-x64_wildfly-14.0.1.final
COPY files/*.sh /usr/local/bin/
COPY files/zips/*.zip /tmp/
COPY files/standalone.xml /tmp/

ADD hubsmsa.ear $JBOSS_HOME/standalone/deployments

ADD files/certificado.cer /tmp

USER root

RUN apt update \
  && apt install -y unzip \
  && unzip /tmp/postgresql-module.zip -d $JBOSS_HOME/modules/system/layers/base \
  && unzip /tmp/keycloak-wildfly-adapter-dist-6.0.1.zip -d $JBOSS_HOME \
  && yes | cp -rf /tmp/standalone.xml $JBOSS_HOME/standalone/configuration/ \
  && chmod 755 /usr/local/bin/*.sh \
  && chown jboss:jboss $JBOSS_HOME -R \
  && keytool -v -import -noprompt -trustcacerts -alias pbhgovbr2 -file /tmp/certificado.cer -keystore /usr/lib/jvm/jre/lib/security/cacerts -storepass changeit \
  && echo 'JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Duser.language=pt -Duser.country=BR -Dfile.encoding=UTF8"' >> $JBOSS_HOME/bin/standalone.conf

ENTRYPOINT [ "/usr/local/bin/entrypoint.sh"]
