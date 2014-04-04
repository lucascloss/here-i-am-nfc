<?php

namespace Control;

use Control\Base,
	Libs\Roteador;

abstract class BaseCRUD extends Base
{
	
	abstract protected function listar();
	
	abstract protected function adicionar();
	
	abstract protected function editar($parametros);
	
	abstract protected function salvar();
	
	abstract protected function remover($parametros);
	
	public function __construct()
	{
		parent::__construct();
	}
	
}

?>