/**
 * 根据projectid获取新数据
 */
var projecticon="../lib/ligerUI/skins/Aqua/images/tree/project_source_folder.gif"
var testcaseicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"
var testsuiteicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"	
var iconmap={
		"PROJECT":projecticon,
		"TESTCASELOG":testcaseicon,
		"TESTSUITLOG":testsuiteicon,
}
function getlogItemLstByType(type) {
	var url = "http://localhost:8080/testresult/tree/getItemByType?itemType="+type
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
function getLogChildren(data) {
    var childdata=[]
	if(data.children){
		childdata=getLogItemListByParent(data.id)
	}
	return childdata;
}
function getLogItemListByParent(id) {
	var url = "http://localhost:8080/testresult/tree/getItemlst?parentid=" + id
	var data=[];
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
	return data
}


