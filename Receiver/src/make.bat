@echo off
echo "Compilation..."
mkdir bin
javac -d bin *.java
echo "Cr�ation du package..."
cd bin
jar cfm DataReceiver.jar ../manifest.mf *.class
del *.class
cd ..
echo "Make complete !"
pause