/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.client;

import java.net.Socket;
import org.opencsta.apps.objects.CSTAApplication;

/**
 * 
 * @author chrismylonas
 */
public interface CSTA_Implementation_Functions {

	/**
     * 
     */
	public void Quit();

	public void ServerStatus();

	/**
	 * The parent application calls this method on the CSTA functionality object
	 * (client) and passes itself.
	 * 
	 * 
	 * @param parent
	 *            The parent application - an implementation of the
	 *            CSTAClientApplication interface
	 */
	public void RegisterParentApplication(CSTAApplication parent);

	/**
	 * @return
	 */
	public Socket getSocket(); // useful for knowing when a csta client
								// connection is established to the server

	/**
     * 
     */
	public void release();
}
