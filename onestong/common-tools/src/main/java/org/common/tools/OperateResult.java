package org.common.tools;

public class OperateResult<T> {

	private String innerException;

	public String getInnerException() {
		return innerException;
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
