#!/usr/bin/env bash
cd ..
rm -r dist
mvn clean install

mkdir dist

cp target/logging.jar dist
cp target/*.xml dist
cp -r target/lib dist
cp bash/run.sh dist

