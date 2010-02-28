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

package org.opencsta.client.polycom;

import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.client.CSTAFunctions;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;

/**
 *
 * @author cm
 */
public class PolycomCSTAClient implements CSTAFunctions{

    public void CSTAEventReceived(CallEvent_Base currentEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void CSTAEventReceived(AgentEvent_Base currentEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCl7(Client_Layer7_Impl cl7) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Client_Layer7_Impl getCl7() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void SendToServer(StringBuffer sb) {
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

    public void TDSDataReceived(String dev, String code, String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void SetDisplay(String device1, String text, boolean beep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSEnable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
