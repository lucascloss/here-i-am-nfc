<?php

namespace View;

use View\Base;

class ManterUsuario extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('ManterUsuario.html');
	}
	
}

?>