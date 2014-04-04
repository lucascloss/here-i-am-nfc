<?php /* Smarty version Smarty-3.1.17, created on 2014-04-03 07:53:04
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html" */ ?>
<?php /*%%SmartyHeaderCode:222487434533cf0978f22f0-12499512%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a66fe520dfab334307d6dd121a71ac1b9e2a43ab' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterAdministrador.html',
      1 => 1396504381,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '222487434533cf0978f22f0-12499512',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_533cf097930fe8_99079376',
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_533cf097930fe8_99079376')) {function content_533cf097930fe8_99079376($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


	<div class="panel panel-default">
		<div class="panel-heading">Gerenciar administradores
		<!-- <div class="pull-right"> -->
						<ul class="pagination pagination-sm pull-right">
							<li class="disabled"><a href="#">&laquo;</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">&raquo;</a></li>
						</ul><!-- </div> --></div>
		<div class="panel-body">
			<p>...</p>
		</div>
	
		<!-- Table -->
		<table class="table">
			<thead>
				<tr>
					<td>Coluna 1</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td>
					</td>
				</tr>
			</tfoot>
		</table>
		<div class="panel-footer">
		</div>
	</div>
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
