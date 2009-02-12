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

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SubListCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

/**
 *
 *  
 */
public class ExplodeTag extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer out = new ConsPointer();
        out.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());
        LispError.checkIsString(aEnvironment, aStackTop, out, 1);

        String str = out.getCons().string();
        int strInd = 0;
        strInd++;
        if (str.charAt(strInd) != '<')
        {
            getResult(aEnvironment, aStackTop).setCons(out.getCons());
            return;
        }
        LispError.checkArgument(aEnvironment, aStackTop, str.charAt(strInd) == '<', 1);
        strInd++;
        String type = "\"Open\"";

        if (str.charAt(strInd) == '/')
        {
            type = "\"Close\"";
            strInd++;
        }
        String tag = new String();

        tag = tag + "\"";
        while (MathPiperTokenizer.isAlpha(str.charAt(strInd)))
        {
            char c = str.charAt(strInd);
            strInd++;
            if (c >= 'a' && c <= 'z')
            {
                c = (char) (c + ('A' - 'a'));
            }
            tag = tag + c;
        }
        tag = tag + "\"";

        Cons info = null;

        while (str.charAt(strInd) == ' ')
        {
            strInd++;
        }
        while (str.charAt(strInd) != '>' && str.charAt(strInd) != '/')
        {
            String name = new String();
            name = name + "\"";

            while (MathPiperTokenizer.isAlpha(str.charAt(strInd)))
            {
                char c = str.charAt(strInd);
                strInd++;
                if (c >= 'a' && c <= 'z')
                {
                    c = (char) (c + ('A' - 'a'));
                }
                name = name + c;
            }
            name = name + "\"";
            LispError.checkArgument(aEnvironment, aStackTop, str.charAt(strInd) == '=', 1);
            strInd++;
            LispError.checkArgument(aEnvironment, aStackTop, str.charAt(strInd) == '\"', 1);
            String value = new String();

            value = value + (str.charAt(strInd));
            strInd++;
            while (str.charAt(strInd) != '\"')
            {
                value = value + (str.charAt(strInd));
                strInd++;
            }
            value = value + (str.charAt(strInd));
            strInd++;

            //printf("[%s], [%s]\n",name.String(),value.String());
            {
                Cons ls = AtomCons.getInstance(aEnvironment, "List");
                Cons nm = AtomCons.getInstance(aEnvironment, name);
                Cons vl = AtomCons.getInstance(aEnvironment, value);
                nm.rest().setCons(vl);
                ls.rest().setCons(nm);
                Cons newinfo = SubListCons.getInstance(ls);
                newinfo.rest().setCons(info);
                info = newinfo;
            }
            while (str.charAt(strInd) == ' ')
            {
                strInd++;

            //printf("End is %c\n",str[0]);
            }
        }
        if (str.charAt(strInd) == '/')
        {
            type = "\"OpenClose\"";
            strInd++;
            while (str.charAt(strInd) == ' ')
            {
                strInd++;
            }
        }
        {
            Cons ls = AtomCons.getInstance(aEnvironment, "List");
            ls.rest().setCons(info);
            info = SubListCons.getInstance(ls);
        }

        Cons xm = AtomCons.getInstance(aEnvironment, "XmlTag");
        Cons tg = AtomCons.getInstance(aEnvironment, tag);
        Cons tp = AtomCons.getInstance(aEnvironment, type);
        info.rest().setCons(tp);
        tg.rest().setCons(info);
        xm.rest().setCons(tg);
        getResult(aEnvironment, aStackTop).setCons(SubListCons.getInstance(xm));

    }
}
