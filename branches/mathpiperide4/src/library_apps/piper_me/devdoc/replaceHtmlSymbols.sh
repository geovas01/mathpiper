#!/bin/sh
sed -e "
s/&uuml;/\\\"u/g
s/&auml;/\\\"a/g
s/&ouml;/\\\"o/g
s/&szlig;/\\\"s/g
s/&Uuml;/\\\"U/g
s/&Auml;/\\\"A/g
s/&Ouml;/\\\"O/g
s/&gt;/\\\$>$/g
s/&lt;/\\\$<$/g
s/<br \/>/\\\\\\\/g
s/<b>/\\{\\\bf /g
s/<\/b>/\\}/g
s/<i>/\\{\\\em /g
s/<\/i>/\\}/g
s/@param/\\\ \\\\\\\ $\\\bullet$ Parameter/g
" $1
