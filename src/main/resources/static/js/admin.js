var tabButtons = document.querySelectorAll(
  ".tabContainer .buttonContainer button"
);
var tabPanels = document.querySelectorAll(".tabContainer  .tabPanel");
var addNew = document.querySelector("#add-new");
var backDrop = document.querySelector(".back-drop");
var modal = document.querySelector(".modal");
var accType = document.querySelector("#type");
var roleTypeDiv = document.querySelector(".role");
var submitBtn = document.querySelector("#addNewSubmit");
var addNewForm = document.querySelector("#addNewForm");
var toggleBtn = document.querySelector("#toggle_hamburger");
var sideBar = document.querySelector(".side-bar");
var logOut = document.querySelector("#logout");
var settings = document.querySelector("#settings");
var newRoom = document.querySelector("#newRoom");
var createRoom = document.querySelector(".create-room");
var createRoomSubmit = document.querySelector("#createRoomBtn");
var roomNameInput = document.querySelector("#room-name");
var roomDescInput = document.querySelector("#room-desc");
var selectClient = document.querySelector("#select-client option");
var selectStaff = document.querySelector("#select-staff option");

(function () {
  var accValue = accType.value;
  if (accValue != "staff") {
    roleTypeDiv.classList.remove("open");
  } else {
    roleTypeDiv.classList.add("open");
  }
})();

toggleBtn.addEventListener("click", function () {
  backDrop.classList.add("open");
  sideBar.classList.add("open");
});

addNew.addEventListener("click", function () {
  backDrop.classList.add("open");
  modal.classList.add("open");
});

backDrop.addEventListener("click", function () {
  backDrop.classList.remove("open");
  modal.classList.remove("open");
  sideBar.classList.remove("open");
  createRoom.classList.remove("open");
});

accType.addEventListener("change", function () {
  if (accType.value == "staff") {
    roleTypeDiv.classList.add("open");
  } else {
    roleTypeDiv.classList.remove("open");
  }
});

newRoom.addEventListener("click", function () {
  createRoom.classList.add("open");
  appendOptions();
});
function showPanel(panelIndex, colorCode) {
  tabButtons.forEach(function (node) {
    node.style.backgroundColor = "";
    node.style.color = "";
  });
  tabButtons[panelIndex].style.backgroundColor = colorCode;
  tabButtons[panelIndex].style.color = "white";
  tabPanels.forEach(function (node) {
    node.style.display = "none";
  });
  tabPanels[panelIndex].style.display = "block";
  //tabPanels[panelIndex].style.backgroundColor=colorCode;
}
showPanel(0, "#0d6efd");
function validateForm() {
  var formValid = true;
  var formElements = addNewForm.elements;
  for (var i = 0; i < formElements.length; i++) {
    if (formElements[i].value === "") {
      formValid = false;
      break;
    }
  }
  return formValid;
}

function sendData() {
  var formData = $("#addNewForm").serialize();
  $.ajax({
    type: "POST",
    url: "/register",
    data: formData,
    success: function () {},
    error: function (xhr, status, error) {
      alert("ajax add new user error" + status + " " + error);
    },
  });
}
submitBtn.addEventListener("click", function () {
  //        if(validateForm){
  //        sendData();
  //        }
  sendData();
});

(function () {
  $.ajax({
    type: "GET",
    url: "/getAllUsers",
    success: function (users) {
      for (var i = 0; i < users.length; i++) {
        var checked;
        if (users[i].isActive) {
          checked = "checked";
        }
        if (users[i].roles[0].name == "ROLE_USER") {
          var singleUser =
            '<div class="user-item"><img class="user-image" src="/img/blank-profile-picture.png" alt="profile-image" srcset=""><h3 class="user-name">' +
            users[i].name +
            '</h3><label class="switch"><input type="checkbox" ' +
            checked +
            '><span class="slider round"></span></label><img src="/img/delete_icon.jpg"></div>';
          $("#client_item").append(singleUser);
        } else {
          var singleUser =
            '<div class="user-item"><img class="user-image" src="/img/blank-profile-picture.png" alt="profile-image" srcset=""><h3 class="user-name">' +
            users[i].name +
            '</h3><label class="switch"><input type="checkbox" ' +
            checked +
            '><span class="slider round"></span></label><img src="/img/delete_icon.jpg"></div>';
          $("#staff_item").append(singleUser);
        }
      }
    },
    error: function (xHr, status, error) {
      alert("ajax get all users error" + status + " " + error);
    },
  });
})();

logout.addEventListener("click", function () {
  window.location.href = "/logout";
});

createRoomSubmit.addEventListener("click", function () {
  if (roomNameInput.value !== "" && roomDescInput.value !== "") {
    $.ajax({
      type: "POST",
      url: "/createroom",
      data: {name: roomNameInput.value, desc: roomDescInput.value },
      success: function () {
        alert("Data created");
      },
      error: function (xhr, status, error) {
        alert("ajax add new user error" + status + " " + error);
      },
    });
  }
});

function appendOptions() {
  $.ajax({
    type: "GET",
    url: "/getAllUsers",
    success: function (users) {
      for (var i = 0; i < users.length; i++) {
        if (users[i].isActive) {
        }
        if (users[i].roles[0].name == "ROLE_USER") {
          let option1 = document.createElement("option");
          option1.text = users[i].name;
          option1.value = users[i].name;
          $("#select-client").append(option1);
        } else {
          let option = document.createElement("option");
          option.text = users[i].name;
          option.value = users[i].name;
          $("#select-staff").append(option);
        }
      }
    },
    error: function (xHr, status, error) {
      console.log("");
    },
  });
}
//      const toggle = document.querySelector('.toggle-button input[type="checkbox"]');
//      const label = document.querySelector('.toggle-button');
//      toggle.addEventListener('click', () => {
//        label.classList.toggle('active');
//        });
