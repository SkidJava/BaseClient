package client.setting;

import java.util.ArrayList;

import client.module.Module;

public class Setting {

	private Module module;

	private String name, currentOption;

	private double cValue, minValue, maxValue;

	private boolean onlyInt, booleanValue;

	private ArrayList<String> options;

	private SettingMode mode;

	public enum SettingMode {
		BOOLEAN, DIGIT, MODES
	}

	public Setting(Module m, String n, boolean b) {
		this.module = m;
		this.name = n;
		this.booleanValue = b;
		this.mode = SettingMode.BOOLEAN;
	}

	public Setting(Module m, String n, double cValue, double minValue, double maxValue, boolean onlyInt) {
		this.module = m;
		this.name = n;
		this.cValue = cValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.onlyInt = onlyInt;
		this.mode = SettingMode.DIGIT;
	}

	public Setting(Module m, String n, String currentOption, ArrayList<String> options) {
		this.module = m;
		this.name = n;
		this.currentOption = currentOption;
		this.options = options;
		this.mode = SettingMode.MODES;
	}

	public boolean isDigit() {
		return this.mode.equals(SettingMode.DIGIT);
	}

	public boolean isBoolean() {
		return this.mode.equals(SettingMode.BOOLEAN);
	}

	public boolean isModes() {
		return this.mode.equals(SettingMode.MODES);
	}

	public Module getModule() {
		return module;
	}

	public String getName() {
		return name;
	}

	public String getCurrentOption() {
		return currentOption;
	}

	public void setCurrentOption(String currentOption) {
		this.currentOption = currentOption;
	}

	public double getCurrentValue() {
		return cValue;
	}

	public int getCurrentOptionIndex() {
		int index = 0;
		for (String s : options) {
			if (s.equalsIgnoreCase(currentOption)) {
				return index;
			}
			index++;
		}
		return index;
	}

	public void setCurrentValue(double cValue) {
		this.cValue = cValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public boolean isOnlyInt() {
		return onlyInt;
	}

	public void setOnlyInt(boolean onlyInt) {
		this.onlyInt = onlyInt;
	}

	public boolean getBooleanValue() {
		return booleanValue && module.isToggled();
	}

	public boolean getRawBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

}
