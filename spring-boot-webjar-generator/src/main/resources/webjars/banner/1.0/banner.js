var Banner = {};
Banner.init = function (bannerId, urlArray) {
    var obj = {
        bannerObj: $("#" + bannerId),
        bgDiv: undefined,
        imgParentDiv: undefined,
        urlArray: urlArray,
        imgLabels: [],
        currentIndex: -1,
        interval: undefined,
        startScroll: Custom_startScroll,
        showImg: Custom_showImg
    };
    obj.bgDiv = $("<div style='filter:blur(2px);width:100%;height:100%;" +
        "position:absolute'>");
    obj.bannerObj.append(obj.bgDiv);
    obj.imgParentDiv = $("<div style='width:100%;height:100%;display: flex;position: absolute'>");
    obj.bannerObj.append(obj.imgParentDiv);
    for (var i = 0; i < urlArray.length; i++) {
        var url = urlArray[i];
        var img = $("<img src='" + url + "' style='display:none;width:auto;height:auto;max-width:100%;" +
            "max-height:100%;margin:auto;'>");
        obj.imgLabels.push(img);
        obj.imgParentDiv.append(img);
    }
    return obj;
};

function Custom_startScroll(intervalTime) {
    if (this.interval !== undefined) {
        clearInterval(this.interval);
    }
    var len = this.urlArray.length;
    var obj = this;
    obj.showImg(0);
    this.interval = setInterval(function () {
        obj.currentIndex++;
        if (obj.currentIndex >= len) {
            obj.currentIndex = 0;
        }
        obj.showImg(obj.currentIndex);
    }, intervalTime);
}

function Custom_showImg(index) {
    var lastIndex = index - 1;
    if (lastIndex < 0) {
        lastIndex = this.urlArray.length - 1;
    }
    this.imgLabels[lastIndex].hide();
    this.imgLabels[index].show();

    this.bgDiv.css("background","url('"+this.urlArray[index]+"')");
}