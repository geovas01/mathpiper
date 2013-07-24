package vmm.xm3d;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vmm.core.Animation;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.UserExhibit;
import vmm.core.View;
import vmm.core.UserExhibit.Support;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.surface.implicit.SurfaceImplicit;
import vmm.surface.parametric.SurfaceParametric;

public class Test2 extends SurfaceParametric {

	public ArrayList<Vector3D> points;
	public Vector3D Xaxis;
	public Vector3D Yaxis;
	public Vector3D Zaxis;
	public boolean isParametric;
	public double Diameter;
	
	// Constructor used for getting the list of points to draw, the axis, and diameter of each point
	public Test2(boolean Parametric){
		isParametric = Parametric;
		points = new ArrayList<Vector3D>();
	}
	
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform, Transform3D newTransform) {
		if (exhibitNeedsRedraw)
			createData();
	}
	
    protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
        

    		DrawAxis(view);
        
    		DrawPoints(view,points);

    	
    }
    
    public void AddPoint(Vector3D p){
    	points.add(p);
    }
    
    // Used to draw each point given by constructor in ArrayList
    public void DrawPoints(View3D view, ArrayList<Vector3D> points){
    	Iterator<Vector3D> points3D = points.iterator();
    	while(points3D.hasNext())
    	{
    		view.drawDot(points3D.next(), Diameter);
    	}
    }
    
    // Used to just setup where the axis are and diameter for ALL points
    public void SetupAxisnDia(Vector3D x, Vector3D y, Vector3D z,double diameter){
    	Xaxis = x;
    	Yaxis = y;
    	Zaxis = z;
    	Diameter= diameter;
    }
    // Used to draw the axis for the 3D representation
    public void DrawAxis(View3D view){
    	
    	
    	
    	Vector3D origin = new Vector3D(0,0,0);
    	view.drawDot(origin,Diameter);
    	view.drawString("Origin", origin);
    	
    	view.drawDot(Xaxis,Diameter);
    	view.drawString("X-Axis", Xaxis);
    	
    	view.drawDot(Yaxis,Diameter);
    	view.drawString("Y-Axis", Yaxis);
    	
    	view.drawDot(Zaxis,Diameter);
    	view.drawString("Z-Axis", Zaxis);
    	
    	view.drawLine(origin,Xaxis);
    	view.drawLine(origin,Yaxis);
    	view.drawLine(origin,Zaxis);
    	
    }
    
 

	public Vector3D surfacePoint(double u, double v) {
		// TODO Auto-generated method stub
		if(isParametric) return new Vector3D (u,v,(Math.sin(u)*Math.cos(v)));
		else{
			return null;
		}
		
	}

    
    public static void main(String[] args) {
    	// you can put as many points in the array list as warranted

        Test2 exhibit = new Test2( true);

        exhibit.SetupAxisnDia(new Vector3D (10,0,0), new Vector3D (0,10,0), new Vector3D (0,0,10), 10.0);
        
        exhibit.AddPoint(new Vector3D (2,7,4));
        
        View view = exhibit.getDefaultView();
        
        WindowXM window = new WindowXM(Menus.SINGLE_GALLERY);

        //window.getMenus();

        if (exhibit != null) {
            window.getMenus().install(view, exhibit);
           
        }
        
        
        
        //Support support = ((UserExhibit) exhibit).getUserExhibitSupport();

        Display display = window.getDisplay();
        

        exhibit.computeDrawData(view, true, exhibit.previousTransform3D, exhibit.previousTransform3D);
        display.install(view, exhibit);
        
        window.setVisible(true);


    }//end main.

	

	
}//end class.

