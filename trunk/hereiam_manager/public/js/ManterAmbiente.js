$(document).ready(function() {
    $('#listaAmbientes').dataTable();
    
    $('#btnAdicionar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterambiente&acao=remover');
    });
//    aqui
    $('[data-toggle="modal"]').click(function(e) {
    	e.preventDefault();
    	var url = $(this).attr('href');
    	if (url.indexOf('#') == 0) {
    		$(url).modal('open');
    	} else {
    		$.get(url, function(data) {
    			$('<div class="modal hide fade">' + data + '</div>').modal();
    		}).success(function() { $('input:text:visible:first').focus(); });
    	}
    });
//    aqui
} );
