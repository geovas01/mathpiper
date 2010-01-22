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
 */
//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import org.mathpiper.ui.gui.worksheets.*;
import java.awt.*;

public class SBoxBuilder {

    SBox[] stack = new SBox[1024];
    int stackDepth = 0;

    static int fontForSize(int aSize) {

        if (aSize > 3) {
            aSize = 3;
        }

        if (aSize < 0) {
            aSize = 0;
        }

        switch (aSize) {

            case 0:
                return 6;

            case 1:
                return 8;

            case 2:
                return 12;

            case 3:
                return 16;

            default:
                return 16;
        }
    }

    public SBox pop() {
        stackDepth--;

        SBox result = stack[stackDepth];

        return result;
    }

    void push(SBox aSbox) {
        stack[stackDepth] = aSbox;
        stackDepth++;
    }

    public int stackDepth() {

        return stackDepth;
    }

    public void process(String aType) {

        if (aType.equals("=") || aType.equals("\\neq") || aType.equals("+") || aType.equals(",") || aType.equals("\\wedge") || aType.equals("\\vee") || aType.equals("<") || aType.equals(">") || aType.equals("<=") || aType.equals(">=")) {

            SBox right = pop();
            SBox left = pop();
            push(new SBoxInfixOperator(left, new SBoxSymbolName(aType), right));
        } else if (aType.equals("/")) {

            SBox denom = pop();
            SBox numer = pop();
            push(new SBoxDivisor(numer, denom));
        } else if (aType.equals("-/2")) {

            SBox right = pop();
            SBox left = pop();
            push(new SBoxInfixOperator(left, new SBoxSymbolName("-"), right));
        } else if (aType.equals("-/1")) {

            SBox right = pop();
            push(new SBoxPrefixOperator(new SBoxSymbolName("-"), right));
        } else if (aType.equals("~")) {

            SBox right = pop();
            push(new SBoxPrefixOperator(new SBoxSymbolName("~"), right));
        } else if (aType.equals("!")) {

            SBox left = pop();
            push(new SBoxPrefixOperator(left, new SBoxSymbolName("!")));
        } else if (aType.equals("*")) {

            SBox right = pop();
            SBox left = pop();
            push(new SBoxInfixOperator(left, new SBoxSymbolName(""), right));
        } else if (aType.equals("[func]")) {

            SBox right = pop();
            SBox left = pop();
            push(new SBoxPrefixOperator(left, right));
        } else if (aType.equals("^")) {

            SBox right = pop();
            SBox left = pop();
            boolean appendToExisting = false;

            if (left instanceof SBoxSubSuperfix) {

                SBoxSubSuperfix sbox = (SBoxSubSuperfix) left;

                if (!sbox.hasSuperfix()) {
                    appendToExisting = true;
                }
            }

            if (appendToExisting) {

                SBoxSubSuperfix sbox = (SBoxSubSuperfix) left;
                sbox.setSuperfix(right);
                push(sbox);
            } else {
                push(new SBoxSubSuperfix(left, right, null));
            }
        } else if (aType.equals("_")) {

            SBox right = pop();
            SBox left = pop();

            if (left instanceof SBoxSubSuperfix) {

                SBoxSubSuperfix sbox = (SBoxSubSuperfix) left;
                sbox.setSubfix(right);
                push(sbox);
            } else {
                push(new SBoxSubSuperfix(left, null, right));
            }
        } else if (aType.equals("[sqrt]")) {

            SBox left = pop();
            push(new SBoxSquareRoot(left));
        } else if (aType.equals("[sum]")) {
            push(new SBoxSum());
        } else if (aType.equals("[int]")) {
            push(new SBoxInt());
        } else if (aType.equals("[roundBracket]")) {

            SBox left = pop();
            push(new SBoxBracket(left, "(", ")"));
        } else if (aType.equals("[squareBracket]")) {

            SBox left = pop();
            push(new SBoxBracket(left, "[", "]"));
        } else if (aType.equals("[accoBracket]")) {

            SBox left = pop();
            push(new SBoxBracket(left, "{", "}"));
        } else if (aType.equals("[grid]")) {

            SBox widthBox = pop();
            SBox heightBox = pop();
            int width = Integer.parseInt(((SBoxSymbolName) widthBox).iSymbol);
            int height = Integer.parseInt(((SBoxSymbolName) heightBox).iSymbol);
            SBoxGrid grid = new SBoxGrid(width, height);
            int i;
            int j;

            for (j = height - 1; j >= 0; j--) {

                for (i = width - 1; i >= 0; i--) {

                    SBox value = pop();
                    grid.SetSBox(i, j, value);
                }
            }

            push(grid);
        } else {
            push(new SBoxSymbolName(aType));
        }
    }

    public void processLiteral(String aExpression) {
        push(new SBoxSymbolName(aExpression));
    }
}//end class

