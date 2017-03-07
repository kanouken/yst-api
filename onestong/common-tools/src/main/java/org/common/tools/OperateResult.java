package org.common.tools;

import java.util.HashMap;
import java.util.Map;

import org.common.tools.db.Page;

public class OperateResult<T> {

	private String innerException;

	public String getInnerException() {
		return innerException;
	}

	public static Map<String, Object> renderPage(Page page, Object object) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("page", page);
		reqMap.put("objects", object);
		return reqMap;
	}

	public Boolean success() {
		boolean flag = true;
		if (this.getData() == null && !this.getStatusCode().equalsIgnoreCase("200")) {
			flag = false;
		}
		return flag;
	}

	public OperateResult(T data) {
		this.statusCode = "200";
		this.innerException = "";
		this.description = "";
		this.data = data;
	}

	public void setInnerException(String innerException) {
		this.innerException = innerException;
	}

	public OperateResult(String innerException, String description, T data) {
		super();
		this.innerException = innerException;
		this.description = description;
		this.data = data;
	}
	
	public OperateResult(String innerException, String description, T data,String statusCode) {
		super();
		this.innerException = innerException;
		this.description = description;
		this.data = data;
		this.statusCode = statusCode;
	}

	public OperateResult() {
		super();
	}

	public static OperateResult renderSuccess(Object data) {
		OperateResult op = new OperateResult();
		op.setStatusCode("200");
		op.setData(data);
		return op;
	}

	private String statusCode;
	private String description;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static OperateResult getVersionErrorOp() {

		OperateResult opForVersion = new OperateResult();
		opForVersion.setData(null);
		opForVersion.setDescription("不支持请求的版本!");
		return opForVersion;
	}

	public static OperateResult getParametersNotCompleteErrorOp(String descript) {

		OperateResult opForVersion = new OperateResult();
		opForVersion.setData(null);
		opForVersion.setDescription(descript);
		return opForVersion;
	}

}
