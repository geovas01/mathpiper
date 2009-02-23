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
package org.mathpiper.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 *
 * @author tkosan
 */
public class Build {

    private java.io.File scriptsDir;
    private java.io.FileWriter packagesFile;

    public void compileScripts(String scriptsDirectory, String outputDirectory) {

        StringBuilder mainScriptsClassBuffer = new StringBuilder();


        mainScriptsClassBuffer.append("static{\n");


        try {

            packagesFile = new java.io.FileWriter("packages.mpi");


            scriptsDir = new java.io.File(scriptsDirectory);

            if (scriptsDir.exists()) {
                java.io.File[] packagesDirectory = scriptsDir.listFiles(new java.io.FilenameFilter() {

                    public boolean accept(java.io.File file, String name) {
                        if (name.endsWith(".rep") || name.startsWith(".")) {
                            return (false);
                        } else {
                            return (true);
                        }
                    }
                });

                Arrays.sort(packagesDirectory);



                String output;
                for (int x = 0; x < packagesDirectory.length; x++) {
                    //Process each package directory.
                    File packageDirectoryFile = packagesDirectory[x];
                    String packageDirectoryFileName = packageDirectoryFile.getName();
                    System.out.println(packageDirectoryFileName);

                    //Create package directory
                    String dirNameRep = packageDirectoryFileName;
                    String newPackageName = dirNameRep + ".rep";
                    String newPackagePath = outputDirectory + newPackageName;
                    File newPackageFile = new File(newPackagePath);
                    Boolean directoryCreated = newPackageFile.mkdir();


                    //Place class files in package dir
                    if (packageDirectoryFile.exists()) {
                        java.io.File[] packageDirectoryContentsArray = packageDirectoryFile.listFiles(new java.io.FilenameFilter() {

                            public boolean accept(java.io.File file, String name) {
                                if (name.startsWith(".")) {
                                    return (false);
                                } else {
                                    return (true);
                                }
                            }
                        });

                        Arrays.sort(packageDirectoryContentsArray);


                        StringBuilder scriptClassBuffer = new StringBuilder();
                        // }//note:tk:remove.
                        String classNameUpper = null;

                        for (int x2 = 0; x2 < packageDirectoryContentsArray.length; x2++) {
                            scriptClassBuffer.delete(0, scriptClassBuffer.length());
                            //Process each script in a .rep directory.
                            File scriptFile = packageDirectoryContentsArray[x2];
                            System.out.println("    " + scriptFile.getName());

                            if (!scriptFile.getName().endsWith(".mrw")) {
                                java.io.File[] packageSubDirectoryContentsArray = scriptFile.listFiles(new java.io.FilenameFilter() {

                                    public boolean accept(java.io.File file, String name) {
                                        if (name.startsWith(".")) {
                                            return (false);
                                        } else {
                                            return (true);
                                        }
                                    }
                                });

                                Arrays.sort(packageSubDirectoryContentsArray);



                                for (int x3 = 0; x3 < packageSubDirectoryContentsArray.length; x3++) {

                                    //Process each script in a .rep directory.
                                    File scriptFile2 = packageSubDirectoryContentsArray[x3];
                                    System.out.println("        "+ scriptFile2.getName());

                                }//end subpackage for.

                            }//end subpackage if.

                        }//end for.




                    }//end if. 



                }//end for.

            } else {
                System.out.println("\nDirectory " + scriptsDirectory + "does not exist.\n");
            }

            packagesFile.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }//end method.

    public static void main(String[] args) {

        String scriptsDirectory;

        if (args.length > 0) {
            scriptsDirectory = args[0];
        } else {
            scriptsDirectory = "/home/tkosan/NetBeansProjects/mathpiper/src/org/mathpiper/scripts/";
        }

        String outputDirectory = "/home/tkosan/NetBeansProjects/scripts/";
        //String outputDirectory = "/home/tkosan/temp/mathpiper/org/mathpiper/scripts/";

        Build scripts = new Build();
        scripts.compileScripts(scriptsDirectory, outputDirectory);

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


