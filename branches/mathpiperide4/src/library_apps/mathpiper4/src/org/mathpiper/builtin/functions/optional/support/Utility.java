package org.mathpiper.builtin.functions.optional.support;

import org.mathpiper.io.InputStatus;
import org.mathpiper.io.MathPiperInputStream;

public class Utility {
	
    public static MathPiperInputStream openInputFile(String aFileName, InputStatus aInputStatus) throws Exception {//Note:tk:primary method for file opening.

        try {
                return new FileInputStream(aFileName, aInputStatus);
        } catch (Throwable e) {
            //MathPiper eats this exception because returning null indicates to higher level code that the file was not found.
        }
        return null;
    }
	

}
