<?php /* Smarty version Smarty-3.1.17, created on 2014-04-12 01:05:47
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Inicial.html" */ ?>
<?php /*%%SmartyHeaderCode:14807032335344b6a743d795-91294125%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '62f5f9e5b489be63b910f81725b86282607803f4' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/Inicial.html',
      1 => 1397257451,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '14807032335344b6a743d795-91294125',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_5344b6a74fb810_14433031',
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_5344b6a74fb810_14433031')) {function content_5344b6a74fb810_14433031($_smarty_tpl) {?><?php echo $_smarty_tpl->getSubTemplate ("FragmentoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoCabecalho.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<?php echo $_smarty_tpl->getSubTemplate ("FragmentoMenu.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>

<div class="panel panel-default">
	<div class="panel-heading"><h2>Bem-vindo ao Gerenciamento <b>Here I Am!</b></h2></div>
		<div class="panel-body">
 			<div class="form-group">
 				<p>O Gerenciamento Here I Am, permite aos Administradores gerenciarem os ambientes e os locais conforme o seu contexto e 
 				suas necessidades!</p>
 				<p>N�o perca tempo, mantenha o seu ambiente ou seu local atualizado!</p>
			</div>
		</div>
	</div>		
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoConteudoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
 
<?php echo $_smarty_tpl->getSubTemplate ("FragmentoRodape.html", $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, null, array(), 0);?>
<?php }} ?>
