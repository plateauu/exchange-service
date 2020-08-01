#!/usr/bin/env bash

JAR_NAME=exchange-service-0.1-all.jar
PROJECT_NAME=$(echo $JAR_NAME | cut -d'-' -f 1,2,3)

function compile() {
  echo "#############################"
  echo
  echo "Building $PROJECT_NAME project"
  echo
  echo "#############################"
  echo
  echo

  ./gradlew clean build
}

function moveToRootAndClean() {
  mv "build/libs/$JAR_NAME" .
  rm -rf build
}
