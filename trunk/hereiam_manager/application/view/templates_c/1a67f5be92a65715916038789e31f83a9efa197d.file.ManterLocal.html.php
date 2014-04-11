<?php /* Smarty version Smarty-3.1.17, created on 2014-04-12 01:29:40
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterLocal.html" */ ?>
<?php /*%%SmartyHeaderCode:1612445595534764d52c18f3-53098038%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '1a67f5be92a65715916038789e31f83a9efa197d' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterLocal.html',
      1 => 1397258978,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '1612445595534764d52c18f3-53098038',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_534764d533c8b6_57973637',
  'variables' => 
  array (
    'acao' => 0,
    'tipoUsuario' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_534764d533c8b6_57973637')) {function content_534764d533c8b6_57973637($_smarty_tpl) {?><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar"||$_smarty_tpl->tpl_vars['acao']->value=="editar") {?>
			<!-- Modal Local -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalLocal"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?> Local</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" method="" action="">
						<div class="form-group">		
							<label for="nome" class="col-sm-4 control-label">Nome</label>
   							<div class="col-xs-5">
     			 				<input type="text" class="form-control" id="nome" placeholder="Nome" />
   				 			</div>	
  						</div>
   						<div class="form-group">	 
		   					<label for="tipo" class="col-sm-4 control-label">Tipo</label>
		   					<div class="col-xs-5">
		     			 		<input type="text" class="form-control" id="tipo" placeholder="Tipo"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==3) {?> disabled="disabled"<?php }?> />
		   				 	</div>
		   				</div>	 
			  			<div class="form-group">
			   				 <label for="info" class="col-sm-4 control-label">Informa&ccedil;&otilde;es</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="info" placeholder="Informa&ccedil;&otilde;es" />
			   				 </div>
			   			</div>
			   			<div class="form-group">	 
			   				 <label for="latitude" class="col-sm-4 control-label">Latitude</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="latitude" placeholder="Latitude"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value!=1) {?> disabled="disabled"<?php }?> />
			   				 </div>   				    				 
						</div>						
						<div class="form-group">	 
			   				 <label for="longitude" class="col-sm-4 control-label">Longitude</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="longitude" placeholder="Longitude"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value!=1) {?> disabled="disabled"<?php }?> />
			   				 </div>   				    				 
						</div>					
						<div class="form-group">	 
			   				 <label for="listaAmbientes" class="col-sm-4 control-label">Nome do Ambiente</label>
			   				 <div class="col-xs-5">
			     			 	<select class="form-control" id="listaAmbientes"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value!=1) {?> disabled="disabled"<?php }?>>...</select>
			   				 </div>   				    				 
						</div>						
						<div class="form-group">	 
			   				 <label for="listaAdmLocal" class="col-sm-4 control-label">Administrador do Local</label>
			   				 <div class="col-xs-5">
								<select class="form-control" id="listaAdmLocal"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==3) {?> disabled="disabled"<?php }?>>...</select>			     			 	
			   				 </div>   				    				 
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<div class="checkbox">
					    			<label >
					      				<input type="checkbox"<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value!=1) {?> disabled="disabled"<?php }?>>&Eacute; importante?
					    			</label>
					  			</div>
							</div>
						</div>							  
					</form>								
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-success"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?></button>
				</div>
			</div>
<?php } elseif ($_smarty_tpl->tpl_vars['acao']->value=="remover") {?>
			<!-- Modal Remover Local -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalRemoverLocal">Remover Local</h4>
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
	<div class="panel-heading"><h3>Gerenciar Locais</h3></div>
		<div class="panel-body">
 			<div class="form-group">	
	<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==1) {?>			
				<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manterlocal&acao=adicionar" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-plus"></span>
				</a>
	<?php }?>			
				<a data-toggle="modal" data-target="#modalEditar" href="index.php?controle=manterlocal&acao=editar" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-edit"></span>
				</a>
	<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==1) {?>					
				<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manterlocal&acao=remover" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-remove"></span>
				</a>	
	<?php }?>													
			</div>		
			<table class="table table-striped table-bordered" id="listaLocais">
				<thead>
					<tr>
						<td><strong>Nome</strong></td>
						<td width="25%"><strong>Ambiente</strong></td>
						<td width="15%"><strong>Tipo</strong></td>
						<td width="20%"><strong>Administrador</strong></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Panquecas do Alemão</td>
						<td>Unisinos</td>
						<td>Restaurantes</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6</td>
						<td>Unisinos</td>
						<td>Centro</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6A</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6B</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6C</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6D</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6E</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6F</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6G</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6H</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6I</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6J</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6K</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6L</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6M</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6N</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6O</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Centro 6P</td>
						<td>Unisinos</td>
						<td>Bloco</td>
						<td>guiwunsch</td>
					</tr>
					<tr>
						<td>Multicolor</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Branco</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Vermelho</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Lilás</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Conveniência</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Bicolor</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Informática</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Laranja</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Engenharia</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Verde</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Amarelo</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Azul</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
					<tr>
						<td>Arenito</td>
						<td>Feevale</td>
						<td>Pr&eacute;dio</td>
						<td>lcloss</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Modal Adicionar -->
	<div class="modal fade" id="modalAdicionar" tabindex="-1" role="dialog" aria-labelledby="modalLocal" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Editar -->
	<div class="modal fade" id="modalEditar" tabindex="-1" role="dialog" aria-labelledby="modalLocal" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Remover -->
	<div class="modal fade" id="modalRemover" tabindex="-1" role="dialog" aria-labelledby="modalRemoverLocal" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php }?><?php }} ?>
