#!/usr/bin/env bash

cd ..
rm -r dist
mvn clean install
mkdir dist
cp target/logging.jar dist
cp -r target/lib dist
cp target/*.properties dist
cp target/*.xml dist
cp bash/run.sh dist


