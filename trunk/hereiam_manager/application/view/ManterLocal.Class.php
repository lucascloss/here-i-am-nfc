<?php

namespace View;

use View\Base;

class ManterLocal extends Base
{

	public function __construct() 
	{
		parent::__construct();	
	}

	public function mostrar()
	{
		$this->display('ManterLocal.html');
	}
	
}

?>