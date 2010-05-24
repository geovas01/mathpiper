

package vmm.xm3d;

import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.UserExhibit;
import vmm.core.View;
import vmm.core.UserExhibit.Support;
import vmm.surface.parametric.UserSurfaceParametric;


public class Test {

    public static void main(String[] args)
    {
        Exhibit exhibit = new UserSurfaceParametric();


        View view = exhibit.getDefaultView();

        


        WindowXM window = new WindowXM(Menus.SINGLE_GALLERY);

        //window.getMenus();

        if (exhibit != null)
                window.getMenus().install(view, exhibit);

        Support support = ((UserExhibit)exhibit).getUserExhibitSupport();

        Display display = window.getDisplay();

        display.install(view, exhibit);

        window.setVisible(true);


    }//end main.

}//end class.
