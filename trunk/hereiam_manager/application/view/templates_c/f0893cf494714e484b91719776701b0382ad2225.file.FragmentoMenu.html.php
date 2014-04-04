<?php /* Smarty version Smarty-3.1.17, created on 2014-04-03 07:38:18
         compiled from "/Users/guiwunsch/Projetos/HereIAm/application/view/templates/FragmentoMenu.html" */ ?>
<?php /*%%SmartyHeaderCode:1874557696533ce6af57b006-02767568%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_valid = $_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'f0893cf494714e484b91719776701b0382ad2225' => 
    array (
      0 => '/Users/guiwunsch/Projetos/HereIAm/application/view/templates/FragmentoMenu.html',
      1 => 1396503497,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '1874557696533ce6af57b006-02767568',
  'function' => 
  array (
  ),
  'version' => 'Smarty-3.1.17',
  'unifunc' => 'content_533ce6af57f361_05045637',
  'variables' => 
  array (
    'controle' => 0,
    'parametros' => 0,
  ),
  'has_nocache_code' => false,
),false); /*/%%SmartyHeaderCode%%*/?>
<?php if ($_valid && !is_callable('content_533ce6af57f361_05045637')) {function content_533ce6af57f361_05045637($_smarty_tpl) {?>	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Navegação</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.php">Here I Am</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li<?php if ($_smarty_tpl->tpl_vars['controle']->value=="Inicial") {?> class="active"<?php }?>><a href="index.php">Inicial</a></li>
					<li class="dropdown<?php if ($_smarty_tpl->tpl_vars['controle']->value=="ManterAdministrador") {?> active<?php }?>">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Administradores <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li <?php if (count($_smarty_tpl->tpl_vars['parametros']->value)>0&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=='sistema') {?>class="active"<?php }?>><a href="index.php?controle=manteradministrador&acao=listar&parametros=tipo/sistema/">Sistema</a></li>
							<li <?php if (count($_smarty_tpl->tpl_vars['parametros']->value)>0&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=='ambiente') {?>class="active"<?php }?>><a href="index.php?controle=manteradministrador&acao=listar&parametros=tipo/ambiente/">Ambiente</a></li>
							<li <?php if (count($_smarty_tpl->tpl_vars['parametros']->value)>0&&$_smarty_tpl->tpl_vars['parametros']->value['tipo']=='local') {?>class="active"<?php }?>><a href="index.php?controle=manteradministrador&acao=listar&parametros=tipo/local/">Local</a></li>
						</ul>
					</li>
					<li<?php if ($_smarty_tpl->tpl_vars['controle']->value=="ManterAmbiente") {?> class="active"<?php }?>><a href="index.php?controle=manterambiente">Ambientes</a></li>
					<li<?php if ($_smarty_tpl->tpl_vars['controle']->value=="ManterLocal") {?> class="active"<?php }?>><a href="index.php?controle=manterlocal">Locais</a></li>
					<li<?php if ($_smarty_tpl->tpl_vars['controle']->value=="ManterAgendaEventos") {?> class="active"<?php }?>><a href="index.php?controle=manteragendaeventos">Agenda de eventos</a></li>
					<li<?php if ($_smarty_tpl->tpl_vars['controle']->value=="ManterUsuario") {?> class="active"<?php }?>><a href="index.php?controle=manterusuario">Usu&aacute;rios</a></li>
				</ul>
				<form class="navbar-form navbar-right" method="get" action="index.php" role="logoff">
					<input type="hidden" name="controle" value="login">
					<input type="hidden" name="acao" value="sair">
					<button type="submit" class="btn btn-default">Sair</button>
				</form>
			</div>
		</div>
	</nav><?php }} ?>
