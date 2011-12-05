/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//#########################################################################
//	OperatorEntry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;

/**
* Abstract base class for entries in the <code>pOperatorRegistry</code>. Each
* operator entry subclass represents a type of prolog operator predicate at the 
* level needed for the parser (they are parse time representations).
* Operator entries contain information about the corresponding operator, such
* as associativity, precedence, and type.
* Each instance of the operator entry is bound to parser representations of its
* operands.  Operator entries generate the corresponding real Prolog operator
* objects and their real Prolog operand <code>jTerms</code>. 
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
abstract public class pOperatorEntry
{
 public final static int 		FX = 0x01;
 public final static int 		FY = 0x02;
 public final static int 		XFX = 0x11;
 public final static int 		XFY = 0x12;
 public final static int 		YFX = 0x21;
 public final static int 		XF = 0x10;
 public final static int 		YF = 0x20;

 public final static int 		NON_ASSOCIATIVE = 0;
 public final static int 		LEFT_ASSOCIATIVE = 1;
 public final static int 		RIGHT_ASSOCIATIVE = 2;

 protected String 		name;
 protected int 			type;
 protected int 			priority;
 protected String		library = null; 
 
 public 	pOperatorEntry(String name,int type,int priority)
 {
  this.name = name;
  this.type = type;
  this.priority = priority;
  
   // prevent invalid entries
  if (name == null || name.length() <= 0 || !isValidType() || 
      priority < 0 || priority > 1200)
   throw new InvalidOperatorEntryException();
 };
 
 public String 			getName()
 {
  return name;
 };
 
 public boolean 		hasLHS()
 {
  return (type & 0xF0) != 0;
 };
 
 public boolean 		hasRHS()
 {
  return (type & 0x0F) != 0;
 };
 
 /**
  * Determine if an unbound operator is not an error, or if it could be an atom
  *
  * @return 		<code>true</code> if this operator could also be an atom if it is
  * 			unbound.
  */
 public boolean 		isAtomPermitted()
 {
  return true;
 };
 
 public int 			getType()
 {
  return type;
 };
 
 public int 			getPriority()
 {
  return priority;
 };
 
 public int 		getAssociativity()
 {
  if ((type & 0x20) == 0x20)
   return LEFT_ASSOCIATIVE;

  if ((type & 0x02) == 0x02)
   return RIGHT_ASSOCIATIVE;

  return NON_ASSOCIATIVE;
 };

 public boolean		isNonAssociativeLeft()
 {
  return ((type & 0x20) != 0x20);
 };

 public boolean 	isNonAssociativeRight()
 {
  return ((type & 0x02) != 0x02);
 };

 public boolean 		isValidType()
 {
  switch (type)
  {
   case FX:
   case FY:
   case XFX:
   case XFY:
   case YFX:
   case XF:
   case YF:
    return true;
   default:
    return false;
  }
 };

 /**
  * Set the name of the library associated with this operator.  Once an association is made
  * it is an error to change it (throws <code>InvalidLibraryEntryException</code> in this case).
  * Operators default to a <code>null</code> valued library.
  *
  * @param lib		The name of the library.
  */
 public void			setLibrary(String lib)
 {
  if (library == null)
   library = lib;
  else
   throw new InvalidLibraryEntryException();
 };

 /**
  * Get the name of the library associated with this operator.
  *
  * @return		The name of the library. May be <code>null</code>.
  */
 public String			getLibrary()
 {
  return library;
 };
 
 public boolean			sameLibrary(String lib)
 {
  if (library == null && lib == null)
   return true;
  if (library == null || lib == null)
   return false;
  
   return library.equals(lib); 
 };

 /**
  * Public interface for generating the real prolog term objects.  May generate syntax errors.
  * Invokes internal <code>createOperator</code> to perform actual construction of term.
  *
  * @param pt 		The parsing token representing the operator. Used for generating 
  * 			information about the location of the operator in the input stream.
  * @param lhs 		A parsing packet (possibly null) representing the left operand.
  * 			Used to generate the left hand <code>jTerm</code>.
  * @param rhs 		A parsing packet (possibly null) representing the right operand.
  * 			Used to generate the right hand <code>jTerm</code>.
  * @param vars 	A registery of named variables (ensures that variables of the same name
  * 			are the same instance). This should be the variable registry of the
  * 			rule or command containing this operator.
  * @param phash 	A registery of terms and their corresponding parsing tokens.
  *
  * @return 		<code>jTerm</code> representing this operator and its operands.
  */
 public jTerm 			createOperator(pToken pt,pPacket lhs,pPacket rhs,
                                                pVariableRegistry vars,pTermToPacketHashtable phash)
 {jTerm 		l = null,r = null;
 
  if (isAtomPermitted() && lhs == null && rhs == null)
   return new jAtom(name);
   
  if (hasLHS() && (lhs == null || ((l = lhs.getTerm(vars,phash)) == null)))
   throw new SyntaxErrorException("LHS term required before operator '" + getName() + "' at ",
                                   pt.getPosition(),pt.getLine(),pt.getCharPos()); 

  if (hasRHS() && (rhs == null || ((r = rhs.getTerm(vars,phash)) == null)))
   throw new SyntaxErrorException("RHS term required after operator '" + getName() + "' at ",
                                   pt.getPosition(),pt.getLine(),pt.getCharPos()); 

  return createOperator(l,r);
 };
 
 /**
  * The internal method for creating the <code>jTerm</code> representation of this operator
  * representation.  Subclasses must override.  The operands are provided, already created.
  *
  * @param l 		The left hand <code>jTerm</code>.
  * @param r 		The right hand <code>jTerm</code>.
  *
  * @return 		<code>jTerm</code> representing this operator and its operands.
  */
 abstract public jTerm 		createOperator(jTerm l,jTerm r);

 public String			toString()
 {StringBuffer			sb = new StringBuffer(getName());

  sb.append("(");
   
  switch (getType())
  {
   case FX:
     sb.append("FX");
	break;
   case FY:
     sb.append("FY");
	break;
   case XFX:
     sb.append("XFX");
	break;
   case XFY:
     sb.append("XFY");
	break;
   case YFX:
     sb.append("YFX");
	break;
   case XF:
     sb.append("XF");
	break;
   case YF:
     sb.append("YF");
	break;
   default:
     sb.append("???");
  }

  sb.append(",");
  sb.append(Integer.toString(getPriority()));
  sb.append(",");
  sb.append((isAtomPermitted() ? "atom allowed" : "atom *not* allowed"));
  sb.append(")");
  
  {String   lib = getLibrary();
  
   if (lib != null)
    sb.append(" library:"+lib);
  }
  
  return sb.toString();
 };
};

