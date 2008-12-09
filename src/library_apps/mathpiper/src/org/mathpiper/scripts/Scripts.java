/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.scripts;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;


public class Scripts
{

    private static Map scriptsMap = new HashMap();

    static{
        scriptsMap.put("factors.rep/code.mpi", org.mathpiper.scripts.Factors.code);
        scriptsMap.put("factors.rep/code.mpi.def", org.mathpiper.scripts.Factors.def);

    }

     public static InputStream getScriptStream(String scriptName)
    {
         /*String script = (String)scriptsMap.get(scriptName);
         if(script != null)
         {
            return new ByteArrayInputStream( script.getBytes() );
         }
         else
         {
             return null;
         }
          * */
         return null;

    }



}//end class.


