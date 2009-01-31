package org.mathrider.piper_me.arithmetic.oldImpl;


  import org.eninom.numerics.*;
  import org.mathrider.piper_me.LispError;

  import org.mathrider.piper_me.arithmetic.*;
  
  public class YacasBigNumber extends BigNumber
  {
    public static boolean NumericSupportForMantissa()
    {
      return true;
    }


    //constructors
    static public YacasBigNumber create( String aString,int aBasePrecision,int aBase/*=10*/) {
      return new YacasBigNumber(aString, aBasePrecision, aBase);
    }
    
    private YacasBigNumber( String aString,int aBasePrecision,int aBase/*=10*/)
    {
      SetTo(aString, aBasePrecision, aBase);
    }
    
    private YacasBigNumber( YacasBigNumber aOther)
    {
      SetTo(aOther);
    }
    
    // no constructors from int or double to avoid automatic conversions
    private YacasBigNumber(int aPrecision/* = 20*/)
    {
      iPrecision = aPrecision;
      iTensExp = 0;
      integer = new BigInteger("0");
    }
    
    static public YacasBigNumber create(int aPrecision/* = 20*/)
    {
    return new YacasBigNumber(aPrecision);
    }
    
    // assign from another number
    private void SetTo( YacasBigNumber aOther)
    {
      iPrecision = aOther.GetPrecision();
      iTensExp = aOther.iTensExp;
      integer  = aOther.integer;
      decimal  = aOther.decimal;
    }
    
    public boolean IsFloat(String aString,int aBase)
    {
      if (aString.indexOf('.')>=0)
        return true;
      if (aBase>10)
        return false;
      if (aString.indexOf('e')>=0)
        return true;
      if (aString.indexOf('E')>=0)
        return true;
      return false;
    }
    // assign from string, precision in base digits
    private void SetTo( String aString,int aPrecision,int aBase/*=10*/)
    {
      integer = null;
      decimal = null;
      boolean isFloat = IsFloat(aString,aBase);

      iPrecision = aPrecision;
      iTensExp = 0;
      if (isFloat)
      {
        int decimalPos;
        decimalPos = aString.indexOf("e");
        if (decimalPos < 0)
          decimalPos = aString.indexOf("E");
        if (decimalPos > 0) // will never be zero
        {
          iTensExp = Integer.parseInt(aString.substring(decimalPos+1,aString.length()));
          aString = aString.substring(0,decimalPos);
        }

        decimal = new BigDecimal(aString); //TODO FIXME does not listen to aBase!!!
        if (decimal.scale() > iPrecision)
          iPrecision = decimal.scale();
      }
      else
      {
        integer = new BigInteger(aString, aBase);
      }
    }
    
    private YacasBigNumber(int pPrecision, int value) {
      iPrecision = pPrecision;
      iTensExp = 0;
      integer = BigInteger.valueOf(value);
    }
    
    private YacasBigNumber(int pPrecision, long value) {
      iPrecision = pPrecision;
      iTensExp = 0;
      integer = BigInteger.valueOf(value);
    }
    
    static public YacasBigNumber create(int pPrecision, long value) {
      return new YacasBigNumber(pPrecision, value);
    }
    
    static public YacasBigNumber create(int pPrecision, int value) {
      return new YacasBigNumber(pPrecision, value);
    }
    
    static public YacasBigNumber create(int pPrecision, double value) {
      return new YacasBigNumber(pPrecision, value);
    }
    
    private void SetTo(double value)
    {
      SetTo(""+value,iPrecision,10);
    }
    
    
    private YacasBigNumber(int pPrecision, double value) {
      iPrecision = pPrecision;
      iTensExp = 0;
      integer = new BigInteger("0");
      SetTo(value);
    }
    
    // Convert back to other types
    /// ToString : return string representation of number in aResult to given precision (base digits)
    public String ToString(int aPrecision, int aBase/*=10*/)
    {
      if (integer != null)
        return integer.toString(aBase);
      else
      {
        String result = decimal.toString();

        int extraExp = 0;
        // Parse out the exponent
        {
          int pos = result.indexOf("E");
          if (pos<0) pos = result.indexOf("e");
          if (pos > 0)
          {
            extraExp = Integer.parseInt(result.substring(pos+1));
            result = result.substring(0,pos);
          }
        }


        int dotPos = result.indexOf('.');
        if (dotPos >= 0)
        {
          int endpos = result.length();
          while (endpos>dotPos && result.charAt(endpos-1) == '0') endpos--;
          if (endpos > 1)
          {
            if (result.charAt(endpos-1) == '.' && result.charAt(endpos-2) >= '0' && result.charAt(endpos-2) <= '9')
            {
              endpos--;
            }
          }
          result = result.substring(0,endpos);
          if ((iTensExp+extraExp) != 0)
          {
            result = result + "e"+(iTensExp+extraExp);
          }
        }
        return result;
      }
    }
    /// Give approximate representation as a double number
    public double Double()
    {
      if (integer != null)
        return integer.doubleValue();
      else
        return decimal.doubleValue();
    }
    public long Long()
    {
      if (integer != null)
        return integer.longValue();
      else
        return decimal.longValue();
    }

    //basic object manipulation
    public boolean Equals( BigNumber b)
    {
    YacasBigNumber aOther = (YacasBigNumber) b;
    if (this == aOther)
      return true;
    
      if (integer != null)
      {
        if (aOther.integer == null)
        {
          //hier
          BigDecimal x = GetDecimal(this);
          if (x.compareTo(aOther.decimal) == 0)
            return true;
          return false;
        }
        return (integer.compareTo(aOther.integer) == 0);
      }
      if (decimal != null)
      {
        BigDecimal thisd = decimal;
        BigDecimal otherd = aOther.decimal;
        if (otherd == null)
        {
          otherd = GetDecimal(aOther);
        }
        if (iTensExp > aOther.iTensExp)
        {
          thisd = thisd.movePointRight(iTensExp - aOther.iTensExp);
        }
        else if (iTensExp < aOther.iTensExp)
        {
          otherd = otherd.movePointRight(iTensExp - aOther.iTensExp);
        }
        return (thisd.compareTo(otherd) == 0);
      }
      return true;
    }
    public boolean IsInt()
    {
      return (integer != null && decimal == null);
    }
    public boolean IsSmall()
    {
      if (IsInt())
      {
        BigInteger i = integer.abs();
        return (i.compareTo(new BigInteger("65535"))<0);
      }
      else
      // a function to test smallness of a float is not present in ANumber, need to code a workaround to determine whether a number fits into double.
      {
        //TODO fixme
        return true;
  /*
        LispInt tensExp = iNumber->iTensExp;
        if (tensExp<0)tensExp = -tensExp;
        return
        (
          iNumber->iPrecision <= 53  // standard float is 53 bits
          && tensExp<1021 // 306  // 1021 bits is about 306 decimals
        );
        // standard range of double precision is about 53 bits of mantissa and binary exponent of about 1021
  */
      }
    }
   
    private void BecomeFloat(int aPrecision/*=0*/)
    {
      if (integer != null)
      {
        decimal = new BigDecimal(integer);
        iTensExp = 0;
        integer = null;
      }
    }
    
    public YacasBigNumber becomeFloat()
    {
    YacasBigNumber x = new YacasBigNumber(this);
      x.BecomeFloat(0);
      return x;
    }
    
    public boolean LessThan( BigNumber b)
    {
      YacasBigNumber aOther = (YacasBigNumber) b;
      boolean floatResult = (decimal != null || aOther.decimal != null);
      if (floatResult)
      {
        BigDecimal dX = GetDecimal(this);
        BigDecimal dY = GetDecimal(aOther);
        return dX.compareTo(dY)<0;
      }
      else
      {
        return integer.compareTo(aOther.integer)<0;
      }
    }
    //arithmetic
    /// Multiply two numbers at given precision and put result in *this
     private void Multiply( YacasBigNumber aX,  YacasBigNumber aY, int aPrecision)
    {
      boolean floatResult = (aX.decimal != null || aY.decimal != null);
      if (floatResult)
      {
        BigDecimal dX = GetDecimal(aX);
        BigDecimal dY = GetDecimal(aY);
        integer = null;
        decimal = dX.multiply(dY);
        int newScale = iPrecision;
        if (newScale < decimal.scale())
          decimal = decimal.setScale(newScale,BigDecimal.ROUND_HALF_EVEN);
        iTensExp = aX.iTensExp + aY.iTensExp;
      }
      else
      {
        decimal = null;
        integer = aX.integer.multiply(aY.integer);
      }
    }
    
     
    public YacasBigNumber multiply(BigNumber b, int aPrecision) {
        YacasBigNumber x = new YacasBigNumber(aPrecision);
        x.Multiply(this, (YacasBigNumber)b, aPrecision);
        return x;
      }
     
    
    /// Add two numbers at given precision and return result in *this
    private void Add( YacasBigNumber aX,  YacasBigNumber aY, int aPrecision)
    {
      boolean floatResult = (aX.decimal != null || aY.decimal != null);
      if (floatResult)
      {
        BigDecimal dX = GetDecimal(aX);
        BigDecimal dY = GetDecimal(aY);

        integer = null;
        if (aX.iTensExp > aY.iTensExp)
        {
          dY = dY.movePointLeft(aX.iTensExp-aY.iTensExp);
          iTensExp = aX.iTensExp;
        }
        else if (aX.iTensExp < aY.iTensExp)
        {
          dX = dX.movePointLeft(aY.iTensExp-aX.iTensExp);
          iTensExp = aY.iTensExp;
        }
        decimal = dX.add(dY);
      }
      else
      {
        decimal = null;
        integer = aX.integer.add(aY.integer);
      }
    }
    /// Negate the given number, return result in *this
    private void Negate( YacasBigNumber aX)
    {
      if (aX.integer != null)
      {
        decimal = null;
        integer = aX.integer.negate();
      }
      if (aX.decimal != null)
      {
        integer = null;
        decimal = aX.decimal.negate();
        iTensExp = aX.iTensExp;
      }
    }

    public YacasBigNumber negate(int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.Negate(this);
      return x;
    }

    
    public YacasBigNumber add(BigNumber b, int aPrecision) {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.Add(this, (YacasBigNumber)b, aPrecision);
      return x;
    }

    
    /// Divide two numbers and return result in *this. Note: if the two arguments are integer, it should return an integer result!
    private void Divide( YacasBigNumber aX,  YacasBigNumber aY, int aPrecision)
    {
      boolean floatResult = (aX.decimal != null || aY.decimal != null);
      if (floatResult)
      {
        BigDecimal dX = GetDecimal(aX);
        BigDecimal dY = GetDecimal(aY);
        integer = null;
        int newScale = aPrecision+aY.GetPrecision();
        if (newScale > dX.scale())
          dX = dX.setScale(newScale);
        decimal = dX.divide(dY,BigDecimal.ROUND_HALF_EVEN);
        iPrecision = decimal.scale();
        iTensExp = aX.iTensExp-aY.iTensExp;
      }
      else
      {
        decimal = null;
        integer = aX.integer.divide(aY.integer);
      }
    }
    
    public YacasBigNumber divide(BigNumber b, int aPrecision) {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.Divide(this, (YacasBigNumber)b, aPrecision);
      return x;
    }

    /// integer operation: *this = y mod z
    private void Mod( YacasBigNumber aY,  YacasBigNumber aZ) throws Exception
    {
      LispError.Check(aY.integer != null, LispError.KLispErrNotInteger);
      LispError.Check(aZ.integer != null, LispError.KLispErrNotInteger);
//  TODO fixme    LispError.Check(!IsZero(aZ),LispError.KLispErrInvalidArg);
      integer = aY.integer.mod(aZ.integer);
      decimal = null;
    }
    
    public YacasBigNumber mod(BigNumber b, int aPrecision) throws Exception {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.Mod(this, (YacasBigNumber)b);
      return x;
    }

    /// For debugging purposes, dump internal state of this object into a string
    public String debug() throws Exception
    {
      if (integer != null)
      {
        return "integer: "+integer.toString();
      }
      else
      {
        return "decimal: "+decimal.unscaledValue()+" scale "+decimal.scale()+" x 10^("+iTensExp+")";
      }
    }

    /// assign self to Floor(aX) if possible
    private void Floor( YacasBigNumber aX)
    {
      if (aX.decimal != null)
      {
        BigDecimal d = aX.decimal;
        if (aX.iTensExp != 0)
        {
          d = d.movePointRight(aX.iTensExp);
        }
        BigInteger rounded = d.toBigInteger();
        if (aX.decimal.signum()<0)
        {
          BigDecimal back =  new BigDecimal(rounded);
          BigDecimal difference = aX.decimal.subtract(back);
          if (difference.signum() != 0)
          {
            rounded = rounded.add(new BigInteger("-1"));
          }
        }
        integer = rounded;
      }
      else
      {
        integer = aX.integer;
      }
      decimal = null;
    }
    
    public YacasBigNumber floor(int aPrecesion) {
      YacasBigNumber x = new YacasBigNumber(aPrecesion);
      x.Floor(this);
      return x;
    }
    /// set precision (in bits)
    private void Precision(int aPrecision)
    {
      iPrecision = aPrecision;
      if (decimal != null)
      {
        if (decimal.scale() > aPrecision)
        {
          decimal = decimal.setScale(aPrecision, BigDecimal.ROUND_HALF_EVEN);
        }
      }
    }
    
    public YacasBigNumber setPrecision(int aPrecision) {
      YacasBigNumber x = new YacasBigNumber(this);
      x.Precision(aPrecision);
      return x;
    }

    /// Bitwise operations, return result in *this.
    private void ShiftLeft(  YacasBigNumber aX, int aNrToShift) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      decimal = null;
      integer = aX.integer.shiftLeft(aNrToShift);
    }
    
    public YacasBigNumber shiftLeft(int aNrToShift, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.ShiftLeft(this,aNrToShift);
      return x;
    }

    private void ShiftRight(  YacasBigNumber aX, int aNrToShift) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      decimal = null;
      integer = aX.integer.shiftRight(aNrToShift);
    }
    
    public YacasBigNumber shiftRight(int aNrToShift, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.ShiftRight(this,aNrToShift);
      return x;
    }
   
    private void Gcd( YacasBigNumber aX,  YacasBigNumber aY) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      LispError.LISPASSERT(aY.integer != null);
      integer = aX.integer.gcd(aY.integer);
      decimal = null;
    }
    
    public YacasBigNumber gcd(BigNumber b, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.Gcd(this, (YacasBigNumber)b);
      return x;
    }
    
    private void BitAnd( YacasBigNumber aX,  YacasBigNumber aY) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      LispError.LISPASSERT(aY.integer != null);
      integer = aX.integer.and(aY.integer);
      decimal = null;
    }
    
    public YacasBigNumber bitAnd(BigNumber b, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.BitAnd(this,(YacasBigNumber)b);
      return x;
    }
    
    
    private void BitOr( YacasBigNumber aX,  YacasBigNumber aY) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      LispError.LISPASSERT(aY.integer != null);
      integer = aX.integer.or(aY.integer);
      decimal = null;
    }

    public YacasBigNumber bitOr(BigNumber b, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.BitOr(this,(YacasBigNumber)b);
      return x;
    }
    
    private void BitXor( YacasBigNumber aX,  YacasBigNumber aY) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      LispError.LISPASSERT(aY.integer != null);
      integer = aX.integer.xor(aY.integer);
      decimal = null;
    }
    
    public YacasBigNumber bitXor(BigNumber b, int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.BitXor(this,(YacasBigNumber)b);
      return x;
    }
    
    private void BitNot( YacasBigNumber aX) throws Exception
    {
      LispError.LISPASSERT(aX.integer != null);
      integer = aX.integer.not();
      decimal = null;
    }
    
    public YacasBigNumber bitNot(int aPrecision ) throws Exception
    {
      YacasBigNumber x = new YacasBigNumber(aPrecision);
      x.BitNot(this);
      return x;
    }
    
    /// Bit count operation: return the number of significant bits if integer, return the binary exponent if float (shortcut for binary logarithm)
    /// give bit count as a platform integer
    private static BigDecimal zero = new BigDecimal("0");
    private static BigDecimal one = new BigDecimal("1");
    private static BigDecimal two = new BigDecimal("2");

    public long BitCount()
    {
      //TODO fixme check that it works as needed
      if (integer != null)
        return integer.abs().bitLength();
      {
        BigDecimal d = decimal.abs();
        if (iTensExp != 0)
          d = d.movePointRight(iTensExp);
        if (d.compareTo(one)>0)
          return d.toBigInteger().bitLength();
        BigDecimal integerPart = new BigDecimal(d.toBigInteger());
        integerPart = integerPart.negate();
        d = d.add(integerPart);
        if (d.compareTo(zero) == 0)
          return 0;
        int bitCount = 0;
   
        //TODO OPTIMIZE
        d = d.multiply(two);
        while (d.compareTo(one)<0)
        {
          d = d.multiply(two);
          bitCount--;
        }
        return bitCount;
      }
    }
   
    /// Give sign (-1, 0, 1)
    public int Sign()
    {
      if (integer != null)
        return integer.signum();
      if (decimal != null)
        return decimal.signum();

      return 0;
    }

    public int GetPrecision()
    {
      return iPrecision;
    }

    int iPrecision;
    int iTensExp;

    BigDecimal GetDecimal(YacasBigNumber aNumber)
    {
      if (aNumber.decimal != null)
        return aNumber.decimal;
      return new BigDecimal(aNumber.integer);
    }

     /// Internal library wrapper starts here.
    BigInteger integer = null;
    BigDecimal decimal= null;
    /// Internal library wrapper ends here.
  }
