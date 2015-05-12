package org.mathpiper.interpreters;

import java.util.ArrayList;
import java.util.List;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;

public class Utilities {

    public static Object associationListGet(Environment aEnvironment, int aStackTop, String key, Cons listCons) throws Throwable {

        Cons keyAtom = AtomCons.getInstance(aEnvironment, aStackTop, Utility.toMathPiperString(aEnvironment, aStackTop, key));

        while (listCons != null) {
            if (listCons.car() instanceof Cons) {
                Cons sublist = (Cons) listCons.car();
                if (sublist != null) {
                    sublist = sublist.cdr();

                    if (Utility.equals(aEnvironment, aStackTop, keyAtom, sublist)) {
                        Object object = listCons.cdr().car();
                        return object;
                    }//end if.

                }//end if.

            }//end if.

            listCons = listCons.cdr();

        }//end if.

        return null;
    }//end method.

    
    
    public static List associationListKeys(Environment aEnvironment, int aStackTop, Cons listCons) throws Throwable {

        List keysList = new ArrayList();

        Cons listItem = ((Cons) listCons.car()).cdr();

        while (listItem != null) {
            Object object = Cons.cadar(listItem);

            keysList.add(object);

            listItem = listItem.cdr();

        }

        return keysList;
    }//end method.




    public static List associationListValues(Environment aEnvironment, int aStackTop, Cons listCons) throws Throwable {

        List keysList = new ArrayList();

        Cons listItem = ((Cons) listCons.car()).cdr();

        while (listItem != null) {
            Object object = Cons.caddar(listItem);

            keysList.add(object);

            listItem = listItem.cdr();
        }

        return keysList;
    }//end method.
    
    
    public static String toJavaString(String aOriginal) {

        if (aOriginal == null) {
                return null;
        }

        // If there are not quotes on both ends of the string then return
        // without any changes.
        if (aOriginal.startsWith("\"") && aOriginal.endsWith("\"")) {
                aOriginal = aOriginal.substring(1, aOriginal.length());
                aOriginal = aOriginal.substring(0, aOriginal.length() - 1);
        }// end if.

        return aOriginal;
    }// end method.

} // end class.


