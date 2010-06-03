package vmm.xm3d;

import java.awt.Graphics2D;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.UserExhibit;
import vmm.core.View;
import vmm.core.UserExhibit.Support;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

public class Test2 extends Exhibit3D {

    protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
        Vector3D point1 = new Vector3D(0,0,0);
        view.drawDot(point1, 10);


        Vector3D point2 = new Vector3D(5,0,0);
        view.drawDot(point2, 10);


        Vector3D point3 = new Vector3D(0,5,0);
        view.drawDot(point3, 10);

        
        Vector3D point4 = new Vector3D(0,0,5);
        view.drawDot(point4, 10);


        view.drawLine(point1,point2);

        view.drawLine(point1,point3);

        view.drawLine(point1,point4);


        view.drawString("Hello", point4);

        view.drawString("Flat", 5, 5);

        

    }


    public static void main(String[] args) {
        Exhibit exhibit = new Test2();


        View view = exhibit.getDefaultView();




        WindowXM window = new WindowXM(Menus.SINGLE_GALLERY);

        //window.getMenus();

        if (exhibit != null) {
            window.getMenus().install(view, exhibit);
        }

        //Support support = ((UserExhibit) exhibit).getUserExhibitSupport();

        Display display = window.getDisplay();

        display.install(view, exhibit);

        window.setVisible(true);


    }//end main.

}//end class.

