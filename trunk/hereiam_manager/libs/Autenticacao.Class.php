<?php

namespace Libs;

use Libs\Singleton;

class Autenticacao extends Singleton
{
	private static $usuario;
	private static $tipoUsuario;
	
	public function __construct() { }
	
	static public function getUsuario()
    {
        return self::$usuario;
    }
    
    static public function getTipoUsuario()
    {
    	return self::$tipoUsuario;	
    }
    
	static public function autenticado() {
		try {
	        if (isset($_SESSION['autenticado']) && $_SESSION['autenticado'] == true) {
	        	
	        	self::$usuario = $_SESSION['usuario'];
	        	
	        	// Teste temporário //
	        	switch (self::$usuario) {
	        		case "admin@solucao":
	        			self::$tipoUsuario = 1;
	        			break;
	        		case "admin@ambiente":
	        			self::$tipoUsuario = 2;
	        			break;
	        		case "admin@local":
	        			self::$tipoUsuario = 3;
	        			break;
	        	}
	        	
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