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
import java.util.Properties;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.client.CSTAFunctions;
import org.opencsta.client.asterisk.AsteriskCSTAClient;
import org.opencsta.client.ericssonapplink4.EricssonAppLink;
import org.opencsta.client.hipath3000.CSTAClient3000;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.common.CallEvent;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;

/**
 * 
 * @author chrismylonas
 */
public class CSTAMulti extends CSTAClientBase implements Runnable,
		CSTAFunctions, CSTA_Implementation_Functions {

	/**
	 * 
	 */
	private CSTAFunctions implementation;

	/**
	 * 
	 */
	private boolean runFlag;

	/**
	 * @param parent
	 * @param _theProps
	 */
	public CSTAMulti(CSTAApplication parent, Properties _theProps) {
		super(_theProps);
		RegisterParentApplication(parent);
		setImplementation();
		setRunFlag(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("static-access")
	public void run() {
		while (isRunFlag()) {
			try {
				while (getSizeWorklist() > 0) {
					StringBuffer sb = getCSTAJob();
					implementation.getCl7().WorkString(sb);
				}
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
     * 
     */
	private void setImplementation() {
		String imp = getPropertyImplementation();
		if (imp.equalsIgnoreCase("ASTERISK")) {
			log.info(this.getClass().getName() + " ---> "
					+ " IMPLEMENTATION: ASTERISK");
			implementation = new AsteriskCSTAClient(this, theProps);
		} else if (imp.equalsIgnoreCase("SIEMENS_HIPATH3000_CSTA")) {
			log.info(this.getClass().getName() + " ---> "
					+ " IMPLEMENTATION: SIEMENS HIPATH 3000");
			implementation = new CSTAClient3000(this, theProps);
			TDSEnable();
		} else if (imp.equalsIgnoreCase("ERICSSON_APPLINK_V4")) {
			log.info(this.getClass().getName() + " ---> "
					+ " IMPLEMENTATION: ERICSSON APPLINK 4.0");
			implementation = new EricssonAppLink(this, theProps);
		} else {
			log.info(this.getClass().getName() + " ---> "
					+ " IMPLEMENTATION: NONE!!!");
		}
	}

	/**
	 * @return
	 */
	private String getPropertyImplementation() {
		String tmp = "";
		try {
			tmp = theProps.getProperty("IMPLEMENTATION");
		} catch (NullPointerException npe) {

		}
		return tmp;

	}

	/**
	 * This is where the csta string first comes into contact with the client
	 * code after leaving the TCP code.
	 * 
	 * 
	 * @return
	 * @param curInStr
	 *            The string just received from the CSTA Server
	 */
	public boolean PassedUp(StringBuffer curInStr) {
		log.info(this.getClass().getName() + " ---> "
				+ " PassedUp - network should not drop out");
		if (curInStr.charAt(0) == 0xA1 || curInStr.charAt(0) == 0xA2) {
			log.info(this.getClass().getName() + " ---> " + " A1/A2 - normal");
		} else if (curInStr.charAt(0) == 0xA3 || curInStr.charAt(0) == 0xA4) {
			log.info(this.getClass().getName() + " ---> " + " A3/A4 - error");
		} else if (curInStr.charAt(0) == 0x55) {
			log.info(this.getClass().getName() + " ---> " + " A1 - event");
			return implementation.getCl7().WorkString(curInStr);
		} else if (curInStr.charAt(0) == 0x99) {
			log.info(this.getClass().getName() + " ---> "
					+ " 0x99 - TDS data received");
		} else if (curInStr.charAt(0) == 0x98) {
			log.info(this.getClass().getName() + " ---> "
					+ " 0x98 - TDS early termination stuff");
		} else {
			log.info(this.getClass().getName() + " ---> "
					+ " Other received from server - must follow up");
		}
		return implementation.getCl7().WorkString(curInStr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#MakeCall(java.lang.String,
	 * java.lang.String)
	 */
	public void MakeCall(String deviceFrom, String deviceTo) {
		log.info(this.getClass().getName() + " CALL CONTROL MAKE CALL: "
				+ deviceFrom + " -> " + deviceTo);
		implementation.MakeCall(deviceFrom, deviceTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#AnswerCall(java.lang.String,
	 * java.lang.String)
	 */
	public void AnswerCall(String device, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL ANSWER CALL: "
				+ call_id + " @ " + device);
		implementation.AnswerCall(device, call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#HoldCall(java.lang.String,
	 * java.lang.String)
	 */
	public void HoldCall(String device, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL HOLD CALL: "
				+ call_id + " @ " + device);
		implementation.HoldCall(device,call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#RetrieveCall(java.lang.String,
	 * java.lang.String)
	 */
	public void RetrieveCall(String device, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL RETRIEVE CALL: "
				+ call_id + " @ " + device);
		implementation.RetrieveCall(device,call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#ClearConnection(java.lang.String,
	 * java.lang.String)
	 */
	public void ClearConnection(String device, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL CLEAR CONNECTION: "
				+ call_id + " @ " + device);
		implementation.ClearConnection(device, call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#MonitorStart(java.lang.String)
	 */
	public void MonitorStart(String device) {
		log.info(this.getClass().getName() + " MONITORING (start): " + device);
		implementation.MonitorStart(device);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSDataReceived(java.lang.String,
	 * java.lang.String)
	 */
	public void TDSDataReceived(String dev, String code) {
		log.info(this.getClass().getName()
				+ " TELEPHONE DATA SERVICE (dev/code): " + dev + "/" + code);
		parent.TDSDataReceived(dev, code, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSDataReceived(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void TDSDataReceived(String dev, String code, String data) {
		log.info(this.getClass().getName()
				+ " TELEPHONE DATA SERVICE (dev/code/data): " + dev + " / "
				+ code + " / " + data);
		parent.TDSDataReceived(dev, code, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#SetDisplay(java.lang.String,
	 * java.lang.String, boolean)
	 */
	public void SetDisplay(String device1, String text, boolean beep) {
		log.info(this.getClass().getName() + " PHYSICAL CONTROL SET DISPLAY: "
				+ text + " @ " + device1 + " beep=" + beep);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TransferCall(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL TRANSFER CALL: "
				+ call_id + " @ " + deviceFrom + " -> " + deviceTo);
		implementation.TransferCall(deviceFrom,deviceTo,call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#DeflectCall(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
		log.info(this.getClass().getName() + " CALL CONTROL DEFLECT CALL: "
				+ call_id + " @ " + deviceFrom + " -> " + deviceTo);
		implementation.DeflectCall(deviceFrom,deviceTo,call_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencsta.client.CSTA_Implementation_Functions#RegisterParentApplication
	 * (org.opencsta.apps.objects.CSTAApplication)
	 */
	public void RegisterParentApplication(CSTAApplication parent) {
		log.info(this.getClass().getName() + " REGISTERING PARENT APPLICATION");
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#TDSEnable()
	 */
	public void TDSEnable() {
		log.info(this.getClass().getName()
				+ " TELEPHONE DATA SERVICE: TDS ENABLE - NOT SUPPORTED YET");
	}

	/**
	 * When a CSTA event is received of a call event type, it arrives here. From
	 * here anything can be done to it. It can be stored for later use, used
	 * immediately, it could trigger a condition, or it could just print to
	 * screen.
	 * 
	 * 
	 * @param event
	 *            The call event that arrives at the client.
	 */
	public void CSTAEventReceived(CallEvent_Base event) {
		System.out.println("Call Event");

		try {
			// **LOG**RUNNING FINE - event.toString
			System.out.println(event.toString());
		} catch (NullPointerException e) {
		}

		parent.CSTACallEventReceived((CallEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#CSTAEventReceived(AgentEvent_Base)
	 */
	public void CSTAEventReceived(AgentEvent_Base currentEvent) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#setCl7(org.opencsta.apps.objects.
	 * Client_Layer7_Impl)
	 */
	public void setCl7(Client_Layer7_Impl cl7) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.client.CSTAFunctions#getCl7()
	 */
	public Client_Layer7_Impl getCl7() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the runFlag
	 */
	public boolean isRunFlag() {
		return runFlag;
	}

	/**
	 * @param runFlag
	 *            the runFlag to set
	 */
	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

}
