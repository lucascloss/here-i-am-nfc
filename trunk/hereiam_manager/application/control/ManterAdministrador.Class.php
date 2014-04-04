<?php

namespace Control;

use Control\Base,
	View\ManterAdministrador as FormManterAdministrador;

class ManterAdministrador extends BaseCRUD
{

	public function __construct() 
	{
		$this->setForm(new FormManterAdministrador());
		parent::__construct();
	}
	
	public function principal()
	{
		$this->listar();
	}
	
	public function ver() { }
	
	public function listar()
	{
		$this->getForm()->mostrar();
	}
	
	public function adicionar() { }
	
	public function editar($parametros) { }
	
	public function salvar() { }
	
	public function remover($parametros) { }
	
}

?>