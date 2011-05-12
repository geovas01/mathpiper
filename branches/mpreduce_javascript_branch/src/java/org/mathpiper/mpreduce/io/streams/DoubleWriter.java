package org.mathpiper.mpreduce.io.streams;


//
// This file is part of the Jlisp implementation of Standard Lisp
// Copyright \u00a9 (C) Codemist Ltd, 1998-2000.
//

/**************************************************************************
 * Copyright (C) 1998-2011, Codemist Ltd.                A C Norman       *
 *                            also contributions from Vijay Chauhan, 2002 *
 *                                                                        *
 * Redistribution and use in source and binary forms, with or without     *
 * modification, are permitted provided that the following conditions are *
 * met:                                                                   *
 *                                                                        *
 *     * Redistributions of source code must retain the relevant          *
 *       copyright notice, this list of conditions and the following      *
 *       disclaimer.                                                      *
 *     * Redistributions in binary form must reproduce the above          *
 *       copyright notice, this list of conditions and the following      *
 *       disclaimer in the documentation and/or other materials provided  *
 *       with the distribution.                                           *
 *                                                                        *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS    *
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT      *
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS      *
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE         *
 * COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,   *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,   *
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS  *
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR  *
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF     *
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH   *
 * DAMAGE.                                                                *
 *************************************************************************/

import java.io.FileOutputStream;
import java.io.IOException;

import org.mathpiper.mpreduce.Jlisp;

public class DoubleWriter extends LispStream
{
    LispPrintStream log;
    boolean closeMe;

    public DoubleWriter(String n, LispPrintStream log) throws IOException // to a named file
    {
        super(n);
        wr = new LispPrintStream(new FileOutputStream(nameConvert(n)));
        this.log = log;
        closeMe = true;
        Jlisp.openOutputFiles.add(this);
    }

    public DoubleWriter(LispPrintStream log) // uses standard input, no extra buffering.
    {
        super("<stdout>");
        wr = Jlisp.out;
        this.log = log;
        closeMe = false;
        Jlisp.openOutputFiles.add(this);
    }

    public void flush()
    {
    wr.flush();
            log.flush();

    }

    public void close()
    {
        Jlisp.openOutputFiles.removeElement(this);
   wr.flush();
            log.flush();
            if (closeMe) wr.close();
            log.close();

    }

    public void print(String s)
    {
        if (s == null) s = "null";
        byte [] v = s.getBytes();
// It *MAY* be better to use getChars here and move data into a pre-allocated
// array of characters.
   int p = 0;
            for (int i=0; i<v.length; i++)
            {   byte c = v[i];
// See commentary if LispOutputStream.java
                if (c == '\n') 
                {   wr.write(v, p, i-p);
                    wr.print(eol);
                    log.write(v, p, i-p);
                    log.print(eol);
                    p = i+1;
                    column = 0;
                }
                else if (c == '\r')
                {   wr.write(v, p, i-p);
                    log.write(v, p, i-p);
                    p = i+1;
                    column = 0;
                }
                else column++;
            }
            wr.write(v, p, v.length-p);
            log.write(v, p, v.length-p);

    }

    public void println(String s)
    {
        if (s == null) s = "null";
        byte [] v = s.getBytes();
   int p = 0;
            for (int i=0; i<v.length; i++)
            {   byte c = v[i];
                if (c == '\n') 
                {   wr.write(v, p, i-p);
                    wr.print(eol);
                    log.write(v, p, i-p);
                    log.print(eol);
                    p = i+1;
                }
                else if (c == '\r')
                {   wr.write(v, p, i-p);
                    log.write(v, p, i-p);
                    p = i+1;
                }
            }
            wr.write(v, p, v.length-p);
            wr.print(eol);
            log.write(v, p, v.length-p);
            log.print(eol);

        column = 0;
    }

}

// end of DoubleWriter.java


