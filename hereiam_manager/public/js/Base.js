URL_BASE = "http://54.186.1.128:3000/api";

$.extend( true, $.fn.dataTable.defaults, {
	"sDom":
		"<'row'<'col-xs-6'l><'col-xs-6'f>r>"+
		"t"+
		"<'row'<'col-xs-6'i><'col-xs-6'p>>",
	"oLanguage": {
		"sUrl": "libs/datatables/datatables-1.10.0/plugins/i18n/Portuguese-Brasil.json"
	}
} );