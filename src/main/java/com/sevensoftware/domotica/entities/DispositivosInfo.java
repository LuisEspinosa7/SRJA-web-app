/**
 * 
 */
package com.sevensoftware.domotica.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LUIS
 *
 */
public class DispositivosInfo {
	
	private String message;
	private List<DispositivoItem> dispositivosItem = new ArrayList<DispositivoItem>();
	
	
	public DispositivosInfo(String message, List<DispositivoItem> dispositivosItem) {
		super();
		this.message = message;
		this.dispositivosItem = dispositivosItem;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public List<DispositivoItem> getDispositivosItem() {
		return dispositivosItem;
	}


	public void setDispositivosItem(List<DispositivoItem> dispositivosItem) {
		this.dispositivosItem = dispositivosItem;
	}

}
