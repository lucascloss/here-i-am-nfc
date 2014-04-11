<?php /* Smarty version Smarty-3.1.17, created on 2014-04-12 01:05:39
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html" */ ?>
<?php /*%%SmartyHeaderCode:58896515953476370814185-42150476%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a66fe520dfab334307d6dd121a71ac1b9e2a43ab' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html',
      1 => 1397257451,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '58896515953476370814185-42150476',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_5347637091d6f9_58531415',
  'variables' => 
  array (
    'acao' => 0,
    'parametros' => 0,
    'tipoUsuario' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_5347637091d6f9_58531415')) {function content_5347637091d6f9_58531415($_smarty_tpl) {?><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar"||$_smarty_tpl->tpl_vars['acao']->value=="editar") {?>
		<!-- Modal Adicionar-Editar Administrador -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalAdm"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?> Administrador 
					do <?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>sistema<?php } elseif (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>ambiente<?php } else { ?>local<?php }?></h4>
				</div>
				<div class="modal-body">
				<form class="form-horizontal" role="form" method="" action="">
					<div class="form-group">		
						<label for="nome" class="col-sm-5 control-label">Nome</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="nome" placeholder="Nome" />
  				 			</div>	
 					</div> 					
 					<div class="form-group">		
						<label for="usuario" class="col-sm-5 control-label">Usu&aacute;rio</label>
  							<div class="col-xs-5">
    			 				<input type="text" class="form-control" id="usuario" placeholder="Usu&aacute;rio" <?php if ($_smarty_tpl->tpl_vars['acao']->value=="editar") {?> disabled="disabled"<?php }?>/>
  				 			</div>	
 					</div>			 					
 					<div class="form-group">		
						<label for="email" class="col-sm-5 control-label">Email</label>
  							<div class="col-xs-5">
    			 				<input type="email" class="form-control" id="email" placeholder="Email" />
  				 			</div>	
 					</div>			 					
 					<div class="form-group">		
						<label for="senha" class="col-sm-5 control-label">Senha</label>
  							<div class="col-xs-5">
    			 				<input type="password" class="form-control" id="senha" placeholder="Senha" />
  				 			</div>	
 					</div> 					
 					<div class="form-group">		
						<label for="confsenha" class="col-sm-5 control-label">Confirma&ccedil;&atilde;o Senha</label>
  							<div class="col-xs-5">
    			 				<input type="password" class="form-control" id="confsenha" placeholder="Confirma&ccedil;&atilde;o Senha" />
  				 			</div>	
 					</div>														
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-success"><?php if ($_smarty_tpl->tpl_vars['acao']->value=="adicionar") {?>Adicionar<?php } else { ?>Editar<?php }?></button>
				</div>
			</div>
<?php } elseif ($_smarty_tpl->tpl_vars['acao']->value=="remover") {?>
		<!-- Modal Remover Administrador -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalRemoverAdm">Remover Administrador 
					do <?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>sistema<?php } elseif (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>ambiente<?php } else { ?>local<?php }?></h4>
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
		<div class="panel-heading"><h3>Gerenciar administradores do <?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>sistema<?php } elseif (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>ambiente<?php } else { ?>local<?php }?></h3></div>
		<div class="panel-body">
 			<div class="form-group">
	<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==1) {?>
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>
			<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manteradministrador&acao=adicionar&parametros=tipo/sistema/" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-plus"></span>
			</a>
		<?php }?>		
		
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>
			<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manteradministrador&acao=adicionar&parametros=tipo/ambiente/" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-plus"></span>
			</a>
		<?php }?>
		
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="local") {?>
			<a data-toggle="modal" data-target="#modalAdicionar" href="index.php?controle=manteradministrador&acao=adicionar&parametros=tipo/local/" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-plus"></span>
			</a>
		<?php }?>
	<?php }?>		
	
	<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>
		<a data-toggle="modal" data-target="#modalEditar" href="index.php?controle=manteradministrador&acao=editar&parametros=tipo/sistema/" class="btn btn-default btn-lg">
			<span class="glyphicon glyphicon-edit"></span>
		</a>
	<?php }?>		
	
	<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>
		<a data-toggle="modal" data-target="#modalEditar" href="index.php?controle=manteradministrador&acao=editar&parametros=tipo/ambiente/" class="btn btn-default btn-lg">
			<span class="glyphicon glyphicon-edit"></span>
		</a>
	<?php }?>
	
	<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="local") {?>
		<a data-toggle="modal" data-target="#modalEditar" href="index.php?controle=manteradministrador&acao=editar&parametros=tipo/local/" class="btn btn-default btn-lg">
			<span class="glyphicon glyphicon-edit"></span>
		</a>
	<?php }?>
		
	<?php if ($_smarty_tpl->tpl_vars['tipoUsuario']->value==1) {?>	
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>
		<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manteradministrador&acao=remover&parametros=tipo/sistema/" class="btn btn-default btn-lg">
			<span class="glyphicon glyphicon-remove"></span>
		</a>
		<?php }?>		
		
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>
			<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manteradministrador&acao=remover&parametros=tipo/ambiente/" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-remove"></span>
			</a>
		<?php }?>
		
		<?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="local") {?>
			<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manteradministrador&acao=remover&parametros=tipo/local/" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-remove"></span>
			</a>
		<?php }?>
	<?php }?>
				<!--  <input type="text" id="teste" />-->
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
	
	<!-- Modal Adicionar -->
	<div class="modal fade" id="modalAdicionar" tabindex="-1" role="dialog" aria-labelledby="modalAdministrador" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Editar -->
	<div class="modal fade" id="modalEditar" tabindex="-1" role="dialog" aria-labelledby="modalAdm" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- Modal Remover -->
	<div class="modal fade" id="modalRemover" tabindex="-1" role="dialog" aria-labelledby="modalRemoverAdm" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php }?><?php }} ?>
