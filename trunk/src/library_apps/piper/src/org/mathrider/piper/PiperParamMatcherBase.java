/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathrider.piper;


/// Abstract class for matching one argument to a pattern.
abstract class PiperParamMatcherBase
{
	/// Check whether some expression matches to the pattern.
	/// \param aEnvironment the underlying Lisp environment.
	/// \param aExpression the expression to test.
	/// \param arguments (input/output) actual values of the pattern
	/// variables for \a aExpression.
	public abstract boolean ArgumentMatches(LispEnvironment  aEnvironment,
	                                        LispPtr  aExpression,
	                                        LispPtr[]  arguments) throws Exception;
}
