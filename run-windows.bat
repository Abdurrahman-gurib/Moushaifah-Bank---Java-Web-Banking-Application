@echo off
cd /d %~dp0
mvn clean package
mvn jetty:run
pause
