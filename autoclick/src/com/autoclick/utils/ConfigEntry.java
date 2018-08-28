package com.autoclick.utils;

public class ConfigEntry {
	private char key = 'o';
	private boolean controlKey;
	private boolean shiftKey;
	private Integer delay = 0;
	private Integer mouseButton = 0;

	public char getKey() {
		return this.key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public boolean isControlKey() {
		return this.controlKey;
	}

	public void setControlKey(boolean controlKey) {
		this.controlKey = controlKey;
	}

	public boolean isShiftKey() {
		return this.shiftKey;
	}

	public void setShiftKey(boolean shiftKey) {
		this.shiftKey = shiftKey;
	}

	public Integer getDelay() {
		return this.delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getMouseButton() {
		return this.mouseButton;
	}

	public void setMouseButton(Integer mouseButton) {
		this.mouseButton = mouseButton;
	}
}