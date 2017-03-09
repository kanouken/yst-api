package ost.file.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	/**
	 * 
	 */
	/**
	 * 400 重写spring 对于 required 参数 未传递 时的异常
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(ex.getParameterName() + "不能为null", HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new OperateResult(ex.getMessage(), ex.getMessage(), null), HttpStatus.OK);
	}

	/**
	 * 402 为保持代码健壮 抛出的 业务异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler({ ApiException.class })
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		ApiException ae = (ApiException) ex;
		return new ResponseEntity<>(new OperateResult(ae.getInnerException(), ae.getMessage(), null), HttpStatus.OK);
	}
	
	/**
	 * 402 为保持代码健壮 抛出的 业务异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler({ IllegalAccessException.class })
	@ResponseBody
	ResponseEntity<?> handler403(HttpServletRequest request, Throwable ex) {
		return new ResponseEntity<>(new OperateResult("", "禁止访问", null), HttpStatus.OK);
	}


	/**
	 * 5xx 错误 数据库、代码异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler
	@ResponseBody
	ResponseEntity<?> handleServerError(HttpServletRequest request, Throwable ex)
			throws NoSuchFieldException, SecurityException {
		StringWriter writer = new StringWriter();
		PrintWriter pWriter = new PrintWriter(writer);
		ex.printStackTrace(pWriter);
		ex.printStackTrace();
		logger.error(writer.getBuffer().toString());
		return new ResponseEntity<>(new OperateResult(writer.getBuffer().toString(), "服务器异常", null), HttpStatus.OK);
	}

}