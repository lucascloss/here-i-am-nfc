<?php

namespace View;

abstract class Base extends \Smarty
{
	
    public function __construct() 
    { 
		parent::__construct();

		$this->template_dir = DIRETORIO_BASE . '/application/view/templates';
		$this->compile_dir = DIRETORIO_BASE . '/application/view/templates_c';
		$this->config_dir = DIRETORIO_BASE . '/application/view/config';
		$this->cache_dir = DIRETORIO_BASE . '/application/view/cache';
	
        $this->caching = false;
    }

	abstract protected function mostrar();
    
    public function atribuirValor($nome, $valor) 
    {
    	$this->assign($nome, $valor);
    }
    
}

?>