function highlight(language, code, optionalCredit) {
    jsRoutes.controllers.Main.highlight(language).ajax({data: code,
        contentType: 'text/plain',
        success: function(rendered) {
            $("#render-to").html(rendered);
        }, error: function(e) {
            console.log("Faile dto highlight", e);
        }});
}
$("#dropdown-samples").find("a").click(function(event) {
    var path = $(event.target).data("sample-path");
    var language = $(event.target).data("sample-language");
    var credit = $(event.target).data("sample-credit");
    $.ajax(path).then(function(data) {
        console.log("Picked sample... ", data);
        highlight(language, data, credit);
    });
});
$("#dropdown-styles").find("a").click(function(event) {
    selectStyle($(event.target).data("style-id"));
});
//highlight("scala", "import stuff._");
function selectStyle(name) {
    $("#rendering-style").text($("*[data-style-id="+name+"]").data("style-css"));
}
selectStyle("monokai");
