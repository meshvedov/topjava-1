var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": function (data) {
                    var date = data["dateTime"].split('T');
                    return date[0] + " " + date[1].substr(0, 5);
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? "exceeded" : "normal");
        },
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "initComplete": makeEditable
    });
    $("#dateTime").datetimepicker({
        format: 'd.m.Y H:i',
        inline: false,
        lang: 'ru'
    });
});