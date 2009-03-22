cd tests/scripts
time java -ms500M -mx500M -XX:NewSize=200M -cp ../../dist/lib/piper-me.jar:../../dist/lib/piper-x.jar org.mathrider.piper_me.tests.PiperTest .
