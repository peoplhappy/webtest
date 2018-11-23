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
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.model.testcase.TestCaseResult;
import org.jtest.app.runtime.RunTimeConfig;
import org.jtest.app.service.config.ConfigService;
import org.jtest.app.service.infs.InfsResultService;
import org.jtest.app.service.infs.InfsService;
import org.jtest.app.service.item.LogTreeItemService;
import org.jtest.app.service.protocal.ProtocalService;
import org.jtest.app.service.testcase.TestCaseResultService;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.util.JsonUtil;
import org.jtest.app.util.ParamReplaceUtil;
import org.jtest.app.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Administrator 运行测试用列和测试集
 */
@RestController
public class TestRunController {
	@Resource
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

	@PostMapping("/run/runtestcase")
	public void RunTestCase(TestCase testcase) {
		pool.execute(new RunThread(RunType.TestCase, testcase));

	}

	@PostMapping("/run/runtestsuits")
	public void RunTestSuites() {

	}

	private class RunThread implements Runnable {
		private RunType type;
		private String threadName;
		private Object testObj;

		public RunThread(RunType type, Object testObject) {
			this.type = type;
			this.threadName = RunTimeConfig.getCurrentThreadName();
			this.testObj = testObject;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 执行测试用列
			if (type.equals(RunType.TestCase)) {
				// 获取所有需要执行的接口
				TestCase testcase = (TestCase) testObj;
				runtestcase(testcase);
			} else if (type.equals(RunType.TestSuites)) {
				// 执行测试集
			}
		}

		private void runtestcase(TestCase testcase) {
			String logFileName = testcase.getTestcaseName() + "_" + System.currentTimeMillis();
			MDC.put("logFileName", logFileName);
			List<InfsResult> infreslist = new ArrayList<InfsResult>();
			List<TestCaseItem> testcaseitemLst = testcaseservice.findTestCaseItem(String.valueOf(testcase.getId()));
			// 将config信息压入map中
			readConfig(testcase.getProjectId());
			// 获取每个接口
			TestCaseResult caseresult = new TestCaseResult();
			caseresult.setName(logFileName);
			caseresult.setExcuteResult("OK");
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
								if (result.getExcuteResult().equalsIgnoreCase("pok")) {
									caseresult.setExcuteResult("POK");
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
				caseresult.setExcuteResult("POK");
			}  catch (Exception e) {
				LogManager.getLogger().logError("excute infs throw a exception", e);
				caseresult.setExcuteResult("POK");
			}
			// 将case存入数据库
			caseresult.setEndtime(TimeUtil.getCurrentTime());
			TestCaseResult newcaseresult = resultservice.saveResult(caseresult);
			//存入logitem中去
			List<LogTreeItem> projectItemList=logtreeitemservice.findItemByProjectIdandItemType(LogItemType.PROJECT, testcase.getProjectId());
			LogTreeItem caseTreeItem=new LogTreeItem();
			caseTreeItem.setHaschildren(false);
			caseTreeItem.setItemType(LogItemType.TESTCASELOG);
			caseTreeItem.setParentid(String.valueOf(projectItemList.get(0).getId()));
			caseTreeItem.setText(caseresult.getName());
			String url="../jtest/html/testcaselogview.html?projectId="+testcase.getProjectId()+"&testcaseresultId="+newcaseresult.getId();
			caseTreeItem.setUrl(url);
			logtreeitemservice.createItem(caseTreeItem);
			for (InfsResult res : infreslist) {
				res.setTestCaseResultId(String.valueOf(newcaseresult.getId()));
				infresservice.saveResult(res);
			}
			MDC.remove("logFileName");
		}

		/**
		 * 执行验证点
		 */
		private String excuteAssert(String assertlist) {
			List<AssertModel> resultlist = new ArrayList<AssertModel>();
			LogManager.getLogger().logInfo("验证点列表:" + assertlist);
			if (StringUtils.isEmpty(assertlist)) {
				// 若未填入验证点默认OK
				return "OK";
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
			if (RunTimeConfig.configmap.containsKey(this.threadName)) {
				RunTimeConfig.configmap.remove(this.threadName);
			}

			List<ConfigInfo> configinfolist = configservice.findByprojectId(projectId);

			Map<String, String> hashmap = new HashMap<String, String>();
			for (ConfigInfo info : configinfolist) {
				hashmap.put(info.getKeyname(), info.getValuestr());
			}
			hashmap.put("projectId", projectId);
			RunTimeConfig.configmap.put(this.threadName, hashmap);
		}

		private String getMapValue(String key) {
			return RunTimeConfig.configmap.get(threadName).get(key);
		}

		private void putMapValue(String key, String value) {
			RunTimeConfig.configmap.get(threadName).put(key, value);
		}

		private InfsResult HttpExcute(ProtocalInfo protocalinfo, InfsInfo inf, TestCaseItem item) {
			InfsResult infres = new InfsResult();
			infres.setExcuteResult("OK");
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
						
						String url = "";
						// 修改转换
						if (getMapValue("domain") != null) {
							url = getMapValue("domain") + inf.getInfsurl();
						} else {
							url = inf.getInfsurl();
						}
						String presentparamaterstr = ParamReplaceUtil.Replace(item.getPresetparameter());
						// 压入map中
						
						if(!StringUtils.isEmpty(presentparamaterstr)){
							Map<String, Object> presentparamater = gson.fromJson(presentparamaterstr,
									new TypeToken<Map<String, Object>>() {
									}.getType());
							for (Map.Entry<String, Object> entry : presentparamater.entrySet()) {
								putMapValue(entry.getKey(), entry.getValue().toString());
							}
						}
						
						url = ParamReplaceUtil.Replace(url);
						String headerinfo = ParamReplaceUtil.Replace(inf.getHeaderinfo());
						String paramterinfo = ParamReplaceUtil.Replace(inf.getParamterinfo());
						Map<String, Object> header = gson.fromJson(headerinfo, new TypeToken<Map<String, Object>>() {
						}.getType());
						RequestParamter parameter = gson.fromJson(paramterinfo, RequestParamter.class);
						if(parameter==null){
							parameter=new RequestParamter();
						}
						res = (ResponseResult) method.invoke(null, new Object[] { url, header, parameter });
						LogManager.getLogger().logInfo(inf.getInfsname()+"执行结果为:"+gson.toJson(res, ResponseResult.class));
						// 将执行结果压入map中
						putMapValue(inf.getInfsname(), gson.toJson(res, ResponseResult.class));
						infres.setEndtime(TimeUtil.getCurrentTime());
						break;
					}
				}
				// 执行assert验证点
				String assertList = excuteAssert(item.getAssertlist());
				infres.setAssertresultlist(assertList);
				infres.setInfsName(inf.getInfsname());
				// 获取结果
				if (!assertList.equals("OK")) {
					List<AssertModel> list = gson.fromJson(assertList, new TypeToken<List<AssertModel>>() {
					}.getType());
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).isAssertResult().equalsIgnoreCase("pok")) {
							infres.setExcuteResult("pok");
						}
					}
				}
			} catch (Exception e) {
				LogManager.getLogger().logError("excute interface" + item.getCallbackname() + "error", e);
				infres.setEndtime(TimeUtil.getCurrentTime());
				infres.setExcuteResult("pok");
			}
			return infres;
		}

	}
}
