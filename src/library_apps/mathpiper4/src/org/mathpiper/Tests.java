package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Tests {

    private HashMap userFunctionsTestsMap = null;

    private HashMap builtInFunctionsTestsMap = null;

    private HashMap documentationExamplesTestsMap = null;

    public Tests() {

        userFunctionsTestsMap = new HashMap();

        builtInFunctionsTestsMap = new HashMap();

        documentationExamplesTestsMap = new HashMap();
    }

    public String[] getUserFunctionScript(String testName)
    {
        return (String[]) userFunctionsTestsMap.get(testName);
    }

    public Map getUserFunctionsMap()
    {
        return userFunctionsTestsMap;
    }

    public String[] getBuiltInFunctionScript(String testName)
    {
        return (String[]) builtInFunctionsTestsMap.get(testName);
    }

    public Map getBuiltInFunctionsMap()
    {
        return builtInFunctionsTestsMap;
    }

    public String[] getdocumentationExamplesTestsScript(String testName)
    {
        return (String[]) documentationExamplesTestsMap.get(testName);
    }

    public Map getdocumentationExamplesTestsMap()
    {
        return documentationExamplesTestsMap;
    }
}
