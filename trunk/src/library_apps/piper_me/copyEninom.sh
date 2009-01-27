echo "Copy all relevant sources from folder $1"
for x in `find src/org/eninom/ -name "*.java"`; do echo $1/$x; cp $1/$x $x; done
for x in `find test/org/eninom/ -name "*.java"`; do echo $1/$x; cp $1/$x $x; done