#!/bin/sh
p="[ ]*\/\*[ ]*<$1>[ ]*\*\/"
sed -e "0,/^$p/ d; /^$p/,$ d" $2