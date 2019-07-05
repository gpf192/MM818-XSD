package com.xsdzq.mm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.dao.PrizeRepository;
import com.xsdzq.mm.dao.impl.ParamRepositoryImpl;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.util.PrizeUtil;

@Service(value = "prizeServiceImpl")
@Transactional(readOnly = true)
public class PrizeServiceImpl implements PrizeService {

	@Autowired
	private PrizeRepository prizeRepository;

	@Autowired
	private ParamRepository paramRepository;

	@Override
	public List<PrizeEntity> getPrizeAll() {
		// TODO Auto-generated method stub
		return prizeRepository.findAll();
	}

	@Override
	public PrizeEntity getMyPrize() {
		// TODO Auto-generated method stub
		// 计算中奖项，返回中奖项
		ParamEntity paramEntity = paramRepository.getValueByCode(ParamRepositoryImpl.LCJCOMPUSER);
		PrizeUtil prizeUtil = PrizeUtil.getInstance();
		String totalString = paramEntity.getValue();
		List<PrizeEntity> prizeList = getPrizeAll();
		if (totalString != null) {
			try {
				int total = Integer.parseInt(totalString);
				if (total > 0) {
					// 得到总数
					for (PrizeEntity prizeEntity : prizeList) {
						System.out.println(prizeEntity.toString());
						String amountString = prizeEntity.getAmount();
						int amount = Integer.parseInt(amountString);
						if (prizeUtil.testChoice(amount, total)) {
							return prizeEntity;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 判断返回 谢谢参与逻辑
		return prizeUtil.getXieXieEntity(prizeList);
	}

}
