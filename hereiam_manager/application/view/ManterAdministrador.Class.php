<?php

namespace View;

use View\Base;

class ManterAdministrador extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('ManterAdministrador.html');
	}
	
}

?>