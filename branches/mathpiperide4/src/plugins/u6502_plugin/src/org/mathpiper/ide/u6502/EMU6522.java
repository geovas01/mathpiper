/* {{{ License.

 Copyright (C) 2008 Ted Kosan
                                                                                      
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
package org.mathpiper.ide.u6502;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EMU6522 extends javax.swing.JPanel implements IOChip, ActionListener {

    private static final int PORTB = 0x0;
    private static final int PORTA = 0x1;
    private static final int DDRB  = 0x2;
    private static final int DDRA  = 0x3;
    private static final int T1CL  = 0x4;
    private static final int T1CH  = 0x5;
    private static final int T1LL  = 0x6;
    private static final int T1LH  = 0x7;
    private static final int T2LL  = 0x8;
    private static final int T2CL  = 0x8;
    private static final int T2CH  = 0x9;
    private static final int SR    = 0xA;
    private static final int ACR   = 0xB;
    private static final int PCR   = 0xC;
    private static final int IFR   = 0xD;
    private static final int IER   = 0xE;
    private static final int ORAX  = 0xF;
    
    private int[] registers = new int[16];
    
    private java.util.Timer timer;
    
    

    private JLabel[] bits = new JLabel[8];

    private JButton button1, button2;

	//private ImageIcon led0, led1, led2, led3, led4, led5, led6, led7;
    private Icon onIcon, offIcon;


    private String label = "";
    
    private int count = 0;
    
    private EMU6551 emu6551;

    public EMU6522(String label, EMU6551 emu6551) {
        super();
        
        this.emu6551 = emu6551;
        registers[PORTB] = 0b00000000;
        registers[PORTA] = 0b00000000;
        registers[DDRB]  = 0b00000000;
        registers[DDRA]  = 0b00000000;
        registers[T1CL]  = 0b00000001;
        registers[T1CH]  = 0b00000000;
        registers[T1LL]  = 0b00000000;
        registers[T1LH]  = 0b00000000;
        // registers[T2LL]  = 0b00000000;
        registers[T2CL]  = 0b00000000;
        registers[T2CH]  = 0b00000000;
        registers[SR]    = 0b00000000;
        registers[ACR]   = 0b00000000;
        registers[PCR]   = 0b00000000;
        registers[IFR]   = 0b00000000;
        registers[IER]   = 0b00000000;
        registers[ORAX]  = 0b00000000;

        this.label = label;

        this.setLayout(new BorderLayout());

        Box leds = new Box(BoxLayout.X_AXIS);

		//Load on image.
        // java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathpiper.ide.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/yellow.gif" );
        java.io.InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/images/yellow.gif");

        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream);
        byte[] buffer = new byte[4096];
        try {
            bufferedInputStream.read(buffer, 0, 4096);
        } catch (Exception e) {
            System.err.println("Failed to read image.");
        }
        onIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(buffer));

		//Load off image.
        // inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathpiper.ide.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/gray.gif" );
        inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/images/gray.gif");

        bufferedInputStream = new java.io.BufferedInputStream(inputStream);
        buffer = new byte[4096];
        try {
            bufferedInputStream.read(buffer, 0, 4096);
        } catch (Exception e) {
            System.err.println("Failed to read image.");
        }
        offIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(buffer));

        //Initialize LED 7.
        bits[7] = new JLabel(offIcon);
        leds.add(bits[7]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 6.         
        bits[6] = new JLabel(offIcon);
        leds.add(bits[6]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 5.         
        bits[5] = new JLabel(offIcon);
        leds.add(bits[5]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 4.         
        bits[4] = new JLabel(offIcon);
        leds.add(bits[4]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 3.         
        bits[3] = new JLabel(offIcon);
        leds.add(bits[3]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 2.         
        bits[2] = new JLabel(offIcon);
        leds.add(bits[2]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 1.         
        bits[1] = new JLabel(offIcon);
        leds.add(bits[1]);
        leds.add(Box.createHorizontalStrut(3));

        //Initialize LED 0..         
        bits[0] = new JLabel(offIcon);
        leds.add(bits[0]);

        button1 = new JButton("Reset");

        button1.addActionListener(this);

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        JLabel deviceLabel = new JLabel(label);
        panel.add(deviceLabel);
        panel.add(leds);
        layout.putConstraint(SpringLayout.WEST, deviceLabel, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, deviceLabel, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, leds, 5, SpringLayout.EAST, deviceLabel);
        layout.putConstraint(SpringLayout.NORTH, leds, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, leds);
        layout.putConstraint(SpringLayout.SOUTH, panel, 5, SpringLayout.SOUTH, leds);

        this.add(panel, BorderLayout.CENTER);     

    }//end constructor.


    public void actionPerformed(ActionEvent event) {
        Object src = event.getSource();

        if (src == button1) {

        } else if (src == button2) {

        }

    }

    public int read(int location) {
        if (location > 0 && location < registers.length) {
            return registers[location];
        } else {
            // throw new Exception("Register " + location + " does not exist.");
            return -1;
        }
    }//end method.

    
    public void write(int location, int value) {

        if (location > 0 && location < registers.length) {
            
            if(location == IER)
            {
                if(((registers[IER] & 0b01000000) == 0) && (value & 0b01000000) != 0)
                {
                    timer = new java.util.Timer();
                    timer.scheduleAtFixedRate(new EMU6522.Task() ,0 , 1);
                }
                else
                {
                    if(timer != null)
                    {
                        timer.cancel();
                        timer = null;
                    }
                }
            }

            registers[location] = value;
        }
    }//end method.

    
    public void reset() {
        if(timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }//end method.
    
    
    private class Task extends java.util.TimerTask
    {
            public synchronized void run()
            {
                    if((registers[T1CL] == 0 && registers[T1CH] == 0) || (--registers[T1CL] == 0 && registers[T1CH] == 0) )
                    {
                        registers[T1CL] = registers[T1LL];
                        registers[T1CH] = registers[T1LH];

                        emu6551.irq();
                        // System.out.println(count++);
                    }

                    if(registers[T1CL] == -1)
                    {
                        registers[T1CL] = 0xFF;
                        registers[T1CH]--;
                    }

            }//
    }//end method.

}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
