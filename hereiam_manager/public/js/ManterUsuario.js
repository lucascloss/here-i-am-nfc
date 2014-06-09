$(document).ready(function() {
	
    $('#listaUsuarios').dataTable( {
        "ajax": URL_BASE + "/appUsers",
        "ajaxDataProp": "",
        "columns": [
            //{ "data": "id" },
            { "data": "name" },
            { "data": "email" },
            { "data": "username" }	            
        ]
    } );
    
    /*$('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=remover');
    });*/
} );
