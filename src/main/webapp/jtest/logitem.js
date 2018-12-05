/**
 * 根据projectid获取新数据
 */
var projecticon="../lib/ligerUI/skins/Aqua/images/tree/project_source_folder.gif"
var testcaseicon="../lib/ligerUI/skins/icons/right.gif"
var testcaseerricon="../lib/ligerUI/skins/icons/candle.gif"
var testsuiteicon="../lib/ligerUI/skins/Aqua/images/tree/tree-leaf.gif"	
var iconmap={
		"PROJECT":projecticon,
		"TESTCASELOG":testcaseicon,
		"TESTSUITLOG":testsuiteicon,
		"TESTCASEERR":testcaseerricon
}
function getlogItemLstByType(type) {
	var url = "http://"+window.location.host+"/testresult/tree/getItemByType?itemType="+type
	var data = []
	$.ajax({
		url : url,
		async : false,
		success : function(result) {
			for ( var i in result) {
				if(result[i]["excuteresult"]=="POK"){
					icon=iconmap["TESTCASEERR"]
				}else{					
					icon=iconmap[result[i]["itemType"]]
				}
				var o = {
					text : result[i]["text"],
					id : result[i]["id"],
					parentid : result[i]["parentid"],
					children : result[i]["haschildren"],
					itemType : result[i]["itemType"],
					icon:icon,
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
	var url = "http://"+window.location.host+"/testresult/tree/getItemlst?parentid=" + id
	var data=[];
	$.ajax({
		url : url,
		async : false,
		success : function(result) {
			for ( var i in result) {
				if(result[i]["excuteresult"]=="POK"){
					icon=iconmap["TESTCASEERR"]
				}else{					
					icon=iconmap[result[i]["itemType"]]
				}
				var o = {
					text : result[i]["text"],
					id : result[i]["id"],
					parentid : result[i]["parentid"],
					children : result[i]["haschildren"],
					itemType : result[i]["itemType"],
					icon:icon,
					projectId:result[i]["projectId"],
					url : result[i]["url"],
				}
				data.push(o)
			}	
		}
	});
	return data
}


