#!/usr/bin/env bash

DEFAULT_PORT=8989
PORT="${1:-$DEFAULT_PORT}"

source ./sources.sh

function compileIfNotExists() {
  if [ -f "$JAR_NAME" ]; then
    echo "Fat-jar $JAR_NAME exists. Using existing file, skipping compilation"
  else
    echo "Fat-jar $JAR_NAME not exist. Compiling sources..."
    echo
    compile
    moveToRootAndClean
  fi
}

function deploy() {
  local PORT=$1
  echo "Running $PROJECT_NAME project at http://localhost:$PORT"
  echo "##########################################################"

  java -Dmicronaut.server.port=$PORT -jar $JAR_NAME
}

echo "##########################################################"
echo "Starting deployment"
echo

compileIfNotExists

echo

deploy "$PORT"
