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
import org.opencsta.client.CSTAFunctions;
import org.opencsta.client.asterisk.AsteriskCSTAClient;
import org.opencsta.client.ericssonapplink4.EricssonAppLink;
import org.opencsta.client.hipath3000.CSTAClient3000;

/**
 *
 * @author cm
 */
public class CSTAMulti implements Runnable,CSTAFunctions{
    private static Logger log = Logger.getLogger(CSTAMulti.class) ;
    private CSTAFunctions implementation ;
    private CSTAApplication parent ;
    private Properties theProps ;

    public CSTAMulti(Properties _theProps){
        this.theProps = _theProps ;
        setImplementation() ;
    }


    //listens for messages in both directions
    @SuppressWarnings("static-access")
    public void run() {
        try{
            Thread.currentThread().sleep(50) ;
        }catch(InterruptedException e){
        }
    }

    private void setImplementation(){
        String imp = getPropertyImplementation();
        if( imp.equalsIgnoreCase("ASTERISK") ){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: ASTERISK") ;
            implementation = new AsteriskCSTAClient(theProps) ;
        }
        else if( imp.equalsIgnoreCase("SIEMENS_HIPATH3000_CSTA")){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: SIEMENS HIPATH 3000") ;
            implementation = new CSTAClient3000(theProps) ;
            TDSEnable();
        }
        else if( imp.equalsIgnoreCase("ERICSSON_APPLINK_V4") ){
            log.info(this.getClass().getName() + " ---> " + " IMPLEMENTATION: ERICSSON APPLINK 4.0") ;
            implementation = new EricssonAppLink(theProps) ;
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

    public void TDSDataReceived(String dev, String code, String data) {
        log.info(this.getClass().getName() + " TELEPHONE DATA SERVICE (dev/code/data): " + dev + " / " + code + " / " + data) ;
    }

    public void TDSDataReceived(String dev, String code) {
        log.info(this.getClass().getName() + " TELEPHONE DATA SERVICE (dev/code): " + dev + " / " + code ) ;
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

}
