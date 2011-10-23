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
package org.mathpiper.interpreters;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class JavaScript implements EntryPoint {

    private static Interpreter interpreterInstance;

    public JavaScript() {
        interpreterInstance = SynchronousInterpreter.getInstance();

    }

    public static void initializeCAS() {
        RepeatingCommand repeatingCommand = new RepeatingCommand() {

            private int loopIndex = 1;

            public boolean execute() {


                boolean returnValue = true;


                switch (loopIndex) {
                    case 1:
                        boolean isStillInitializing = interpreterInstance.initialize();

                        if (!isStillInitializing) {
                            loopIndex++;
                        }
                        break;
                    default:


                        returnValue = false;

                        callCasLoaded();

                        break;

                }//end switch.

                return returnValue;

            }
        };

        Scheduler.get().scheduleIncremental(repeatingCommand);
    }

    //---------
    public static String casEvaluate(String send) {
        EvaluationResponse evaluationResponse = interpreterInstance.evaluate(send);

        return evaluationResponse.getResult();
    }

    //---------
    public static native void exportCasVersionMethod() /*-{
    $wnd.casVersion = function(){
    return @org.mathpiper.Version::version()();
    }
    }-*/;

    //---------
    public static native void callCasLoaded() /*-{
    $wnd.casLoaded();
    }-*/;

    public static native void exportEvaluateMethod() /*-{
    $wnd.casEval = function(send){
    return @org.mathpiper.interpreters.JavaScript::casEvaluate(Ljava/lang/String;)(send);
    }
    }-*/;

    public void onModuleLoad() {
        exportCasVersionMethod();

        exportEvaluateMethod();

        initializeCAS();

    }//end method.
}//end class.

