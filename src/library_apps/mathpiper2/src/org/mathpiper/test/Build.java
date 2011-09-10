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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tkosan
 */
public class Build {

    private boolean strip = true;
    private java.io.File scriptsDir;
    //private java.io.FileWriter packagesFile;
    private java.io.FileWriter scriptsJavaFile;
    private java.io.FileWriter testsJavaFile;
    private String sourceScriptsDirectory = null;
    //private String outputScriptsDirectory = null;
    private String documentationOutputDirectory = null;
    private String sourceDirectory = null;
    private java.io.DataOutputStream documentationFile;
    private java.io.FileWriter documentationIndexFile;
    private long documentationOffset = 0;
    private java.io.FileWriter functionCategoriesFile;
    private List<CategoryEntry> functionCategoriesList = new ArrayList<CategoryEntry>();
    private int documentedFunctionsCount = 0;
    private int undocumentedMPWFileCount = 0;
    //private Interpreter mathpiper = Interpreters.newSynchronousInterpreter();

    private Build() {
    }//end constructor.

    public Build(String sourceDirectory, String outputDirectory) throws Exception {
    	    
    	this.sourceDirectory = sourceDirectory;
    	
        documentationOutputDirectory = outputDirectory;
        sourceScriptsDirectory = sourceDirectory + "org/mathpiper/scripts4/";

        this.initializeFiles();



    }

    public void initializeFiles() throws Exception {
  
	    documentationFile = new DataOutputStream(new java.io.FileOutputStream(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/documentation.txt"));
	
	    documentationIndexFile = new java.io.FileWriter(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/documentation_index.txt");
	
	    functionCategoriesFile = new java.io.FileWriter(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/function_categories.txt");

    }

    public void compileScripts() throws Exception {


        //System.out.println("XXXXX " + sourceDirectory);


        scriptsJavaFile = new java.io.FileWriter(sourceDirectory + "org/mathpiper/Scripts.java");
        String topOfClass =
                "package org.mathpiper;\n"
                + "\n"
                + "import java.util.HashMap;\n"
                + "\n"
                + "import java.util.Map;\n"
                + "\n"
                + "public class Scripts {\n"
                + "\n"
                + "    private HashMap scriptMap = null;\n\n"
                + "    public Scripts() {\n\n"
                + "        scriptMap = new HashMap();\n\n"
                + "        String[] scriptString;\n\n";
        scriptsJavaFile.write(topOfClass);



        testsJavaFile = new java.io.FileWriter(sourceDirectory + "org/mathpiper/Tests.java");
        topOfClass =
                "package org.mathpiper;\n"
                + "\n"
                + "import java.util.HashMap;\n"
                + "\n"
                + "import java.util.Map;\n"
                + "\n"
                + "public class Tests {\n"
                + "\n"
                + "    private HashMap testsMap = null;\n\n"
                + "    public Tests() {\n\n"
                + "        testsMap = new HashMap();\n\n"
                + "        String[] testString;\n\n";
        testsJavaFile.write(topOfClass);



        scriptsDir = new java.io.File(sourceScriptsDirectory);

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





                //Place files in package dir
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


                    for (int x2 = 0; x2 < packageDirectoryContentsArray.length; x2++) {
                        //Process each script or subdirectory in a .rep directory.***********************************************************************************
                        File scriptFileOrSubdirectoy = packageDirectoryContentsArray[x2];

                        if (scriptFileOrSubdirectoy.getName().toLowerCase().endsWith(".mrw")) {
                            throw new Exception("The .mrw file extension has been deprecated ( " + scriptFileOrSubdirectoy.getName() + " ).");
                        }

                        if (scriptFileOrSubdirectoy.getName().toLowerCase().endsWith(".mpw")) {
                            //Process a .mpw files that is in a top-level package. ************************************************************************

                            System.out.print("    " + scriptFileOrSubdirectoy.getName() + " -> ");

                            documentedFunctionsCount++;

                            processMPWFile(scriptFileOrSubdirectoy);

                        } else {
                            //Process a subdirectory.***********************************************************************************************

                            System.out.println("    " + scriptFileOrSubdirectoy.getName());

                            java.io.File[] packageSubDirectoryContentsArray = scriptFileOrSubdirectoy.listFiles(new java.io.FilenameFilter() {

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
                                //Process each script in a package subdirectlry directory.
                                File scriptFile2 = packageSubDirectoryContentsArray[x3];
                                System.out.print("        " + scriptFile2.getName() + " -> ");

                                processMPWFile(scriptFile2);

                                //mpi file.


                            }//end subpackage for.



                        }//end else.

                    }//end package for.


                }//end if.



            }//end for.


            if (documentationFile != null) {

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/core");

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/optional");

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/plugins/jfreechart");
            }

            Collections.sort(functionCategoriesList);
            for (CategoryEntry entry : functionCategoriesList) {
                functionCategoriesFile.write(entry.toString() + "\n");
            }


        } else {
            System.out.println("\nDirectory " + sourceScriptsDirectory + " does not exist.\n");
        }



        String bottomOfClass =
                "    }\n\n"
                + "    public String[] getScript(String functionName)\n"
                + "    {\n"
                + "        return (String[]) scriptMap.get(functionName);\n"
                + "    }\n"
                + "\n"
                + "    public Map getMap()\n"
                + "    {\n"
                + "        return  scriptMap;\n"
                + "    }\n"
                + "}\n";
        scriptsJavaFile.write(bottomOfClass);
        scriptsJavaFile.close();



        bottomOfClass =
                "    }\n\n"
                + "    public String[] getScript(String testName)\n"
                + "    {\n"
                + "        return (String[]) testsMap.get(testName);\n"
                + "    }\n"
                + "\n"
                + "    public Map getMap()\n"
                + "    {\n"
                + "        return testsMap;\n"
                + "    }\n"
                + "}\n";
        testsJavaFile.write(bottomOfClass);
        testsJavaFile.close();



        if (documentationFile != null) {

            documentationFile.close();
            documentationIndexFile.close();
            functionCategoriesFile.close();
        }


        System.out.println("\nDocumented functions: " + this.documentedFunctionsCount + "\n");

        System.out.println("Undocumented .mpw files: " + this.undocumentedMPWFileCount + "\n");


    }//end method.

    List scanSourceFile(File sourceFile) throws Exception {


        String fileName = sourceFile.getName();

        //Uncomment for debugging.
        /*
        if (getFileName.equals("Factors.mpw")) {
        int xxx = 1;
        }//end if.*/

        List<Fold> folds = new ArrayList();
        StringBuilder foldContents = new StringBuilder();
        String foldHeader = "";
        boolean inFold = false;
        int lineCounter = 0;
        int startLineNumber = -1;


        FileInputStream fstream = new FileInputStream(sourceFile);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        //Read File Line By Line
        while ((line = br.readLine()) != null) {
            //line = line.trim();
            //System.out.println(line);
            lineCounter++;

            if (line.startsWith("%/")) {

                if (inFold == false) {
                    throw new Exception("Opening fold tag missing in " + fileName + ".");
                }

                Fold fold = new Fold(startLineNumber, foldHeader, foldContents.toString());
                foldContents.delete(0, foldContents.length());
                folds.add(fold);
                inFold = false;

            } else if (line.startsWith("%")) {

                if (inFold == true) {
                    throw new Exception("Closing fold tag missing in " + fileName + ".");
                }

                startLineNumber = lineCounter;
                foldHeader = line;
                inFold = true;
            } else if (inFold == true) {
                foldContents.append(line);
                foldContents.append("\n");

            }
        }//end while.

        if (inFold == true) {
            throw new Exception("Opening or closing fold tag missing in " + fileName + ".");
        }

        //Close the input stream
        in.close();


        return folds;

    }//end.

    class Fold {

        private int startLineNumber;
        private String type;
        private String contents;
        private Map<String, String> attributes = new HashMap();

        public Fold(int startLineNumber, String header, String contents) {

            this.startLineNumber = startLineNumber;

            scanHeader(header);

            this.contents = contents;
        }//end inner class.

        private void scanHeader(String header) {
            String[] headerParts = header.trim().split(",");

            type = headerParts[0];

            for (int x = 1; x < headerParts.length; x++) {
                headerParts[x] = headerParts[x].replaceFirst("=", ",");
                String[] headerPart = headerParts[x].split(",");
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

        public int getStartLineNumber()
        {
            return this.startLineNumber;
        }
        
    }//end inner class.

    private void processMPWFile(File mpwFile) throws Exception {

        String mpwFilePath = mpwFile.getAbsolutePath();
        mpwFilePath = mpwFilePath.substring(mpwFilePath.indexOf(File.separator + "org" + File.separator + "mathpiper" + File.separator)); //"/org/mathpiper/";

        List<Fold> folds = scanSourceFile(mpwFile);

        boolean hasDocs = false;

        String scopeAttribute = "public";
        String subTypeAttribute = "";
        //String scope = "public";

        FoldLoop:
        for (Fold fold : folds) {

            String foldType = fold.getType();

            if (foldType.equalsIgnoreCase("%mathpiper")) {


                if (fold.getAttributes().containsKey("scope")) {
                    scopeAttribute = (String) fold.getAttributes().get("scope");
                }

                if (fold.getAttributes().containsKey("subtype")) {
                    subTypeAttribute = (String) fold.getAttributes().get("subtype");
                }

                if (!scopeAttribute.equalsIgnoreCase("nobuild")) {


                    String[] functionsNotToBuild = {""};

                    /*{"CForm", "CFormable?", "issues", "debug", "jFactorsPoly", "jasFactorsInt",
                        "xContent", "xFactor", "xFactors", "xFactorsBinomial", "xFactorsResiduals", "xPrimitivePart", "html", "odesolver",
                        "orthopoly", "openmath", "ManipEquations", "Manipulate", "SolveSetEqns", "ControlChart", "GeoGebra", "GeoGebraHistogram",
                        "GeoGebraPlot", "GeoGebraPoint", "ggbLine", "HighschoolForm", "jas_test", "JFreeChartHistogram", "JavaAccess", "RForm",
                        "xCheckSolution", "xSolve", "xSolvePoly", "xSolveRational", "xSolveReduce", "xSolveSqrts", "xSolveSystem", "xTerms",};*/

                    for (String fileName : functionsNotToBuild) {
                        fileName = fileName + ".mpw";
                        if (fileName.equalsIgnoreCase(mpwFile.getName())) {
                            continue FoldLoop;
                        }
                    }

                    String foldContents = fold.getContents();


                    if (subTypeAttribute.equalsIgnoreCase("automatic_test")) {

                        processAutomaticTestFold(fold, mpwFile.getPath());

                    } else {


                        String processedScript;
                        if (this.strip == true) {
                            //Uses regular expressions to process scripts.
                            String foldContentsString = foldContents.toString();
                            // //See http://ostermiller.org/findcomment.html
                            String foldContentsStringNoComments = foldContentsString.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "");
                            foldContentsStringNoComments = foldContentsStringNoComments.replace("\t", "");
                            foldContentsStringNoComments = foldContentsStringNoComments.replaceAll(" +", " ");
                            foldContentsStringNoComments = foldContentsStringNoComments.replaceAll("\\n+", "");
                            foldContentsStringNoComments = foldContentsStringNoComments.replace("\\", "\\\\");
                            foldContentsStringNoComments = foldContentsStringNoComments.replace("\"", "\\\"");
                            processedScript = foldContentsStringNoComments;
                        } else {
                            processedScript = foldContents.toString();
                            processedScript = processedScript.replace("\\", "\\\\");
                            processedScript = processedScript.replace("\"", "\\\"");
                            processedScript = processedScript.replace("\n", "\\n");
                        }




                        /*
                        //Uses the parser and the printer to process scripts.
                        String processedScript = "";
                        InputStatus inputStatus = new InputStatus();
                        inputStatus.setTo(mpwFile.getName());
                        StringInputStream functionInputStream = new StringInputStream(foldContents, inputStatus);
                        try {
                        processedScript = parsePrintScript(mathpiper.getEnvironment(), -1, functionInputStream, false);
                        } catch (Exception e) {
                        System.out.println(inputStatus.getFileName() + ": Line: " + inputStatus.getLineNumber());
                        throw (e);
                        }
                         */


                        /*
                        //Gzip + base64.
                        StringReader input = new StringReader(processedScript);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        OutputStreamWriter writer = new OutputStreamWriter(new GZIPOutputStream(out));
                        char[] charBuffer = new char[1024];
                        while (input.read(charBuffer) != -1) {
                        writer.write(charBuffer);
                        }
                        writer.close();
                        processedScript = (new BASE64Encoder()).encode(out.toByteArray());
                        processedScript = processedScript.replace("\n", "\\n");
                         */


                        //if (mpwFile.getName().equalsIgnoreCase("NormalForm.mpw")) {
                        //    int xx = 2;
                        //}

                        if (fold.getAttributes().containsKey("def")) {
                            String defAttribute = (String) fold.getAttributes().get("def");
                            if (!defAttribute.equalsIgnoreCase("")) {

                                if (strip == true) {
                                    scriptsJavaFile.write("\n        scriptString = new String[2];");
                                } else {
                                    scriptsJavaFile.write("\n        scriptString = new String[3];");
                                }


                                scriptsJavaFile.write("\n        scriptString[0] = null;");


                                scriptsJavaFile.write("\n        scriptString[1] = \"" + processedScript + "\";");

                                if (strip == false) {

                                    mpwFilePath = mpwFilePath.replace("\\", "\\\\");
                                    
                                    scriptsJavaFile.write("\n        scriptString[2] = \"" + mpwFilePath + "\";");
                                }

                                scriptsJavaFile.write("\n");
                                
                                String[] defFunctionNames = defAttribute.split(";");

                                for (int x = 0; x < defFunctionNames.length; x++) {
                                    scriptsJavaFile.write("        scriptMap.put(\"" + defFunctionNames[x] + "\"," + "scriptString" + ");\n");
                                }//end if.
                            }//end if.
                        }//end if.

                    }//end else.

                }//end if.


            } else if (foldType.equalsIgnoreCase("%mathpiper_docs")) {
                //System.out.println("        **** Contains docs *****");
                hasDocs = true;

                processMathPiperDocsFold(fold, mpwFilePath);

            }//end if.


        }//end subpackage for.

        if (!hasDocs) {
            System.out.println("**** Does not contain docs ****");
            this.undocumentedMPWFileCount++;
        } else {
            System.out.println();
        }
    }//end method.

    private void processMathPiperDocsFold(Fold fold, String mpwFilePath) throws Exception {
        if (documentationFile != null) {

            String functionNamesString = "";
            if (fold.getAttributes().containsKey("name")) {
                functionNamesString = (String) fold.getAttributes().get("name");

                //Uncomment to debug the documentation for a given function..
                /*if(functionNamesString.equals("RepToNumber"))
                {
                int xxx = 1;
                }*/


                String[] functionNames = functionNamesString.split(";");

                for (String functionName : functionNames) {
                    //DataOutputStream individualDocumentationFile = null;
                    /*
                    try{
                    individualDocumentationFile =  new DataOutputStream(new java.io.FileOutputStream(outputDirectory + functionName));
                    }catch(Exception ex)
                    {
                    ex.printStackTrace();
                    }*/

                    documentationIndexFile.write(functionName + ",");
                    documentationIndexFile.write(documentationOffset + ",");

                    String contents = fold.getContents();

                    contents = contents + "\n*SOURCE " + mpwFilePath;

                    byte[] contentsBytes = contents.getBytes();
                    documentationFile.write(contentsBytes, 0, contentsBytes.length);
                    //individualDocumentationFile.write(contentsBytes, 0, contentsBytes.length);
                    //individualDocumentationFile.close();

                    documentationOffset = documentationOffset + contents.length();
                    documentationIndexFile.write(documentationOffset + "\n");

                    byte[] separator = "\n==========\n".getBytes();
                    documentationFile.write(separator, 0, separator.length);

                    documentationOffset = documentationOffset + separator.length;

                    String access = "public";

                    if (fold.getAttributes().containsKey("categories")) {


                        int commandIndex = contents.indexOf("*CMD");
                        if (commandIndex == -1) {
                            throw new Exception("Missing *CMD tag.");
                        }
                        String descriptionLine = contents.substring(commandIndex, contents.indexOf("\n", commandIndex));
                        String description = descriptionLine.substring(descriptionLine.lastIndexOf("--") + 2);
                        description = description.trim();

                        if (description.contains(",")) {
                            description = "\"" + description + "\"";
                        }

                        System.out.print(functionName + ": " + description + ", ");

                        String functionCategories = (String) fold.getAttributes().get("categories");
                        String[] categoryNames = functionCategories.split(";");
                        String categories = "";

                        int categoryIndex = 0;
                        String functionCategoryName = "";

                        for (String categoryName : categoryNames) {
                            if (categoryIndex == 0) {
                                //functionCategoriesFile.write(categoryName + ",");
                                functionCategoryName = categoryName;

                            } else {
                                categories = categories + categoryName + ",";
                            }
                            categoryIndex++;
                        }//end for.

                        //functionCategoriesFile.write(functionName + ",");


                        //functionCategoriesFile.write(description);


                        if (!categories.equalsIgnoreCase("")) {
                            categories = categories.substring(0, categories.length() - 1);
                            //functionCategoriesFile.write("," + categories);

                        }
                        //functionCategoriesFile.write("\n");
                        if (functionCategoryName.equalsIgnoreCase("")) {
                            functionCategoryName = "Uncategorized";  //todo:tk:perhaps we should throw an exception here.
                        }

                        if (fold.getAttributes().containsKey("access")) {
                            access = (String) fold.getAttributes().get("access");
                        }

                        CategoryEntry categoryEntry = new CategoryEntry(functionCategoryName, functionName, access, description, categories);

                        functionCategoriesList.add(categoryEntry);

                    } else {
                        System.out.print(functionName + ": **** Uncategorized ****, ");
                    }
                }//end for.
            }//end if.

        }//end if.
    }//end method

    private void processAutomaticTestFold(Fold fold, String filePath) throws Exception {

        String foldContents = fold.getContents();

        foldContents = foldContents.replace("\\", "\\\\");
        foldContents = foldContents.replace("\n", "\\n");
        foldContents = foldContents.replace("\"", "\\\"");

        String nameAttribute = "";

        if (fold.getAttributes().containsKey("name") && !(nameAttribute = (String) fold.getAttributes().get("name")).equals("")) {

            filePath = filePath.substring(filePath.indexOf(File.separator + "org" + File.separator + "mathpiper" + File.separator));
            filePath = filePath.replace("\\", "\\\\");
            
            //foldContents =  ("Testing(\\\"" + nameAttribute + "\\\");" + foldContents);
            testsJavaFile.write("\n        testString = new String[3];");
            testsJavaFile.write("\n        testString[0] = \"" + fold.getStartLineNumber() + "\";");
            testsJavaFile.write("\n        testString[1] = \"" + foldContents + "\";");
            testsJavaFile.write("\n        testString[2] = \"" + filePath + "\";\n");
            testsJavaFile.write("        testsMap.put(\"" + nameAttribute + "\"," + "testString" + ");\n");

        } else {
            throw new Exception("The following test code has no name: " + foldContents);
        }




    }//end method.

    public void execute() throws Exception {
        //execute() method is needed by ant to run this class.
        System.out.println("****************** Compiling scripts *******");
        System.out.println("Source directory: " + this.sourceScriptsDirectory);

        compileScripts();
    }//end method.

    private class CategoryEntry implements Comparable {

        private String categoryName;
        private String functionName;
        private String access;
        private String description;
        private String categories;

        public CategoryEntry(String categoryName, String functionName, String access, String description, String categories) {
            this.categoryName = categoryName;
            this.functionName = functionName;
            this.access = access;
            this.description = description;
            this.categories = categories;
        }

        public int compareTo(Object o) {
            CategoryEntry categoryEntry = (CategoryEntry) o;
            return this.functionName.compareToIgnoreCase(categoryEntry.getFunctionName());
        }//end method.

        public String getFunctionName() {
            return this.functionName;
        }//end method.

        public String toString() {
            return categoryName + "," + functionName + "," + access + "," + description + "," + categories;
        }//end method.
    }//end class.

    private void processBuiltinDocs(String sourceDirectoryPath, String outputDirectoryPath, String pluginFilePath) throws Exception {
        // try {
        System.out.println("\n***** Processing built in docs *****");

        File builtinFunctionsSourceDir = new java.io.File(sourceDirectoryPath + pluginFilePath);


        java.io.FileWriter pluginsListFile = null;
        if(!outputDirectoryPath.endsWith("core"))
        {
            pluginsListFile = new java.io.FileWriter(outputDirectoryPath + "/" + pluginFilePath + "/plugins_list.txt");
        }

        System.out.println(outputDirectoryPath + "/" + pluginFilePath + "/plugins_list.txt");

        if (builtinFunctionsSourceDir.exists()) {
            java.io.File[] javaFilesDirectory = builtinFunctionsSourceDir.listFiles(new java.io.FilenameFilter() {

                public boolean accept(java.io.File file, String name) {
                    if (name.endsWith(".java")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            Arrays.sort(javaFilesDirectory);

            for (int x = 0; x < javaFilesDirectory.length; x++) {

                File javaFile = javaFilesDirectory[x];
                String javaFileName = javaFile.getName();

                if (pluginsListFile != null) {
                    pluginsListFile.append(javaFileName.substring(0, javaFileName.length() - 4) + "class" + "\n");
                }


                System.out.print(javaFileName + " -> ");

                this.documentedFunctionsCount++;

                List<Fold> folds = scanSourceFile(javaFile);

                boolean hasDocs = false;

                String scopeAttribute = "public";
                String subTypeAttribute = "";

                for (Fold fold : folds) {

                    String foldType = fold.getType();
                    if (foldType.equalsIgnoreCase("%mathpiper_docs")) {
                        // System.out.println("        **** Contains docs *****  " + javaFileName);
                        hasDocs = true;


                        processMathPiperDocsFold(fold, javaFile.getPath());

                    } else if (foldType.equalsIgnoreCase("%mathpiper")) {
                        if (fold.getAttributes().containsKey("scope")) {
                            scopeAttribute = (String) fold.getAttributes().get("scope");
                        }

                        if (fold.getAttributes().containsKey("subtype")) {
                            subTypeAttribute = (String) fold.getAttributes().get("subtype");
                        }

                        if (!scopeAttribute.equalsIgnoreCase("nobuild")) {

                            String foldContents = fold.getContents();


                            if (subTypeAttribute.equalsIgnoreCase("automatic_test")) {
                                this.processAutomaticTestFold(fold, javaFile.getPath());
                            }//end if.
                        }//end if.
                    }//end else.

                }//end for

                if (!hasDocs) {
                    System.out.println("**** Does not contain docs ****");// + javaFileName);
                    this.undocumentedMPWFileCount++;
                } else {
                    System.out.println();
                }




            }//end for

            if (pluginsListFile != null) {
                pluginsListFile.close();
            }

        }//end if.

        /*               } catch (java.io.IOException e) {
        e.printStackTrace();
        }*/


    }//end method.


    /*

    public static String parsePrintScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, boolean evaluate) throws Exception {

    StringBuffer printedScriptStringBuffer = new StringBuffer();

    MathPiperInputStream previous = aEnvironment.iCurrentInput;
    try {
    aEnvironment.iCurrentInput = aInput;
    // TODO make "EndOfFile" a global thing
    // read-parse-evaluate to the end of file
    String eof = (String) aEnvironment.getTokenHash().lookUp("EndOfFile");
    boolean endoffile = false;
    MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(),
    aEnvironment.iCurrentInput, aEnvironment,
    aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
    aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
    ConsPointer readIn = new ConsPointer();
    while (!endoffile) {
    // Read expression
    parser.parse(aStackTop, readIn);

    LispError.check(aEnvironment, aStackTop, readIn.getCons() != null, LispError.READING_FILE, "","INTERNAL");
    // check for end of file
    if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
    endoffile = true;
    } // Else print and maybe evaluate
    else {
    printExpression(printedScriptStringBuffer, aEnvironment, readIn);

    if (evaluate == true) {
    ConsPointer result = new ConsPointer();
    aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, result, readIn);
    }
    }
    }//end while.

    return printedScriptStringBuffer.toString();

    } catch (Exception e) {
    System.out.println(e.getMessage());
    e.printStackTrace(); //todo:tk:uncomment for debugging.

    EvaluationException ee = new EvaluationException(e.getMessage(),  aEnvironment.iCurrentInput.iStatus.getFileName(), aEnvironment.iCurrentInput.iStatus.getLineNumber(), aEnvironment.iCurrentInput.iStatus.getLineNumber());
    throw ee;
    } finally {
    aEnvironment.iCurrentInput = previous;
    }
    }


    public static void printExpression(StringBuffer outString, Environment aEnvironment, ConsPointer aExpression) throws Exception {
    MathPiperPrinter printer = new MathPiperPrinter(aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
    //LispPrinter printer = new LispPrinter(false);

    MathPiperOutputStream stream = new StringOutputStream(outString);
    printer.print(-1, aExpression, stream, aEnvironment);
    outString.append(";");

    }//end method.


    public static void fileCopy(String from_name, String to_name) throws IOException {
    File from_file = new File(from_name); // Get File objects from Strings
    File to_file = new File(to_name);

    if (!from_file.exists()) {
    abort("no such source file: " + from_name);
    }
    if (!from_file.isFile()) {
    abort("can't copy directory: " + from_name);
    }
    if (!from_file.canRead()) {
    abort("source file is unreadable: " + from_name);
    }

    if (to_file.isDirectory()) {
    to_file = new File(to_file, from_file.getName());
    }


    String parent = to_file.getParent(); // The destination directory
    if (parent == null) // If none, use the current directory
    {
    parent = System.getProperty("user.dir");
    }
    File dir = new File(parent); // Convert it to a file.
    if (!dir.exists()) {
    abort("destination directory doesn't exist: " + parent);
    }
    if (dir.isFile()) {
    abort("destination is not a directory: " + parent);
    }
    if (!dir.canWrite()) {
    abort("destination directory is unwriteable: " + parent);
    }



    FileInputStream from = null; // Stream to read from source
    FileOutputStream to = null; // Stream to write to destination
    try {
    from = new FileInputStream(from_file); // Create input stream
    to = new FileOutputStream(to_file); // Create output stream
    byte[] buffer = new byte[4096]; // To hold file contents
    int bytes_read; // How many bytes in buffer

    while ((bytes_read = from.read(buffer)) != -1) // Read until EOF
    {
    to.write(buffer, 0, bytes_read); // write
    }
    } finally {
    if (from != null) {
    try {
    from.close();
    } catch (IOException e) {
    ;
    }
    }
    if (to != null) {
    try {
    to.close();
    } catch (IOException e) {
    ;
    }
    }
    }
    }


    private static void abort(String msg) throws IOException {
    throw new IOException("FileCopy: " + msg);
    }


     */
    public static void main(String[] args) {
        try {
            //fileCopy("/home/tkosan/NetBeansProjects/mathpiper_javascript_branch/src/org/mathpiper/test/Scripts.java", "/home/tkosan/NetBeansProjects/mathpiper_javascript_branch/src/org/mathpiper/Scripts.java"); if(1==1) return;

            //String baseDirectory = "/home/tkosan/NetBeansProjects/mathpiper_javascript_branch";
            
            String sourceDirectory = null;
            
            String outputDirectory = null;

            if (args.length == 2) {
                sourceDirectory = args[0];
                outputDirectory = args[1];
            }
            else
            {
            	    throw new Exception("Two arguments must be submitted to the main method.");
            }

            Build build = new Build(sourceDirectory, outputDirectory);


            build.compileScripts();


            /*
            Map functionDocs = new HashMap();

            BufferedReader documentationIndex = new BufferedReader(new FileReader(outputDocsDirectory.getPath() + "/documentation_index.txt"));

            String line;
            while ((line = documentationIndex.readLine()) != null) {

            String[] values = line.split(",");

            if (values[0].indexOf(";") != -1) {
            String[] functionNames = values[0].split(";");
            for (String name : functionNames) {
            functionDocs.put(name, values[1] + "," + values[2]);
            }//end for.
            } else {
            functionDocs.put(values[0], values[1] + "," + values[2]);
            }//end else.
            }//end while.

            documentationIndex.close();
            // */



        } catch (Exception e) {
            e.printStackTrace();
        }


    }//end main
}//end class.

