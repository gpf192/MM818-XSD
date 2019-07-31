package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.entity.EmpTicketEntity;
import com.xsdzq.mm.model.Pagination;
import com.xsdzq.mm.service.EmpService;
import com.xsdzq.mm.service.EmpTicketService;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/activity/emp")
public class EmpController {

	@Autowired
	TokenService tokenService;

	@Autowired
	@Qualifier("empTicketServiceImpl")
	EmpTicketService empTicketService;

	@Autowired
	@Qualifier("empServiceImpl")
	EmpService empService;

	@GetMapping(value = "/list", produces = "application/json; charset=utf-8")
	public Map<String, Object> getEmpTicketList(@RequestParam int pageNumber, @RequestParam int pageSize,
			@RequestParam String divison) {

		List<EmpTicketEntity> empTicketList = empTicketService.getEmpTicketEntities(pageNumber, pageSize, divison);
		Pagination pagination = new Pagination(pageNumber, pageSize);
		int total = empTicketService.countEmpNumberByDivison(divison);
		pagination.setTotalItems(total);

		return GsonUtil.buildMap(0, "ok", empTicketList, pagination);
	}

	@GetMapping(value = "/query", produces = "application/json; charset=utf-8")
	public Map<String, Object> queryEmp(@RequestParam String name) {
		List<EmpTicketEntity> eList = empService.findByEmpNameLike(name);

		return GsonUtil.buildMap(0, "ok", eList);
	}

}
