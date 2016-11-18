package com.cmri.bpt.common.entity;

import java.io.Serializable;


public class LogAnylizeInfoVO  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String caselogname;

	public String getCaselogname() {
		return caselogname;
	}
	public void setCaselogname(String caselogname) {
		this.caselogname = caselogname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


}
