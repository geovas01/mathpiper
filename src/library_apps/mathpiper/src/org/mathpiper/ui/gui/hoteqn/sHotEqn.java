/*****************************************************************************
*                                                                            *
*                 HotEqn Equation Viewer Swing Component                     *
*                                                                            *
******************************************************************************
* Java-Coponent to view mathematical Equations provided in the LaTeX language*
******************************************************************************

Copyright 2006 Stefan Muller and Christian Schmid

This file is part of the HotEqn package.

    HotEqn is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; 
    HotEqn is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

******************************************************************************
*                                                                            *
* Constructor:                                                               *
*   sHotEqn()                Construtor without any initial equation.        *
*   sHotEqn(String equation) Construtor with initial equation to display.    *
*   sHotEqn(String equation, JApplet app, String name)                       *
*                            The same as above if used in an applet          *
*                            with applet name.                               *
*                                                                            *
* Public Methods:                                                            *
*   void setEquation(String equation)  Sets the current equation.            *
*   String getEquation()               Returns the current equation.         *
*   void setDebug(boolean debug)       Switches debug mode on and off.       *
*   boolean isDebug()                  Returns the debug mode.               * 
*   void setFontname(String fontname)  Sets one of the java fonts.           *
*   String getFontname()               Returns the current fontname.         *
*   void setFontsizes(int g1, int g2, int g3, int g4) Sets the fontsizes     *
*                                      for rendering. Possible values are    *
*                                      18, 14, 16, 12, 10 and 8.             *
*   void setBackground(Color BGColor)  Sets the background color.            *
*                                      Overrides method in class component.  *           
*   Color getBackground()              Returns the used background color.    *
*                                      Overrides method in class component.  *           
*   void setForeground(Color FGColor)  Sets the foreground color.            *
*                                      Overrides method in class component.  * 
*   Color getForeground()              Returns the used foreground color.    *
*                                      Overrides method in class component.  * 
*   void setBorderColor(Color border)  Sets color of the optional border.    *
*   Color getBorderColor()             Returns the color of the border.      * 
*   void setBorder(boolean borderB)    Switches the border on or off.        * 
*   boolean isBorder()                 Returns wether or not a border is     *
*                                      displayed.                            *
*   void setRoundRectBorder(boolean borderB)                                 *
*                                      Switches between a round and a        *
*                                      rectangular border.                   *
*                                      TRUE: round border                    *
*                                      FALSE: rectangular border             *
*   boolean isRoundRectBorder()        Returns if the border is round or     *
*                                      rectangular.                          *
*   void setEnvColor(Color env)        Sets color of the environment.        *
*   Color getEnvColor()                Returns the color of the environment. * 
*   void setHAlign(String halign)      Sets the horizontal alignment.        *
*                                      Possible values are: left, center and *
*                                      right.                                *
*   String getHAlign()                 Returns the horizontal alignment.     *
*   void setVAlign(String valign)      Sets the vertical alignment.          *
*                                      Possible values are: top, middle and  *
*                                      bottom.                               *
*   public String getVAlign()          Returns the vertical alignment.       *
*   void setEditable(boolean editableB)  Makes the component almost editable.*
*                                      Parts of the displayed equation are   *
*                                      selectable when editable is set true. *
*                                      This is turned on by default.         *
*   boolean isEditable()               Returns wether or not the equation    *
*                                      is editable (selectable).             *
*   String getSelectedArea()           Return selected area of an equation.  *
*   Dimension getPreferredSize()       Returns the prefered size required to *
*                                      display the entire shown equation.    *
*                                      Overrides method in class component.  *
*   Dimension getMinimumSize()         This method return the same value as  *
*                                      getPreferedSize                       *
*                                      Overrides method in class component.  *
*   Dimension getSizeof(String equation) Returns the size required to        *
*                                      display the given equation.           *
*   void addActionListener(ActionListener listener)                          *
*                                      Adds the specified action listener to *
*                                      receive action events from this text  *
*                                      field.                                *
*   void removeActionListener(ActionListener listener)                       *
*                                      Removes the specified action listener *
*                                      to receive action events from this    *
*                                      text field.                           *
*   Image getImage()                   Returns the HotEqn image              *  
*                                                                            *
******************************************************************************
************  Version 0.x                *************************************
* 15.07.1996  Start.                                                         *
* 18.07.1996  Parameter Expansion                                            *
* 22.07.1996  Scanner: Token Table                                           *
* 24.07.1996  Fraction \frac{ }{ }                                           *
* 25.07.1996  Root \sqrt{}, Tief _, High ^, recursive. Font.                 *
* **********  Version 1.0                *************************************
* 26.07.1996  Array \array                                                   *
* 29.07.1996  Parentheses \left ( | \{ \[ \right ) | \} \]                   *
*             public setEquation(String equation) for JS                     *
* 30.07.1996  Greek symbols in Scanner                                       *
* 04.08.1996  Greek Symbols isolation to be downloaded from the net.         *
* 05.08.1996  Greek character set refresh (black and white Prob.)               *
* **********  Version 1.01               *************************************
* 29.08.1996  \sum Sum, \prod Product                                        *
* **********  Version 1.02               *************************************
* 23.09.1996  Various large \bar \hat \acute \grave \dot                   *
*             \tilde \ddot                                                   *
* **********  Version 1.03               *************************************
* 24.09.1996  Handing over mechanism between the various                     *
*             applets on an HTML page.                                       *
* **********  Version 1.04               *************************************
*             evalMFile at mouse-click (->JS->Plugin)                        *
*             engGetFull                                                     *
* 14.10.1996  Matrix2LaTeX retrieves current matrix by the plugin            *
*               and calls on setRightSide.                                   *
* 15.10.1996  All plugin functions with arguments that have to               *
*                the argument from JS fetch "var VCLabHandle"                *
************  Version 1.05               *************************************
* 18.10.1996  Solution Applet -> Plugin (everything back to results !!)      *
************  Version 1.1                *************************************
* 04.01.1997  Integral \int_{}^{}                                            *
*             Limits \lim \infty \arrow                                      *
* 22.01.1997  Corrected the engGetFull() method                              *
******************************************************************************
**************   Release of Version 2.0  *************************************
*                                                                            *
*        1997 Chr. Schmid, S. Mueller                                        *
*             Redesigned for Matlab 5                                        *
* 05.11.1997  Renaming of the parameters                                     *
*             old:             new:                                          *
*             engEvalString    mEvalString                                   *
*             eval             mEvalString                                   *
*             evalMFile        mEvalMFile                                    *
*             engGetFull       mGetArray                                     *
*             Matrix2LaTeX     mMatrix2LaTeX                                 *
* 09.11.1997  Background and Foreground Color, Border, Size                  *
* 10.11.1997  Separation into HotEqn(no MATLAB) and mHotEqn(MATLAB) version  *
* 12.11.1997  Scanner compactified, parser small changes:                    *
*             new methof: adjustBox for recalculation of box size after      * 
*             function calls.                                                *
*             \sin \cos .... not italics                                     *
* 16.11.1997  setEquation(String LeftSideS, String RightSideS) method added  *
* 23.11.1997  Paint not reentrant                                            *
* 13.11.1997  Binary operators         (Kopka: LaTeX: Kap. 5.3.3) prepared   * 
*  (2.00c)    quantities and their negation    ( "    Kap. 5.3.4)    "       *
*             Arrows                           ( "    Kap. 5.3.5)    "       *
*             various additional symbols       ( "    Kap. 5.3.6)    "       *
*             additional horizontal spaces \, \; \: \!            prepared   *
*             \not                                                prepared   *
* 29.11.1997  Scanner optimized (2.00d)                                      *
* 30.11.1997  Paint buffered (2.00e)                                         *
* 03.12.1997  horizontal spaces, \not, \not{<eqn>} implemented       (2.00f) *
* 06.12.1997  ! cdot cdots lim sup etc. ( ) oint arrows some symb.   (2.00g) *
* 08.12.1997  left and right []                                      (2.00h) *
* 08.12.1997  default font plain                                     (2.00i) *
* 11.12.1997  SINGLE (false) argument and STANDARD (true)                    *
*             (e.g. \not A or \not{a+B} ) for all commands, where single     *
*             or multiple arguments are allowed (_ ^ \sum ... )      (2.00j) * 
* 13.12.1997  A_i^2 (i plotted over 2, according to LaTex)           (2.00k) *
* 14.12.1997  LaTeX Syntax for brackets, beautified array,frac,fonts (2.00l) *
* 18.12.1997  scanner reduced to one scan, tokens now stored in array(2.00m) *
* 19.12.1997  all bracket types implemented by font/draw             (2.00n) *
* 20.12.1997  bracket section new, Null,ScanInit deadlock removed    (2.00o) *
* 22.12.1997  separation of HotEqn.java EqScanner.java EqToken.java  (2.00p) *
*             \choose \atop                                                  *
* 26.12.1997  overline underline overbrace underbrace stackrel       (2.00q) *
*             \fgcolor{rrggbb}{...} \bgcolor{rrggbb}{...}            (2.00r) *
* 30.12.1997  ScanInit,setEqation combined \choose modified to \atop (2.00s) *
*             and some other minor optimizations                             *
* 31.12.1997  overline underline sqrt retuned                        (2.00t) *
*             overbrace and underbrace uses arc, new <> Angle                *
*             right brackets with SUB and SUP                                *
* 31.12.1997  getWidth()  getHeight() Ermittl. d. Groesse v. aussen  (2.00u) *
*             \begin{array}{...} ... \end{array}                             *
* 01.01.1998  Tokens stored dynamically (limit 500 tokens removed)   (2.00v) *
*             Some minor optimization in serveral functions                  *
* 02.01.1998  \fbox \mbox \widehat \widetilde                        (2.00w) *
* 02.01.1998  drawArc used for brackets, \widetilde good             (2.00x) *
* 03.01.1998  expect()-methods to check on expected tokens           (2.00y) *
* 04.01.1998  redesign of thread synchronization, getWidth|Height OK (2.00y1)*
*             some minor optimization in parser and documentation            *
* 04.01.1998  minor error with SpaceChar corrected                           *
*             \begin{eqnarray} implemented                           (2.00z) * 
* 08.01.1998  minor corrections for TeX-generated fonts              (2.00z1)*
* 09.01.1998  *{} for \begin{array} implemented                      (2.00z2)*  
* 13.01.1998  new media tracking, cached images, FGBGcolor corrected (2.00z4)* 
* 15.01.1998  Synchronisation with update changed because of overrun (2.00z5)* 
*             Default space for erroneous images                             *
*                                                                            *
* 17.01.1998  Separation into HotEqn and dHotEqn version.            (2.01)  *
*             HotEqn is only for Eqn. viewing and dHotEqn includes           *
*             all public methods. The mHotEqn is now based on dHotEqn.       *
*             Hourglass activity indicator added.                            *
* 18.01.1998  Image cache realized by hash table                     (2.01a) *
* 06.02.1998  New align parameter halign, valign. Correct alignment  (2.01b) *
* 27.02.1998  \sqrt[ ]{}                                             (2.01c) *
* 04.03.1998  Better spacing within brackets                         (2.01d) *
******************************************************************************
*     1998    S. Mueller, Chr. Schmid                                        *
* 19.01.1998  AWT component for use in other applications (like buttons,     *
*             scrollbars, labels, textareas,...)                     (2.01b) *
* 10.03.1998  adjustments                                            (2.01b1)*
* 11.03.1998  migration to JDK1.1.5                                  (2.01d1)*
* 14.03.1998  migration to the new event model and public methods    (2.01d2)*
* 20.03.1998  setPreferredSize() setMinimumSize()                    (2.01d3)*
* 04.04.1998  this.getSize()... in paint reinstalled.                (2.01d4)*
*             PropertyChange... ---> automatic resize of bean                *
* 11.04.1998  java-files renamed cHotEqn.java --> bHotEqn.java (Bean)(2.01d5)*
*             setBorder() setRoundRectBorder()                               *
* 12.04.1998  partial rearranging of variables and methods                   *
*             bHotEqn -> separated into cHotEqn & bHotEqn            (2.02)  *
* 26.04.1998  possible workarround for getImage()-problem            (2.02a) *
* 27.04.1998  Toolkit.getDefaultToolkit().getImage() is buggy for            *
*             Netscape 4.04 and 4.05 (JDK1.1x) (see getSymbol(...)           *
* 02.05.1998  image-loading problem solved                           (2.02b) *
*             output to System.out only if debug==true                       *
* 09.05.1998  selectable equations     (minor error correction 2.01f)(2.03)  *
* 30.03.1998  GreekFontDescents corrected (better for Communicator)  (2.01e) *
* 12.05.1998  see mHotEqn and EqScanner                              (2.01f) *
* 22.05.1998  modified border radius calculation                     (2.01g) *
* 10.04.1999  corrected alpha value in Color Mask Filter             (2.01h) *
* 21.05.1998  selection almost completed                             (2.03a) *                                
* 24.05.1998  setEditable(), isEditable(), getselectedArea()         (2.03b) *
*             fontsize-problem solved, starts with editable=true             *
**************   Release of Version 3.00 *************************************
*     2001    Chr. Schmid                                                    *
* 18.01.2001  modified according to old HotEqn, SymbolLoader added, three    *
*             parameter constructor for applet context with applet name,     *
*             events corrected, edit mode highlight with transparency        *
* 14.05.2001  getImage method added                                   (3.01) *
* 15.06.2001  getImage method returns null when Image not ready       (3.02) *
* 01.12.2001  edit mode on mouse down,drag,up and new string search   (3.03) *
* 18.02.2002  faster version with one scan in generateImage           (3.04) *
* 19.02.2002  Environment color parameter + methods                   (3.04) * 
* 20.02.2002  New SymbolLoader with packed gif files (fast and small) (3.10) * 
* 23.03.2002  New method getSizeof to determine size of equation      (3.11) * 
* 27.10.2002  Package atp introduced                                  (3.12) * 
**************   Release of Version 4.00 *************************************
* 28.10.2002  Swing version forked from cHotEqn 3.12                  (4.00) * 
*             Thanks to Markus Schlicht                                      * 
*****************************************************************************/

package org.mathpiper.ui.gui.hoteqn;

// **** localWidth u. localHeight only at getPreferredSize() to give back

// package bHotEqn;  // for Bean-compilation to avoid double filenames

import java.util.*;
//changed 13.10.2002 //import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*;
//changed 13.10.2002 //import java.applet.Applet; // If component is called by applet.
import java.net.URL;       // for image loading in beans
import java.io.*;
import java.util.StringTokenizer;

import javax.swing.*; //changed 13.10.2002
import java.awt.Font; //changed 13.10.2002
import java.awt.Color; //changed 13.10.2002
import java.awt.Image; //changed 13.10.2002
import java.awt.Graphics; //changed 13.10.2002
import java.awt.MediaTracker; //changed 13.10.2002
import java.awt.Dimension; //changed 13.10.2002
import java.awt.Toolkit; //changed 13.10.2002
import java.awt.AWTEventMulticaster; //changed 13.10.2002
import java.awt.AWTEvent; //changed 13.10.2002
import java.awt.Polygon; //changed 13.10.2002
import java.awt.FontMetrics; //changed 13.10.2002
import java.awt.Rectangle; //changed 13.10.2002

public class sHotEqn extends JComponent //changed 13.10.2002
                     implements MouseListener, MouseMotionListener {

private static final String  VERSION = "sHotEqn V 4.00 ";

private int       width          = 0;
private int       height         = 0;
private String    nameS          = null;
private String    equation       = null;
private String    Fontname       = "Helvetica";

ActionListener    actionListener;     // Post action events to listeners

private EqScanner eqScan;
private EqToken   eqTok;

private Font f1 = new Font(Fontname,Font.PLAIN, 16);   
private Font f2 = new Font(Fontname,Font.PLAIN, 14);
private Font f3 = new Font(Fontname,Font.PLAIN, 11);
private Font f4 = new Font(Fontname,Font.PLAIN, 10);

private static final float mk = 2.0f;     // Switchable factor for parentheses (font,draw)

private static final int GreekFontSizes[]    = { 8,10,12,14,18 }; // Default GreekFonts
private static final int GreekFontDescents[] = { 2, 3, 4, 5, 6 }; // Default GreekFonts Descents
private int GreekSize[]                      = {14,12,10, 8};
private int GreekDescent[]                   = { 3, 3, 3, 3};
private static final int EmbedFontSizes[]    = { 9,11,14,16,22 }; // Assigned normal Fonts

/* greek font embedding characteristic based on Helvetica

 nominal font size  18  14  12  10   8
   greek leading     1   0   0   0   0
   greek height     23  16  15  13  11
   greek ascent     18  14  12  10   8
   greek descent     6   5   4   3   2
   embed size       22  16  14  11   9
   embed leading     1   1   0   0   0
   embed height     26  19  16  14  12
   embed ascent     20  15  13  11   9
   embed descent     6   3   3   3   3
*/

private Image   bufferImage;                     // double buffer image
private boolean imageOK          = false;
private int     localWidth       = 0;
private int     localHeight      = 0;

private Color   BGColor          = Color.white;
private Color   EnvColor         = Color.white;
private Color   FGColor          = Color.black;
private Color   BorderColor      = Color.red;
private boolean borderB          = false; //If true, draws a border around the component.
private boolean roundRectBorderB = false; //Makes the border rounded.
private int     border           = 0;
private String  halign           = "left";
private String  valign           = "top";
private int     xpos             = 0;
private int     ypos             = 0;
private boolean drawn            = false;       // drawn Semaphore for paint

private sSymbolLoader symbolLoader;              // flexible fontloader
private MediaTracker tracker;                   // global image tracker
private Hashtable imageH = new Hashtable (13);  // Hashtable for Image Cache (prime)

private JApplet  app;  //changed 13.10.2002 // Applet-Handle: because Netscape 4.x Bug mit Toolkit...getImage()
public  boolean appletB          = false;      // true if for HotEqn - sHotEqn used
public  boolean beanB            = false;      // true when used as bean.
public  boolean debug            = false;       // debug-reporting.

private boolean editMode         = false;      // Editor mode: select parts of equation
private boolean editableB        = true;
private int     mouse1X          = 0;
private int     mouse1Y          = 0;
private int     mouse2X          = 0;
private int     mouse2Y          = 0;
private int     xOFF             = 0;
private int     yOFF             = 0;
private int     y0 = 0;
private int     x0 = 0;
private int     y1 = 0;
private int     x1 = 0;
private int     editModeRec    = 5;
private boolean editModeFind   = false;
private int     editModeCount1 = 0;
private int     editModeCount2 = 0;
private Image   selectImage;

//*************************  Constructor ()  ****************************************
public  sHotEqn() {
  this("sHotEqn", null, "sHotEqn");
}

public  sHotEqn(String equation) {
  this(equation, null, "sHotEqn");
}

public sHotEqn(String equation, JApplet app, String nameS) {//changed 13.10.2002
   this.app       = app;                // Handle for Applet for Applet.getImage()
   this.equation  = equation;
   this.nameS     = nameS;
   addMouseListener(this);
   addMouseMotionListener(this);
   if (app != null)  appletB=true; 
   symbolLoader   = new sSymbolLoader();      // Font loader.
   tracker        = new MediaTracker(this);  // Media tracker for Images
   eqScan         = new EqScanner(equation); // Scanner to detect the Token.
   //System.out.println(VERSION+nameS);
}

//*************************  Public Methods ***********************************

public void setEquation(String equation) {
    this.equation = equation;
    eqScan.setEquation(equation);
    drawn   = false;
    imageOK = false;
    repaint();
} 
public String getEquation() { return equation; }

public void printStatus( String s) {
   if (debug) System.out.println(nameS + " " + s);
}

private void displayStatus( String s) {
   if (debug) {if (appletB) app.showStatus(nameS + " " + s); else printStatus(s);}
}

public Image getImage() {
	if (imageOK) return bufferImage; else return null;
}

public void setDebug(boolean debug) {
   this.debug = debug;
}
public boolean isDebug() { return debug; }

public void   setFontname(String fontname) { Fontname = fontname;}
public String getFontname()                { return Fontname;}

public void setFontsizes(int gsize1, int gsize2, int gsize3, int gsize4) {
   int    size1  = 16;
   int    size2  = 14;
   int    size3  = 11;
   int    size4  =  9;

   GreekSize[0]=0;
   GreekSize[1]=0;
   GreekSize[2]=0;
   GreekSize[3]=0;

   // Fontlargen for all the characters and the Greek symbols and special characters.
   for (int i=0; i<GreekFontSizes.length; i++){
       if (gsize1 == GreekFontSizes[i]) {GreekSize[0]=gsize1;GreekDescent[0]=GreekFontDescents[i];size1=EmbedFontSizes[i];}
       if (gsize2 == GreekFontSizes[i]) {GreekSize[1]=gsize2;GreekDescent[1]=GreekFontDescents[i];size2=EmbedFontSizes[i];}
       if (gsize3 == GreekFontSizes[i]) {GreekSize[2]=gsize3;GreekDescent[2]=GreekFontDescents[i];size3=EmbedFontSizes[i];}
       if (gsize4 == GreekFontSizes[i]) {GreekSize[3]=gsize4;GreekDescent[3]=GreekFontDescents[i];size4=EmbedFontSizes[i];} 
   }

   // If no matching font size found, most alternative Fonts chosen.
   if (GreekSize[0]==0) {GreekSize[0]=GreekFontSizes[GreekFontSizes.length-1];GreekDescent[0]=GreekFontDescents[GreekFontDescents.length-1];size1=EmbedFontSizes[EmbedFontSizes.length-1];}
   if (GreekSize[1]==0) {GreekSize[1]=GreekSize[0];GreekDescent[1]=GreekDescent[0];size2=size1;}
   if (GreekSize[2]==0) {GreekSize[2]=GreekSize[1];GreekDescent[2]=GreekDescent[1];size3=size2;} 
   if (GreekSize[3]==0) {GreekSize[3]=GreekSize[2];GreekDescent[3]=GreekDescent[2];size4=size3;}

   // Fonts for the representation.
   f1  = new Font(Fontname,Font.PLAIN,size1);   
   f2  = new Font(Fontname,Font.PLAIN,size2);
   f3  = new Font(Fontname,Font.PLAIN,size3);
   f4  = new Font(Fontname,Font.PLAIN,size4);

//System.out.println("gsize= "+gsize1+" "+gsize2+" "+gsize3+" "+gsize4);
//System.out.println("size= "+size1+" "+size2+" "+size3+" "+size4);
}
 
public void setBackground(Color BGColor) {
   this.BGColor = this.EnvColor = BGColor;
   drawn   = false;
   imageOK = false;
   repaint();
}
public Color getBackground() { return BGColor; }

public void setForeground(Color FGColor) {
   this.FGColor = FGColor;
   drawn   = false;
   imageOK = false;
   repaint();
}
public Color getForeground() { return FGColor; }

public void setBorderColor(Color BorderColor) {
   this.BorderColor = BorderColor;
    drawn           = false;
    imageOK         = false;
    repaint();
}
public Color getBorderColor() { return BorderColor; }

public void setBorder(boolean borderB) {
    this.borderB = borderB;
    drawn        = false;
    imageOK      = false;
    repaint();
}
public boolean isBorder() { return borderB; }

public void setRoundRectBorder(boolean roundRectBorderB) {
    this.roundRectBorderB = roundRectBorderB;
    drawn                 = false;
    imageOK               = false;
    repaint();
}
public boolean isRoundRectBorder() { return roundRectBorderB; }

public void   setHAlign(String halign) {  
   this.halign = halign;
   drawn       = false;
   imageOK     = false;
}
public void setEnvColor(Color EnvColor) {
   this.EnvColor = EnvColor;
    drawn           = false;
    imageOK         = false;
    repaint();
}
public Color getEnvColor() { return EnvColor; }

public String getHAlign() { return halign; }

public void setVAlign(String valign) {   
   this.valign = valign;
   drawn       = false;
   imageOK     = false;
}
public String getVAlign() { return valign; }

public void    setEditable(boolean editableB) { this.editableB = editableB; }
public boolean isEditable()                   { return editableB;           }

public String getSelectedArea() {
   return  eqScan.getSelectedArea(editModeCount1,editModeCount2);
}

//*************************  Event handler  ************************************
public void mousePressed(MouseEvent ev)  {}
public void mouseReleased(MouseEvent ev) {}
public void mouseEntered(MouseEvent ev)  {}
public void mouseExited(MouseEvent ev)   {}
public void mouseClicked(MouseEvent ev)  {}
public void mouseMoved(MouseEvent ev)    {}
public void mouseDragged(MouseEvent ev)  {}

public void processMouseEvent(MouseEvent ev) {

  // Print width/height information when the control key is pressed during a mouse click.
  if (ev.isControlDown()) { 
     if (ev.getID() == MouseEvent.MOUSE_PRESSED && !ev.isMetaDown()) {
        System.out.println(nameS+" (width,height) given=("+this.getSize().width+","+this.getSize().height
                                 +") used=("+this.getPreferredSize().width+","+this.getPreferredSize().height+")");
     }
  }
  else {
		if (editableB) {			
			if (ev.getID() == MouseEvent.MOUSE_PRESSED) {  
				mouse1X = ev.getX();
				mouse1Y = ev.getY();
				mouse2X = 0;
				mouse2Y = 0;
				editModeRec = 5;
				selectImage = null;
				repaint();  // Mark for repainting.
			} 
			else if (ev.getID() == MouseEvent.MOUSE_RELEASED) { 
				if (editMode) {
					Graphics g = this.getGraphics();
					g.setFont(f1);
					g.setColor(FGColor);
					//g.drawLine(mouse1X,mouse1Y,mouse2X,mouse2Y);
					eqScan.start();
					BoxC area = eqn(xOFF, yOFF, true, g, 1); 
					if (debug) printStatus("selectedArea = "+eqScan.getSelectedArea(editModeCount1,editModeCount2));
					ImageProducer filteredProd 
						= new FilteredImageSource(bufferImage.getSource(),
							new CropImageFilter(x0 ,y0 ,x1-x0 ,y1-y0 ));
					ImageProducer filteredProd2 
						= new FilteredImageSource(filteredProd, 
							new ColorMaskFilter(Color.red,true));
					selectImage = Toolkit.getDefaultToolkit().createImage(filteredProd2);
					g.drawImage(selectImage,x0,y0,this);
					//System.out.println("counts "+editModeCount1+" "+editModeCount2);
					editMode = false;
				} 
			}
		}
  }
  super.processMouseEvent(ev);  
} 

public void processMouseMotionEvent(MouseEvent ev) {
  if ((ev.getID() == MouseEvent.MOUSE_DRAGGED) && 
      (mouse1X  != 0) &&  editableB) { 
		editMode = true;
		mouse2X  = ev.getX();
		mouse2Y  = ev.getY();
  }
} // end processMouseMotionEvent 

    @Override
public Dimension getPreferredSize() {
  if (width==0 & height==0) {
    Graphics g = this.getGraphics();
    if (g!=null) {  
          //System.out.println("getGraphics is not null");
          g.setFont(f1);
     	  eqScan.start();
          BoxC area = eqn(0,150, false, g, 1);
          if (borderB == true) border=5;
          else border = 0;
          localWidth  = 1+area.dx+2*border;
          localHeight = 1+area.dy_pos+area.dy_neg+2*border;
          //System.out.println("getPref0... "+localWidth+" "+localHeight);
    }
  }
  width  = localWidth;
  height = localHeight;

  if (localWidth<=1) return new Dimension(100,100); //safety.

  return new Dimension(localWidth,localHeight);
}

public Dimension getSizeof(String equation) {
	int borderVal;
	Image genImage=createImage(200,200);
	Graphics g = genImage.getGraphics();
	g.setFont(f1);
	eqScan.setEquation(equation);
	BoxC area = eqn(0,150, false, g, 1);
	g.dispose();
	if (borderB) borderVal=5;	else borderVal = 0;
  return new Dimension(1+area.dx+2*borderVal,1+area.dy_pos+area.dy_neg+2*borderVal);
}

public Dimension getMinimumSize() { return getPreferredSize();}

public Dimension getMaximumSize() { return getPreferredSize();}

public void addActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.add(actionListener, listener);
       enableEvents(AWTEvent.MOUSE_EVENT_MASK);}
 
public void removeActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.remove(actionListener, listener);}

//changed  13.10.2002
/*
public synchronized void update (Graphics g) {
// ******!!!! is this method at all necessary ?????*******
  if (drawn) return;
     imageOK = false;
     paint(g);  
}
*/

private void draw_hourglass(Graphics g) {
  g.setColor(Color.red);
  int hm=height/2-10; int hp=hm+20;
  int wm=width/2-5;   int wp=wm+10;
  Polygon polygon = new Polygon();
  polygon.addPoint(wm, hm);
  polygon.addPoint(wp, hp);
  polygon.addPoint(wm, hp);
  polygon.addPoint(wp, hm);
  g.fillPolygon(polygon); 
}

//added 13.10.2002
public synchronized void paintComponent (Graphics g) {
   super.paintComponent(g);
   if (width!=this.getSize().width || height!=this.getSize().height) {
      imageOK     = false;
      bufferImage = null;
      width  = this.getSize().width;
      height = this.getSize().height;
   }

   if (!imageOK) {
      draw_hourglass(g);

      selectImage = null;

      if (bufferImage == null) bufferImage=createImage(width,height);
      Graphics bufferg = bufferImage.getGraphics();
      generateImage (bufferg);
      bufferg.dispose();
   }
   g.drawImage(bufferImage,0,0,this);
   if (selectImage!=null)    g.drawImage(selectImage,x0,y0,this);	
}

/*
//changed  13.10.2002
public synchronized void paint (Graphics g) {

}  // paint
*/

//  fast version with one scan and double buffer
private synchronized void generateImage (Graphics g) {
     BoxC area0 = new BoxC();
     Image genImage=createImage(width,height+height);
     Graphics geng = genImage.getGraphics();
     geng.setFont(f1);
     g.setColor(BGColor);
     g.fillRect(0,0,width,height);
     geng.setColor(BGColor);
     geng.fillRect(0,0,width,height+height);
     border=0;
     if (borderB && roundRectBorderB) {
       g.setColor(EnvColor);
       g.fillRect(0,0,width,height);
       g.setColor(BGColor);
       g.fillRoundRect(0,0,width-1,height-1,20,20);
       g.setColor(BorderColor);
       g.drawRoundRect(0,0,width-1,height-1,20,20);
       border=5;
     } else {
       if (borderB && !roundRectBorderB) {
         g.setColor(BorderColor);
         g.drawRect(0,0,width-1,height-1);
         border=5;
       }
     }
     geng.setColor(FGColor);

     //FontMetrics fM  = g.getFontMetrics();
     //System.out.println("getAscent     = "+fM.getAscent()      );
     //System.out.println("getDescent    = "+fM.getDescent()     );
     //System.out.println("getHeight     = "+fM.getHeight()      );
     //System.out.println("getLeading    = "+fM.getLeading()     );
     //System.out.println("getMaxAdvance = "+fM.getMaxAdvance()  );
     //System.out.println("getMaxAscent  = "+fM.getMaxAscent()   );
     //System.out.println("getMaxDecent  = "+fM.getMaxDecent()   );
     //System.out.println("getMaxDescent = "+fM.getMaxDescent()  );
   
     // Scanner to back out & equation in d. middle d. of the window.

     //imageH.clear();  // Image Cache blank (not required)
     //System.out.println("before 1. eqn");
     eqScan.start();
     area0 = eqn(0,height, true, geng, 1);
     displayStatus(" ");


     // set alignment
     xpos=0; // left
     if (halign.equals("center"))     xpos=1;
     else if (halign.equals("right")) xpos=2;
           
     ypos=0; // top      
     if (valign.equals("middle"))      ypos=1;
     else if (valign.equals("bottom")) ypos=2;
                  
     // Calculate actual size
     localWidth  = 1+area0.dx+2*border;
     localHeight = 1+area0.dy_pos+area0.dy_neg+2*border;

     // Test size and modify alignment if too small
     boolean toosmall = false; 
     if (localWidth > width)   {toosmall=true; xpos=0;}
     if (localHeight > height) {toosmall=true; ypos=1;}
     // Calculate position
     int xoff=border;
     int yoff=border; 
     switch (xpos) {
       case 0: break;
       case 1: xoff=(width-area0.dx)/2; break;
       case 2: xoff=width-border-area0.dx-1; break;
     }
     switch (ypos) {
       case 0: break;
       case 1: yoff=border-(localHeight-height)/2; break;
       case 2: yoff=height-border-area0.dy_neg-area0.dy_pos; break;
     }
     //System.out.println("after 1. eqn");
     g.drawImage(genImage,xoff,yoff,xoff+area0.dx,yoff+area0.dy_pos+area0.dy_neg+1,0,height-area0.dy_pos,area0.dx,height+area0.dy_neg+1 ,this);
     //System.out.println("after 2. eqn");
     geng.dispose();
     if (toosmall) printStatus("(width,height) given=("+width+","+height +") used=("+localWidth+","+localHeight+")");
     imageOK = true;
     drawn   = true;
     xOFF=xoff;
     yOFF=yoff+area0.dy_pos;
     notify(); // notifiy that painting has been completed
} // end generateImage

/*  slower version with two scans
private synchronized void generateImage (Graphics g) {
     BoxC area  = new BoxC();
     BoxC area0 = new BoxC();
     g.setFont(f1);
     g.setColor(BGColor);
     g.fillRect(0,0,width,height);
     border=0;
     if (borderB && roundRectBorderB) {
       g.setColor(EnvColor);
       g.fillRect(0,0,width,height);
       g.setColor(BGColor);
       g.fillRoundRect(0,0,width-1,height-1,20,20);
       g.setColor(BorderColor);
       g.drawRoundRect(0,0,width-1,height-1,20,20);
       border=5;
     } else {
       if (borderB && !roundRectBorderB) {
         g.setColor(BorderColor);
         g.drawRect(0,0,width-1,height-1);
         border=5;
       }
     }
     g.setColor(FGColor);

     //FontMetrics fM  = g.getFontMetrics();
     //System.out.println("getAscent     = "+fM.getAscent()      );
     //System.out.println("getDescent    = "+fM.getDescent()     );
     //System.out.println("getHeight     = "+fM.getHeight()      );
     //System.out.println("getLeading    = "+fM.getLeading()     );
     //System.out.println("getMaxAdvance = "+fM.getMaxAdvance()  );
     //System.out.println("getMaxAscent  = "+fM.getMaxAscent()   );
     //System.out.println("getMaxDecent  = "+fM.getMaxDecent()   );
     //System.out.println("getMaxDescent = "+fM.getMaxDescent()  );
   
     // Scanner reset & equation in d. Mitte d. Fensters

     //imageH.clear();  // Image Cache leeren (nicht erforderlich)
     //System.out.println("before 1. eqn");
     eqScan.start();
     area0 = eqn(0,150, false, g, 1);
     displayStatus(" ");
     
     // set alignment
     xpos=0; // left
     if (halign.equals("center"))     xpos=1;
     else if (halign.equals("right")) xpos=2;
           
     ypos=0; // top      
     if (valign.equals("middle"))      ypos=1;
     else if (valign.equals("bottom")) ypos=2;
                  
     // Calculate actual size
     localWidth  = 1+area0.dx+2*border;
     localHeight = 1+area0.dy_pos+area0.dy_neg+2*border;

     // Test size and modify alignment if too small
     boolean toosmall = false; 
     if (localWidth > width)   {toosmall=true; xpos=0;}
     if (localHeight > height) {toosmall=true; ypos=1;}
     // Calculate position
     int xoff=border;
     int yoff=area0.dy_pos+border; 
     switch (xpos) {
       case 0: break;
       case 1: xoff=(width-area0.dx)/2; break;
       case 2: xoff=width-border-area0.dx-1; break;
     }
     switch (ypos) {
       case 0: break;
       case 1: yoff=border+area0.dy_pos-(localHeight-height)/2; break;
       case 2: yoff=height-border-area0.dy_neg-1; break;
     }
     //System.out.println("after 1. eqn");
     eqScan.start();
     area = eqn(xoff,yoff,true,g,1);
     //System.out.println("after 2. eqn");
     if (toosmall) printStatus("(width,height) given=("+width+","+height
                                   +") used=("+localWidth+","+localHeight+")");
     imageOK = true;
     drawn   = true;
     xOFF=xoff;
     yOFF=yoff;
     notify(); // notifiy that painting has been completed
} // end generateImage
*/


//***************************************************************************
//***************************************************************************
//***************             Parser-routine              ******************
private BoxC eqn(int x, int y, boolean disp, Graphics g, int rec){
   // different number of parameters
   return eqn(x, y, disp, g, rec, true); // Standard Argument (e.g. A_{.....})
} // end eqn


private BoxC eqn(int x, int y, boolean disp, Graphics g, int rec, boolean Standard_Single){
// Parameter: Baseline coordinates:         x and y
//            or drawing large calculate: disp (true/false)
//            Recursion (break, high,low,...)
//            Single (e.g. A_3)(false) o. Standard argument (e.g. A_{3+x})(true)

// the method: boxReturn = adjustBox(box,boxReturn) replaces the separate
//              calculation of the new box size after a function call
   BoxC        box       = new BoxC();  // for R�ckgaben function calls
   BoxC        boxReturn = new BoxC();  // accumulates the max. box size

   boolean Standard_Single_flag = true;
   boolean Space_flag           = false;
   boolean editModeFindLEFT = false;
   int editModeCount = 0;
   int editModeCountLEFT = 0;
   int eqToktyp;
   //String eqTokstringS;

   while (!eqScan.EoT() && Standard_Single_flag) {
     eqTok = eqScan.nextToken();
     if (editMode && disp) editModeCount = eqScan.get_count();

     Space_flag = false; 
     //System.out.print (eqTok.typ);
	 //if ( disp) System.out.println("Token ="+eqTok.typ);
	 editModeCountLEFT = editModeCount;
     eqToktyp = eqTok.typ;
     //eqTokstringS = eqTok.stringS;

     switch(eqTok.typ) {
     case EqToken.AndSym:
     case EqToken.DBackSlash:
     case EqToken.END:
     case EqToken.EndSym:
     case EqToken.RIGHT:
               if (editModeFind && disp) { 
				  //System.out.println("RighteditModeCount ="+editModeCount);
                  if (editModeCount > editModeCount2) editModeCount2 = editModeCount;
                  if (editModeCount < editModeCount1) editModeCount1 = editModeCount;
               }
               return boxReturn;
     case EqToken.ACCENT:
               box = ACCENT(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.ANGLE:
               box = ANGLE(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.ARRAY:
			   if (editModeFind && disp) editModeFindLEFT = true;
               box = ARRAY(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.BEGIN:
			   if (editModeFind && disp) editModeFindLEFT = true;
               box = BEGIN(x+boxReturn.dx,y,disp,g,rec); 
               break;
     case EqToken.BeginSym:
               box = eqn(x+boxReturn.dx,y,disp,g,rec,true); 
               break;
     case EqToken.FGColor:
               box = FG_BGColor(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.BGColor:
               box = FG_BGColor(x+boxReturn.dx,y,disp,g,rec,false);
               break;
     case EqToken.FBOX:
			   if (editModeFind && disp) editModeFindLEFT = true;
               box = FBOX(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.Id:
               box = Id(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.NOT:
               box = NOT(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.Op: 
               box = Op(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.FRAC:
               box = FRAC(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.ATOP:
               box = FRAC(x+boxReturn.dx,y,disp,g,rec,false);
               break;
     case EqToken.FUNC:
     case EqToken.Num:
               box = Plain(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.SYMBOP:
               box = SYMBOP(x+boxReturn.dx,y,disp,g,rec,false);
               break;
     case EqToken.SYMBOPD:
               box = SYMBOP(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.LEFT:
			   if (editModeFind && disp) editModeFindLEFT = true;
               box = LEFT(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.LIM:
               box = LIM(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.MBOX:
               box = MBOX(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.OverBRACE:
               box = OverBRACE(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.UnderBRACE:
               box = UnderBRACE(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.OverLINE:
               box = OverUnderLINE(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.UnderLINE:
               box = OverUnderLINE(x+boxReturn.dx,y,disp,g,rec,false);
               break;
     case EqToken.Paren:
               box = Paren(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.SPACE:
               box = SPACE(x+boxReturn.dx,y,disp,g);
               break;
     case EqToken.SQRT:
			   if (editModeFind && disp) editModeFindLEFT = true;
               box = SQRT(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.STACKREL:
               box = STACKREL(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.SUP:
               box = SUP(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.SUB:
               box = SUB(x+boxReturn.dx,y,disp,g,rec,true);
               break;
     case EqToken.SYMBOLBIG:
               box = SYMBOLBIG(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.VEC:
               box = VEC(x+boxReturn.dx,y,disp,g,rec);
               break;
     case EqToken.SpaceChar:
               box = new BoxC(0,0,0);
               // bei SpaceChar gilt immer noch eqn(...,false) (single eqn)
               Space_flag = true;   
               break;
     case EqToken.Invalid:
     case EqToken.Null:
               box = new BoxC(0,0,0);
               break;
     default:
               printStatus("Parser: unknown token: "+eqTok.typ+" "+eqTok.stringS);
               //ignore
     } // end switch

	if (disp)  {
		if (editMode)  {
			//System.out.println("x+boxReturn.dx = "+(x+boxReturn.dx)+" mouse1X = "+mouse1X+" x+boxReturn.dx+box.dx ="+(x+boxReturn.dx+box.dx));
			if (!editModeFind) {
				if ( x+boxReturn.dx    <= mouse1X                    &&  
					 mouse1X           <= (x+boxReturn.dx+box.dx)    && 
					 (y-box.dy_pos)    <= mouse1Y                    && 
					 mouse1Y           <= (y+box.dy_neg) ) {
					//System.out.println("Top token "+eqToktyp+" "+eqTokstringS+" "+rec+" "+editModeRec);
					x0 = x1 = mouse1X;
					y0 = y1 = mouse1Y;
					editModeFind   = true;
					editModeCount1 = editModeCount;
					editModeCount2 = editModeCount;
				}
			}
			if (!editModeFind) {
				if ( x+boxReturn.dx    <= mouse2X                    &&  
					 mouse2X           <= (x+boxReturn.dx+box.dx)    && 
					 (y-box.dy_pos)    <= mouse2Y                    && 
					 mouse2Y           <= (y+box.dy_neg) ) {
					//System.out.println("Top2token "+eqToktyp+" "+eqTokstringS+" "+rec+" "+editModeRec);
					x0 = x1 = mouse2X;
					y0 = y1 = mouse2Y;
					editModeFind = true;
					editModeCount1 = editModeCount;
					editModeCount2 = editModeCount;
					int dummyX = mouse2X;
					int dummyY = mouse2Y;
					mouse2X    = mouse1X;
					mouse2Y    = mouse1Y;
					mouse1X    = dummyX;
					mouse1Y    = dummyY;
				}
			}
			//System.out.println("Token ="+eqToktyp+" editModeFind ="+editModeFind+" editModeFindLEFT ="+editModeFindLEFT);
			if (editModeFind) {
				//System.out.println("Mitte token  "+eqToktyp+" "+eqTokstringS+" "+rec+" "+editModeRec+" "+editModeCount1+" "+editModeCount2);
				x0 = Math.min(x0, x + boxReturn.dx);
				x1 = Math.max(x1, x + boxReturn.dx + box.dx);
				y0 = Math.min(y0, y - box.dy_pos);
				y1 = Math.max(y1, y + box.dy_neg);
				//g.setColor(Color.green);
				//g.drawRect(x0, y0, x1-x0, y1-y0);
				//g.setColor(FGColor);
				if (editModeRec>rec) editModeRec = rec;
     			switch(eqToktyp) {
     				case EqToken.LEFT :
     				case EqToken.FBOX :
     				case EqToken.MBOX :
     				case EqToken.BEGIN :
     				case EqToken.ARRAY :
     				case EqToken.SQRT :
						editModeFindLEFT = true;
						if (editModeCountLEFT > editModeCount2) editModeCount2 = editModeCountLEFT;
						if (editModeCountLEFT < editModeCount1) editModeCount1 = editModeCountLEFT;
						editModeCount = eqScan.get_count();
						//System.out.println("MBOX/FBOX/LEFT handling");
				} // end switch
				if (editModeCount > editModeCount2) editModeCount2 = editModeCount;
				if (editModeCount < editModeCount1) editModeCount1 = editModeCount;
				//System.out.println("editModeCount1 "+editModeCount1);
				//System.out.println("editModeCount2 "+editModeCount2);
				if ( x+boxReturn.dx    <= mouse2X                    &&  
				     mouse2X           <= (x+boxReturn.dx+box.dx)    && 
				     (y-box.dy_pos)    <= mouse2Y                    && 
				     mouse2Y           <= (y+box.dy_neg)  ) {
					//System.out.println("Ende token   "+eqToktyp+" "+eqTokstringS+" "+rec+" "+editModeRec);
					//g.setColor(Color.red);
					//g.drawRect(x0, y0, x1-x0, y1-y0);
					//g.setColor(FGColor);
					if (editModeRec == rec) {
						editMode     = false;
						editModeFind = false;
						//System.out.println("editModeCount "+editModeCount);
					}
				}
			} // end editModeFind
		} // end editMode
		if (editModeFindLEFT) {
			//System.out.println("find LEFT token  "+eqToktyp+" "+eqTokstringS+" "+rec+" "+editModeRec+" "+editModeCount1+" "+editModeCount2);
			x0 = Math.min(x0, x + boxReturn.dx);
			x1 = Math.max(x1, x + boxReturn.dx + box.dx);
			y0 = Math.min(y0, y - box.dy_pos);
			y1 = Math.max(y1, y + box.dy_neg);
			//g.setColor(Color.green);
			//g.drawRect(x0, y0, x1-x0, y1-y0);
			//g.setColor(FGColor);
   			switch(eqToktyp) {
   				case EqToken.LEFT :
   				case EqToken.FBOX :
   				case EqToken.MBOX :
   				case EqToken.BEGIN :
   				case EqToken.ARRAY :
   				case EqToken.SQRT :
				if (editModeCountLEFT > editModeCount2) editModeCount2 = editModeCountLEFT;
				if (editModeCountLEFT < editModeCount1) editModeCount1 = editModeCountLEFT;
				editModeCount = eqScan.get_count();
				//System.out.println("MBOX/FBOX/LEFT handling");
			} // end switch
			if (editModeCount > editModeCount2) editModeCount2 = editModeCount;
			if (editModeCount < editModeCount1) editModeCount1 = editModeCount;
			//System.out.println("editModeCount1 "+editModeCount1);
			//System.out.println("editModeCount2 "+editModeCount2);
			editModeFindLEFT = false;
		} // end editModeFindLEFT
	} // end disp

	boxReturn.dx    += box.dx; 
	boxReturn.dy_pos = Math.max(boxReturn.dy_pos,box.dy_pos);
	boxReturn.dy_neg = Math.max(boxReturn.dy_neg,box.dy_neg); 
	if (!Standard_Single && !Space_flag) Standard_Single_flag = false;   // Single argument (e.g. A_3)
	} // end while
    return boxReturn;
} // end eqn


//************************************************************************
private BoxC ACCENT(int x, int y, boolean disp, Graphics g, int rec) {
// accents: \dot \ddot \hat \grave \acute \tilde
// eqTok.stringS contain the be displayed(n) character
   BoxC        box      = new BoxC();
   int         count    = 0;
   FontMetrics fM       = g.getFontMetrics();
   String      large  = eqTok.stringS;
   

   //Only disp=true must Scanner later reset will
   if (disp) count = eqScan.get_count(); 


   // large Argument-Box calculate
   box    = eqn(x,y,false,g,rec,false);
   int dx = Math.max(box.dx,fM.stringWidth(large));
   int dy_pos = box.dy_pos + (int)(fM.getAscent()/2); 
   int dy_neg = box.dy_neg; 

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 

      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
 
      // Argument draw
      box = eqn(x,y,true,g,rec,false);

      // Mittenverschiebung ausrechenen
      int d_dx = 3*(int)( (dx-fM.stringWidth(large))/4 );

      if (large.equals(".") | large.equals("..")) {
         g.drawString(large,x+d_dx,y-fM.getAscent());
         }
      else if (large.equals("�") | large.equals("`")) {
         g.drawString(large,x+d_dx,y-(int)(fM.getAscent()/3));
         }
      else g.drawString(large,x+d_dx,y-(int)(fM.getAscent()*2/3));
   } // end disp
   return new BoxC(dx,dy_pos,dy_neg);  
} // end ACCENT

//************************************************************************
private BoxC ANGLE(int x, int y, boolean disp, Graphics g) {
   // Spitze Klammern < und >

   BoxC        box      = new BoxC();
   FontMetrics fM       = g.getFontMetrics();
   int dx     = g.getFont().getSize()/2;
   int dy_pos = fM.getHeight()-fM.getDescent();
   int dy_neg = fM.getDescent();

   // only disp draw
   if (disp) {
      int yp     = y-dy_pos+1;
      int yn     = y+dy_neg-1;
      int m      = (yp+yn)/2;
      if (eqTok.stringS.equals("<")) {
         g.drawLine(x+dx,yp,x,m);
         g.drawLine(x,m,x+dx,yn);
      } else {
         g.drawLine(x,yp,x+dx,m);
         g.drawLine(x+dx,m,x,yn);
      }
    } // end disp
   return new BoxC(dx,dy_pos,dy_neg);  
} // end ACCENT

//************************************************************************
private BoxC ARRAY(int x, int y, boolean disp, Graphics g, int rec) {
   int         dx        = 0;
   int         dy_pos    = 0;
   int         dy_neg    = 0;
   int         dy_pos_max= 0;
   int         dx_eqn[]      = new int[100];  // Breite columnselemente
   int         dy_pos_eqn[]  = new int[100];  // H�he   rowselemente
   int         dy_neg_eqn[]  = new int[100];  // H�he   rowselemente
   BoxC        box       = new BoxC();
   int         count     = 0;
   FontMetrics fM        = g.getFontMetrics();
   // Abstand 1 quad hinter Element
   int quad              = g.getFont().getSize();

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count(); 

   // "{" vom Scanner holen
   if (!expect(EqToken.BeginSym, "ARRAY: BeginSym")) return new BoxC(0,0,0);  

   // loop: rows
   for (int y_i = 0; y_i<99; y_i++) {
      dy_pos = 0;
      dy_neg = 0;

      // loop: columns
      for (int x_i=0; x_i<99; x_i++) {
         // large der Argument-Box calculate
         box  = eqn(x,y,false,g,rec);

         dy_pos = Math.max(dy_pos,box.dy_pos); 
         dy_neg = Math.max(dy_neg,box.dy_neg); 

         // Breitesten Elemente pro column
         dx_eqn[x_i] = Math.max(dx_eqn[x_i],box.dx+quad);

         // delimiter am columnsende
         if ((eqTok.typ==EqToken.DBackSlash) || 
             (eqTok.typ==EqToken.EndSym)) break;
      } // end columns

      // H�chste und tiefste rowsh�he
      dy_pos_eqn[y_i] = Math.max(dy_pos_eqn[y_i],dy_pos);
      dy_neg_eqn[y_i] = Math.max(dy_neg_eqn[y_i],dy_neg);
      dy_pos_max += (dy_pos + dy_neg); 

      // delimiter am ARRAY-Ende
      if (eqTok.typ == EqToken.EndSym) break;
   } // end rows


   // maximum rows wide determine
   int dx_max = 0;
   for (int i=0; i<99; i++) dx_max += dx_eqn[i];

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 

      //g.drawRect(x,y-dy_pos_max/2-fM.getDescent(),dx_max,dy_pos_max);
 
      // "{" vom Scanner holen
      expect(EqToken.BeginSym, "ARRAY: Begin");  

     // loop: rows
     dy_pos = 0;
     for (int y_i=0; y_i<99; y_i++) {
        dx     = 0;
        if (y_i==0) { dy_pos  = dy_pos_eqn[y_i]; }
           else     { dy_pos += (dy_neg_eqn[y_i-1] + dy_pos_eqn[y_i]); }
        // loop: columns
        for (int x_i=0; x_i<99; x_i++) {
           // large der Argument-Box calculate
           box = eqn(x+dx,y-dy_pos_max/2-fM.getDescent()+dy_pos,true,g,rec);
           dx     += dx_eqn[x_i];

           // delimiter am columnsende
           if ((eqTok.typ == EqToken.DBackSlash) ||
               (eqTok.typ == EqToken.EndSym)) break;
        } // end columns
        // delimiter am ARRAY-Ende
        if (eqTok.typ == EqToken.EndSym) break;
     } // end rows
   } // end disp

   return new BoxC(dx_max-quad,dy_pos_max/2+fM.getDescent(),dy_pos_max/2-fM.getDescent());  
} // end ARRAY

//************************************************************************
private BoxC BEGIN(int x, int y, boolean disp, Graphics g, int rec) {
   int         dx,     dx_max                 = 0;
   int         dy_pos, dy_neg, dy_top, dy_max = 0;
   int         dx_eqn[]      = new int[100];  // Breite columns elemente
   int         dy_pos_eqn[]  = new int[100];  // H�he   rows element
   int         dy_neg_eqn[]  = new int[100];  // H�he   rows elemente
   int         format[]      = new int[100];  // Format 1-l 2-c 3-r 4-@
   int         format_count[]= new int[100];  // f�r getcount() bei @-Einsch�ben
   int         format_dx     = 0;             // dx     bei @-Einsch�ben
   int         format_dy_pos = 0;             // dy_pos bei @-Einsch�ben
   int         format_dy_neg = 0;             // dy_neg bei @-Einsch�ben
   BoxC        box           = new BoxC();
   int         count         = 0;
   FontMetrics fM            = g.getFontMetrics();
   int         quad          = g.getFont().getSize();
   int         i             = 0; 
   boolean     flag          = false;
   boolean     flag_end      = false;
   boolean     format_flag   = true;
   boolean     array_eqnarray= true;          // default: \begin{array}
   int         times         = 0; // Zahl bei *{xxx}
   int count2 =0;

   if (!expect(EqToken.BeginSym))  return new BoxC(0,0,0);   

   if (eqScan.nextToken().stringS.equals("eqnarray")) array_eqnarray = false;

   if (!expect(EqToken.EndSym, "BEGIN: EndSym")) return new BoxC(0,0,0);  

   if (array_eqnarray) {
     count = eqScan.get_count();
     if (!expect(EqToken.BeginSym)) {
        // NO format-string
        format_flag = false;
        eqScan.set_count(count);
     }
   }


   if (array_eqnarray && format_flag) {
      // *********** Format Angaben erkennen ********* 
      // l left(1)    c center(2)   r right(3)
      // @{...} Einschub statt Zwischenraum(4) 

      EqToken token = new EqToken();
      token = eqScan.nextToken();

      while (token.typ != EqToken.EndSym) {
         StringBuffer SBuffer = new StringBuffer(token.stringS); 
         for (int z=0; z<SBuffer.length(); z++){
           // System.out.println("z= "+z+"  String="+SBuffer.charAt(z));
           switch (SBuffer.charAt(z)) {
            case 'l':
               format[i] = 1;  
               if (i<99) i++; 
               break;
            case 'c':
               format[i] = 2;  
               if (i<99) i++;
               break;
            case 'r':
               format[i] = 3;  
               if (i<99) i++;
               break;
            case '@': 
               format[i] = 4; 
               format_count[i]  = eqScan.get_count();         
               box              = eqn(x,y,false,g,rec,false); // large calculate
               format_dx       += box.dx;
               format_dy_pos = Math.max(format_dy_pos,box.dy_pos);
               format_dy_neg = Math.max(format_dy_neg,box.dy_neg);
               if (i<99) i++;
               break;
            case '*': 
               expect(EqToken.BeginSym, "Begin *{"); 
               try { times = Integer.parseInt(eqScan.nextToken().stringS); }
               catch (NumberFormatException e){ times = 0; }
               expect(EqToken.EndSym, EqToken.BeginSym, "Begin }{"); 

               int count1 = eqScan.get_count();
               for (int ii=0 ; ii<times ; ii++) {
                   eqScan.set_count(count1);
                   token  = eqScan.nextToken();

                   while (token.typ != EqToken.EndSym) {
                      StringBuffer SBuffer2 = new StringBuffer(token.stringS); 
                      for (int zzz=0; zzz<SBuffer2.length(); zzz++){
                         //System.out.println("zzz= "+zzz+"  String="+SBuffer2.charAt(zzz));
                         switch (SBuffer2.charAt(zzz)) {
                         case 'l':
                            format[i] = 1;  
                            if (i<99) i++; 
                            break;
                         case 'c':
                            format[i] = 2;  
                            if (i<99) i++; 
                            break;
                         case 'r':
                            format[i] = 3;  
                            if (i<99) i++; 
                            break;
                         case '@': 
                            format[i] = 4; 
                            format_count[i]  = eqScan.get_count();         
                            box              = eqn(x,y,false,g,rec,false); // large equation
                            format_dx       += box.dx;
                            format_dy_pos = Math.max(format_dy_pos,box.dy_pos);
                            format_dy_neg = Math.max(format_dy_neg,box.dy_neg);
                            if (i<99) i++; 
                            break;
                         default:
                            printStatus("P: begin: illegal format 2");
                         } // end switch
                      } // end for
                   token     = eqScan.nextToken();
                   } // end while 
              } // end for ii times 
              break; // end case '*'
           default:
              printStatus("P: begin: illegal format 1");
           } // end switch
         } // end for
      token     = eqScan.nextToken();
      } // end while
   } // end array_eqnarray

   if (!array_eqnarray) {
      format[0] = 3;
      format[1] = 2;
      format[2] = 1;
      i = 3;
   } 

   // zwischen lrc place, sonst @{...} statt place
   for (int z=0; z<i-1 ; z++) {
      if ( format[z]!=4 && format[z+1]!=4)  dx_max += quad/2;  
   }

   // Ausgabe des Format Arrays
   //if (disp) for (int z=0; z<i+2 ; z++) System.out.println("format "+format[z]);


   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count();

   // loop: rows
   for (int y_i = 0; y_i<99; y_i++) {
      dy_pos = 0;
      dy_neg = 0;

      // loop: columns
      for (int x_i=0; x_i<99; x_i++) {
         // large der Argument-Box calculate
         box  = eqn(x,y,false,g,rec);  

         dy_pos = Math.max(dy_pos,box.dy_pos); 
         dy_neg = Math.max(dy_neg,box.dy_neg); 

         // largest Element per column
         dx_eqn[x_i] = Math.max(dx_eqn[x_i],box.dx);  

         // delimiter on columns end
         if ((eqTok.typ == EqToken.DBackSlash) || 
             (eqTok.typ == EqToken.END)           ) break;
      } // end columns

      dy_pos = Math.max(dy_pos,format_dy_pos); 
      dy_neg = Math.max(dy_neg,format_dy_neg); 
      dy_pos_eqn[y_i] = dy_pos;
      dy_neg_eqn[y_i] = dy_neg;
      dy_max += (dy_pos + dy_neg); 

      // delimiter on ARRAY-End
      if (eqTok.typ == EqToken.END) break;
   } // end rows


   // maximum rows wide determine
   for (i=0; i<99; i++) dx_max += dx_eqn[i]; 
 
   dx_max += 2 * quad/2;  // place links and right

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 

     dy_pos = 0;
     dy_top = dy_max/2+fM.getDescent();
     // loop: rows
     for (int y_i=0; y_i<99; y_i++) {
        dx     = quad/2;
        if (y_i==0) { dy_pos  = dy_pos_eqn[y_i]; }
           else     { dy_pos += (dy_neg_eqn[y_i-1] + dy_pos_eqn[y_i]); }

        int f = 0;     

        // loop: columns
        for (int x_i=0; x_i<99; x_i++) {

           while (format[f]==4){
              count = eqScan.get_count();
              eqScan.set_count(format_count[f]); 
              box = eqn(x+dx, y-dy_top+dy_pos,true,g,rec,false);
              dx     += box.dx;
              eqScan.set_count(count); 
              f++;
           }

           switch (format[f]) {
           case 0:
           case 1:
              // left
              box = eqn(x+dx, y-dy_top+dy_pos,true,g,rec);
              f++;
              break;
           case 2:
              // center
              count = eqScan.get_count(); 
              box = eqn(x, y,false,g,rec);
              eqScan.set_count(count); 
              box = eqn(x+dx+(dx_eqn[x_i]-box.dx)/2, y-dy_top+dy_pos,true,g,rec);
              f++;
              break;
           case 3:
              // right
              count = eqScan.get_count(); 
              box = eqn(x, y,false,g,rec);
              eqScan.set_count(count); 
              box = eqn(x+dx+dx_eqn[x_i]-box.dx, y-dy_top+dy_pos,true,g,rec);
              f++;
              break;
           case 4:
           default:
           } // end switch
 
           if (format[f]!=4) dx += quad/2;   // no @{}, then something mehr place

           dx     += dx_eqn[x_i];

           // delimiter am columnsende
           flag     = false;
           flag_end = false;
           if (eqTok.typ == EqToken.DBackSlash) flag=true;
           else if (eqTok.typ == EqToken.END)        {flag=true; flag_end=true;}
 
           // @{} am FormatstringEnde
           while (format[f]==4){
              count = eqScan.get_count();
              eqScan.set_count(format_count[f]); 
              box = eqn(x+dx, y-dy_top+dy_pos,true,g,rec,false);
              dx     += box.dx;
              eqScan.set_count(count); 
              f++;
           }
           if (flag) break;

       } // end columns

        if (flag_end) break;    // delimiter am ARRAY-Ende
     } // end rows
   } // end disp


   if (!expect(EqToken.BeginSym,"BEGIN 2: begin") ) return new BoxC(0,0,0); 
   eqScan.nextToken(); // array o. eqnarray
   if (!expect(EqToken.EndSym, "BEGIN 2: end") ) return new BoxC(0,0,0); 

   return new BoxC(dx_max+format_dx,dy_max/2+fM.getDescent(),dy_max/2-fM.getDescent());  
} // end BEGIN

//************************************************************************
private BoxC FBOX(int x, int y, boolean disp, Graphics g, int rec) {
   BoxC  box      = new BoxC();
   int   quadh     = g.getFont().getSize()/2;

   box = eqn(x+quadh, y, disp, g, rec, false);
   if (disp)   g.drawRect(x+quadh/2,      y-box.dy_pos-quadh/2, 
                          box.dx+quadh, box.dy_pos+box.dy_neg+quadh);
   return new BoxC(box.dx+quadh+quadh, box.dy_pos+quadh, box.dy_neg+quadh); 
} // end FBOX

//************************************************************************
private BoxC FG_BGColor(int x, int y, boolean disp, Graphics g, int rec,
                       boolean FG_BG) {
   BoxC      box        = new BoxC();
   int       count      = 0;
   Color     localColor = Color.white;

   // "{" vom Scanner holen
   if (!expect(EqToken.BeginSym, "Color: BeginSym") )  return new BoxC(0,0,0);  

   // Farbe vom Scanner holen (Wegen Unterscheidung Buchstaben Zahlen,
   // z.B. 000012 , ffccff ABER 00ff00 (MIX Buchst. Zahl.) loop)
   StringBuffer SBuffer = new StringBuffer("");
   for (int i=1; i<7; i++){
       SBuffer.append(eqScan.nextToken().stringS); 
       if (SBuffer.length() == 6) break;     
   }

   try   { localColor = new Color(Integer.parseInt(SBuffer.toString(),16));}
   catch (NumberFormatException e){ BGColor = Color.white; }

   // "}" vom Scanner holen
   if (!expect(EqToken.EndSym, "Color: EndSym") )  return new BoxC(0,0,0);  

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count();

   // large der Argument-Box calculate; die FGFarben muessen hier gesetzt will, da
   // im ersten Pass schon Images geladen und gefiltert will koennen!
   Color oldColor = g.getColor();
   if (FG_BG) g.setColor(localColor);
   box    = eqn(x,y,false,g,rec,false);
   g.setColor(oldColor);

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count);
      g.setColor(localColor);
      if (!FG_BG) {
         g.fillRect(x, y-box.dy_pos, box.dx, box.dy_pos+box.dy_neg);
         g.setColor(oldColor);
      }
      // Argument draw
      box = eqn(x,y,true,g,rec,false);
      g.setColor(oldColor);
   } // end disp
   return box;  
} // end FG_BGColor

//***********************************************************************************
private BoxC FRAC(int x, int y, boolean disp, Graphics g, int rec, boolean frac_other){
   int     bruch    = 0;
   BoxC    box      = new BoxC();
   BoxC    boxZ     = new BoxC();
   BoxC    boxN     = new BoxC();
   int     count    = 0;
   Font    localFont= g.getFont();
   int     quad     = localFont.getSize();

   rec_Font(g,rec+1);
   FontMetrics fM   = g.getFontMetrics();

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count();

   // Z�hler-Box calculate
   boxZ       = eqn(x,y,false,g,rec+1,false);
   int dy_pos = boxZ.dy_pos + boxZ.dy_neg; 

   // Nenner-Box calculate
   boxN = eqn(x,y,false,g,rec+1,false);
   int dx = Math.max(boxZ.dx,boxN.dx);  // wenn Nenner breiter als Z�hler 
   int dy_neg = boxN.dy_pos + boxN.dy_neg;

   // Abstand 3/18 quad before und hinter Bruchstrich
   Font font = g.getFont();
   int dx_bruch = (3*font.getSize())/18;
   dx += 2*dx_bruch;

   // Bruchstrich auf charactermitte anheben
   if (fM.getAscent()<dy_neg)  bruch = fM.getAscent()/2;

   // Space f�r Bruchstrich
   dy_pos+=(2+bruch);
   dy_neg+=(1-bruch); 

   // only disp=true is Scanner reset
   if (disp) {
       //System.out.println("Parser: FRAC: set_count = "+count);
       eqScan.set_count(count); 

      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
 
      // Bruchstrich
      if (frac_other) g.drawLine(x+dx_bruch,y-bruch,x+dx-dx_bruch,y-bruch);  

      // Z�hler draw
      box = eqn(x+(dx-boxZ.dx)/2,y-2-boxZ.dy_neg-bruch,true,g,rec+1,false);

      if (editModeFind && (rec<editModeRec)) editModeRec = rec; 
                              // damit bei Markierung der ganze Bruch 
                              // erkannt is.
     
      // Nenner draw
      box = eqn(x+(dx-boxN.dx)/2,y+1+boxN.dy_pos-bruch,true,g,rec+1,false);   

   } // end disp
   rec_Font(g,rec);



   return new BoxC(dx,dy_pos,dy_neg);  
} // end FRAC


//************************************************************************
private BoxC Id(int x, int y, boolean disp, Graphics g){

   Font font = g.getFont();
   g.setFont(new Font(Fontname,Font.ITALIC,font.getSize()));
   FontMetrics fM = g.getFontMetrics();
   if (disp) g.drawString(eqTok.stringS,x,y);
   int dx = fM.stringWidth(eqTok.stringS);
   int dy_pos = fM.getHeight()-fM.getDescent();
   int dy_neg = fM.getDescent();
   // if (disp) g.drawRect(x+dx,y-box.dy_pos,box.dx,box.dy_pos+box.dy_neg);
   g.setFont(font);
   return new BoxC(dx,dy_pos,dy_neg);
} // end Id

//************************************************************************
private void arc(Graphics g, int x, int y, int r, int start, int angle) {
// draw an arc of angle at (x,y) with radius r begin from start
// angles are in degrees
// positive angles are counterclockwise 
   g.drawArc(x-r,y-r,2*r,2*r,start,angle);
} // arc

//************************************************************************
private void drawBracket(Graphics g, String Bracket, int x, int dx, int yp, int yn, int quad, int s) {
 
  int r   = dx/2;
  int d   = x+r;
  int dd  = x + dx;
  int dh  = x + r/2;
  int ddh = d + r/2;
  int m   = (yp+yn)/2;
  int rred=(int)(r*0.86602540378444);
  int ypr=yp+rred;
  int ynr=yn-rred;
  if (Bracket.equals("[")) {
     g.drawLine(dh,yp,dh,yn);
     g.drawLine(dh,yn,ddh,yn);
     g.drawLine(dh,yp,ddh,yp);
  } 
  else if (Bracket.equals("]")) {
     g.drawLine(ddh,yp,ddh,yn);
     g.drawLine(dh,yn,ddh,yn);
     g.drawLine(dh,yp,ddh,yp);
  } 
  else if (Bracket.equals("|")) {
     g.drawLine(d,yp,d,yn);
  }
  else if (Bracket.equals("||")) {
     int d4 = d+quad/4;
     g.drawLine(d,yp,d,yn);
     g.drawLine(d4,yp,d4,yn);
  }
  else if (Bracket.equals("(")) {
     for (int i=s;i<2+s;i++) {
        int dpi=dh+i;
        arc(g,ddh+i,ypr,r,180,-60);
        g.drawLine(dpi,ypr,dpi,ynr);
        arc(g,ddh+i,ynr,r,180,60);
     }
  }
  else if (Bracket.equals(")")) {
     for (int i=s;i<2+s;i++) {
        int dpi=ddh+i;
        arc(g,dh+i,ypr,r,0,60);
        g.drawLine(dpi,ypr,dpi,ynr);
        arc(g,dh+i,ynr,r,0,-60);
     }
  }
  else if (Bracket.equals("<")) {
     g.drawLine(dh,m,ddh,yp);
     g.drawLine(dh,m,ddh,yn);
  }
  else if (Bracket.equals(">")) {
     g.drawLine(ddh,m,dh,yp);
     g.drawLine(ddh,m,dh,yn);
  }
  else if (Bracket.equals("{")) {
     for (int i=s;i<2+s;i++) {
        int dpi=d+i;
        arc(g,dd+i,ypr,r,180,-60);
        g.drawLine(dpi,ypr,dpi,m-r);
        arc(g,x+i,m-r,r,0,-90);
        arc(g,x+i,m+r,r,0,90);
        g.drawLine(dpi,m+r,dpi,ynr);
        arc(g,dd+i,ynr,r,180,60);
     }
  }
  else if (Bracket.equals("}")) {
     for (int i=s;i<2+s;i++) {
        int dpi=d+i;
        arc(g,x+i,ypr,r,0,60);
        g.drawLine(dpi,ypr,dpi,m-r);
        arc(g,dd+i,m-r,r,-180,90);
        arc(g,dd+i,m+r,r,180,-90);
        g.drawLine(dpi,m+r,dpi,ynr);
        arc(g,x+i,ynr,r,0,-60);
     }
  }
} // drawBracket 

//************************************************************************
private BoxC LEFT(int x, int y, boolean disp, Graphics g, int rec) {
   int         dx_left      = 0;
   int         dx_right     = 0;
   BoxC        box          = new BoxC();
   int         count        = 0;
   Font        localFont    = g.getFont();
   int         quad         = localFont.getSize();
   int         mkq          = (int)(mk * quad);
   int         space        = quad/9;
   Font BracketFont;
   FontMetrics BracketMetrics;

   // only disp=true only Scanner later reset will
   if (disp)  count = eqScan.get_count();

   // Klammertyp f�r linke Seite vom Scanner holen
   String LeftBracket    = eqScan.nextToken().stringS;

   // large der Argument-Box calculate
   box    = eqn(x,y,false,g,rec);
   int dx     = box.dx;
   int dy_pos = box.dy_pos;  
   int dy_neg = box.dy_neg; 
   int yp     = y-dy_pos+1;
   int yn     = y+dy_neg-1;
 
   // Klammertyp f�r rechte Seite vom Scanner holen
   String RightBracket = eqScan.nextToken().stringS;

   // Klammerlarge calculate
   int BracketSize    = dy_pos+dy_neg-2;

   BracketFont = new Font("Helvetica",Font.PLAIN,BracketSize);
   g.setFont(BracketFont);   
   BracketMetrics = g.getFontMetrics();
   if (LeftBracket.equals("<") || LeftBracket.equals(">")) {
      dx_left = quad;
   }
   else if (BracketSize < mkq) {
      dx_left  = BracketMetrics.stringWidth(LeftBracket);
      if ("([{)]}".indexOf(LeftBracket) >= 0) dx_left += space;
   }
   else dx_left = quad;

   if (RightBracket.equals("<") || RightBracket.equals(">")) {
      dx_right = quad;
   }
   else if (BracketSize < mkq) {
      dx_right = BracketMetrics.stringWidth(RightBracket);
      if ("([{)]}".indexOf(RightBracket) >= 0) dx_right += space;
   }
   else dx_right = quad;
   g.setFont(localFont);

   // hinter Klammer Hoch-/Tiefstellung
   int count2 = eqScan.get_count();
   // "SUB"
   int SUB_dx = 0;
   int SUB_baseline = 0;  
   if (eqScan.nextToken().typ == EqToken.SUB) {
      box    = SUB(x,y,false,g,rec,false);
      SUB_dx=box.dx;
      SUB_baseline = yn+box.dy_pos-(box.dy_pos+box.dy_neg)/2;
      dy_neg += (box.dy_pos+box.dy_neg)/2;
   } else eqScan.set_count(count2); 
   int count1 = eqScan.get_count();

   // "SUP"
   int SUP_dx = 0;
   int SUP_baseline = 0; 
   if (eqScan.nextToken().typ == EqToken.SUP) {
      box    = SUP(x,y,false,g,rec,false);
      SUP_dx = box.dx;
      SUP_baseline = yp+box.dy_pos-(box.dy_pos+box.dy_neg)/2;
      dy_pos += (box.dy_pos+box.dy_neg)/2;
   } else eqScan.set_count(count1); 
   SUB_dx = Math.max(SUB_dx,SUP_dx);

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 

      //g.drawRect(x+dx_left,y-dy_pos,dx,dy_pos+dy_neg);

      // linker Klammertyp vom Scanner holen
      LeftBracket = eqScan.nextToken().stringS;
      if (!LeftBracket.equals(".")) {
         if (BracketSize < mkq && !(LeftBracket.equals("<") || LeftBracket.equals(">"))) { 
            // linke Klammern mit font draw
            g.setFont(BracketFont);
            g.drawString(LeftBracket,x,yn-BracketMetrics.getDescent()
                                         -BracketMetrics.getLeading()/2);
            g.setFont(localFont);
         } else 
            //linke Klammern direkt draw
            drawBracket (g,LeftBracket,x,dx_left,yp,yn,quad,0);
      }

      // Argument draw
      box = eqn(x+dx_left,y,true,g,rec);

      // rechter Klammertyp vom Scanner holen
      RightBracket = eqScan.nextToken().stringS;
      if (!RightBracket.equals(".")) {
         if (BracketSize < mkq && !(RightBracket.equals("<") || RightBracket.equals(">"))) { 
            // rechte Klammern mit font draw
            g.setFont(BracketFont);
            if ("([{)]}".indexOf(RightBracket) < 0) space = 0;
            g.drawString(RightBracket,x+dx+dx_left+space,yn-BracketMetrics.getDescent()
                                                     -BracketMetrics.getLeading()/2);
            g.setFont(localFont); 
         } else 
            //rechte Klammern direkt draw
           drawBracket (g,RightBracket,x+dx+dx_left,dx_right,yp,yn,-quad,-1); 
      }
      // hinter Klammer Hoch-/Tiefstellung
      count2 = eqScan.get_count();
      // "SUB" 
      if (expect(EqToken.SUB)) 
         box = SUB(x+dx+dx_left+dx_right,SUB_baseline,true,g,rec,false);
      else eqScan.set_count(count2); 
      count1 = eqScan.get_count();
      // "SUP" 
      if (expect(EqToken.SUP)) 
         box = SUP(x+dx+dx_left+dx_right,SUP_baseline,true,g,rec,false);
      else eqScan.set_count(count1); 
    } // end disp
   return new BoxC(dx+dx_left+dx_right+SUB_dx,dy_pos+2,dy_neg+2);  
} // end LEFT

//************************************************************************
private BoxC LIM(int x, int y, boolean disp, Graphics g, int rec){
   int     dx       = 0;
   BoxC    box      = new BoxC();
   int SUB_dx       = 0;
   int SUB_baseline = 0;

   FontMetrics fM       = g.getFontMetrics();
   String stringS = eqTok.stringS;

   // es only Scanner later reset will
   int count = eqScan.get_count();

   int im_dx = dx = fM.stringWidth(stringS);
   int dy_pos = fM.getHeight()-fM.getDescent();
   int dy_neg = fM.getDescent();

   if (expect(EqToken.SUB)) {
      box    = SUB(x,y,false,g,rec,false);
      SUB_dx=box.dx;
      dx = Math.max(dx,box.dx);
      SUB_baseline = box.dy_pos;
      dy_neg = box.dy_pos+box.dy_neg;
   } else eqScan.set_count(count); 
 
   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
      g.drawString(stringS,x+(dx-im_dx)/2,y);
      if (expect(EqToken.SUB))
         box = SUB(x+(dx-SUB_dx)/2,y+SUB_baseline,true,g,rec,false);
      else eqScan.set_count(count); 
   } // end disp

   return new BoxC(dx,dy_pos,dy_neg);  
} // end LIM

//************************************************************************
private BoxC MBOX(int x, int y, boolean disp, Graphics g) {
   // \mbox{...}  plain text within equations 
   int         dx       = 0; 
   int         dy_pos   = 0;
   int         dy_neg   = 0;
   BoxC        box      = new BoxC();

   // "{" vom Scanner holen
   if (!expect(EqToken.BeginSym)) return new BoxC(0,0,0);  

   while (!eqScan.EoT()) {
      eqTok = eqScan.nextToken();
      if (eqTok.typ != EqToken.EndSym) { 
         box = Plain(x+dx, y, disp, g);
         dx += box.dx;
         dy_pos = Math.max(dy_pos,box.dy_pos);
         dy_neg = Math.max(dy_neg,box.dy_neg);
      }
      else break;
   }

   return new BoxC(dx, dy_pos, dy_neg);  
} // end MBOX

//**********************************************************************
private BoxC NOT(int x, int y, boolean disp, Graphics g, int rec){
// Negation: \not <symbol>   or \not{ <eqn> }
   BoxC    box      = new BoxC();

   box    = eqn(x,y,disp,g,rec,false);  

   if (disp) g.drawLine(x + box.dx/4 ,     y + box.dy_neg,
                        x + (box.dx*3)/4,  y - box.dy_pos );
   return box;  
} // end NOT

//************************************************************************
private BoxC Op(int x, int y, boolean disp, Graphics g) {
// Operatoren
   FontMetrics fM       = g.getFontMetrics();
 
   if (disp) g.drawString(eqTok.stringS,x+1,y);
   return new BoxC(fM.stringWidth(eqTok.stringS) + 2,
                   fM.getHeight()-fM.getDescent(),
                   fM.getDescent());  
} // end Op

//*************************************************************************
private BoxC OverBRACE(int x, int y, boolean disp, Graphics g, int rec) {
   int         count    = 0;
   BoxC        box      = new BoxC();
   int r                = g.getFont().getSize()/4;
   int rh               = r/2;
   int SUP_dx           = 0;
   int SUP_base         = 0;
   int SUP_dy           = 0;

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count(); 

   // large der Argument-Box calculate
   box          = eqn(x,y,false,g,rec,false);
   int dx       = box.dx;
   int dxh      = dx/2;
   int x_middle = dxh;
   int dy_pos   = box.dy_pos;
   int dy_neg   = box.dy_neg;

   // "SUP" behandeln, FALLS beforehanden
   int count1 = eqScan.get_count();
   if (expect(EqToken.SUP)) {
      box      = SUP(x,y,false,g,rec,false);
      SUP_dx   = box.dx;
      x_middle = Math.max(x_middle,SUP_dx/2);
      SUP_base = dy_pos     + box.dy_neg;
      SUP_dy   = box.dy_pos + box.dy_neg;
   } else eqScan.set_count(count1); 

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count);
      int xx   = x + x_middle-dxh;  
      box      = eqn(xx, y, true, g, rec, false);
      int rred = (int)(r*0.86602540378444);
      for (int i=0;i<2;i++) {
         int ypi = y-dy_pos-rh+i;
         arc(g,xx+rred,ypi+r,r,90,60);
         g.drawLine(xx+rred,ypi,xx+dxh-r,ypi);
         arc(g,xx+dxh-r,ypi-r,r,0,-90);
         arc(g,xx+dxh+r,ypi-r,r,-90,-90);
         g.drawLine(xx+dxh+r,ypi,xx+dx-rred,ypi);
         arc(g,xx+dx-rred,ypi+r,r,90,-60);
      }
      count1 = eqScan.get_count();
      if (expect(EqToken.SUP)) 
         box = SUP(x+x_middle-SUP_dx/2, y-SUP_base-r-rh,true,g,rec,false);
      else eqScan.set_count(count1); 
   } // end disp

   dy_pos += SUP_dy + r + rh ;
   dx = Math.max(dx,SUP_dx);

   return new BoxC(dx,dy_pos,dy_neg); 
} // end OverBRACE


//*************************************************************************
private BoxC UnderBRACE(int x, int y, boolean disp, Graphics g, int rec) {
   int         count    = 0;
   BoxC        box      = new BoxC();
   int r                = g.getFont().getSize()/4;
   int rh               = r/2;
   int SUB_dx           = 0;
   int SUB_base         = 0;
   int SUB_dy           = 0;

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count(); 

   // large der Argument-Box calculate
   box      = eqn(x,y,false,g,rec,false);
   int dx       = box.dx;
   int dxh      = dx/2;
   int x_middle = dxh;
   int dy_pos   = box.dy_pos;
   int dy_neg   = box.dy_neg;

   // "SUB" behandeln, FALLS beforehanden
   int count1 = eqScan.get_count();
   if (expect(EqToken.SUB)) {
      box      = SUB(x,y,false,g,rec,false);
      SUB_dx   = box.dx;
      x_middle = Math.max(x_middle,SUB_dx/2);
      SUB_base = dy_neg     + box.dy_pos;
      SUB_dy   = box.dy_pos + box.dy_neg;
   } else eqScan.set_count(count1); 

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      int xx   = x + x_middle-dxh;  
      box      = eqn(xx, y, true, g, rec, false);
      int rred = (int)(r*0.86602540378444);
      for (int i=0;i<2;i++) {
         int ypi = y+dy_neg+rh-i;
         arc(g,xx+rred,ypi-r,r,-90,-60);
         g.drawLine(xx+rred,ypi,xx+dxh-r,ypi);
         arc(g,xx+dxh-r,ypi+r,r,90,-90);
         arc(g,xx+dxh+r,ypi+r,r,90,90);
         g.drawLine(xx+dxh+r,ypi,xx+dx-rred,ypi);
         arc(g,xx+dx-rred,ypi-r,r,-90,60);
      }
      count1 = eqScan.get_count();
      if (eqScan.nextToken().typ == EqToken.SUB) 
         box = SUB(x+x_middle-SUB_dx/2, y+SUB_base+r+rh,true,g,rec,false);
      else eqScan.set_count(count1); 
   } // end disp

   dy_neg += SUB_dy + r + rh ;
   dx = Math.max(dx,SUB_dx);

   return new BoxC(dx,dy_pos,dy_neg); 
} // end UnderBRACE

//************************************************************************
private BoxC OverUnderLINE(int x, int y, boolean disp, Graphics g, int rec, 
                          boolean OverUnder) {
   int         count    = 0;
   BoxC        box      = new BoxC();
  
   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count(); 

   // large der Argument-Box calculate
   box = eqn(x,y,false,g,rec,false);
   if (OverUnder)  box.dy_pos += 2; // place over Strich
   else            box.dy_neg += 2; // place unter Strich
   int dy_pos=box.dy_pos;
   int dy_neg=box.dy_neg;
   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      if (OverUnder)  g.drawLine(x+1, y-dy_pos+2, x+box.dx-1, y-dy_pos+2);
      else            g.drawLine(x, y+dy_neg-2, x+box.dx, y+dy_neg-2);
      box = eqn(x,y,true,g,rec,false);
   } 
   return new BoxC(box.dx,dy_pos,dy_neg); 
} // end OverUnderLINE

//************************************************************************
private BoxC Paren(int x, int y, boolean disp, Graphics g){
   FontMetrics fM = g.getFontMetrics();
   int space      = g.getFont().getSize()/9;
   int dx         = fM.stringWidth(eqTok.stringS);
   int i = "([{)]}".indexOf(eqTok.stringS);
   if (i >= 0) {
      dx += space;
      if (i > 2 ) x += space;
   }
   if (disp) g.drawString(eqTok.stringS,x,y);
   return new BoxC( dx,
                    fM.getHeight()-fM.getDescent(),
                    fM.getDescent());
} // end Paren

//************************************************************************
private BoxC Plain(int x, int y, boolean disp, Graphics g){
   FontMetrics fM       = g.getFontMetrics();

   if (disp) g.drawString(eqTok.stringS,x,y);
   return new BoxC( fM.stringWidth(eqTok.stringS),
                    fM.getHeight()-fM.getDescent(),
                    fM.getDescent());
} // end Plain

//************************************************************************
private BoxC SPACE(int x, int y, boolean disp, Graphics g){
   // additional positive or negative space between elements
   int         dx     = 0;
   Font font = g.getFont();
   try                            { dx = Integer.parseInt(eqTok.stringS);}
   catch (NumberFormatException e){ dx = 0; }
   dx     =  ( dx * font.getSize()) / 18;
   return new BoxC(dx,0,0);  
} // end SPACE

//************************************************************************
private BoxC SQRT(int x, int y, boolean disp, Graphics g, int rec) {
   BoxC        box      = new BoxC();
   int         count    = 0;
   FontMetrics fM       = g.getFontMetrics();
   int dx_n             = 0;
   int dy_pos_n         = 0;
   int dy_neg_n         = 0;
   int dy_n             = 0;
   boolean n_sqrt       = false;

   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count();

   // something place for the hook of the root.
   int dx_Haken  = fM.stringWidth("A");
   int dx_Hakenh = dx_Haken/2;


   // \sqrt[...]{...}
   int     count1 = eqScan.get_count();
   EqToken token  = new EqToken();
   token          = eqScan.nextToken();
   if (token.stringS.equals("[")) {
      // large der [n.ten] root
      rec_Font(g,rec+1);
      box    = eqn(x,y,false,g,rec+1,true);
      rec_Font(g,rec);
      dx_n     = box.dx;
      dy_pos_n = box.dy_pos;
      dy_neg_n = box.dy_neg;
      dy_n     = dy_neg_n + dy_pos_n;
      n_sqrt   = true;
   } 
   else eqScan.set_count(count1);  

   // large der Argument-Box calculate
   box    = eqn(x,y,false,g,rec,false);
   int dx     = box.dx +  dx_Haken;
   int dy_pos = box.dy_pos + 2;  // additional place over overbar
   int dy_neg = box.dy_neg; 

   if (n_sqrt & dx_n>dx_Hakenh) dx += dx_n - dx_Hakenh;

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 

      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
 
      // root character
      int dx_n_h = 0;
      if (n_sqrt & dx_n > dx_Hakenh) dx_n_h = dx_n - dx_Hakenh;
      g.drawLine(x+dx_n_h+1,y-dy_pos/2,           x+dx_n_h+dx_Hakenh,y+dy_neg-1);
      g.drawLine(x+dx_n_h+dx_Hakenh,y+dy_neg-1,  x+dx_n_h+dx_Haken-2,y-dy_pos+2);
      g.drawLine(x+dx_n_h+dx_Haken-2,y-dy_pos+2,  x+dx,y-dy_pos+2 );

      if (n_sqrt) {
         token  = eqScan.nextToken(); 
         rec_Font(g,rec+1);
         if (dx_n>=dx_Hakenh){
               g.drawLine(x+1,y-dy_pos/2, x+dx_n_h+1,y-dy_pos/2);
               box = eqn(x+1,y- dy_pos/2 - dy_neg_n-1,true,g,rec+1,true);
         }
         else  box = eqn(x+1+(dx_Hakenh-dx_n),y- dy_pos/2 - dy_neg_n-1,true,g,rec+1,true);
         rec_Font(g,rec);
      }

      // Argument draw
      box = eqn(x+dx_n_h+dx_Haken,y,true,g,rec,false);

   } // end disp

   if (n_sqrt & dy_pos/2<dy_n) dy_pos = dy_pos/2 + dy_n;
  
   return new BoxC(dx,dy_pos,dy_neg);  
} // end SQRT

//***********************************************************************************
private BoxC STACKREL(int x, int y, boolean disp, Graphics g, int rec){
   // \stackrel{...}{...}
   BoxC    box      = new BoxC();
   int     count    = 0;
   int     leading  = g.getFontMetrics().getLeading();
   
   // only disp=true only Scanner later reset will
   if (disp) count = eqScan.get_count();

   // Obere-Box calculate
   box          = SUP(x, y, false, g, rec, true);
   int dx       = box.dx; 
   int dx_top  = box.dx;
   int dy_pos   = box.dy_pos + box.dy_neg - leading; 
   int base     = box.dy_neg - leading;

   // Untere-Box calculate
   box          = eqn(x, y, false, g, rec, false);
   dx           = Math.max(dx,box.dx);
   int x_mitte  = dx/2;
   int dx_below = box.dx;
   dy_pos      += box.dy_pos;
   int dy_neg   = box.dy_neg;
   base        += box.dy_pos;

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
 
      // top draw
      box = SUP(x+x_mitte-dx_top/2, y-base, true, g, rec, false);
     
      // below draw
      box = eqn(x+x_mitte-dx_below/2, y, true, g, rec, false);
   } // end disp

   return new BoxC(dx,dy_pos,dy_neg);  
} // end STACKREL

//************************************************************************
private BoxC SUB(int x, int y, boolean disp, Graphics g, int rec, boolean sub) {
   int         dy_pos   = 0;
   int         dy_neg   = 0;
   BoxC        box      = new BoxC();
   int         count    = 0;
   int         ascenth  = g.getFontMetrics().getAscent()/2;

   // only disp=true only Scanner later reset will
   if (disp)  count = eqScan.get_count();

   rec_Font(g,rec+1);
   // large der Argument-Box calculate
   box    = eqn(x,y,false,g,rec+1,false);
   int dx = box.dx;
   if (sub){ dy_pos = ascenth-1; 
             dy_neg = box.dy_pos+box.dy_neg-dy_pos;}
   else    { dy_neg = box.dy_pos+box.dy_neg;}

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      //g.drawRect(x,y-dy_pos,dx,box.dy_pos+box.dy_neg);
      // Argument draw
      if (sub)  box = eqn(x,y+box.dy_pos-dy_pos,true,g,rec+1,false);
      else      box = eqn(x,y+box.dy_pos,true,g,rec+1,false);
   } // end disp
   rec_Font(g,rec);

   // if next token is SUP, plot SUP-expression ABOVE SUB-expression
   if (sub) {
     count = eqScan.get_count();
     if (expect(EqToken.SUP)){ 
        box = SUP(x,y,disp,g,rec,true);
        dx     = Math.max(dx,box.dx);
        dy_pos = Math.max(dy_pos,box.dy_pos);}
     else  eqScan.set_count(count);
   }

   return new BoxC(dx,dy_pos,dy_neg);  
} // end SUB


//************************************************************************
private BoxC SUP(int x, int y, boolean disp, Graphics g, int rec, boolean sup) {
   // sup = true, put in exponent     sup = false, don't move (\sum,\lim)
   int         dy_pos   = 0;
   int         dy_neg   = 0;
   BoxC        box      = new BoxC();
   int         count    = 0;
   int         ascenth  = g.getFontMetrics().getAscent()/2;

   // only disp=true only Scanner later reset will
   if (disp)  count = eqScan.get_count();

   rec_Font(g,rec+1);

   // large der Argument-Box calculate
   box    = eqn(x,y,false,g,rec+1,false);
   int dx = box.dx;
   if (sup){ dy_neg = -ascenth-1;
             dy_pos = box.dy_pos+box.dy_neg-dy_neg;}
   else    { dy_pos = box.dy_pos+box.dy_neg;} 

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count);
      //g.drawRect(x,y-dy_pos,dx,box.dy_pos+box.dy_neg);
      // Argument draw
      if (sup)  box = eqn(x,y-box.dy_neg+dy_neg,true,g,rec+1,false);
      else      box = eqn(x,y-box.dy_neg,true,g,rec+1,false);
   } // end disp

   rec_Font(g,rec);

   // if next token is SUB, plot SUB-expression BELOW SUP-expression
   if (sup) {
     count = eqScan.get_count();
     if (expect(EqToken.SUB)){ 
        box    = SUB(x,y,disp,g,rec,true);
        dx     = Math.max(dx,box.dx);
        dy_neg = Math.max(dy_neg,box.dy_neg);}
     else  eqScan.set_count(count);
   }

   return new BoxC(dx,dy_pos,dy_neg);  
} // end SUP

/************************************************************************/
private Image getSymbol(Graphics g, int rec){
   // Symbol over the network load if not already in cache
   // using the Media Tracker, so that the image on the display time
   // also completely loaded.

   // generate unique key
   String key = eqTok.stringS+GreekSize[rec-1]+g.getColor().getRGB();

   if (!imageH.containsKey(key)) {
      // Symbol is not in Cache

      String s1 = "Fonts/Greek" + GreekSize[rec - 1] + "/" + eqTok.stringS + ".gif";
      Image image = symbolLoader.getImage(appletB, beanB, s1, g, app);
      int i = eqScan.get_count();
      tracker.addImage(image,i);
      displayStatus("Loading "+eqTok.stringS);
      try   { tracker.waitForID(i,10000); }    // warten bis Bild geladen (max. 10 sec.)
      catch (InterruptedException e) {};
      if (tracker.isErrorID(i))  displayStatus("Error loading "+eqTok.stringS);
      else {
        //System.out.println("put"+key);
        imageH.put(key,image);
        //System.out.println("putted"+key);
      }
      //displayStatus(eqTok.stringS+" is loaded");
      //System.out.println(image.getWidth(this)+" "+image.getHeight(this)+" "+tracker.isErrorAny()+" "+g.getColor()+" "+i+" "+img);
      return image;
   }
   else {
      //System.out.println("get"+key);
      return (Image)(imageH.get(key)); // retrieve from cache
   }
} // end getSymbol
 
//************************************************************************
private BoxC SYMBOP(int x, int y, boolean disp, Graphics g, int rec, boolean desc){
   FontMetrics fM     = g.getFontMetrics();
   // Symbol over the network load
   // using the Media Tracker, so that the image on the display time
   // also completely loaded.
   rec=Math.min(rec,GreekSize.length);
   Image image = getSymbol(g,rec);
   int dx = image.getWidth(this);
   if (dx < 0) dx = fM.getMaxAdvance();    // default falls image missing
   if (disp) {
     int dy = 0;
     if (desc) dy = GreekDescent[rec-1];
     g.drawImage(image,x,y-image.getHeight(this)+dy,this);
   }
   //System.out.println(image.getWidth(this)+" "+image.getHeight(this));
   //if (disp) g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
   return new BoxC(dx,fM.getHeight()-fM.getDescent(),fM.getDescent());  
} // end SYMBOP


//************************************************************************
private BoxC SYMBOLBIG(int x, int y, boolean disp, Graphics g, int rec){
   // for SUM,PROD,INT, etc.
   int         dx        = 0;
   BoxC        box       = new BoxC();
   int       SUB_baseline= 0;
   int       SUP_baseline= 0;
   int       SUB_dx      = 0;
   int       SUP_dx      = 0;
   int      dy_pos_image = 0;
   int       asc         = g.getFontMetrics().getAscent();
   // Symbol over the network load
   // using the Media Tracker, so that the image on the display time
   // also completely loaded.
   rec=Math.min(rec,GreekSize.length);
   //System.out.println(" before getSymbol");
   Image image = getSymbol(g,rec);
   //System.out.println(" after getSymbol");
   int im_dx  = dx = image.getWidth(this);
   int h      = image.getHeight(this);
   if (h < 0) {
      h = 2*asc;      // default falls image missing
      im_dx  = dx = asc;
   }
   int dy_neg = (int)(h/2-0.4*asc);
   int dy_pos = dy_pos_image = h-dy_neg;
   //if (disp)  g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);

   /////////// SUB und SUP ////// calculate
   // es only Scanner later reset will
   int count = eqScan.get_count();

   // "SUB" 
   if (expect(EqToken.SUB)) {
      box          = SUB(x,y,false,g,rec,false);
      SUB_dx       = box.dx;
      dx           = Math.max(dx,box.dx);
      SUB_baseline = dy_neg+box.dy_pos;
      dy_neg      += box.dy_pos+box.dy_neg;
   } else eqScan.set_count(count); 
   int count1 = eqScan.get_count();

   // "SUP" 
   if (expect(EqToken.SUP)) {
      box          = SUP(x,y,false,g,rec,false);
      SUP_dx       = box.dx;
      dx           = Math.max(dx,box.dx);
      SUP_baseline = dy_pos+box.dy_neg;
      dy_pos      += box.dy_pos+box.dy_neg;
   } else eqScan.set_count(count1); 

   // only disp=true is Scanner reset
   if (disp) {
      eqScan.set_count(count); 
      g.drawImage(image,x+(dx-im_dx)/2,y-dy_pos_image,this);
      //g.drawRect(x,y-dy_pos,dx,dy_pos+dy_neg);
      // "SUB" 
      if (expect(EqToken.SUB)) 
         box = SUB(x+(dx-SUB_dx)/2,y+SUB_baseline,true,g,rec,false);
      else eqScan.set_count(count); 
      count1 = eqScan.get_count();
      // "SUP" 
      if (expect(EqToken.SUP)) 
         box = SUP(x+(dx-SUP_dx)/2,y-SUP_baseline,true,g,rec,false);
      else eqScan.set_count(count1); 
   } // end disp
   return new BoxC(dx,dy_pos,dy_neg);  
} // end SYMOLBIG


//************************************************************************
private BoxC VEC(int x, int y, boolean disp, Graphics g, int rec) {
   // \vec{...}  \bar{...} \whidehat{...} \whidetilde{...} 
   BoxC        box   = new BoxC();
   int         quad  = g.getFont().getSize();
   String      arg   = eqTok.stringS;

   box = eqn(x,y,disp,g,rec,false);
   int dx     = box.dx;
   int dxh    = dx/2;
   int dd     = quad/4;
   int dy_pos = box.dy_pos + dd; 
   int dy_neg = box.dy_neg; 

   // only disp=true is Scanner reset
   if (disp) {

      int ddy   = y-dy_pos+dd;
      int quad8 = quad/8; 
      int ddx   = x + dx;
      int xdx2 = x+dxh;
      if (arg.equals("")) {
         g.drawLine(x,ddy,ddx,ddy);
         g.drawLine(x+(int)(dx*0.8),ddy-quad8,ddx,ddy);
         g.drawLine(x+(int)(dx*0.8),ddy+quad8,ddx,ddy);
      }
      else if (arg.equals("bar")) 
         g.drawLine(x,ddy,ddx,ddy);
      else if (arg.equals("widehat")) {
         g.drawLine(x,    ddy,       xdx2, ddy-dd);
         g.drawLine(xdx2, ddy-dd ,ddx , ddy);
      }
      else if (arg.equals("widetilde")) { 
         int y1Val = 0;
         int y2 = 0;
         for (int i=1; i<dxh ; i++) {
         y1Val = y2;
         y2 = (int) ( quad8 * Math.sin(1.3*Math.PI*i/dxh) );
         g.drawLine(xdx2+i-1,ddy+y1Val,xdx2+i,ddy+y2);
         g.drawLine(xdx2-i+1,ddy-y1Val,xdx2-i,ddy-y2);         }
      }

   } // end disp
   return new BoxC(dx, dy_pos+2, dy_neg);  
} // end VEC


//**********************************************************************
private boolean expect(int token) {
   return expect(token, "");
}

private boolean expect(int token, String S) {
   int typ;
   while ( (typ = eqScan.nextToken().typ) == EqToken.SpaceChar ) ;
   if (typ == token) {
      return true;
   } else {
     if (!S.equals("")) printStatus("Parser: "+ S+" not found");        
      return false;
   } 
} // end expect

private boolean expect(int token1, int token2) {
   return expect(token1, token2, "");
}

private boolean expect(int token1, int token2, String S) {
   int typ;
   boolean tag;

   while ( (typ = eqScan.nextToken().typ) == EqToken.SpaceChar ) ;
   tag = (typ == token1);
  
   while ( (typ = eqScan.nextToken().typ) == EqToken.SpaceChar ) ;
   tag = (typ == token2);

   if (!tag) {
     if (!S.equals("")) printStatus("Parser: "+ S+" not found");        
   } 
   return tag;
} // end expect

//**********************************************************************
private void rec_Font(Graphics g, int rec) {
   // Begrenzung der Rekursionstiefe f�r die Schriftlarge
   // Vermeidung von SEHR KLEINEN Schriften
   if (rec <= 1)  g.setFont(f1);
   else if (rec == 2)  g.setFont(f2);
   else if (rec == 3)  g.setFont(f3);
   else g.setFont(f4); 
} // end limit_rec
} // end ********** class sHotEqn ****************************************

// sSymbolLoader for packed font files (fast speed)
class sSymbolLoader {
private ImageProducer [] imageSources = {null,null,null,null,null};
private String [] fontsizes = {"8","10","12","14","18"};	
private Hashtable fontdesH = new Hashtable (189);
//Fonts are included in HotEqn zip/jar file
private static boolean kLocalFonts=true;

public sSymbolLoader() { }
// dummy constructor

public Image getImage( boolean  appletB, boolean beanB, String filenameS,
                Graphics g,      JApplet   app) { //changed 13.10.2002
	StringTokenizer st = new StringTokenizer(filenameS, "/");
	String fontsize = st.nextToken();
	fontsize = (st.nextToken()).substring(5);
	String fn = st.nextToken();
	int	k =	-1;
	for	(boolean loop =	true; loop;) {
		if (fontsizes[++k].equals(fontsize)) loop=false;
		if (k==4) loop=false;
	}
	//System.out.println(fontsizes[k]);
	if (imageSources[k] == null) { 			
		imageSources[k]=getBigImage(appletB, beanB,  "Fonts"+fontsize+".gif",  app);
		String desname = "Des"+fontsize+".gif";
		BufferedInputStream istream = null;
		// load font descriptors
		try {
			if (kLocalFonts) {
				InputStream ip = getClass().getResourceAsStream(desname);
				//System.out.println("ip");
				istream = new BufferedInputStream(getClass().getResourceAsStream(desname));
				//System.out.println("nlocal");
			} else {
				//Try loading external Font files in component/applet/bean specific manner
				if (!appletB & !beanB) {
					// component code
					istream = new BufferedInputStream((new URL(desname)).openStream());
				} else if (appletB) { 
					// applet code
					istream = new BufferedInputStream((new URL(app.getCodeBase(), desname)).openStream());
					//System.out.println("file");
				} else {
					// bean code
					// beanB==true
					try {
						istream = new BufferedInputStream(getClass().getResource(desname).openStream());
					} catch (Exception ex) { }
				}
			}
			ObjectInputStream p = new ObjectInputStream(istream);
			int len = (int)p.readInt();
			for (int i=0;i<len;i++) {
				String ft = (String)p.readObject();
				fontdesH.put(fontsize+ft,new Rectangle((Rectangle)p.readObject()));
			}
			istream.close();
		}	
		catch(Exception	exf)
		{
			System.out.println(exf.toString());
			imageSources[k] = null;
		}
	}
	// crop and filter images
	Image image = null;
	if (imageSources[k]!= null) {
		Rectangle r = (Rectangle)(fontdesH.get(fontsize+fn));
		image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
            new FilteredImageSource(imageSources[k],
			new CropImageFilter(r.x,r.y,r.width,r.height )), new ColorMaskFilter(g.getColor())));
  }
  return image;
} // end getImage


public ImageProducer getBigImage( boolean  appletB, boolean beanB, String filenameS, JApplet   app) { //changed 13.10.2002
  ImageProducer imageSource=null;

  if(kLocalFonts) {
    imageSource = getLocalImageSource(filenameS);
  }
  if(imageSource==null) { //Fonts are not local
    kLocalFonts=false;  //don't attempt to load local fonts anymore

    //Try loading external Font files in component/applet/bean specific manner
    if (!appletB & !beanB) {
      // component code
      imageSource=Toolkit.getDefaultToolkit().getImage( filenameS ).getSource();
    } else if (appletB) { 
      // applet code
      imageSource= app.getImage(app.getCodeBase(), filenameS ).getSource();
    } else {
      // bean code
      // beanB==true
      try {
        URL url = getClass().getResource(  filenameS );
        imageSource = (ImageProducer) url.getContent();
      } catch (Exception ex) { }
    }
  }
  return imageSource;
} // end getImage

ImageProducer getLocalImageSource(String resourceName) {
  //Try loading images from jar
  ImageProducer source = null;
  try {
    // Next line assumes that Fonts are in the same jar file as sSymbolLoader
    // Since resourceName doesn't start with a "/", resourceName is treated
    // as the relative path to the image file from the directory where
    // sSymbolLoader.class is.
    InputStream imageStream = getClass().getResourceAsStream(resourceName);
    int numBytes = imageStream.available();//System.out.println(numBytes);
    byte[] imageBytes = new byte[numBytes];
	//System.out.println(numBytes);
    // Note: If all bytes are immediately available, the while loop just
    // executes once and could be replaced by the line:
    // imageStream.read(imageBytes,0,numBytes);
    // This may always be the case for the small Font images

    int alreadyRead = 0;
    int justRead = 0;
    while (justRead != -1) {
      justRead = imageStream.read(imageBytes,alreadyRead,numBytes);
      if(justRead != -1) { //didn't get all the bytes
        alreadyRead += justRead; //Total Read so far
        numBytes = imageStream.available(); //Amount left to read
        int totalBytes = alreadyRead + numBytes; //total bytes needed to
                                                 //store everything we know about
		//System.out.println("+"+numBytes);
        if((totalBytes) > imageBytes.length) {  //haven't yet allocated enough space
          byte[] tempImageBytes= (byte[]) imageBytes.clone();
          imageBytes = new byte[totalBytes];
          System.arraycopy(tempImageBytes, 0, imageBytes, 0, alreadyRead);
        }
      }
      if (numBytes == 0) break;
    }
    //Create an ImageProducer from the image bytes
    source = Toolkit.getDefaultToolkit().createImage(imageBytes).getSource();
  }
  catch (Exception io) {}
  return source;
} // end getLocalImageSource

} // end class sSymbolLoader

/* 
// sSymbolLoader for unpacked font files (slow speed)
class sSymbolLoader {

public sSymbolLoader() { }
// dummy constructor

//Fonts are included in HotEqn zip/jar file
private static boolean kLocalFonts=true;

public Image getImage( boolean  appletB, boolean beanB, String filenameS,
                Graphics g,      JApplet   app) {
  ImageProducer imageSource=null;
  Image image=null;

  if(kLocalFonts) {
    imageSource = getLocalImageSource(filenameS);
  }
  if(imageSource==null) { //Fonts are not local
    kLocalFonts=false;  //don't attempt to load local fonts anymore

    //Try loading external Font files in component/applet/bean specific manner
    if (!appletB & !beanB) {
      // component code
      imageSource=Toolkit.getDefaultToolkit().getImage( filenameS ).getSource();
    } else if (appletB) { 
      // applet code
      imageSource= app.getImage(app.getCodeBase(), filenameS ).getSource();
    } else {
      // bean code
      // beanB==true
      try {
        URL url = getClass().getResource(  filenameS );
        imageSource = (ImageProducer) url.getContent();
      } catch (Exception ex) {
      }
    }
  }
  if(imageSource!=null) {
    image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
               imageSource, new ColorMaskFilter(g.getColor())));
  }
  return image;
} // end getImage

ImageProducer getLocalImageSource(String resourceName) {
  //Try loading images from jar
  ImageProducer source = null;
  try {
    // Next line assumes that Fonts are in the same jar file as sSymbolLoader
    // Since resourceName doesn't start with a "/", resourceName is treated
    // as the relative path to the image file from the directory where
    // sSymbolLoader.class is.
    InputStream imageStream = getClass().getResourceAsStream(resourceName);
    int numBytes = imageStream.available();//System.out.println(numBytes);
    byte[] imageBytes = new byte[numBytes];
//System.out.println(numBytes);
    // Note: If all bytes are immediately available, the while loop just
    // executes once and could be replaced by the line:
    // imageStream.read(imageBytes,0,numBytes);
    // This may always be the case for the small Font images

    int alreadyRead = 0;
    int justRead = 0;
    while (justRead != -1) {
      justRead = imageStream.read(imageBytes,alreadyRead,numBytes);
      if(justRead != -1) { //didn't get all the bytes
        alreadyRead += justRead; //Total Read so far
        numBytes = imageStream.available(); //Amount left to read
        int totalBytes = alreadyRead + numBytes; //total bytes needed to
                                                 //store everything we know about
//System.out.println("+"+numBytes);
        if((totalBytes) > imageBytes.length) {  //haven't yet allocated enough space
          byte[] tempImageBytes= (byte[]) imageBytes.clone();
          imageBytes = new byte[totalBytes];
          System.arraycopy(tempImageBytes, 0, imageBytes, 0, alreadyRead);
        }
      }
    }
    //Create an ImageProducer from the image bytes
    source = Toolkit.getDefaultToolkit().createImage(imageBytes).getSource();
  }
  catch (Exception io) {}
  return source;
} // end getLocalImageSource

} // end class sSymbolLoader
*/

