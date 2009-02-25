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
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    //Process each package directory.************************************************************************
                    File packageDirectoryFile = packagesDirectory[x];
                    String packageDirectoryFileName = packageDirectoryFile.getName();
                    System.out.println(packageDirectoryFileName);

                    //Create package directory
                    String dirNameRep = packageDirectoryFileName;
                    String newPackageName = dirNameRep + ".rep";
                    String newPackagePath = outputDirectory + newPackageName;
                    File newPackageFile = new File(newPackagePath);
                    Boolean directoryCreated = newPackageFile.mkdir();

                    //mpi file.
                    BufferedWriter mpiFileOut = null;
                    File newMPIFile = new File(newPackagePath + "/" + "code.mpi");
                    newMPIFile.createNewFile();
                    mpiFileOut = new BufferedWriter(new FileWriter(newMPIFile));

                    //mpi.def file
                    BufferedWriter mpiDefFileOut = null;
                    File newMPIDefFile = new File(newPackagePath + "/" + "code.mpi.def");
                    newMPIDefFile.createNewFile();
                    mpiDefFileOut = new BufferedWriter(new FileWriter(newMPIDefFile));


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


                        // }//note:tk:remove.
                        String classNameUpper = null;

                        for (int x2 = 0; x2 < packageDirectoryContentsArray.length; x2++) {
                            //Process each script in a .rep directory.
                            File scriptFile = packageDirectoryContentsArray[x2];
                            System.out.println("    " + scriptFile.getName());

                            if (scriptFile.getName().endsWith(".mrw")) {


                                List<Fold> folds = scanMRWFile(scriptFile);

                                for (Fold fold : folds) {

                                    String foldType = fold.getType();
                                    if (foldType.equalsIgnoreCase("%mathpiper")) {

                                        mpiFileOut.write(fold.getContents());


                                        if (fold.getAttributes().containsKey("def")) {
                                            String defAttribute = (String) fold.getAttributes().get("def");
                                            if (defAttribute.equalsIgnoreCase("true")) {
                                                mpiDefFileOut.write(scriptFile.getName().replace(".mrw", ""));
                                                mpiDefFileOut.write("\n");
                                            }
                                        }

                                    } else if (foldType.equalsIgnoreCase("%html")) {
                                    }




                                }//end subpackage for.

                            } else {
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
                                    System.out.println("        " + scriptFile2.getName());
                                }
                            }//end else.

                        }//end package for.




                    }//end if. 

                    if (mpiFileOut != null) {
                        mpiFileOut.close();
                        mpiDefFileOut.write("}\n");
                        mpiDefFileOut.close();
                    }

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
        File newScriptsDirectory = new File(outputDirectory);
        Boolean directoryCreated = newScriptsDirectory.mkdir();

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

    List scanMRWFile(File mrwFile) {
        List<Fold> folds = new ArrayList();
        StringBuilder foldContents = new StringBuilder();
        String foldHeader = "";
        boolean inFold = false;

        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(mrwFile);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            //Read File Line By Line
            while ((line = br.readLine()) != null) {

                //System.out.println(line);

                if (line.startsWith("%/")) {
                    Fold fold = new Fold(foldHeader, foldContents.toString());
                    foldContents.delete(0, foldContents.length());
                    folds.add(fold);

                } else if (line.startsWith("%")) {
                    foldHeader = line;
                    inFold = true;
                } else if (inFold == true) {
                    foldContents.append(line);
                    foldContents.append("\n");

                }
            }//end while.


            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return folds;

    }//end.

    class Fold {

        private String type;
        private String contents;
        private Map<String, String> attributes = new HashMap();

        public Fold(String header, String contents) {
            scanHeader(header);

            this.contents = contents;
        }//end inner class.

        private void scanHeader(String header) {
            String[] headerParts = header.trim().split(",");

            type = headerParts[0];

            for (int x = 1; x < headerParts.length; x++) {
                String[] headerPart = headerParts[x].split("=");
                String attributeName = headerPart[0];
                String attributeValue = headerPart[1].replace("\"", "");
                attributes.put(attributeName, attributeValue);
            }

        }//end method.

        public Map getAttributes() {
            return attributes;
        }

        public String getContents() {
            return contents;
        }

        public String getType() {
            return type;
        }
    }//end inner class.
}//end class.
