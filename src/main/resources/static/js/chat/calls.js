var voiceBtn = document.querySelector(".call");
var videoBtn = document.querySelector(".video");
var currentId = document.querySelector("#currentUser").value;
var voiceVideoPanel = document.querySelectorAll("#remoteVideo #localVideo");

var peer = new Peer(currentId);
peer.on('open', function(id) {
  console.log('My peer ID is: ' + id);
});

voiceBtn.addEventListener('click', function(){
navigator.mediaDevices.getUserMedia({ audio: true })
        .then(stream => {
            const audio = new Audio();
            audio.srcObject = stream;
            audio.play();
            peer.call('1', stream);
        });

    peer.on('call', call => {
        call.answer();
        call.on('stream', remoteStream => {
            const audio = document.createElement('audio');
            audio.srcObject = remoteStream;
            audio.play();
        });
    });
});
// Media constraints
const mediaConstraints = {
  video: true,
  audio: false
};

// Local stream
var localStream;
videoBtn.addEventListener('click', function(){
//voiceVideoPanel.classList.add("open");
    navigator.mediaDevices.getUserMedia(mediaConstraints)
       .then((stream) => {
         // Save local stream
         localStream = stream;

         // Display local video
         const localVideo = document.getElementById('localVideo');
         localVideo.srcObject = localStream;

         // Initiate call
         const call = peer.call('1', stream);

         // Answer call
         call.answer(localStream);

         // Display remote video
         const remoteVideo = document.getElementById('remoteVideo');
         call.on('stream', (remoteStream) => {
           remoteVideo.srcObject = remoteStream;
         });

         // Handle call close
         call.on('close', () => {
           remoteVideo.srcObject = null;
         });
       })
       .catch((err) => {
         console.error('Error starting video call:', err);
       });
});