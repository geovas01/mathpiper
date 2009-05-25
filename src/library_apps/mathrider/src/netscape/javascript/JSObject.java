
package netscape.javascript;
import java.applet.Applet;
import org.mathrider.GeoGebraXMLParse;
import geogebra.GeoGebraApplet;

public class JSObject{
	private org.mathpiper.interpreters.Interpreter synchronousInterpreter;
	private GeoGebraXMLParse xmlParser;
	private GeoGebraApplet applet;
	
	private JSObject(GeoGebraApplet applet)
	{
		this.applet = applet;
		synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
		xmlParser = new org.mathrider.GeoGebraXMLParse();
	}//end constructor.

    public static JSObject getWindow(Applet applet){

	return new JSObject((GeoGebraApplet) applet);
    }
    
 
    public Object getMember(String name){

	return "True";
    }

    public Object getSlot(int index){
	return "True";
    }

    public void setMember(String name,Object value){
    }

    public void setSlot(int index,Object value){
    }

    public void removeMember(String name){
    }

    public Object call(String methodName,Object args[]){
	    
	    //System.out.println("AAAAAA: " + methodName + ",  " + args.length + ", " + args[0]);
	    String objectName = (String) args[0];
	    
	    if(methodName.equalsIgnoreCase("GeoGebraAddListener") || methodName.equalsIgnoreCase("GeoGebraUpdateListener"))
	    {
		    String xml = applet.getXML(objectName);
		    //System.out.println("BBBBBB: " + xml);
		    String expression;
		    if(xml.startsWith("<expression"))
		    {
			    expression = xml.substring(xml.indexOf("exp=\"") + 5, xml.indexOf("\"/>"));
			    expression = expression.replace("=",":=");
			    expression = expression.replace("\u00b9","^1");
			    expression = expression.replace("\u00b2","^2");
			    expression = expression.replace("\u00b3","^3");
			    expression = expression.replace("\u8308","^4");
			    expression = expression.replace("\u8309","^5");
			    expression = expression.replace("\u8310","^6");
			    expression = expression.replace("\u8311","^7");
			    expression = expression.replace("\u8312","^8");
			    expression = expression.replace("\u8313","^9");
			    expression = expression.replace(" ","");
			    //System.out.println("BBBBBB22: " + expression);
			    synchronousInterpreter.evaluate(expression + ";");
			    xml = xml.substring(xml.indexOf("\n"), xml.length());
		    }
		    
		    String list = xmlParser.parse(xml);
		    synchronousInterpreter.evaluate("Clear(" + objectName +");");
		    //System.out.println("CCCCC: " + list);
		    synchronousInterpreter.evaluate(objectName + " := " + list + ";");
	    }
	    /*else if(methodName.equalsIgnoreCase("GeoGebraUpdateListener"))
	    {
		    String xml = applet.getXML(objectName);
		    String list = xmlParser.parse(xml);
		    synchronousInterpreter.evaluate("Clear(" + objectName +");");
		    //System.out.println("PPPPPP: " + list);
		    org.mathpiper.interpreters.EvaluationResponse response = synchronousInterpreter.evaluate(objectName + " := " + list + ";");
		  
		    //System.out.println("QQQQQ: " + response.getResult() + "YYYY " + response.getExceptionMessage());
	    }*/
	    
	return "True";
    }

    public Object eval(String s){
	return "True";
    }
}

