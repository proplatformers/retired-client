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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.net.TCPClient;
import org.opencsta.net.TCPClientOwnerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the base class for CSTAClient classes. It sets up the TCP link
 * between it and the server, sets up the log files and implements the common
 * (3000 & 4000) call functions that are the same.
 * 
 * 
 * @author chrismylonas
 */
public abstract class CSTAClientBase implements TCPClientOwnerInterface {

	/**
	 * 
	 */
	private Thread tcpThread;

	/**
	 * 
	 */
	public static Logger log = LoggerFactory.getLogger(CSTAClientBase.class);

	/**
	 * 
	 */
	private List<StringBuffer> workList;

	/**
	 * the tcp link to the server
	 * 
	 */
	private TCPClient tcp;

	/**
	 * The properties file that the client uses to get some values
	 * 
	 */
	protected Properties theProps;

	/**
	 * the application that benefits from knowing what is happening at the phone
	 * 
	 */
	public CSTAApplication parent;

	/**
     * 
     */
	private String APPNAME;

	/**
	 * Creates a new instance of CSTAClientBase - the constructor
	 * 
	 */
	public CSTAClientBase(Properties _theProps) {
		theProps = _theProps;
		APPNAME = theProps.getProperty("CSTA_APPNAME");
		workList = Collections.synchronizedList(new LinkedList<StringBuffer>());
		tcp = new TCPClient(this, APPNAME, _theProps);
		tcp.setREPLACEDLEWITHDLEDLE(Boolean.parseBoolean(theProps
				.getProperty(APPNAME + "_REPLACEDLEWITHDLEDLE")));
		tcp.setREPLACEDLEDLEWITHDLE(Boolean.parseBoolean(theProps
				.getProperty(APPNAME + "_REPLACEDLEDLEWITHDLE")));
		tcp.setCSTAClientCommunications(Boolean.parseBoolean(theProps
				.getProperty(APPNAME + "_OPENCSTACLIENTSERVER")));
		tcpThread = new Thread(tcp, "TCP Thread");
		tcpThread.start();
		// LEAVING THESE HERE FOR NEXT REFACTOR TIME. THESE OBJECTS NEED
		// TO a) EXIST; and b) BE INSTANTIATED. ALL REFERENCES TO THEM
		// HAVE BEEN COMMENTED OUT TODAY (NOV2010)
		// callcontrols = new CallControl_Services_SiemensHipath3000() ;
		// ldfs = new LogicalDeviceFeatures_Services() ;
		// devicecontrols = new PhysicalDeviceFeatures_Services() ;
		log.info("CSTAClientBase - Started Base Functions and logging...getting tcp info");
		log.info(tcp.GetConnectionInfo());
	}

	/**
	 * @return
	 */
	public Socket getSocket() {
		return tcp.getSocket();
	}

	/**
     * 
     */
	public void release() {
		tcp.setRunFlag(false);
		tcpThread.interrupt();
		theProps = null;
		// LEFT HERE AS PER CONSTRUCTOR INSTRUCTIONS
		// callcontrols = null ;
		// ldfs = null ;
		// devicecontrols = null ;
		log.info(this.getClass().getName() + " -> "
				+ " all CSTA resources have been released");
		log.info(this.getClass().getName() + " -> "
				+ " releasing network resources");
		tcpThread = null;
		tcp = null;
	}

	/**
	 * 
	 */
	public void restartParent() {
		parent.cstaFail();
	}

	/**
	 * Send the string to the CSTAServer over TCP
	 * 
	 * 
	 * @param str
	 *            The string to send
	 */
	public void SendToServer(StringBuffer str) {
		tcp.Send(str);
	}

	/**
	 * returns true, doesn't do anything. The events go to another place.
	 * 
	 * 
	 * @return
	 * @param curInStr
	 */
	public boolean PassedUp(StringBuffer curInStr) {
		String received = "";
		for (int i = 0; i < curInStr.length(); i++) {
			received += Integer.toHexString(curInStr.charAt(i)) + " ";
		}
		log.info(this.getClass().getName() + "Received From Server - R: "
				+ received);
		return true;
	}

	/**
	 * Disconnects the socket
	 * 
	 */
	public void Quit() {
		tcp.Disconnect();
	}

	/**
	 * 
	 */
	public void ServerStatus() {
		char[] tmp = { 0x01, 0x01 };
		String tmps = new String(tmp);
		SendToServer(new StringBuffer(tmps));
	}

	/**
	 * 
	 */
	public void KillServer() {
		char[] tmp = { 0x01, 0x02 };
		String tmps = new String(tmp);
		SendToServer(new StringBuffer(tmps));
	}

	/**
	 * 
	 */
	public void cstaFail() {
		try {
			parent.cstaFail();
		} catch (NullPointerException e) {
			System.exit(0);
		}
	}

	/**
	 * @param str
	 */
	public synchronized void addWorkIN(StringBuffer str) {
		System.out.println("ADDED WORK IN");
		workList.add(str);
	}

	/**
	 * @return
	 */
	public synchronized StringBuffer getCSTAJob() {
		return (StringBuffer) workList.remove(0);
	}

	/**
	 * @return
	 */
	public int getSizeWorklist() {
		return workList.size();
	}

	/**
	 * @return the APPNAME
	 */
	public String getAPPNAME() {
		return APPNAME;
	}

	/**
	 * @param APPNAME
	 *            the APPNAME to set
	 */
	public void setAPPNAME(String APPNAME) {
		this.APPNAME = APPNAME;
	}

}
