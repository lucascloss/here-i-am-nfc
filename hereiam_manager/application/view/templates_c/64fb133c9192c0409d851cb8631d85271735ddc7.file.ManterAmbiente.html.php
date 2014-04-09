<?php /* Smarty version Smarty-3.1.17, created on 2014-04-09 05:22:05
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAmbiente.html" */ ?>
<?php /*%%SmartyHeaderCode:14992470525344b6ab28bb67-54291970%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '64fb133c9192c0409d851cb8631d85271735ddc7' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAmbiente.html',
      1 => 1397013723,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '14992470525344b6ab28bb67-54291970',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_5344b6ab30a933_79837041',
  'variables' => 
  array (
    'acao' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_5344b6ab30a933_79837041')) {function content_5344b6ab30a933_79837041($_smarty_tpl) {?><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalAdicionarTitulo">Adicionar Ambiente</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" method="" action="">
					<div class="form-group">		
						<label for="nome" class="col-sm-5 control-label">Nome</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="nome" placeholder="Nome">
  				 			</div>	
 						</div>			
					<div class="form-group">		
						<label for="tipo" class="col-sm-5 control-label">Tipo</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="tipo" placeholder="Tipo">
  				 			</div>	
					</div>
					<div class="form-group">		
						<label for="info" class="col-sm-5 control-label">Info</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="info" placeholder="Info">
  				 			</div>	
 						</div>								
					<div class="form-group">		
						<label for="latitude" class="col-sm-5 control-label">Latitude</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="latitude" placeholder="Latitude">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="longitude" class="col-sm-5 control-label">Longitude</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="longitude" placeholder="Longitude">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="endereco" class="col-sm-5 control-label">Endere�o</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="endereco" placeholder="Endere�o">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="bairro" class="col-sm-5 control-label">Bairro</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="bairro" placeholder="Bairro">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="numero" class="col-sm-5 control-label">N�mero</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="numero" placeholder="N�mero">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="cidade" class="col-sm-5 control-label">Cidade</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="cidade" placeholder="Cidade">
  				 			</div>	
 						</div>
 						<div class="form-group">		
						<label for="estado" class="col-sm-5 control-label">Estado</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="estado" placeholder="Estado">
  				 			</div>	
 						</div>
 						<div class="form-group">		
						<label for="pais" class="col-sm-5 control-label">Pa�s</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="pais" placeholder="Pa�s">
  				 			</div>	
 						</div>
					<div class="form-group">		
						<label for="listaAdmAmbiente" class="col-sm-5 control-label">Administrador do Ambiente</label>
  							<div class="col-xs-5">
    			 				<select class="form-control" id="listaAdmAmbiente">...</select>
  				 			</div>	
 						</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-success">Adicionar</button>
				</div>
			</div>
<?php } elseif ($_smarty_tpl->tpl_vars['acao']->value=="editar") {?>

<?php } else { ?>
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


	<div class="panel panel-default">
		<div class="panel-heading"><h3>Gerenciar Ambientes</h3></div>
		<div class="panel-body">
 			<div class="form-group">
 			<!--  aqui -->
 			<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manterambiente&acao=adicionar" class="btn btn-primary">Teste</a><br />
 			<!--  aqui  -->
				<button id="btnAdicionar" type="button" class="btn btn-default btn-lg" 
						data-toggle="modal" data-target="#modalAddAmbiente">
					<span class="glyphicon glyphicon-plus"></span>
				</button>
				<button id="btnEditar" type="button" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
				<button id="btnRemover" type="button" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-remove"></span>
				</button>								
			</div>		
			<table class="table table-striped table-bordered" id="listaAdministradores">
				<thead>
					<tr>
						<td><strong>Nome</strong></td>
						<td width="20%"><strong>Usu&aacute;rio</strong></td>
						<td width="30%"><strong>Email</strong></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Guilherme Wunsch</td>
						<td>guiwunsch</td>
						<td>email@guiwunsch.com</td>
					</tr>
					<tr>
						<td>Lucas Closs</td>
						<td>lcloss</td>
						<td>lucas.closs@gmail.com</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="modalAdicionar" tabindex="-1" role="dialog" aria-labelledby="modalAdicionarTitulo" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php }?>
<?php }} ?>
