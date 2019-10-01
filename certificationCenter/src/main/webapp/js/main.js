var doc = document;
function showClientMenu() {
    var apply = doc.getElementById('apply');
    apply.style.display = 'block';
    var apps = doc.getElementById('apps');
    apps.style.display = 'block';
    var login = doc.getElementById('login');
    login.style.display = 'none';
    var logout = doc.getElementById('logout');
    logout.style.display = 'block';
}