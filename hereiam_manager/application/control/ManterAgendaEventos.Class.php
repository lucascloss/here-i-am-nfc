<?php

namespace Control;

use Control\Base,
	View\ManterAgendaEventos as FormManterAgendaEventos;

class ManterAgendaEventos extends BaseCRUD
{

	public function __construct() 
	{
		$this->setForm(new FormManterAgendaEventos());
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
	
	public function adicionar()
	{
		$this->getForm()->mostrar();
	}
	
	public function editar($parametros) 
	{
		$this->getForm()->mostrar();
	}
	
	public function salvar() { }
	
	public function remover($parametros) 
	{
		$this->getForm()->mostrar();
	}
	
}

?>