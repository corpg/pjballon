@echo off
echo "Compilation..."
mkdir bin
javac -d bin *.java
echo "Création du package..."
cd bin
jar cfm DataSender.jar ../manifest.mf *.class
del *.class
cd ..
echo "Make complete !"
pause