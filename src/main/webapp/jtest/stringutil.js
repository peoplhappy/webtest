/**
 * 提取string中数据
 */
function GetQueryString(str,name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = str.match(reg);
	// search,查询？后面的参数，并匹配正则
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}