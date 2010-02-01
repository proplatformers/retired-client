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

//import apps.pna.PNA_Base;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.opencsta.net.TCPClientOwnerInterface;
import org.opencsta.net.TCPClient ;
import java.util.Properties ;
import org.opencsta.servicedescription.callcontrol.services.CallControl_Services_SiemensHipath3000;
import org.opencsta.servicedescription.logicaldevicefeatures.services.LogicalDeviceFeatures_Services_SiemensHipath3000;
import org.opencsta.servicedescription.physicaldevicefeatures.services.PhysicalDeviceFeatures_Services_SiemensHipath3000;
import org.apache.log4j.Logger ;
import org.opencsta.apps.objects.CSTAApplication;

/**
 * This class is the base class for CSTAClient classes.  It sets up the TCP link between it and the server,
 * sets up the log files and implements the common (3000 & 4000) call functions that are the same.
 *
 *
 * @author mylo
 */
public abstract class CSTAClientBase implements TCPClientOwnerInterface{
    private Thread tcpThread  ;
    public static Logger log = Logger.getLogger(CSTAClientBase.class) ;
    private List<StringBuffer> workList ;
    /**
     * the call controls offered
     *
     */
//    protected CallControl_Services_SiemensHipath3000 callcontrols ;
    
    /**
     * the tcp link to the server
     *
     */
    private TCPClient tcp ;
    /**
     * the logical device services offered
     *
     */
//    protected LogicalDeviceFeatures_Services ldfs ;
    
//    /*
//     * ignore this til a better implementation later of whatever it does
//     *
//     */
//    private PNA_Base pnab ;
    
    /**
     * the physical device features offered
     *
     */
//    protected PhysicalDeviceFeatures_Services devicecontrols ;
    
    /**
     * The properties file that the client uses to get some values
     *
     */
    protected Properties theProps ;
    

        /**
     * the application that benefits from knowing what is happening at the phone
     *
     */
    public CSTAApplication parent ;

    private String APPNAME ;
//    /**
//     * The Logger object for the client
//     *
//     */
//    protected static Logger log ;
//    
//    /**
//     * The log's filehandler
//     *
//     */
//    protected static FileHandler fh ;
    
    /**
     * Creates a new instance of CSTAClientBase - the constructor
     *
     */
    public CSTAClientBase(Properties _theProps) {
//        theProps = PropertiesController.getInstance() ;
//        CreateLogFile() ;
        theProps = _theProps ;
        APPNAME = theProps.getProperty("CSTA_APPNAME") ;
        workList = Collections.synchronizedList( new LinkedList<StringBuffer>() );
        tcp = new TCPClient(this,APPNAME,_theProps) ;
        tcp.setREPLACEDLEWITHDLEDLE(Boolean.parseBoolean( theProps.getProperty(APPNAME + "_REPLACEDLEWITHDLEDLE" ) )) ;
        tcp.setREPLACEDLEDLEWITHDLE(Boolean.parseBoolean(theProps.getProperty(APPNAME + "_REPLACEDLEDLEWITHDLE"))) ;
        tcp.setCSTAClientCommunications( Boolean.parseBoolean(theProps.getProperty(APPNAME + "_OPENCSTACLIENTSERVER")) ) ;
        tcpThread = new Thread(tcp,"TCP Thread") ;
        tcpThread.start() ;
//        callcontrols = new CallControl_Services_SiemensHipath3000() ;
//        ldfs = new LogicalDeviceFeatures_Services() ;
//        devicecontrols = new PhysicalDeviceFeatures_Services() ;
        log.info("CSTAClientBase - Started Base Functions and logging...getting tcp info");
        log.info( tcp.GetConnectionInfo() ) ;
    }

    public Socket getSocket(){
        return tcp.getSocket() ;
    }

    public void release(){
        tcp.setRunFlag(false);
        tcpThread.interrupt();
        theProps = null ;
//        callcontrols = null ;
//        ldfs = null ;
//        devicecontrols = null ;
        log.info( this.getClass().getName() + " -> " + " all CSTA resources have been released" ) ;
        log.info( this.getClass().getName() + " -> " + " releasing network resources") ;
        tcpThread = null ;
        tcp = null ;
    }

    public void restartParent(){
        parent.cstaFail() ;
    }
    
    /**
     * Looks at the properties file and creates log files based on that
     * information.
     *
     */
//    protected void CreateLogFile(){
//        Properties tmp_props = theProps ;
//        log = Logger.getLogger(tmp_props.getProperty("LOGGER_CSTACLIENT_APPLICATION")) ;
//        
//        try{
//            fh = new FileHandler((tmp_props.getProperty("BASE_DIR") + tmp_props.getProperty("LOG_CSTACLIENT_CSTA")),
//                    Integer.parseInt(tmp_props.getProperty("BEHAVIOUR_LOG_SIZELIMIT")),
//                    Integer.parseInt(tmp_props.getProperty("BEHAVIOUR_LOG_COUNT")),
//                    Boolean.getBoolean(tmp_props.getProperty("BEHAVIOUR_LOG_APPEND")) ) ;
//            
//            
//        }catch(IOException e){
//            e.printStackTrace() ;
//        }catch(Exception e){
//            e.printStackTrace() ;
//        }
//        
//        //ADD FILE HANDLERS TO THE LOGGERs
//        log.addHandler(fh) ;
//    }
    
    /**
     * Send the string to the CSTAServer over TCP
     *
     *
     * @param str The string to send
     */
    public void SendToServer(StringBuffer str){
        tcp.Send(str) ;
    }
    
    /**
     * returns true, doesn't do anything.
     * The events go to another place.
     *
     *
     * @return
     * @param curInStr
     */
    public boolean PassedUp(StringBuffer curInStr){
        String received = "" ;
        for(int i = 0 ; i < curInStr.length() ; i++ ){
            received += Integer.toHexString(curInStr.charAt(i) ) + " " ;
        }
        log.info(this.getClass().getName() + "Received From Server - R: " + received) ;
//        System.out.println(this.getClass().getName() + "Received From Server\n\t" + received) ;
        return true ;
    }
    
    /**
     * MakeCall is used to call from one device (calling party) to another device (called party)
     *
     *
     * @param deviceFrom The calling party
     * @param deviceTo The called party
     */
//    public void MakeCall(String deviceFrom, String deviceTo){
//        log.info(this.getClass().getName() + " ---> " + "Make Call " + deviceFrom + " to " + deviceTo) ;
//        StringBuffer sb = callcontrols.MakeCall(deviceFrom, deviceTo) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * AnswerCall answers a call at an answering device
     *
     *
     * @param device The answering party
     * @param call_id The call to answer
     */
//    public void AnswerCall(String device, String call_id){
//        //**LOG**RUNNING FINE - Answer Call <device> <call_id>
//        StringBuffer sb = callcontrols.AnswerCall(device, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * HoldCall holds a call at holding device
     *
     *
     * @param device The holding party
     * @param call_id The call to be held
     */
//    public void HoldCall(String device, String call_id){
//        //**LOG**RUNNING FINE - Hold Call <device> <call_id>
//        StringBuffer sb = callcontrols.HoldCall(device, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * RetrieveCall retrieves a held call
     *
     *
     * @param device The device where the call is to be retrieved from
     * @param call_id The call to be retrieved
     */
//    public void RetrieveCall(String device, String call_id){
//        //**LOG**RUNNING FINE - Retrieve Call <device> <call_id>
//        StringBuffer sb = callcontrols.RetrieveCall(device, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * ClearConnection hangs up a call at a clearing device
     *
     *
     * @param device The hanging up device
     * @param call_id The call to be hung up or cleared
     */
//    public void ClearConnection(String device, String call_id){
//        log.info(this.getClass().getName() + " ---> " + "Clear Connection" + call_id + " @ " + device) ;
//        StringBuffer sb = callcontrols.ClearConnection(device, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * MonitorStart monitors an extension for call events only
     *
     *
     * @param device Device on which monitor is to be on
     */
//    public void MonitorStart(String device){
//        //**LOG**RUNNING FINE - Monitor Start <device>
//        StringBuffer sb = callcontrols.MonitorRequest(device) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * Transfer a call, (blindly i think),  to another device.
     *
     *
     * @param deviceFrom The place the call is at now but wants to transfer
     * @param deviceTo The destination where the call will go
     * @param call_id The call that is being transferred
     */
//    public void TransferCall(String deviceFrom, String deviceTo, String call_id){
//        //**LOG**RUNNING FINE - Transfer Call <deviceFrom> <deviceTo> <call_id>
//        StringBuffer sb = callcontrols.SingleStepTransfer(deviceFrom, deviceTo, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * Deflect (divert) a call that is alerting queued or failed to another destination.
     *
     *
     * @param deviceFrom The device to deflect from
     * @param deviceTo The device to deflect to
     * @param call_id The call_id of the call
     */
//    public void DeflectCall(String deviceFrom, String deviceTo, String call_id){
//        //**LOG**RUNNING FINE - Deflect Call <deviceFrom> <deviceTo> <call_id>
//        StringBuffer sb = callcontrols.DeflectCall(deviceFrom, deviceTo, call_id) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * Sets the display of a device for a permanent amount of time.  To clear the
     * device to original settings, text is an empty string.
     *
     *
     * @param device1 The device to set display on
     * @param text The text to display
     * @param beep Whether or not to beep on delivery
     */
//    public abstract void SetDisplay(String device1, String text, boolean beep);
    
    /**
     * Requests a new agent state at a specified device
     *
     *
     * @param device The device to change state at
     * @param agentID The agent id
     * @param requestedState the requested state
     */
//    public void ChangeAgentState(String device, String agentID, int requestedState){
//        //LOG**RUNNING FINE - Change Agent State <device> <agentID> <requestedState>
//        StringBuffer sb = ldfs.setAgentState(device,agentID,requestedState) ;
//        SendToServer(sb) ;
//    }
    
    /**
     * Disconnects the socket
     *
     */
    public void Quit(){
        tcp.Disconnect() ;
    }

    public void ServerStatus(){
        char[] tmp = {0x01,0x01};
        String tmps = new String(tmp);
        SendToServer(new StringBuffer(tmps)) ;
    }

    public void KillServer(){
        char[] tmp = {0x01,0x02};
        String tmps = new String(tmp);
        SendToServer(new StringBuffer(tmps)) ;
    }

    public void cstaFail(){
        try{
            parent.cstaFail();
        }catch(NullPointerException e){
            System.exit(0) ;
        }
    }

    public synchronized void addWorkIN(StringBuffer str){
        System.out.println("ADDED WORK IN") ;
        workList.add(str);
    }

	public synchronized StringBuffer getCSTAJob(){
		return (StringBuffer)workList.remove(0) ;
	}

	public int getSizeWorklist(){
		return workList.size() ;
	}

    /**
     * @return the APPNAME
     */
    public String getAPPNAME() {
        return APPNAME;
    }

    /**
     * @param APPNAME the APPNAME to set
     */
    public void setAPPNAME(String APPNAME) {
        this.APPNAME = APPNAME;
    }

}
