package com.xsdzq.mm.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.SignatureEntity;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.RandomUtil;
import com.xsdzq.mm.util.WxConfigUtil;
import com.xsdzq.mm.util.WxHttpUtil;
import com.xsdzq.mm.util.WxReturnParam;

@RestController
@RequestMapping("/activity/wxshare")
public class ShareController {
	Logger logger = LoggerFactory.getLogger(ShareController.class.getName());

	@Autowired
	TokenService tokenService;
	
	@GetMapping(value = "/getSign", produces = "application/json; charset=utf-8")
	public Map<String, Object> getSign(@RequestParam String url, @RequestParam String appid) {		 
        //调用财富接口，获取jsapi_ticket      
		String jsapi_ticket = null;
		//先从缓存中获取token
		ParamEntity param = tokenService.getValueByCode("jsapi_ticket");
		long updateTime = Long.parseLong(param.getUpdateTime());
		Long min = (Long) ((System.currentTimeMillis() - updateTime)/60000);
		if(min > 120) {
			//间隔大于120分钟 重新获取ticket   并更新原来的
			try {
				WxReturnParam wp  = WxHttpUtil.post(appid);
				if("success".equals(wp.getErrorCode())) {
					jsapi_ticket = wp.getTicket();
				}else {
					return GsonUtil.buildMap(1, wp.getErrorMsg(), null);
				}
				param.setValue(jsapi_ticket);
				param.setUpdateTime(String.valueOf(System.currentTimeMillis()));
				tokenService.modifyParam(param);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return GsonUtil.buildMap(1, "财富接口异常", null);
			}
		}else {
			jsapi_ticket = param.getValue();
		}

	
        String noncestr = RandomUtil.generateString(4);//
        //开始签名 所需参数：jsapi_ticket \ noncestr \timestamp  \ url
        String timestamp = String.valueOf(System.currentTimeMillis());
        
		String signature = null;
		signature = WxConfigUtil.signature( jsapi_ticket,  timestamp,  noncestr,  url);
		SignatureEntity signatureEntity = new SignatureEntity(signature);
		signatureEntity.setSignature(signature);
		signatureEntity.setNoncestr(noncestr);
		signatureEntity.setTimestamp(timestamp);
		return GsonUtil.buildMap(0, "ok", signatureEntity);
	}

}
