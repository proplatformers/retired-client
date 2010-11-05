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
 * @author chrismylonas
 */
public class AsteriskCSTAClient implements CSTAFunctions {

	/**
	 * 
	 */
	private CSTAMulti cstaMulti;

	/**
	 * 
	 */
	private Client_Layer7_Impl cl7;

	/**
	 * @param cstamulti
	 * @param _props
	 */
	public AsteriskCSTAClient(CSTAMulti cstamulti, Properties _props) {
		this.cstaMulti = cstamulti;
		cl7 = new Client_Layer7();
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
	 * @see org.opencsta.client.CSTAFunctions#MakeCall(java.lang.String,
	 * java.lang.String)
	 */
	public void MakeCall(String deviceFrom, String deviceTo) {
		throw new UnsupportedOperationException("Not supported yet.");
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
	 * @see org.opencsta.client.CSTAFunctions#MonitorStart(java.lang.String)
	 */
	public void MonitorStart(String device) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#CSTAEventReceived(CallEvent_Base)
	 */
	public void CSTAEventReceived(CallEvent_Base currentEvent) {
		throw new UnsupportedOperationException("Not supported yet.");
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

	/**
	 * @return the cl7
	 */
	public Client_Layer7_Impl getCl7() {
		return cl7;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#setCl7(org.opencsta.apps.objects.
	 * Client_Layer7_Impl)
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
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
