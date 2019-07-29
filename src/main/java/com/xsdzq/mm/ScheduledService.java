package com.xsdzq.mm;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xsdzq.mm.entity.OpenAccountEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.ProductEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;
import com.xsdzq.mm.entity.SignInvestViewEntity;
import com.xsdzq.mm.entity.UserEmpRelationEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.ProductSellViewService;
import com.xsdzq.mm.service.ProductService;
import com.xsdzq.mm.service.UserEmpRelationService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.TicketUtil;


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
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("userTicketServiceImpl")
	UserTicketService userTicketService;
	
	public   String getEmpId(String clientId) {
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
		return empId;
	}
	
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
      //  log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    	System.out.println("进入 job 111111111111111111111111111111111111111111111111");
    	// 判断job开关
    	String flag = userService.getValueByCode("jobFlag").getCode();
    	System.out.println("=====>>>>> job 开关为 "+ flag);
    	if("1".equals(flag)) {
    		//定时扫描交易任务
        	try {
    			productSellTask();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("productSellTask 发生异常"+"111111111111111111111111111111111111111111111111");

    			e.printStackTrace();
    		}
        	 	
        	//定时扫描用户活动期间签约投顾 自动归票至经纪人
        	try {
    			tgfundOpenAccountTask();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("tgfundOpenAccountTask 发生异常"+"111111111111111111111111111111111111111111111111");

    			e.printStackTrace();
    		}
        	//定式扫描用户活动期间开通基金账户 自动归票至经纪人
        	try {
    			fundOpenAccountTask();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("fundOpenAccountTask 发生异常"+"111111111111111111111111111111111111111111111111");

    			e.printStackTrace();
    		}
    	}
    	
    	
	}
    
		

  //定时扫描用登录记录  户分享记  抽奖获得额外投票权 自动归票至经纪人
	    public void frontTask(int ticketNum , String reason) {
			//获得t-1      
			String nowString = DateUtil.getPreDay();
			List<UserTicketRecordEntity> loginRecordList = userTicketService.getByVotesSourceAndDateFlag(reason, nowString);
			if(loginRecordList != null) {
				for(UserTicketRecordEntity r:loginRecordList) {
					String clientId = r.getUserEntity().getClientId();
					String empId = getEmpId(clientId);
					if(!"0".equals(empId)) {
						//有经纪人
						userService.addTicketByJobForReduceEmp(clientId,  empId,  ticketNum,  reason);
					}
					
				}
			}
	    }

	    //定时扫描产品交易方法
	    public void productSellTask() {
	//查看用户交易视图crm，循环交易记录
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDay());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//查询所有的产品 循环每一个产品与销售记录进行匹配
			Date  d = DateUtil.getPreDayAs();
			List<ProductEntity> productList = productService.getAll(d, d);
			if(productList != null) {
				for(ProductEntity Product:productList) {
					String productCode = Product.getCode();
					List<ProductSellViewEntity> productSellViewList = productSellViewService.getByDealTimeAndProductCode(preDay, productCode);
			    	if(productSellViewList != null) {
			        	for(ProductSellViewEntity p:productSellViewList) {
			        	//	System.out.println("奖品：----------------------"+p.getName());
			        		String clientId = p.getClientId();
			        		String clientName = p.getClientName();
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
			        		//计算得票数
			        		double xishu = Double.parseDouble((productService.getProductByCode(p.getProductCode())).getCoefficient());
			        		double dealAmount = Double.parseDouble( p.getDealAmount());
			        		
			        		BigDecimal dealAmountDecimal = new BigDecimal(dealAmount);
			        		BigDecimal xishuDecimal = BigDecimal.valueOf(xishu);
			        		double num = dealAmountDecimal.multiply(xishuDecimal).doubleValue();        		
			        		int ticketNum =(int) Math.round(num);
			        		
			        		//判断是否有经纪人
			        		if("0".equals(empId)) {
			        			//无经纪人 判断用户记录表是否存在clientid，
			        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
			        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票 
			        			userService.addTicketByJob(clientId, clientName, ticketNum); 
			        			
			        		}else {       			
			        			//有经纪人  判断用户记录表是否存在clientid
			        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
			        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
			        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum); 
			        		}
			        	}
			    	}
				}
			}
			
	    	
	    }
	    //扫描开通基金账户 一次性得票
	    public void fundOpenAccountTask() {
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDay());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<OpenAccountEntity> list=  userService.findByOpenDate(preDay);
			if(list != null) {
				//如果有记录
				for(OpenAccountEntity p:list) {
					String clientId = p.getClientId();		
					//判断得票记录表中是否有该用户记录 若没有则继续插入
					List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientId(TicketUtil.BUYFUNDTICKET, clientId);
					if(UserTicketRecordList == null) {
		        		String empId = "0";
		        		//获取经纪人
		        		UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
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
		        		//获取票数
		        		int ticketNum = Integer.parseInt(userService.getValueByCode("fund_ticket_num").getCode());
		        		//判断是否有经纪人
		        		String clientName = ue.getClientName();
		        		if("0".equals(empId)) {
		        			//无经纪人 判断用户记录表是否存在clientid，
		        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
		        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票 
		        			userService.addTicketByJob(clientId, clientName, ticketNum); 
		        			
		        		}else {       			
		        			//有经纪人  判断用户记录表是否存在clientid
		        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
		        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
		        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum); 
		        		}
					}

				}
			}
			
	    }
	    //扫描签约投顾 一次性得票
	    public void tgfundOpenAccountTask() {
	    	//查看上一日签约投顾记录表
	    	String preDay = DateUtil.getPreDay();
	    	List<SignInvestViewEntity> SignInvestViewList= userService.findByserviceTypeAndStatusAndEffectiveDate(0, "1", preDay);
	    	if(SignInvestViewList != null) {
	    		//如果有记录
	    		for(SignInvestViewEntity si:SignInvestViewList) {
	    			String clientId = si.getClientId();	
	    			
	    			String empId = si.getInvestId();
					//判断得票记录表中是否有该用户记录 若没有则继续插入
					List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientId(TicketUtil.NEEFUNDTICKET, clientId);
					
	        		//获取票数
	        		int ticketNum = Integer.parseInt(userService.getValueByCode("fund_ticket_num").getCode());	        		
	        		//获取用户姓名
	        		UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
	        		String clientName = ue.getClientName();
	        		
					if(UserTicketRecordList == null) {
						//无经纪人 判断用户记录表是否存在clientid，
	        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
	        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票 
	        			userService.addTicketByJob(clientId, clientName, ticketNum); 
	        			
	        		}else {   			
	        			//有经纪人  判断用户记录表是否存在clientid
	        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
	        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
	        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum); 
	        		}
					
	    		}
	    	}
	    }

}