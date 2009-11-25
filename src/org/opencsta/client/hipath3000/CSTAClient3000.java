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

package org.opencsta.client.hipath3000;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.servicedescription.common.CSTAGUIOwner;
import org.opencsta.servicedescription.common.CallEvent;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base ;
import org.opencsta.client.CSTAFunctions ;
import org.opencsta.client.CSTAClientBase ;
/**
 * The Client for the Hipath 3000 range.
 *
 *
 * @author mylo
 */
public class CSTAClient3000 extends CSTAClientBase implements CSTAFunctions{
    Logger log = Logger.getLogger(CSTAClient3000.class) ;
    /**
     * the client csta layer 7 portion
     *
     */
    private Client_Layer7 cl7 ;
    
    
    
    
    
    //SHORTCUT: just for TDS display to gui
    private CSTAGUIOwner gui ;
    //    private PNA_BASEPage pna ;
    
    
    
    
    /**
     * Creates a new instance of CSTAClient
     *
     */
    public CSTAClient3000(Properties _theProps) {
        super(_theProps) ;
        //**LOG**GENERAL INFO = Using: Hipath 3000 CSTA Client
        cl7 = new Client_Layer7(this) ;
        //**LOG**GENERAL FINE - CallControls created - OK
        
        //**LOG**GENERAL FINE - DeviceControls created - OK
        
        //**LOG**GENERAL FINE - LogicalDeviceFeatures created - OK
        //agentEvent = new AgentEvent() ;
        //callEvent = new CallEvent() ;
    }
    
    /**
     * This is where the csta string first comes into contact with the client code after leaving the TCP code.
     *
     *
     * @return
     * @param curInStr The string just received from the CSTA Server
     */
    public boolean PassedUp(StringBuffer curInStr){
        log.info(this.getClass().getName() + " ---> " + " PassedUp - network should not drop out") ;
        if(curInStr.charAt(0) == 0xA1 || curInStr.charAt(0) == 0xA2){
            log.info(this.getClass().getName() + " ---> " + " A1/A2 - normal") ;
        } else if(curInStr.charAt(0) == 0xA3 || curInStr.charAt(0) == 0xA4){
            log.info(this.getClass().getName() + " ---> " + " A3/A4 - error") ;
        } else if(curInStr.charAt(0) == 0x55){
            log.info(this.getClass().getName() + " ---> " + " A1 - event") ;
            return cl7.WorkString(curInStr) ;
        }
        else if(curInStr.charAt(0) == 0x99){
            log.info(this.getClass().getName() + " ---> " + " 0x99 - TDS data received") ;
        } else if(curInStr.charAt(0) == 0x98){
            log.info(this.getClass().getName() + " ---> " + " 0x98 - TDS early termination stuff") ;
        } else{
            log.info(this.getClass().getName() + " ---> " + " Other received from server - must follow up") ;
        }
        return cl7.WorkString(curInStr) ;
    }
    
    /**
     * dunno exactly
     */
    public void GUIIntro(CSTAGUIOwner gui){
        this.gui = gui ;
        //**LOG**GENERAL FINE - Graphical User Interface introduced - OK
    }
    
    /**
     * When a CSTA event is received of a call event type, it arrives here.  From here
     * anything can be done to it.  It can be stored for later use, used immediately, it
     * could trigger a condition, or it could just print to screen.
     *
     *
     * @param event The call event that arrives at the client.
     */
    public void CSTAEventReceived(CallEvent_Base event){
        System.out.println("Call Event") ;
        
        try{
            //**LOG**RUNNING FINE - event.toString
            System.out.println( event.toString() ) ;
        }catch(NullPointerException e){
        }
        
        parent.CSTACallEventReceived((CallEvent)event) ;
    }
    
    /**
     * When a CSTA event is received of an agent event type, it arrives here.  From here
     * anything can be done to it.  It can be stored for later use, used immediately, it
     * could trigger a condition, or it could just print to screen.
     *
     *
     * @param event The agent event that arrives at the client.
     */
    public void CSTAEventReceived(AgentEvent_Base event){
        System.out.println("Agent Event") ;
        try{
            //**LOG**RUNNING FINE - event.toString
            System.out.println( event.toString() ) ;
        }catch(NullPointerException e){
        }
    }
    
    /**
     * This method can be moved to the parent class.
     *
     *
     * @param device1
     * @param text
     * @param beep
     */
    public void SetDisplay(String device1, String text, boolean beep){
        //**LOG**RUNNING FINE - Set Display <device> <text> <beep>
        StringBuffer sb = devicecontrols.SetDisplay(device1, text, beep) ;
        log.info("Ext: " + device1 + " " + text + beep) ;
        SendToServer(sb) ;
    }
    
    
    /**
     * A clear connection that was used for some custom application in time that was
     * PNA-ish.
     *
     *
     * @param device Presumably the device
     * @param call_id The call id
     */
    public void ClearConnection_PNA(String device, String call_id){
        //**LOG**RUNNING FINE - Clear Connection PNA <device> <call_id>
        StringBuffer sb = callcontrols.ClearConnection_PNA(device, call_id) ;
        SendToServer(sb) ;
    }
    
    /**
     * A monitor start for only connectionCleared and serviceInitiated events
     *
     *
     * @param device the device to be monitored
     */
    public void MonitorStart_PNA(String device){
        //**LOG**RUNNING FINE - Monitor Start PNA <device>
        StringBuffer sb = callcontrols.MonitorRequest_SI_CC_only(device) ;
        SendToServer(sb) ;
    }
    
    /**
     * Agent events only wanted to arrive for the device
     *
     *
     * @param device the device to be monitored
     */
    public void MonitorStart_Agents(String device){
        //**LOG**RUNNING FINE - Monitor Start Logical Device Features <device>
        StringBuffer sb = callcontrols.MonitorRequest_LogicalDeviceFeatures(device) ;
        SendToServer(sb) ;
    }
    
    /**
     * Agent and Call events wanted for the specified device
     *
     *
     * @param device The device in question
     */
    public void MonitorStart_AgentsAndCalls(String device){
        //**LOG**RUNNING FINE - Monitor Start Logical Device Features and calls <device>
        StringBuffer sb = callcontrols.MonitorRequest_LogicalDeviceFeaturesAndCalls(device) ;
        SendToServer(sb) ;
    }
    
    /**
     * prolly doesn't belong here anymore
     *
     *
     * @param device
     */
    public void MonitorStart_HP4000_CC_SI_ONLY(String device){
        //**LOG**RUNNING FINE - Monitor Start HP 4000 CC SI only <device>
        StringBuffer sb = callcontrols.MonitorRequest_HP4000_CC_only(device) ;
        SendToServer(sb) ;
    }
    
    
    
    
    
    /**
     * whatever it says in the interface docs
     *
     */
    
    public void TDSEnable(){
        //**LOG**RUNNING FINE - Telephone Data Service Enabled - OK
        StringBuffer sb = callcontrols.TDSClientRequest() ;
        SendToServer(sb) ;
    }
    
    /**
     * whatever it says in the interface docs
     *
     *
     * @param dev
     * @param code
     * @param data
     */
    public void TDSDataReceived(String dev, String code, String data){
        //**LOG**RUNNING FINE - TDS Received <device> <code> <data>
        if(gui != null)
            gui.TDSDataDisplay(dev, code, data) ;
        else
            parent.TDSDataReceived(dev,code,data) ;
    }
    
    /**
     * whatever it says in the interface docs
     *
     *
     * @param dev
     * @param code
     */
    public void TDSDataReceived(String dev, String code){
        //**LOG**RUNNING FINE - TDS Received <device> <code>
        System.out.println("Received tds data dev & code: " + dev + code ) ;
        if(gui != null)
            gui.TDSDataDisplay(dev,code, "") ;
        else
            parent.TDSDataReceived(dev,code,null) ;
    }
    
    /**
     * whatever it says in the interface docs
     *
     * @param parent the parent itself - say a gui or some other application
     * that would like to know what the phone is doing
     */
    public void RegisterParentApplication(CSTAApplication parent){
        //        System.out.println("PNA_Base.PNA_Intro - TDSabout to be enabled") ;
        //**LOG**RUNNING FINE - BASEPage introduced - OK
        this.parent = parent ;
        //        TDSenable() ;
    }
    

}
