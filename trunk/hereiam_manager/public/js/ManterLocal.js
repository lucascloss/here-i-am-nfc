$(document).ready(function() {
	WS_URL = URL_BASE + "/places";
	
    $('#listaLocais').DataTable( {
    	"ajax": WS_URL,
        "ajaxDataProp": "",
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "environmentId" },
            { "data": "type" },
            { "data": "placeAdmId" }     
        ]        
    } );
    
    $('#listaLocais tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
        var selected = $("#listaLocais").DataTable().rows('.selected').data();
        $("#btnEditarLocal").attr( "disabled", (selected.length != 1) );
        $("#btnRemoverLocal").attr( "disabled", (selected.length == 0) );
    } );
    
    $('#modalAdicionar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.adicionarLocal);
    });
        
    $('#modalAdicionar').on('show.bs.modal', function (e) {
    	$('.progressBar').show();
 		
 		$.ajax({
         	type: "GET",
         	url: URL_BASE + "/environments",        	
         	success: function(data) {
         		$('#adicionarLocal #environmentId option').remove();
         		for (var i = 0; i < data.length; i++) {         			
         			$('#adicionarLocal #environmentId').append($("<option></option>").text(data[i].name).val(data[i].id));
         		}
             } , 
             dataType: "json"
        });

 	    $.ajax({
         	type: "GET",
         	url: URL_BASE + "/placeAdms",        	
         	success: function(data) {
         		$('#adicionarLocal #placeAdmId option').remove();         		
         		for (var i = 0; i < data.length; i++) {         			
         			$('#adicionarLocal #placeAdmId').append($("<option></option>").text(data[i].name).val(data[i].id));
         		}
         		
         		$('.progressBar').hide();
             } , 
             dataType: "json"
        });   
    });
      
    $('#modalEditar').on('loaded.bs.modal', function (e) {    	    	    	    
	    $(this).find("form").submit($.editarLocal);
	    
	    $('#editarLocal #latitude').attr( 'disabled', true );
	    $('#editarLocal #longitude').attr( 'disabled', true );
	    $('#editarLocal #idNfc').attr( 'disabled', true );
	    $('#editarLocal #environmentId').attr( 'disabled', true );
	    $('#editarLocal #placeAdmId').attr( 'disabled', true );
    });
    
    $('#modalEditar').on('show.bs.modal', function (e) {
    	var selected = $("#listaLocais").DataTable().rows('.selected').data();
    	
    	$('.progressBar').show();
	    
	    $.ajax({
        	type: "GET",
        	url: WS_URL + "/" + selected[0].id,        	
        	success: function(data) {
        		$("#editarLocal #name").val(data.name);
        		$("#editarLocal #type").val(data.type);
        		$("#editarLocal #info").val(data.info);
        		$("#editarLocal #idNfc").val(data.idNfc);
        		$("#editarLocal #latitude").val(data.latitude);
        		$("#editarLocal #longitude").val(data.longitude);
        		
        		if ((data.environmentId != null) && (data.environmentId > 0)) {
	        		$.ajax({
	                	type: "GET",
	                	url: URL_BASE + "/environments/" + data.environmentId,        	
	                	success: function(data1) {                		
	                		$('#editarLocal #environmentId option').remove();
	                		$('#editarLocal #environmentId').append($("<option></option>").text(data1.name).attr( "selected", "selected" ));                		        	           		        	
	                    } , 
	                    dataType: "json"
	                });
        		}
        		
        		if ((data.placeAdmId != null) && (data.placeAdmId > 0)) {
	        	    $.ajax({
	                	type: "GET",
	                	url: URL_BASE + "/placeAdms/" + data.placeAdmId,        	
	                	success: function(data2) {
	                		$('#editarLocal #placeAdmId option').remove();
	                		$('#editarLocal #placeAdmId').append($("<option></option>").text(data2.name).attr( "selected", "selected" ));        	           		        	
	                    } , 
	                    dataType: "json"
	                });
        		}
        		
        		if(data.important == true){
        			$('#editarLocal #important').attr('checked', true);
        		} else {
        			$('#editarLocal #important').attr('checked', false);
        		}
        		
        		$('.progressBar').hide();
            }, 
            dataType: "json"
        });
    });
    
    $('#modalRemover').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.removerLocal);
    });
    
    $.adicionarLocal = function (event) {
    	$('.progressBar').show();
    	
    	var obj = new Object();
        obj.name = $("#adicionarLocal #name").val();
        obj.type  = $("#adicionarLocal #type").val();
        obj.info = $("#adicionarLocal #info").val();
        obj.idNfc = $("#adicionarLocal #idNfc").val();
        obj.latitude = $("#adicionarLocal #latitude").val();
        obj.longitude = $("#adicionarLocal #longitude").val();
        obj.environmentId = $("#adicionarLocal #environmentId").val();
        obj.placeAdmId = $("#adicionarLocal #placeAdmId").val();
        obj.important = $("#adicionarLocal #important").val();
        
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
    
    $.editarLocal = function (event) {
    	$('.progressBar').show();
    	
    	var selected = $("#listaLocais").DataTable().rows('.selected').data();
    	
    	if (selected.length == 1) {
	    	var obj = new Object();
	        obj.name = $("#editarLocal #name").val();
	        obj.type  = $("#editarLocal #type").val();
	        obj.info = $("#editarLocal #info").val();
	        obj.idNfc = $("#editarLocal #idNfc").val();
	        obj.latitude = $("#editarLocal #latitude").val();
	        obj.longitude = $("#editarLocal #longitude").val();
	        //obj.environmentId = $("#editarLocal #environmentId").val();
	        //obj.placeAdmId = $("#editarLocal #placeAdmId").val();
	        obj.important = $("#editarLocal #important").val();
	        
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
    
    $.removerLocal = function (event) {
    	$('.progressBar').show();
    	var selected = $("#listaLocais").DataTable().rows('.selected').data();

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
    	$("#btnEditarLocal").attr( "disabled", true );
        $("#btnRemoverLocal").attr( "disabled", true );
    };
    $init();
    
    $.reload = function() {
    	$init();
        $("#listaLocais").dataTable()._fnAjaxUpdate();
    };
    
});