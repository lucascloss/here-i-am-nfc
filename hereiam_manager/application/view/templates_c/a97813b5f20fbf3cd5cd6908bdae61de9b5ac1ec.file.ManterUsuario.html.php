<?php /* Smarty version Smarty-3.1.17, created on 2014-04-12 01:43:08
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterUsuario.html" */ ?>
<?php /*%%SmartyHeaderCode:1999576354534768d9521c60-00374438%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a97813b5f20fbf3cd5cd6908bdae61de9b5ac1ec' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/ManterUsuario.html',
      1 => 1397259535,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '1999576354534768d9521c60-00374438',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_534768d957c6c8_15125101',
  'variables' => 
  array (
    'acao' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_534768d957c6c8_15125101')) {function content_534768d957c6c8_15125101($_smarty_tpl) {?><?php if ($_smarty_tpl->tpl_vars['acao']->value=="remover") {?>
		<!-- Modal Remover Usu�rio-->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modalRemoverUsuario">Remover Usu&aacute;rio</h4>
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
	<div class="panel-heading"><h3>Gerenciar Usu&aacute;rios</h3></div>
		<div class="panel-body">
 			<div class="form-group">										
			<a data-toggle="modal" data-target="#modalRemover" href="index.php?controle=manterusuario&acao=remover" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-remove"></span>
			</a>																													
			</div>		
			<table class="table table-striped table-bordered" id="listaUsuarios">
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
						<td>Henrique Schuster</td>
						<td>henrique.schuster</td>
						<td>henrique.schuster@gmail.com</td>
					</tr>
					<tr>
						<td>Lucas Closs</td>
						<td>lcloss</td>
						<td>lucas.closs@gmail.com</td>
					</tr>
					<tr>
						<td>M&aacute;rcio Silveira</td>
						<td>marcio.f.silveira</td>
						<td>marcio.f.silveira@gmail.com</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Modal Remover -->
	<div class="modal fade" id="modalRemover" tabindex="-1" role="dialog" aria-labelledby="modalRemoverUsuario" aria-hidden="true">
		<div class="modal-dialog">
	    	<div class="modal-content">
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->		
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php }?><?php }} ?>
