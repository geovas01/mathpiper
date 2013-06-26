package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Scripts {

    private HashMap scriptMap = null;

    public Scripts() {

        scriptMap = new HashMap();
    }

    public String[] getScript(String functionName)
    {
        return (String[]) scriptMap.get(functionName);
    }

    public Map getMap()
    {
        return  scriptMap;
    }
}
