package com.upbest.mvc.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.upbest.mvc.handler.JsonDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamInfoVO {
	
	private Integer id;
	//考卷名称
	private String examName;
	//考卷类型
	private int examType;
	//考卷类型描述
	private String examTypeName;
	//考卷时长
	private int examTimer;
	//及格分数
	private int passValue;
	//总分
	private int totalValue;
	//考卷创建时间
	private Date createTime;
	private String heads;
	private String description;
	
	private List<QuestionVO> questionList;
	private Map<String, List<QuestionVO>> questionMap;
	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public int getExamType() {
		return examType;
	}

	public void setExamType(int examType) {
		this.examType = examType;
	}

	public int getExamTimer() {
		return examTimer;
	}

	public void setExamTimer(int examTimer) {
		this.examTimer = examTimer;
	}
	
	public int getPassValue() {
		return passValue;
	}

	public void setPassValue(int passValue) {
		this.passValue = passValue;
	}

	public int getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public List<QuestionVO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionVO> questionList) {
		this.questionList = questionList;
	}

	public String getExamTypeName() {
		return examTypeName;
	}

	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, List<QuestionVO>> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<String, List<QuestionVO>> questionMap) {
		this.questionMap = questionMap;
	}

	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
