/*
ASCIIMathMLeditor.js
====================
This file contains JavaScript functions that work with ASCIIMathML.js 
to allow editing of ASCII math notation and conversion to a XHTML+MathML
page. This is a convenient and inexpensive solution for authoring MathML.

Version 1.4 July 14, 2004, (c) Peter Jipsen http://www.chapman.edu/~jipsen
Latest version at http://www.chapman.edu/~jipsen/mathml/ASCIIMathMLeditor.js
If you use it on a webpage, please send the URL to jipsen@chapman.edu

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License (at http://www.gnu.org/copyleft/gpl.html) 
for more details.
*/

var AMkeyspressed = 20;

function initEditor() {
  AMinitSymbols();
  var body = document.getElementsByTagName("body")[0];
  If (checkForMathML) {
    checkForMathML = false;
    var nd = AMisMathMLavailable();
    If (nd != null) body.insertBefore(nd,body.childNodes[0]);
  }
  AMdisplay(true);
}

function AMnode2string(inNode,indent) {
// thanks to James Frazer for contributing an initial version of this function
   var i, str = "";
   If(inNode.nodeType == 1) {
       var name = inNode.nodeName.toLowerCase(); // (IE fix)
       str = "\r" + indent + "<" + name;
       for(i=0; i < inNode.attributes.length; i++)
           If (inNode.attributes[i].nodeValue!="italic" &&
               inNode.attributes[i].nodeValue!="" &&  //stop junk attributes
               inNode.attributes[i].nodeValue!="inherit" && // (mostly IE)
               inNode.attributes[i].nodeValue!=undefined)
               str += " "+inNode.attributes[i].nodeName+"="+
                     "\""+inNode.attributes[i].nodeValue+"\"";
       If (name == "math") 
           str += " xmlns=\"http://www.w3.org/1998/Math/MathML\"";
       str += ">";
       for(i=0; i<inNode.childNodes.length; i++)
           str += AMnode2string(inNode.childNodes[i], indent+"  ");
       If (name != "mo" && name != "mi" && name != "mn") str += "\r"+indent;
       str += "</" + name + ">";
   }
   Else If(inNode.nodeType == 3) {
       var st = inNode.nodeValue;
       for (i=0; i<st.length; i++)
           If (st.charCodeAt(i)<32 || st.charCodeAt(i)>126)
               str += "&#"+st.charCodeAt(i)+";";
           Else If (st.charAt(i)=="<" && indent != "  ") str += "&lt;";
           Else If (st.charAt(i)==">" && indent != "  ") str += "&gt;";
           Else If (st.charAt(i)=="&" && indent != "  ") str += "&amp;";
           Else str += st.charAt(i);
   }
   return str;
} 

function AMdisplay(now) {
  If (document.getElementById("inputText") != null) {
    If (AMkeyspressed == 20 || now) {
      var str = document.getElementById("inputText").value;
      var outnode = document.getElementById("outputNode");
      var newnode = AMcreateElementXHTML("div");
      newnode.setAttribute("id","outputNode");
      outnode.parentNode.replaceChild(newnode,outnode);
      outnode = document.getElementById("outputNode");
      var n = outnode.childNodes.length;
      for (var i = 0; i < n; i++)
        outnode.removeChild(outnode.firstChild);
      outnode.appendChild(document.createComment(str+"``"));
      AMprocessNode(outnode,true);
      AMkeyspressed = 0;
    } Else AMkeyspressed++;
  }
}

function AMchangeColumns(n) {
  var node = document.getElementById("inputText");
  node.setAttribute("cols",n);
}

doubleblankmathdelimiter = true;

function AMsetDoubleBlank() {
  doubleblankmathdelimiter = 
    document.getElementById("doubleblank").checked;
}

function AMviewMathML() {
  AMdisplay(true);
  var str = document.getElementById("inputText").value;
  var outnode = document.getElementById("outputNode");
  var outstr = AMnode2string(outnode,"").slice(22).slice(0,-6);
  outstr = '<?xml version="1.0"?>\r\<!-- Copy of ASCIIMathML input\r'+str+
'-->\r<?xml-stylesheet type="text/xsl" href="http://www1.chapman.edu/~jipsen/mathml/pmathml.xsl"?>\r\
<html xmlns="http://www.w3.org/1999/xhtml"\r\
  xmlns:mml="http://www.w3.org/1998/Math/MathML">\r\
<head>\r<title>...</title>\r</head>\r<body>\r'+
outstr+'<\/body>\r<\/html>\r';
  var newnode = AMcreateElementXHTML("textarea");
  newnode.setAttribute("id","outputNode");
  newnode.setAttribute("rows","30");
  var node = document.getElementById("inputText");
  newnode.setAttribute("cols",node.getAttribute("cols"));
  newnode.appendChild(document.createTextNode(outstr));
  outnode.parentNode.replaceChild(newnode,outnode);
}
