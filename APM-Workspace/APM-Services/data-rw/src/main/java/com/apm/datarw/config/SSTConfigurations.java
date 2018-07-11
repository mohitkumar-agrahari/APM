package com.apm.datarw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
@Component
@EnableAutoConfiguration
public class SSTConfigurations {
	@Value("${sst.enable.debugging:false}")
	private boolean debugging;
	
	@Value("${spring.profiles.active:server}")
	private String springProfile;
	
	@Value("${sst.historian.dll.path}")
	private String dllPath;
	
	@Value("${sst.historian.server.address}")
	private String serverAddress;
	
	@Value("${sst.historian.server.username}")
	private String serverUsername;
	
	@Value("${sst.historian.server.password}")
	private String serverPassword;
	
	@Value("${sst.python.runner.add_analytic}")
	private String addAnalyticUrl;
	
	@Value("${sst.python.runner.async_run}")
	private String asyncRunUrl;

	public boolean isDebugging() {
		return debugging;
	}

	public void setDebugging(boolean debugging) {
		this.debugging = debugging;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getServerUsername() {
		return serverUsername;
	}

	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getAddAnalyticUrl() {
		return addAnalyticUrl;
	}

	public void setAddAnalyticUrl(String addAnalyticUrl) {
		this.addAnalyticUrl = addAnalyticUrl;
	}

	public String getAsyncRunUrl() {
		return asyncRunUrl;
	}

	public void setAsyncRunUrl(String asyncRunUrl) {
		this.asyncRunUrl = asyncRunUrl;
	}

	public String getSpringProfile() {
		return springProfile;
	}

	public void setSpringProfile(String springProfile) {
		this.springProfile = springProfile;
	}

	public String getDllPath() {
		return dllPath;
	}

	public void setDllPath(String dllPath) {
		this.dllPath = dllPath;
	}
	
	@Override
	public String toString() {
		return "SSTConfigurations [debugging=" + debugging + ", springProfile=" + springProfile + ", dllPath=" + dllPath
				+ ", maxQueryTags=" + 5 + ", serverAddress=" + serverAddress + ", serverUsername="
				+ serverUsername + ", serverPassword=" + serverPassword + ", addAnalyticUrl=" + addAnalyticUrl
				+ ", asyncRunUrl=" + asyncRunUrl + "]";
	}
	
	
}
