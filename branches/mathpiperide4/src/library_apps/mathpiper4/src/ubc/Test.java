package ubc;

import java.util.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;


public class Test
{
    public static void main (String argv[]) throws FileNotFoundException, IOException
    {
        iPrologFileServices   fs = new jPrologFileServices();
        PrintWriter   out = new PrintWriter(System.out);
        BufferedReader  in = new BufferedReader(new InputStreamReader(System.in));
        String knowledgeBase =  "queens(N,Qs) :- 	range(1,N,Ns), queens(Ns,[],Qs). queens(UnplacedQs, SafeQs, Qs) :- 	selectq(Q, UnplacedQs, UnplacedQs1), 	\\+ attack(Q,SafeQs), 	queens(UnplacedQs1,[Q|SafeQs],Qs). queens([],Qs,Qs). attack(X,Xs) :- attack(X, 1, Xs). attack(X,N,[Y|_]) :- X is Y+N ; X is Y-N. attack(X,N,[_|Ys]) :- N1 is N+1, attack(X,N1,Ys). range(M,N,[M|Ns]) :- M < N, M1 is M+1, range(M1,N,Ns). range(N,N,[N]). selectq(X,[X|Xs],Xs). selectq(X,[Y|Ys],[Y|Zs]) :- selectq(X,Ys,Zs). ";
        //jPrologAPI   api = new jPrologAPI(knowledgeBase,fs,out,in,null);

        jPrologAPI api = new jPrologAPI("");
        Hashtable    bindings = new Hashtable();
        Hashtable    result;
        StringBuffer   qbuff = new StringBuffer();

        api.consultSource(knowledgeBase);

        System.out.println(api.getRequiredCreditInfo());

        if (argv.length > 0)
            bindings.put("B",new Integer(Integer.parseInt(argv[0])));

        qbuff.append(" queens(4,X).");

        result = api.query(qbuff.toString(),null);//bindings);

        while (result != null)
        {
            System.out.println("The result is: " + result.get("X").toString());

            result = api.retry();
        }

        System.out.println("That's Every Solution!");
    };
};