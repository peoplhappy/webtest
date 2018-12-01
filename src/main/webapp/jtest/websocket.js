/**
 * websocket连接协议
 */
uid=uuid()
var tip
console.log(uid)
var stompClient = null;
// 加载完浏览器后 调用connect（），打开双通道
$(function() {
	// 打开双通道
	connect()
})
// 强制关闭浏览器 调用websocket.close（）,进行正常关闭
window.onunload = function() {
	disconnect()
}
function connect() {
	//获取ip

	var socket = new SockJS('http://localhost:8080/websocket'); // 连接SockJS的endpoint名称为"websocket"
	stompClient = Stomp.over(socket);// 使用STMOP子协议的WebSocket客户端
	stompClient.connect({}, function(frame) {// 连接WebSocket服务端
		console.log('Connected:' + frame);
		// 通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息
		stompClient.subscribe('/user/' + uid + '/message', function(message){  
	         console.log(message.body)
	         if(tip==null){
	        	 tip=$.ligerDialog.tip({  title: '提示信息',content:message.body });
	         }else{
	        	 tip.close()
	        	 tip=$.ligerDialog.tip({  title: '提示信息',content:message.body });
	         }
	         
	    });  
	});
}

// 关闭双通道
function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("Disconnected");
}
function showResponse(message) {
	var response = $("#response");
	response.append("<p>" + message.userName + "</p>");
}