package com.autoclick.utils;

import java.awt.Robot;

import gui.Autoclick;

public class RobotClick implements Runnable {
	private int mouseMask;
	private final int delay;

	public RobotClick(int mouseKey, int delay) {
		this.delay = delay;
		switch (mouseKey) {
			case 0 :
				this.mouseMask = 4096;
			default :
		}
	}

	public void run() {
		Robot robot = null;

		try {
			robot = new Robot();
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		while (Autoclick.getStatus().get()) {
			robot.mousePress(this.mouseMask);
			robot.mouseRelease(this.mouseMask);

			try {
				Thread.currentThread();
				Thread.sleep((long) this.delay);
			} catch (InterruptedException var3) {
				var3.printStackTrace();
			}
		}

	}
}