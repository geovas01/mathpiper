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
package org.mathpiper.ui.gui.consoles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.text.Element;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.ResponseListener;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;

public class GraphicConsole extends javax.swing.JPanel implements ActionListener, KeyListener, ResponseListener, ItemListener, MathPiperOutputStream {

    ResultHolder resultHolder;
    private boolean suppressOutput = false;
    private final Color green = new Color(0, 130, 0);
    private final Color purple = new Color(153, 0, 153);
    private Interpreter interpreter = Interpreters.getAsynchronousInterpreter();
    private StringBuilder input = new StringBuilder();
    private JButton haltButton, clearConsoleButton, clearRawButton, helpButton, smallerFontButton, largerFontButton;
    private JCheckBox rawOutputCheckBox;
    private JCheckBox showRawOutputCheckBox;
    private JTextArea rawOutputTextArea;
    private ColorPane textPane;
    private MathPiperOutputStream currentOutput;
    private JScrollPane typePane;
    private char[] typedKey = new char[1];
    private JPanel consoleButtons;
    private JPanel rawButtons;
    private boolean deleteFlag = false;
    private double zoomScale = 2.0;
    private int fontSize = 12;
    private int resultHolderAdjustment = 2;
    private Font bitstreamVera;
    private StringBuilder inputLines;
    private int responseInsertionOffset = -1;
    private boolean encounteredIn = false;
    private boolean noLinesBetweenInAndEndOfTextArea = false;
    private JSplitPane splitPane;
    private int splitPaneDividerLocation = 400;
    private JScrollPane rawOutputScrollPane;
    private JPanel rawOutputPanel;
    private JPopupMenu Pmenu;
    private Stack history = new java.util.Stack();
    private boolean controlKeyDown = false;
    private int historyIndex = -1;
    private int caretPositionWhenEnterWasPressed = -1;
    private JRadioButton numericModeButton;
    private JRadioButton symbolicModeButton;
    private ButtonGroup resultModeGroup;
    private boolean numericResultMode = false;
    private String helpMessage =
            "Enter an expression after any In> prompt and press <enter> or <shift><enter> to evaluate it.\n\n" +
            "Type In> on the left end of any line to create your own input prompt.\n\n" +
            "Use <ctrl><up arrow> and <ctrl><down arrow> to navigate through the command line history.\n\n" +
            "The console window is an editable text area, so you can add text to it and remove text from \n" +
            "it as needed.\n\n" +
            "Placing ;; after the end of the line of input will suppress the output.\n\n" +
            "The Raw Output checkbox sends all side effects output to the raw output text area.";


    public GraphicConsole() {

        inputLines = new StringBuilder();


        this.setLayout(new BorderLayout());

        //keySendQueue = new java.util.concurrent.ArrayBlockingQueue(30);

        consoleButtons = new JPanel();
        consoleButtons.setLayout(new BoxLayout(consoleButtons, BoxLayout.X_AXIS));


        rawOutputPanel = new JPanel();
        rawOutputPanel.setLayout(new BorderLayout());
        rawButtons = new JPanel();
        rawButtons.setLayout(new BoxLayout(rawButtons, BoxLayout.X_AXIS));



        //textArea = new JTextArea(30, 20);
        textPane = new ColorPane();

        textPane.append(purple, "MathPiper version " + org.mathpiper.Version.version + ".\n");
        textPane.append(purple, "Enter an expression after any In> prompt and press <enter> or <shift><enter> to evaluate it.\n");


        textPane.append(Color.BLACK, "\nIn> \n");
        textPane.setCaretPosition(textPane.getDocument().getLength());

        //java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathpiper.ide.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/ttf-bitstream-vera-1.10/VeraMono.ttf" );

        //bitstreamVera = Font.createFont (Font.TRUETYPE_FONT, inputStream);
        //bitstreamVera = bitstreamVera.deriveFont(fontSize);
        //typeArea.setFont(bitstreamVera);


        textPane.addKeyListener(this);
        typePane = new JScrollPane(textPane);
        //guiBox.add(typePane);



        haltButton = new JButton("Halt Calculation");
        haltButton.setEnabled(false);
        haltButton.setForeground(Color.RED);
        haltButton.addActionListener(this);
        consoleButtons.add(haltButton);



        smallerFontButton = new JButton("Font-");
        smallerFontButton.addActionListener(this);
        consoleButtons.add(smallerFontButton);
        largerFontButton = new JButton("Font+");
        largerFontButton.addActionListener(this);
        consoleButtons.add(largerFontButton);

        rawOutputCheckBox = new JCheckBox("Raw Side Effects");
        rawOutputCheckBox.addItemListener(this);
        rawButtons.add(rawOutputCheckBox);
        this.rawOutputTextArea = new JTextArea();
        rawOutputTextArea.setEditable(false);
        rawOutputTextArea.setText("Raw output text area.\n\n");


        showRawOutputCheckBox = new JCheckBox("Show Raw");
        showRawOutputCheckBox.addItemListener(this);
        consoleButtons.add(showRawOutputCheckBox);

        consoleButtons.add(Box.createGlue());


        clearConsoleButton = new JButton("Clear");
        clearConsoleButton.addActionListener(this);
        consoleButtons.add(clearConsoleButton);


        clearRawButton = new JButton("Clear Raw");
        clearRawButton.addActionListener(this);
        rawButtons.add(clearRawButton);


        helpButton = new JButton("Help");
        helpButton.addActionListener(this);
        consoleButtons.add(helpButton);



        JButton structureButton = new javax.swing.JButton("Structure");
        structureButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                new StructureDialog(textPane).setVisible(true);

            }//end method.

        });
        structureButton.setEnabled(true);
        consoleButtons.add(structureButton);



        JButton testButton = new javax.swing.JButton("Test");
        testButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                MathPiperDocument document = (MathPiperDocument) textPane.getDocument();

                /*SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setFontSize(attrs, fontSize + 5);
                document.setCharacterAttributes(0, document.getLength() + 1, attrs, false);*/

                //document.scanTree(fontSize);




                document.scanViews(textPane, fontSize);

            }//end method.

        });
        testButton.setEnabled(true);
        consoleButtons.add(testButton);



        this.add(consoleButtons, BorderLayout.NORTH);

        this.rawOutputPanel.add(rawButtons, BorderLayout.NORTH);

        //this.add(guiBox, BorderLayout.CENTER);


        rawOutputScrollPane = new JScrollPane(rawOutputTextArea);
        rawOutputPanel.add(rawOutputScrollPane);


        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, typePane, null);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(splitPaneDividerLocation);

        this.add(splitPane);



        this.addPopupMenu();






    }//Constructor.


    public void actionPerformed(ActionEvent event) {
        Object src = event.getSource();

        if (src == haltButton) {
            interpreter.haltEvaluation();
        } else if (src == smallerFontButton) {
            this.fontSize -= 2;

            MathPiperDocument document = (MathPiperDocument) textPane.getDocument();
            /*
            document.putProperty("ZOOM_FACTOR", new Double(zoomScale));
            document.refresh();*/

            this.setJTextPaneFont(textPane, fontSize);
            document.scanViews(textPane, fontSize + resultHolderAdjustment);
        } else if (src == largerFontButton) {
            this.fontSize += 2;

            MathPiperDocument document = (MathPiperDocument) textPane.getDocument();
            /*document.putProperty("ZOOM_FACTOR", new Double(zoomScale));
            document.refresh();*/

            this.setJTextPaneFont(textPane, fontSize);
            document.scanViews(textPane, fontSize + resultHolderAdjustment);


        } else if (src == helpButton) {
            JOptionPane.showMessageDialog(this, this.helpMessage);
        } else if (src == clearConsoleButton) {
            this.textPane.setText("");
            this.textPane.append(Color.BLACK, "In> ");
        } else if (src == clearRawButton) {
            this.rawOutputTextArea.setText("");
        }

    }//end method.


    public void itemStateChanged(ItemEvent ie) {
        Object source = ie.getSource();

        if (source == rawOutputCheckBox) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                Environment environment = interpreter.getEnvironment();
                this.currentOutput = environment.iCurrentOutput;
                environment.iCurrentOutput = this;
            } else {
                Environment environment = interpreter.getEnvironment();
                environment.iCurrentOutput = this.currentOutput;
            }//end if/else.
        } else if (source == showRawOutputCheckBox) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                splitPane.add(rawOutputPanel);
                splitPane.setDividerLocation(splitPaneDividerLocation);
                splitPane.revalidate();
            } else {
                splitPane.remove(2);
                splitPane.revalidate();
            }//end if/else.
        } else if (source == numericModeButton) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                this.numericResultMode = true;
            } else {
                this.numericResultMode = false;
            }//end if/else.
        } else if (source == symbolicModeButton) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                this.numericResultMode = false;
            } else {
                this.numericResultMode = true;
            }//end if/else.

        }//end if/else.
    }//end method.


    public void putChar(char aChar) throws Exception {
        if (rawOutputTextArea != null && currentOutput != null) {
            this.rawOutputTextArea.append("" + aChar);
            this.rawOutputTextArea.setCaretPosition(this.rawOutputTextArea.getDocument().getLength());
            this.currentOutput.putChar(aChar);
        }//end if.
    }//end method.


    public void write(String aString) throws Exception {
        int i;
        for (i = 0; i < aString.length(); i++) {
            putChar(aString.charAt(i));
        }
    }//end method.


    public void keyPressed(KeyEvent e) {
        int keyCode = (int) e.getKeyCode();

        if (keyCode == KeyEvent.VK_CONTROL) {
            this.controlKeyDown = true;
        }//end if.


        if (keyCode == KeyEvent.VK_UP && this.controlKeyDown) {
            //System.out.println("up");

            if (!history.empty() && historyIndex != history.size() - 1) {


                historyIndex++;
                //System.out.println(history.get((history.size()-1) - historyIndex));

                try {
                    int lineNumber = textPane.getLineOfOffset(textPane.getCaretPosition());
                    int lineStartOffset = textPane.getLineStartOffset(lineNumber);
                    int lineEndOffset = textPane.getLineEndOffset(lineNumber);

                    textPane.replaceRange("In> " + (String) history.get((history.size() - 1) - historyIndex), lineStartOffset, lineEndOffset);

                } catch (BadLocationException ble) {
                    //Eat exception.
                }


            }//end if.

        }//end if.


    }//end method.


    public void keyReleased(KeyEvent e) {
        int keyCode = (int) e.getKeyCode();

        if (keyCode == KeyEvent.VK_CONTROL) {
            this.controlKeyDown = false;
        }//end if.


        if (keyCode == KeyEvent.VK_DOWN && this.controlKeyDown) {
            //System.out.println("down");

            if (!history.empty() && (!(historyIndex < 1))) {


                historyIndex--;
                //System.out.println(history.get((history.size()-1) - historyIndex));

                try {
                    int lineNumber = textPane.getLineOfOffset(textPane.getCaretPosition());
                    int lineStartOffset = textPane.getLineStartOffset(lineNumber);
                    int lineEndOffset = textPane.getLineEndOffset(lineNumber);

                    textPane.replaceRange("In> " + (String) history.get((history.size() - 1) - historyIndex), lineStartOffset, lineEndOffset);

                } catch (BadLocationException ble) {
                    //Eat exception.
                }

            } else if (!history.empty() && historyIndex == 0) {
                try {
                    int lineNumber = textPane.getLineOfOffset(textPane.getCaretPosition());
                    int lineStartOffset = textPane.getLineStartOffset(lineNumber);
                    int lineEndOffset = textPane.getLineEndOffset(lineNumber);

                    textPane.replaceRange("In> ", lineStartOffset, lineEndOffset);

                    this.historyIndex = -1;

                } catch (BadLocationException ble) {
                    //Eat exception.;
                }

            }//end else.
        }//end if.

    }//end method.


    public void keyTyped(KeyEvent e) {

        char key = e.getKeyChar();

        //System.out.println((int)key);

        if ((int) key == e.VK_ENTER || (int) key == 13) { //== 10) {
            try {

                //System.out.println("key pressed"); //TODO remove.

                //System.out.println("LN: " + lineNumber + "  LSO: " + lineStartOffset + "  LEO: " + lineEndOffset  );
                if (!e.isShiftDown()) {
                    textPane.replaceRange("", textPane.getCaretPosition() - 1, textPane.getCaretPosition());
                }//end if.

                caretPositionWhenEnterWasPressed = textPane.getCaretPosition();

                int lineNumber = textPane.getLineOfOffset(textPane.getCaretPosition());
                //lineNumber--;
                String line = "";

                int lineStartOffset = textPane.getLineStartOffset(lineNumber);

                int lineEndOffset = textPane.getLineEndOffset(lineNumber);

                line = textPane.getText(lineStartOffset, lineEndOffset - lineStartOffset);

                if (line.startsWith("In>") && line.substring(3).trim().equals("")) {
                    
                }
                else if (line.startsWith("In>")) {

                    String eol = new String(line);
                    String code = line.substring(3, line.length()).trim();
                    responseInsertionOffset = lineEndOffset;
                    if (!eol.endsWith(";") && !eol.endsWith("\\\n")) {
                        code = code + ";";
                    }//end if.

                    clearPreviousResponse();


                    // System.out.println("1: " + code);

                    if (code.endsWith(";;")) {
                        this.suppressOutput = true;
                    }

                    code = code.replaceAll(";;;", ";");
                    code = code.replaceAll(";;", ";");

                    //code = code.replaceAll("\\\\", "");

                    //System.out.println("2: " + code);

                    history.push(code.substring(0, code.length() - 1));
                    this.historyIndex = -1;

                    if (code.length() > 0) {
                        interpreter.addResponseListener(this);
                        interpreter.evaluate("[" + code + "];", true);
                        haltButton.setEnabled(true);

                    }//end if.


                } else {
                    textPane.insert(Color.BLACK, "\n", caretPositionWhenEnterWasPressed);
                }



                //input.delete(0, input.length());
                // typeArea.append(response.getResult());

            } catch (BadLocationException ex) {
                System.out.println(ex.getMessage() + " , " + ex.offsetRequested());
            }

            //typeArea.append(new String(typedKey));
            //typeArea.setCaretPosition( typeArea.getDocument().getLength() );
       /* } else if ((int) key == 22) {
            try {
            String clipBoard = (String) java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getData(java.awt.datatransfer.DataFlavor.stringFlavor);

            if (clipBoard.length() != 0) {
            char[] chars = clipBoard.toCharArray();
            for (int x = 0; x < chars.length; x++) {
            //buffer.put((int) chars[x]);
            }//end for.
            //setReceiveDataRegisterFull(true);
            }//end if.

            } catch (NullPointerException ev) {
            ev.printStackTrace();
            } catch (IllegalStateException ev) {
            ev.printStackTrace();
            } catch (java.awt.datatransfer.UnsupportedFlavorException ev) {
            ev.printStackTrace();
            } catch (java.io.IOException ev) {
            ev.printStackTrace();
            }//*/
        } else {
            //System.out.println(key);
            //registers[0] = (int) key;
            if ((int) key == e.VK_BACK_SPACE) {  //== 8) {
                deleteFlag = true;
            }

            input.append(key);
            //typeArea.append(Character.toString(key));
            //buffer.put((int) key);
            //setReceiveDataRegisterFull(true);
        }
    }//end method.


    public void response(EvaluationResponse response) {
        //ResultHolder resultHolder = null;

        Object responseObject = response.getObject();
        if (responseObject == null && response.getResultList() != null) {

            try {
                Interpreter syncronousInterpreter = Interpreters.getSynchronousInterpreter();

                //Evaluate Hold function.
                Cons holdAtomCons = AtomCons.getInstance(syncronousInterpreter.getEnvironment(), -1, "Hold");
                holdAtomCons.cdr().setCons(response.getResultList().getCons());
                Cons holdSubListCons = SublistCons.getInstance(syncronousInterpreter.getEnvironment(), holdAtomCons);
                ConsPointer holdInputExpressionPointer = new ConsPointer(holdSubListCons);


                //Evaluate TeXForm function.
                Cons texFormAtomCons = AtomCons.getInstance(syncronousInterpreter.getEnvironment(), -1, "TeXForm");
                texFormAtomCons.cdr().setCons(holdInputExpressionPointer.getCons());
                Cons texFormSubListCons = SublistCons.getInstance(syncronousInterpreter.getEnvironment(), texFormAtomCons);
                ConsPointer texFormInputExpressionPointer = new ConsPointer(texFormSubListCons);
                EvaluationResponse latexResponse = syncronousInterpreter.evaluate(texFormInputExpressionPointer);

                String latexString = latexResponse.getResult();

                latexString = Utility.stripEndQuotes(latexString);

                latexString = Utility.stripEndDollarSigns(latexString);

                resultHolder = new ResultHolder(latexString, response.getResult(), fontSize + resultHolderAdjustment);


                //Set the % variable to the original result.
                Environment iEnvironment = syncronousInterpreter.getEnvironment();
                String percent = (String) iEnvironment.getTokenHash().lookUp("%");
                iEnvironment.setGlobalVariable(-1, percent, response.getResultList(), true);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultHolder = new ResultHolder(response.getResult(), response.getResult(), (int) (zoomScale + 20));
        }



        //final int caretPosition = responseInsertionOffset;

        int offsetIndex = responseInsertionOffset;

        final int initialOffset = offsetIndex;

        String extraNewline = "";
        if (!encounteredIn) {

            if (noLinesBetweenInAndEndOfTextArea == true) {
                extraNewline = "\n";// + result + "\n\nIn> ";
                offsetIndex++;
            }

        }//end if.*/

        final int responseOffset = offsetIndex;
        String result;
        if (this.suppressOutput == false) {
            result = "Result: ";// + response.getResult().trim();
        } else {
            result = "Result: " + "OUTPUT SUPPRESSED";

            this.suppressOutput = false;
        }


        String sideEffects = null;
        int sideEffectsOffset = 0;
        int sideEffectsLength = 0;
        if (!response.getSideEffects().equalsIgnoreCase("")) {
            sideEffectsOffset = responseOffset + result.length();
            sideEffects = "\nSide Effects:\n" + response.getSideEffects();
            sideEffectsLength = sideEffects.length();
        }


        String exception = null;
        int exceptionOffset = 0;
        int exceptionLength = 0;
        if (response.isExceptionThrown()) {
            exceptionOffset = responseOffset + result.length() + sideEffectsOffset;
            exception = "\nException: " + response.getExceptionMessage();
            exceptionLength = exception.length();
        }





        final String finalExtraNewline = extraNewline;
        final String finalResult = result;
        final String finalSideEffects = sideEffects;
        final String finalException = exception;

        final int finalSideEffectsOffset = sideEffectsOffset;
        final int finalExceptionOffset = exceptionOffset;
        final int insertInOffset = responseOffset + result.length() + sideEffectsLength + exceptionLength;
        final int finalCaretPositionWhenEnterWasPressed = caretPositionWhenEnterWasPressed;
        final ResultHolder resultHolderFinal = resultHolder;



        /* if (insertionPointLine == lineCount - 1) {
        SwingUtilities.invokeLater(new Runnable() {

        public void run() {
        haltButton.setEnabled(false);
        textArea.append(Color.BLACK, finalOutput);
        }


        });

        //textArea.setCaretPosition( textArea.getDocument().getLength() );
        } else {*/
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                haltButton.setEnabled(false);


                textPane.insert(Color.BLACK, finalExtraNewline, initialOffset); //finalExtraNewline

                textPane.insert(Color.BLUE, finalResult, responseOffset);

                if (finalSideEffects != null) {
                    textPane.insert(green, finalSideEffects, finalSideEffectsOffset);
                }

                if (finalException != null) {
                    textPane.insert(Color.RED, finalException, finalExceptionOffset);
                }


                if (!encounteredIn) {

                    textPane.insert(Color.BLACK, "\n\nIn> ", insertInOffset);

                } else {
                    //textPane.setCaretPosition(caretPosition - 1);
                    textPane.setCaretPosition(finalCaretPositionWhenEnterWasPressed);

                }


                try {
                    // Get the text pane's document JTextPane
                    StyledDocument doc = (StyledDocument) textPane.getDocument();
                    // The component must first be wrapped in a style
                    Style style = doc.addStyle("StyleName", null);
                    StyleConstants.setComponent(style, resultHolderFinal);
                    // Insert the component at the end of the text
                    int currentCaretPosition = textPane.getCaretPosition();

                    doc.insertString(responseOffset + 8, "ignored text", style);
                    //textPane.setCaretPosition(responseOffset + 8);
                    //textPane.insertComponent(resultHolderFinal);
                    //textPane.setCaretPosition(currentCaretPosition);
                    //textPane.insert(Color.red, "hello", responseOffset + 8);


                } catch (BadLocationException e) {
                    e.printStackTrace();
                }


            }//end method.

        });

        //}//end if/else.

    }//end method.


    public boolean remove() {
        return true;
    }


    private void clearPreviousResponse() {

        try {
            int lineNumber = textPane.getLineOfOffset(responseInsertionOffset - 1);

            if (responseInsertionOffset == -1 || lineNumber == textPane.getLineCount()) {
                encounteredIn = false;
                return;
            }

            String line = "";
            int lineStartOffset = 0;
            int lineEndOffset = 0;

            do {

                lineNumber++;
                lineStartOffset = textPane.getLineStartOffset(lineNumber);
                lineEndOffset = textPane.getLineEndOffset(lineNumber);
                line = textPane.getText(lineStartOffset, lineEndOffset - lineStartOffset);

            } while (!line.startsWith("In>") && lineNumber < textPane.getLineCount());

            textPane.replaceRange("\n\n\n", responseInsertionOffset - 1, lineStartOffset);
            encounteredIn = line.startsWith("In>");
            return;

        } catch (BadLocationException ex) {
            encounteredIn = false;
            textPane.replaceRange("\n\n\n", responseInsertionOffset, textPane.getDocument().getLength());
            return;
        }
    }//end method.


    private void captureInputLines(int lineNumber) {

        inputLines.delete(0, inputLines.length());

        try {
            int lineStartOffset = textPane.getLineStartOffset(lineNumber);
            int lineEndOffset = textPane.getLineEndOffset(lineNumber);
            String line = textPane.getText(lineStartOffset, lineEndOffset - lineStartOffset);

            if (line.startsWith("In>")) {

                //Scan backwards to first line that does not start with In>.
                do {
                    lineStartOffset = textPane.getLineStartOffset(lineNumber);
                    lineEndOffset = textPane.getLineEndOffset(lineNumber);
                    line = textPane.getText(lineStartOffset, lineEndOffset - lineStartOffset);
                    lineNumber--;

                } while (line.startsWith("In>") && lineNumber != -1);//end do/while.

                if (lineNumber != -1) {
                    lineNumber++;
                }


                //Scan forwards to first line that does not start with In>.
                boolean pastInputLines = false;
                noLinesBetweenInAndEndOfTextArea = false;
                do {
                    lineNumber++;
                    lineStartOffset = textPane.getLineStartOffset(lineNumber);
                    lineEndOffset = textPane.getLineEndOffset(lineNumber);
                    line = textPane.getText(lineStartOffset, lineEndOffset - lineStartOffset).trim();
                    if (line.startsWith("In>")) {
                        String eol = new String(line);
                        inputLines.append(line.substring(3, line.length()).trim());
                        responseInsertionOffset = lineEndOffset;
                        if (!eol.endsWith(";") && !eol.endsWith("\\\n")) {
                            inputLines.append(";");
                        }//end if.
                    } else {
                        pastInputLines = true;
                    }


                } while (!pastInputLines && lineNumber < textPane.getLineCount());//end while.

            }//end if.*/

        } catch (BadLocationException ex) {
            noLinesBetweenInAndEndOfTextArea = true;
        }


    }//end method.


    public void setHaltButtonEnabledState(boolean state) {
        this.haltButton.setEnabled(state);

    }//end method.

    public class ColorPane extends JTextPane {

        public ColorPane() {
            super();
            //this.getDocument().putProperty("i18n", Boolean.FALSE);
            //this.getDocument().putProperty("ZOOM_FACTOR", new Double(zoomScale));
            this.setDocument(new MathPiperDocument());

        }


        public void append(Color c, String s) { // better implementation--uses // StyleContext.
            //MutableAttributeSet attrs = getInputAttributes();

            SimpleAttributeSet attrs = new SimpleAttributeSet();
            //StyleConstants.setFontSize(attrs, fontSize);

            StyleConstants.setForeground(attrs, c);

            int len = getDocument().getLength(); // same value as // getText().length();
            setCaretPosition(len); // place caret at the end (with no selection).
            setCharacterAttributes(attrs, false);
            replaceSelection(s); // there is no selection, so inserts at caret.
        }//end method.


        public void insert(Color c, String str, int pos) {

            Font font = getFont();

            //MutableAttributeSet attrs = getInputAttributes();
            SimpleAttributeSet attrs = new SimpleAttributeSet();

            //StyleConstants.setFontFamily(attrs, font.getFamily());
            //StyleConstants.setFontSize(attrs, fontSize);
            StyleConstants.setForeground(attrs, c);

            //StyleContext sc = StyleContext.getDefaultStyleContext();
            //MutableAttributeSet aset = this.getInputAttributes();
            //AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            setCaretPosition(pos); // place caret at the end (with no selection)
            setCharacterAttributes(attrs, false);
            replaceSelection(str);



        }


        /**
         * Translates an offset into the components text to a
         * line number.
         *
         * @param offset the offset >= 0
         * @return the line number >= 0
         * @exception BadLocationException thrown if the offset is
         *   less than zero or greater than the document length.
         */
        public int getLineOfOffset(int offset) throws BadLocationException {
            Document doc = getDocument();
            if (offset < 0) {
                throw new BadLocationException("Can't translate offset to line", -1);
            } else if (offset > doc.getLength()) {
                throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
            } else {
                Element map = getDocument().getDefaultRootElement();
                return map.getElementIndex(offset);
            }
        }


        /**
         * Determines the number of lines contained in the area.
         *
         * @return the number of lines > 0
         */
        public int getLineCount() {
            Element map = getDocument().getDefaultRootElement();
            return map.getElementCount();
        }


        /**
         * Determines the offset of the start of the given line.
         *
         * @param line  the line number to translate >= 0
         * @return the offset >= 0
         * @exception BadLocationException thrown if the line is
         * less than zero or greater or equal to the number of
         * lines contained in the document (as reported by
         * getLineCount).
         */
        public int getLineStartOffset(int line) throws BadLocationException {
            int lineCount = getLineCount();
            if (line < 0) {
                throw new BadLocationException("Negative line", -1);
            } else if (line >= lineCount) {
                throw new BadLocationException("No such line", getDocument().getLength() + 1);
            } else {
                Element map = getDocument().getDefaultRootElement();
                Element lineElem = map.getElement(line);
                return lineElem.getStartOffset();
            }
        }


        /**
         * Determines the offset of the end of the given line.
         *
         * @param line  the line >= 0
         * @return the offset >= 0
         * @exception BadLocationException Thrown if the line is
         * less than zero or greater or equal to the number of
         * lines contained in the document (as reported by
         * getLineCount).
         */
        public int getLineEndOffset(int line) throws BadLocationException {
            int lineCount = getLineCount();
            if (line < 0) {
                throw new BadLocationException("Negative line", -1);
            } else if (line >= lineCount) {
                throw new BadLocationException("No such line", getDocument().getLength() + 1);
            } else {
                Element map = getDocument().getDefaultRootElement();
                Element lineElem = map.getElement(line);
                int endOffset = lineElem.getEndOffset();
                // hide the implicit break at the end of the document
                return ((line == lineCount - 1) ? (endOffset - 1) : endOffset);
            }
        }


        /**
         * Replaces text from the indicated start to end position with the
         * new text specified.  Does nothing if the model is null.  Simply
         * does a delete if the new string is null or empty.
         * <p>
         * This method is thread safe, although most Swing methods
         * are not.
         *
         * @param str the text to use as the replacement
         * @param start the start position >= 0
         * @param end the end position >= start
         * @exception IllegalArgumentException  if part of the range is an
         *  invalid position in the model
         * @see #insert
         * @see #replaceRange
         */
        public void replaceRange(String str, int start, int end) {
            if (end < start) {
                throw new IllegalArgumentException("end before start");
            }


            Font font = getFont();

            MutableAttributeSet attrs = getInputAttributes();


            StyleConstants.setFontFamily(attrs, font.getFamily());
            StyleConstants.setFontSize(attrs, fontSize);


            setCharacterAttributes(attrs, false);
            this.select(start, end);
            replaceSelection(str);

        }//end method.

    }//end class


    public void setJTextPaneFont(JTextPane textPane, int fontSize) {

        StyledDocument document = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, fontSize);
        document.setCharacterAttributes(0, document.getLength() + 1, attrs, false);
        document.setParagraphAttributes(0, document.getLength() + 1, attrs, false);

    }//end method.

    public static class PopupTriggerMouseListener extends MouseAdapter {

        private JPopupMenu popup;
        private JComponent component;


        public PopupTriggerMouseListener(JPopupMenu popup, JComponent component) {
            this.popup = popup;
            this.component = component;
        }

        //some systems trigger popup on mouse press, others on mouse release, we want to cater for both

        private void showMenuIfPopupTrigger(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(component, e.getX() + 3, e.getY() + 3);
            }
        }

        //according to the javadocs on isPopupTrigger, checking for popup trigger on mousePressed and mouseReleased
        //should be all  that is required
        //public void mouseClicked(MouseEvent e)

        public void mousePressed(MouseEvent e) {
            showMenuIfPopupTrigger(e);
        }


        public void mouseReleased(MouseEvent e) {
            showMenuIfPopupTrigger(e);
        }

    }//end method.


    private void addPopupMenu() {
        final JPopupMenu menu = new JPopupMenu();
        final JMenuItem copyItem = new JMenuItem();
        copyItem.setAction(textPane.getActionMap().get(DefaultEditorKit.copyAction));
        copyItem.setText("Copy");

        final JMenuItem cutItem = new JMenuItem();
        cutItem.setAction(textPane.getActionMap().get(DefaultEditorKit.cutAction));
        cutItem.setText("Cut");

        final JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.setAction(textPane.getActionMap().get(DefaultEditorKit.pasteAction));
        pasteItem.setText("Paste");

        final JMenuItem selectAllItem = new JMenuItem("Select All");
        selectAllItem.setAction(textPane.getActionMap().get(DefaultEditorKit.selectAllAction));
        selectAllItem.setText("Select All");

        final JMenuItem insertPrompt = new JMenuItem("Insert In>");
        insertPrompt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textPane.insert(Color.BLACK, "In> ", textPane.getCaretPosition());
            }

        });
        insertPrompt.setText("Insert In>");

        menu.add(copyItem);
        menu.add(cutItem);
        menu.add(pasteItem);
        menu.add(new JSeparator());
        menu.add(selectAllItem);
        menu.add(new JSeparator());
        menu.add(insertPrompt);


        textPane.add(menu);
        textPane.addMouseListener(new PopupTriggerMouseListener(menu, textPane));
    }//end method.


    public static void main(String[] args) {
        GraphicConsole console = new GraphicConsole();

        JFrame frame = new javax.swing.JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.add(console, BorderLayout.CENTER);
        //frame.setAlwaysOnTop(true);
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setTitle("Graphic Console");
        //frame.setResizable(false);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null); // added
        frame.pack();
        frame.setVisible(true);
    }//end main.
}//end class.

