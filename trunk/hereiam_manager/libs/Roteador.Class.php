<?php

namespace Libs;

use Libs\Singleton;

class Roteador extends Singleton
{

    private static $controles = array('Inicial', 'Login', 'ManterAgendaEventos', 'ManterAmbiente', 'ManterLocal', 
    	'ManterUsuario', 'ManterAdministrador');
    
	private $url = array();
    private $controle;
    private $acao;
    private $parametros;
	
    public function __construct()
    {
        $this->ajustaUrl();
    }

    private function ajustaUrl()
    {
		array_push($this->url, (isset($_GET['controle'])) ? $_GET['controle'] : '');
		array_push($this->url, (isset($_GET['acao'])) ? $_GET['acao'] : '');
		if (isset($_GET['parametros'])) {
			$listaParametros = explode('/', $_GET['parametros']);
			$i = 0;
			foreach ($listaParametros as $parametro) {
				if ($i == 0) {
					$chave = $parametro;
					$i++;
				} else {
					$parametros[$chave] = $parametro;
					$i = 0;
				}
			}
			array_push($this->url, (isset($parametros)) ? $parametros : array());
		}

		$chaveControle = array_search(strtolower($this->url[0]), array_map('strtolower', self::$controles));
        $this->controle = ((isset($this->url[0])) && (!empty($this->url[0])) && (is_string($this->url[0]))) && ($chaveControle != false)
            ? self::$controles[$chaveControle]
            : 'Inicial';
          
		$this->acao = ((isset($this->url[1])) && (!empty($this->url[1])) && (is_string($this->url[1])))
            ? $this->url[1]
            : 'principal';
            	
        $this->parametros =  ((isset($this->url[2])) && (!empty($this->url[2])) && (is_array($this->url[2])))
            ? $this->url[2]
            : array();	
    }

    public function getControle()
    {
		return $this->controle;
    }

    public function getAcao()
    {
        return $this->acao;
    }

    public function getParametro($chave)
    {
    	if (array_key_exists($chave, $this->parametros)) {
    		return $this->parametros[$chave];
    	} else {
    		return false;
    	}
    }
    
    public function getParametros()
    {
    	return $this->parametros;
    }
    
}

?>