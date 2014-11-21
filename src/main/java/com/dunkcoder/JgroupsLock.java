package com.dunkcoder;

import org.jgroups.JChannel;
import org.jgroups.blocks.locking.LockService;

public class JgroupsLock implements Lock {

	private JChannel channel;
	private LockService lockService;

	public JgroupsLock() {
		try {
			channel = new JChannel("udp-lock-stack.xml");
			lockService = new LockService(channel);
			channel.connect("LockCluster");
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void close() {
		lockService.unlockAll();
		channel.close();
	}

	public void lock(String name) {
		lockService.getLock(name).lock();
	}

	public void release(String name) {
		lockService.getLock(name).unlock();
	}
}