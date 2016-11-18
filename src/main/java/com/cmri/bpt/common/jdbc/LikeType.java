package com.cmri.bpt.common.jdbc;

/**
 * 
 * @author Hu Changwei
 * @version 1.0
 */
public enum LikeType {
	Left, Right, Center;
	// 默认like 类型
	public static final LikeType Default = Center;
}