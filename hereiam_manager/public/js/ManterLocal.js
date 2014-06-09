$(document).ready(function() {
	WS_URL = URL_BASE + "/places";
	
    $('#listaLocais').DataTable( {
        "ajax": WS_URL,
        "ajaxDataProp": "",
        "columns": [
            { "data": "name" },
            { "data": "environmentId" },
            { "data": "type" },
            { "data": "placeAdmId" }     
        ]
    } );
    
    $("#btnEditarLocal").prop( "disabled", true );
    $("#btnRemoverLocal").prop( "disabled", true );
    
    $('#listaLocais tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
        var selected = $("#listaLocais").DataTable().rows('.selected').data();
        $("#btnEditarLocal").prop( "disabled", (selected.length != 1) );
        $("#btnRemoverLocal").prop( "disabled", (selected.length == 0) );
    } );
    
    $('#modalAdicionar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.adicionarLocal);
    });
    
    $('#modalEditar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.editarLocal);
    });
    
    $('#modalRemover').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.removerLocal);
    });
    
    $.adicionarLocal = function (event) {
    	var obj = new Object();
        obj.name = $("#name").val();
        obj.type  = $("#type").val();
        obj.info = $("#info").val();
        obj.idNfc = $("#idNfc").val();
        obj.latitude = $("#latitude").val();
        obj.longitude = $("#longitude").val();
        obj.environmentId = $("#environmentId").val();
        obj.placeAdmId = $("#placeAdmId").val();
        obj.important = $("#important").val();
        
        $.ajax({
        	type: "POST",
        	url: WS_URL,
        	data: obj,
        	success: function(data) {
        		$("#modalAdicionar").modal("hide");
        		$("#listaLocais").dataTable()._fnAjaxUpdate();
            }, 
        	dataType: "json"
        });
        
        event.preventDefault();
    };
    
    $.editarLocal = function (event) {
    	var selected = $("#listaLocais").DataTable().rows('.selected').data();

    	if (selected.length == 1) {
	    	var obj = new Object();
	        obj.name = $("#name").val();
	        obj.type  = $("#type").val();
	        obj.info = $("#info").val();
	        obj.idNfc = $("#idNfc").val();
	        obj.latitude = $("#latitude").val();
	        obj.longitude = $("#longitude").val();
	        obj.environmentId = $("#environmentId").val();
	        obj.placeAdmId = $("#placeAdmId").val();
	        obj.important = $("#important").val();
	        
	        $.ajax({
	        	type: "PUT",
	        	url: WS_URL + "/" + selected[0].id,
	        	data: obj,
	        	success: function(data) {
	        		$("#modalEditar").modal("hide");
	        		$("#listaLocais").dataTable()._fnAjaxUpdate();
	            }, 
	        	dataType: "json"
	        });
    	}
        
    	event.preventDefault();
    };
    
    $.removerLocal = function (event) {
    	var selected = $("#listaLocais").DataTable().rows('.selected').data();

    	if (selected.length > 0) {
    		for (var i = 0; i < selected.length; i++) {
		        $.ajax({
		        	type: "DELETE",
		        	url: WS_URL + "/" + selected[i].id,
		        	success: function(data) {
		        		$("#modalRemover").modal("hide");
		        		$("#listaLocais").dataTable()._fnAjaxUpdate();
		            }, 
		        	dataType: "json"
		        });
    		}
    	}

    	event.preventDefault();
    };
    
});