package com.redhat.crowdfunding.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.redhat.crowdfunding.bean.Fund;
import com.redhat.crowdfunding.service.CrowdFundingService;
import com.redhat.crowdfunding.service.CrowdFundingServiceImpl;

/**
 * @author littleredhat
 */
@Controller
public class CrowdFundingController {

	@RequestMapping("list")
	public String List(HttpServletRequest request, HttpServletResponse response) {
		CrowdFundingService service = new CrowdFundingServiceImpl();
		try {
			int totalNum = service.getFundCount();
			request.setAttribute("totalNum", totalNum);
			return "list";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "error";
	}

	@RequestMapping("getFunds")
	@ResponseBody
	public String getFunds(HttpServletRequest request, HttpServletResponse response) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		CrowdFundingService service = new CrowdFundingServiceImpl();
		try {
			List<Fund> data = service.getFunds(pageIndex);
			System.out.println(JSON.toJSONString(data));
			return JSON.toJSONString(data);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping("raiseFund")
	@ResponseBody
	public boolean raiseFund(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		CrowdFundingService service = new CrowdFundingServiceImpl(password, content);
		try {
			return service.raiseFund();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping("sendCoin")
	@ResponseBody
	public boolean sendCoin(HttpServletRequest request, HttpServletResponse response) {
		String owner = request.getParameter("owner");
		int coin = Integer.parseInt(request.getParameter("coin"));
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		CrowdFundingService service = new CrowdFundingServiceImpl(password, content);
		try {
			return service.sendCoin(owner, coin);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
