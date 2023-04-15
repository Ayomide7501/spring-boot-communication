var sendBtn = document.querySelector("#record");
var inputField = document.querySelector("#message-input");
var currentId = document.querySelector("#currentUser").value;
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

var currentUserId = document.querySelector('#currentUser').value;
  var camBtn = document.querySelector('#image-input');
  camBtn.addEventListener('change', function(){
  var formData = new FormData();
  var fileInput = $("#image-input")[0];
  formData.append("type", "image");
  formData.append("roomId", "1");
  formData.append("id", "1");
  formData.append("content", "/message-image/");
  formData.append("file", fileInput.files[0]);
  $.ajax({
        url: "/chat/sendmessage",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function(data) {
          // handle success response
        },
        error: function(xhr, textStatus, errorThrown) {
          // handle error response
        }
      });
  });


sendBtn.addEventListener('click', function(){
alert("id" + currentId);
    const url = window.location.href;
    const segments = url.split("/");
    const secondSegment = segments[4];

    if(inputField.value !== ""){
    var chatMessage = {
        content: inputField.value,
        messageType: "text",
        senderId: currentId,
        time: getTime()
    };

    stompClient.send('/app/chat/'+ secondSegment +'/send', {}, JSON.stringify(chatMessage));
    $.ajax({
    type: "POST",
    url: "/chat/sendmessage",
    data: {type: "text", content: inputField.value, roomId: secondSegment, id: currentId},
    success: function(data){
    inputField.value = "";
    },
    error: function(xhr, status, error){
            console.log("status: " + status +"  error: " + error);
            alert(error);
    }
    });
    }
});


function appendData(data){
for(var i=0; i<data.length; i++){
if(data[i].senderId == currentId){
switch(data[i].messageType){
case "text":
    var chatDiv = '<div class="chat-item chat-item-sender"><div class="item item-sender"><h5 class="sender">You</h5><p>' +data[i].content +'</p><p class="sender-time">'+ data[i].time +'</p></div><img src="/img/blank-profile-picture.png" alt=""></div>';
    $(".chat-group").append(chatDiv);
break;
case "image":

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
if(data.senderId == currentId){
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
