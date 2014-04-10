$(document).ready(function() {
    $('#listaLocais').dataTable();
   
    $('[data-toggle="modal"]').click(function(e) { 
    	/*$usuario = !empty($_POST['usuario']) ? $_POST['usuario'] : "";
    	$senha = !empty($_POST['senha']) ? $_POST['senha'] : "";*/ 
	
	$.ajax({
	        url: "http://hereiam.zapto.org/hereiamwse/user/find?username=lcloss"
	    }).then(function(data) {
	    	$('.nome').append(data.name);
	    });
    });
    
    
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
