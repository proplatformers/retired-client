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

import org.opencsta.apps.objects.Client_Layer7_Impl;
import org.opencsta.servicedescription.callcontrol.events.CallEvent_Base;
import org.opencsta.servicedescription.common.helpers.CSTA_Layer_7_Common;
import org.opencsta.servicedescription.common.helpers.CallEventHandler;
import org.opencsta.servicedescription.common.helpers.LogicalDeviceFeatureEventHandler;
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
	CSTAClient3000 parent;

	/**
	 * @param parent
	 */
	public Client_Layer7(CSTAClient3000 parent) {
		this.parent = parent;
		callEventHandler = new CallEventHandler();
		logicalDeviceFeatureEventHandler = new LogicalDeviceFeatureEventHandler();
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

		if (curInStr.charAt(1) == 0x81) {
			int length = curInStr.length();
			for (int i = 0; i < (length - 15); i++) {// cos otherwise we'll get
														// to the end of the
														// buffer and thrown and
														// exception,.,can't put
														// try{}catch here???
				if (curInStr.charAt(i) == 0x81
						&& curInStr.charAt(i + 1) >= 0x80)
					curInStr = curInStr.deleteCharAt(i);
				else
					;
			}
		} else
			;

		while (curInStr.length() != 0) {
			if (curInStr.charAt(0) == 0xA2)// ROSE RESPONSE
				curInStr = DeleteChars(curInStr, 2);
			else if (curInStr.charAt(0) == 0x02 && curInStr.charAt(1) != 0x01) {// INTEGER
																				// (INVOKE
																				// ID)
				int length = (int) curInStr.charAt(1);
				length += 2;
				curInStr = DeleteChars(curInStr, length);
			}

			else if (curInStr.charAt(0) == 0x30)// SEQUENCE
				curInStr = DeleteChars(curInStr, 2);

			else if (curInStr.charAt(0) == 0x02 && curInStr.charAt(1) == 0x01) {// WHICH
																				// SERVICE
																				// IT
																				// IS
				if (curInStr.charAt(2) == 0x15)// CSTA EVENT
					CSTAEventReceived(curInStr);
				else
					curInStr = DeleteChars(curInStr, 3);// ,
														// "WORKING...SERVICE_ID")
														// ;//IF SERVICE IS 0x15
														// = CSTA EVENT, process
														// somewhere else

				curInStr = new StringBuffer();
			}

			else if (curInStr.charAt(0) == 0x30)// SEQUENCE
				curInStr = DeleteChars(curInStr, 2);

			else if (curInStr.charAt(0) == 0x6B) {// CALL ID
				curInStr = curInStr.deleteCharAt(0).deleteCharAt(0);
				if (curInStr.charAt(0) == 0x30)// SEQUENCE
					curInStr = curInStr.deleteCharAt(0).deleteCharAt(0);
				if (curInStr.charAt(0) == 0x80) {// CALL ID
					int length = (int) curInStr.charAt(1);
					for (int i = 0; i < length + 2; i++)
						curInStr = curInStr.deleteCharAt(0);

				}
			}

			else if (curInStr.charAt(0) == 0x05)// NO DATA NULL
				curInStr = DeleteChars(curInStr, 2);// ,
													// "WORKING...NO DATA NULL")
													// ;

			else if (curInStr.charAt(0) == 0xA1) {// STATICID FOR THE DEVICE
				curInStr = DeleteChars(curInStr, 2);// , "WORKING...MIDWAY A1")
													// ;
			} else if (curInStr.charAt(0) == 0x55) {
				CSTAEventReceived(curInStr);
				curInStr = new StringBuffer();
			} else if (curInStr.charAt(0) == 0x80) {
				int length = (int) curInStr.charAt(1);
				length += 2;
				curInStr = DeleteChars(curInStr, length);// , "DEVICE") ;
			} else if (curInStr.charAt(0) == 0x99) {// TDS data received
				System.out.println("\n\n\t\t* CALLING * FROM * 0x99 * LAND");
				ParseTDSdata(curInStr, false);
				curInStr = new StringBuffer();
			} else if (curInStr.charAt(0) == 0x98) {
				// THIS WAS USED IN A NURSE PAGING APPLICATION
				// EARLY TERMINATION TDS DATA STUFF RECEIVED FOR QUICKER ACCESS
				// TO TDS CODE
				System.out.println("\n\n\t\t* CALLING * FROM * 0x98 * LAND");
				ParseTDSdata(curInStr, true);
				curInStr = new StringBuffer();
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.apps.objects.Client_Layer7_Impl#ParseTDSdata(java.lang.
	 * StringBuffer, boolean)
	 */
	public void ParseTDSdata(StringBuffer sb, boolean et) {
		System.out.println("\t***\t***\t***\tParse TDS data in Client Layer7");

		// boolean et, early termination, used for quick access to tds code for
		// client

		// SAMPLE 99 12 80 03 32 30 30 81 01 31 82 08 31 31 31 31 31 31 31 31
		sb = DeleteChars(sb, 2);
		int length = (int) sb.charAt(1);
		String dev = sb.substring(2, (2 + length));
		System.out.println("\t\tClientLayer7.ParseTDSdata() - dev: " + dev);
		sb = DeleteChars(sb, (length + 2));
		// SAMPLE now is: 81 01 31 82 08 31 31 31 31 31 31 31 31
		length = 1;// (int)sb.charAt(1) ;
		System.out.println("\t\tClientLayer7.ParseTDSdata() - code length: "
				+ Integer.toHexString(length));
		String code = sb.substring(2, (2 + length));
		if (et == false) {
			// SAMPLE now is: 82 08 31 31 31 31 31 31 31 31
			sb = DeleteChars(sb, (length + 2));
			length = (int) sb.charAt(1);
			String data = sb.substring(2, (2 + length));
			sb = DeleteChars(sb, (length + 2));
			parent.TDSDataReceived(dev, code, data);
		} else
			parent.TDSDataReceived(dev, code);
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

}
