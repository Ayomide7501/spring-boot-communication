var chatItem = document.querySelectorAll('.chat');
for(var i=0; i<chatItem.length; i++){
chatItem[i].addEventListener('click', function(){
var id = this.id;
window.location.href = "/chatroom/" + id;
});
}
