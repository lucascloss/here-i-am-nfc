<?php

namespace Control;

use Control\Base,
	View\Inicial as FormInicial;

class Inicial extends Base
{

	public function __construct() 
	{
		$this->setForm(new FormInicial());
		parent::__construct();
	}
	
	public function principal()
	{
		$this->ver();
	}
	
	public function ver()
	{
		$this->getForm()->mostrar();
	}
	
}

?>