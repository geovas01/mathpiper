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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class WebConsole implements EntryPoint {

    private static Interpreter interpreterInstance;

    public WebConsole() {
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

                        //callCasLoaded();

                        break;

                }//end switch.

                return returnValue;

            }
        };

        Scheduler.get().scheduleIncremental(repeatingCommand);
    }



    public void onModuleLoad() {


        initializeCAS();
        
		final Button sendButton = new Button("Evaluate");
		final TextBox inputBox = new TextBox();
		inputBox.setMaxLength(80);
		inputBox.setVisibleLength(80);
		final TextArea outputArea = new TextArea();
		

		sendButton.addStyleName("sendButton");

		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("nameFieldContainer").add(inputBox);
		RootPanel.get("outputContainer").add(outputArea);
		
		
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				evaluate();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					evaluate();
				}
			}



			private void evaluate() {
				String expression = inputBox.getText();
				EvaluationResponse response = interpreterInstance.evaluate(expression);
				
				outputArea.setText(response.getResult());
			}

		}//end class	
		
		
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		inputBox.addKeyUpHandler(handler);

    }//end method.
}//end class.

