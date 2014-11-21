package com.dunkcoder;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LockWindow extends JFrame {

	private final static Icon LOCKED_ICON;
	private final static Icon UNLOCKED_ICON;

	static {
		try {
			ClassLoader cl = LockWindow.class.getClassLoader();
			LOCKED_ICON = new ImageIcon(ImageIO.read(cl.getResource("locked.png")));
			UNLOCKED_ICON = new ImageIcon(ImageIO.read(cl.getResource("unlocked.png")));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private final Lock lock;
	private final String lockName;

	public LockWindow(Lock lock, String lockName) {
		super("Click the icon to see distributed lock");
		this.lock = lock;
		this.lockName = lockName;
		Action toggleAction = new ToggleLockAction();
		JButton toggleButton = new JButton(toggleAction);
		add(toggleButton);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private class ToggleLockAction extends AbstractAction {
		private boolean hasLock = false;

		private ToggleLockAction() {
			putValue(Action.LARGE_ICON_KEY, UNLOCKED_ICON);
		}

		public void actionPerformed(ActionEvent e) {
			if (hasLock) {
				lock.release(lockName);
				hasLock = false;
				putValue(Action.LARGE_ICON_KEY, UNLOCKED_ICON);
			} else {
				lock.lock(lockName);
				hasLock = true;
				putValue(Action.LARGE_ICON_KEY, LOCKED_ICON);
			}
		}
	}
}