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
package org.mathpiper.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MPWSFile {

    public MPWSFile() {
        super();
    }

    
    public static Map<String, Fold> getFoldsMap(String sourceName, InputStream inputStream) throws Throwable {
        return getFoldsMap(sourceName, inputStream, null, null);
    }//end method.

    
    public static Map<String, Fold> getFoldsMap(String sourceName, InputStream inputStream, String type) throws Throwable {
        return getFoldsMap(sourceName, inputStream, type, null);
    }//end method.        

    
    public static Map<String, Fold> getFoldsMap(String sourceName, InputStream inputStream, String foldType, String foldSubtype) throws Throwable {
        Map<String, Fold> namedFolds = new HashMap<String, Fold>();

        List<Fold> folds = scanSourceFile(sourceName, inputStream);

        for (Fold fold : folds) {
            Map<String, String> attributes = fold.getAttributes();

            Set<String> keys = attributes.keySet();

            for (String key : keys) {

                if (key.equalsIgnoreCase("name")) {
                    
                    String foldName = attributes.get(key);
                    
                    if(foldType == null && foldSubtype == null)
                    {
                        namedFolds.put(foldName, fold);
                    }
                    else if(foldType != null && foldSubtype == null)
                    {
                        String currentFoldType = attributes.get(foldType);
                        
                        if(currentFoldType.equals(foldType))
                        {
                            namedFolds.put(foldName, fold);
                        }
                    }
                    else if(foldType != null && foldSubtype != null)
                    {
                        String currentFoldType = attributes.get(foldType);
                        
                        String currentFoldSubtype = attributes.get(foldType);
                        
                        if(currentFoldType.equals(foldType) && currentFoldSubtype.equals(foldSubtype))
                        {
                            namedFolds.put(foldName, fold);
                        }
                    }
                }
            }
        }

        return namedFolds;

    }//end method.

    
    public static List<Fold> scanSourceFile(String sourceName, InputStream inputStream) throws Throwable {

        //Uncomment for debugging.
        /*
         if (getFileName.equals("Factors.mpw")) {
         int xxx = 1;
         }//end if.*/
        List<Fold> folds = new ArrayList<Fold>();
        StringBuilder foldContents = new StringBuilder();
        String foldHeader = "";
        boolean inFold = false;
        boolean inOutputFold = false;
        int lineCounter = 0;
        int startLineNumber = -1;

        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        //Read File Line By Line
        while ((line = br.readLine()) != null) {

            //System.out.println(line);
            lineCounter++;

            if (!line.matches("^ */%/.*$") && !line.matches("^ *\\. */%/.*$") && line.contains("%/")) {

                String tempLine = line;

                if (tempLine.startsWith(".")) {
                    tempLine = line.substring(1);

                    if (tempLine.trim().startsWith("%/output")) {
                        inOutputFold = false;
                    }
                }

                if (!inOutputFold && tempLine.trim().startsWith("%/")) {

                    if (inFold == false) {
                        throw new Exception("Opening fold tag missing in file " + sourceName + " on line " + lineCounter);
                    }

                    Fold fold = new Fold(startLineNumber, lineCounter, foldHeader, foldContents.toString());
                    foldContents.delete(0, foldContents.length());
                    folds.add(fold);
                    inFold = false;
                }

            } else if (!inOutputFold && line.trim().startsWith("%") && line.trim().length() > 2 && line.trim().charAt(1) != ' ') {

                if (inFold == true) {
                    throw new Exception("Closing fold tag missing in file " + sourceName + " on line " + lineCounter);
                }

                if (line.trim().startsWith("%output")) {
                    inOutputFold = true;
                }

                startLineNumber = lineCounter;
                foldHeader = line.trim();
                inFold = true;
            } else if (inFold == true) {
                foldContents.append(line);
                foldContents.append("\n");

            }
        }//end while.

        if (inFold == true) {
            throw new Exception("Opening or closing fold tag missing in file " + sourceName + " on line " + lineCounter);
        }

        //Close the input stream
        in.close();

        return folds;

    }//end.

    public static void main(String[] args) {
        File mpwFile = new File("/home/tkosan/workspace/mathpiper4/src/org/mathpiper/scripts4/a_initialization/standard/numeric.mpw");

        try {
            List<Fold> folds = scanSourceFile(mpwFile.getPath(), new FileInputStream(mpwFile));

            for (Fold fold : folds) {
                Map attributes = fold.getAttributes();

                Set keys = attributes.keySet();

                for (Object key : keys) {
                    System.out.println(key.toString() + " - " + attributes.get(key));

                    if (attributes.get(key).equals("information")) {
                        String code = fold.getContents();

                        System.out.println(code);

                    }
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
