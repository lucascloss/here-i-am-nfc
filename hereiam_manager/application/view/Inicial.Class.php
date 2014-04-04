<?php

namespace View;

use View\Base;

class Inicial extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('Inicial.html');
	}
	
}

?>