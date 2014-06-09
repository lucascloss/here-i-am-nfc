<?php

use Libs\Carregador,
	Libs\Roteador;
	
define('DIRETORIO_BASE', __DIR__ . '/..');
define('URL_BASE', '/HereIAm');

require_once DIRETORIO_BASE . '/Libs/Singleton.Class.php';
require_once DIRETORIO_BASE . '/Libs/Carregador.Class.php';	
require_once DIRETORIO_BASE . '/Libs/Roteador.Class.php';
require_once DIRETORIO_BASE . '/Libs/smarty/smarty-3.1.17/Smarty.class.php';

$carregador = Carregador::getInstancia();

$carregador->setDiretorioApp(DIRETORIO_BASE . '/application');
$carregador->setDiretorioLibs(DIRETORIO_BASE);

$carregador->registrar();

// Inicializa a sessão
session_start();

// Captura ação corrente na requisição
$roteador = Roteador::inicializar();

$reqControle = $carregador->getControleNamespace() . '\\' . $roteador->getControle();
$controle = new $reqControle;

call_user_func(array($controle, $roteador->getAcao()), $roteador->getParametros());

?>