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

import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.servicedescription.common.CSTAGUIOwner;
import org.opencsta.servicedescription.common.CallEvent;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base ;
import org.opencsta.client.CSTAFunctions ;
import org.opencsta.client.CSTAClientBase ;
import org.opencsta.client.CSTAMulti;
import org.opencsta.servicedescription.callcontrol.services.CallControl_Services_SiemensHipath3000;
import org.opencsta.servicedescription.logicaldevicefeatures.services.LogicalDeviceFeatures_Services_SiemensHipath3000;
import org.opencsta.servicedescription.physicaldevicefeatures.services.PhysicalDeviceFeatures_Services_SiemensHipath3000;
/**
 * The Client for the Hipath 3000 range.
 *
 *
 * @author mylo
 */
public class CSTAClient3000 implements CSTAFunctions{
    /**
     * the client csta layer 7 portion
     *
     */
    private Client_Layer7_Impl cl7 ;
    
    private CSTAMulti cstaMulti ;
    protected PhysicalDeviceFeatures_Services_SiemensHipath3000 devicecontrols ;
    protected LogicalDeviceFeatures_Services_SiemensHipath3000 ldfs ;
    protected CallControl_Services_SiemensHipath3000 callcontrols ;

    
    //SHORTCUT: just for TDS display to gui
//    private CSTAGUIOwner gui ;
    //    private PNA_BASEPage pna ;
    
    
    
    
    /**
     * Creates a new instance of CSTAClient
     *
     */
    public CSTAClient3000(CSTAMulti cstamulti, Properties _theProps) {
        this.cstaMulti = cstamulti ;
        //**LOG**GENERAL INFO = Using: Hipath 3000 CSTA Client
        cl7 = new Client_Layer7(this) ;
        callcontrols = new CallControl_Services_SiemensHipath3000() ;
        ldfs = new LogicalDeviceFeatures_Services_SiemensHipath3000() ;
        devicecontrols = new PhysicalDeviceFeatures_Services_SiemensHipath3000() ;
        //**LOG**GENERAL FINE - CallControls created - OK
        
        //**LOG**GENERAL FINE - DeviceControls created - OK
        
        //**LOG**GENERAL FINE - LogicalDeviceFeatures created - OK
        //agentEvent = new AgentEvent() ;
        //callEvent = new CallEvent() ;
    }
    

    
//    /**
//     * dunno exactly
//     */
//    public void GUIIntro(CSTAGUIOwner gui){
//        this.gui = gui ;
//        //**LOG**GENERAL FINE - Graphical User Interface introduced - OK
//    }
    

    
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

    public void MakeCall(String deviceFrom, String deviceTo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void AnswerCall(String device, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void HoldCall(String device, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void RetrieveCall(String device, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void ClearConnection(String device, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void MonitorStart(String device) {
        MonitorStart_AgentsAndCalls(device) ;
    }

    public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Quit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void ServerStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Socket getSocket() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void release() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void CSTAEventReceived(CallEvent_Base currentEvent) {
        cstaMulti.CSTAEventReceived(currentEvent);
    }

    public void TDSDataReceived(String dev, String code, String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * @param cl7 the cl7 to set
     */
    public void setCl7(Client_Layer7_Impl cl7) {
        this.cl7 = cl7;
    }


    public Client_Layer7_Impl getCl7() {
        return cl7;
    }

    public void SendToServer(StringBuffer sb) {
        cstaMulti.SendToServer(sb) ;
    }
    

}
