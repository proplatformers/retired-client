/*
This file is part of Open CSTA.

    Open CSTA is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Open CSTA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Open CSTA.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.opencsta.apps.objects;

import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.servicedescription.common.AgentEvent ;
import org.opencsta.servicedescription.common.CallEvent ;

/**
 *
 * @author mylo
 */
public class CSTAAppImplementation_NeverUse implements CSTAApplication{
    
    /** Creates a new instance of CSTAAppImplementation_NeverUse */
    public CSTAAppImplementation_NeverUse() {
    }
    
    public void CSTACallEventReceived(CallEvent event){
        System.out.println("Call Event") ;
        try{
            //**LOG**RUNNING FINE - event.toString
            System.out.println( event.toString() ) ;
        }catch(NullPointerException e){
        }
    }
    
    public void CSTAAgentEventReceived(AgentEvent event){
        System.out.println("Agent Event") ;
        try{
            //**LOG**RUNNING FINE - event.toString
            System.out.println( event.toString() ) ;
        }catch(NullPointerException e){
        }
    }
    
    public void TDSDataReceived(String dev, String code, String data){
        String output = "\n\tDevice: " + dev + "\n\tCode: " + code + "\n\tData: " + data ;
        System.out.println(output) ;
    }

    public void cstaFail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
