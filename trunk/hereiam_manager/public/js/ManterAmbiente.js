$(document).ready(function() {
	WS_URL = URL_BASE + "/environments";
	
	$('.progressBar').show();
    $('#listaAmbientes').DataTable( {
        "ajax": WS_URL,
        "ajaxDataProp": "",
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "type" },
            { "data": "environmentAdmId" }     
        ]
    });
    
    $('.progressBar').hide();
    
    $('#listaAmbientes tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
        var selected = $("#listaAmbientes").DataTable().rows('.selected').data();
        $("#btnEditarAmbiente").attr( "disabled", (selected.length != 1) );
        $("#btnRemoverAmbiente").attr( "disabled", (selected.length == 0) );
    } );
    
    $('#modalAdicionar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.adicionarAmbiente);
    });
        
    $('#modalAdicionar').on('show.bs.modal', function (e) {
    	$('.progressBar').show();
 		
 		$.ajax({
         	type: "GET",
         	url: URL_BASE + "/environmentAdms",        	
         	success: function(data) {         		        		
         		$('#adicionarAmbiente #environmentAdmId option').remove();
         		for (var i = 0; i < data.length; i++) {         			
         			$('#adicionarAmbiente #environmentAdmId').append($("<option></option>").text(data[i].name).val(data[i].id));
         		}
         		$('.progressBar').hide();
             } , 
             dataType: "json"
        }); 	  
    });
      
    $('#modalEditar').on('loaded.bs.modal', function (e) {    	    	    	    
	    $(this).find("form").submit($.editarAmbiente);
	    
	    $('#editarAmbiente #idNfc').attr( 'disabled', true );
	    $('#editarAmbiente #latitude').attr( 'disabled', true );
	    $('#editarAmbiente #longitude').attr( 'disabled', true );
	    $('#editarAmbiente #address').attr( 'disabled', true );
	    $('#editarAmbiente #district').attr( 'disabled', true );
	    $('#editarAmbiente #number').attr( 'disabled', true );
	    $('#editarAmbiente #city').attr( 'disabled', true );
	    $('#editarAmbiente #state').attr( 'disabled', true );
	    $('#editarAmbiente #country').attr( 'disabled', true );
	    $('#editarAmbiente #environmentAdmId').attr( 'disabled', true );
    });
    
    $('#modalEditar').on('show.bs.modal', function (e) {
    	var selected = $("#listaAmbientes").DataTable().rows('.selected').data();
    	
    	$('.progressBar').show();
	    
	    $.ajax({
        	type: "GET",
        	url: WS_URL + "/" + selected[0].id,        	
        	success: function(data) {
        		$("#editarAmbiente #name").val(data.name);
        		$("#editarAmbiente #type").val(data.type);
        		$("#editarAmbiente #info").val(data.info);
        		$("#editarAmbiente #idNfc").val(data.idNfc);
        		$("#editarAmbiente #latitude").val(data.latitude);
        		$("#editarAmbiente #longitude").val(data.longitude);
        		$('#editarAmbiente #address').val(data.address);
        	    $('#editarAmbiente #district').val(data.district);
        	    $('#editarAmbiente #number').val(data.number);
        	    $('#editarAmbiente #city').val(data.city);
        	    $('#editarAmbiente #state').val(data.state);
        	    $('#editarAmbiente #country').val(data.country);        	    
        	    
        		if ((data.environmentAdmId != null) && (data.environmentAdmId > 0)) {
	        	    $.ajax({
	                	type: "GET",
	                	url: URL_BASE + "/environmentAdms/" + data.environmentAdmId,        	
	                	success: function(data1) {
	                		$('#editarAmbiente #environmentAdmId option').remove();
	                		$('#editarAmbiente #environmentAdmId').append($("<option></option>").text(data1.name).attr( "selected", "selected" ));        	           		        	
	                    } , 
	                    dataType: "json"
	                });
        		}        		        		       		
        		$('.progressBar').hide();
            }, 
            dataType: "json"
        });
    });
    
    $('#modalRemover').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.removerAmbiente);
    });
    
    $.adicionarAmbiente = function (event) {
    	$('.progressBar').show();
    	
    	var obj = new Object();    	
    	obj.name = $("#adicionarAmbiente #name").val();    	
    	obj.type = $("#adicionarAmbiente #type").val();
    	obj.info = $("#adicionarAmbiente #info").val();
    	obj.idNfc = $("#adicionarAmbiente #idNfc").val();
    	obj.latitude = $("#adicionarAmbiente #latitude").val();
    	obj.longitude = $("#adicionarAmbiente #longitude").val();
    	obj.address = $('#adicionarAmbiente #address').val();
    	obj.district = $('#adicionarAmbiente #district').val();
    	obj.number = $('#adicionarAmbiente #number').val();
    	obj.city = $('#adicionarAmbiente #city').val();
    	obj.state = $('#adicionarAmbiente #state').val();
    	obj.country = $('#adicionarAmbiente #country').val();
    	obj.environmentAdmId = $('#adicionarAmbiente #environmentAdmId').val();
    	
    	$.ajax({
        	type: "POST",
        	url: WS_URL,
        	data: obj,
        	success: function(data) {
        		$('.progressBar').hide();
        		
        		$("#modalAdicionar").modal("hide");
        		$("#listaAmbientes").dataTable()._fnAjaxUpdate();
            }
        });    	    	  	
        
        event.preventDefault();
    };
    
    $.editarAmbiente = function (event) {
    	$('.progressBar').show();
    	
    	var selected = $("#listaAmbientes").DataTable().rows('.selected').data();
    	
    	if (selected.length == 1) {    		    	
	    	var obj = new Object();
    	   	obj.name = $("#editarAmbiente #name").val();
    	   	obj.type = $("#editarAmbiente #type").val();
    	   	obj.info = $("#editarAmbiente #info").val();
    	   	/*obj.idNfc = $("#editarAmbiente #idNfc").val();
        	obj.latitude = $("#editarAmbiente #latitude").val();
        	obj.longitude = $("#editarAmbiente #longitude").val();
        	obj.address = $('#editarAmbiente #address').val();
        	obj.district = $('#editarAmbiente #district').val();
        	obj.number = $('#editarAmbiente #number').val();
        	obj.city = $('#editarAmbiente #city').val();
        	obj.state = $('#editarAmbiente #state').val();
        	obj.country = $('#editarAmbiente #country').val();
        	obj.environmentAdmId = $('#editarAmbiente #environmentAdmId').val();*/
        	
    	   	
    	   	$.ajax({
	        	type: "PUT",
	        	url: WS_URL + "/" + selected[0].id,
	        	data: obj,
	        	success: function(data) {
	        		$('.progressBar').hide();	        		
	        		$("#modalEditar").modal("hide");       		
	        		$.reload();
	        		$.init();
	            }, 	            
	        	dataType: "json"
	        });
    	}
        
    	event.preventDefault();    	   	
    };
    
    $.removerAmbiente = function (event) {
    	$('.progressBar').show();
    	var selected = $("#listaAmbientes").DataTable().rows('.selected').data();

    	if (selected.length > 0) {
    		for (var i = 0; i < selected.length; i++) {
		        $.ajax({
		        	type: "DELETE",
		        	url: WS_URL + "/" + selected[i].id,
		        	success: function(data) {
		        		$('.progressBar').hide();
		        		$("#modalRemover").modal("hide");
		        		$.reload();
		        		$.init();
		            }, 
		        	dataType: "json"
		        });
    		}
    	}

    	event.preventDefault();
    };
    
    $init = function() {
    	$("#btnEditarAmbiente").attr( "disabled", true );
        $("#btnRemoverAmbiente").attr( "disabled", true );
    };
    $init();
    
    $.reload = function() {
//        $.init();
        $("#listaAmbientes").dataTable()._fnAjaxUpdate();
    };
    
});