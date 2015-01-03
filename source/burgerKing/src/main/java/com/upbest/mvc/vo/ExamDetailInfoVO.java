package com.upbest.mvc.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 问卷详细结构信息
 * 
 * @author QunZheng
 *
 */
public class ExamDetailInfoVO {
	// 问卷基本信息
	private BasicInfo basicInfo;
	// 问卷头信息
	private List<Field> heads;
	// 问卷模块信息
	private List<Module> modules;
	// 问卷额外模块信息
	private List<AdditionalModule> additionalModules;

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public List<Field> getHeads() {
		return heads;
	}

	public void setHeads(List<Field> heads) {
		this.heads = heads;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<AdditionalModule> getAdditionalModules() {
		return additionalModules;
	}

	public void setAdditionalModules(List<AdditionalModule> additionalModules) {
		this.additionalModules = additionalModules;
	}

	public static class BasicInfo {
		// 考卷名称
		private String examName;
		// 考卷类型
		private int examType;
		// 考卷类型描述
		private String examTypeName;
		// 考卷时长
		private int examTimer;
		// 及格分数
		private int passValue;
		// 总分
		private int totalValue;
		// 考卷创建时间
		private Date createTime;
		//标识成绩的(红绿灯)
		private Integer level;

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

		public String getExamTypeName() {
			return examTypeName;
		}

		public void setExamTypeName(String examTypeName) {
			this.examTypeName = examTypeName;
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

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		
	}

	public static class Field {
		private int id;
		private String fieldName;
		private String fieldValue;
		// 字段类型
		private int fieldType;
		// 字段的类型中文描述
		private String fieldTypeDes;

		public Field() {
		}

		public Field(String fieldName) {
			super();
			this.fieldName = fieldName;
		}

		public Field(int id, String fieldName) {
			super();
			this.id = id;
			this.fieldName = fieldName;
		}

		public Field(int id, String fieldName, String fieldValue) {
			super();
			this.id = id;
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
		}
		
		public Field(String fieldName, String fieldValue) {
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
		}

		public Field(int id, String fieldName, String fieldValue,
				int fieldType, String fieldTypeDes) {
			super();
			this.id = id;
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
			this.fieldType = fieldType;
			this.fieldTypeDes = fieldTypeDes;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldValue() {
			return fieldValue;
		}

		public void setFieldValue(String fieldValue) {
			this.fieldValue = fieldValue;
		}

		public int getFieldType() {
			return fieldType;
		}

		public void setFieldType(int fieldType) {
			this.fieldType = fieldType;
		}

		public String getFieldTypeDes() {
			return fieldTypeDes;
		}

		public void setFieldTypeDes(String fieldTypeDes) {
			this.fieldTypeDes = fieldTypeDes;
		}

	}

	public static class Module {
		// 模块ID
		protected int moduleId;
		// 模块名称
		protected String name;
		// 模块描述
		protected String desc;
		// 模块下关联的字段
		protected Set<Field> fields;

		// 模块关联的问题
		protected List<Question> questions;

		public Module() {
		}

		public Module(String name) {
			super();
			this.name = name;
		}

		public Module(String name, String desc) {
			this.name = name;
			this.desc = desc;
		}

		public int getModuleId() {
			return moduleId;
		}

		public void setModuleId(int moduleId) {
			this.moduleId = moduleId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public Set<Field> getFields() {
			return fields;
		}

		public void setFields(Set<Field> fields) {
			this.fields = fields;
		}

		public List<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}

		public void addQuestion(Question question) {
			if (questions == null) {
				questions = new ArrayList<ExamDetailInfoVO.Question>();
			}
			questions.add(question);
		}

		public void addField(Field field) {
			if (fields == null) {
				fields = new HashSet<Field>();
			}
			fields.add(field);
		}

		public void addField(String field) {
			addField(new Field(field));
		}

	}

	public static class Question {
		// 问题描述
		private String quesDesc;
		// 问题分值
		private int score;

		private int id;
		// 试题类型
		private int questionType;
		// 试题类型的中文描述
		private String questionTypeDesc;
		// 是否是图片上传
		private boolean uploadPic;
		private String serialNumber;
		
		private QuestionTestDetailInfo testDetailInfo;

		public Question() {
			super();
		}

		public Question(int id, String quesDesc, int score) {
			super();
			this.id = id;
			this.quesDesc = quesDesc;
			this.score = score;
		}

		public Question(int id, String quesDesc, int score, int questionType,
				String questionTypeDesc, boolean uploadPic, String serialNumber) {
			this.quesDesc = quesDesc;
			this.score = score;
			this.id = id;
			this.questionType = questionType;
			this.questionTypeDesc = questionTypeDesc;
			this.uploadPic = uploadPic;
			this.serialNumber = serialNumber;
		}
		
		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getQuesDesc() {
			return quesDesc;
		}

		public void setQuesDesc(String quesDesc) {
			this.quesDesc = quesDesc;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getQuestionType() {
			return questionType;
		}

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public String getQuestionTypeDesc() {
			return questionTypeDesc;
		}

		public void setQuestionTypeDesc(String questionTypeDesc) {
			this.questionTypeDesc = questionTypeDesc;
		}

		public boolean isUploadPic() {
			return uploadPic;
		}

		public void setUploadPic(boolean uploadPic) {
			this.uploadPic = uploadPic;
		}

		public QuestionTestDetailInfo getTestDetailInfo() {
			return testDetailInfo;
		}

		public void setTestDetailInfo(QuestionTestDetailInfo testDetailInfo) {
			this.testDetailInfo = testDetailInfo;
		}
	}

	public static class AdditionalModule extends Module {
		/*
		 * public static final int MODULE_TYPE_CASH_AUDIT = 1; public static
		 * final int MODULE_TYPE_SIDE_BY_SIDE = 2;
		 */

		private int moduleType;
		
		private List<Map<String, Object>> testResult;

		public AdditionalModule() {
			super();
		}

		public AdditionalModule(int moduleType) {
			super();
			this.moduleType = moduleType;
		}

		public AdditionalModule(String name, String desc) {
			this.name = name;
			this.desc = desc;
		}

		public AdditionalModule(String name, String desc, int moduleType) {
			this.name = name;
			this.desc = desc;
			this.moduleType = moduleType;
		}

		public int getModuleType() {
			return moduleType;
		}

		public void setModuleType(int moduleType) {
			this.moduleType = moduleType;
		}

		public List<Map<String, Object>> getTestResult() {
			return testResult;
		}

		public void setTestResult(List<Map<String, Object>> testResult) {
			this.testResult = testResult;
		}
		
	}

	public static class AdditionalModuleTestResult {
		private String name;
		private List<Map<String, Object>> testResult;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Map<String, Object>> getTestResult() {
			return testResult;
		}

		public void setTestResult(List<Map<String, Object>> testResult) {
			this.testResult = testResult;
		}

	}

	public static class ExamTestDetailInfo {
		private List<Field> heads;
		private List<QuestionTestDetailInfo> questionInfos;

		public List<Field> getHeads() {
			return heads;
		}

		public void setHeads(List<Field> heads) {
			this.heads = heads;
		}

		public List<QuestionTestDetailInfo> getQuestionInfos() {
			return questionInfos;
		}

		public void setQuestionInfos(List<QuestionTestDetailInfo> questionInfos) {
			this.questionInfos = questionInfos;
		}

	}

	/**
	 * 试题测评详细
	 * 
	 * @author QunZheng
	 *
	 */
	public static class QuestionTestDetailInfo {
		/**
		 * 试题id
		 */
		private int quesId;
		/**
		 * 试题得分情况
		 */
		private int score;
		
		private String evidence;

		/**
		 * 字段信息
		 */
		private List<Field> fieldInfos;

		public int getQuesId() {
			return quesId;
		}

		public void setQuesId(int quesId) {
			this.quesId = quesId;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public List<Field> getFieldInfos() {
			return fieldInfos;
		}

		public void setFieldInfos(List<Field> fieldInfos) {
			this.fieldInfos = fieldInfos;
		}

		public String getEvidence() {
			return evidence;
		}

		public void setEvidence(String evidence) {
			this.evidence = evidence;
		}

		public void addFieldInfo(Field field) {
			if (fieldInfos == null) {
				fieldInfos = new ArrayList<ExamDetailInfoVO.Field>();
			}
			fieldInfos.add(field);
		}

	}
}
