/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathrider.piper.lisp;

	/** PiperArgStack implements a stack of pointers to objects that can be used to pass
	*  arguments to functions, and receive results back.
	*/
	public class PiperArgStack
	{

		//TODO appropriate constructor?
		public PiperArgStack(int aStackSize)
		{
			iStack = new PointerArray(aStackSize,null);
			iStackTop = 0;
			//printf("STACKSIZE %d\n",aStackSize);
		}

		public int GetStackTop()  {return iStackTop;}

		public void RaiseStackOverflowError() throws Exception
		{
			LispError.RaiseError("Argument stack reached maximum. Please extend argument stack with --stack argument on the command line.");
		}

		public void PushArgOnStack(Cons aObject) throws Exception
		{
			if (iStackTop >= iStack.Size())
			{
				RaiseStackOverflowError();
			}
			iStack.SetElement(iStackTop,aObject);
			iStackTop++;
		}

		public void PushNulls(int aNr)  throws Exception
		{
			if (iStackTop+aNr > iStack.Size())
			{
				RaiseStackOverflowError();
			}
			iStackTop+=aNr;
		}

		public Pointer GetElement(int aPos) throws Exception
		{
			LispError.LISPASSERT(aPos>=0 && aPos < iStackTop);
			return iStack.GetElement(aPos);
		}

		public void PopTo(int aTop) throws Exception
		{
			LispError.LISPASSERT(aTop<=iStackTop);
			while (iStackTop>aTop)
			{
				iStackTop--;
				iStack.SetElement(iStackTop,null);
			}
		}
		PointerArray iStack;
		int iStackTop;
	};
