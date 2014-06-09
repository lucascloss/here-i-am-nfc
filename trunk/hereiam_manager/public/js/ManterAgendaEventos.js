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
    
    /*$('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteragendaeventos&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteragendaeventos&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteragendaeventos&acao=remover');
    });*/   
} );
