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

package org.opencsta.apps.objects;

/**
 * This class is a simple software representation of a real world extension.
 * 
 * @author chrismylonas
 */
public class Extension {
	/**
	 * Extension number represented as a String object.
	 */
	private String id_str;

	/**
	 * Extension number represented as an int.
	 */
	private int id;

	/**
	 * The string that is currently on the display. If null then display on
	 * handset is default - usually time and date.
	 */
	protected String display;

	/**
	 * Represents the extension is on hook - not busy. This is always opposite
	 * to offhook.
	 */
	protected boolean onhook;

	/**
	 * Represents the extension is off hook - busy. This is always opposite to
	 * onhook.
	 */
	protected boolean offhook;

	/**
	 * Represents whether the extension is displaying a message other than the
	 * default message which is blank or the date and time.
	 */
	protected boolean isDisplaying;

	/**
	 * Represents that the extension is on a call that is currently active.
	 */
	protected boolean onActiveCall;

	/**
	 * Represents that the extension is on a call that is currently passive.
	 * This call state occurs when a call is on hold at the extension. The
	 * extension can still be alerted as if it were not on any call at all.
	 */
	protected boolean onPassiveCall;

	/**
	 * Represents that the extension is currently alerting.
	 */
	protected boolean alerting;

	/** Creates a new instance of Extension */
	public Extension() {
		id_str = null;
		id = 0;
		onhook = true;
		offhook = !onhook;
		display = null;
		isDisplaying = false;
		onActiveCall = false;
		onPassiveCall = false;
		alerting = false;
	}

	/**
	 * @param extNumber
	 */
	public Extension(String extNumber) {
		id_str = extNumber;
		id = Integer.parseInt(extNumber);
		onhook = true;
		offhook = !onhook;
		display = null;
		isDisplaying = false;
		onActiveCall = false;
		onPassiveCall = false;
		alerting = false;
	}

	/**
	 * @param extNumber
	 */
	public Extension(int extNumber) {
		id_str = toString();
		id = extNumber;
		onhook = true;
		offhook = !onhook;
		display = null;
		isDisplaying = false;
		onActiveCall = false;
		onPassiveCall = false;
		alerting = false;
	}

	/**
	 * @param bool
	 */
	public void set_onhook(boolean bool) {
		onhook = bool;
		offhook = !onhook;
	}

	/**
	 * @param bool
	 */
	public void set_offhook(boolean bool) {
		offhook = bool;
		onhook = !offhook;
	}

	/**
	 * @param str
	 */
	public void set_display(String str) {
		display = str;
		set_isDisplaying(true);
		isDisplaying = get_isDisplaying();
	}

	/**
	 * @param bool
	 */
	public void set_isDisplaying(boolean bool) {
		isDisplaying = bool;
	}

	/**
	 * @param bool
	 */
	public void set_onActiveCall(boolean bool) {
		onActiveCall = bool;
	}

	/**
	 * @param bool
	 */
	public void set_onPassiveCall(boolean bool) {
		onPassiveCall = bool;
	}

	/**
	 * @param bool
	 */
	public void set_alerting(boolean bool) {
		alerting = bool;
	}

	/**
	 * @return
	 */
	public boolean get_onhook() {
		return onhook;
	}

	/**
	 * @return
	 */
	public boolean get_offhook() {
		return offhook;
	}

	/**
	 * @return
	 */
	public String get_display() {
		return display;
	}

	/**
	 * @return
	 */
	public boolean get_isDisplaying() {
		return isDisplaying;
	}

	/**
	 * @return
	 */
	public boolean get_onActiveCall() {
		return onActiveCall;
	}

	/**
	 * @return
	 */
	public boolean get_onPassiveCall() {
		return onPassiveCall;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = "\nExtension: " + id;
		str += "\nIs displaying: " + isDisplaying;
		str += "\nDISPLAY: " + display;
		str += "\nOff hook: " + offhook;
		str += "\nAlerting: " + alerting;
		str += "\nOn Active Call: " + onActiveCall;
		str += "\nOn Passive Call: " + onPassiveCall;
		return str;
	}

}
