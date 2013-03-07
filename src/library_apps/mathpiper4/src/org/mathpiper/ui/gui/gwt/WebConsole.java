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
 *///}}}
package org.mathpiper.ui.gui.gwt;


import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.SynchronousInterpreter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
					boolean isStillInitializing = interpreterInstance
							.initialize();

					if (!isStillInitializing) {
						loopIndex++;
					}
					break;
				default:

					returnValue = false;

					// callCasLoaded();

					break;

				}// end switch.

				return returnValue;

			}
		};

		Scheduler.get().scheduleIncremental(repeatingCommand);
	}

	public void onModuleLoad() {

		// DockLayoutPanel appPanel = new DockLayoutPanel(Unit.EM);
		// RootLayoutPanel.get().add(appPanel);

		HeaderPanel hp = new HeaderPanel();
		

		final RichTextArea textArea = new RichTextArea();
		textArea.setWidth("100%");
		textArea.setHeight("100%"); 
		
		//Toolbar
		//final RichTextToolbar toolBar = new RichTextToolbar(textArea);
		//hp.setHeaderWidget(toolBar);
		

		// RootPanel.get("target").add(vp);

		final Button sendButton = new Button("Evaluate");
		// final TextBox inputBox = new TextBox();

		// inputBox.setMaxLength(80);
		// inputBox.setVisibleLength(80);
		final RichTextArea inputArea = new RichTextArea();
		inputArea.setWidth("100%");
		//inputArea.getFormatter().setFontSize(RichTextArea.FontSize.MEDIUM);
		

		
		
		String welcomeMessage = "//MathPiper version " + org.mathpiper.Version.version() + 
				"\n//Enter code into this text area and press <shift><enter> to evaluate it.\n\n";
		
		
		inputArea.setText(welcomeMessage);
	

		
		
		hp.setContentWidget(textArea);
		


		hp.setFooterWidget(inputArea);

		sendButton.addStyleName("sendButton");

		// RootPanel.get("sendButtonContainer").add(sendButton);
		// RootPanel.get("nameFieldContainer").add(inputBox);
		// RootPanel.get("outputContainer").add(outputArea);

		RootLayoutPanel.get().add(hp);

		class MyHandler implements ClickHandler, KeyDownHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				evaluate();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyDown(KeyDownEvent event) {
				
				if (event.isShiftKeyDown() && event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					event.preventDefault();
					evaluate();
				}
			}

			private void evaluate() {
				String expression = inputArea.getText();
				
				expression = expression.trim();
				
				if(! expression.endsWith(";"))
				{
					expression = expression + ";";
				}
				
				expression = expression.replace("\\","\\\\");
				expression = expression.replace("\"","\\\"");

				EvaluationResponse evaluationResponse = interpreterInstance.evaluate("LoadScript(\"" + expression + "\");");
				
				String result = evaluationResponse.getResult();
				
				String errorResult = "";

				if (evaluationResponse.isExceptionThrown())
				{

					Throwable exception = evaluationResponse.getException();

					if (exception instanceof org.mathpiper.exceptions.EvaluationException) {
						EvaluationException evaluationException = (org.mathpiper.exceptions.EvaluationException) exception;

						int errorLineNumber = evaluationException.getLineNumber() - 1;

						int startIndex = evaluationException.getStartIndex();

						int endIndex = evaluationException.getEndIndex();

						//<FONT COLOR="######">text text text text text</FONT>

						errorResult = "<FONT COLOR=\"FF0000\">" + evaluationException.getMessage()
								+ " Error near line "
								+ (errorLineNumber + 1)
								+ " starting at index " + startIndex + ".</FONT>";



					} else {
						errorResult = "<FONT COLOR=\"FF0000\">" + exception.getMessage() + "</FONT>";
					}
					

					if (!evaluationResponse.getSideEffects().equalsIgnoreCase(
							"")) {
						result = "<FONT COLOR=\"0000FF\">Result: </FONT>" + errorResult 
								+ "<br /><FONT COLOR=\"008200\">Side Effects:</FONT><br />"
								+ evaluationResponse.getSideEffects().replace("\n", "<br />\n ");
					} else {
						result = "<FONT COLOR=\"0000FF\">Result: " + errorResult + "</FONT>" + "<br />\n";
					}

				}

				else if (!evaluationResponse.getSideEffects().equalsIgnoreCase(
						"")) {
					result = "<FONT COLOR=\"0000FF\">Result: </FONT>" + evaluationResponse.getResult()
							+ "<br />\n  <FONT COLOR=\"008200\">Side Effects:</FONT><br />\n "
							+ evaluationResponse.getSideEffects();
				} else {
					result = "<FONT COLOR=\"0000FF\">Result: </FONT>" + result;
				}
				
				result = result + "<br />\n <br />\n  ";

				textArea.getFormatter().insertHTML(result);
				
				//Scroll to bottom of the text area.
				IFrameElement iframe = IFrameElement.as(textArea.getElement());
                                Document document = iframe.getContentDocument();
                                document.setScrollTop(document.getScrollHeight());
				
				inputArea.setFocus(true);
			}

		}// end class

		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		inputArea.addKeyDownHandler(handler);
		
		initializeCAS();
		
		inputArea.setFocus(true);
		

	}// end method.
}// end class.

