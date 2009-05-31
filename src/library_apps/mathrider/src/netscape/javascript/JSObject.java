
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
			String independentXML = applet.getXML(objectName);
			String dependentXML = applet.getAlgorithmXML(objectName);
			System.out.println("BBBBBBindependentXML: " + independentXML);
			System.out.println("BBBBBBdependentXML: " + dependentXML);
			String expression = null;

			if(independentXML.startsWith("<expression") || !dependentXML.equalsIgnoreCase(""))
			{
				if(independentXML.startsWith("<expression"))
				{
					expression = independentXML.substring(independentXML.indexOf("exp=\"") + 5, independentXML.indexOf("\"/>"));
					independentXML = independentXML.substring(independentXML.indexOf("\n"), independentXML.length());
				}
				else
				{
					expression = dependentXML.substring(dependentXML.indexOf("exp=\"") + 5, dependentXML.indexOf("\"/>"));
				}//end else.

				expression = expression.replace("\u00b9","^1");
				expression = expression.replace("\u00b2","^2");
				expression = expression.replace("\u00b3","^3");
				expression = expression.replace("\u8308","^4");
				expression = expression.replace("\u8309","^5");
				expression = expression.replace("\u8310","^6");
				expression = expression.replace("\u8311","^7");
				expression = expression.replace("\u8312","^8");
				expression = expression.replace("\u8313","^9");
				String[] parts = expression.split("=");
				parts[1] = parts[1].trim();
				parts[1] = parts[1].replace(" ","*");
				expression = parts[0] + ":=" + parts[1];
				//expression = expression.replace(" ","");
				System.out.println("BBBBBBexpression: " + expression);
				synchronousInterpreter.evaluate(expression + ";");

			}//end if.


			String list = xmlParser.parse(independentXML);
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

