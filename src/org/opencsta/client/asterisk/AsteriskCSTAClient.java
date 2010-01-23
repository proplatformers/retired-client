/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client.asterisk;

import java.net.Socket;
import java.util.Properties;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.client.CSTAFunctions;
import org.opencsta.client.CSTAMulti;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;

/**
 *
 * @author cm
 */
public class AsteriskCSTAClient implements CSTAFunctions{
    private CSTAMulti cstaMulti ;
    private Client_Layer7_Impl cl7 ;

    public AsteriskCSTAClient(CSTAMulti cstamulti,Properties _props){
        this.cstaMulti = cstamulti ;
        cl7 = new Client_Layer7() ;
    }

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void CSTAEventReceived(CallEvent_Base currentEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void CSTAEventReceived(AgentEvent_Base currentEvent) {
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

    /**
     * @return the cl7
     */
    public Client_Layer7_Impl getCl7() {
        return cl7;
    }

    public void setCl7(Client_Layer7_Impl cl7) {
        this.cl7 = cl7 ;
    }

    public void SendToServer(StringBuffer sb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
