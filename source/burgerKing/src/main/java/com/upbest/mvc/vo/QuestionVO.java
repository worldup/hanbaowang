package com.upbest.mvc.vo;

import com.upbest.mvc.entity.BQuestion;

public class QuestionVO extends BQuestion {
	private String modelName;
	
	private String questionType;
	
	private String moduleId;
	
	

	public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	
	
	
}
