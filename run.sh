#!/bin/bash
# must line terminators LF. "file <textfile>"
rm -rf out
javac src/*.java -d out --release 7
jar cvfm out/Slowcat.jar manifest.txt -C out . lib
java -jar out/Slowcat.jar -d 30 -e UTF-8 starwars.txt
