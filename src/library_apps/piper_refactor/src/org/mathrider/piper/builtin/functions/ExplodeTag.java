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

package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.SubList;
import org.mathrider.piper.lisp.parsers.Tokenizer;

/**
 *
 *  
 */
public class ExplodeTag extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer out = new ConsPointer();
        out.set(argument(aEnvironment, aStackTop, 1).get());
        LispError.checkIsStringCore(aEnvironment, aStackTop, out, 1);

        String str = out.get().string();
        int strInd = 0;
        strInd++;
        if (str.charAt(strInd) != '<')
        {
            result(aEnvironment, aStackTop).set(out.get());
            return;
        }
        LispError.checkArgumentCore(aEnvironment, aStackTop, str.charAt(strInd) == '<', 1);
        strInd++;
        String type = "\"Open\"";

        if (str.charAt(strInd) == '/')
        {
            type = "\"Close\"";
            strInd++;
        }
        String tag = new String();

        tag = tag + "\"";
        while (Tokenizer.isAlpha(str.charAt(strInd)))
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

            while (Tokenizer.isAlpha(str.charAt(strInd)))
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
            LispError.checkArgumentCore(aEnvironment, aStackTop, str.charAt(strInd) == '=', 1);
            strInd++;
            LispError.checkArgumentCore(aEnvironment, aStackTop, str.charAt(strInd) == '\"', 1);
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
                Cons ls = Atom.getInstance(aEnvironment, "List");
                Cons nm = Atom.getInstance(aEnvironment, name);
                Cons vl = Atom.getInstance(aEnvironment, value);
                nm.cdr().set(vl);
                ls.cdr().set(nm);
                Cons newinfo = SubList.getInstance(ls);
                newinfo.cdr().set(info);
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
            Cons ls = Atom.getInstance(aEnvironment, "List");
            ls.cdr().set(info);
            info = SubList.getInstance(ls);
        }

        Cons xm = Atom.getInstance(aEnvironment, "XmlTag");
        Cons tg = Atom.getInstance(aEnvironment, tag);
        Cons tp = Atom.getInstance(aEnvironment, type);
        info.cdr().set(tp);
        tg.cdr().set(info);
        xm.cdr().set(tg);
        result(aEnvironment, aStackTop).set(SubList.getInstance(xm));

    }
}
