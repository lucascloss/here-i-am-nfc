$(document).ready(function() {
	
    $('#listaLocais').dataTable( {
        "ajax": URL_BASE + "/places",
        "ajaxDataProp": "",
        "columns": [
            //{ "data": "id" },
            { "data": "name" },
            { "data": "environmentId" },
            { "data": "type" },
            { "data": "placeAdmId" }     
        ]
    } );
    
    /*$('[data-toggle="modal"]').click(function(e) { 
    	$.ajax({
    	    type:"GET", 
    	    url: "http://hereiam.zapto.org/hereiamwse/user/find?username=lcloss", 
    	    success: function(data) {
    	            $("body").append(JSON.stringify(data));
    	        }, 
    	    error: function(jqXHR, textStatus, errorThrown) {
    	            alert(jqXHR.status);
    	        },
    	   dataType: "json",
    	   async:false
    	});
    });
    
    $('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=remover');
    });*/
});