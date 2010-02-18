// v0.2 - 19/12/05 kbrunton
// Altered 2/5/06 James Gray to change maths colour and the class of the output node to "answer"

// This script allows customisations of ASCIIMath - it must appear after the script tag
// that imports ASCIIMathML.js

// for simple symbol inclusions, use this syntax
newcommand("!<=","\u2270");
newcommand("!>=","\u2271");
newcommand("tick","\u2713");
newcommand("cross","\u2717");

// KB: added new definitions for times - overrides the older defn - try a*b and a*.b
// JG: commented out KB's times definition because didn't override defn when using IE
//AMsymbols = AMsymbols.concat([{input:"*", tag:"mo", output:"\u00D7", tex:"times", ttype:CONST}])
//AMsymbols = AMsymbols.concat([{input:"*.", tag:"mo", output:"\u22C5", tex:"cdot", ttype:CONST}])
AMsymbols = AMsymbols.concat([{input:"cosec",  tag:"mo", output:"cosec", tex:null, ttype:UNARY, func:true}])
AMsymbols = AMsymbols.concat([{input:"arcsin",  tag:"mo", output:"arcsin", tex:null, ttype:UNARY, func:true}])
AMsymbols = AMsymbols.concat([{input:"arccos",  tag:"mo", output:"arccos", tex:null, ttype:UNARY, func:true}])
AMsymbols = AMsymbols.concat([{input:"arctan",  tag:"mo", output:"arctan", tex:null, ttype:UNARY, func:true}])


// KB: don't allow double-blank to delimit asciimath - more hassle than its worth
doubleblankmathdelimiter = false;

// KB: default colour of maths
mathcolor="";

// KB: explicitly hide style
displaystyle=true;


function AMarr2docFrag(arr) {
  var newFrag=document.createDocumentFragment();
  var tutorComment = false;
  for (var i=0; i<arr.length; i++) {
    if (tutorComment) {
        var newnode =  AMcreateElementXHTML("span");
        newnode.className = "tutor";
        var messagenode = document.createComment(arr[i]+"``");
        newnode.appendChild(messagenode);
        newFrag.appendChild(newnode);
    }
    else {
        newFrag.
        //appendChild(AMcreateElementXHTML("span").
        appendChild(document.createComment(arr[i]+"``"));
    }
    tutorComment = !tutorComment;
  }
  return newFrag;
}


var key1 = false;
var key2 = false;        

// KB: 06/10/05 modified this to take input and output nodes as parameters
// to allow more than one 'editor' place on a page
// JG: Added the variables key1 and key2.  Key1 is true when a key has been pressed, key2 is true
// if another key is pressed before the timout occurs.  This allows you to type fast without the display
// updating (which slows the typing down).
function AMdisplay(inputNodeId, outputNodeId, now) {
    if(!now){
        if(!key1 && !key2) {
            key1 = true;
            setTimeout("AMdisplay('"+inputNodeId+"', '"+outputNodeId+"', true);", 250);
        } else if (key1 && !key2) {
            key2 = true;
        }
    }
    if(now){
        if (key1 && key2){
            key2 = false;
            setTimeout("AMdisplay('"+inputNodeId+"', '"+outputNodeId+"', true);", 250);
        } else if (!key2){
            key1 = false;
            if (document.getElementById(inputNodeId) != null) {
                var str = document.getElementById(inputNodeId).value;
                var outnode = document.getElementById(outputNodeId);
                var newnode = AMcreateElementXHTML("div");
                newnode.setAttribute("id",outputNodeId);
                //JG changed the line below from setAttribute("class","answer") because it didn't work with IE
                newnode.className = "answer";
                outnode.parentNode.replaceChild(newnode,outnode);
                outnode = document.getElementById(outputNodeId);
                var n = outnode.childNodes.length;
                for (var i = 0; i < n; i++)
                    outnode.removeChild(outnode.firstChild);
                var arr = str.split("#");
                var frag = AMarr2docFrag(arr);
                outnode.appendChild(frag);
                AMprocessNode(outnode,true);
            }
        }
    }
}

// KB: Another overridden method
// Live preview of editing requires a piece of ASCIIMath (a blank ``) to work correctly - bug in ASCIImath
// This fails when in 'Image Fallback' mode as empty content returns a 'render failed' image. The addition here
// checks for the condition when not using MathML and discards the (empty) math
function AMstrarr2docFrag(arr, linebreaks,usingMathML) {
  var newFrag=document.createDocumentFragment();
  var expr = false;
  for (var i=0; i<arr.length; i++) {
    // KB: Added condition to ignore array frags that are empty which otherwise caused mimetex to fail
    if (expr && !usingMathML && arr[i]!="") newFrag.appendChild(AMTparseMath(arr[i]));
    else if (expr && usingMathML) newFrag.appendChild(AMparseMath(arr[i]));
    else {
      var arri = (linebreaks ? arr[i].split("\n\n") : [arr[i]]);
      newFrag.appendChild(AMcreateElementXHTML("span").
      appendChild(document.createTextNode(arri[0])));
      for (var j=1; j<arri.length; j++) {
        newFrag.appendChild(AMcreateElementXHTML("p"));
        newFrag.appendChild(AMcreateElementXHTML("span").
        appendChild(document.createTextNode(arri[j])));
      }
    }
    expr = !expr;
  }
  return newFrag;
}

// KB: James - you can, if you want, delete the functions from here on down
// as you don't use them

// This function is a variation on the above function but 
// artificially quotes the input node's value
function AMdisplayQuoted(inputNodeId,outputNodeId,now) {
  if (document.getElementById(inputNodeId) != null) {
    if (AMkeyspressed == 5 || now) {
      var str = "`"+document.getElementById(inputNodeId).value+"`";
      var outnode = document.getElementById(outputNodeId);
      var newnode = AMcreateElementXHTML("span");
      newnode.setAttribute("id",outputNodeId);
      newnode.setAttribute("class","studentanswer");
      outnode.parentNode.replaceChild(newnode,outnode);
      outnode = document.getElementById(outputNodeId);
      var n = outnode.childNodes.length;
      for (var i = 0; i < n; i++)
        outnode.removeChild(outnode.firstChild);
      outnode.appendChild(document.createComment(str+"``"));
      AMprocessNode(outnode,true);
      AMkeyspressed = 0;
    } else AMkeyspressed++;
  }
}

// changes the column textarea size for the given node
function AMchangeColumns(inputNodeId,n) {
  var node = document.getElementById(inputNodeId);
  node.setAttribute("cols",n);
}


// View the mathsML - more a debug tool than anything else
function AMviewMathML(inputNodeId,outputNodeId) {
  AMdisplay(true);
  var str = document.getElementById(inputNodeId).value;
  var outnode = document.getElementById(outputNodeId);
  var outstr = AMnode2string(outnode,"").slice(22).slice(0,-6);
  outstr = '<?xml version="1.0"?>\r\<!-- Copy of ASCIIMathML input\r'+str+
'-->\r<?xml-stylesheet type="text/xsl" href="http://www1.chapman.edu/~jipsen/mathml/pmathml.xsl"?>\r\
<html xmlns="http://www.w3.org/1999/xhtml"\r\
  xmlns:mml="http://www.w3.org/1998/Math/MathML">\r\
<head>\r<title>...</title>\r</head>\r<body>\r'+outstr+'<\/body>\r<\/html>\r';
  var newnode = AMcreateElementXHTML("textarea");
  newnode.setAttribute("id",outputNodeId);
  newnode.setAttribute("rows","30");
  var node = document.getElementById(inputNodeId);
  newnode.setAttribute("cols",node.getAttribute("cols"));
  newnode.appendChild(document.createTextNode(outstr));
  outnode.parentNode.replaceChild(newnode,outnode);
}

