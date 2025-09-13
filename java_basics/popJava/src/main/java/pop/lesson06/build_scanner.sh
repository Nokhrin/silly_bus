PROJECT_DIR=$HOME/projects/silly_bus/java_basics/popJava/
OUT_DIR=target/classes
SRC_FILE=src/main/java/pop/lesson04/ScannerInput.java
JAR_FILE=/tmp/scanner_app.jar
MAIN_CLASS=pop.lesson04.ScannerInput

cd "$PROJECT_DIR" || exit
echo "рабочая директория"
pwd
echo "компиляция"
javac -verbose -d $OUT_DIR $SRC_FILE
echo "сборка jar"
jar --verbose --create --file $JAR_FILE --main-class $MAIN_CLASS -C $OUT_DIR .
echo "сформированный jar"
file $JAR_FILE
echo "содержимое сформированного jar"
unzip -l $JAR_FILE
