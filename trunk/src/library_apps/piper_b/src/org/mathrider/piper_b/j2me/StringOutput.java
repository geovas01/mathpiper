package org.mathrider.piper_b.j2me;


class StringOutput extends LispOutput
{
    public StringOutput(StringBuffer aString)
  {
    iString = aString;
  }
    public void PutChar(char aChar)
  {
    iString.append(aChar);
  }
    StringBuffer iString;
}
