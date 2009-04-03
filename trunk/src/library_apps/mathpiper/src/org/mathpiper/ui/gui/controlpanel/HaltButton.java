/*
 * $Id: MultiSplitLayout.java,v 1.15 2005/10/26 14:29:54 hansmuller Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.mathpiper.ui.gui.controlpanel;

import javax.swing.JButton;

public class HaltButton extends JButton {

    private static HaltButton singletonInstance;

    private HaltButton() {
        this.setText("Halt Execution");
        setForeground(java.awt.Color.RED);
        setEnabled(false);
        addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    org.mathpiper.interpreters.Interpreter interpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
                    interpreter.haltEvaluation();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setEnabled(false);
                }

            }
        });
    }//end constructor.

    public static  HaltButton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new HaltButton();
        }
        return singletonInstance;
    }
}//end class.
