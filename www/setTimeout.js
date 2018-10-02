var exec = require('cordova/exec');

var timers = {};
var count = 1;

exports.setTimeout = function (success, time) {
    var id = count++;
    timers[id] = success;
    exec(function() {
        if(timers[id]) {
            success();
            delete timers[id];
        }
    }, function(e){console.error(e);}, 'setTimeout', 'setTimeout', [time]);
    return id;
};

exports.clearTimeout = function (id) {
    delete timers[id];    
};
