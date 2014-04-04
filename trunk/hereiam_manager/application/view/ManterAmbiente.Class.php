<?php

namespace View;

use View\Base;

class ManterAmbiente extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('ManterAmbiente.html');
	}
	
}

?>