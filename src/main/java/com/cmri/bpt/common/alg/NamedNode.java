package com.cmri.bpt.common.alg;

public class NamedNode {
	private String name;

	public NamedNode(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object chkObj) {
		if (this == chkObj) {
			return true;
		}
		if (chkObj == null || chkObj.getClass() != this.getClass()) {
			return false;
		}
		NamedNode another = (NamedNode) chkObj;
		if (this.name == null) {
			if (another.name == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return this.name.equals(another.name);
		}
	}

}
