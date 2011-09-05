// Bean Sheet
// Copyright (C) 2004 Alexey Zinger
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package zinger.util;

import java.util.*;

import zinger.util.recycling.*;

public class ThreadLimiter
{
    public static class RunnableException extends RuntimeException
    {
        private final Exception runnableException;

        public RunnableException(final Exception runnableException)
        {
            this.runnableException = runnableException;
        }

        public Exception getException()
        {
            return this.runnableException;
        }
    }

    protected static final ObjectGenerator COUNTER_GENERATOR = new ArrayGenerator(int.class, 1)
    {
        public boolean prepareObject(final Object obj, final Object arg) throws IllegalArgumentException
        {
            try
            {
                ((int[])obj)[0] = 0;
                return true;
            }
            catch(final ClassCastException ex)
            {
                throw new IllegalArgumentException();
            }
        }
    };

    protected final int maxThreadCount;
    protected final long threadTimeout;
    protected final int maxThreadWaits;

    protected final Map threads = new HashMap();
    protected final ObjectRecycler counterRecycler;

    public ThreadLimiter(final int maxThreadCount, final long threadTimeout, final int maxThreadWaits)
    {
        this.maxThreadCount = maxThreadCount;
        this.threadTimeout = threadTimeout;
        this.maxThreadWaits = maxThreadWaits;

        this.counterRecycler = new CappedObjectRecycler(ThreadLimiter.COUNTER_GENERATOR, this.maxThreadCount);
    }

    protected String getCategoryName()
    {
        return this.getClass().getName();
    }

    public final void run(final Runnable toRun) throws TimeoutException, InterruptedException, Exception
    {
        final Thread thread = Thread.currentThread();
        int[] threadCounter;
        synchronized(this.threads)
        {
            threadCounter = (int[])this.threads.get(thread);
            if(threadCounter == null)
            {
                for(int i = 0; this.threads.size() >= maxThreadCount; ++i)
                {
                    if(i >= this.maxThreadWaits)
                    {
                        throw new TimeoutException();
                    }
                    this.threads.wait(this.threadTimeout);
                }
                threadCounter = (int[])this.counterRecycler.getObject();
                this.threads.put(thread, threadCounter);
            }
        }
        ++threadCounter[0];
        try
        {
            toRun.run();
        }
        catch(final ThreadLimiter.RunnableException ex)
        {
            throw ex.getException();
        }
        finally
        {
            if((--threadCounter[0]) <= 0)
            {
                synchronized(this.threads)
                {
                    this.threads.remove(thread);
                    this.threads.notify();
                }
            }
        }
    }

    public StringBuffer status(StringBuffer sb)
    {
        if(sb == null)
        {
            sb = new StringBuffer();
        }

        sb.append("maxThreadCount\t").append(this.maxThreadCount).append('\n');

        sb.append("threadTimeout\t").append(this.threadTimeout).append('\n');

        sb.append("maxThreadWaits\t").append(this.maxThreadWaits).append('\n');

        sb.append("active threads:");
        synchronized(this.threads)
        {
            if(this.threads.isEmpty())
            {
                sb.append(" none\n");
            }
            else
            {
                sb.append('\n');
                Map.Entry entry;
                for(final Iterator iterator = this.threads.entrySet().iterator(); iterator.hasNext();)
                {
                    entry = (Map.Entry)iterator.next();
                    sb.append(entry.getKey())
                      .append(" (")
                      .append(((int[])entry.getValue())[0])
                      .append(")\n");
                }
            }
        }

        return sb;
    }
}
