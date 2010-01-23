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

package org.opencsta.client;

import java.net.Socket;
import org.opencsta.apps.objects.CSTAApplication ;
import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;

/**
 * CSTAFunctions is the highest level interface which all CSTAClient objects implement to get any CSTA
 * functionality going.
 *
 *
 * @author mylo
 */
public interface CSTAFunctions {


    public void CSTAEventReceived(CallEvent_Base currentEvent) ;
    public void CSTAEventReceived(AgentEvent_Base currentEvent) ;
    public void setCl7(Client_Layer7_Impl cl7) ;
    public Client_Layer7_Impl getCl7() ;
    public void SendToServer(StringBuffer sb) ;
    /**
     * Make a call from a device to another device
     *
     *
     * @param deviceFrom The calling device
     * @param deviceTo The called device
     */
    public void MakeCall(String deviceFrom, String deviceTo) ;
    
    /**
     * AnswerCall answers a call at an answering device
     *
     *
     * @param device The answering party
     * @param call_id The call to answer
     */
    public void AnswerCall(String device, String call_id) ;
    
    /**
     * HoldCall holds a call at holding device
     *
     *
     * @param device The holding party
     * @param call_id The call to be held
     */
    public void HoldCall(String device, String call_id) ;
    
    /**
     * RetrieveCall retrieves a held call
     *
     *
     * @param device The device where the call is to be retrieved from
     * @param call_id The call to be retrieved
     */
    public void RetrieveCall(String device, String call_id) ;
    
    /**
     * ClearConnection hangs up a call at a clearing device
     *
     *
     * @param device The hanging up device
     * @param call_id The call to be hung up or cleared
     */
    public void ClearConnection(String device, String call_id) ;
    
    /**
     * MonitorStart monitors an extension for call events only
     *
     *
     * @param device Device on which monitor is to be on
     */
    public void MonitorStart(String device) ;
    
    /**
     * The Telephone Data Service received data gets pumped through this method.
     *
     *
     * @param dev The device the logical i/o link comes to
     * @param code The code pressed [0-9]
     * @param data The data sent max length 24
     */
    public void TDSDataReceived(String dev, String code, String data) ;
    
    /**
     * The Telephone Data Service received data gets pumped through this method.
     * If the transmission is cut short however after the code is entered and no data sent
     * this method is called instead.
     *
     *
     * @param dev The device the logical i/o link comes to
     * @param code The code pressed [0-9]
     */
    public void TDSDataReceived(String dev, String code) ;
    
    /**
     * Sets the display of a device for a permanent amount of time.  To clear the
     * device to original settings, text is an empty string.
     *
     *
     * @param device1 The device to set display on
     * @param text The text to display
     * @param beep Whether or not to beep on delivery
     */
    public void SetDisplay(String device1, String text, boolean beep) ;
    
    /**
     * Transfer a call, (blindly i think),  to another device.
     *
     *
     * @param deviceFrom The place the call is at now but wants to transfer
     * @param deviceTo The destination where the call will go
     * @param call_id The call that is being transferred
     */
    public void TransferCall(String deviceFrom, String deviceTo, String call_id) ;
    
    /**
     * Deflect (divert) a call that is alerting queued or failed to another destination.
     *
     *
     * @param deviceFrom The device to deflect from
     * @param deviceTo The device to deflect to
     * @param call_id The call_id of the call
     */
    public void DeflectCall(String deviceFrom, String deviceTo, String call_id) ;
    
    
    /**
     * Notify the server that the TDS is to be instantiated and that this client is requesting it.
     *
     */
    public void TDSEnable() ;

}
