

function init() {
    layui.use(['slider','layer'],function () {
        layui.slider.render({
            elem: '#questionNum'
            ,input: true
            ,max: 50
        });
    });
    num2Letter();
}

function addItem(obj) {
    var num = $(obj).prev().find(".option-item-key").text();
    num = getBigLetterIndex(num);
    var n = 0;
    if(num && num !== ''){
        n = parseInt(num);
    }

    $(obj).before(getOptionHtml());
    $(obj).prev().find(".option-item-key").text(getBigLetter(n+1));
    var optionNum = $(obj).parent().find(".option-item").length-1;
    $(obj).prev().css("top", (optionNum*15)+"%");
    cssControl();
}

function addQuestion(obj) {
    $(obj).parents(".question-all").after(getQuestionHtml(2));
    cssControl();
    questionNumControl();
    num2Letter();
}

function delItem(obj) {
    $(obj).parents(".option-item").remove();
}

function delQuestion(obj) {
    var questions = $(".question-all");
    if(questions.length <= 1){
        App.error("唯一的题目不能删除");
        return;
    }
    $(obj).parents(".question-all").remove();
    questionNumControl();
}

function questionNumControl() {
    var all = $(".question-all");
    all.each(function (i) {
        all.eq(i).find(".question .position").text(i+1);
        all.eq(i).find(".question .info .radio input[type=radio]").attr("name",i+1);
    });
}

function cssControl(){
    $("input[type=text]").addClass("border-hide");
    $(".question-all .option .option-item .option-item-text").css("min-width","200px");
}

function num2Letter(){
    $("span.letter").each(function (i) {
        var n = parseInt($(this).text());
        var letter = getBigLetter(n);
        $(this).text(letter);
    })

    $("input.letter").each(function (i) {
        var n = parseInt($(this).val());
        var letter = getBigLetter(n);
        $(this).attr("value",letter);
    })
}