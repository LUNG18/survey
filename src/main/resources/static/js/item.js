
function getOptionHtml(i){
    var n = "";
    if(typeof i === "number"){
        n = i;
    }
    var optionHtml =
        '<span class="option-item">'+
            '<label>'+
                '<span class="option-item-key letter">'+n+'</span>'+
                '<span class="block">.</span>'+
                '<span class="option-item-text">'+
                    '<input value="" type="text" placeholder="选项..."/>'+
                '</span>'+
                '<span class="pointer" onclick="delItem(this)">' +
                    '<i class="layui-icon layui-icon-close-fill"></i>' +
                '</span>'+
            '</label>'+
        '</span>';
    return optionHtml;
}

function getQuestionHtml(n) {
    var questionHtml =
        '<div class="question-all">'+

            '<div class="question">'+
                '<div class="info">'+
                    '<span class="position"></span>'+
                    '<span class="block">.</span>'+
                    '<span class="title">'+
                        '<input value="" type="text" placeholder="问题..."/>'+
                    '</span>' +
                    '<span class="radio">'+
                        '<label class="block"><input type="radio" value="1" checked />单选</label>'+
                        '<label class="block"><input type="radio" value="2" />多选</label>'+
                    '</span>' +
                '</div>' +
            '</div>'+

            '<div class="option">';
    for(var i=0; i<n; i++){
        questionHtml = questionHtml + getOptionHtml(i);
    }
    questionHtml = questionHtml +
            '<span class=\'option-add pointer\' onclick="addItem(this)">' +
                    '<i class="layui-icon layui-icon-add-circle"></i>添加选项' +
                '</span>'+
            '</div>'+

            '<div class="description">' +
                '<span>问题说明:</span>' +
                '<span class="block"></span>' +
                '<input placeholder="问题说明..." type="text" value="" class="border-hide">' +
            '</div>'+

            '<span class=\'question-add pointer\' onclick="addQuestion(this)">' +
                '<i class="layui-icon layui-icon-add-circle"></i>添加一题' +
            '</span>'+
            '<span class=\'question-del pointer\' onclick="delQuestion(this)">' +
                '<i class="layui-icon layui-icon-close-fill"></i>删除此题' +
            '</span>'+

            '<div class="line"></div>'+
        '</div>';
    return questionHtml;
}

var small_letter = [
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
    's','t','u','v','w','x','y','z'
];
var big_letter = [
    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
    'S','T','U','V','W','X','Y','Z'
];

function getBigLetterIndex(o) {
    return big_letter.findIndex(function(item){ return item === o; });
}

function getSmallLetterIndex(o) {
    return small_letter.findIndex(function(item){ return item === o; });
}

function getBigLetter(n){
    return big_letter[n];
}

function getSmallLetter(n){
    return small_letter[n];
}