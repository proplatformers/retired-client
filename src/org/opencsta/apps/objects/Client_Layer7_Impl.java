/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.apps.objects;

/**
 * 
 * @author chrismylonas
 */
public interface Client_Layer7_Impl {

	/**
	 * @param curInStr
	 * @return
	 */
	public boolean WorkString(StringBuffer curInStr);

	/**
	 * @param sb
	 * @param et
	 */
	public void ParseTDSdata(StringBuffer sb, boolean et);

	/**
	 * @param curInStr
	 */
	public void CSTAEventReceived(StringBuffer curInStr);
}
