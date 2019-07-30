function checkData(form) {
  var formD = new FormData(form);
    if (checkIsFieldEmpty(formD.get('firstname'))) {
      displayAlert('Field for the name is not filled');
  } else if (checkIsFieldEmpty(formD.get('lastname'))) {
      displayAlert('Field for the surname is not filled');
  } else if (checkIsFieldEmpty(formD.get('email'))) {
      displayAlert('Email is not specified');
  } else if (checkAge(formD.get('age'))) {
    displayAlert('Invalid age specified');
  } else if (checkPassword(formD.get('password')) === -1) {
    displayAlert('Password is not strong enough');
  } else {
    form.submit();
  }
}

function checkIsFieldEmpty(name) {
  return name == "";
}

function checkAge(age) {
  return age < 6;
}
function checkPassword(password) {
    /*
    Notes:
        (?=.*[0-9]) - string contains at least one number;
        (?=.*[a-z]) - string contains at least one latin letter at lower case;
        [0-9a-zA-Z!@#$%^&*]{6,} - string contains at least 6 symbols.
    
    */
    var myPattern = /(?=.*[0-9])(?=.*[a-z])[0-9a-zA-Z!@#$%^&*]{6,}/g;
    return password.search(myPattern);  
}

var doc = document;

function displayAlert(message) {
    doc.getElementById('alert').style.display = 'block';
    doc.getElementById('alert').innerHTML = message;
}

var cancellButton = doc.getElementById('cancell');
var firstName = doc.getElementById('firstname');
var lastName = doc.getElementById('lastname');
var mail = doc.getElementById('email');
var yearsOld = doc.getElementById('age');
var addr = doc.getElementById('address');
var phoneNum = doc.getElementById('phone');
var parole = doc.getElementById('password');
    
cancellButton.onclick = function () {
    doc.getElementById('alert').style.display = 'none';
    firstName.value = '';
    lastName.value = '';
    mail.value = null;
    yearsOld.value = '';
    addr.value = '';
    phoneNum.value = '';
    parole.value = null;
};