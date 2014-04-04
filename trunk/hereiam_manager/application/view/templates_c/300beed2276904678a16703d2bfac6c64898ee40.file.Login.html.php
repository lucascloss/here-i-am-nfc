<?php /* Smarty version Smarty-3.1.17, created on 2014-04-03 06:54:05
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Login.html" */ ?>
<?php /*%%SmartyHeaderCode:1642163954533ce96d63faa5-96340712%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '300beed2276904678a16703d2bfac6c64898ee40' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Login.html',
      1 => 1396499192,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '1642163954533ce96d63faa5-96340712',
  'function' => 
  array (
  ),
  'variables' => 
  array (
    'valores' => 0,
  ),
  'has_nocache_code' => false,
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_533ce96d6e2cc5_83677711',
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_533ce96d6e2cc5_83677711')) {function content_533ce96d6e2cc5_83677711($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

		<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMensagem.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

		<form class="form-signin" role="form" method="post" action="index.php?controle=login&acao=entrar">
			<h2 class="form-signin-heading">Por favor, entrar</h2>
			<input type="email" name="email" class="form-control" placeholder="Email"<?php if (isset($_smarty_tpl->tpl_vars['valores']->value)) {?> value="<?php echo $_smarty_tpl->tpl_vars['valores']->value['email'];?>
"<?php }?> required autofocus>
			<input type="password" name="senha" class="form-control" placeholder="Senha" required>
			<label class="checkbox">
				<input type="checkbox" name="lembrar" value="lembrar"<?php if (isset($_smarty_tpl->tpl_vars['valores']->value)) {?> value="<?php if (isset($_smarty_tpl->tpl_vars['valores']->value['lembrar'])) {?>true<?php } else { ?>false<?php }?>"<?php }?>> Lembrar
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
		</form>
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
