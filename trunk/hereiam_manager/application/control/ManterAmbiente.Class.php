<?php

namespace Control;

use Control\Base,
	View\ManterAmbiente as FormManterAmbiente;

class ManterAmbiente extends BaseCRUD
{

	public function __construct() 
	{
		$this->setForm(new FormManterAmbiente());
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