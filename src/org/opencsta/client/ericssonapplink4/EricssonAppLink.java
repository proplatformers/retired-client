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
 * @author chrismylonas
 */
public class EricssonAppLink implements CSTAFunctions {

	/**
	 * 
	 */
	private Client_Layer7_Impl cl7;

	/**
	 * 
	 */
	protected PhysicalDeviceFeatures_Services_AppLink4 devicecontrols;

	/**
	 * 
	 */
	protected LogicalDeviceFeatures_Services_AppLink4 ldfs;

	/**
	 * 
	 */
	protected CallControl_Services_AppLink4 callcontrols;

	/**
	 * 
	 */
	private CSTAMulti cstaMulti;

	/**
	 * @param cstamulti
	 * @param _props
	 */
	public EricssonAppLink(CSTAMulti cstamulti, Properties _props) {
		this.cstaMulti = cstamulti;
		cl7 = new Client_Layer7(this);
		callcontrols = new CallControl_Services_AppLink4();
		devicecontrols = new PhysicalDeviceFeatures_Services_AppLink4();
		ldfs = new LogicalDeviceFeatures_Services_AppLink4();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#SetDisplay(java.lang.String,
	 * java.lang.String, boolean)
	 */
	public void SetDisplay(String device1, String text, boolean beep) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSDataReceived(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void TDSDataReceived(String dev, String code, String data) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSDataReceived(java.lang.String,
	 * java.lang.String)
	 */
	public void TDSDataReceived(String dev, String code) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @param parent
	 */
	public void RegisterParentApplication(CSTAApplication parent) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSEnable()
	 */
	public void TDSEnable() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#MonitorStart(java.lang.String)
	 */
	public void MonitorStart(String device) {
		MonitorStart_AgentsAndCalls(device);
	}

	/**
	 * Agent and Call events wanted for the specified device
	 * 
	 * 
	 * @param device
	 *            The device in question
	 */
	public void MonitorStart_AgentsAndCalls(String device) {
		// **LOG**RUNNING FINE - Monitor Start Logical Device Features and calls
		// <device>
		StringBuffer sb = callcontrols.MonitorStart(device);
		SendToServer(sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#MakeCall(java.lang.String,
	 * java.lang.String)
	 */
	public void MakeCall(String deviceFrom, String deviceTo) {
		StringBuffer req = callcontrols.MakeCall(deviceTo, deviceTo);
		SendToServer(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#AnswerCall(java.lang.String,
	 * java.lang.String)
	 */
	public void AnswerCall(String device, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#HoldCall(java.lang.String,
	 * java.lang.String)
	 */
	public void HoldCall(String device, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#RetrieveCall(java.lang.String,
	 * java.lang.String)
	 */
	public void RetrieveCall(String device, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#ClearConnection(java.lang.String,
	 * java.lang.String)
	 */
	public void ClearConnection(String device, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TransferCall(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#DeflectCall(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
     * 
     */
	public void Quit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
     * 
     */
	public void ServerStatus() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return
	 */
	public Socket getSocket() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
     * 
     */
	public void release() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#CSTAEventReceived(CallEvent_Base)
	 */
	public void CSTAEventReceived(CallEvent_Base currentEvent) {
		cstaMulti.CSTAEventReceived(currentEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#CSTAEventReceived(AgentEvent_Base)
	 */
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
	 * @param cl7
	 *            the cl7 to set
	 */
	public void setCl7(Client_Layer7_Impl cl7) {
		this.cl7 = cl7;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencsta.client.CSTAFunctions#SendToServer(java.lang.StringBuffer)
	 */
	public void SendToServer(StringBuffer sb) {
		Client_Layer7 l7 = (Client_Layer7) getCl7();
		sb = l7.Rose_Invoke(sb);
		sb = l7.Wrap(sb);
		cstaMulti.SendToServer(sb);

	}
}