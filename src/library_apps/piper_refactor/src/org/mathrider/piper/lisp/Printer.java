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

package org.mathrider.piper.lisp;


public class Printer
{
	public void Print(ConsPointer aExpression, Output aOutput, Environment aEnvironment) throws Exception
	{
		PrintExpression(aExpression, aOutput, aEnvironment,0);
	}
	public void RememberLastChar(char aChar)
	{
	}

	void PrintExpression(ConsPointer aExpression, Output aOutput,
	                     Environment aEnvironment,int aDepth /* =0 */) throws Exception
	{
		ConsPointer iter = new ConsPointer();
		iter.set(aExpression.get());
		int item = 0;
		while (iter.get() != null)
		{
			// if String not null pointer: print string
			String string = iter.get().string();

			if (string != null)
			{
				aOutput.Write(string);
				aOutput.PutChar(' ');
			}
			// else print "(", print sublist, and print ")"
			else if (iter.get().subList() != null)
			{
				if (item != 0)
				{
					Indent(aOutput,aDepth+1);
				}
				aOutput.Write("(");
				PrintExpression((iter.get().subList()),aOutput, aEnvironment,aDepth+1);
				aOutput.Write(")");
				item=0;
			}
			else
			{
				aOutput.Write("[GenericObject]");
			}
			iter = (iter.get().cdr());
			item++;
		} // print cdr element
	}

	void Indent(Output aOutput, int aDepth) throws Exception
	{
		aOutput.Write("\n");
		int i;
		for (i=aDepth;i>0;i--)
		{
			aOutput.Write("  ");
		}
	}
};


