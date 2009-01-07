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
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;

public class Console extends javax.swing.JPanel implements ActionListener, KeyListener
{
   private Interpreter interpreter = Interpreters.getSynchronousInterpreter();
   private StringBuilder input = new StringBuilder();


	private JButton button1, button2, button3;
	private JTextArea typeArea;
	private JScrollPane typePane;
	private char[] typedKey = new char[1];



	private JPanel buttons;
	private boolean deleteFlag = false;
	private float fontSize = 12;
	private Font bitstreamVera;


	public Console()
	{


		this.setLayout(new BorderLayout());

		//keySendQueue = new java.util.concurrent.ArrayBlockingQueue(30);

		buttons = new JPanel();

		Box guiBox = new Box(BoxLayout.Y_AXIS);
		typeArea = new JTextArea(30,20);

		//java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/ttf-bitstream-vera-1.10/VeraMono.ttf" );

			//bitstreamVera = Font.createFont (Font.TRUETYPE_FONT, inputStream);
			//bitstreamVera = bitstreamVera.deriveFont(fontSize);
			//typeArea.setFont(bitstreamVera);


			typeArea.addKeyListener(this);
			typePane = new JScrollPane(typeArea);
			guiBox.add(typePane);

			Box ioBox = new Box(BoxLayout.Y_AXIS);

			button1 = new JButton("Reset");
			//button1.setBackground(Color.green);
			button1.addActionListener(this);
			buttons.add(button1);
			button2 = new JButton("Font--");
			button2.addActionListener(this);
			buttons.add(button2);
			button3 = new JButton("Font++");
			button3.addActionListener(this);
			buttons.add(button3);

			ioBox.add(buttons);


			this.add(ioBox,BorderLayout.NORTH);

			this.add(guiBox,BorderLayout.CENTER);


	}//Constructor.



	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
		//bitstreamVera = bitstreamVera.deriveFont(fontSize);
		//typeArea.setFont(bitstreamVera);
	}//end method.



	public void actionPerformed(ActionEvent event)
	{
		Object src = event.getSource();

		if (src == button1)
		{
			//this.resetAll();
		}
		else if (src == button2)
		{
			this.fontSize -= 2;
			//bitstreamVera = bitstreamVera.deriveFont(fontSize);
			//typeArea.setFont(bitstreamVera);
		}
		else if (src == button3)
		{
			this.fontSize += 2;
			//bitstreamVera = bitstreamVera.deriveFont(fontSize);
			//typeArea.setFont(bitstreamVera);
		}

	}



	public void keyPressed(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{

		char key = e.getKeyChar();
		System.out.println((int)key);

        if((int)key == 10)
        {

            EvaluationResponse response = interpreter.evaluate(input.toString());
            input.delete(0, input.length());
            typeArea.append(response.getResult());
             typeArea.append("\n\n");

            //typeArea.append(new String(typedKey));
			//typeArea.setCaretPosition( typeArea.getDocument().getLength() );
        }
        else if((int)key == 22)
		{
			try
			{
				String clipBoard = (String)java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getData(java.awt.datatransfer.DataFlavor.stringFlavor);

				if(clipBoard.length() != 0)
				{
					char[] chars = clipBoard.toCharArray();
					for(int x = 0; x < chars.length; x++)
					{
						//buffer.put((int) chars[x]);

					}//end for.
					//setReceiveDataRegisterFull(true);
				}//end if.

			}
			catch(NullPointerException ev)
			{
				ev.printStackTrace();
			}
			catch( IllegalStateException ev)
			{
				ev.printStackTrace();
			}
			catch(java.awt.datatransfer.UnsupportedFlavorException ev)
			{
				ev.printStackTrace();
			}
			catch(java.io.IOException ev)
			{
				ev.printStackTrace();
			}
		}
		else
		{
			//System.out.println(key);
			//registers[0] = (int) key;
			if((int)key == 8)
			{
				deleteFlag = true;
			}

            input.append(key);
            //typeArea.append(Character.toString(key));
			//buffer.put((int) key);
			//setReceiveDataRegisterFull(true);
		}
	}



	public static void main(String[] args)
	{
		Console console = new Console();

		JFrame frame = new javax.swing.JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.add(console,BorderLayout.CENTER);
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

