
package netscape.javascript;
import java.applet.Applet;


public class JSObject{
	private org.mathpiper.interpreters.Interpreter synchronousInterpreter;
	private JSObject()
	{
		synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
	}//end constructor.

    public static JSObject getWindow(Applet a){

	return new JSObject();
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
	    
	    System.out.println("XXXXX: " + methodName);
	    synchronousInterpreter.evaluate("box := 7;");
	return "True";
    }

    public Object eval(String s){
	return "True";
    }
}

