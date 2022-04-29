function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yy = today.getFullYear();
    dd = checkTime(dd);
    mm = checkTime(mm);
    yy = checkTime(yy);
    h = checkTime(h);
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('txt').innerHTML = "<br>" + h + ":" + m + ":" + s + "; " + dd + "/" + mm + "/" + yy;
    var t = setTimeout(startTime, 1000);
}

// add zero in front of numbers < 10
function checkTime(i) {
    if (i < 10) {
        i = "0" + i
    }
    return i;
}