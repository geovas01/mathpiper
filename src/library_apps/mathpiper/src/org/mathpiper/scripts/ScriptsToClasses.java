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

package org.mathpiper.scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author tkosan
 */
public class ScriptsToClasses {

    private java.io.File scriptsDir;
    private java.io.FileWriter logFile;

    public void classify(String directory) {
        StringBuilder s = new StringBuilder();
        s.append("package org.mathpiper.scripts;\n");

        s.append("import java.io.ByteArrayInputStream;\n");
        s.append("import java.util.HashMap;\n");
        s.append("import java.util.Map;\n");
        s.append("import java.io.InputStream;\n");
        s.append("\n");
        s.append("public class Scripts\n");
        s.append("{\n");
        s.append("\n");
        s.append("private static Map scriptsMap = new HashMap();\n");
        s.append("\n");
        s.append("static{\n");


        try {

            logFile = new java.io.FileWriter("scripts_to_classes.log");


            scriptsDir = new java.io.File(directory);

            if (scriptsDir.exists()) {
                java.io.File[] packagesDirectory = scriptsDir.listFiles(new java.io.FilenameFilter() {

                    public boolean accept(java.io.File file, String name) {
                        if (name.endsWith(".rep")) {
                            return (true);
                        } else {
                            return (false);
                        }
                    }
                });


                String output;
                //Execute each .mpt file in the specified directory.
                for (int x = 0; x < packagesDirectory.length; x++) {
                    File packageDirectoryFile = packagesDirectory[x];
                    String packageDirectoryFileName = packageDirectoryFile.getName();
                    System.out.println(packageDirectoryFileName);
                    StringBuilder f = new StringBuilder();

                    f.append("package org.mathpiper.scripts;\n");
                    f.append("\n");
                    f.append("public class ");
                    String dirNameRep = packageDirectoryFile.getName();
                    String classNameLower = dirNameRep.substring(0, dirNameRep.indexOf("."));
                    String firstChar = classNameLower.substring(0, 1);
                    firstChar = firstChar.toUpperCase();
                    String classNameUpper = firstChar + classNameLower.substring(1);

                    f.append(classNameUpper + "{\n");



                    if (packageDirectoryFile.exists()) {
                        java.io.File[] packageDirectory = packageDirectoryFile.listFiles(new java.io.FilenameFilter() {

                            public boolean accept(java.io.File file, String name) {
                                if (name.endsWith(".mpi") || name.endsWith(".def")) {
                                    return (true);
                                } else {
                                    return (false);
                                }
                            }
                        });



                        //Process a package.
                        for (int x2 = 0; x2 < packageDirectory.length; x2++) {
                            File scriptFile = packageDirectory[x2];
                            System.out.println("   " + scriptFile.getName());
                            String sc = readFileAsString(scriptFile);
                             //sc = sc.replaceAll("//(.)*\n", ""); //Single line comments.
                            sc = sc.replaceAll("\r", "");
                           // sc = sc.replaceAll("(/\*[\d\D]*?\*/)|(\/\*(\s*|.*?)*\*\/)|(\/\/.*)|(/\\*[\\d\\D]*?\\*/)|([\r\n ]*//[^\r\n]*)+",""); //Multiline comments on same line and single line comments.
                            sc = sc.replaceAll("\n", "");
                            sc = sc.replaceAll("\"", "\\\\\"");
                            
                           

                            //System.out.println(sc);

                            f.append("static final String ");
                            String scriptFileName = scriptFile.getName();
                            String scriptFileNameOnly = scriptFileName.substring(0, scriptFileName.indexOf("."));
                            if (scriptFileName.endsWith(".def")) {
                                scriptFileNameOnly = scriptFileNameOnly + "_def";
                            }


                            f.append(scriptFileNameOnly);
                            s.append("scriptsMap.put(\"" + packageDirectoryFileName + "/" + scriptFileName + "\"," + classNameUpper + "." + scriptFileNameOnly + ");\n");
                            f.append(" = \"");
                            f.append(sc);
                            f.append(" \";\n");



                           


                        }//end for.
                         f.append("}\n");
                         
                         writeStringToFile(f.toString(),"/home/tkosan/temp/mathpiper/org/mathpiper/scripts/" + classNameUpper + ".java");
                          int d = 0;
                    }//end if.

                }//end for.
                s.append("}\n}\n");

                writeStringToFile(s.toString(),"/home/tkosan/temp/mathpiper/org/mathpiper/scripts/Scripts.java");

                System.out.println(s.toString());

            } else {
                System.out.println("\nDirectory " + directory + "does not exist.\n");
            }

            logFile.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }//end method.

    public static void main(String[] args) {
        /*Note: This program currently only works if it is executed in the current directory
        because MathPiper cannot handle paths properly yet.

        Execute with a command line similar to the following:

        java -cp .;../dist/piper.jar org.mathpiper.tests.MathPiperTest
         */

        String directory;

        if (args.length > 0) {
            directory = args[0];
        } else {
            directory = "/home/tkosan/NetBeansProjects/mathpiper/scripts";
        }


        ScriptsToClasses scripts = new ScriptsToClasses();
        scripts.classify(directory);

    }//end main

    /** @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
     */
    private static String readFileAsString(File file) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(file));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }//end method.

    private static void writeStringToFile(String string, String fileName) throws IOException {
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));


            //someText.replaceAll("\n", System.getProperty("line.separator"));

            out.write(string);
            out.close();

        } catch (IOException e) {

            System.out.println("Exception ");

        }
    }//end method.
}//end class.



