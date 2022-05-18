function changeLanguagePL() {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('lang', 'pl');
    window.location.search = urlParams;
}
function changeLanguageEN() {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('lang', 'en');
    window.location.search = urlParams;
}
function changeLanguage() {
    var x = document.getElementById("locales").value;
    if (x == "pl") {
        changeLanguagePL();
    } else  if (x == "en") {
        changeLanguageEN();
    }
    document.getElementById("locales").value = "";
}