rm -rvf implementation-html/*
rm -vf src4tex/*
mkdir src4tex
for x in `find ../src -name "*.java" -print`; do cp -v $x src4tex/.; done


for x in `find src4tex -name "*.java" -print`
do
./selcode.sh literate $x | ./replacetabs.sh | 
./replaceHtmlSymbols.sh | ./code2tex.sh > $x.tex
rm $x
done
latex implementation
for x in `find src4tex -name "*.java.tex" -print`
do
  sed -e "s/\\\begin{code}/\\\begin{rawhtml}<table class=\"ntable\"  width=\"100\\\%\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\"><tr><td bgcolor=#eeefff align=\"left\" dir=\"ltr\"> \\\end{rawhtml}\\\begin{verbatim}/
          s/\\\end{code}/ \\\end{verbatim}\\\begin{rawhtml}<\/td><\/tr><\/table>\\\end{rawhtml}/ 
         " $x > tmp
  mv tmp $x
done
latex2html implementation-html -image_type png -dir implementation-html -mkdir
doxygen doxygen-config.Doxyfile
