$(document).ready(function() {
    $('#listaLocais').dataTable();
    
    $('#btnAdicionar').click(function() { 
//    	$(location).attr('href', 'index.php?controle=manterlocal&acao=adicionar');
    });
    
    $('#btnEditar').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=editar');
    });
    
    $('#btnRemover').click(function() { 
    	$(location).attr('href', 'index.php?controle=manterlocal&acao=remover');
    });
} );
