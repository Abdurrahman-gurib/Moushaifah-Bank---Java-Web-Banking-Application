#!/usr/bin/env bash
cd "$(dirname "$0")"
mvn clean package
mvn jetty:run
