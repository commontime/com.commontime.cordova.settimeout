var exec = require('cordova/exec');

var count = 1;

exports.setTimeout = function (success, time) {
    var that = this;
    var id = count++;
    exec(function(result) {
        if(result === "OK") {
            success.call(that);
        }
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'setTimeout', [time?time:0, id]);
    return id;
};

exports.clearTimeout = function (id) {
    exec(function() {
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'clearTimeout', [id]);
};

exports.setInterval = function (success, time, id) {
    var that = this;
    var id = id || count++;
    exec(function(result) {
        if(result === "OK") {
            success.call(that);
        }
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'setInterval', [time?time:0, id]);
    return id;
}

exports.clearInterval = function (id) {
    exec(function() {
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'clearInterval', [id]);
};

document.addEventListener("deviceready", function() {
    if( window.device.platform === "Android" ) {
        if( window.device.version.split(".")[0] > 0 ) {
            window.setTimeout = cordova.plugins.setTimeout.setTimeout;
            window.clearTimeout = cordova.plugins.setTimeout.clearTimeout;
            window.setInterval = cordova.plugins.setTimeout.setInterval;
            window.clearInterval = cordova.plugins.setTimeout.clearInterval;
        }
    }
}, false);
