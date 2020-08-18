package com.xsdzq.mm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xsdzq.mm.entity.ChangWaiSellViewEntity;
import com.xsdzq.mm.entity.CreditAccountOpenViewEntity;
import com.xsdzq.mm.entity.OpenAccountEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.ProductEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;
import com.xsdzq.mm.entity.ShareOptionAccountOpenViewEntity;
import com.xsdzq.mm.entity.SignInvestViewEntity;
import com.xsdzq.mm.entity.UserEmpRelationEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.ProductSellViewService;
import com.xsdzq.mm.service.ProductService;
import com.xsdzq.mm.service.UserEmpRelationService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;
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
	//(cron = "0/5 * * * * *") 每5分钟     ， cron = "0 35 05 * * ?" 凌晨5点35
    //@Scheduled(cron = "0/5 * * * * *")
    @Scheduled(cron = "0 20 11 * * ?")
    public void scheduled(){
      //  log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    	System.out.println("进入 job 111111111111111111111111111111111111111111111111");
    	// 判断job开关
    	String flag = userService.getValueByCode("jobFlag").getValue();
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
				  } catch (Exception e) { // TODO Auto-generated
					  System.out.println("tgfundOpenAccountTask 发生异常"+"111111111111111111111111111111111111111111111111");
			  
					  e.printStackTrace(); 
			  }
			 
        	//定式扫描用户活动期间开通基金账户 自动归票至经纪人
			
			  try { 
				  fundOpenAccountTask(); 
			  } catch (Exception e) { 
					  // TODO Auto-generated
				    System.out.println("fundOpenAccountTask 发生异常"
				  +"111111111111111111111111111111111111111111111111");
				  
				  e.printStackTrace(); 
			  }
    		//扫描理财产品购买记录
    		/*try {
    			productSellTaskForKMH();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("productSellTaskForKMH 发生异常"+"111111111111111111111111111111111111111111111111");

    			e.printStackTrace();
    		}
        	 	
        	//扫描签约投顾			
			  try { 
				  tgfundOpenAccountTaskForKMH(); 
				  } catch (Exception e) { // TODO Auto-generated
					  System.out.println("tgfundOpenAccountTaskForKMH 发生异常"+"111111111111111111111111111111111111111111111111");
			  
					  e.printStackTrace(); 
			  }
			 
        	//扫描开通信用账户			
			  try { 
				  creditAccountTaskForKMH(); 
			  } catch (Exception e) { 
					  // TODO Auto-generated
				    System.out.println("creditAccountTaskForKMH 发生异常"
				  +"111111111111111111111111111111111111111111111111");
				  
				  e.printStackTrace(); 
			  }
			//扫描开通期权			
			  try { 
				  shareOptionAccountTaskForKMH(); 
			  } catch (Exception e) { 
					  // TODO Auto-generated
				    System.out.println("shareOptionAccountTaskForKMH 发生异常"
				  +"111111111111111111111111111111111111111111111111");
				  
				  e.printStackTrace(); 
			  }*/
    	}
    	
    	
	}
    	

	    //定时扫描产品交易方法
	    public void productSellTask() {
	//查看用户交易视图crm，循环交易记录
	    	System.out.println("=====================>>>>> 产品交易job 开始 ");
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDayForCrm());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//preDay = 20200814;//测试用
			//查询所有的产品 循环每一个产品与销售记录进行匹配
			Date  d = DateUtil.getPreDayAs();
			List<ProductEntity> productList = productService.getAll(d, d);//获取前一天仍有效的产品
			System.out.println("产品 个数："+ productList.size());
			if(productList.size() != 0) {
				for(ProductEntity Product:productList) {
					String productCode = Product.getCode();
					System.out.println("******************* chanpin shi  "+productCode);
					
					if(Product.getScanFlag() == 1) {
					//	1 标识 需要扫场内， 此时场内场外一起扫 ，否则只扫场外
					
					//根据产品code 和前一天日期 ，获取crm接口的交易数据
					List<ProductSellViewEntity> productSellViewList = productSellViewService.getByDealTimeAndProductCode(preDay, productCode);
			    	if(productSellViewList.size() != 0) {
			        	for(ProductSellViewEntity p:productSellViewList) {

			        		String clientId = p.getClientId();
			        		String serialNum = p.getLsh();
			        		//查看前一天的 job 是否执行，若执行 则跳过
							List<UserTicketRecordEntity> UserTicketRecordList = userService.findBySerialNum(serialNum);
							//List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientIdAndDateFlag(TicketUtil.BUYFUNDTICKET, clientId, DateUtil.getPreDay());
							if(UserTicketRecordList.size() == 0) {
								String clientName = p.getClientName();
				        		/*UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
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
				        		System.out.println("*************----empid   "+ empId);*/
				        		//计算得票数
				        		double xishu = Double.parseDouble((productService.getProductByCode(p.getProductCode())).getCoefficient());
				        		double dealAmount = Double.parseDouble( p.getDealAmount());
				        		
				        		BigDecimal dealAmountDecimal = new BigDecimal(dealAmount);
				        		BigDecimal xishuDecimal = BigDecimal.valueOf(xishu);
				        		double num = dealAmountDecimal.multiply(xishuDecimal).doubleValue();        		
				        		int ticketNum =(int) Math.round(num);
			        			System.out.println("票数*************----  "+ ticketNum);
			        			if(ticketNum>0) {
				        			userService.addTicketByJob(clientId, clientName, ticketNum, TicketUtil.BUYFUNDTICKET, serialNum);

			        			}
			        			/*
				        		//判断是否有经纪人
				        		if("0".equals(empId)) {
				        			//无经纪人 判断用户记录表是否存在clientid，
				        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
				        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票 
				        			userService.addTicketByJob(clientId, clientName, ticketNum, TicketUtil.BUYFUNDTICKET); 
				        			
				        		}else {       			
				        			//有经纪人  判断用户记录表是否存在clientid
				        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
				        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
				        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum, TicketUtil.BUYFUNDTICKET); 
				        		}
				        		*/
							}
			        		
			        	}
			    	}
				}else {
					//只扫场外
					List<ChangWaiSellViewEntity> productSellViewList = productSellViewService.getCwByDealTimeAndProductCode(preDay, productCode);
					if(productSellViewList.size() != 0) {
			        	for(ChangWaiSellViewEntity p:productSellViewList) {

			        		String clientId = p.getClientId();
			        		String serialNum = p.getLsh();
			        		//查看前一天的 job 是否执行，若执行 则跳过
							List<UserTicketRecordEntity> UserTicketRecordList = userService.findBySerialNum(serialNum);
							//List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientIdAndDateFlag(TicketUtil.BUYFUNDTICKET, clientId, DateUtil.getPreDay());
							if(UserTicketRecordList.size() == 0) {
								String clientName = p.getClientName();
				        		
				        		//计算得票数
				        		double xishu = Double.parseDouble((productService.getProductByCode(p.getProductCode())).getCoefficient());
				        		double dealAmount = Double.parseDouble( p.getDealAmount());
				        		
				        		BigDecimal dealAmountDecimal = new BigDecimal(dealAmount);
				        		BigDecimal xishuDecimal = BigDecimal.valueOf(xishu);
				        		double num = dealAmountDecimal.multiply(xishuDecimal).doubleValue();        		
				        		int ticketNum =(int) Math.round(num);
			        			if(ticketNum>0) {
				        			userService.addTicketByJob(clientId, clientName, ticketNum, TicketUtil.BUYFUNDTICKET, serialNum);

			        			}
			        			
							}
			        		
			        	}
			    	}
				}
			}
		}  	
	   }
	    
	    //开门红活动     定时扫描产品交易方法 2020年2月
	    public void productSellTaskForKMH() {
	//查看用户交易视图crm，循环交易记录
	    	System.out.println("=====================>>>>> 产品交易job 开始 ");
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDayForCrm());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//查询所有的产品 循环每一个产品与销售记录进行匹配
			Date  d = DateUtil.getPreDayAs();
			List<ProductEntity> productList = productService.getAll(d, d);//查询有效期内的产品
			System.out.println("产品 个数："+ productList.size());
			if(productList.size() != 0) {
				for(ProductEntity product:productList) {
					String productCode = product.getCode();
					System.out.println("******************* 本次扫描产品code：  "+productCode);
					if(product.getScanFlag() == 1) {
						//1 标识 需要扫场内， 此时场内场外一起扫 ，否则只扫场外
						List<ProductSellViewEntity> productSellViewList = productSellViewService.getByDealTimeAndProductCode(preDay, productCode);
				    	if(productSellViewList.size() != 0) {
				        	for(ProductSellViewEntity p:productSellViewList) {
				        		//条件  金额 大于等于 5k 放在数据库视图里判断

				        		String clientId = p.getClientId();
				        		String serialNum = null;
								try {
									serialNum = p.getLsh();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        		//查看前一天的 job 是否执行，若执行 则跳过
				        		//查看 记录表中是否有该条流水号的记录，若没有就加入
								List<PrizeRecordEntity> prizeRecordList = userService.findPrizeRecordBySerialNum(serialNum);
								if(prizeRecordList.size() == 0) {												        					 			        		
				        			//计算抽奖次数
									double dealAmount = Double.parseDouble( p.getDealAmount());
				        			BigDecimal dealAmountDecimal = new BigDecimal(dealAmount);
				        			BigDecimal number = dealAmountDecimal.divide(new BigDecimal("10000"),0,BigDecimal.ROUND_HALF_UP);
				        			userService.addPrizeNumAndRecordForKMH(clientId, PrizeUtil.PRIZE_BUY_TYPE, number.intValue(), serialNum);
								}
				        		
				        	}
				    	}else {
				    		System.out.println("******************* 没有销售产品记录 ");
				    	}
					}else {
						//只扫场外
						List<ChangWaiSellViewEntity> productSellViewList = productSellViewService.getCwByDealTimeAndProductCode(preDay, productCode);
				    	if(productSellViewList.size() != 0) {
				        	for(ChangWaiSellViewEntity p:productSellViewList) {
				        		//条件  金额 大于等于 5k 放在数据库视图里判断

				        		String clientId = p.getClientId();
				        		String serialNum = null;
								try {
									serialNum = p.getLsh();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        		//查看前一天的 job 是否执行，若执行 则跳过
				        		//查看 记录表中是否有该条流水号的记录，若没有就加入
								List<PrizeRecordEntity> prizeRecordList = userService.findPrizeRecordBySerialNum(serialNum);
								if(prizeRecordList.size() == 0) {												        					 			        		
				        			//计算抽奖次数
									double dealAmount = Double.parseDouble( p.getDealAmount());
				        			BigDecimal dealAmountDecimal = new BigDecimal(dealAmount);
				        			BigDecimal number = dealAmountDecimal.divide(new BigDecimal("10000"),0,BigDecimal.ROUND_HALF_UP);
				        			userService.addPrizeNumAndRecordForKMH(clientId, PrizeUtil.PRIZE_BUY_TYPE, number.intValue(), serialNum);
								}
				        		
				        	}
				    	}else {
				    		System.out.println("******************* 没有销售产品记录 ");
				    	}
					}

				}
			}else{
				System.out.println("******************* 没有  产品记录 ");
			}				    	
	    }
	    
	    //扫描开通基金账户 一次性得票
	    public void fundOpenAccountTask() {
	    	System.out.println("=====================>>>>> 开通基金job 开始 ");

	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDayForCrm());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<OpenAccountEntity> list=  userService.findByOpenDate(preDay);//查询前一天之前所有的记录
			if(list.size() != 0) {
				//如果有记录
				for(OpenAccountEntity p:list) {
					String clientId = p.getClientId();		
					//判断得票记录表中是否有该用户记录 若没有则继续插入
					List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientId(TicketUtil.NEEFUNDTICKET, clientId);
					if(UserTicketRecordList.size() == 0) {
		        		String empId = "0";
		        		//获取经纪人
		        		UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
		        		/*if(ue != null) {
		        			String brokerId = ue.getBrokerId();
		        			String touguId = ue.getTouguId();
		        			if(brokerId != null && touguId != null ) {
		        				empId = brokerId;
		        			}else if(brokerId != null && touguId == null) {
		        				empId = brokerId;
		        			}else if(brokerId == null && touguId != null) {
		        				empId = touguId;
		        			}
		        		}*/
		        		//获取票数
		        		int ticketNum = Integer.parseInt(userService.getValueByCode("fund_ticket_num").getValue());
		        		//判断是否有经纪人
		        		String clientName = "";
						try {
							clientName = ue.getClientName();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		userService.addTicketByJob(clientId, clientName, ticketNum, TicketUtil.NEEFUNDTICKET,"");
		        		/*
		        		if("0".equals(empId)) {
		        			//无经纪人 判断用户记录表是否存在clientid，
		        			//如果不存在  插入用户表  插入用户票数表-增票  插入用户得票记录表-增票
		        			//如果存在  更新用户票数表-增票  插入用户得票记录-增票 
		        			userService.addTicketByJob(clientId, clientName, ticketNum, TicketUtil.NEEFUNDTICKET); 
		        			
		        		}else { 
		        			 clientName = ue.getClientName();
		        			//有经纪人  判断用户记录表是否存在clientid
		        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
		        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
		        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum, TicketUtil.NEEFUNDTICKET); 
		        		}
		        		*/
					}

				}
			}else {
				System.out.println("******************* 没有 kaitong 开通基金账户 记录 ");
			}
			
	    }
	    //扫描签约投顾 一次性得票
	    public void tgfundOpenAccountTask() {
	    	System.out.println("=====================>>>>> 签约投顾 job 开始 ");

	    	//查看上一日之前所有的签约投顾记录表
	    	String preDay = DateUtil.getPreDayForCrm();
	    	//preDay = "20200814";//ceshi
	    	List<SignInvestViewEntity> SignInvestViewList= userService.findByserviceTypeAndStatusAndEffectiveDate(0, "1", preDay);
	    	//System.out.println("***********************+ " +(SignInvestViewList == null)+ "--"+SignInvestViewList.size());
	    	if(SignInvestViewList.size() != 0) {
	    		//如果有记录
	    		for(SignInvestViewEntity si:SignInvestViewList) {
	    			String clientId = si.getClientId();	
	    			//System.out.println("OOOOOOOOOOOOOOOOOOOOOOO"+clientId);
	    			String empId = si.getInvestId();
					//判断得票记录表中是否有该用户记录 若没有则继续插入
					List<UserTicketRecordEntity> UserTicketRecordList = userService.findByVotesSourceAndUserEntity_clientId(TicketUtil.BROKETICKET, clientId);
	    			//System.out.println("wwwwwwwwwwwwwwwwww "+UserTicketRecordList.size());

	        		//获取票数
	        		int ticketNum = Integer.parseInt(userService.getValueByCode("tg_ticket_num").getValue());	        		
	        		//获取用户姓名
	        		UserEmpRelationEntity ue =userEmpRelationService.findByClientId(clientId);
	        		String clientName = "";
	        		if(ue != null) {
	        			 clientName = ue.getClientName();
	        		}
	        		
	        		
					if(UserTicketRecordList.size() == 0) {
						//如果不存在记录 表示之前没有因签约投顾而得票
						//有经纪人  判断用户记录表是否存在clientid
	        			//如果不存在  插入用户表  插入用户票数表-票数是0 // 插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
	        			//如果存在  插入用户得票记录表-增票 -自动减票 //插入用户投票员工表//员工票数表添加
	        			 userService.addTicketByJobWithEmpId(clientId, clientName, empId, ticketNum, TicketUtil.BROKETICKET); 	        			 				
	        			
	        		}
					
	    		}
	    	}else{
				System.out.println("******************* 没有 qianyue 签约投顾 记录 ");
	    	}
	    }

	    //开门红   扫描签约投顾 一次性得5次抽奖次数  
	    public void tgfundOpenAccountTaskForKMH() {
	    	System.out.println("=====================>>>>> 签约投顾 job 开始 ");

	    	//查看上一日签约投顾记录表
	    	String preDay = DateUtil.getPreDayForCrm();
	    	//preDay = "20200814";//ceshi
	    	List<SignInvestViewEntity> SignInvestViewList= userService.findByserviceTypeAndStatusAndEffectiveDate(0, "1", preDay);
	    	System.out.println("***********************+ " +(SignInvestViewList == null)+ "--"+SignInvestViewList.size());
	    	if(SignInvestViewList.size() != 0) {
	    		//如果有记录
	    		for(SignInvestViewEntity si:SignInvestViewList) {
	    			String clientId = si.getClientId();	
	    			//判断用户 是否已经添加过票，防止用户刷票，每人一次
	    			List<PrizeRecordEntity> prizeRecordList = userService.findPrizeRecordByClinetIdAndReason(clientId,PrizeUtil.PRIZE_TOUGU_TYPE);
	    			if(prizeRecordList.size() == 0) {
	    				userService.addPrizeNumAndRecordForKMH(clientId, PrizeUtil.PRIZE_TOUGU_TYPE, 5, "");    
	    			}
        			    		
					
	    		}
	    	}else{
				System.out.println("******************* 没有 qianyue 签约投顾 记录 ");
	    	}
	    }
	    //开门红，开通信用账户，10次抽奖机会
	    public void creditAccountTaskForKMH() {
	    	System.out.println("=====================>>>>> 开通信用账户 job 开始 ");

	    	//查看上一日签约投顾记录表
	    	//String preDay = DateUtil.getPreDayForCrm();
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDayForCrm());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	List<CreditAccountOpenViewEntity> creditAccountViewList= userService.findCreditAccountBydataFlag(preDay);
	    	if(creditAccountViewList.size() != 0) {
	    		//如果有记录
	    		for(CreditAccountOpenViewEntity si:creditAccountViewList) {
	    			String clientId = si.getClientId();	
	    			//判断用户 是否已经添加过票，防止用户刷票，每人一次
	    			List<PrizeRecordEntity> prizeRecordList = userService.findPrizeRecordByClinetIdAndReason(clientId,PrizeUtil.PRIZE_XINYONG_TYPE);
	    			if(prizeRecordList.size() == 0) {
	    				userService.addPrizeNumAndRecordForKMH(clientId, PrizeUtil.PRIZE_XINYONG_TYPE, 10, "");        		
	    			}
	    		}
	    	}else{
				System.out.println("******************* 没有 开通信用账户 记录 ");
	    	}
	    }
	    //开门红，开通期权账户,10次抽奖机会
	    public void shareOptionAccountTaskForKMH() {
	    	System.out.println("=====================>>>>> 开通期权账户 job 开始 ");

	    	//查看上一日签约投顾记录表
	    	int preDay = 0;
			try {
				preDay = Integer.parseInt(DateUtil.getPreDayForCrm());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	//String preDay = DateUtil.getPreDayForCrm();
	    	List<ShareOptionAccountOpenViewEntity> creditAccountViewList= userService.findShareOptionAccountBydataFlag(preDay);
	    	if(creditAccountViewList.size() != 0) {
	    		//如果有记录
	    		for(ShareOptionAccountOpenViewEntity si:creditAccountViewList) {
	    			String clientId = si.getClientId();	
	    			//判断用户 是否已经添加过票，防止用户刷票，每人一次
	    			List<PrizeRecordEntity> prizeRecordList = userService.findPrizeRecordByClinetIdAndReason(clientId,PrizeUtil.PRIZE_QIQUAN_TYPE);
	    			if(prizeRecordList.size() == 0) {
	    				userService.addPrizeNumAndRecordForKMH(clientId, PrizeUtil.PRIZE_QIQUAN_TYPE, 10, "");        		
	    			}
	    		}
	    	}else{
				System.out.println("******************* 没有 开通期权账户 记录 ");
	    	}
	    }
}