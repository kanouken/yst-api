package org.common.tools;

public class OperateResult {

	private String innerException;

	public String getInnerException() {
		return innerException;
	}

	public void setInnerException(String innerException) {
		this.innerException = innerException;
	}

	public OperateResult(String innerException, String description, Object data) {
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

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
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
