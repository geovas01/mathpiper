/* DefaultTeXFont.java
 * =========================================================================
 * This file is part of the JMathTeX Library - http://jmathtex.sourceforge.net
 *
 * Copyright (C) 2004-2007 Universiteit Gent
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 */

package be.ugent.caagt.jmathtex;

import java.awt.Font;
import java.util.Map;

/**
 * The default implementation of the TeXFont-interface. All font information is read
 * from an xml-file.
 */
class DefaultTeXFont implements TeXFont {
    
    private static String[] defaultTextStyleMappings;
    
    /**
     * No extension part for that kind (TOP,MID,REP or BOT)
     */
    protected static final int NONE = -1;
    
    protected final static int NUMBERS = 0;
    protected final static int CAPITALS = 1;
    protected final static int SMALL = 2;
    
    private static Map<String,CharFont[]> textStyleMappings;
    private static Map<String,CharFont>  symbolMappings;
    private static FontInfo[] fontInfo;
    private static Map<String,Float> parameters;
    private static Map<String,Number> generalSettings;
    
    protected static final int TOP = 0, MID = 1, REP = 2, BOT = 3;
    
    protected static final int WIDTH = 0, HEIGHT = 1, DEPTH = 2, IT = 3;
    
    static {
        DefaultTeXFontParser parser = new DefaultTeXFontParser();
        // general font parameters
        parameters = parser.parseParameters();
        // general settings
        generalSettings = parser.parseGeneralSettings();
        // text style mappings
        textStyleMappings = parser.parseTextStyleMappings();
        // default text style : style mappings
        defaultTextStyleMappings = parser.parseDefaultTextStyleMappings();
        // symbol mappings
        symbolMappings = parser.parseSymbolMappings();
        // fonts + font descriptions
        fontInfo = parser.parseFontDescriptions();
        
        // check if mufontid exists
        int muFontId = generalSettings.get(DefaultTeXFontParser.MUFONTID_ATTR).intValue();
        if (muFontId < 0 || muFontId >= fontInfo.length || fontInfo[muFontId] == null)
            throw new XMLResourceParseException(
                    DefaultTeXFontParser.RESOURCE_NAME,
                    DefaultTeXFontParser.GEN_SET_EL,
                    DefaultTeXFontParser.MUFONTID_ATTR,
                    "contains an unknown font id!");
    }
    
    private final float size; // standard size
    
    public DefaultTeXFont(float pointSize) {
        size = pointSize;
    }
    
    public TeXFont deriveFont(float size) {
        return new DefaultTeXFont(size);
    }
    
    public float getAxisHeight(int style) {
        return getParameter("axisheight") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getBigOpSpacing1(int style) {
        return getParameter("bigopspacing1") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getBigOpSpacing2(int style) {
        return getParameter("bigopspacing2") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getBigOpSpacing3(int style) {
        return getParameter("bigopspacing3") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getBigOpSpacing4(int style) {
        return getParameter("bigopspacing4") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getBigOpSpacing5(int style) {
        return getParameter("bigopspacing5") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    private Char getChar(char c, CharFont[] cf, int style) {
        int kind, offset;
        if (c >= '0' && c <= '9') {
            kind = NUMBERS;
            offset = c - '0';
        } else if (c >= 'a' && c <= 'z') {
            kind = SMALL;
            offset = c - 'a';
        } else {
            kind = CAPITALS;
            offset = c - 'A';
        }
        
        // if the mapping for the character's range, then use the default style
        if (cf[kind] == null)
            return getDefaultChar(c, style);
        else
            return getChar(new CharFont((char) (cf[kind].c + offset),
                    cf[kind].fontId), style);
    }
    
    public Char getChar(char c, String textStyle, int style)
    throws TextStyleMappingNotFoundException {
        Object mapping = textStyleMappings.get(textStyle);
        if (mapping == null) // text style mapping not found
            throw new TextStyleMappingNotFoundException(textStyle);
        else
            return getChar(c, (CharFont[]) mapping, style);
    }
    
    public Char getChar(CharFont cf, int style) {
        float size = getSizeFactor(style);
        FontInfo info = fontInfo[cf.fontId];
        Font font = info.getFont();
        return new Char(cf.c, font.deriveFont(size), cf.fontId, getMetrics(cf,
                size));
    }
    
    public Char getChar(String symbolName, int style)
    throws SymbolMappingNotFoundException {
        Object obj = symbolMappings.get(symbolName);
        if (obj == null) // no symbol mapping found!
            throw new SymbolMappingNotFoundException(symbolName);
        else
            return getChar((CharFont) obj, style);
    }
    
    public Char getDefaultChar(char c, int style) {
        // these default text style mappings will allways exist,
        // because it's checked during parsing
        if (c >= '0' && c <= '9')
            return getChar(c, defaultTextStyleMappings[NUMBERS], style);
        else if (c >= 'a' && c <= 'z')
            return getChar(c, defaultTextStyleMappings[SMALL], style);
        else
            return getChar(c, defaultTextStyleMappings[CAPITALS], style);
    }
    
    public float getDefaultRuleThickness(int style) {
        return getParameter("defaultrulethickness") * getSizeFactor(style)
        * PIXELS_PER_POINT;
    }
    
    public float getDenom1(int style) {
        return getParameter("denom1") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getDenom2(int style) {
        return getParameter("denom2") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public Extension getExtension(Char c, int style) {
        Font f = c.getFont();
        int fc = c.getFontCode();
        float s = getSizeFactor(style);
        
        // construct Char for every part
        FontInfo info = fontInfo[fc];
        int[] ext = info.getExtension(c.getChar());
        Char[] parts = new Char[ext.length];
        for (int i = 0; i < ext.length; i++) {
            if (ext[i] == NONE)
                parts[i] = null;
            else
                parts[i] = new Char((char) ext[i], f, fc, getMetrics(new CharFont(
                        (char) ext[i], fc), s));
        }
        
        return new Extension(parts[TOP], parts[MID], parts[REP], parts[BOT]);
    }
    
    public float getKern(CharFont left, CharFont right, int style) {
        if (left.fontId == right.fontId){
            FontInfo info = fontInfo[left.fontId];
            return info.getKern(left.c, right.c, getSizeFactor(style)
            * PIXELS_PER_POINT);
        } else
            return 0;
    }
    
    public CharFont getLigature(CharFont left, CharFont right) {
        if (left.fontId == right.fontId) {
            FontInfo info =  fontInfo[left.fontId];
            return info.getLigature(left.c, right.c);
        } else
            return null;
    }
    
    private Metrics getMetrics(CharFont cf, float size) {
        FontInfo info = fontInfo[cf.fontId];
        float[] m = info.getMetrics(cf.c);
        return new Metrics(m[WIDTH], m[HEIGHT], m[DEPTH], m[IT], size
                * PIXELS_PER_POINT);
    }
    
    public int getMuFontId() {
        return generalSettings.get(DefaultTeXFontParser.MUFONTID_ATTR).intValue();
    }
    
    public Char getNextLarger(Char c, int style) {
        FontInfo info = fontInfo[c.getFontCode()];
        CharFont ch = info.getNextLarger(c.getChar());
        FontInfo newInfo = fontInfo[ch.fontId];
        return new Char(ch.c, newInfo.getFont().deriveFont(getSizeFactor(style)),
                ch.fontId, getMetrics(ch, getSizeFactor(style)));
    }
    
    public float getNum1(int style) {
        return getParameter("num1") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getNum2(int style) {
        return getParameter("num2") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getNum3(int style) {
        return getParameter("num3") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getQuad(int style, int fontCode) {
        FontInfo info = fontInfo[fontCode];
        return info.getQuad(getSizeFactor(style) * PIXELS_PER_POINT);
    }
    
    public float getSize() {
        return size;
    }
    
    public float getSkew(CharFont cf, int style) {
        FontInfo info = fontInfo[cf.fontId];
        char skew = info.getSkewChar();
        if (skew == -1)
            return 0;
        else
            return getKern(cf, new CharFont(skew, cf.fontId), style);
    }
    
    public float getSpace(int style) {
        int spaceFontId = generalSettings
                .get(DefaultTeXFontParser.SPACEFONTID_ATTR).intValue();
        FontInfo info = fontInfo[spaceFontId];
        return info.getSpace(getSizeFactor(style) * PIXELS_PER_POINT);
    }
    
    public float getSub1(int style) {
        return getParameter("sub1") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSub2(int style) {
        return getParameter("sub2") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSubDrop(int style) {
        return getParameter("subdrop") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSup1(int style) {
        return getParameter("sup1") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSup2(int style) {
        return getParameter("sup2") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSup3(int style) {
        return getParameter("sup3") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getSupDrop(int style) {
        return getParameter("supdrop") * getSizeFactor(style) * PIXELS_PER_POINT;
    }
    
    public float getXHeight(int style, int fontCode) {
        FontInfo info = fontInfo[fontCode];
        return info.getXHeight(getSizeFactor(style) * PIXELS_PER_POINT);
    }
    
    public boolean hasNextLarger(Char c) {
        FontInfo info = fontInfo[c.getFontCode()];
        return (info.getNextLarger(c.getChar()) != null);
    }
    
    public boolean hasSpace(int font) {
        FontInfo info = fontInfo[font];
        return info.hasSpace();
    }
    
    public boolean isExtensionChar(Char c) {
        FontInfo info = fontInfo[c.getFontCode()];
        return info.getExtension(c.getChar()) != null;
    }
    
    private static float getParameter(String parameterName) {
        Object param = parameters.get(parameterName);
        if (param == null)
            return 0;
        else
            return ((Float) param).floatValue();
    }
    
    private static float getSizeFactor(int style) {
        if (style < TeXConstants.STYLE_SCRIPT)
            return 1;
        else if (style < TeXConstants.STYLE_SCRIPT_SCRIPT)
            return generalSettings.get("scriptfactor").floatValue();
        else
            return generalSettings.get("scriptscriptfactor").floatValue();
    }
}
