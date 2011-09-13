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
package org.mathpiper.ui.gui.worksheets;

import org.mathpiper.ui.gui.worksheets.hints.Hints;
import org.mathpiper.ui.gui.worksheets.hints.HintWindow;
import org.mathpiper.ui.gui.worksheets.hints.HintItem;
import org.mathpiper.ui.gui.worksheets.mathoutputlines.PromptedStringLine;
import org.mathpiper.ui.gui.worksheets.mathoutputlines.ImageLine;
import org.mathpiper.ui.gui.worksheets.mathoutputlines.MathOutputLine;
import org.mathpiper.ui.gui.worksheets.mathoutputlines.PromptedGraph2DLine;
import org.mathpiper.ui.gui.worksheets.mathoutputlines.PromptedFormulaLine;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.io.CachedStandardFileInputStream;

import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import org.mathpiper.Version;
import org.mathpiper.interpreters.EvaluationResponse;

public class ConsoleApplet extends Applet implements KeyListener, FocusListener, ClipboardOwner, MouseListener, MouseMotionListener, Console
{

    AppletOutput out;
    boolean focusGained = false;
    boolean scrolling = false;
    int yDown = 0;
    int yStart = 0;
    boolean calculating = false;
    MathPiperOutputStream stdoutput = null;
    Interpreter interpreter = null;
    StringBuffer outputStringBuffer = new StringBuffer();
    boolean gotDatahubInit = false;
    final static int nrLines = 100;
    MathOutputLine lines[] = new MathOutputLine[nrLines];
    int currentLine = 0;
    int totalLinesHeight = 0;
    String inputLine = new String();
    String gatheredMultiLine = new String();
    int cursorPos = 0;
    final int inset = 5;
    final static String inputPrompt = "In> ";
    final static String outputPrompt = "Result: ";
    static final int fontHeight = 14;
    private Font font = new Font("Verdana", Font.PLAIN, fontHeight);
    private static final int nrHistoryLines = 100;
    public static String history[] = new String[nrHistoryLines];
    public static int currentHistoryLine = 0;
    static int historyBrowse = 0;
    boolean inputDirty = true;
    boolean outputDirty = true;
    Image piperLogo = null;
    Image offImg = null;
    Graphics offGra = null;
    Color bkColor = new Color(255, 255, 255);
    HintWindow hintWindow = null;
    Hints the_hints = new Hints();
    boolean thumbMoused = false;
    int scrollWidth = 16;
    int thumbPos = 0;
    String lastMatchedWord = "";
    String matchToInsert = "";
    int ito = -1;
    String lastError;
    /// Applet initialization
    public void init()
    {
        setBackground(bkColor);
        setLayout(null);
        this.setPreferredSize(new Dimension(400,400));
        addKeyListener(this);
        addFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        out = new AppletOutput(this);
        resetInput();

        String hintsfilename = getDocumentBase().toString();
        int slash = hintsfilename.lastIndexOf('/');
        if (slash >= 0)
        {
            hintsfilename = hintsfilename.substring(0, slash + 1);
        }
        hintsfilename = hintsfilename + "hints.txt";
        loadHints(hintsfilename);
    }

    public void focusGained(FocusEvent evt)
    {
        focusGained = true;
        inputDirty = true;
        outputDirty = true;
        if (!gotDatahubInit)
        {
            start();
        }
        repaint();
    }

    public void focusLost(FocusEvent evt)
    {
        focusGained = false;
        inputDirty = true;
        outputDirty = true;
        repaint();
    }

    public void mouseClicked(MouseEvent event)
    {
    }

    public void mouseEntered(MouseEvent event)
    {
    }

    public void mouseExited(MouseEvent event)
    {
    }

    public void mousePressed(MouseEvent event)
    {
        scrolling = false;
        int th = calcThumbHeight();
        int canvasHeight = getHeight() - fontHeight - 1;
        int w = getWidth();
        if (canvasHeight < totalLinesHeight)
        {
            int x = event.getX();
            int y = event.getY();
            if (x > w - scrollWidth && y < canvasHeight)
            {

                if (y >= thumbPos + 2 && y <= thumbPos + 2 + th)
                {
                    yDown = y;
                    yStart = thumbPos;
                } else
                {
                    thumbPos = y - 2;
                    clipThumbPos();
                }
                scrolling = true;
                thumbMoused = true;
                outputDirty = true;
                repaint();
            }
        }
    }

    public void mouseReleased(MouseEvent event)
    {
        if (scrolling)
        {
            scrolling = false;
            return;
        } else if (hintWindow != null)
        {
            if (matchToInsert.length() > 0)
            {
                inputLine = inputLine.substring(0, ito) + matchToInsert + inputLine.substring(ito, inputLine.length());
                cursorPos += matchToInsert.length();
                refreshHintWindow();
                repaint();
                return;
            }
        }
    }

    public void mouseMoved(MouseEvent event)
    {
        boolean newthumbMoused = false;
        int canvasHeight = getHeight() - fontHeight - 1;
        int w = getWidth();
        if (canvasHeight < totalLinesHeight)
        {
            int x = event.getX();
            int y = event.getY();
            if (x > getWidth() - scrollWidth && y < canvasHeight)
            {
                newthumbMoused = true;
            }
        }
        if (thumbMoused != newthumbMoused)
        {
            thumbMoused = newthumbMoused;
            outputDirty = true;
            repaint();
        }
    }

    void clipThumbPos()
    {
        int th = calcThumbHeight();
        int canvasHeight = getHeight() - fontHeight - 1;
        if (thumbPos < 0)
        {
            thumbPos = 0;
        }
        if (thumbPos > canvasHeight - th - 4)
        {
            thumbPos = canvasHeight - th - 4;
        }
    }

    public void mouseDragged(MouseEvent event)
    {
        int th = calcThumbHeight();
        int canvasHeight = getHeight() - fontHeight - 1;
        int w = getWidth();
        if (scrolling)
        {
            int x = event.getX();
            int y = event.getY();
            thumbPos = yStart + (y - yDown);
            clipThumbPos();
            outputDirty = true;
            repaint();
        }
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
    }

    public void start()
    {
        clearOutputLines();
        if (false /*TODO remove loading the logo piperLogo == null*/)
        {
            try
            {
                String fname = getDocumentBase().toString();
                int ind = fname.lastIndexOf("/");
                if (ind > 0)
                {
                    fname = fname.substring(0, ind + 1) + "piper.gif";
                    piperLogo = getImage(new URL(fname));
                }
            } catch (Exception e)
            {
            }
        }


        //stdoutput = new StringOutputStream(outp);
        String docBase = getDocumentBase().toString();
        interpreter = Interpreters.getSynchronousInterpreter(docBase);
        interpreter.getEnvironment().iCurrentInput = new CachedStandardFileInputStream(interpreter.getEnvironment().iInputStatus);


        if (piperLogo != null)
        {
            addLine(new ImageLine(piperLogo, this));
        }
        {
            String s;
            int bkred = 255;
            int bkgrn = 255;
            int bkblu = 255;
            s = getParameter("bkred");
            if (s != null)
            {
                bkred = Integer.parseInt(s);
            }
            s = getParameter("bkgrn");
            if (s != null)
            {
                bkgrn = Integer.parseInt(s);
            }
            s = getParameter("bkblu");
            if (s != null)
            {
                bkblu = Integer.parseInt(s);
            }
            bkColor = new Color(bkred, bkgrn, bkblu);
            setBackground(bkColor);
        }
        {



            Font font = new Font("helvetica", Font.PLAIN, 12);
            Color c = new Color(96, 96, 96);

            addLineStatic(100, "", "", font, c);
            addLineStatic(100, "", "", font, c);
            addLineStatic(100, "", "MathPiper version '" + Version.version + "'.", font, c);
            addLineStatic(100, "", "Type 'restart' to restart MathPiper, or 'cls' to clear screen.\n", font, c);
            addLineStatic(100, "", "To see example commands, keep typing 'Example();'\n", font, c);
        }
        /*{
            String docbase = getDocumentBase().toString();

            if (docbase.substring(0, 4).equals("file"))
            {
                int pos = docbase.lastIndexOf("/");
                String zipFileName = docbase.substring(0, pos + 1) + "mathpiper.jar";
                zipFileName = "file://" + zipFileName.substring(5,zipFileName.length());
                if (getParameter("debug") != null)
                {
                    AddLineStatic(100, "", " '" + zipFileName + "'.", font, Color.red);
                }
               try
                {
                    java.util.zip.ZipFile z = new java.util.zip.ZipFile(new File(new java.net.URI(zipFileName)));
                    UtilityFunctions.zipFile = z;
                } catch (Exception e)
                {
                    out.println("Failed to find mathpiper.jar");
                    out.println("" + zipFileName + " : \n");
                    out.println(e.toString());
                }
            }
            if (docbase.startsWith("http"))
            {
                //jar:http://www.xs4all.nl/~apinkus/interpreter.jar!/
                int pos = docbase.lastIndexOf("/");
                String scriptBase = "jar:" + docbase.substring(0, pos + 1) + "mathpiper.jar!/";
                if (getParameter("debug") != null)
                {
                    AddLineStatic(100, "", " '" + scriptBase + "'.", font, Color.red);
                }

                interpreter.evaluate("DefaultDirectory(\"" + scriptBase + "\");");
            }
        }*/

        try
        {
            out.println("");
        } catch (Exception e)
        {
            out.println(e);
        }

        //This is where the initialization parameters from the browser are initialized. tk.
        int i = 1;
        while (true)
        {
            String argn = "init" + i;
            String s = getParameter(argn);
            if (s == null)
            {
                break;
            }
            s = unescape(s);

            EvaluationResponse response = interpreter.evaluate(s);

            i++;
        }

        gotDatahubInit = false;
        tryInitThroughDatahub();

        i = 1;
        while (true)
        {
            String argn = "history" + i;
            String s = getParameter(argn);
            if (s == null)
            {
                break;
            }
            s = unescape(s);
            appendHistoryLine(s);
            i++;
        }

        resetInput();
    }

    void tryInitThroughDatahub()
    {
        if (!gotDatahubInit)
        {
            //programMode browser parameter is used here. tk.
            String programMode = getParameter("programMode");
            if (programMode == null)
            {
                gotDatahubInit = true;
            } else
            {
                try
                {
                    Applet dataHub = getAppletContext().getApplet("datahub");
                    if (dataHub != null)
                    {
                        org.mathpiper.ui.gui.applets.storage.DatahubApplet cons = (org.mathpiper.ui.gui.applets.storage.DatahubApplet) dataHub;
                        cons.setProgramMode(programMode);

                        String programContentsToLoad = "[" + cons.getProgram() + "];";
                        gotDatahubInit = true; // We're already satisfied here, as we got the contents from the datahub.
                        invokeCalculationSilent(programContentsToLoad);
                    }
                } catch (Exception e)
                {
                }
            }
        }
    }

    public void stop()
    {
    }

    public void appendHistoryLine(String line)
    {
        //TODO optimize! We need to wrap around the history buffer, this is inefficient.
        if (currentHistoryLine == nrHistoryLines)
        {
            int i;
            for (i = 0; i < currentHistoryLine - 1; i++)
            {
                history[i] = history[i + 1];
            }
            currentHistoryLine--;
        }
        history[currentHistoryLine] = line;
        currentHistoryLine++;
    }

    private String unescape(String s)
    {
        StringBuffer buf = new StringBuffer();
        int i, nr = s.length();
        for (i = 0; i < nr; i++)
        {
            if (s.charAt(i) == '\'' && s.charAt(i + 1) == '\'')
            {
                buf.append('\"');
                i++;
            } else
            {
                buf.append(s.charAt(i));
            }
        }
        return buf.toString();
    }

    public void resetInput()
    {
        if (inputLine.length() > 0)
        {
            if (inputLine.charAt(inputLine.length() - 1) != '\\')
            {
                gatheredMultiLine = "";
            }
        }
        inputLine = "";
        cursorPos = 0;
        historyBrowse = currentHistoryLine;
        inputDirty = true;
    }

    /// Applet destruction
    public void destroy()
    {
    }

    public void keyPressed(KeyEvent e)
    {
        processKeyEvent(e);
    }

    public void keyTyped(KeyEvent e)
    {
        //    processKeyEvent(e);
    }

    public void keyReleased(KeyEvent e)
    {
        //    processKeyEvent(e);
    }

    public void setClipboardContents(String aString)
    {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    public String getClipboardContents()
    {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) &&
                contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText)
        {
            try
            {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (java.awt.datatransfer.UnsupportedFlavorException ex)
            {
                //highly unlikely since we are using a standard DataFlavor
                System.out.println(ex);
            } catch (IOException ex)
            {
                System.out.println(ex);
            }
        }
        return result;
    }

    protected void processKeyEvent(KeyEvent e)
    {
        inputDirty = true;
        if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK)
        {
            if (KeyEvent.KEY_PRESSED != e.getID())
            {
                return;
            }
            if (e.getKeyCode() == (int) 'C')
            {
                //out.println("Copy");
                setClipboardContents(gatheredMultiLine + inputLine);
            } else if (e.getKeyCode() == (int) 'V')
            {
                try
                {
                    String toInsert = getClipboardContents();
                    if (toInsert != null)
                    {
                        int cr = toInsert.indexOf('\n');
                        while (cr >= 0)
                        {
                            inputLine = inputLine + toInsert.substring(0, cr);
                            toInsert = toInsert.substring(cr + 1, toInsert.length());
                            cr = toInsert.indexOf('\n');

                            appendHistoryLine(inputLine);
                            addLinesStatic(48, inputPrompt, inputLine);
                            if (inputLine.charAt(inputLine.length() - 1) == '\\')
                            {
                                gatheredMultiLine = gatheredMultiLine + inputLine.substring(0, inputLine.length() - 1);
                            } else
                            {
                                performRequest("Result: ", gatheredMultiLine + inputLine, true);
                            }
                            resetInput();
                        }
                        inputLine = inputLine + toInsert;
                        refreshHintWindow();
                        repaint();
                        return;
                    }
                } catch (Exception ex)
                {
                }
            } else
            {
                return;
            }
        }

        if (KeyEvent.KEY_PRESSED == e.getID())
        {
            if (e.VK_SHIFT == e.getKeyCode())
            {
                return;
            }
            if (e.VK_CONTROL == e.getKeyCode())
            {
                return;
            }
            if (e.VK_ALT == e.getKeyCode())
            {
                return;
            } else if (e.VK_HOME == e.getKeyCode())
            {
                cursorPos = 0;
            } /*Does not seem to work?
            else if (e.VK_COPY == e.getKeyCode())
            {
            System.out.println("COPY");
            }
            else if (e.VK_PASTE == e.getKeyCode())
            {
            System.out.println("PASTE");
            }
             */ else if (e.VK_END == e.getKeyCode())
            {
                cursorPos = inputLine.length();
            } else if (e.VK_LEFT == e.getKeyCode())
            {
                if (cursorPos > 0)
                {
                    cursorPos--;
                    refreshHintWindow();
                    repaint();
                    return;
                }
            } else if (e.VK_BACK_SPACE == e.getKeyCode())
            {
                if (cursorPos > 0)
                {
                    cursorPos--;
                    inputLine = new StringBuffer(inputLine).delete(cursorPos, cursorPos + 1).toString();
                    refreshHintWindow();
                    repaint();
                    return;
                }
            } else if (e.VK_DELETE == e.getKeyCode())
            {
                if (inputLine.length() > 0)
                {
                    if (cursorPos == inputLine.length())
                    {
                        cursorPos--;
                    }
                    inputLine = new StringBuffer(inputLine).delete(cursorPos, cursorPos + 1).toString();
                    refreshHintWindow();
                    repaint();
                    return;
                }
            } else if (e.VK_ESCAPE == e.getKeyCode())
            {
                if (hintWindow != null)
                {
                    hintWindow = null;
                } else
                {
                    resetInput();
                }
                repaint();
                return;
            } else if (e.VK_UP == e.getKeyCode())
            {
                boolean handled = false;
                if (hintWindow != null)
                {
                    if (hintWindow.iAllowSelection)
                    {
                        handled = true;
                        if (hintWindow.iCurrentPos > 0)
                        {
                            hintWindow.iCurrentPos--;
                            repaint();
                        }
                    }
                }

                if (!handled)
                {
                    handled = true;
                    String prefix = inputLine.substring(0, cursorPos);
                    int i = historyBrowse - 1;
                    while (i > 0)
                    {
                        if (history[i].startsWith(prefix))
                        {
                            break;
                        }
                        i--;
                    }
                    if (i >= 0 && i != historyBrowse && history[i].startsWith(prefix))
                    {
                        historyBrowse = i;
                        inputLine = history[historyBrowse];
                    }
                }
            } else if (e.VK_DOWN == e.getKeyCode())
            {
                boolean handled = false;
                if (hintWindow != null)
                {
                    if (hintWindow.iAllowSelection)
                    {
                        handled = true;
                        if (hintWindow.iCurrentPos < hintWindow.iNrLines - 1)
                        {
                            hintWindow.iCurrentPos++;
                            repaint();
                        }
                    }
                }

                if (!handled)
                {
                    String prefix = inputLine.substring(0, cursorPos);
                    int i = historyBrowse + 1;
                    while (i < currentHistoryLine)
                    {
                        if (history[i].startsWith(prefix))
                        {
                            break;
                        }
                        i++;
                    }
                    if (i < currentHistoryLine && history[i].startsWith(prefix))
                    {
                        historyBrowse = i;
                        inputLine = history[historyBrowse];
                    } else
                    {
                        int pos = cursorPos;
                        resetInput();
                        inputLine = prefix;
                        cursorPos = pos;
                    }
                }
            } else if (e.VK_RIGHT == e.getKeyCode())
            {
                boolean handled = false;

                if (!handled)
                {
                    handled = true;
                    if (cursorPos < inputLine.length())
                    {
                        cursorPos++;
                        refreshHintWindow();
                        repaint();
                        return;
                    }
                }
            } else if (e.VK_ENTER == e.getKeyCode())
            {
                boolean handled = false;

                if (!handled)
                {
                    if (cursorPos == ito && matchToInsert.length() > 0)
                    {
                        //System.out.println("matchToInsert = "+matchToInsert);
                        handled = true;
                        inputLine = inputLine.substring(0, ito) + matchToInsert + inputLine.substring(ito, inputLine.length());
                        cursorPos += matchToInsert.length();
                        refreshHintWindow();
                        repaint();
                        return;
                    }
                }
                if (!handled)
                {
                    if (hintWindow != null)
                    {
                        if (cursorPos == ito && hintWindow.iAllowSelection)
                        {
                            handled = true;
                            String item = hintWindow.iText[hintWindow.iCurrentPos];
                            if (lastMatchedWord.equals(item))
                            {
                                item = "(";
                            } else
                            {
                                item = item.substring(lastMatchedWord.length(), item.length());
                            }
                            inputLine = inputLine.substring(0, ito) + item + inputLine.substring(ito, inputLine.length());
                            cursorPos += item.length();
                            refreshHintWindow();
                            repaint();
                            return;
                        }
                    }
                }
                if (!handled)
                {
                    if (inputLine.length() > 0)
                    {
                        appendHistoryLine(inputLine);
                        addLinesStatic(48, inputPrompt, inputLine);
                        if (inputLine.charAt(inputLine.length() - 1) == '\\')
                        {
                            gatheredMultiLine = gatheredMultiLine + inputLine.substring(0, inputLine.length() - 1);
                        } else
                        {
                            performRequest("Result: ", gatheredMultiLine + inputLine, true);
                        }
                        resetInput();
                        refreshHintWindow();
                        repaint(0);
                    }
                }
            }
            inputDirty = true;
            repaint();
        } else if (KeyEvent.KEY_TYPED == e.getID())
        {
            int c = (int) e.getKeyChar();
            if (c >= 32 && c < 127)
            {
                inputLine = new StringBuffer(inputLine).insert(cursorPos, e.getKeyChar()).toString();
                cursorPos++;
                refreshHintWindow();
                inputDirty = true;
                repaint();
            }
        }
    }

    boolean directCommand(String inputLine)
    {
        if (inputLine.equals("restart"))
        {
            stop();
            start();
            return true;
        } else if (inputLine.equals("cls"))
        {
            clearOutputLines();
            return true;
        } else if (inputLine.equals(":test"))
        {
            try
            {
                Applet dataHub = getAppletContext().getApplet("datahub");
                if (dataHub != null)
                {
                    org.mathpiper.ui.gui.applets.storage.DatahubApplet cons = (org.mathpiper.ui.gui.applets.storage.DatahubApplet) dataHub;
                    String programContentsToLoad = "[" + cons.getTestcode() + "];";
                    invokeCalculationSilent(programContentsToLoad);
                }
            } catch (Exception e)
            {
            }
            return true;
        } else if (inputLine.equals("?license") || inputLine.equals("?licence") || inputLine.equals("?warranty"))
        {
            try
            {
                getAppletContext().showDocument(new URL("gpl.html"), "license");
            } catch (Exception e)
            {
            }
            return true;
        }
        return false;
    }

    void performRequest(String outputPrompt, String inputLine, boolean doRepaint)
    {
        boolean succeed = false;
        if (directCommand(inputLine))
        {
            return;
        } else
        {
            resetInput();
            refreshHintWindow();

            calculating = true;
            if (doRepaint)
            {
                paint(getGraphics());
            }
            outputStringBuffer.delete(0, outputStringBuffer.length());
            //String response = "";
            EvaluationResponse response = null;

            response = interpreter.evaluate(inputLine);

            
            outputStringBuffer.append(response.getSideEffects());//todo:tk:hack to try to determine why outputStringBuffer is always being added as an output line as an empty string.

            calculating = false;

            addOutputLine(outputStringBuffer.toString());

            
            if (response.isExceptionThrown() == true)
            {
                addLinesStatic(48, "Error> ", response.getException().getMessage());
            }
            //AddLinesStatic(48, outputPrompt, response.getSideEffects());//TODO:tk: latex results are returned as a side effect, but normal results are not.  Also, what is a static line?.
            succeed = true;
        }
        {
            if (!succeed)
            {
                out.println("Request failed");
            }
        }
    }

    void addLinesStatic(int indent, String prompt, String str)
    {
        int pos;
        while ((pos = str.indexOf('\n')) >= 0)
        {
            addLineStatic(indent, prompt, str.substring(0, pos), font, Color.black);
            str = str.substring(pos + 1, str.length());
        }
        if (str.length() > 0)
        {
            addLineStatic(indent, prompt, str, font, Color.black);
        }
    }










    void clearOutputLines()
    {
        int i;
        for (i = 0; i < nrLines; i++)
        {
            lines[i] = null;
        }
        totalLinesHeight = 0;
        thumbPos = 0;
        outputDirty = true;
    }

    void addLine(MathOutputLine aLine)
    {
        {
            createOffscreenImage();
            if (lines[currentLine] != null)
            {
                totalLinesHeight -= lines[currentLine].height(offGra);
            }
            lines[currentLine] = aLine;
            if (lines[currentLine] != null)
            {
                totalLinesHeight += lines[currentLine].height(offGra);
            }
            currentLine = (currentLine + 1) % nrLines;
            {
                int canvasHeight = getHeight() - fontHeight - 1;
                if (canvasHeight < totalLinesHeight)
                {
                    int th = calcThumbHeight();
                    thumbPos = canvasHeight - th - 4;
                }
            }
            outputDirty = true;
        }
    }

    void addLine(int index, String text)
    {
        addLineStatic(index, text);
        repaint(0);
    }

    public void addLineStatic(int indent, String text)
    {
        addLineStatic(indent, "", text, font, Color.black);
    }
    Color iPromptColor = new Color(128, 128, 128);
    Font iPromptFont = new Font("Verdana", Font.PLAIN, 12);

    void addLineStatic(int indent, String prompt, String text, Font aFont, Color aColor)
    {
        addLine(new PromptedStringLine(indent, prompt, text, iPromptFont, aFont, iPromptColor, aColor));
        outputDirty = true;
    }

    /// Drawing current view
    public void update(Graphics g)
    {
        paint(g);
    }

    void createOffscreenImage()
    {
        // draw an offScreen drawing
        Dimension dim = getSize();
        if (offGra == null)
        {
            offImg = createImage(dim.width, dim.height);
            offGra = offImg.getGraphics();
        }
    }

    public void paint(Graphics g)
    {
        createOffscreenImage();

        // Render image
        paintToBitmap(offGra);
        // put the OffScreen image OnScreen
        g.drawImage(offImg, 0, 0, null);
        if (hintWindow != null)
        {
            if (g instanceof Graphics2D)
            {
                Graphics2D g2d = null;
                g2d = (Graphics2D) g;
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
            }
            MathPiperGraphicsContext context = new MathPiperGraphicsContext(g, 0, 0);
            context.setFontSize(1, fontHeight/*12*/);
            int nr_total_lines = 1;
            Dimension d = getSize();
            hintWindow.draw(5, (int) (d.getHeight() - context.fontHeight() - nr_total_lines * context.fontHeight()), context);
        }
    }

    int calcThumbHeight()
    {
        int canvasHeight = getHeight() - fontHeight - 1;
        int hgt = ((canvasHeight - 4) * canvasHeight) / totalLinesHeight;
        if (hgt < 16)
        {
            hgt = 16;
        }
        return hgt;
    }

    public void paintToBitmap(Graphics g)
    {
        synchronized (this)
        {
            if (g instanceof Graphics2D)
            {
                Graphics2D g2d = null;
                g2d = (Graphics2D) g;
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

                g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }

            FontMetrics metrics = getFontMetrics(font);

            g.setColor(bkColor);
            int yfrom = 0;

            g.setFont(font);
            int inHeight = fontHeight;

            int yto = getHeight();
            if (!outputDirty)
            {
                yfrom += getHeight() - inHeight;
            }
            if (!inputDirty)
            {
                yto -= inHeight;
            }
            g.clearRect(0, yfrom, getWidth(), yto);
            g.setColor(Color.black);

            int i;
            int y = getHeight() - inHeight - g.getFontMetrics().getHeight();

            int canvasHeight = getHeight() - fontHeight - 1;
            if (outputDirty)
            {
                y -= totalLinesHeight;
                if (canvasHeight < totalLinesHeight)
                {
                    int th = calcThumbHeight();
                    double scale = (1.0 * thumbPos) / (canvasHeight - th - 4);
                    y += (int) ((1 - scale) * (totalLinesHeight - canvasHeight));
                }
                g.setClip(0, 0, getWidth(), getHeight() - fontHeight - 1);
                for (i = 0; i < nrLines; i++)
                {
                    int index = (currentLine + i) % nrLines;
                    if (lines[index] != null)
                    {
                        if (y + lines[index].height(g) > 0)
                        {
                            lines[index].draw(g, inset, y);
                        }
                        y += lines[index].height(g);
                    }
                }
                g.setClip(0, 0, getWidth(), getHeight());
                int w = getWidth();
                //System.out.println("height = "+totalLinesHeight+", screen = "+(canvasHeight));
                if (canvasHeight < totalLinesHeight)
                {
                    int thumbHeight = calcThumbHeight();
                    g.setColor(Color.white);
                    g.fillRect(w - scrollWidth, 0, scrollWidth, canvasHeight);

                    if (thumbMoused)
                    {
                        g.setColor(new Color(192, 192, 240));
                    } else
                    {
                        g.setColor(new Color(124, 124, 224));
                    }
                    g.fillRect(w - scrollWidth + 2, thumbPos + 2, scrollWidth - 4, thumbHeight);

                    g.setColor(Color.black);
                    g.drawRect(w - scrollWidth, 0, scrollWidth, canvasHeight);

                    g.drawRect(w - scrollWidth + 2, thumbPos + 2, scrollWidth - 4, thumbHeight);
                }
            }
            y = getHeight() - g.getFontMetrics().getDescent();
            outputDirty = false;
            if (focusGained && !calculating)
            {
                if (inputDirty)
                {
                    if (y + fontHeight > 0)
                    {
                        int promptLength = metrics.stringWidth(inputPrompt);
                        g.setColor(Color.red);
                        g.setFont(font);

                        g.drawString(inputPrompt, inset, y);
                        g.drawString(inputLine, inset + promptLength, y);
                        int cursorLocation = promptLength;
                        for (i = 0; i < cursorPos; i++)
                        {
                            cursorLocation += metrics.charWidth(inputLine.charAt(i));
                        }
                        y += g.getFontMetrics().getDescent();
                        g.setColor(Color.blue);
                        g.drawLine(inset + cursorLocation, y - 2, inset + cursorLocation, y - fontHeight + 1);
                    //TODO remove?            g.drawLine(inset+cursorLocation+1,y-2,inset+cursorLocation+1,y-fontHeight+1);
                    }
                }
            } else
            {
                String toPrint = "Click here to enter an expression";
                if (calculating)
                {
                    toPrint = "Calculating...";
                }

                int promptLength = metrics.stringWidth(toPrint);
                g.setColor(Color.blue);
                g.setFont(font);

                g.drawString(toPrint, inset, y);
                y += g.getFontMetrics().getDescent();
            }
            inputDirty = false;
        }
    }



    void loadHints(String filename)
    {
        CharacterDataReader file = new CharacterDataReader();
        int opened = 0;
        try
        {
            URL url = new URL(filename);
            opened = file.open(url);
        } catch (Exception e)
        {
        }
        if (opened != 0)
        {
            String line = file.readLine();
            String[] tokens = new String[16];
            int nrTokens = 0;
            while (line != null)
            {
                if (line.substring(0, 2).equals("::"))
                {
                    break;
                }
                int i = 0;
                nrTokens = 0;
                while (i < line.length())
                {
                    int start = i;
                    while (line.charAt(i) != ':')
                    {
                        i++;
                    }
                    tokens[nrTokens] = line.substring(start, i);
                    nrTokens++;
                    i++;
                }
                if (nrTokens > 3)
                {
                    HintItem hi = new HintItem();
                    hi.base = tokens[1];
                    hi.hint = tokens[2];
                    hi.description = tokens[3];
                    the_hints.hintTexts[the_hints.nrHintTexts] = hi;
                    the_hints.nrHintTexts++;
                }

                line = file.readLine();
            }
            file.close();
        } else
        {
            out.println("could not read hints");
        }
    }

    HintWindow createHints(int fontsize)
    {
        HintWindow hw = new HintWindow(fontsize);
        return hw;
    }

    void addHintLine(HintWindow hints, String aText, String aDescription)
    {
        hints.addLine(aText);
        if (aDescription.length() > 0)
        {
            hints.addDescription(aDescription);
        }
    }

    HintWindow tryToHint(String text, int length)
    {
        HintWindow hints = null;
        int nrhints = the_hints.nrHintTexts;
        int i, start;
        start = 0;
        if (start < 0)
        {
            return null;
        }
        for (i = start; i < nrhints; i++)
        {
            if (text.charAt(0) > the_hints.hintTexts[i].base.charAt(0))
            {
                continue;
            }
            if (text.charAt(0) < the_hints.hintTexts[i].base.charAt(0))
            {
                continue;
            }
            int baselen = the_hints.hintTexts[i].base.length();
            if (length == baselen)
            {
                if (text.substring(0, baselen).equals(the_hints.hintTexts[i].base))
                {
                    if (hints == null)
                    {
                        hints = createHints(12 /*iDefaultFontSize*/);
                        hints.iAllowSelection = false;
                    }
                    addHintLine(hints, the_hints.hintTexts[i].hint, the_hints.hintTexts[i].description);
                }
            }
        }
        return hints;
    }

    void refreshHintWindow()
    {
        ito = cursorPos;

        while (true)
        {
            if (ito == inputLine.length())
            {
                break;
            }
            if (!MathPiperTokenizer.isAlpha(inputLine.charAt(ito)))
            {
                break;
            }
            ito++;
        }
        if (ito > 0)
        {
            int c = inputLine.charAt(ito - 1);
            if (c == ',' || c == ')')
            {
                int braces = -1;
                if (c == ')')
                {
                    ito--;
                    braces = -2;
                }
                while (braces != 0)
                {
                    if (ito <= 0)
                    {
                        break;
                    }
                    if (inputLine.charAt(ito - 1) == '(')
                    {
                        braces++;
                    }
                    if (inputLine.charAt(ito - 1) == ')')
                    {
                        braces--;
                    }
                    ito--;
                }
            }
        }
        if (ito > 0)
        {
            if (inputLine.charAt(ito - 1) == '(')
            {
                ito--;
            }
        }
        if (ito == 0)
        {
            while (true)
            {
                if (ito == cursorPos)
                {
                    break;
                }
                if (!MathPiperTokenizer.isAlpha(inputLine.charAt(ito)))
                {
                    break;
                }
                ito++;
            }
        }
        int ifrom = ito;
        while (true)
        {
            if (ifrom == 0)
            {
                break;
            }
            char c = inputLine.charAt(ifrom - 1);
            if (!MathPiperTokenizer.isAlpha(c) && !MathPiperTokenizer.isDigit(c))
            {
                break;
            }
            ifrom--;
        }
        // Name of function *has* to start with alphabetic letter
        while (ifrom < ito && MathPiperTokenizer.isDigit(inputLine.charAt(ifrom)))
        {
            ifrom++;
        }
        matchToInsert = "";
        lastMatchedWord = "";
        if (ito > ifrom)
        {
            lastMatchedWord = inputLine.substring(ifrom, ito);
        }

        hintWindow = null;
        if (lastMatchedWord.length() > 0)
        {
            //System.out.println("word is "+word);

            int nr = lastMatchedWord.length();
            int maxHintLines = 18;
            String texts[] = new String[maxHintLines + 1];
            int nrHintLines = 0;

            int i;
            for (i = 0; i < the_hints.nrHintTexts; i++)
            {
                if (nrHintLines == maxHintLines)
                {
                    break;
                }
                if (nr <= (the_hints.hintTexts[i].base).length() &&
                        lastMatchedWord.equals(the_hints.hintTexts[i].base.substring(0, nr)))
                {
                    boolean add = true;
                    if (nrHintLines > 0)
                    {
                        if (texts[nrHintLines - 1].equals(the_hints.hintTexts[i].base))
                        {
                            add = false;
                        }
                    }
                    if (add)
                    {
                        texts[nrHintLines++] = the_hints.hintTexts[i].base;
                    }
                    // Exact match, keep this one line
                    if (nrHintLines == 1 && ito != cursorPos && lastMatchedWord.equals(the_hints.hintTexts[i].base))
                    {
                        break;
                    }
                }
            }

            if (nrHintLines == maxHintLines)
            {
                texts[nrHintLines++] = "...";
            }
            if (nrHintLines == 1)
            {
                if (lastMatchedWord.length() < texts[0].length())
                {
                    matchToInsert = texts[0].substring(lastMatchedWord.length(), texts[0].length());
                }
                hintWindow = tryToHint(texts[0], texts[0].length());
            } else if (nrHintLines > 1)
            {
                hintWindow = createHints(12);
                hintWindow.iAllowSelection = true;

                for (i = 0; i < nrHintLines; i++)
                {
                    addHintLine(hintWindow, texts[i], "");
                }
            }
        }
    }

    public void invokeCalculation(String expression)
    {
        if (!gotDatahubInit)
        {
            start();
        }
        appendHistoryLine(expression);
        addLinesStatic(48, "In> ", expression);
        resetInput();
        refreshHintWindow();
        inputDirty = true;
        outputDirty = true;
        performRequest("Result: ", expression, false);
        inputDirty = true;
        outputDirty = true;
        repaint();
    }

    public String calculate(String expression)
    {
        if (!gotDatahubInit)
        {
            start();
        //String result = "";
        }
        EvaluationResponse evaluationResponse = null;

        evaluationResponse = interpreter.evaluate(expression);

        lastError = evaluationResponse.getException().getMessage();  //Note:tk: need to check for null value.
        return evaluationResponse.getResult();
    }

    public String getLastError()
    {
        if (lastError != null)
        {
            return lastError;
        } else
        {
            return "";
        }
    }

    private void addOutputLine(String outp)
    {
        if (outp.length() > 0)
        {
            int dollarPos = outp.indexOf("$");
            while (dollarPos >= 0)
            {
                // Print plain text before the dollared content
                if (dollarPos > 0)
                {
                    addLinesStatic(48, "", outp.substring(0, dollarPos));
                }
                // Strip off the left dollar sign
                outp = outp.substring(dollarPos + 1, outp.length());

                // Find the right dollar sign, and split there too
                dollarPos = outp.indexOf("$");
                String dollared = outp.substring(0, dollarPos);
                outp = outp.substring(dollarPos + 1, outp.length());

                //System.out.println("Dollared: "+dollared);
                int plotPos = dollared.indexOf("plot2d:");
                if (plotPos >= 0)
                {
                    dollared = dollared.substring(plotPos + 7);
                    //System.out.println("Plotting: ["+dollared+"]");
                    addLine(new PromptedGraph2DLine(48, "Result:", iPromptFont, iPromptColor, dollared));
                } else
                {
                    addLine(new PromptedFormulaLine(48, "Result:", iPromptFont, iPromptColor, dollared));
                }
                dollarPos = outp.indexOf("$");
            }
            // If there is some plain text left at the end, print
            if (outp.length() > 0)
            {
                addLinesStatic(48, "", outp.toString());
            }
        }
        outputDirty = true;
    }

    public void addInputLine(String expression)
    {
        synchronized (this)
        {
            if (!gotDatahubInit)
            {
                start();
            }
            appendHistoryLine(expression);
            addLinesStatic(48, "In> ", expression);
            resetInput();
            refreshHintWindow();
            inputDirty = true;
            outputDirty = true;
            calculating = true;
        }
        repaint();
    }

    public void invokeCalculationSilent(String expression)
    {
        synchronized (this)
        {
            if (directCommand(expression))
            {
                return;
            } else
            {
                outputStringBuffer.delete(0, outputStringBuffer.length());
                EvaluationResponse evaluationResponse = null;

                evaluationResponse = interpreter.evaluate(expression);

                calculating = false;
                addOutputLine(outputStringBuffer.toString());
                if (evaluationResponse != null && evaluationResponse.isExceptionThrown())
                {
                    addLinesStatic(48, "Error> ", evaluationResponse.getException().getMessage());
                }

                resetInput();
                refreshHintWindow();
                inputDirty = true;
                outputDirty = true;
            }
        }
        repaint();
    }

    public void stopCurrentCalculation()
    {
        //interpreter.getEnvironment().iEvalDepth = interpreter.getEnvironment().iMaxEvalDepth + 100;
        interpreter.haltEvaluation();
    }
}

