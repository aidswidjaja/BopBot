#!/bin/sh

# This will cause the script to run in a loop so that the bot auto-restarts
# when you use the shutdown command
LOOP=true

run() {
    nohup java -Dnogui=true -jar $(ls -t JMusicBot* | head -1) | tee ~/logs.txt
}

while
    run
    $LOOP
do
    continue
done 
