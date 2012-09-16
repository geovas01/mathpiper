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
package org.mathpiper.lisp.printers;


import org.mathpiper.lisp.*;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.cons.Cons;

public class LispPrinter {

    String newLineCharacter = "\n";
    String spaceCharacter = "  ";

    public LispPrinter() {

    }

    //private List<Cons> visitedLists = new ArrayList<Cons>();

    public void print(int aStackTop, Cons aExpression, MathPiperOutputStream aOutput, Environment aEnvironment) throws Throwable {
        printExpression(aExpression, aOutput, aEnvironment, 0);

        //visitedLists.clear();
    }


    public void rememberLastChar(char aChar) {
    }


    void printExpression(Cons aExpression, MathPiperOutputStream aOutput, Environment aEnvironment, int aDepth /* =0 */) throws Throwable {

        Cons consWalker = aExpression;
        int item = 0;

        if(consWalker == null)
        {
            aOutput.write("<null>");
        }

        while (consWalker != null) {

            if (consWalker.car() instanceof String) {
                String atom = (String) consWalker.car();
                
                aOutput.write(atom);


            } // else print "(", print sublist, and print ")"
            else if (consWalker.car() instanceof Cons) {
                if (item != 0) {
                    indent(aOutput, aDepth + 1);
                }

                /*
                Cons atomCons = (Cons) consWalker.getCons();
                if (visitedLists.contains(atomCons)) {
                aOutput.write("(CYCLE_LIST)");

                } else {
                visitedLists.add(atomCons);*/

                if (item != 0) {
                    indent(aOutput, aDepth + 1);
                }
                aOutput.write("(");
                printExpression(((Cons) consWalker.car()), aOutput, aEnvironment, aDepth + 1);
                aOutput.write(")");
                item = 0;
                //}


            } else {
                aOutput.write("[BuiltinObject]");
            }

            consWalker = (consWalker.cdr()); // print rest element
            
            if(consWalker != null)
            {
               aOutput.putChar(' ');
            }

            item++;

        }//end while.

    }//end method.


    void indent(MathPiperOutputStream aOutput, int aDepth) throws Throwable {
        aOutput.write(newLineCharacter);
        int i;
        for (i = aDepth; i > 0; i--) {
            aOutput.write(spaceCharacter);
        }
    }

};
