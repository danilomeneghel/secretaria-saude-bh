#!/bin/bash
set -e

source utils.sh

recover_from_configs keycloak \
  && recover_datasource_from_secrets hubsmsa \
  && sed_configure_datasource \
  && sed_configure_bindings \
  && start_application_server

sleep infinity & wait