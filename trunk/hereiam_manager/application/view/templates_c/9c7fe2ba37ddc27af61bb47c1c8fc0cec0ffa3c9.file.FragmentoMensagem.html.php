<?php /* Smarty version Smarty-3.1.17, created on 2014-04-11 05:28:50
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/FragmentoMensagem.html" */ ?>
<?php /*%%SmartyHeaderCode:34359030353476172a995a9-96478334%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '9c7fe2ba37ddc27af61bb47c1c8fc0cec0ffa3c9' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/FragmentoMensagem.html',
      1 => 1396498766,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '34359030353476172a995a9-96478334',
  'function' => 
  array (
  ),
  'variables' => 
  array (
    'mensagem' => 0,
  ),
  'has_nocache_code' => false,
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_53476172b3a109_99212755',
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_53476172b3a109_99212755')) {function content_53476172b3a109_99212755($_smarty_tpl) {?><?php if (isset($_smarty_tpl->tpl_vars['mensagem']->value)) {?>
	<div class="alert alert-<?php echo $_smarty_tpl->tpl_vars['mensagem']->value['tipo'];?>
"><?php echo $_smarty_tpl->tpl_vars['mensagem']->value['texto'];?>
</div>
<?php }?><?php }} ?>
