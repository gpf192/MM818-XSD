package com.xsdzq.mm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;
import com.xsdzq.mm.entity.UserEmpRelationEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.ProductSellViewService;
import com.xsdzq.mm.service.UserEmpRelationService;
import com.xsdzq.mm.service.UserService;


@Component
@Async
public class ScheduledService {
	@Autowired
	@Qualifier("prizeServiceImpl")
	PrizeService prizeService;
	
	@Autowired
	@Qualifier("productSellViewServiceImpl")
	ProductSellViewService productSellViewService;
	
	@Autowired
	@Qualifier("userEmpRelationServiceImpl")
	UserEmpRelationService userEmpRelationService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
      //  log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    	System.out.println("111111111111111111111111111111111111111111111111");
    	//定时扫描交易任务
    	//查看用户交易视图crm，循环交易记录
    	
    	List<ProductSellViewEntity> productSellViewList = productSellViewService.getByDealTime(20180301);
    	if(productSellViewList != null) {
        	for(ProductSellViewEntity p:productSellViewList) {
        	//	System.out.println("奖品：----------------------"+p.getName());
        		String clientId = p.getClientId();
        		UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
        		String empId = "0";
        		if(ue != null) {
        			String brokerId = ue.getBrokerId();
        			String touguId = ue.getTouguId();
        			if(brokerId != null && touguId != null ) {
        				empId = brokerId;
        			}else if(brokerId != null && touguId == null) {
        				empId = brokerId;
        			}else if(brokerId == null && touguId != null) {
        				empId = touguId;
        			}
        		}
        		//判断是否有经纪人
        		if(!"0".equals(empId)) {
        			//有经纪人  判断用户记录表是否存在clientid，
        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 
        			//如果存在  插入用户得票记录表-增票 -自动减票 
        		}else {
        			//无经纪人 判断用户记录表是否存在clientid，
        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票
        		}
        	}
    	}
    	
    	//定时扫描用户登录记录 自动归票至经纪人// 用户登录 用户表已经有记录 这里只更新用户票数表-减票   插入用户得票记录表-自动减票
    	//定时扫描用户分享记录 自动归票至经纪人
    	//定时扫描用户抽奖获得额外投票权 自动归票至经纪人
    	
    	//定时扫描用户活动期间签约投顾 自动归票至经纪人
    	//定式扫描用户活动期间开通基金账户 自动归票至经纪人
    	

    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
      //  log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    	System.out.println("2222222222222222222222222222222222222222222222222222");
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
     //   log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    	System.out.println("33333333333333333333333333333333333333333333333333333");
    }
}