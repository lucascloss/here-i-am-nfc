<?php /* Smarty version Smarty-3.1.17, created on 2014-04-11 05:28:50
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Login.html" */ ?>
<?php /*%%SmartyHeaderCode:2263087755347617298cbc4-04924088%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '300beed2276904678a16703d2bfac6c64898ee40' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Login.html',
      1 => 1397185631,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '2263087755347617298cbc4-04924088',
  'function' => 
  array (
  ),
  'variables' => 
  array (
    'valores' => 0,
  ),
  'has_nocache_code' => false,
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_53476172a8e576_37627595',
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_53476172a8e576_37627595')) {function content_53476172a8e576_37627595($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMensagem.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>


		<form class="form-signin" role="form" method="post" action="index.php?controle=login&acao=entrar">
			<h2 class="form-signin-heading">Por favor, entrar</h2>
			<input type="text" name="usuario" class="form-control" placeholder="Usu&aacute;rio"<?php if (isset($_smarty_tpl->tpl_vars['valores']->value)) {?> value="<?php echo $_smarty_tpl->tpl_vars['valores']->value['email'];?>
"<?php }?> required autofocus>
			<input type="password" name="senha" class="form-control" placeholder="Senha" required>
			
			<label for="listaLogin" class="col-sm-12 control-label">Acessar como:</label>
			
			<select class="form-control" id="listaLogins">
				<option value="admSolucao">Administrador da Solução</option>
				<option value="admAmbiente">Administrador do Ambiente</option>
				<option value="admLocal">Administrador do Local</option>
			</select>
						
			<label class="checkbox">
				<input type="checkbox" name="lembrar" value="lembrar"<?php if (isset($_smarty_tpl->tpl_vars['valores']->value)) {?> value="<?php if (isset($_smarty_tpl->tpl_vars['valores']->value['lembrar'])) {?>true<?php } else { ?>false<?php }?>"<?php }?>> Lembrar
			</label>
			<button id="btnEntrar" class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
		</form>
		
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
