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
 /*


                        scriptClassBuffer.append("package org.mathpiper.scripts." + newPackageName + ";\n");
                        scriptClassBuffer.append("\n\n//*** This file was generated by the ScriptsToClasses class.  Do not edit it. ***\n\n");
                        scriptClassBuffer.append("public class ");
                        String rawScriptName = scriptFile.getName();
                        String classNameLower = rawScriptName.substring(0, rawScriptName.indexOf("."));
                        String firstChar = classNameLower.substring(0, 1);
                        firstChar = firstChar.toUpperCase();
                        classNameUpper = firstChar + classNameLower.substring(1);


                        //String sc = readFileAsString(scriptFile);

                        byte[] bytes = getBytesFromFile(scriptFile);

                        String unicodeBytes = convertBytesToUnicodeString(bytes);

                        //                            System.out.println(unicodeBytes);

                        //                            getUnicodeStringStream(unicodeBytes);




                        String scriptFileName = scriptFile.getName();
                        String scriptFileNameOnly = scriptFileName.substring(0, scriptFileName.indexOf("."));
                        if (scriptFileName.endsWith(".def")) {
                        scriptFileNameOnly = scriptFileNameOnly + "_def";
                        classNameUpper = classNameUpper + "Def";
                        }
                        firstChar = scriptFileNameOnly.substring(0, 1);
                        firstChar = firstChar.toLowerCase();
                        scriptFileNameOnly = firstChar + scriptFileNameOnly.substring(1);
                        scriptClassBuffer.append(classNameUpper + "{\n");
                        scriptClassBuffer.append("public static final String ");
                        scriptClassBuffer.append(scriptFileNameOnly);
                        String packagePath = "org.mathpiper.scripts." + newPackageName + ".";
                        mainScriptsClassBuffer.append("scriptsMap.put(\"" + packageDirectoryFileName + "/" + scriptFileName + "\"," + packagePath + classNameUpper + "." + scriptFileNameOnly + ");\n");
                        scriptClassBuffer.append(" = ");
                        scriptClassBuffer.append(unicodeBytes);
                        scriptClassBuffer.append("\n");
                        scriptClassBuffer.append("}\n");
                        String pp = newPackageFile.getPath();
                        writeStringToFile(scriptClassBuffer.toString(), newPackageFile.getPath() + File.separator + classNameUpper + ".java");
                         */
                        }//end for.




                    }//end if. 



                }//end for.
                /*



            mainScriptsClassBuffer.append("}\n\n\n");
            mainScriptsClassBuffer.append("public static java.io.InputStream getScriptStream(String scriptName)\n");
            mainScriptsClassBuffer.append("{\n");
            mainScriptsClassBuffer.append("String unicodeString = (String) scriptsMap.get(scriptName);\n");
            mainScriptsClassBuffer.append("char[] chars = unicodeString.toCharArray();\n");
            mainScriptsClassBuffer.append("byte[] bytes = new byte[chars.length*2];\n");
            mainScriptsClassBuffer.append("byte b0,b1 = 0;\n");
            mainScriptsClassBuffer.append("int bytesIndex = 0;\n");
            mainScriptsClassBuffer.append("for(int x=0; x<chars.length;x++)\n");
            mainScriptsClassBuffer.append("{\n");
            mainScriptsClassBuffer.append("     bytes[bytesIndex++] = (byte) (chars[x] >> 8);\n");
            mainScriptsClassBuffer.append("    bytes[bytesIndex++] = (byte) (chars[x] & 0xff);\n");
            mainScriptsClassBuffer.append("}\n");
            mainScriptsClassBuffer.append("ByteArrayInputStream bytesStream = new ByteArrayInputStream(bytes);\n");
            mainScriptsClassBuffer.append("return bytesStream;\n");
            mainScriptsClassBuffer.append("}//end method.\n");
            mainScriptsClassBuffer.append("}\n");


            writeStringToFile(mainScriptsClassBuffer.toString(), outputDirectory + "Scripts.java");

            System.out.println(mainScriptsClassBuffer.toString());
             */
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

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    private static String printByteArray(byte[] bytes) {
        /*java_code = []
        java_code.append("{")
        for byte in bytes:
        if byte > 127:
        byte = (byte - 128) + -128
        java_code.append(str(byte))
        java_code.append(",")
        del java_code[-1]
        java_code.append("}")*/


        StringBuilder b = new StringBuilder();
        // b.append("{");
        for (int x = 0; x < bytes.length; x++) {
            byte bite = bytes[x];
            if (bite > 127) {
                //bite = (bite - 128) + -128;
                System.out.println("Encountered a byte that was greater than 127.");
            }

            String num = Integer.toHexString(bite);
            num = num.toUpperCase();
            if (num.length() < 2) {
                num = "0" + num;
            }
            num = "0x" + num;

            b.append(num);
            b.append(",");

        }//end for.
        b.delete(b.length() - 1, b.length());
        //b.append("}");
        return b.toString();


    }// end method.

    public static String convertBytesToUnicodeString(byte[] bytes) {
        int x = 0;
        int length = bytes.length;
        StringBuilder unicodeString = new StringBuilder();
        unicodeString.append("\"");
        byte b0, b1 = 32;
        while (x < length) {
            b0 = b1 = 32;

            if (x < length) //Obtain 1st byte.
            {
                b0 = bytes[x++];
            }

            if (x < length) //Obtain 2nd byte.
            {
                b1 = bytes[x++];
            }

            String b0String = Integer.toHexString(b0).toUpperCase();
            if (b0String.length() == 1) {
                b0String = "0" + b0String;
            }

            String b1String = Integer.toHexString(b1).toUpperCase();
            if (b1String.length() == 1) {
                b1String = "0" + b1String;
            }

            String uniCode = "\\u" + b0String + b1String;

            /*{ r"\u000A":r"\n", r"\u0009":r"\t",r"\u000D":r"\r", r"\u000C":r"\f", r"\u0008":r"\b", r"\u005C":r"\\" }*/

            if (uniCode.equalsIgnoreCase("\\u000A")) {
                uniCode = "\\n";
            }
            if (uniCode.equalsIgnoreCase("\\u0009")) {
                uniCode = "\\t";
            }
            if (uniCode.equalsIgnoreCase("\\u000D")) {
                uniCode = "\\r";
            }
            if (uniCode.equalsIgnoreCase("\\u000C")) {
                uniCode = "\\f";
            }
            if (uniCode.equalsIgnoreCase("\\u0008")) {
                uniCode = "\\b";
            }
            if (uniCode.equalsIgnoreCase("\\u005C")) {
                uniCode = "\\\\";
            }

            //uniCode = uniCode.substring(1,uniCode.length());
            unicodeString.append(uniCode);

        }//end for.

        unicodeString.append("\";");

        return unicodeString.toString();

    }//end method.

    public static java.io.InputStream getScriptStream(String unicodeString) {
        char[] chars = unicodeString.toCharArray();
        byte[] bytes = new byte[chars.length * 2];
        byte b0, b1 = 0;
        int bytesIndex = 0;
        for (int x = 0; x < chars.length; x++) {
            bytes[bytesIndex++] = (byte) (chars[x] >> 8);
            bytes[bytesIndex++] = (byte) (chars[x] & 0xff);
        }
        return new ByteArrayInputStream(bytes);
    }//end method.
}//end class.


