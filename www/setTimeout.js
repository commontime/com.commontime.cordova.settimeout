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

document.addEventListener("deviceready", function() {
    window.setTimeout = cordova.plugins.setTimeout.setTimeout;
    window.clearTimeout = cordova.plugins.setTimeout.clearTimeout;
}, false);


