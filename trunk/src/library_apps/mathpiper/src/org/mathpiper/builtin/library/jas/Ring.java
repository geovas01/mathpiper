

package org.mathpiper.builtin.library.jas;

//Represents a JAS polynomial ring: GenPolynomialRing.

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenPolynomialTokenizer;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

//Methods to create ideals and ideals with parametric coefficients.
public class Ring {
    
    private PolynomialList pset;
    private GenPolynomialRing ring;
    private GreatestCommonDivisorAbstract engine;
    private SquarefreeAbstract sqf;
    private FactorAbstract factor;

    public Ring(String ringstr) throws Exception{

       StringReader sr = new StringReader( ringstr );
       GenPolynomialTokenizer tok = new GenPolynomialTokenizer(sr);
       pset = tok.nextPolynomialSet();
       ring = pset.ring;

       engine = GCDFactory.getProxy(ring.coFac);

       sqf = SquarefreeFactory.getImplementation(ring.coFac);

       factor = FactorFactory.getImplementation(ring.coFac);

    }//end method.


    public List gens()
    {

        /*
         List<GenPolynomial> genericPolynomials = ring.generators();
         List returnList = new ArrayList();
         for(GenPolynomial genericPolynomial: genericPolynomials)
        {
            returnList.add(new RingElem(genericPolynomial));
        }*/

        return ring.generators();
    }

}//end class.





