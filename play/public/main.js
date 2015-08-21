var currentLanguage;
var currentCode;
var currentCredit;
function highlight(language, code, optionalCredit) {
    var ci = $('#code-input');
    if ( ci.val() != code ) {
        ci.val(code);
    }
    if ( currentCode === code ) return;
    currentLanguage = language;
    currentCode = code;
    currentCredit = optionalCredit;
    jsRoutes.controllers.Main.highlight(language).ajax({data: code,
        contentType: 'text/plain',
        success: function(rendered) {
            $("#render-to").html(rendered);
        }, error: function(e) {
            console.log("Faile dto highlight", e);
        }});
}
function renderSample(a) {
    var path = $(a).data("sample-path");
    var language = $(a).data("sample-language");
    var credit = $(a).data("sample-credit");
    $.ajax(path).then(function(data) {
        highlight(language, data, credit);
    });
}
$("#dropdown-samples").find("a").click(function(event) {
    renderSample(event.target);
});
$("#dropdown-styles").find("a").click(function(event) {
    selectStyle($(event.target).data("style-id"));
});
$("#dropdown-languages").find("a").click(function(event) {
    selectLanguage($(event.target).data("language-id"));
});
function selectLanguage(id) {
    return highlight(id, currentCode, currentCredit);
}
function selectStyle(name) {
    $("#rendering-style").text($("*[data-style-id="+name+"]").data("style-css"));
}
selectStyle("monokai");

$("#dropdown-samples").find("a[data-sample-language=scala]").first().each(function(_, a) { return renderSample(a); });
$('#code-input').keyup(function(evt) {
    var code = $(evt.target).val();
    renderCode(code);
});
function renderCode(code) {
    console.log("Render code = ", code);
    highlight(currentLanguage, code, currentCredit);
}