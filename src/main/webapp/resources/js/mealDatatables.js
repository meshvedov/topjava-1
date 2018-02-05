var ajaxUrl = "ajax/profile/meals/";
var datatableApi;
$(document).ready(function () {
    datatableApi = $("#datatable").DataTable({
        "info": true,
        "columns": [
            {"data": "dateTime"},
            {"data": "description"},
            {"data": "calories"},
            {"defaultContent": "Edit", "orderable": false},
            {"defaultContent": "Delete", "orderable": false}
        ]
    });

    makeEditable();
});