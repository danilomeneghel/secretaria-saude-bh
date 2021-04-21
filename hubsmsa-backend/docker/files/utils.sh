#!/bin/bash
set -e

function set_management_user() {
  : ${MANAGEMENT_USERNAME:='admin'}
  : ${MANAGEMENT_PASSWORD:='admin'}

  echo '=> Setting management user'
  $JBOSS_HOME/bin/add-user.sh $MANAGEMENT_USERNAME $MANAGEMENT_PASSWORD
}

function start_application_server() {
  echo '=> Starting Wildfly'
  $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 &
}

function recover_from_configs() {
  CONFIG_NAME=$1

  if [ -f "/run/configs/${CONFIG_NAME}" ]; then
    KEYCLOAK_REALM=`grep -Po "KEYCLOAK_REALM=(.*)$" /run/configs/${CONFIG_NAME} | cut -d= -f2`
    KEYCLOAK_RESOURCE=`grep -Po "KEYCLOAK_RESOURCE=(.*)$" /run/configs/${CONFIG_NAME} | cut -d= -f2`
    KEYCLOAK_URL=`grep -Po "KEYCLOAK_URL=(.*)$" /run/configs/${CONFIG_NAME} | cut -d= -f2`
    KEYCLOAK_SECRET=`grep -Po "KEYCLOAK_SECRET=(.*)$" /run/configs/${CONFIG_NAME} | cut -d= -f2`
  else
    echo "O arquivo de configs /run/configs/${CONFIG_NAME} não existe"
    exit 0
  fi
}

function recover_datasource_from_secrets() {
  SECRET_NAME=$1

  if [ -f "/run/secrets/${SECRET_NAME}" ]; then
    DS_NAME=`grep -Po "DS_NAME=(.*)$" /run/secrets/${SECRET_NAME} | cut -d= -f2`
    DS_CONNECTION_URL=`grep -Po "DS_CONNECTION_URL=(.*)$" /run/secrets/${SECRET_NAME} | cut -d= -f2`
    DS_USERNAME=`grep -Po "DS_USERNAME=(.*)$" /run/secrets/${SECRET_NAME} | cut -d= -f2`
    DS_PASSWORD=`grep -Po "DS_PASSWORD=(.*)$" /run/secrets/${SECRET_NAME} | cut -d= -f2`
  else
    echo "O arquivo de secrets /run/secrets/${SECRET_NAME} não existe"
    exit 0
  fi
}

function sed_configure_datasource(){
  echo '=> Adding datasourse '$DS_NAME
  sed -i 's|{{DS_NAME}}|'$DS_NAME'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
  sed -i 's|{{DS_CONNECTION_URL}}|'$DS_CONNECTION_URL'?currentSchema=hubsmsa|g' $JBOSS_HOME/standalone/configuration/standalone.xml
  sed -i 's|{{DS_USERNAME}}|'$DS_USERNAME'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
  sed -i 's|{{DS_PASSWORD}}|'$DS_PASSWORD'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
}

function sed_configure_bindings(){
    echo '=> Adding bindings...'
	sed -i 's|$URL_APLICACAO|'${URL_APLICACAO}'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
	sed -i 's|$KEYCLOAK_REALM|'${KEYCLOAK_REALM}'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
	sed -i 's|$KEYCLOAK_RESOURCE|'${KEYCLOAK_RESOURCE}'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
	sed -i 's|$KEYCLOAK_URL|'${KEYCLOAK_URL}'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
	sed -i 's|$KEYCLOAK_SECRET|'${KEYCLOAK_SECRET}'|g' $JBOSS_HOME/standalone/configuration/standalone.xml
}

