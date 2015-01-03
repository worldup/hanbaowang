package com.upbest.mvc.constant;

import org.apache.commons.lang.StringUtils;

public class Constant {
	public static class Code{
		public static int SUCCESS_CODE = 2000;
		public static int TYPE_CONVERT_ERROR_CODE = 1002;
		public static int ILLEGAL_CODE = 1001;
		public static int NULL_CODE = 1007;
		public static int EXIST_SAME_TASK_CODE = 1009;
		
	}
	
	public static class OperatorResultMsg{
		public static final String SUCCESS = "操作成功";
		public static final String ERROR = "操作失败";
		public static String EXIST_SAME_TASK_MSG = "已存在相同类型的任务";
	}
	
	public enum MessageType{
		Notice(1,"公告"),Emergency(2,"紧急"),Task(3,"任务"),Exception(4,"异常信息"),Unknow(5,"未知");
		
		private String aliase;
		private int value;
		private MessageType(int value,String aliase){
			this.aliase = aliase;
			this.value = value;
		}
		
		public String getAliase() {
			return aliase;
		}
		
		public int getValue() {
			return value;
		}

		/**
		 * 
		 * @param 
		 * @return(公告)
		 */
		public static MessageType getMessageType(int val){
			MessageType[] types = values();
			for (MessageType type : types) {
				if(type.getValue() == val){
					return type;
				}
			}
			return Unknow;
		}
	}
	
	public enum QuestionType{
		SingleSelect(1,"单选题"),Judge(2,"判断题"),MultiSelect(3,"多选题"),
		ShortQuestion(4,"简答题"),Other(5,"其它");
		
		
		private String name;
		private int value;
		private QuestionType(int value,String name){
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		/**
		 * 
		 * @param 
		 * @return(公告)
		 */
		public static String getName(int val){
			QuestionType[] types = values();
			for (QuestionType type : types) {
				if(type.getValue() == val){
					return type.getName();
				}
			}
			return "";
		}
		
		//add by xubin 2014-9-28  试题类型搜索
		public static int getValue(String name){
		    QuestionType[] types = values();
		    for (QuestionType type : types) {
		        if(type.getName().contains(name)){
		            return type.getValue();
		        }
		    }
		    return 0;
		}
	}
	
	public enum HeadType{
		Fill(1,"填空题"),Judge(2,"判断题"),Select(2,"选择题"),ShortAnswer(3,"简答题"),
		Date(4,"日期"),Drop(5,"下拉"),Audit(6,"评估人"),Top(7,"置顶"),
		LastScore(8,"上次稽核成绩"),Score(9,"成绩"),DuplicateNum(10,"重复扣分项编号"),
		Time(11,"时间"),Area(12,"地区");
		
		
		private String name;
		private int value;
		private HeadType(int value,String name){
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		/**
		 * 
		 * @param 
		 * @return(公告)
		 */
		public static String getName(int val){
			HeadType[] types = values();
			for (HeadType type : types) {
				if(type.getValue() == val){
					return type.getName();
				}
			}
			return "";
		}
		
	}
	
	public enum TaskState{
		NotComplete(0,"未完成"),Complete(1,"已完成");
		
		private String name;
		private int value;
		private TaskState(int value,String name){
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		/**
		 * 
		 * @param 
		 * @return(公告)
		 */
		public static String getName(int val){
			TaskState[] types = values();
			for (TaskState type : types) {
				if(type.getValue() == val){
					return type.getName();
				}
			}
			return "";
		}
	}
	
	/**
	 * 门店状态
	 * @author QunZheng
	 *
	 */
	public enum ShopState{
		Open("Opened","开业"),Close("close","关闭"),Building(null,"筹建中"),
		TempClose("temp closure","临时关店");
		
		/**
		 * 英文描述
		 */
		private String enName;
		/**
		 * 中文描述
		 */
		private String chName;
		
		public String getEnName() {
			return enName;
		}

		public String getChName() {
			return chName;
		}

		private ShopState(String enName,String chName){
			this.enName = enName;
			this.chName = chName;
		}
		
		/**
		 * 
		 * @param 
		 * @return
		 */
		public static String getChName(String enName){
			if(enName == null){
				return Building.chName;
			}
			
			ShopState[] status = values();
			for (ShopState statu : status) {
				if(statu.enName.equals(enName)){
					return statu.chName;
				}
			}
			return "";
		}
	}
	
//	甜品站，24小时，BK咖啡,汽车外卖，外送服务
	/**
	 * 门店状态
	 * @author QunZheng
	 *
	 */
	public enum BrandExtension{
		Dessert(1,"甜品站"),Hour_24(2,"24小时"),Coffee(3,"BK咖啡"),
		AutomotiveTakeaway(4,"汽车外卖"),DeliveryService(5,"外送服务");
		
		/**
		 * 英文描述
		 */
		private int value;
		/**
		 * 中文描述
		 */
		private String name;
		
		private BrandExtension(int value,String name){
			this.value = value;
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}



		/**
		 * 
		 * @param 
		 * @return
		 */
		public static String getName(int value){
			
			BrandExtension[] brands = values();
			for (BrandExtension brand : brands) {
				if(brand.value == value){
					return brand.name;
				}
			}
			return "";
		}
		
		public static String getName(String values){
			StringBuilder sb = new StringBuilder();
			if(!StringUtils.isEmpty(values)){
				String[] valAry = values.split(",");
				for (String val : valAry) {
					sb.append(getName(Integer.parseInt(val)));
				}
			}
			return sb.toString();
		}
	}
	
	/*public enum WorkType{
		WEEK(1,"每周"),MONTH(2,"月度"),Task(3,"任务"),Exception(4,"异常信息"),Unknow(5,"未知");
		
		private String aliase;
		private int value;
		private MessageType(int value,String aliase){
			this.aliase = aliase;
			this.value = value;
		}
		
		public String getAliase() {
			return aliase;
		}
		
		public int getValue() {
			return value;
		}

		*//**
		 * 
		 * @param 
		 * @return(公告)
		 *//*
		public static MessageType getMessageType(int val){
			MessageType[] types = values();
			for (MessageType type : types) {
				if(type.getValue() == val){
					return type;
				}
			}
			return Unknow;
		}
	}*/
}
