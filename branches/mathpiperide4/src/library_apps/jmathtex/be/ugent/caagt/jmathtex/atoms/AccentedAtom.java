/* AccentedAtom.java
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

package be.ugent.caagt.jmathtex.atoms;

import be.ugent.caagt.jmathtex.xmlconfigurationparsers.TeXSymbolParser;
import be.ugent.caagt.jmathtex.fonts.Char;
import be.ugent.caagt.jmathtex.fonts.TeXFont;
import be.ugent.caagt.jmathtex.exceptions.InvalidTeXFormulaException;
import be.ugent.caagt.jmathtex.exceptions.InvalidSymbolTypeException;
import be.ugent.caagt.jmathtex.exceptions.SymbolNotFoundException;
import be.ugent.caagt.jmathtex.*;
import be.ugent.caagt.jmathtex.atoms.CharSymbol;
import be.ugent.caagt.jmathtex.boxes.StrutBox;
import be.ugent.caagt.jmathtex.boxes.VerticalBox;
import be.ugent.caagt.jmathtex.boxes.CharBox;
import be.ugent.caagt.jmathtex.boxes.HorizontalBox;
import be.ugent.caagt.jmathtex.boxes.Box;

/**
 * An atom representing another atom with an accent symbol above it.
 */
public class AccentedAtom extends Atom {
    
    // accent symbol
    private final SymbolAtom accent;
    
    // base atom
    protected Atom base = null;
    
    /**
     * Creates an AccentedAtom from a base atom and an accent symbol defined by its name
     *
     * @param base base atom
     * @param accentName name of the accent symbol to be put over the base atom
     * @throws InvalidSymbolTypeException if the symbol is not defined as an accent ('acc')
     * @throws SymbolNotFoundException if there's no symbol defined with the given name
     */
    public AccentedAtom(Atom base, String accentName)
    throws InvalidSymbolTypeException, SymbolNotFoundException {
        accent = SymbolAtom.get(accentName);
        
        if (accent.type == TeXConstants.TYPE_ACCENT)
            this.base = base;
        else
            throw new InvalidSymbolTypeException("The symbol with the name '"
                    + accentName + "' is not defined as an accent ("
                    + TeXSymbolParser.TYPE_ATTR + "='acc') in '"
                    + TeXSymbolParser.RESOURCE_NAME + "'!");
    }
    
    /**
     * Creates an AccentedAtom from a base atom and an accent symbol defined as a TeXFormula.
     * This is used for parsing MathML.
     *
     * @param base base atom
     * @param acc TeXFormula representing an accent (SymbolAtom)
     * @throws InvalidTeXFormulaException if the given TeXFormula does not represent a
     * 			single SymbolAtom (type "TeXConstants.TYPE_ACCENT")
     * @throws InvalidSymbolTypeException if the symbol is not defined as an accent ('acc')
     */
    public AccentedAtom(Atom base, TeXFormula acc)
    throws InvalidTeXFormulaException, InvalidSymbolTypeException {
        if (acc == null)
            throw new InvalidTeXFormulaException(
                    "The accent TeXFormula can't be null!");
        else {
            Atom root = acc.root;
            if (root instanceof SymbolAtom) {
                accent = (SymbolAtom) root;
                if (accent.type == TeXConstants.TYPE_ACCENT)
                    this.base = base;
                else
                    throw new InvalidSymbolTypeException(
                            "The accent TeXFormula represents a single symbol with the name '"
                            + accent.getName()
                            + "', but this symbol is not defined as an accent ("
                            + TeXSymbolParser.TYPE_ATTR + "='acc') in '"
                            + TeXSymbolParser.RESOURCE_NAME + "'!");
            } else
                throw new InvalidTeXFormulaException(
                        "The accent TeXFormula does not represent a single symbol!");
        }
    }
    
    public Box createBox(TeXEnvironment env) {
        TeXFont tf = env.getTeXFont();
        int style = env.getStyle();
        
        // set base in cramped style
        Box b = (base == null ? new StrutBox(0, 0, 0, 0) : base.createBox(env
                .crampStyle()));
        
        float u = b.getWidth();
        float s = 0;
        if (base instanceof CharSymbol)
            s = tf.getSkew(((CharSymbol) base).getCharFont(tf), style);
        
        // retrieve best Char from the accent symbol
        Char ch = tf.getChar(accent.getName(), style);
        while (tf.hasNextLarger(ch)) {
            Char larger = tf.getNextLarger(ch, style);
            if (larger.getWidth() <= u)
                ch = larger;
            else
                break;
        }
        
        // calculate delta
        float delta = Math.min(b.getHeight(), tf.getXHeight(style, ch
                .getFontCode()));
        
        // create vertical box
        VerticalBox vBox = new VerticalBox();
        
        // accent
        Box y;
        float italic = ch.getItalic();
        if (italic > TeXFormula.PREC) {
            y = new HorizontalBox(new CharBox(ch));
            y.add(new StrutBox(italic, 0, 0, 0));
        } else
            y = new CharBox(ch);
        
        // if diff > 0, center accent, otherwise center base
        float diff = (u - y.getWidth()) / 2;
        y.setShift(s + (diff > 0 ? diff : 0));
        if (diff < 0)
            b = new HorizontalBox(b, y.getWidth(), TeXConstants.ALIGN_CENTER);
        vBox.add(y);
        
        // kern
        vBox.add(new StrutBox(0, -delta, 0, 0));
        // base
        vBox.add(b);
        
        // set height and depth vertical box
        float total = vBox.getHeight() + vBox.getDepth(), d = b.getDepth();
        vBox.setDepth(d);
        vBox.setHeight(total - d);
        return vBox;
    }
}
