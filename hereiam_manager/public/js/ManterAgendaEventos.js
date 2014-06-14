$(document).ready(function() {
	WS_URL = URL_BASE + "/calendars";
	
	$('.progressBar').show();
    $('#listaEventos').dataTable( {
        "ajax": URL_BASE + "/calendars",
        "ajaxDataProp": "",
        "columns": [
            { "data": "id" },
            { "data": "event" },
            { "data": "environmentId" },
            { "data": "placeId" },
            { "data": "begin" },
            { "data": "ends" }  
        ]
    } );
     
    $('.progressBar').hide();        
    
    $('#listaEventos tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
        var selected = $("#listaEventos").DataTable().rows('.selected').data();
        $("#btnEditarEvento").attr( "disabled", (selected.length != 1) );
        $("#btnRemoverEvento").attr( "disabled", (selected.length == 0) );
    } );
    
    $('#modalAdicionar').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.adicionarEvento);
    	
    	$('#dateBegin').datepicker();
        $('#timeBegin').datetimepicker({
    			datepicker:false,
    			format:'H:i'
    	});
        $('#dateEnd').datepicker();
        $('#timeEnd').datetimepicker({
    			datepicker:false,
    			format:'H:i'
    	});
    });
        
    $('#modalAdicionar').on('show.bs.modal', function (e) {
    	$('.progressBar').show();
    	
    	$.ajax({
         	type: "GET",
         	url: URL_BASE + "/environments",        	
         	success: function(data) {         		        		
         		$('#adicionarEvento #environments option').remove();
         		for (var i = 0; i < data.length; i++) {         			
         			$('#adicionarEvento #environments').append($("<option></option>").text(data[i].name).val(data[i].id));
         		}
             } , 
             dataType: "json"
        }); 	  
    	
    	$.ajax({
         	type: "GET",
         	url: URL_BASE + "/places",        	
         	success: function(data) {         		        		
         		$('#adicionarEvento #places option').remove();
         		for (var i = 0; i < data.length; i++) {         			
         			$('#adicionarEvento #places').append($("<option></option>").text(data[i].name).val(data[i].id));
         		}
             } , 
             dataType: "json"
        }); 	  
    });
    
    $('#modalEditar').on('loaded.bs.modal', function (e) {    	    	    	    
	    $(this).find("form").submit($.editarEvento);
	    
	    $('#dateBegin').datepicker();
        $('#timeBegin').datetimepicker({
    			datepicker:false,
    			format:'H:i'
    	});
        $('#dateEnd').datepicker();
        $('#timeEnd').datetimepicker({
    			datepicker:false,
    			format:'H:i'
    	});
    });
    
    $('#modalEditar').on('show.bs.modal', function (e) {
    	var selected = $("#listaEventos").DataTable().rows('.selected').data();
    	
    	$('.progressBar').show();
	    
	    $.ajax({
        	type: "GET",
        	url: WS_URL + "/" + selected[0].id,        	
        	success: function(data) {
        		$("#editarEvento #event").val(data.event);        		
        		$("#editarEvento #info").val(data.info);
        		
        		var begins = data.begin.split("#");
        		
        		$("#editarEvento #dateBegin").val(begins[0]);
        		$("#editarEvento #timeBegin").val(begins[1]);
        		
        		var ends = data.ends.split("#");
        		
        		$("#editarEvento #dateEnd").val(ends[0]);
        		$("#editarEvento #timeEnd").val(ends[1]);
        	  
        	    $.ajax({
                 	type: "GET",
                 	url: URL_BASE + "/environments",        	
                 	success: function(data1) {         		        		
                 		$('#editarEvento #environments option').remove();
                 		for (var i = 0; i < data1.length; i++) {         			
                 			$('#editarEvento #environments').append($("<option></option>").text(data1[i].name).val(data1[i].id));
                 		}
                     } , 
                     dataType: "json"
                }); 	  
            	
            	$.ajax({
                 	type: "GET",
                 	url: URL_BASE + "/places",        	
                 	success: function(data2) {         		        		
                 		$('#adicionarEvento #places option').remove();
                 		for (var i = 0; i < data2.length; i++) {         			
                 			$('#editarEvento #places').append($("<option></option>").text(data2[i].name).val(data2[i].id));
                 		}
                     } , 
                     dataType: "json"
                }); 	
            	
        		$('.progressBar').hide();
            }, 
            dataType: "json"
        });
    });
    
    $('#modalRemover').on('loaded.bs.modal', function (e) {
    	$(this).find("form").submit($.removerEvento);
    });
    
    $.adicionarEvento = function (event) {
    	$('.progressBar').show();
    	
    	var obj = new Object();    	
    	obj.event = $("#adicionarEvento #event").val();    	    	
    	obj.info = $("#adicionarEvento #info").val();
    	obj.begin = $("#adicionarEvento #dateBegin").val() + "#" + $("#adicionarEvento #timeBegin").val();
    	obj.ends = $("#adicionarEvento #dateEnd").val() + "#" + $("#adicionarEvento #timeEnd").val();
    	obj.environmentId = $("#adicionarEvento #environments").val();
    	obj.placeId = $("#adicionarEvento #places").val();
    	
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
    
    $.editarEvento = function (event) {
    	$('.progressBar').show();
    	
    	var selected = $("#listaEventos").DataTable().rows('.selected').data();
    	
    	if (selected.length == 1) {    		    	
	    	var obj = new Object();
	    	obj.event = $("#editarEvento #event").val();    	    	
	    	obj.info = $("#editarEvento #info").val();
	    	obj.begin = $("#editarEvento #dateBegin").val() + "#" + $("#editarEvento #timeBegin").val();
	    	obj.ends = $("#editarEvento #dateEnd").val() + "#" + $("#editarEvento #timeEnd").val();
	    	obj.environmentId = $("#editarEvento #environments").val();
	    	obj.placeId = $("#editarEvento #places").val();
        	    	   	
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
        
    $.removerEvento = function (event) {
    	$('.progressBar').show();
    	var selected = $("#listaEventos").DataTable().rows('.selected').data();

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
    	$("#btnEditarEvento").attr( "disabled", true );
        $("#btnRemoverEvento").attr( "disabled", true );
    };
    $init();
    
    $.reload = function() {        
    	$init();
        $("#listaEventos").dataTable()._fnAjaxUpdate();
    };
    
});
