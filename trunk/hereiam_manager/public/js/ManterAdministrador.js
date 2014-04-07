/*$(document).ready(function() {
    var oTable = $('#example').dataTable( {
        "bProcessing": true,
        "sAjaxSource": "sources/objects.txt",
        "aoColumns": [
            { "mData": "engine" },
            { "mData": "browser" },
            { "mData": "platform" },
            { "mData": "version" },
            { "mData": "grade" }
        ]
    } );
} );*/

$(document).ready(function() {
    $('#listaAdministradores').dataTable();
    
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
