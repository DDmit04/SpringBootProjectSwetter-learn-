#!/usr/bin/env bash

echo 'start building maven progect...'

cd /c/Users/admit-nootebook/Desktop/PhotonWorkspace/sweater-addDbMigration 
mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
     /c/Users/admit-nootebook/Desktop/PhotonWorkspace/sweater-addDbMigration/target/sweater-1.0-SNAPSHOT.jar \
    ddmit04@192.168.0.19:/home/ddmit04/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa ddmit04@192.168.0.19 << EOF
pgrep java | xargs kill -9
nohup java -jar sweater-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'