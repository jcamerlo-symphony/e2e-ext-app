#!/bin/bash

#Replacing the default config.json
echo "Replacing the default config.json"
POSITIONAL=()
COMPUTED_JQ=""
while [[ $# -gt 0 ]]; do
  key="$1"

  case $key in
    --podUrl)
      COMPUTED_JQ="${COMPUTED_JQ} | .sessionAuthHost = \"$2\" | .keyAuthHost = \"$2\" | .podHost = \"$2\" | .agentHost = \"$2\""
      POD_URL="$2"
      shift 2
      ;;
    --podPort)
      COMPUTED_JQ="${COMPUTED_JQ} | .sessionAuthPort = $2 | .keyAuthPort = $2 | .podPort = $2 | .agentPort = $2"
      shift 2
      ;;
    *)
      POSITIONAL+=("$1")
      shift
      ;;
  esac
done
jq ". ${COMPUTED_JQ}" ./src/main/resources/config.json > config.tmp && mv config.tmp ./src/main/resources/config.json
cat ./src/main/resources/config.json

# Quick Start 
mvn install
mvn spring-boot:run