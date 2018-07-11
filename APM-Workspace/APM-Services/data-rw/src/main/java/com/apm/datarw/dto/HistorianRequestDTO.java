package com.apm.datarw.dto;

import java.util.List;

public class HistorianRequestDTO {
	
	private long analytic_id;
	private long start_date;
	private long end_date;
	private List<String> input_tags;
	private long data_interval;
	public long getAnalytic_id() {
		return analytic_id;
	}
	public void setAnalytic_id(long analytic_id) {
		this.analytic_id = analytic_id;
	}
	public long getStart_date() {
		return start_date;
	}
	public void setStart_date(long start_date) {
		this.start_date = start_date;
	}
	public long getEnd_date() {
		return end_date;
	}
	public void setEnd_date(long end_date) {
		this.end_date = end_date;
	}
	public List<String> getInput_tags() {
		return input_tags;
	}
	public void setInput_tags(List<String> input_tags) {
		this.input_tags = input_tags;
	}
	public long getData_interval() {
		return data_interval;
	}
	public void setData_interval(long data_interval) {
		this.data_interval = data_interval;
	}


}
