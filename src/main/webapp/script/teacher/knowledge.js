
/**
 * 搜索
 * @param {DOM} 搜搜表单
 */
function searchKnowledge(form) {
    var input = form.search.value;
    if ($.trim(input) === "") {
        return false;
    }
    return true;
}
/**
 * [[显示/隐藏专业添加窗口]
 */
function toggleKnowledgeAdd(isShow) {
    var knowledge_add = document.getElementById("knowledge_add");
    if(isShow) {
        //加载所有年级
        _loadKnowledge(function(element) {
            return "<option value=" + element.pid + ">" + element.knowledgeName + "</option>";
        }, true);
        knowledge_add.style.display = "block";
    }else {
        knowledge_add.style.display = "none";
    }
}
/**
 * 加载所有年级
 * @param callback 回调函数
 * 此回调函数接收可以接收两个参数，第一个是element(需要处理的json对象)
 * 此函数需要返回一个代表option元素的字符串
 * @param isAdd 是否是学生增加
 */
function _loadKnowledge(callback, isAdd) {
    $.ajax({
        "url": "knowledge/ajax",
        "dataType": "json",
        "async": false,
        "type" : "POST",
        "success": function(json) {
            if(json.result == "1") {
                var $pidSelect = isAdd ? $("#pid_select_add") : $("#pid_select_edit");
                $pidSelect.empty();
                var options = new Array();
                //只有在增加时才显示提示
                options.push("<option value='-1'>无</option>");
                for(var i = 0;i < json.data.length;i ++) {
                    options.push("<option value='"+json.data[i].id+"'>"+json.data[i].knowledgeName+"</option>");
                }
                $pidSelect.append($(options.join("")));
            }
        }
    });
}
function addKnowledge(form) {
    var error = document.getElementById("knowledge_add_error");

    $.ajax({
        "url": "teacher/knowledge/add",
        "type" : "POST",
        "data": "pid=" + $("#pid_select_add").val() + "&knowledgeName=" + form.knowledgeName.value,
        "async": false,
        "dataType": "json",
        "success": function(json) {
            if(json.result == 0) {
                error.innerHTML = json.message;
            }else {
                toggleStudentAdd(false);
                _resetKnowledge(form.knowledgeName, error);
                Tips.showSuccess(json.message);
            }
        }
    });
}
/**
 * [[重置输入界面]]
 * @param {[[DOM]]} knowledge [[输入]]
 * @param {[[DOM]]} error [[错误显示]]
 */
function _resetKnowledge(knowledge, error) {
    knowledge.value = "";
    error.innerHTML = "";
}
/**
 * [[删除单个元素]]
 * @param {[[DOM]]} btn [[触发此函数的按钮]]
 */
function deleteKnowledge(btn) {
    var id = $(btn).parents("tr").find("td:first").html();
    sendDeleteRequest(id);
}
/**
 * [[发送删除请求]]
 * @param {[[String]]} [[请求参数]]
 */
function sendDeleteRequest(id) {
    $.ajax({
        "url" : "teacher/knowledge/delete",
        "type" : "POST",
        "dataType" : "json",
        "data": "id=" + id,
        "async" : false,
        "success" : function(json) {
            if (json.result == "0") {
                Tips.showError(json.message);
            }else if (json.result == "1") {
                Tips.showSuccess(json.message);
                window.location.reload();
            }
        }
    });
}

function toggleKnowledgeEdit(isShow, btn) {
    var knowledge_edit = document.getElementById("knowledge_edit");
    if (isShow) {
        var pid = -1;
        var pid_td = $(btn).parent().prev();
        _loadKnowledge(function(element) {
            var option = "<option value='" + element.pid + "'";
            if (element.id == pid){
                option += " selected";
                pid = element.pid;
            }
            pid = element.pid;
            option += ">" + element.knowledgename + "</option>";
            return option;
        }, false);
        var name_td = pid_td.prev();
        $("#knowledge_edit_name").val(name_td.html());
        $("#knowledge_edit_id").val(name_td.prev().html());
        knowledge_edit.style.display = "block";
    } else {
        knowledge_edit.style.display = "none";
    }
}


/**
 *
 * @param form
 */
function editKnowledge(form) {
    var error = document.getElementById("knowledge_edit_error");
    $.ajax({
        "url": "teacher/knowledge/edit",
        "data": "knowledgeName=" + form.knowledgeName.value + "&id=" + form.id.value + "&pid=" + $("#pid_select_edit").val(),
        "async": false,
        "dataType": "json",
        "success": function(json) {
            if(json.result == 0) {
                error.innerHTML = json.message;
            }else {
                toggleKnowledgeEdit(false);
                _resetKnowledge(form.knowledgeName, error);
                Tips.showSuccess(json.message);
                window.location.href = "teacher/knowledge/list";
            }
        }
    });
}


