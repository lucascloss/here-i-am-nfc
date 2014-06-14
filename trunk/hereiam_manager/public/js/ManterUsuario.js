$(document).ready(function() {
	WS_URL = URL_BASE + "/appUsers";
    
	$('.progressBar').show();
	$('#listaUsuarios').DataTable( {
        "ajax": WS_URL,
        "ajaxDataProp": "",
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "email" },
            { "data": "username" }	            
        ]
    } );
    
	 $('#listaUsuarios tbody').on( 'click', 'tr', function () {
	        $(this).toggleClass('selected');
	        
	        var selected = $("#listaUsuarios").DataTable().rows('.selected').data();	        
	        $("#btnRemoverUsuario").attr( "disabled", (selected.length == 0) );
	  } );
	 
	 $('#modalRemover').on('loaded.bs.modal', function (e) {
	    	$(this).find("form").submit($.removerUsuario);
	 });
	 
	 $.removerUsuario = function (event) {
	    	$('.progressBar').show();
	    	var selected = $("#listaUsuarios").DataTable().rows('.selected').data();

	    	if (selected.length > 0) {
	    		for (var i = 0; i < selected.length; i++) {
			        $.ajax({
			        	type: "DELETE",
			        	url: WS_URL + "/" + selected[i].id,
			        	success: function(data) {
			        		$('.progressBar').hide();
			        		$("#modalRemover").modal("hide");
			        		$.reload();
			            }, 
			        	dataType: "json"
			        });
	    		}
	    	}

	    	event.preventDefault();
	    };
	    
	    $init = function() {	    	
	        $("#btnRemoverUsuario").attr( "disabled", true );
	    };
	    $init();
	    
	    $.reload = function() {
	        $init();
	        $("#listaUsuarios").dataTable()._fnAjaxUpdate();
	    };
} );
