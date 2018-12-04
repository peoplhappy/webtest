/**
 * 定义相关menu
 */

function getfoldermenu() {
	return $.ligerMenu({
		top : 100,
		left : 100,
		width : 120,
		items : [ {
			text : '新建文件夹',
			click : createFolder
		}, {
			text : '删除文件夹',
			click : deleteFolder
		}, {
			text : '修改名称',
			click : modifyname
		}, {
			line : true
		}, {
			text : '创建测试用例',
			click : createTestCase
		}, {
			line : true
		}, {
			text : '创建测试集',
			click : createTestSuites
		}

		]
	})
}
/**
 * 获取测试用例右键菜单
 * 
 * @returns
 */
function gettestcasemenu() {
	return $.ligerMenu({
		top : 100,
		left : 200,
		width : 200,
		items : [ {
			text : '查看测试用例详情',
			click : viewtestcase
		}, {
			text : '修改名称',
			click : modifyname
		}, {
			line : true
		}, {
			text : '删除测试用例',
			click : deleteTestCase
		}, {
			line : true
		}, {
			text : '运行测试用例',
			click : runtestcase
		}, {
			text : '调试测试用例',
			click : deleteFolder
		}

		]
	})
}

/**
 * 获取测试集右键菜单
 * 
 * @returns
 */
function gettestsuitmenu() {
	return $.ligerMenu({
		top : 100,
		left : 200,
		width : 200,
		items : [ {
			text : '查看测试集详情',
			click : viewtestsuits
		}, {
			text : '修改名称',
			click : modifyname
		}, {
			line : true
		}, {
			text : '删除测试集',
			click : deleteFolder
		}, {
			line : true
		}, {
			text : '运行测试集',
			click : deleteFolder
		}, {
			text : '调试测试集',
			click : deleteFolder
		}

		]
	})
}

/**
 * 获取根目录菜单，新建项目，删除项目,查看项目详情，统计下面用例数量，测试集数量
 * 
 * @returns
 */
function getprojectmenu() {
	return $.ligerMenu({
		top : 100,
		left : 200,
		width : 200,
		items : [ {
			text : '新建项目',
			click : createProject
		} ]
	})
}
// 创建文件夹
function createFolder(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)

	$.ligerDialog.prompt('请输入文件夹名称', function(yes, value) {
		if (yes) {
			createItem(node, value, "FOLDER");
		}
	});
}

function deleteFolder(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)
	var parent = manager.getParent(node)
	$.ligerDialog.confirm('同时将删除文件夹下的所有内容，是否确定', function(yes) {
		if (yes) {
			deleteitem(parent, node);
		}

	});

}
function createTestCase(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)

	$.ligerDialog.prompt('请输入测试用例名称', function(yes, value) {
		if (yes) {
			createItem(node, value, "TESTCASE");
		}
	});
}
function deleteTestCase(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)
	var parent = manager.getParent(node)
	$.ligerDialog.confirm('同时将删除测试用例下的已进行配置的所有内容，是否确定', function(yes) {
		if (yes) {
			deleteitem(parent, node);
		}

	});
}
function createTestSuites(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)

	$.ligerDialog.prompt('请输入测试集名称', function(yes, value) {
		if (yes) {
			createItem(node, value, "TESTSUITS");
		}
	});
}
function deleteTestSuites(e) {
	console.log(foldermenu)
}

function modifyname(e) {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(foldermenu.menuNodeID)
	var parent = manager.getParent(node)
	$.ligerDialog.prompt('请输入新名称', node.text, function(yes, value) {
		if (yes) {
			modifyItemName(parent, node, value);
		}
	});
}

function viewtestcase() {

}

function viewtestsuits() {

}

function createProject() {
	var manager = $("#tree1").ligerGetTreeManager();
	var node = manager.getDataByID(projectmenu.menuNodeID)

	$.ligerDialog.prompt('请输入工程名称', function(yes, value) {
		if (yes)
			createItem(node, value, "PROJECT");
	});
}

function viewProject() {

}
/**
 * 运行测试用例
 */
function runtestcase() {
	var url = "http://"+window.location.host+"/run/runtestcase"
	var postdata=[]
	$.ajax({
		url : url,
		async : false,
		data : postdata,
		success : function(result) {
			
		}
	});

}
