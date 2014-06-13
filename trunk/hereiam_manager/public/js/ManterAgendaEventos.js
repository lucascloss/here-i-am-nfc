$(document).ready(function() {
	
    $('#listaEventos').dataTable( {
        "ajax": URL_BASE + "/calendars",
        "ajaxDataProp": "",
        "columns": [
            //{ "data": "id" },
            { "data": "event" },
            { "data": "environmentId" },
            { "data": "placeId" },
            { "data": "begin" },
            { "data": "ends" }  
        ]
    } );
        
    $init = function() {
    	$("#btnEditarAgenda").prop( "disabled", true );
        $("#btnRemoverAgenda").prop( "disabled", true );
    };
    $init();
    
    $.reload = function() {
        $.init();
        
        $("#listaAgendas").dataTable()._fnAjaxUpdate();
    };
    
});
