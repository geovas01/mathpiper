package org.mathrider.piper_me;


class LispDefFiles extends LispAssociatedHash // <LispDefFile>
{
  LispDefFile File(String aFileName)
  {
    // Create a new entry
    LispDefFile file = (LispDefFile)get(aFileName);
    if (file == null)
    {
      LispDefFile newfile = new LispDefFile(aFileName);
      // Add the new entry to the hash table
      put(aFileName, newfile);
      file = (LispDefFile)get(aFileName);
    }
    return file;
  }
}
