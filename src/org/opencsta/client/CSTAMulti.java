/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;
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
 * @author cm
 */
public class CSTAMulti extends CSTAClientBase implements Runnable,CSTAFunctions,CSTA_Implementation_Functions{
//    private static Logger log = Logger.getLogger(CSTAMulti.class) ;
    private CSTAFunctions implementation ;
    private boolean runFlag ;
//    private CSTAApplication parent ;
//    private Properties someProps ;

    public CSTAMulti(CSTAApplication parent,Properties _theProps){
        super(_theProps) ;
//        someProps = _theProps ;
        RegisterParentApplication(parent);
        setImplementation() ;
        setRunFlag(true) ;
    }


    //listens for messages
    @SuppressWarnings("static-access")
    public void run() {
        while( isRunFlag() ){
            try{
                while( getSizeWorklist() > 0 ){
                    StringBuffer sb = getCSTAJob();
                    implementation.getCl7().WorkString(sb);
                }
                Thread.currentThread().sleep(100) ;
            }catch(InterruptedException e){
            }
        }
    }

    private void setImplementation(){
        String imp = getPropertyImplementation();
        if( imp.equalsIgnoreCase("ASTERISK") ){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: ASTERISK") ;
            implementation = new AsteriskCSTAClient(this,theProps) ;
        }
        else if( imp.equalsIgnoreCase("SIEMENS_HIPATH3000_CSTA")){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: SIEMENS HIPATH 3000") ;
            implementation = new CSTAClient3000(this,theProps) ;
            TDSEnable();
        }
        else if( imp.equalsIgnoreCase("ERICSSON_APPLINK_V4") ){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: ERICSSON APPLINK 4.0") ;
            implementation = new EricssonAppLink(this,theProps) ;
        }
        else{
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: NONE!!!") ;
        }
    }

    private String getPropertyImplementation(){
        String tmp = "" ;
        try{
            tmp = theProps.getProperty("IMPLEMENTATION") ;
        }catch(NullPointerException npe){

        }
        return tmp ;

    }



    /**
     * This is where the csta string first comes into contact with the client code after leaving the TCP code.
     *
     *
     * @return
     * @param curInStr The string just received from the CSTA Server
     */
    public boolean PassedUp(StringBuffer curInStr){
        log.info(this.getClass().getName() + " ---> " + " PassedUp - network should not drop out") ;
        if(curInStr.charAt(0) == 0xA1 || curInStr.charAt(0) == 0xA2){
            log.info(this.getClass().getName() + " ---> " + " A1/A2 - normal") ;
        } else if(curInStr.charAt(0) == 0xA3 || curInStr.charAt(0) == 0xA4){
            log.info(this.getClass().getName() + " ---> " + " A3/A4 - error") ;
        } else if(curInStr.charAt(0) == 0x55){
            log.info(this.getClass().getName() + " ---> " + " A1 - event") ;
            return implementation.getCl7().WorkString(curInStr) ;
        }
        else if(curInStr.charAt(0) == 0x99){
            log.info(this.getClass().getName() + " ---> " + " 0x99 - TDS data received") ;
        } else if(curInStr.charAt(0) == 0x98){
            log.info(this.getClass().getName() + " ---> " + " 0x98 - TDS early termination stuff") ;
        } else{
            log.info(this.getClass().getName() + " ---> " + " Other received from server - must follow up") ;
        }
        return implementation.getCl7().WorkString(curInStr) ;
    }


    public void MakeCall(String deviceFrom, String deviceTo) {
        log.info(this.getClass().getName() + " CALL CONTROL MAKE CALL: " + deviceFrom + " -> " + deviceTo) ;
        implementation.MakeCall(deviceFrom, deviceTo);
    }

    public void AnswerCall(String device, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL ANSWER CALL: " + call_id + " @ " + device) ;
    }

    public void HoldCall(String device, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL HOLD CALL: " + call_id + " @ " + device) ;
    }

    public void RetrieveCall(String device, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL RETRIEVE CALL: " + call_id + " @ " + device) ;
    }

    public void ClearConnection(String device, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL CLEAR CONNECTION: " + call_id + " @ " + device) ;
        implementation.ClearConnection(device, call_id);
    }

    public void MonitorStart(String device) {
        log.info(this.getClass().getName() + " MONITORING (start): " + device) ;
        implementation.MonitorStart(device);
    }


    /**
     * whatever it says in the interface docs
     *
     *
     * @param dev
     * @param code
     */
    public void TDSDataReceived(String dev, String code){
        log.info(this.getClass().getName() + " TELEPHONE DATA SERVICE (dev/code): " + dev + "/"+ code) ;
        parent.TDSDataReceived(dev,code,null) ;
    }



    
    /**
     * whatever it says in the interface docs
     *
     *
     * @param dev
     * @param code
     * @param data
     */
    public void TDSDataReceived(String dev, String code, String data){
        log.info(this.getClass().getName() + " TELEPHONE DATA SERVICE (dev/code/data): " + dev + " / " + code + " / " + data) ;
        parent.TDSDataReceived(dev,code,data) ;
    }

    public void SetDisplay(String device1, String text, boolean beep) {
        log.info(this.getClass().getName() + " PHYSICAL CONTROL SET DISPLAY: " + text + " @ " + device1 + " beep=" + beep) ;
    }

    public void TransferCall(String deviceFrom, String deviceTo, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL TRANSFER CALL: " + call_id + " @ " + deviceFrom + " -> " + deviceTo) ;
    }

    public void DeflectCall(String deviceFrom, String deviceTo, String call_id) {
        log.info(this.getClass().getName() + " CALL CONTROL DEFLECT CALL: " + call_id + " @ " + deviceFrom + " -> " + deviceTo) ;
    }

    public void RegisterParentApplication(CSTAApplication parent) {
        log.info(this.getClass().getName() + " REGISTERING PARENT APPLICATION") ;
        this.parent = parent ;
    }

    public void TDSEnable() {
        log.info(this.getClass().getName() + " TELEPHONE DATA SERVICE: TDS ENABLE - NOT SUPPORTED YET") ;
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
     * When a CSTA event is received of a call event type, it arrives here.  From here
     * anything can be done to it.  It can be stored for later use, used immediately, it
     * could trigger a condition, or it could just print to screen.
     *
     *
     * @param event The call event that arrives at the client.
     */
    public void CSTAEventReceived(CallEvent_Base event){
        System.out.println("Call Event") ;

        try{
            //**LOG**RUNNING FINE - event.toString
            System.out.println( event.toString() ) ;
        }catch(NullPointerException e){
        }

        parent.CSTACallEventReceived((CallEvent)event) ;
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

    /**
     * @return the runFlag
     */
    public boolean isRunFlag() {
        return runFlag;
    }

    /**
     * @param runFlag the runFlag to set
     */
    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }

}
