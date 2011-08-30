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
package org.mathpiper.interpreters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 * This class consists exclusively of static factory methods which return MathPiper interpreter instances.
 * These static methods are the only way to obtain instances of MathPiper interpeters.
 */
public class Interpreters {

    private Interpreters() {
    }

    /**
     * Instantiates a new synchronous {@link Interpreter} and returns it.  The interpreter contains
     * its own namespace and it runs in the client's thread.
     *
     * @return a new synchronous interpreter
     */
    public static Interpreter newSynchronousInterpreter() {
        return SynchronousInterpreter.newInstance();
    }

    /**
     * Instantiates a new synchronous {@link Interpreter} and returns it.  The interpreter contains
     * its own namespace and it runs in the client's thread.  The docBase argument is used
     * to specify a path which contains the core MathPiper scripts.  A typical case where a
     * docBase path needs to be  used is with Applets.  The following code shows the document
     * base being obtained inside of an applet and then being used to obtain a new interpreter
     * instance which uses the docBase path to locate the core MathPiper scripts:
     * <p>
     * {@code String docBase = getDocumentBase().toString();}<br />
     *  {@code mathPiperInterpreter = org.mathpiper.interpreters.Interpreters.newSynchronousInterpreter(docBase);}
     *
     * @param  docBase path which contains core MathPiper scripts
     * @return a new synchronous interpreter
     */
    public static Interpreter newSynchronousInterpreter(String docBase) {
        return SynchronousInterpreter.newInstance(docBase);
    }

     /**
     * Returns a synchronous {@link Interpreter} singleton.  All users of the interpreter singleton share
      * the same namespace and it runs in the client's thread.
     *
     * @return a synchronous interpreter singleton
     */
    public static Interpreter getSynchronousInterpreter() {
        return SynchronousInterpreter.getInstance();
    }

     /**
     * Returns a synchronous {@link Interpreter} singleton.  All users of the interpreter singleton share
      * the same namespace and it runs in the client's thread.  The docBase argument is used
     * to specify a path which contains the core MathPiper scripts.  A typical case where a
     * docBase path needs to be  used is with Applets.  The following code shows the document
     * base being obtained inside of an applet and then being used to obtain a new interpreter
     * instance which uses the docBase path to locate the core MathPiper scripts:
     * <p>
     * {@code String docBase = getDocumentBase().toString();}<br />
     * {@code mathPiperInterpreter = org.mathpiper.interpreters.Interpreters.newSynchronousInterpreter(docBase);}
     *
     * @param docBase path which contains core MathPiper scripts
     * @return a synchronous interpreter singleton
     */
    public static Interpreter getSynchronousInterpreter(String docBase) {
        return SynchronousInterpreter.getInstance(docBase);
    }



    /**
     * Instantiates a new asynchronous {@link Interpreter} and returns it.  The interpreter contains
     * its own namespace and it runs in its own thread.
     *
     * @return a new asynchronous interpreter
     */
    public static Interpreter newAsynchronousInterpreter() {
        return AsynchronousInterpreter.newInstance();
    }

     /**
     * Instantiates a new asynchronous {@link Interpreter} and returns it.  The interpreter contains
     * its own namespace and it runs in its own thread.  The docBase argument is used
     * to specify a path which contains the core MathPiper scripts.  A typical case where a
     * docBase path needs to be  used is with Applets.  The following code shows the document
     * base being obtained inside of an applet and then being used to obtain a new interpreter
     * instance which uses the docBase path to locate the core MathPiper scripts:
     * <p>
     * {@code String docBase = getDocumentBase().toString();}<br />
     *  {@code mathPiperInterpreter = org.mathpiper.interpreters.Interpreters.newAynchronousInterpreter(docBase);}
     *
     * @param  docBase path which contains core MathPiper scripts
     * @return a new aynchronous interpreter
     */
    public static Interpreter newAsynchronousInterpreter(String docBase) {
        return AsynchronousInterpreter.newInstance(docBase);
    }

     /**
     * Returns an asynchronous {@link Interpreter} singleton.  All users of the interpreter singleton share
      * the same namespace and it runs in its own thread.  The interpreter singleton is the same one which
      * is used by the synchronous interpreter.
     *
     * @return an asynchronous interpreter singleton
     */
    public static Interpreter getAsynchronousInterpreter() {
        return AsynchronousInterpreter.getInstance();
    }

     /**
     * Returns an asynchronous {@link Interpreter} singleton.  All users of the interpreter singleton share
      * the same namespace and it runs in its own thread.    The interpreter singleton is the same one which
      * is used by the synchronous interpreter.  The docBase argument is used
     * to specify a path which contains the core MathPiper scripts.  A typical case where a
     * docBase path needs to be  used is with Applets.  The following code shows the document
     * base being obtained inside of an applet and then being used to obtain a new interpreter
     * instance which uses the docBase path to locate the core MathPiper scripts:
     * <p>
     * {@code String docBase = getDocumentBase().toString();}<br />
     * {@code mathPiperInterpreter = org.mathpiper.interpreters.Interpreters.newSynchronousInterpreter(docBase);}
     *
     * @param docBase path which contains core MathPiper scripts
     * @return an asynchronous interpreter singleton
     */
    public static Interpreter getAsynchronousInterpreter(String docBase) {
        return AsynchronousInterpreter.getInstance(docBase);
    }



    //================================================
    public static synchronized List addOptionalFunctions(Environment aEnvironment, String functionsPath) {

        List failList = new ArrayList();

        try {
            String[] listing = getResourceListing(BuiltinFunction.class, functionsPath);
            for (int x = 0; x < listing.length; x++) {

                String fileName = listing[x];

                if (!fileName.toLowerCase().endsWith(".class")) {
                    continue;
                }


                fileName = fileName.substring(0, fileName.length() - 6);
                fileName = functionsPath + fileName;
                fileName = fileName.replace("/", ".");

                //System.out.println(fileName);

                try {
                    Class functionClass = Class.forName(fileName, true, BuiltinFunction.class.getClassLoader());

                    //System.out.println("CLASS :" + functionClass.toString() + "   CLASSLOADER: " + BuiltinFunction.class.getClassLoader().toString());

                    Object functionObject = functionClass.newInstance();
                    if (functionObject instanceof BuiltinFunction) {
                        BuiltinFunction function = (BuiltinFunction) functionObject;
                        function.plugIn(aEnvironment);
                    }//end if.
                } catch (ClassNotFoundException cnfe) {
                    System.out.println("Class not found: " + fileName);
                } catch (InstantiationException ie) {
                    System.out.println("Can not instantiate class: " + fileName);
                } catch (IllegalAccessException iae) {
                    System.out.println("Illegal access of class: " + fileName);
                } catch (NoClassDefFoundError ncdfe) {
                    //System.out.println("Class not found: " + fileName);
                    failList.add(fileName);
                }

            }//end for.



        } catch (Exception e) {
            e.printStackTrace();
        }


        return failList;
    }//end method.

    private static String[] getResourceListing(Class loadedClass, String path) throws URISyntaxException, IOException {

        InputStream inputStream = loadedClass.getClassLoader().getResourceAsStream(path + "plugins_list.txt");

        if (inputStream == null) {
            return null;
        }

        BufferedReader pluginListFileReader = new BufferedReader(new InputStreamReader(inputStream));



        java.util.Set<String> result = new HashSet<String>();

        String name = null;
        while ((name = pluginListFileReader.readLine()) != null) {
            name = name.trim();
            result.add(name);
        }
        return result.toArray(new String[result.size()]);

    }//end method.

    public static void main(String[] args) {

        Interpreter interpreter = getSynchronousInterpreter();

        Environment environment = interpreter.getEnvironment();

        addOptionalFunctions(environment,"org/mathpiper/builtin/functions/optional/");

        interpreter.evaluate("ViewGraphicConsole();");

    }


}//end class.
