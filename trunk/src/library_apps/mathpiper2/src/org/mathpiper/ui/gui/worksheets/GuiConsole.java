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
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.ui.gui.worksheets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GuiConsole extends JFrame {

    /** Creates new form Calculator */
    public GuiConsole() {
        this.setSize(500, 500);
        this.setPreferredSize(new Dimension(500, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);

        ConsolePanel consolePanel = new ConsolePanel();
        //TempPanel consolePanel = new TempPanel();
        this.getContentPane().add(consolePanel);
        this.getContentPane().add(new JTextField(20),BorderLayout.NORTH);
        this.pack();
        consolePanel.init();
        consolePanel.start();

        /*//Make textField get the focus whenever frame is activated.
        this.addWindowFocusListener(new WindowAdapter() {

            public void windowGainedFocus(WindowEvent e) {
                consolePanel.requestFocusInWindow();
            }
        });*/




    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GuiConsole().setVisible(true);
            }
        });
    }
}