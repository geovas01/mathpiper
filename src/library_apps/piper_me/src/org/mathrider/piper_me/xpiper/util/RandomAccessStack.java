package org.mathrider.piper_me.xpiper.util;

/*
 Copyright (c) Oliver Glier
 
 Warning: The licence of this file does not imply that the project
 of which it is part of has similar licence terms. In fact, this
 licence is very liberal and might be overruled by the project's
 own licence, for example the GPL.
 
 Redistribution and use in source and binary forms, with or without 
 modification, are permitted provided that the following conditions 
 are met:

 1. Redistributions of source code must retain the above copyright notice, 
 this list of conditions and the following disclaimer.
 2. Neither the name of ist authors or of its contributors may be used to 
 endorse or promote products derived from this software without specific
 prior written permission.

 THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//! Random Access Stack
/*<literate>*/
/**
 * Wrapper for Extendinbe Array in order to provide a mutable stack.
 */
public final class RandomAccessStack<E> extends ExtendibleArray<E> {
  public E pop() {
    return removeFirst();
  }

  public E top() {
    return get(0);
  }

  public void push(E item) {
    addFirst(item);
  }
}
