function checkRegistrationData(form) {
    var formD = new FormData(form);
    if (checkIsFieldEmpty(formD.get('unp').trim())) {
        displayAlert('Organisation identification number is not specified');
    } else if (checkIsFieldEmpty(formD.get('organisationName').trim())) {
        displayAlert('Field for the organisation name is not filled');
    } else if (checkIsFieldEmpty(formD.get('organisationAddress').trim())) {
        displayAlert('Field for the organisation address is not filled');
    } else if (checkIsFieldEmpty(formD.get('organisationPhone').trim())) {
        displayAlert('Organisation phone number is not specified');
    } else if (checkIsFieldEmpty(formD.get('organisationEmail').trim())) {
        displayAlert('Organisation email is not specified');
    } else if (checkIsFieldEmpty(formD.get('login').trim())) {
        displayAlert('Login is empty');
    } else if (checkIsFieldEmpty(formD.get('name').trim())) {
        displayAlert('Field for the executor name is not filled');
    } else if (checkIsFieldEmpty(formD.get('surname').trim())) {
        displayAlert('Field for the executor surname is not filled');
    } else if (checkIsFieldEmpty(formD.get('executorEmail'))) {
        displayAlert('Executor email is not specified');
    } else if (checkIsFieldEmpty(formD.get('executorPhone'))) {
        displayAlert('Executor phone number is not specified');
    } else if (checkPassword(formD.get('password')) === -1) {
        displayAlert('Password is not strong enough');
    } else if (!validatePassword(formD.get('password'), formD.get('passwordAgain'))) {
        displayAlert('Passwords do not match');
    } else {
        form.submit();
    }
}

function checkIsFieldEmpty(field) {
    return field == "";
}

function checkPassword(password) {
    var passwordPattern = /(?=.*[0-9])(?=.*[a-z])[0-9a-zA-Z!@#$%^&*]{6,}/g;
    return password.search(passwordPattern);
}

function validatePassword(password, passwordAgain) {
    return password === passwordAgain;
}

function displayAlert(message) {
    document.getElementById('alert').style.display = 'block';
    document.getElementById('alert').innerHTML = message;
}
