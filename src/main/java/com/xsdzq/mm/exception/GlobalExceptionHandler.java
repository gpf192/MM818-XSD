package com.xsdzq.mm.exception;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xsdzq.mm.util.GsonUtil;


@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());

	@ExceptionHandler(Exception.class)
	@ResponseBody
	Map<String, Object> handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return GsonUtil.buildMap(1, e.getMessage(), null);
	}

	/**
	 * 处理所有业务异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	Map<String, Object> handleBusinessException(BusinessException e) {
		logger.error(e.getMessage(), e);
		return GsonUtil.buildMap(1, e.getMessage(), null);
	}

	/**
	 * 处理所有接口数据验证异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error(e.getMessage(), e);
		return GsonUtil.buildMap(1, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
	}

	/**
	 * 处理级联异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	Map<String, Object> handleConstraintViolationException(ConstraintViolationException e) {
		logger.error(e.getMessage(), e);
		String message = "ConstraintViolationException";
		return GsonUtil.buildMap(2, message, null);
	}

	/**
	 * 处理级联异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	Map<String, Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.error(e.getMessage(), e);
		String message = "ConstraintViolationException";
		return GsonUtil.buildMap(2, message, null);
	}

}
