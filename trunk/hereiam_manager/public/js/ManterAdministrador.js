$(document).ready(function() {
	
    if ($('#listaAdministradores-sistema').length > 0) {
    	alert('passou aqui'); 
	    $('#listaAdministradores-sistema').dataTable( {
	        "ajax": URL_BASE + "/solutionAdms",
	        "ajaxDataProp": "",
	        "columns": [
	                    //{ "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    } else if ($('#listaAdministradores-ambiente').length > 0) {
	    $('#listaAdministradores-ambiente').dataTable( {
	        "ajax": URL_BASE + "/environmentAdms",
	        "ajaxDataProp": "",
	        "columns": [
	                    //{ "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    } else if ($('#listaAdministradores-local').length > 0) {
	    $('#listaAdministradores-local').dataTable( {
	        "ajax": URL_BASE + "/placeAdms",
	        "ajaxDataProp": "",
	        "columns": [
	                    //{ "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    }
    
    $('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteradministrador&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteradministrador&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manteradministrador&acao=remover');
    });
    
    $('#teste').datepicker();
} );
