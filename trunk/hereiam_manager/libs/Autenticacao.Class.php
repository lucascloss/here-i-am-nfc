<?php

namespace Libs;

use Libs\Singleton;

class Autenticacao extends Singleton
{
	private static $usuario;
	
	public function __construct() { }
	
	static public function getUsuario()
    {
        return self::$usuario;
    }
	
	static private function setUsuario($obj)
    {
        self::$usuario = $obj;
    }
	
	static public function autenticado() {
		try {
	        if(isset($_SESSION['autenticado']) && $_SESSION['autenticado'] == true) {
	        	
	        	//$usuario = $_SESSION['usuario'];
	        	//$usuario = $em->find('Modelo\Usuario', $usuario);

	        	//self::setUsuario($usuario);
				return true;
	        }
			else {
				return false;
			}
		} catch (\Exception $e) {
			return false;
		}
	}
	
	static public function sairdosistema() {
		self::limpasessao();
		
		header('Location: index.php?controle=login');
		die();
	}
	
	static public function limpasessao() {
		if(isset($_SESSION["autenticado"]))
		{
			$_SESSION["autenticado"] = false;
		}
		if(isset($_SESSION["usuario"]))
		{
			$_SESSION['usuario'] = null;
			unset($_SESSION['usuario']);
		}
	}
}

?>