/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client.asterisk;

import java.util.Properties;
import org.opencsta.apps.objects.CSTAApplication;
import org.opencsta.client.CSTAClientBase;
import org.opencsta.client.CSTAFunctions;

/**
 *
 * @author cm
 */
public class AsteriskCSTAClient extends CSTAClientBase implements CSTAFunctions{


    public AsteriskCSTAClient(Properties _props){
        super(_props) ;

    }

    @Override
    public void SetDisplay(String device1, String text, boolean beep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code, String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSDataReceived(String dev, String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void RegisterParentApplication(CSTAApplication parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void TDSEnable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
