/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.apps.objects;

/**
 *
 * @author cm
 */
public interface Client_Layer7_Impl {
    public boolean WorkString(StringBuffer curInStr) ;
    public void ParseTDSdata(StringBuffer sb, boolean et) ;
    public void CSTAEventReceived(StringBuffer curInStr) ;
}
