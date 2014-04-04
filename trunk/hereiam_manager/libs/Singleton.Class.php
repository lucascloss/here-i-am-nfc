<?php

namespace Libs;

abstract class Singleton 
{
	
	protected static $instancias = array();

	/*
	 * Classe base para trabalhar com o padrão singleton. Basta extendê-la.
	 */
	protected function __construct() { }
	
	/*
	 * Método responsável por fazer a inicialização das configurações padrão da classe.
	 */
	final public static function inicializar() { 
		$args = func_get_args();
		$classe = strtolower(get_called_class());
		self::$instancias[$classe] = new $classe($args);
		
		return self::$instancias[$classe];
	}
	
	/*
	 * Método para pegar a instãncia da classe.
	 */
	final public static function getInstancia() 
	{
		$args = func_get_args();
		$classe = strtolower(get_called_class());
		if (!isset(self::$instancias[$classe])) {
			$classe::inicializar($args);
		}
		
		return self::$instancias[$classe];
	}
	
	/*
	 * Faz com que não seja possível trabalhar com mais de uma instância dessa classe.
	 */
	final public function __clone() 
	{
		throw new Exception('Não é possível duplicar uma classe singleton.');
	}
	
}

?>