<?php /* Smarty version Smarty-3.1.17, created on 2014-04-12 01:51:49
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAgendaEventos.html" */ ?>
<?php /*%%SmartyHeaderCode:124884996853475c71ca24e7-01397419%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'cf7cc945989913659deb4c8754e569f615c120c8' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAgendaEventos.html',
      1 => 1397260257,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '124884996853475c71ca24e7-01397419',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_53475c71d3dd86_05076440',
  'variables' => 
  array (
    'acao' => 0,
    'tipoUsuario' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_53475c71d3dd86_05076440')) {function content_53475c71d3dd86_05076440($_smarty_tpl) {?><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar"||$_smarty_tpl->tpl_vars['acao']->value=="editar") {?>
			<!-- Modal Evento -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalEvento"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?> Evento</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" method="" action="">
					<div class="form-group">		
						<label for="evento" class="col-sm-5 control-label">Nome do Evento</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="evento" placeholder="Nome do Evento">
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="info" class="col-sm-5 control-label">Informa&ccedil;&otilde;es</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="info" placeholder="Informa&ccedil;&otilde;es">
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="dataInicio" class="col-sm-5 control-label">Data de In&iacute;cio</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="dataInicioIn" placeholder="">    			 				
								<script type=�text/javascript�>								
									$('#dataInicioIn').datepicker();											
								</script>
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="horaInicio" class="col-sm-5 control-label">Hor&aacute;rio de In&iacute;cio</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="horaInicioIn" placeholder="">
    			 				<script type="text/javascript">
	    			 				$('#horaInicioIn').datetimepicker({
	    			 					datepicker:false,
	    			 					format:'H:i'
	    			 				});
						        </script>
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="dataFim" class="col-sm-5 control-label">Data do Encerramento</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="dataFimIn" placeholder="">
    			 				<script type=�text/javascript�>								
									$('#dataFimIn').datepicker();											
								</script>
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="horaFim" class="col-sm-5 control-label">Hor&aacute;rio do Encerramento</label>
  							<div class="col-xs-5">  							
    			 				<input type="text" class="form-control" id="horafimIn" placeholder="">
    			 				<script type="text/javascript">
	    			 				$('#horafimIn').datetimepicker({
	    			 					datepicker:false,
	    			 					format:'H:i'
	    			 				});
						        </script>
					        </div>	
					</div>	
					<div class="form-group">	 
			   			 <label for="listaAmbientes" class="col-sm-5 control-label">Nome do Ambiente</label>
			   			 <div class="col-xs-5">
			     		 	<select class="form-control" id="listaAmbientes"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value!=1) {?> disabled="disabled"<?php }?>>...</select>
			   			 </div>   				    				 
					</div>
					<div class="form-group">	 
			   			 <label for="listaAmbientes" class="col-sm-5 control-label">Nome do Local</label>
			   			 <div class="col-xs-5">
			     		 	<select class="form-control" id="listaLocais"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==3) {?> disabled="disabled"<?php }?>>...</select>
			   			 </div>   				    				 
					</div>								
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-success"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?></button>
				</div>
			</div>
<?php } elseif ($_smarty_tpl->tpl_vars['acao']->value=="remover") {?>
		<!-- Modal Remover Evento -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalRemoverEvento">Remover Evento</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" method="" action="">
					
				</form>				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-danger">Remover</button>
				</div>
			</div>
<?php } else { ?>
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


	<div class="panel panel-default">
		<div class="panel-heading"><h3>Gerenciar Eventos</h3></div>
		<div class="panel-body">
 			<div class="form-group">
 				<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manteragendaeventos&acao=adicionar" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-plus"></span>
				</a>				
				<a data-toggle="modal" data-target="#modalEditar" href="index.php?controle=manteragendaeventos&acao=editar" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-edit"></span>
				</a>				
				<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manteragendaeventos&acao=remover" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-remove"></span>
				</a>								
			</div>		
			<table class="table table-striped table-bordered" id="listaEventos">
				<thead>
					<tr>
						<td><strong>Nome</strong></td>
						<td width="20%"><strong>Ambiente</strong></td>
						<td width="20%"><strong>Local</strong></td>
						<td width="15%"><strong>In&iacute;cio</strong></td>
						<td width="15%"><strong>Fim</strong></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Uniinfo 2014</td>
						<td>Unisinos</td>
						<td>Centro 6</td>
						<td>15/08/2013 14:00:00</td>
						<td>21/08/2013 23:00:00</td>
					</tr>
					<tr>
						<td>Palestra sobre dispositivos m&oacute;veis</td>
						<td>Unisinos</td>
						<td>Centro 6O</td>
						<td>18/04/2013 19:30:00</td>
						<td>18/04/2013 22:00:00</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- Modal Adicionar -->
	<div class="modal fade" id="modalAdicionar" tabindex="-1" role="dialog" aria-labelledby="modalEvento" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Editar -->
	<div class="modal fade" id="modalEditar" tabindex="-1" role="dialog" aria-labelledby="modalEvento" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Remover -->
	<div class="modal fade" id="modalRemover" tabindex="-1" role="dialog" aria-labelledby="modalRemoverEvento" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php }?>
<?php }} ?>
