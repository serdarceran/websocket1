#!/bin/bash

cd notification-service
mvn clean package


docker-compose rm -fs
docker-compose up --build -d
docker-compose ps

