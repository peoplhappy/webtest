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

<<<<<<< HEAD
	
=======
	private class RunThread implements Runnable {
		private RunType type;
		private Object testObj;
		private String userId;
        

		public RunThread(RunType type,String userId, Object testObject) {
			this.type = type;
			this.testObj = testObject;
			this.userId=userId;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 执行测试用列
			if (type.equals(RunType.TestCase)) {
				// 获取所有需要执行的接口
				TestCase testcase = (TestCase) testObj;
				try {
					runtestcase(userId,testcase,null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (type.equals(RunType.TestSuites)) {
				// 执行测试集
				TestSuite testsuite = (TestSuite) testObj;
				runTestSuite(userId,testsuite);
			}
		}
		
		private void runTestSuite(String userId,TestSuite testsuite){
			List<TestCase> testcaselist=testsuiteservice.findTestCase(testsuite.getProjectId(), testsuite.getTestcaseIds());
			if(testcaselist.size()==0){
				return;
			}
			List<LogTreeItem> projectItemList=logtreeitemservice.findItemByProjectIdandItemType(LogItemType.PROJECT, testsuite.getProjectId());
			LogTreeItem testsuiteResultItem=new LogTreeItem();
			testsuiteResultItem.setHaschildren(false);
			testsuiteResultItem.setItemType(LogItemType.TESTSUITLOG);
			testsuiteResultItem.setParentid(String.valueOf(projectItemList.get(0).getId()));
			testsuiteResultItem.setText(testsuite.getTestsuiteName()+"_"+System.currentTimeMillis());
			long resultId=logtreeitemservice.createItem(testsuiteResultItem).getId();
			for(TestCase testcase:testcaselist){
				try {
					runtestcase(userId,testcase,String.valueOf(resultId));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void runtestcase(String userId,TestCase testcase,String parentId) throws Exception {
			String logFileName = testcase.getTestcaseName() + "_" + System.currentTimeMillis();
			MDC.put("logFileName", logFileName);
			List<InfsResult> infreslist = new ArrayList<InfsResult>();
			List<TestCaseItem> testcaseitemLst = testcaseservice.findTestCaseItem(String.valueOf(testcase.getId()));
			// 将config信息压入map中
			readConfig(testcase.getProjectId());
			try {
				websocketservice.userMessage(userId, "start to run testcase "+testcase.getTestcaseName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取每个接口
			TestCaseResult caseresult = new TestCaseResult();
			caseresult.setName(logFileName);
			caseresult.setExcuteResult(ResultType.OK.name());
			caseresult.setLogfileurl("/logs/"+logFileName + ".log");
			caseresult.setStarttime(TimeUtil.getCurrentTime());
			caseresult.setProjectId(testcase.getProjectId());
			try {
				for (int i = 0; i < testcaseitemLst.size(); i++) {
					InfsInfo inf = infsservice.findInfs(testcase.getProjectId(),
							testcaseitemLst.get(i).getCallbackname());
					ProtocalInfo protocalinfo = service.findProtocal(inf.getProtocol(), inf.getSendType());
					// 判断是否为第三方组件
					if (protocalinfo.isThird3rd()) {

					} else {
						if (protocalinfo.getLanguageName().equalsIgnoreCase("java")) {
							if (protocalinfo.getProtocalname().equalsIgnoreCase("HTTP")) {
								InfsResult result = HttpExcute(protocalinfo, inf, testcaseitemLst.get(i));
								if (result.getExcuteResult().equalsIgnoreCase(ResultType.POK.name())) {
									caseresult.setExcuteResult(ResultType.POK.name());
								}
								infreslist.add(result);
							} else {
								System.out.println("暂不支持的协议");
							}

						} else {
							System.out.println("内置程序中不支持其他语言");
						}
					}
					// 入库讲infsresult
				}


			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				LogManager.getLogger().logError("excute infs throw a exception", e);
				caseresult.setExcuteResult(ResultType.POK.name());
			}  catch (Exception e) {
				LogManager.getLogger().logError("excute infs throw a exception", e);
				caseresult.setExcuteResult(ResultType.POK.name());
			}
			// 将case存入数据库
			caseresult.setEndtime(TimeUtil.getCurrentTime());
			TestCaseResult newcaseresult = resultservice.saveResult(caseresult);
			//存入logitem中去
			List<LogTreeItem> projectItemList=logtreeitemservice.findItemByProjectIdandItemType(LogItemType.PROJECT, testcase.getProjectId());
			LogTreeItem caseTreeItem=new LogTreeItem();
			caseTreeItem.setHaschildren(false);
			caseTreeItem.setItemType(LogItemType.TESTCASELOG);
			if(!StringUtils.isEmpty(parentId)){
				caseTreeItem.setParentid(parentId);
			}else{
				caseTreeItem.setParentid(String.valueOf(projectItemList.get(0).getId()));
			}		
			caseTreeItem.setText(caseresult.getName());
			caseTreeItem.setExcuteresult(caseresult.getExcuteResult());
			String url="../jtest/html/testcaselogview.html?projectId="+testcase.getProjectId()+"&testcaseresultId="+newcaseresult.getId();
			caseTreeItem.setUrl(url);
			logtreeitemservice.createItem(caseTreeItem);
			for (InfsResult res : infreslist) {
				res.setTestCaseResultId(String.valueOf(newcaseresult.getId()));
				infresservice.saveResult(res);
			}		
			try {
				websocketservice.userMessage(userId, testcase.getTestcaseName()+" run end");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MDC.remove("logFileName");
			clearConfig();
		}

		/**
		 * 执行验证点
		 */
		private String excuteAssert(String assertlist) {
			List<AssertModel> resultlist = new ArrayList<AssertModel>();
			LogManager.getLogger().logInfo("验证点列表:" + assertlist);
			if (StringUtils.isEmpty(assertlist)) {
				// 若未填入验证点默认OK
				return ResultType.OK.name();
			} else {
				List<String> list = gson.fromJson(assertlist, new TypeToken<List<String>>() {
				}.getType());
				for (int i = 0; i < list.size(); i++) {
					String str = ParamReplaceUtil.Replace(list.get(i));
					resultlist.add(gson.fromJson(str, AssertModel.class));
				}
				return gson.toJson(resultlist, new TypeToken<List<AssertModel>>() {
				}.getType());
			}
		}

		/**
		 * 讲config中的信息全部压入map中
		 */
		private void readConfig(String projectId) {
			if (RunTimeConfig.configmap.containsKey(RunTimeConfig.getCurrentThreadName())) {
				clearConfig();
			}

			List<ConfigInfo> configinfolist = configservice.findByprojectId(projectId);

			Map<String, String> hashmap = new HashMap<String, String>();
			for (ConfigInfo info : configinfolist) {
				hashmap.put(info.getKeyname(), info.getValuestr());
			}
			hashmap.put("projectId", projectId);
			RunTimeConfig.configmap.put(RunTimeConfig.getCurrentThreadName(), hashmap);
		}

		private void clearConfig(){
			RunTimeConfig.configmap.remove(RunTimeConfig.getCurrentThreadName());
		}
		

		private InfsResult HttpExcute(ProtocalInfo protocalinfo, InfsInfo inf, TestCaseItem item) {
			InfsResult infres = new InfsResult();
			infres.setExcuteResult(ResultType.OK.name());
			infres.setStarttime(TimeUtil.getCurrentTime());
			infres.setInfsName(inf.getInfsname());
			try {
				// 加载方法运行
				Class clz = Class.forName(protocalinfo.getClassFileUrl());
				// 加载方法
				Method[] methods = clz.getMethods();
				// 将结果写入数据库

				ResponseResult res = null;
				for (Method method : methods) {
					if (method.getName().equalsIgnoreCase(protocalinfo.getMethodName())) {
						// 执行方法
						
						String url =inf.getInfsurl();
						String presentparamaterstr = ParamReplaceUtil.Replace(item.getPresetparameter());
						// 压入map中
						
						if(!StringUtils.isEmpty(presentparamaterstr)){
							Map<String, Object> presentparamater = gson.fromJson(presentparamaterstr,
									new TypeToken<Map<String, Object>>() {
									}.getType());
							for (Map.Entry<String, Object> entry : presentparamater.entrySet()) {
								MapUtil.putMapValue(entry.getKey(), entry.getValue().toString());
							}
						}
						
						url = ParamReplaceUtil.Replace(url);
						String headerinfo = ParamReplaceUtil.Replace(inf.getHeaderinfo());
						String paramterinfo = ParamReplaceUtil.Replace(inf.getParamterinfo());
						LogManager.getLogger().logInfo(inf.getInfsname()+"请求头为:"+headerinfo);
						LogManager.getLogger().logInfo(inf.getInfsname()+"请求参数为:"+paramterinfo);
						Map<String, Object> header = gson.fromJson(headerinfo, new TypeToken<Map<String, Object>>() {
						}.getType());
						RequestParamter parameter = gson.fromJson(paramterinfo, RequestParamter.class);
						if(parameter==null){
							parameter=new RequestParamter();
						}
						res = (ResponseResult) method.invoke(null, new Object[] { url, header, parameter });
						LogManager.getLogger().logInfo(inf.getInfsname()+"执行结果为:"+gson.toJson(res, ResponseResult.class));
						// 将执行结果压入map中
						MapUtil.putMapValue(inf.getInfsname(), gson.toJson(res, ResponseResult.class));
						infres.setEndtime(TimeUtil.getCurrentTime());
						break;
					}
				}
				// 执行assert验证点
				String assertList = excuteAssert(item.getAssertlist());
				infres.setAssertresultlist(assertList);
				infres.setInfsName(inf.getInfsname());
				// 获取结果
				if (!assertList.equals(ResultType.OK)) {
					List<AssertModel> list = gson.fromJson(assertList, new TypeToken<List<AssertModel>>() {
					}.getType());
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).isAssertResult().equalsIgnoreCase(ResultType.POK.name())) {
							infres.setExcuteResult(ResultType.POK.name());
						}
					}
				}
			} catch (Exception e) {
				LogManager.getLogger().logError("excute interface" + item.getCallbackname() + "error", e);
				infres.setEndtime(TimeUtil.getCurrentTime());
				infres.setExcuteResult(ResultType.POK.name());
			}
			return infres;
		}

	}
>>>>>>> parent of a488941... 修改bug
}
