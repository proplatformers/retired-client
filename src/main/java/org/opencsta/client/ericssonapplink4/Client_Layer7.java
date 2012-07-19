/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client.ericssonapplink4;

import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.common.helpers.CSTA_Layer_7_Common;
import org.opencsta.servicedescription.common.helpers.CallEventHandler;
import org.opencsta.servicedescription.logicaldevicefeatures.events.AgentEvent_Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chrismylonas
 */
public class Client_Layer7 extends CSTA_Layer_7_Common implements
		Client_Layer7_Impl {

	/**
	 * 
	 */
	Logger log = LoggerFactory.getLogger(Client_Layer7.class);

	/**
	 * 
	 */
	private String xRef;

	/**
	 * 
	 */
	EricssonAppLink parent;

	// BECAUSE APPLINK IS 'THE CSTA SERVER', WE ONLY NEED LIMITED SERVER
	// FUNCTIONALITY,
	// SO WE BORROW IT FROM CSTA_SERVER'S LAYER 7 FOR INVOKE ID GENERATION AND
	// OTHER
	// STUFF LIKE THAT
	/**
	 * 
	 */
	int invoke_a = 0;

	/**
	 * 
	 */
	int invoke_b = 0;

	/**
	 * 
	 */
	int invoke_c = 0;

	/**
	 * 
	 */
	int invoke_d = 0;

	/**
	 * @param _parent
	 */
	public Client_Layer7(EricssonAppLink _parent) {
		this.parent = _parent;
		callEventHandler = new CallEventHandler();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.apps.objects.Client_Layer7_Impl#WorkString(java.lang.
	 * StringBuffer)
	 */
	public boolean WorkString(StringBuffer curInStr) { // formerly PassedUp,
														// then
														// RoseResultPassedUp
		boolean invokeFlag = true;
		int counter = 0;
		while (curInStr.length() > 0) {
			for (int i = 0; i < curInStr.length(); i++) {
				System.out.print("0x"
						+ Integer.toHexString((int) curInStr.charAt(i)) + " ");
			}
			if (curInStr.charAt(0) == 0x0) {
				curInStr = DeleteChars(curInStr, 2);
			} else if (curInStr.charAt(0) == 0xa2) {
				return true;
			} else if (curInStr.charAt(0) == 0xa3 | curInStr.charAt(0) == 0xa4) {
				return true;
			} else if (curInStr.charAt(0) == 0xa1) {
				curInStr = DeleteChars(curInStr, 2);
			} else if (curInStr.charAt(0) == 0x02 & invokeFlag) {
				int len = (int) curInStr.charAt(1);
				String invoke = curInStr.substring(2, (2 + len));
				log.debug("INVOKE ID: ");
				for (int i = 0; i < invoke.length(); i++) {
					log.debug(Integer.toHexString((int) invoke.charAt(i)));
				}
				invokeFlag = false;
				curInStr = DeleteChars(curInStr, (2 + len));
			} else if (curInStr.charAt(0) == 0x02 & !invokeFlag) {
				if (curInStr.charAt(2) == 0x15) {
					curInStr = DeleteChars(curInStr, 5);
				}
			} else if (curInStr.charAt(0) == 0x55) {
				int length = curInStr.charAt(1);
				String xref = curInStr.substring(2, (2 + length));
				curInStr = DeleteChars(curInStr, (2 + length));
			} else if (curInStr.charAt(0) == 0x80) {
				log.info("Event Received");
				curInStr = DeleteChars(curInStr, 2);
				CallEvent_Base currentEvent = callEventHandler
						.WorkEvent_Ericsson(curInStr);
				parent.CSTAEventReceived(currentEvent);
			} else {
				log.warn(this.getClass().getName()
						+ " other start of CSTA string for workstring");
				curInStr = new StringBuffer();
			}
			counter++;
		}
		if (curInStr.length() > 0) {
			log.error("Unsupported CSTA Function: ");
			StringBuffer tmp = new StringBuffer();
			for (int i = 0; i < curInStr.length(); i++) {
				tmp.append(Integer.toHexString((int) curInStr.charAt(i)));
			}
			log.error(tmp.toString());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencsta.apps.objects.Client_Layer7_Impl#CSTAEventReceived(java.lang
	 * .StringBuffer)
	 */
	public void CSTAEventReceived(StringBuffer curInStr) {

		if (curInStr.charAt(0) == 0x55) {
			int length = (int) curInStr.charAt(1);
			xRef = curInStr.substring(2, (length + 2));
			curInStr = DeleteChars(curInStr, (length + 2));
		} else
			;

		if (curInStr.charAt(0) == 0xA0) {// call event

			curInStr = CheckLengthAndStrip(curInStr, 2);

			CallEvent_Base currentEvent = callEventHandler.WorkEvent(curInStr);

			parent.CSTAEventReceived((CallEvent_Base) currentEvent);
		} else if (curInStr.charAt(0) == 0xA3) {// hookswitch event

			return;
		} else if (curInStr.charAt(0) == 0xA4) {// logicalDeviceFeature Event
			curInStr = CheckLengthAndStrip(curInStr, 2);
			AgentEvent_Base currentEvent = logicalDeviceFeatureEventHandler
					.WorkEvent(curInStr);
			parent.CSTAEventReceived((AgentEvent_Base) currentEvent);
		} else
			return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.apps.objects.Client_Layer7_Impl#ParseTDSdata(java.lang.
	 * StringBuffer, boolean)
	 */
	public void ParseTDSdata(StringBuffer sb, boolean et) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// BORROWED CSTA SERVER INVOKE ID GENERATION CODE
	/**
	 * @return
	 */
	public String InvokeGeneration() {
		String invoke_id = new String();
		invoke_a++;

		if (invoke_a > 255) {
			invoke_a = 0;
			invoke_b++;
			if (invoke_b > 255) {
				invoke_b = 0;
				invoke_c++;
				if (invoke_c > 255) {
					invoke_c = 0;
					invoke_d++;
					if (invoke_d > 255) {
						invoke_d = 0;
						invoke_a = 1;
					}
				}
			}
		}

		if (invoke_d > 0) {
			char[] invID = { (char) invoke_d, (char) invoke_c, (char) invoke_b,
					(char) invoke_a };
			invoke_id = new String(invID);
			return invoke_id;
		} else if (invoke_c > 0) {
			char[] invID = { (char) invoke_c, (char) invoke_b, (char) invoke_a };
			invoke_id = new String(invID);
			return invoke_id;
		} else if (invoke_b > 0) {
			char[] invID = { (char) invoke_b, (char) invoke_a };
			invoke_id = new String(invID);
			return invoke_id;
		} else if (invoke_a > 0) {
			char[] invID = { (char) invoke_a };
			invoke_id = new String(invID);
			return invoke_id;
		}

		return invoke_id;
	}

	/**
	 * @param sb
	 * @return
	 */
	public StringBuffer Rose_Invoke(StringBuffer sb) {
		String invoke_id = InvokeGeneration();
		sb = sb.insert(0, invoke_id).insert(0, (char) invoke_id.length())
				.insert(0, INTEGER);
		sb = sb.insert(0, (char) sb.length()).insert(0, ROSE_INVOKE);
		return sb;
	}

	// BORROWED ROSE INVOKE STUFF
	/**
	 * @param sb
	 * @return
	 */
	public StringBuffer Wrap(StringBuffer sb) {
		sb = sb.insert(0, (char) sb.length()).insert(0, '\u0000');
		return sb;
	}

}
