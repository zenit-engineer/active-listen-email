@echo off
title Email Listener Process
echo Starting ActiveListenEmail process...

set EMAIL_HOST=smtp.gmail.com
set EMAIL_USERNAME=<change-accordingly>
set EMAIL_PASSWORD=<change-accordingly>
set FROM_SENDER_EMAIL=<change-accordingly>
set DOWNLOAD_PATH=<change-accordingly>
set IMAPS_PORT=993

java -jar ActiveListenEmail-1.0-SNAPSHOT-jar-with-dependencies.jar
pause
