var exec = require('cordova/exec');

var timers = {};
var count = 1;

exports.setTimeout = function (success, time) {
    var that = this;
    var id = count++;
    timers[id] = success;
    exec(function() {
        if(timers[id]) {
            success.call(that);
            delete timers[id];
        }
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'setTimeout', [time?time:0]);
    return id;
};

exports.clearTimeout = function (id) {
    delete timers[id];    
};

exports.setInterval = function (success, time, id) {    
    var that = this;
    var id = id || count++;
    timers[id] = success;
    exec(function() {
        if(timers[id]) {
            success.call(that);
            setInterval(success, time, id);
        }
    }, function(e){
        console.error(e);
    }, 'setTimeout', 'setTimeout', [time?time:0]);
    return id;
}

exports.clearInterval = function (id) {
    delete timers[id];    
};

document.addEventListener("deviceready", function() {
    if( window.device.platform === "Android" ) {
        if( window.device.version.split(".")[0] > 6 ) {
            window.setTimeout = cordova.plugins.setTimeout.setTimeout;
            window.clearTimeout = cordova.plugins.setTimeout.clearTimeout;
            // window.setInterval = cordova.plugins.setTimeout.setInterval;
            // window.clearInterval = cordova.plugins.setTimeout.clearInterval;
        }
    }
}, false);


