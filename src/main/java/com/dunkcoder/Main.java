package com.dunkcoder;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	private final JgroupsLock lockManager;
	private LockWindow lockWindow;

	public Main() {
		lockManager = new JgroupsLock();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					lockWindow = new LockWindow(lockManager, "LockName");
					lockWindow.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							lockManager.close();
						}
					});
				}
			});
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}
}