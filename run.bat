RMDIR /S /Q out
javac src/*.java -d out --release 7
REM 
REM jar --create --file out/Slowcat.jar --manifest manifest.txt lib -C out .
REM jar --create --file out/Slowcat.jar --manifest manifest.txt -C out . lib
REM jar cvfm out/Slowcat.jar manifest.txt lib -C out .
REM 
REM ==
REM 
jar cvfm out/Slowcat.jar manifest.txt -C out . lib
java -jar out/Slowcat.jar -d 30 -e UTF-8 starwars.txt
PAUSE
