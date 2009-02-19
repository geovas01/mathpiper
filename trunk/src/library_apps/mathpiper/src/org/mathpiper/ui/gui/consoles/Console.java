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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.ResponseListener;

public class Console extends javax.swing.JPanel implements ActionListener, KeyListener, ResponseListener {

    private Interpreter interpreter = Interpreters.getAsynchronousInterpreter();
    private StringBuilder input = new StringBuilder();
    private JButton button1,  button2,  button3;
    private JTextArea textArea;
    private JScrollPane typePane;
    private char[] typedKey = new char[1];
    private JPanel buttons;
    private boolean deleteFlag = false;
    private float fontSize = 12;
    private Font bitstreamVera;
    private StringBuilder inputLines;
    private int responseInsertionOffset = -1;
    private boolean encounteredIn = false;

    public Console() {
      
        inputLines = new StringBuilder();


        this.setLayout(new BorderLayout());

        //keySendQueue = new java.util.concurrent.ArrayBlockingQueue(30);

        buttons = new JPanel();

        Box guiBox = new Box(BoxLayout.Y_AXIS);
        textArea = new JTextArea(30, 20);
        textArea.append("MathPiper version " + org.mathpiper.Version.version + ".\n");
        textArea.append("Enter an expression after any In> prompt and press <shift><enter> to evaluate it.\n");
        textArea.append("Press <enter> after an expression to create an additional input line and to append a hidden ;.\n");
        textArea.append("Press <shift><enter> after any input line in a group of input lines to execute them all.\n");
        textArea.append("Type In> on the left edge of any line to create your own input prompt.\n");
        textArea.append("Press <enter> after an empty In> to erase the In>.\n");
        textArea.append("Any line in a group that ends with a \\ will not have a ; appended to it.\n");
        textArea.append("Pressing <ctrl><enter> at the end of a line automatically appends a \\ to the line.\n");

        textArea.append("\nIn> ");
        textArea.setCaretPosition(textArea.getDocument().getLength());

        //java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/ttf-bitstream-vera-1.10/VeraMono.ttf" );

        //bitstreamVera = Font.createFont (Font.TRUETYPE_FONT, inputStream);
        //bitstreamVera = bitstreamVera.deriveFont(fontSize);
        //typeArea.setFont(bitstreamVera);


        textArea.addKeyListener(this);
        typePane = new JScrollPane(textArea);
        guiBox.add(typePane);

        Box ioBox = new Box(BoxLayout.Y_AXIS);

        button1 = new JButton("Halt Calculation");
        button1.setEnabled(false);
        //button1.setBackground(Color.green);
        button1.addActionListener(this);
        buttons.add(button1);
        /*button2 = new JButton("Font--");
        button2.addActionListener(this);
        buttons.add(button2);
        button3 = new JButton("Font++");
        button3.addActionListener(this);
        buttons.add(button3);*/

        ioBox.add(buttons);


        this.add(ioBox, BorderLayout.NORTH);

        this.add(guiBox, BorderLayout.CENTER);


    }//Constructor.

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    //bitstreamVera = bitstreamVera.deriveFont(fontSize);
    //typeArea.setFont(bitstreamVera);
    }//end method.

    public void actionPerformed(ActionEvent event) {
        Object src = event.getSource();

        if (src == button1) {
            interpreter.haltEvaluation();
        } else if (src == button2) {
            this.fontSize -= 2;
        //bitstreamVera = bitstreamVera.deriveFont(fontSize);
        //typeArea.setFont(bitstreamVera);
        } else if (src == button3) {
            this.fontSize += 2;
        //bitstreamVera = bitstreamVera.deriveFont(fontSize);
        //typeArea.setFont(bitstreamVera);
        }

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {

        char key = e.getKeyChar();
        //System.out.println((int)key);

        if ((int) key == 10) {
            try {
                int lineNumber = textArea.getLineOfOffset(textArea.getCaretPosition());
                String line = "";
                //System.out.println("key pressed"); //TODO remove.

                //System.out.println("LN: " + lineNumber + "  LSO: " + lineStartOffset + "  LEO: " + lineEndOffset  );
                if (e.isShiftDown()) {

                    captureInputLines(lineNumber);
                    clearPreviousResponse();



                    String code = inputLines.toString().replaceAll(";;", ";").trim();
                    code = code.replaceAll("\\\\", "");

                    //System.out.println(code);

                    if (code.length() > 0) {
                        interpreter.addResponseListener(this);
                        interpreter.evaluate("[" + code + "];");
                        button1.setEnabled(true);

                    }//end if.
                } else {
                    int relativeLineOffset = -1;
                    int cursorInsert = 0;
                    String eol = "";
                    if (e.isControlDown()) {
                        relativeLineOffset = 0;
                        int textAreaLineCount = textArea.getLineCount();
                        if (lineNumber + 1 == textAreaLineCount) {
                            eol = " \\\n";
                            cursorInsert = 3;
                        }

                    }
                    int lineStartOffset = textArea.getLineStartOffset(lineNumber + relativeLineOffset);
                    int lineEndOffset = textArea.getLineEndOffset(lineNumber + relativeLineOffset);
                    line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);
                    if (line.startsWith("In> \n") || line.startsWith("In>\n")) {
                        textArea.replaceRange("", lineStartOffset, lineEndOffset);
                    } else if (line.startsWith("In>")) {
                        textArea.insert(eol + "In> ", lineEndOffset);
                        textArea.setCaretPosition(lineEndOffset + 4 + cursorInsert);
                    }

                }



            //input.delete(0, input.length());
            // typeArea.append(response.getResult());

            } catch (BadLocationException ex) {
                System.out.println(ex.getMessage() + " , " + ex.offsetRequested());
            }

        //typeArea.append(new String(typedKey));
        //typeArea.setCaretPosition( typeArea.getDocument().getLength() );
        } else if ((int) key == 22) {
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
            }
        } else {
            //System.out.println(key);
            //registers[0] = (int) key;
            if ((int) key == 8) {
                deleteFlag = true;
            }

            input.append(key);
        //typeArea.append(Character.toString(key));
        //buffer.put((int) key);
        //setReceiveDataRegisterFull(true);
        }
    }//end method.

    public void response(EvaluationResponse response) {
        


        
        String output = "Result: " + response.getResult().trim();

        if (!response.getSideEffects().equalsIgnoreCase("")) {
            output += "\nSide Effects:\n" + response.getSideEffects();
        }

        if (response.isExceptionThrown()) {
            output += "\nException: " + response.getExceptionMessage();
        }

        if (!encounteredIn) {
            output = "\n" + output + "\n\nIn> ";
        }

        final String finalOutput = output;
        try {
            if (textArea.getLineOfOffset(responseInsertionOffset) == textArea.getLineCount()) {
                         SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    button1.setEnabled(false);
                    textArea.append(finalOutput);
                }
            });
                
                //textArea.setCaretPosition( textArea.getDocument().getLength() );
            } else {
                         SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    button1.setEnabled(false);
                     textArea.insert(finalOutput, responseInsertionOffset);
                }
            });
               
            }//end if/else.
        } catch (BadLocationException ex) {
            System.out.println(ex);
        }
    }//end method.

    public boolean remove() {
        return true;
    }

    private void clearPreviousResponse() {

        try {
            int lineNumber = textArea.getLineOfOffset(responseInsertionOffset);

            if (responseInsertionOffset == -1 || lineNumber == textArea.getLineCount()) {
                encounteredIn = false;
                return;
            }

            String line = "";
            int lineStartOffset = 0;

            do {

                lineNumber++;
                lineStartOffset = textArea.getLineStartOffset(lineNumber);
                int lineEndOffset = textArea.getLineEndOffset(lineNumber);
                line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);

            } while (!line.startsWith("In>") && lineNumber < textArea.getLineCount());

            textArea.replaceRange("\n\n", responseInsertionOffset, lineStartOffset);
            encounteredIn = line.startsWith("In>");
            return;

        } catch (BadLocationException ex) {
            encounteredIn = false;
            return;
        }
    }//end method.

    private void captureInputLines(int lineNumber) {

        inputLines.delete(0, inputLines.length());

        try {
            int lineStartOffset = textArea.getLineStartOffset(lineNumber);
            int lineEndOffset = textArea.getLineEndOffset(lineNumber);
            String line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);

            if (line.startsWith("In>")) {
                //Scan backwards to first line that does not start with In>.
                do {
                    lineStartOffset = textArea.getLineStartOffset(lineNumber);
                    lineEndOffset = textArea.getLineEndOffset(lineNumber);
                    line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);
                    lineNumber--;

                } while (line.startsWith("In>") && lineNumber != -1);//end do/while.

                if (lineNumber != -1) {
                    lineNumber++;
                }


                //Scan forwards to first line that does not start with In>.
                boolean pastInputLines = false;
                do {
                    lineNumber++;
                    lineStartOffset = textArea.getLineStartOffset(lineNumber);
                    lineEndOffset = textArea.getLineEndOffset(lineNumber);
                    line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);
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


                } while (!pastInputLines && lineNumber < textArea.getLineCount());//end while.

            }//end if.

        } catch (BadLocationException ex) {
        }


    }

    public static void main(String[] args) {
        Console console = new Console();

        JFrame frame = new javax.swing.JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.add(console, BorderLayout.CENTER);
        //frame.setAlwaysOnTop(true);
        frame.setSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setLocationRelativeTo(null); // added
        frame.pack();
        frame.setVisible(true);
    }//end main.
}//end class.

