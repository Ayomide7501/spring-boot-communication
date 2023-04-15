var sendBtn = document.querySelector("#cam");
var inputField = document.querySelector("#message-input");
var currentId = document.querySelector("#currentUser");
var element = document.querySelector('.chat-group');
element.scrollTop = element.scrollHeight;

var socket = new SockJS('/elevated-stomp-endpoint');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    const url = window.location.href;
    const segments = url.split("/");
    const roomId = segments[4];
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat/'+ roomId, function(message) {
    let data = JSON.parse(message.body);
    stompReceive(data);
    });
});

(function(){
const url = window.location.href;
const segments = url.split("/");
const secondSegment = segments[4];
$.ajax({
type: "GET",
url: "/chatroom/getAllMessages",
data: {roomId: secondSegment},
success: function(data){
appendData(data);
},
error: function(){
}
});
})();

sendBtn.addEventListener('click', function(){
    const url = window.location.href;
    const segments = url.split("/");
    const secondSegment = segments[4];

    if(inputField.value !== ""){
    var chatMessage = {
        content: inputField.value,
        messageType: "text",
        senderId: currentId.value,
        time: getTime()
    };
    stompClient.send('/app/chat/'+ secondSegment +'/send', {}, JSON.stringify(chatMessage));
    $.ajax({
    type: "POST",
    url: "/chat/sendmessage",
    data: {type: "text", content: inputField.value, roomId: secondSegment, id: currentId.value},
    success: function(data){
    inputField.value = "";
    }/*,
    error: function(xhr, status, error){
            alert("status: " + status +"  error: " + error);
    }*/
    });
    }
});
function appendData(data){
for(var i=0; i<data.length; i++){
if(data[i].senderId == currentId.value){
switch(data[i].messageType){
case "text":
    var chatDiv = '<div class="chat-item chat-item-sender"><div class="item item-sender"><h5 class="sender">You</h5><p>' +data[i].content +'</p><p class="sender-time">'+ data[i].time +'</p></div><img src="/img/blank-profile-picture.png" alt=""></div>';
    $(".chat-group").append(chatDiv);
break;
default:
break;
}
}else{
switch(data[i].messageType){
case "text":
var chatDiv = '<div class="chat-item chat-item-receiver"><img src="/img/blank-profile-picture.png" alt=""><div class="item item-receiver"><h5 id="sender">You</h5><p id="body">'+data[i].content+'</p><p class="sender-time">'+data[i].time+'</p></div></div>';
$(".chat-group").append(chatDiv);
break;
default:
break;
}}
}
}
function stompReceive(data){
if(data.senderId == currentId.value){
    switch(data.messageType){
    case "text":
        var chatDiv = '<div class="chat-item chat-item-sender"><div class="item item-sender"><h5 class="sender">You</h5><p>' +data.content +'</p><p class="sender-time">'+ data.time +'</p></div><img src="/img/blank-profile-picture.png" alt=""></div>';
        $(".chat-group").append(chatDiv);
    break;
    default:
    break;
    }
    }else{
    switch(data.messageType){
    case "text":
    var chatDiv = '<div class="chat-item chat-item-receiver"><img src="/img/blank-profile-picture.png" alt=""><div class="item item-receiver"><h5 id="sender">You</h5><p id="body">'+data.content+'</p><p class="sender-time">'+data.time+'</p></div></div>';
    $(".chat-group").append(chatDiv);
    break;
    default:
    break;
    }}
}
function getTime(){
let currentTime = new Date();
let hours = currentTime.getHours().toString().padStart(2, '0');
let minutes = currentTime.getMinutes().toString().padStart(2, '0');
let formattedTime = hours + ':' + minutes;
return formattedTime;
}
