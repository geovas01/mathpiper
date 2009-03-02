echo "Copy all relevant sources from folder $1"
cd common
for x in `find org/eninom/ -name "*.java"`; do echo $1/src/$x; cp $1/src/$x $x; done
cd ..
for x in `find test/org/eninom/ -name "*.java"`; do echo $1/$x; cp $1/$x $x; done
