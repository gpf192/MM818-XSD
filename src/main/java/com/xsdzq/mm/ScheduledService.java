package com.xsdzq.mm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.ProductSellViewService;


@Component
@Async
public class ScheduledService {
	@Autowired
	@Qualifier("prizeServiceImpl")
	PrizeService prizeService;
	
	@Autowired
	@Qualifier("productSellViewServiceImpl")
	ProductSellViewService productSellViewService;
	//定时扫描交易任务
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
      //  log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    	System.out.println("111111111111111111111111111111111111111111111111");
    	//查看用户交易视图crm，循环交易记录
    	
    	List<ProductSellViewEntity> productSellViewList = productSellViewService.getByDealTime(20180301);
    	if(productSellViewList != null) {
        	for(ProductSellViewEntity p:productSellViewList) {
        	//	System.out.println("奖品：----------------------"+p.getName());
        	}
    	}

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