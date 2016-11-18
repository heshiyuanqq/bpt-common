package com.cmri.bpt.common.base;

public interface Converter<TSrc, TDest> {
	TDest convert(TSrc src, int index);
}
