/**
 * 根据projectid获取新数据
 */
var projecticon="../lib/ligerUI/skins/Aqua/images/tree/project_source_folder.gif"
var foldericon="../lib/ligerUI/skins/Aqua/images/tree/folder.gif"
var testcaseicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"
var testsuiteicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"	
var configicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"
var infconfigicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"
var protocalconfigicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"
var iconmap={
		"PROJECT":projecticon,
		"FOLDER":foldericon,
		"TESTCASE":testcaseicon,
		"TESTSUITS":testsuiteicon,
		"CONFIGFILE":configicon,
		"INFSCONFIGFILE":infconfigicon,
		"PROTOCALCONFIGFILE":protocalconfigicon
}
function getItemLst(parentid) {
	var url = "http://"+window.location.host+"/tree/getItemlst?parentid="+parentid
	var data = []
	$.ajax({
		url : url,
		async : false,
		success : function(result) {
			for ( var i in result) {
				var o = {
					text : result[i]["text"],
					id : result[i]["id"],
					parentid : result[i]["parentid"],
					children : result[i]["haschildren"],
					itemType : result[i]["itemType"],
					icon:iconmap[result[i]["itemType"]],
					projectId:result[i]["projectId"],
					url : result[i]["url"],
				}
				data.push(o)
			}
            }
	});
	console.log("获取的树节点为" + data)
	return data
}
function getChildren(data) {
    var childdata=[]
	if(data.children){
		childdata=getItemLst(data.id)
	}
	return childdata;
}
function getItem(id) {
	var url = "http://"+window.location.host+"/tree/getItem" + "?id=" + id
	var data;
	$.ajax({
		url : url,
		async : false,
		success : function(result) {
			data = result
			if (data.haschildren) {
				data.children = true
			}
		}
	});
	return data
}

function createItem(parent, name, itemType) {
	var url = "http://"+window.location.host+"/tree/createItem"
	// 增加判断同级节点是否有重名对象，若有禁止新增
	if (checkRepeat(parent,name)) {
		$.ligerDialog.error('同级目录下有同名文件，请检查')
		return
	}
	var data;
	var projectName;
	if(parent.itemType=="PROJECT"){
		projectName=parent.id
	}else if(parent.itemType=="FOLDER"){
		projectName=parent.projectId
	}
	$.ajax({
		url : url,
		async : false,
		type : "POST",
		data : {
			"parentid" : parent.id,
			"projectId" : projectName,
			"itemType" : itemType,
			"haschildren":false,
			"text" : name
		},
		success : function(result) {
			var manager = $("#tree1").ligerGetTreeManager();
			var parentnode = manager.getNodeDom(parent)
			result.children = result.haschildren
			result.icon=iconmap[result["itemType"]]
			// 更新parent的children
			var children = parent.children
			if (children == undefined) {
				children = [ result ]
			} else if (children == null) {
				children = [ result ]
			} else if (children == true) {
				children = [ result ]
			} else if (children == false) {
				children = [ result ]
			} else {
				children.push(result)
			}
			//更新父节点的haschilder值
			parent.children = children
			manager.update(parentnode, [ parent ])
			//manager.append(parentnode, [ result ])

			manager.reloadNode(parent, children)
		}
	});
	//刷新tree2
	var manager2 = $("#tree2").ligerGetTreeManager();
	manager2.refreshTree()
	return data
}

function deleteitem(parent, node) {
	var url = "http://"+window.location.host+"/tree/deleteItem"
	$.ajax({
		url : url,
		async : false,
		type : "DELETE",
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			"parentid" : node.parentid,
			"projectName" : node.projectName,
			"itemType" : node.itemType,
			"text" : node.text,
			"id" : node.id,
			"url" : node.url
		}),
		success : function(result) {
			var manager = $("#tree1").ligerGetTreeManager();
			var parentnode = manager.getNodeDom(parent)
			// 更新父节点信息
			// 同时刷新父节点数据
			// 更新parent的children
			var children = parent.children
			index = children.indexOf(node)
			if (index > -1) {
				children.splice(index, 1);
			}
			if(children.length<1){
				parent.children = false
			}else{
				parent.children=children
			}
			
			manager.update(parentnode, [ parent ])
			manager.remove(node)
			manager.reloadNode(parent, children)
		}
	});
}

function modifyItemName(parent,node,text){
	var url = "http://"+window.location.host+"/tree/modifyItem"
	if (checkRepeat(parent,name)) {
		$.ligerDialog.error('同级目录下有同名文件，请检查')
		return
	}
	$.ajax({
		url : url,
		async : false,
		type : "PUT",
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			"parentid" : node.parentid,
			"projectName" : node.projectName,
			"itemType" : node.itemType,
			"text" : text,
			"id" : node.id,
			"childlist" : node.childlist,
			"url" : node.url
		}),
		success : function(result) {
			var manager = $("#tree1").ligerGetTreeManager();
			var parentnode = manager.getNodeDom(parent)
			// 更新父节点信息
			// 同时刷新父节点数据
			// 更新parent的children
			var children = parent.children
			for(var i in children){
				if(node.id==children[i].id){
					children[i].text=text
				}
			}
			parent.children = children
			manager.update(parentnode, [ parent ])
			manager.reloadNode(parent, children)
		}
	});
}

function modifyitemposition(){
	
}


function checkRepeat(parent, text) {
	var result = false
	var children = parent.children
	for ( var i in children) {
		if (children[i].text == text) {
			result = true
			break
		}
	}
	return result
}
