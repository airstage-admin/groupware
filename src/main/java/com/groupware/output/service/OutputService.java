package com.groupware.output.service;

import com.groupware.output.form.OutputForm;

public interface OutputService {
	String createStudyExcel(OutputForm form);
	String createInternalProjectExcel(OutputForm form);
}