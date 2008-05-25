package org.mathrider.piper;


interface TResult
{
  public int NrLines(PiperGraphicsContext  aGraphicsContext, int width) ;
  public void draw(PiperGraphicsContext  aGraphicsContext, int current_word, int width, int height, int red, int green, int blue);
};

