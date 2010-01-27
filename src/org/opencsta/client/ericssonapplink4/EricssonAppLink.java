/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client.ericssonapplink4;

import java.net.Socket;
import java.util.Properties;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.client.CSTAFunctions;
import org.opencsta.client.CSTAMulti;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.callcontrol.services.CallControl_Services_AppLink4;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.services.LogicalDeviceFeatures_Services_AppLink4;
import org.opencsta.servicedescription.physicaldevicefeatures.services.PhysicalDeviceFeatures_Services_AppLink4;

/**
 *
 * @author cm
 */
public class EricssonAppLink implements CSTAFunctions{
    private Client_Layer7_Impl cl7 ;
    protected PhysicalDeviceFeatures_Services_AppLink4 devicecontrols ;
    protected LogicalDeviceFeatures_Services_AppLink4 ldfs ;
    protected CallControl_Services_AppLink4 callcontrols ;
    private CSTAMulti cstaMulti ;

    public EricssonAppLink(CSTAMulti cstamulti,Properties _props){
        this.cstaMulti = cstamulti ;
        cl7 = new Client_Layer7(this) ;
        callcontrols = new CallControl_Services_AppLink4() ;
        devicecontrols = new PhysicalDeviceFeatures_Services_AppLink4() ;
        ldfs = new LogicalDeviceFeatures_Services_AppLink4() ;
    }

    /*
     *
     * The following needs to be looked at further.  Ignore for now.
     *
     * */
    public void SetDisplay(String device1, String text, boolean beep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code, String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void RegisterParentApplication(CSTAApplication parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSEnable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     * 
     * 
     * */


//    //AppLink sends 0x0 0xlength ROSE-INVOKE/ROSE-RESPONSE
//    public boolean PassedUp(StringBuffer curInStr){
//        log.info(this.getClass().getName() + " ---> " + " PassedUp - network should not drop out") ;
//        if(curInStr.charAt(0) == 0xA1 || curInStr.charAt(0) == 0xA2){
//            log.info(this.getClass().getName() + " ---> " + " A1/A2 - normal") ;
//        } else if(curInStr.charAt(0) == 0xA3 || curInStr.charAt(0) == 0xA4){
//            log.info(this.getClass().getName() + " ---> " + " A3/A4 - error") ;
//        } else if(curInStr.charAt(0) == 0x55){
//            log.info(this.getClass().getName() + " ---> " + " A1 - event") ;
//            return cl7.WorkString(curInStr) ;
//        }
//        else if(curInStr.charAt(0) == 0x99){
//            log.info(this.getClass().getName() + " ---> " + " 0x99 - TDS data received") ;
//        } else if(curInStr.charAt(0) == 0x98){
//            log.info(this.getClass().getName() + " ---> " + " 0x98 - TDS early termination stuff") ;
//        } else{
//            log.info(this.getClass().getName() + " ---> " + " Other received from server - must follow up") ;
//        }
//        return cl7.WorkString(curInStr) ;
//    }
//
//    protected void CSTAEventReceived(CallEvent_Base callEvent_Base) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    protected void CSTAEventReceived(AgentEvent_Base agentEvent_Base) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }



    public void MonitorStart(String device){
        MonitorStart_AgentsAndCalls(device) ;
    }
    /**
     * Agent and Call events wanted for the specified device
     *
     *
     * @param device The device in question
     */
    public void MonitorStart_AgentsAndCalls(String device){
        //**LOG**RUNNING FINE - Monitor Start Logical Device Features and calls <device>
        StringBuffer sb = callcontrols.MonitorStart(device) ;
        SendToServer(sb) ;
    }

    public void MakeCall(String deviceFrom, String deviceTo) {
        StringBuffer req = callcontrols.MakeCall(deviceTo, deviceTo) ;
        SendToServer(req);
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

    public void CSTAEventReceived(AgentEvent_Base currentEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the cl7
     */
    public Client_Layer7_Impl getCl7() {
        return cl7;
    }

    /**
     * @param cl7 the cl7 to set
     */
    public void setCl7(Client_Layer7_Impl cl7) {
        this.cl7 = cl7;
    }

    public void SendToServer(StringBuffer sb) {
        Client_Layer7 l7 = (Client_Layer7)getCl7() ;
        sb = l7.Rose_Invoke(sb) ;
        sb = l7.Wrap(sb) ;
        cstaMulti.SendToServer(sb);

    }

}
