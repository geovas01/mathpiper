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
	
	
	public MPWSFile()
	{
		super();
	}
	
	
	public static Map getFoldsMap(InputStream inputStream) throws Throwable
	{
		
		Map<String,Fold> namedFolds = new HashMap();
		
		List<Fold> folds = scanSourceFile(inputStream);
		
		for(Fold fold:folds)
		{
			Map<String,String> attributes = fold.getAttributes();
			
			Set<String> keys = attributes.keySet();
			
			for(String key: keys)
			{
				
				if(key.equalsIgnoreCase("name"))
				{
					String name = attributes.get(key);
					namedFolds.put(name, fold);
				}
			}
		}
		
		return namedFolds;
		
	}//end method.
	
	
	
    public static List scanSourceFile(InputStream inputStream) throws Throwable {

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


        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        //Read File Line By Line
        while ((line = br.readLine()) != null) {
            
            //System.out.println(line);
            lineCounter++;

            if (line.contains("%/")) {
        	
        	String tempLine = line;
        	
	        if(tempLine.startsWith("."))
	        {
	            tempLine = line.substring(1);
	        }
	       
	            
	        if (tempLine.trim().startsWith("%/"))
	        {
	            	        
                    if (inFold == false) {
                        throw new Exception("Opening fold tag missing on line " + lineCounter);
                    }
        
                    Fold fold = new Fold(startLineNumber, foldHeader, foldContents.toString());
                    foldContents.delete(0, foldContents.length());
                    folds.add(fold);
                    inFold = false;
	        }
	        

            } else if (line.trim().startsWith("%") && line.trim().length() > 2 && line.trim().charAt(1) != ' ') {
        	
                if (inFold == true) {
                    throw new Exception("Closing fold tag missing on line " + lineCounter);
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
            throw new Exception("Opening or closing fold tag missing on line " + lineCounter);
        }

        //Close the input stream
        in.close();


        return folds;

    }//end.
    
    
    
    public static void main(String[] args)
    {
    	File mpwFile = new File("/home/tkosan/workspace/mathpiper4/src/org/mathpiper/scripts4/proposed/miscellaneous/CombinationsList.mpw");
    	
    	try
    	{
    		List<Fold> folds = scanSourceFile(new FileInputStream(mpwFile));
    		
    		for(Fold fold:folds)
    		{
    			Map attributes = fold.getAttributes();
    			
    			Set keys = attributes.keySet();
    			
    			for(Object key: keys)
    			{
    				System.out.println(key.toString() + " - " + attributes.get(key));
    				
    				if(attributes.get(key).equals("information"))
    				{
    					String code = fold.getContents();
    					
    					System.out.println(code);
    					
    				}
    			}
    		}
    		
    		
    		
    	}
    	catch(Throwable e)
    	{
    		e.printStackTrace();
    	}
    }



}
