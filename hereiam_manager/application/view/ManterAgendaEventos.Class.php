<?php

namespace View;

use View\Base;

class ManterAgendaEventos extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('ManterAgendaEventos.html');
	}
	
}

?>