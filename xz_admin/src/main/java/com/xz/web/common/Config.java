package com.xz.web.common;

public class Config {
	
	private String testPath;
	private String imageServer;
	private int alarmValue;
	private String compRedisChannelInput;
	private String compRedisChannelOutput;
	private int compTimeout;
	private String staticRedisChannelInput;
	private String staticRedisChannelOutput;
	private int staticTimeout;
	private String captureRedisChannelInput;
	private String captureRedisChannelOutput;
	private int captureTimeout;
	private String deviceKeyImagesInput;
	private String deviceKeyImagesOutput;
	private String imagePath;

	public String getTestPath() {
		return testPath;
	}

	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}

	public String getImageServer() {
		return imageServer;
	}

	public void setImageServer(String imageServer) {
		this.imageServer = imageServer;
	}

	public int getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(int alarmValue) {
		this.alarmValue = alarmValue;
	}

	public int getCompTimeout() {
		return compTimeout;
	}

	public void setCompTimeout(int compTimeout) {
		this.compTimeout = compTimeout;
	}

	public String getCompRedisChannelInput() {
		return compRedisChannelInput;
	}

	public void setCompRedisChannelInput(String compRedisChannelInput) {
		this.compRedisChannelInput = compRedisChannelInput;
	}

	public String getCompRedisChannelOutput() {
		return compRedisChannelOutput;
	}

	public void setCompRedisChannelOutput(String compRedisChannelOutput) {
		this.compRedisChannelOutput = compRedisChannelOutput;
	}

	public String getStaticRedisChannelInput() {
		return staticRedisChannelInput;
	}

	public void setStaticRedisChannelInput(String staticRedisChannelInput) {
		this.staticRedisChannelInput = staticRedisChannelInput;
	}

	public String getStaticRedisChannelOutput() {
		return staticRedisChannelOutput;
	}

	public void setStaticRedisChannelOutput(String staticRedisChannelOutput) {
		this.staticRedisChannelOutput = staticRedisChannelOutput;
	}

	public int getStaticTimeout() {
		return staticTimeout;
	}

	public void setStaticTimeout(int staticTimeout) {
		this.staticTimeout = staticTimeout;
	}

	public String getDeviceKeyImagesInput() {
		return deviceKeyImagesInput;
	}

	public void setDeviceKeyImagesInput(String deviceKeyImagesInput) {
		this.deviceKeyImagesInput = deviceKeyImagesInput;
	}

	public String getDeviceKeyImagesOutput() {
		return deviceKeyImagesOutput;
	}

	public void setDeviceKeyImagesOutput(String deviceKeyImagesOutput) {
		this.deviceKeyImagesOutput = deviceKeyImagesOutput;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCaptureRedisChannelInput() {
		return captureRedisChannelInput;
	}

	public void setCaptureRedisChannelInput(String captureRedisChannelInput) {
		this.captureRedisChannelInput = captureRedisChannelInput;
	}

	public String getCaptureRedisChannelOutput() {
		return captureRedisChannelOutput;
	}

	public void setCaptureRedisChannelOutput(String captureRedisChannelOutput) {
		this.captureRedisChannelOutput = captureRedisChannelOutput;
	}

	public int getCaptureTimeout() {
		return captureTimeout;
	}

	public void setCaptureTimeout(int captureTimeout) {
		this.captureTimeout = captureTimeout;
	}
	
}
