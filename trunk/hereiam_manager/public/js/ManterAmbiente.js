$(document).ready(function() {
	
    $('#listaAmbientes').dataTable( {
        "ajax": URL_BASE + "/environments",
        "ajaxDataProp": "",
        "columns": [
            //{ "data": "id" },
            { "data": "name" },
            { "data": "type" },
            { "data": "environmentAdmId" }     
        ]
    } );
    
    /*$('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=remover');
    });*/

} );
