#!/bin/sh
sed -e "
:t
1,1 {	/^[ ]*\/\*/ {d; bt
	}
	s/^/\\\begin\{code\}\n/; bt
}
$,$ {	/^[ ]*\*\// {d; bt
	}
	s/$/\n\\\end\{code\}\n/; N; bt
}
s/^[ ]*\/\*\*/\\\end\{code\}/
s/^[ ]*\/\*/\\\end\{code\}/
s/^[ ]*\*\//\\\begin\{code\}/
s/^[ ]*\*//
" $1
