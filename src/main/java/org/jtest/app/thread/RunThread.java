package org.jtest.app.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.MDC;
import org.jtest.app.config.BeanContext;
import org.jtest.app.dao.config.ConfigDao;
import org.jtest.app.log.LogManager;
import org.jtest.app.model.asserts.AssertModel;
import org.jtest.app.model.config.ConfigInfo;
import org.jtest.app.model.infs.InfsInfo;
import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.model.protocal.ProtocalInfo;
import org.jtest.app.model.requests.RequestParamter;
import org.jtest.app.model.requests.ResponseResult;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.model.testcase.TestCaseResult;
import org.jtest.app.runtime.RunTimeConfig;
import org.jtest.app.service.config.ConfigService;
import org.jtest.app.service.infs.InfsService;
import org.jtest.app.service.protocal.ProtocalService;
import org.jtest.app.service.testcase.TestCaseResultService;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.util.ParamReplaceUtil;
import org.jtest.app.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 采用内部类进行注入
 * @author Administrator
 *
 */
public class RunThread{
	private String threadName;
	private Gson gson = new Gson();

	// 构造函数
	public RunThread(String threadName) {
		this.threadName = threadName;
	}

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
	

	/**
	 * 执行testcase
	 */
	public void runTestCase(TestCase testcase) {
		// 获取所有需要执行的接口
		String logFileName = testcase.getTestcaseName() + "_" + System.currentTimeMillis();
		MDC.put("logFileName", logFileName);
        List<InfsResult> infreslist=new ArrayList<InfsResult>();
		List<TestCaseItem> testcaseitemLst = testcaseservice.findTestCaseItem(String.valueOf(testcase.getId()));
		// 将config信息压入map中
		readConfig(testcase.getProjectId());
		// 获取每个接口
		TestCaseResult caseresult = new TestCaseResult();
		caseresult.setLogfileurl(logFileName + ".log");
		caseresult.setStarttime(TimeUtil.getCurrentTime());
		caseresult.setProjectId(testcase.getProjectId());
		try {
			for (int i = 0; i < testcaseitemLst.size(); i++) {
				InfsInfo inf = infsservice.findInfs(testcase.getProjectId(), testcaseitemLst.get(i).getCallbackname());
				ProtocalInfo protocalinfo = service.findProtocal(inf.getProtocol(), inf.getSendType());
				// 判断是否为第三方组件
				if (protocalinfo.isThird3rd()) {

				} else {
					if (protocalinfo.getLanguageName().equalsIgnoreCase("java")) {
						if(protocalinfo.getProtocalname().equalsIgnoreCase("HTTP")){
							InfsResult result=HttpExcute(protocalinfo,inf,testcaseitemLst.get(i).getAssertlist());
							if(result.getExcuteResult().equalsIgnoreCase("pok")){
								caseresult.setExcuteResult("POK");
							}
							infreslist.add(result);
						}else{
							System.out.println("暂不支持的协议");
						}

					} else {
						System.out.println("内置程序中不支持其他语言");
					}
				}
                //入库讲infsresult
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LogManager.getLogger().logError("excute infs throw a exception", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogManager.getLogger().logError("excute infs throw a exception", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			LogManager.getLogger().logError("excute infs throw a exception", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			LogManager.getLogger().logError("excute infs throw a exception", e);
		} catch (Exception e) {
			LogManager.getLogger().logError("excute infs throw a exception", e);
		}
		caseresult.setEndtime(TimeUtil.getCurrentTime());
		// 将case存入数据库
		caseresult.setEndtime(TimeUtil.getCurrentTime());
		caseresult=resultservice.saveResult(caseresult);
		for(InfsResult res:infreslist){
			
		}
		
		MDC.remove("logFileName");
	}

	/**
	 * 执行testcase
	 */
	public void runTestSuites() {

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
	private void readConfig(String projectId){
		if(RunTimeConfig.configmap.containsKey(this.threadName)){
			RunTimeConfig.configmap.remove(this.threadName);
		}
		
		List<ConfigInfo> configinfolist=configservice.findByprojectId(projectId);
		
		Map<String,String> hashmap=new HashMap<String,String>();
		for(ConfigInfo info:configinfolist){
			hashmap.put(info.getKeyname(), info.getValuestr());
		}
		hashmap.put("projectId", projectId);
		RunTimeConfig.configmap.put(this.threadName, hashmap);
	}
	
	
	private String getMapValue(String key){		
		return RunTimeConfig.configmap.get(threadName).get(key);
	}
	private void putMapValue(String key,String value){		
		RunTimeConfig.configmap.get(threadName).put(key,value);
	}
	
	private InfsResult HttpExcute(ProtocalInfo protocalinfo,InfsInfo inf,String asserts) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
		// 加载方法运行
		Class clz = Class.forName(protocalinfo.getClassFileUrl());
		// 加载方法
		Method[] methods = clz.getMethods();
		// 将结果写入数据库
		InfsResult infres = new InfsResult();
		ResponseResult res = null;
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(protocalinfo.getMethodName())) {
				// 执行方法
				infres.setStarttime(TimeUtil.getCurrentTime());
				String url="";
				// 修改转换
				if(getMapValue("domain")!=null){
					url=getMapValue("domain")+inf.getInfsurl();
				}else{
					url=inf.getInfsurl();
				}
				url=ParamReplaceUtil.Replace(url);
				String headerinfo=ParamReplaceUtil.Replace(inf.getHeaderinfo());
				String paramterinfo=ParamReplaceUtil.Replace(inf.getParamterinfo());
				Map<String, Object> header = gson.fromJson(headerinfo,
						new TypeToken<Map<String, Object>>() {
						}.getType());
				RequestParamter parameter = gson.fromJson(paramterinfo, RequestParamter.class);	
				res = (ResponseResult) method.invoke(null,
						new Object[] { url, header, parameter });
				infres.setEndtime(TimeUtil.getCurrentTime());
				break;
			}
		}
		// 执行assert验证点
		String assertList = excuteAssert(asserts);
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
		return infres;
	}
	
	
}
