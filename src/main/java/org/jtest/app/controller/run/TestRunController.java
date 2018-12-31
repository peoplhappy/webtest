package org.jtest.app.controller.run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.jboss.logging.MDC;
import org.jtest.app.controller.BaseController;
import org.jtest.app.controller.run.thread.RunThread;
import org.jtest.app.controller.websocket.WebSocketController;
import org.jtest.app.dao.config.ConfigDao;
import org.jtest.app.log.LogManager;
import org.jtest.app.model.asserts.AssertModel;
import org.jtest.app.model.config.ConfigInfo;
import org.jtest.app.model.infs.InfsInfo;
import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;
import org.jtest.app.model.protocal.ProtocalInfo;
import org.jtest.app.model.requests.RequestParamter;
import org.jtest.app.model.requests.ResponseResult;
import org.jtest.app.model.testcase.RunType;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseDto;
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.model.testcase.TestCaseResult;
import org.jtest.app.model.testcase.TestSuite;
import org.jtest.app.model.testcase.TestSuitesResult;
import org.jtest.app.model.threadlocal.ThreadLocalParam;
import org.jtest.app.model.websocket.WebSocketMessage;
import org.jtest.app.runtime.RunTimeConfig;
import org.jtest.app.service.config.ConfigService;
import org.jtest.app.service.infs.InfsResultService;
import org.jtest.app.service.infs.InfsService;
import org.jtest.app.service.item.LogTreeItemService;
import org.jtest.app.service.protocal.ProtocalService;
import org.jtest.app.service.testcase.TestCaseResultService;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.service.testcase.TestSuiteService;
import org.jtest.app.util.JsonUtil;
import org.jtest.app.util.MapUtil;
import org.jtest.app.util.ParamReplaceUtil;
import org.jtest.app.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jtest.app.model.param.ResultType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Administrator 运行测试用列和测试集
 */
@RestController
public class TestRunController extends BaseController{
	@Resource(name="threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor pool;
	private Gson gson = JsonUtil.gson;
	@Autowired
	private ConfigService configservice;
	@Autowired
	private TestCaseService testcaseservice;
	@Autowired
	private ProtocalService service;
	@Autowired
	private InfsService infsservice;
	@Autowired
	private TestCaseResultService resultservice;
	@Autowired
	private InfsResultService infresservice;
	@Autowired
	private LogTreeItemService logtreeitemservice;
	@Autowired
	private WebSocketController websocketservice;
	@Autowired
	private TestSuiteService testsuiteservice;


	@PostMapping("/run/runtestcase")
	public void RunTestCase(TestCaseDto dto) {
		TestCase testcase=testcaseservice.findTestCase(dto.getProjectId(), dto.getTestcaseId());
		pool.execute(new RunThread(RunType.TestCase, dto.getUserId(),testcase,configservice,testcaseservice,service
				,infsservice,resultservice,infresservice,logtreeitemservice,websocketservice,testsuiteservice));

	}

	@PostMapping("/run/runtestsuits")
	public void RunTestSuites(TestCaseDto dto) {
         TestSuite testsuite=testsuiteservice.findById(dto.getTestcaseId());
         pool.execute(new RunThread(RunType.TestSuites, dto.getUserId(),testsuite,configservice,testcaseservice,service
 				,infsservice,resultservice,infresservice,logtreeitemservice,websocketservice,testsuiteservice));
	}


}
