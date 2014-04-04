<?php

namespace View;

use View\Base;

class Login extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('Login.html');
	}
	
}

?>