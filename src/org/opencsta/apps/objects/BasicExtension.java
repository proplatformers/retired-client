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

import java.util.Properties;
import org.opencsta.client.CSTAFunctions;
import org.opencsta.client.hipath3000.CSTAClient3000;
import org.opencsta.servicedescription.callcontrol.events.Delivered;
import org.opencsta.servicedescription.callcontrol.events.Diverted;
import org.opencsta.servicedescription.callcontrol.events.Established;
import org.opencsta.servicedescription.callcontrol.events.Failed;
import org.opencsta.servicedescription.common.AgentEvent;
import org.opencsta.servicedescription.common.CallEvent;

/**
 *
 * @author cm
 */
public class BasicExtension implements Comparable<BasicExtension>,CSTAApplication{
    private CSTAFunctions csta ;
    private int state ;
    private int rank ;
    private String id ;
    private final int HIGHNUMS = 3;
    private final int AVAILABLE = 0 ;
    private final int ALERTING = 1;
    private final int HOLDING = 2 ;
    private final int ONHOLD = 3 ;
    private final int LIVECALL = 4 ;
    
    public BasicExtension(){
        
    }
    
    public BasicExtension(String _id){
        this.id = _id ;
        this.rank = Integer.parseInt(this.id) ;
        this.state = 0 ;
        csta = new CSTAClient3000(new Properties() ) ;
        csta.RegisterParentApplication(this);
        csta.MonitorStart(this.id) ;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public int getBaccarat(){
        int num = getRank() ;
        while(  num  > 9 ){
            num = fun( num ) ;
        }
        return num ;
    }
    
    public int fun(int num){
        int sum = 0 ;
        String s = Integer.toString(num) ;
        for( int i = 0 ; i < s.length() ; i++ ){
            sum += Character.getNumericValue(s.charAt(i) ) ;
        }
        return sum ;
    }

    public void performAction(String victim){
//        System.out.println( getId() + " is using victim " + victim ) ;
        //use victim in case another party is needed.
        //otherwise use current call state, and current call info
        //for 
        if( state == AVAILABLE ){
            if( getBaccarat() >= HIGHNUMS ){
                csta.MakeCall(getId(), victim);
            }
            else{
                
            }
        }
        else if(state == ALERTING){
            if( getBaccarat() >= HIGHNUMS ){
                
            }
            else{
                
            }
        }
        else if(state == HOLDING ){
            if( getBaccarat() >= HIGHNUMS ){
                
            }
            else{
                
            }
        }
        else if(state == ONHOLD ){
            if( getBaccarat() >= HIGHNUMS ){
                
            }
            else{
                
            }
        }
        else if(state == LIVECALL){
            if( getBaccarat() >= HIGHNUMS ){
                
            }
            else{
                
            }
        }
    }

    public int compareTo(BasicExtension ext2) {
        return this.getRank() - ext2.getRank() ;
    }
    
    public CSTAFunctions getCSTA(){
        return csta ;
    }

    public void CSTACallEventReceived(CallEvent event) {
        if( event instanceof Failed){
            Failed f = (Failed)event ;
            csta.ClearConnection(getId(), f.get_failedConnection().get_callID().get_value() ) ;
        }
        else if( event instanceof Delivered){
            Delivered d = (Delivered)event ;
            csta.AnswerCall(getId(), d.get_connection().get_callID().get_value());
        }
        else if ( event instanceof Established ){
            Established e = (Established)event ;
            csta.ClearConnection(getId(), e.get_establishedConnection().get_callID().get_value());
        }
        else if ( event instanceof Diverted ){
            Diverted e = (Diverted)event ;
            csta.ClearConnection(e.get_divertingDevice().get_deviceID().getValue(), e.get_connection().get_callID().get_value());
        }
    }

    public void CSTAAgentEventReceived(AgentEvent event) {

    }

    public void TDSDataReceived(String dev, String code, String data) {

    }

    public void cstaFail() {
        throw new UnsupportedOperationException(this.getClass().getName() + " cstaFail Not supported yet.");
    }
}
