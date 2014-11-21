package com.dunkcoder;

public interface Lock {
	void lock(String name);

	void release(String name);
}