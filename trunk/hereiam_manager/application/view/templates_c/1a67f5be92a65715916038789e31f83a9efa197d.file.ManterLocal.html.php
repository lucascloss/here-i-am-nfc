<?php /* Smarty version Smarty-3.1.17, created on 2014-04-08 05:10:23
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterLocal.html" */ ?>
<?php /*%%SmartyHeaderCode:137831637053420b4378d396-90405534%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '1a67f5be92a65715916038789e31f83a9efa197d' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterLocal.html',
      1 => 1396926365,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '137831637053420b4378d396-90405534',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_53420b437d6cf2_03109518',
  'variables' => 
  array (
    'parametros' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_53420b437d6cf2_03109518')) {function content_53420b437d6cf2_03109518($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


<?php if (!isset($_smarty_tpl->tpl_vars['acao'])) $_smarty_tpl->tpl_vars['acao'] = new Smarty_Variable(null);if ($_smarty_tpl->tpl_vars['acao']->value = "listar") {?>
<div class="panel panel-default">
<!-- 		<div class="panel-heading"><h3>Gerenciar administradores do <?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>sistema<?php } elseif (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>ambiente<?php } else { ?>local<?php }?></h3></div> -->
	<div class="panel-heading"><h3>Gerenciar Locais</h3></div>
		<div class="panel-body">
 			<div class="form-group">
				<button id="btnAdicionar" type="button" class="btn btn-default btn-lg" 
						data-toggle="modal" data-target="#modalAddLocal">
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

<!-- Modal Adicionar Local -->
	<div class="modal fade" id="modalAddLocal" tabindex="-1" role="dialog" aria-labelledby="modalLocalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalLocalLabel">Adicionar Local</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" method="" action="">
						<div class="form-group">		
							<label for="nome" class="col-sm-4 control-label">Nome</label>
   							<div class="col-xs-5">
     			 				<input type="text" class="form-control" id="nome" placeholder="Nome">
   				 			</div>	
  						</div>
   						<div class="form-group">	 
		   					<label for="tipo" class="col-sm-4 control-label">Tipo</label>
		   					<div class="col-xs-5">
		     			 		<input type="text" class="form-control" id="tipo" placeholder="Tipo">
		   				 	</div>
		   				</div>	 
			  			<div class="form-group">
			   				 <label for="info" class="col-sm-4 control-label">Informações</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="info" placeholder="Info">
			   				 </div>
			   			</div>
			   			<div class="form-group">	 
			   				 <label for="latitude" class="col-sm-4 control-label">Latitude</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="latitude" placeholder="Latitude">
			   				 </div>   				    				 
						</div>
						
						<div class="form-group">	 
			   				 <label for="longitude" class="col-sm-4 control-label">Longitude</label>
			   				 <div class="col-xs-5">
			     			 	<input type="text" class="form-control" id="longitude" placeholder="Longitude">
			   				 </div>   				    				 
						</div>
						
						<div class="form-group">	 
			   				 <label for="listaAmbientes" class="col-sm-4 control-label">Nome do Ambiente</label>
			   				 <div class="col-xs-5">
			     			 	<select class="form-control" id="listaAmbientes">...</select>
			   				 </div>   				    				 
						</div>
						
						<div class="form-group">	 
			   				 <label for="listaAdmLocal" class="col-sm-4 control-label">Administrador do Local</label>
			   				 <div class="col-xs-5">
								<select class="form-control" id="listaAdmLocal">...</select>			     			 	
			   				 </div>   				    				 
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<div class="checkbox">
					    			<label >
					      				<input type="checkbox">É importante?
					    			</label>
					  			</div>
							</div>
						</div>							  
					</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-success">Adicionar</button>
				</div>
			</div>
		</div>
	</div>
<?php }?>
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
