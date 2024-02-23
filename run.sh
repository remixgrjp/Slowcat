#!/bin/bash
# must line terminators LF. "file <textfile>"
rm -rf out
javac src/*.java -d out
jar cvfm out/Slowcat.jar manifest.txt -C out . lib
java -jar out/Slowcat.jar < starwars.txt
