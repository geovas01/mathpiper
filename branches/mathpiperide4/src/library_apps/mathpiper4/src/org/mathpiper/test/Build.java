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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Build {

    private boolean strip = false; //Set to false to have unaltered scripts placed into Scripts.java.
    
    private java.io.File scriptsDir;
    //private java.io.FileWriter packagesFile;
    private java.io.FileWriter scriptsJavaFile;
    private java.io.FileWriter testsJavaFile;
    private String sourceScriptsDirectory = null;
    //private String outputScriptsDirectory = null;
    private String documentationOutputDirectory = null;
    private String sourceDirectory = null;
    private java.io.DataOutputStream documentationOutputFile;
    private java.io.FileWriter documentationOutputIndexFile;
    private java.io.DataOutputStream licenseOutputFile;
    private java.io.FileWriter licenseOutputIndexFile;
    private long documentationOffset = 0;
    private long licenseOffset = 0;
    private java.io.FileWriter functionOutputCategoriesFile;
    private List<CategoryEntry> functionCategoriesList = new ArrayList<CategoryEntry>();
    private int documentedFunctionsCount = 0;
    private int undocumentedMPWFileCount = 0;
    private String version;
    //private Interpreter mathpiper = Interpreters.newSynchronousInterpreter();

    private Build() {
    }//end constructor.

    public Build(String sourceDirectory, String outputDirectory, String version) throws Throwable {
    	    
    	this.sourceDirectory = sourceDirectory;
    	
        documentationOutputDirectory = outputDirectory;
        
        sourceScriptsDirectory = sourceDirectory + "org/mathpiper/scripts4/";
        
        this.version = version;

        this.initializeFiles();
    }

    public void initializeFiles() throws Throwable {
  
	    documentationOutputFile = new DataOutputStream(new java.io.FileOutputStream(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/documentation.txt"));
	
	    documentationOutputIndexFile = new java.io.FileWriter(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/documentation_index.txt");

	    licenseOutputFile = new DataOutputStream(new java.io.FileOutputStream(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/license.txt"));
	
	    licenseOutputIndexFile = new java.io.FileWriter(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/license_index.txt");
	
	    functionOutputCategoriesFile = new java.io.FileWriter(documentationOutputDirectory + "org/mathpiper/ui/gui/help/data/function_categories.txt");
    }

    public void compileScripts() throws Throwable {
	
	System.out.println("****************** Compiling scripts *******");
        System.out.println("Source directory: " + this.sourceScriptsDirectory);


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
                + "    private HashMap userFunctionsTestsMap = null;\n\n"
                + "    private HashMap builtInFunctionsTestsMap = null;\n\n"
                + "    public Tests() {\n\n"
                + "        userFunctionsTestsMap = new HashMap();\n\n"
                + "        builtInFunctionsTestsMap = new HashMap();\n\n"
                + "        String[] testString;\n\n";
        testsJavaFile.write(topOfClass);



        scriptsDir = new java.io.File(sourceScriptsDirectory);

        if (scriptsDir.exists()) {

            //Process built in functions first.
            if (documentationOutputFile != null) {

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/core");

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/optional");

                processBuiltinDocs(sourceDirectory, documentationOutputDirectory, "org/mathpiper/builtin/functions/plugins/jfreechart");
            }


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


            Collections.sort(functionCategoriesList);
            for (CategoryEntry entry : functionCategoriesList) {
                functionOutputCategoriesFile.write(entry.toString() + "\n");
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
                + "    public String[] getUserFunctionScript(String testName)\n"
                + "    {\n"
                + "        return (String[]) userFunctionsTestsMap.get(testName);\n"
                + "    }\n"
                + "\n"
                + "    public Map getUserFunctionsMap()\n"
                + "    {\n"
                + "        return userFunctionsTestsMap;\n"
                + "    }\n"
                + "\n"
                + "    public String[] getBuiltInFunctionScript(String testName)\n"
                + "    {\n"
                + "        return (String[]) builtInFunctionsTestsMap.get(testName);\n"
                + "    }\n"
                + "\n"
                + "    public Map getBuiltInFunctionsMap()\n"
                + "    {\n"
                + "        return builtInFunctionsTestsMap;\n"
                + "    }\n"
                + "}\n";
        testsJavaFile.write(bottomOfClass);
        testsJavaFile.close();



        if (documentationOutputFile != null) {

            licenseOutputFile.close();
            licenseOutputIndexFile.close();
        }

        if (documentationOutputFile != null) {

            documentationOutputFile.close();
            documentationOutputIndexFile.close();
            functionOutputCategoriesFile.close();
        }


        System.out.println("\nDocumented functions: " + this.documentedFunctionsCount + "\n");

        System.out.println("Undocumented .mpw files: " + this.undocumentedMPWFileCount + "\n");


    }//end method.



    private void processMPWFile(File mpwFile) throws Throwable {

        String mpwFilePath = mpwFile.getAbsolutePath();
        mpwFilePath = mpwFilePath.substring(mpwFilePath.indexOf(File.separator + "org" + File.separator + "mathpiper" + File.separator)); //"/org/mathpiper/";

        List<Fold> folds = MPWSFile.scanSourceFile(mpwFile.getPath(), new FileInputStream(mpwFile));

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

                    /*{"UnparseC", "CUnparsable?", "issues", "debug", "jFactorsPoly", "jasFactorsInt",
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

                        processAutomaticTestFold(fold, mpwFile.getPath(),false);

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
                        } catch (Throwable e) {
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
                                    
                                    scriptsJavaFile.write("\n        scriptString[2] = \"" + mpwFilePath + ", (" + defAttribute + ")\";");
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
            }
            else if (foldType.equalsIgnoreCase("%html") && fold.getAttributes().containsKey("subtype") && ((String)fold.getAttributes().get("subtype")).equals("license")) {
                //System.out.println("        **** Contains docs *****");
                hasDocs = true;

                processLicenseFold(fold, mpwFilePath);

            }//end if.


        }//end subpackage for.

        if (!hasDocs) {
            System.out.println("**** Does not contain docs ****");
            this.undocumentedMPWFileCount++;
        } else {
            System.out.println();
        }
    }//end method.

    private void processMathPiperDocsFold(Fold fold, String mpwFilePath) throws Throwable {
        if (documentationOutputFile != null) {

            String functionNamesString = "";
            
            if (fold.getAttributes().containsKey("name")) {
        	
                functionNamesString = (String) fold.getAttributes().get("name");

                if(functionNamesString.equals(""))
                {
                    System.out.print("*** UNNAMED IN DOCUMENTATION ***");
                    return;
                }


                String[] functionNames = functionNamesString.split(";");

                for (String functionName : functionNames) {
                    //DataOutputStream individualDocumentationFile = null;
                    /*
                    try{
                    individualDocumentationFile =  new DataOutputStream(new java.io.FileOutputStream(outputDirectory + functionName));
                    }catch(Throwable ex)
                    {
                    ex.printStackTrace();
                    }*/

                    documentationOutputIndexFile.write(functionName + ",");
                    documentationOutputIndexFile.write(documentationOffset + ",");

                    String contents = fold.getContents();

                    contents = contents + "\n*SOURCE " + mpwFilePath;

                    byte[] contentsBytes = contents.getBytes();
                    documentationOutputFile.write(contentsBytes, 0, contentsBytes.length);
                    //individualDocumentationFile.write(contentsBytes, 0, contentsBytes.length);
                    //individualDocumentationFile.close();

                    documentationOffset = documentationOffset + contents.length();
                    documentationOutputIndexFile.write(documentationOffset + "\n");

                    byte[] separator = "\n==========\n".getBytes();
                    documentationOutputFile.write(separator, 0, separator.length);

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
            }
            else
            {
        	System.out.print("*** UNNAMED IN DOCUMENTATION ***");
            }

        }//end if.
    }//end method

    
    private void processLicenseFold(Fold fold, String mpwFilePath) throws Throwable {
        if (licenseOutputFile != null) {

            String licenseNamesString = "";
            
            if (fold.getAttributes().containsKey("name")) {
        	
                licenseNamesString = (String) fold.getAttributes().get("name");

                if(licenseNamesString.equals(""))
                {
                    System.out.print("*** UNNAMED IN DOCUMENTATION ***");
                    return;
                }


                String[] licenseNames = licenseNamesString.split(";");

                for (String licenseName : licenseNames) {

                    licenseOutputIndexFile.write(licenseName + ",");
                    licenseOutputIndexFile.write(licenseOffset + ",");

                    String contents = fold.getContents();

                    byte[] contentsBytes = contents.getBytes();
                    licenseOutputFile.write(contentsBytes, 0, contentsBytes.length);

                    licenseOffset = licenseOffset + contents.length();
                    licenseOutputIndexFile.write(licenseOffset + "\n");

                    byte[] separator = "\n==========\n".getBytes();
                    licenseOutputFile.write(separator, 0, separator.length);

                    licenseOffset = licenseOffset + separator.length;

                    String access = "public";

                    if (fold.getAttributes().containsKey("categories")) {



                        String description = "NO DESCRIPTTION";
                        if(fold.getAttributes().containsKey("description"))
                        {
                            description = (String) fold.getAttributes().get("description");
                        }
                        	


                        if (description.contains(",")) {
                            description = "\"" + description + "\"";
                        }

                        System.out.print(licenseName + ": " + description + ", ");

                        String functionCategories = (String) fold.getAttributes().get("categories");
                        String[] categoryNames = functionCategories.split(";");
                        String categories = "";

                        int categoryIndex = 0;
                        String functionCategoryName = "";

                        for (String categoryName : categoryNames) {
                            if (categoryIndex == 0) {
                                functionCategoryName = categoryName;

                            } else {
                                categories = categories + categoryName + ",";
                            }
                            categoryIndex++;
                        }//end for.



                        if (!categories.equalsIgnoreCase("")) {
                            categories = categories.substring(0, categories.length() - 1);

                        }

                        if (functionCategoryName.equalsIgnoreCase("")) {
                            functionCategoryName = "Uncategorized";  //todo:tk:perhaps we should throw an exception here.
                        }

                        if (fold.getAttributes().containsKey("access")) {
                            access = (String) fold.getAttributes().get("access");
                        }

                        CategoryEntry categoryEntry = new CategoryEntry(functionCategoryName, licenseName, access, description, categories);

                        functionCategoriesList.add(categoryEntry);

                    } else {
                        System.out.print(licenseName + ": **** Uncategorized ****, ");
                    }
                }//end for.
            }
            else
            {
        	System.out.print("*** UNNAMED IN DOCUMENTATION ***");
            }

        }//end if.
    }//end method
    
    
    
    private void processAutomaticTestFold(Fold fold, String filePath, boolean builtin) throws Throwable {

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
            if(builtin)
            {
                testsJavaFile.write("        builtInFunctionsTestsMap.put(\"" + nameAttribute + "\"," + "testString" + ");\n");
            }
            else
            {
                testsJavaFile.write("        userFunctionsTestsMap.put(\"" + nameAttribute + "\"," + "testString" + ");\n");
            }

        } else {
            throw new Exception("The following test code has no name: " + foldContents);
        }




    }//end method.

    public void execute() throws Throwable {
        //This method is needed by ant to run this class.

        compileScripts();
        
        createVersionJavaFile();
    }//end method.
    
    
    private void createVersionJavaFile() throws Throwable
    {
	System.out.println("****************** Creating version file *******");
	
        FileWriter versionOutputFile = new java.io.FileWriter(sourceDirectory + "org/mathpiper/Version.java");
	        
	        
	String versionJavaFile = 
	    "package org.mathpiper;\n"
            + "\n"
	    + "//*** GENERATED FILE, DO NOT EDIT ***\n"    
            + "\n"
            + "public class Version\n"
            + "{\n"
            + "   private static final String version = \"" + this.version +"\";\n"
            + "\n"
            + "   public static String version()\n"
            + "   {\n"
            + "       return version;\n"
            + "   }\n"
            + "}\n";
	
	versionOutputFile.write(versionJavaFile);
	
	versionOutputFile.close();
	
    }

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

    private void processBuiltinDocs(String sourceDirectoryPath, String outputDirectoryPath, String pluginFilePath) throws Throwable {
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

                List<Fold> folds = MPWSFile.scanSourceFile(javaFile.getPath(), new FileInputStream(javaFile));

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
                                this.processAutomaticTestFold(fold, javaFile.getPath(),true);
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

    public static String parsePrintScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, boolean evaluate) throws Throwable {

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
    endoffile = true;Pointer
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

    } catch (Throwable e) {
    System.out.println(e.getMessage());
    e.printStackTrace(); //todo:tk:uncomment for debugging.

    EvaluationException ee = new EvaluationException(e.getMessage(),  aEnvironment.iCurrentInput.iStatus.getFileName(), aEnvironment.iCurrentInput.iStatus.getLineNumber(), aEnvironment.iCurrentInput.iStatus.getLineNumber());
    throw ee;
    } finally {
    aEnvironment.iCurrentInput = previous;
    }
    }


    public static void printExpression(StringBuffer outString, Environment aEnvironment, ConsPointer aExpression) throws Throwable {
    MathPiperUnparser printer = new MathPiperUnparser(aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
    //LispUnparser printer = new LispUnparser(false);

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
            
            String version = "UNDEFINED";

            if (args.length == 3) {
                sourceDirectory = args[0];
                outputDirectory = args[1];
                version = args[2];
                
                System.out.println("XXX " + sourceDirectory + ", " + outputDirectory);
            }
            else
            {
            	    throw new Exception("Three arguments must be submitted to the main method.");
            }

            Build build = new Build(sourceDirectory, outputDirectory, version);


            build.execute();


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



        } catch (Throwable e) {
            e.printStackTrace();
        }


    }//end main
}//end class.

