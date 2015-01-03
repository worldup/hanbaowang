package com.upbest.pageModel;

/**
 * 
 * JSON模型
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * 
 */
public class Json implements java.io.Serializable {

	private boolean success = false;

	private String msg = "";

	private Object obj = null;
	
	//private Object signobj = null;
/*	public Object getSignobj() {
		return signobj;
	}

	public void setSignobj(Object signobj) {
		this.signobj = signobj;
	}
*/
//	private Boolean iscache = null;

	/*public Boolean getIscache() {
		return iscache;
	}

	public void setIscache(Boolean iscache) {
		this.iscache = iscache;
	}*/

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}
 
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
