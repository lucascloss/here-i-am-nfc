$(document).ready(function() {
	
    if ($('#listaAdministradores-sistema').length > 0) {
    	WS_URL = URL_BASE + "/solutionAdms";
	    var dTable = $('#listaAdministradores-sistema').DataTable( {
	        "ajax": WS_URL,
	        "ajaxDataProp": "",
	        "columns": [
	                    { "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    } else if ($('#listaAdministradores-ambiente').length > 0) {
    	WS_URL = URL_BASE + "/environmentAdms";
    	var dTable = $('#listaAdministradores-ambiente').DataTable( {
	        "ajax": WS_URL,
	        "ajaxDataProp": "",
	        "columns": [
	                    { "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    } else if ($('#listaAdministradores-local').length > 0) {
    	WS_URL = URL_BASE + "/placeAdms";
    	var dTable = $('#listaAdministradores-local').DataTable( {
	        "ajax": WS_URL,
	        "ajaxDataProp": "",
	        "columns": [
	                    { "data": "id" },
	                    { "data": "name" },
	                    { "data": "email" },
	                    { "data": "username" }	  
	        ]
	    } );
    }

    $("[id^=listaAdministradores-] tbody").on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
        var selected = dTable.rows('.selected').data();
        $("#btnEditarAdministrador").attr( "disabled", (selected.length != 1) );
        $("#btnRemoverAdministrador").attr( "disabled", (selected.length == 0) );
    } );
    
    $('#modalAdicionar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.adicionarAdministrador);
    });   
    
    $('#modalEditar').on('loaded.bs.modal', function (e) {    	    	    	    
	    $(this).find("form").submit($.editarAdministrador);
	    
	    $('#editarLocal #username').attr( 'disabled', true );
    });
    
    $('#modalEditar').on('show.bs.modal', function (e) {
    	var selected = dTable.rows('.selected').data();
    	
    	$('.progressBar').show();
	    
	    $.ajax({
        	type: "GET",
        	url: WS_URL + "/" + selected[0].id,        	
        	success: function(data) {
        		$("#editarAdministrador #name").val(data.name);
        		$("#editarAdministrador #username").val(data.username);
        		$("#editarAdministrador #email").val(data.email);
        		$("#editarAdministrador #password").val(data.password);
        		
        		$('.progressBar').hide();
            }, 
            dataType: "json"
        });
    });
    
    $('#modalRemover').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.removerAdministrador);
    });
    
    $.adicionarAdministrador = function (event) {
    	$('.progressBar').show();
    	
    	var obj = new Object();
        obj.name = $("#adicionarAdministrador #name").val();
        obj.username  = $("#adicionarAdministrador #username").val();
        obj.email = $("#adicionarAdministrador #email").val();
        obj.password = $("#adicionarAdministrador #password").val();
        
        $.ajax({
        	type: "POST",
        	url: WS_URL,
        	data: obj,
        	success: function(data) {
        		$('.progressBar').hide();
        		$("#modalAdicionar").modal("hide");
        		$.reload();
            }
        });
        
        event.preventDefault();
    }; 
    
    $.editarAdministrador = function (event) {
    	$('.progressBar').show();
    	
    	var selected = dTable.rows('.selected').data();

    	if (selected.length == 1) {
	    	var obj = new Object();
	        obj.name = $("#editarAdministrador #name").val();
	        obj.username  = $("#editarAdministrador #username").val();
	        obj.email = $("#editarAdministrador #email").val();
	        obj.password = $("#editarAdministrador #password").val();
	        
	        console.log(obj);
	        $.ajax({
	        	type: "PUT",
	        	url: WS_URL + "/" + selected[0].id,
	        	data: obj,
	        	success: function(data) {
	        		$('.progressBar').hide();
	        		$("#modalEditar").modal("hide");
	        		$.reload();
	            }, 
	        	dataType: "json"
	        });
    	}
        
    	event.preventDefault();
    };
    
    $.removerAdministrador = function (event) {
    	$('.progressBar').show();
    	var selected = dTable.rows('.selected').data();

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
    
    $.init = function() {
    	$("#btnEditarAdministrador").attr( "disabled", true );
        $("#btnRemoverAdministrador").attr( "disabled", true );
    };
    $.init();
    
    $.reload = function() {
    	$.init();
        dTable.ajax.reload();
    };
} );
