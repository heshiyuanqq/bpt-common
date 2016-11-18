package com.cmri.bpt.common.base;

public interface TargetJudger<T> {
	boolean isTarget(T toBeChecked);
}
