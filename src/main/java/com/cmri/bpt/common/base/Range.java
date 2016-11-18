package com.cmri.bpt.common.base;

public class Range<T> {
	private T from;
	private T to;

	public Range() {
		//
	}

	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public static <T> Range<T> newOne() {
		return new Range<T>();
	}

	public static <T> Range<T> newOne(T from, T to) {
		return new Range<T>(from, to);
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

}
