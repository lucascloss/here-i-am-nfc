<?php /* Smarty version Smarty-3.1.17, created on 2014-04-07 04:16:51
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html" */ ?>
<?php /*%%SmartyHeaderCode:20552811325341fff55a01b7-69510640%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a66fe520dfab334307d6dd121a71ac1b9e2a43ab' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html',
      1 => 1396836891,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '20552811325341fff55a01b7-69510640',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_5341fff564eef1_44119260',
  'variables' => 
  array (
    'parametros' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_5341fff564eef1_44119260')) {function content_5341fff564eef1_44119260($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


<?php if (!isset($_smarty_tpl->tpl_vars['acao'])) $_smarty_tpl->tpl_vars['acao'] = new Smarty_Variable(null);if ($_smarty_tpl->tpl_vars['acao']->value = "listar") {?>
	<div class="panel panel-default">
		<div class="panel-heading"><h3>Gerenciar administradores do <?php if (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="sistema") {?>sistema<?php } elseif (isset($_smarty_tpl->tpl_vars['parametros']->value)&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=="ambiente") {?>ambiente<?php } else { ?>local<?php }?></h3></div>
		<div class="panel-body">
 			<div class="form-group">
				<button id="btnAdicionar" type="button" class="btn btn-default btn-lg">
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
<?php  } else { if (!isset($_smarty_tpl->tpl_vars['acao'])) $_smarty_tpl->tpl_vars['acao'] = new Smarty_Variable(null);if ($_smarty_tpl->tpl_vars['acao']->value = "adicionar") {?>
<?php }}?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
