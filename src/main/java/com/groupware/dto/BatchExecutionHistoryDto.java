package com.groupware.dto;

/**
* BatchExecutionHistoryDto
* batch_execution_historyテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/
public class BatchExecutionHistoryDto {
	private int id;
	private String batch_name;
	private String execution_date;
	private boolean result;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getExecution_date() {
		return execution_date;
	}
	public void setExecution_date(String execution_date) {
		this.execution_date = execution_date;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
}
