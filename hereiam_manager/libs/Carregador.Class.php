<?php

namespace Libs;

use Libs\Singleton;

/*
 * Responsável por carregar automaticamente os arquivos PHP do sistema para que não seja necessário
 * efetuar inclusões exaustivas desses arquivos.
 */
class Carregador extends Singleton
{

	private $extensao;
	private $diretorioApp;
	private $diretorioLibs;
	private $modeloNamespace;
	private $visualizacaoNamespace;
	private $controleNamespace;
	private $libsNamespace;
	
	/*
	 * Inicializa as configurações padrão da classe Carregador.
 	 */
	public function __construct() 
	{
		$this->extensao = '.Class.php';
		$this->diretorioApp = '/application';
		$this->diretorioLibs = '/libs';
		$this->modeloNamespace = 'Model';
		$this->visualizacaoNamespace = 'View';
		$this->controleNamespace = 'Control';
		$this->libsNamespace = 'Libs';
	}
	
	public function getExtensao()
	{
		return $this->extensao;
	}
		
	public function setExtensao($extensao)
	{
		$this->extensao = $extensao;
	}
	
	public function getDiretorioApp()
	{
		return $this->diretorioApp;
	}
		
	public function setDiretorioApp($diretorioApp)
	{
		$this->diretorioApp = $diretorioApp;
	}

	public function getDiretorioLibs()
	{
		return $this->diretorioLibs;
	}
		
	public function setDiretorioLibs($diretorioLibs)
	{
		$this->diretorioLibs = $diretorioLibs;
	}
	
	public function getModeloNamespace()
	{
		return $this->modeloNamespace;
	}
		
	public function setModeloNamespace($modeloNamespace)
	{
		$this->modeloNamespace = $modeloNamespace;
	}
	
	public function getVisualizacaoNamespace()
	{
		return $this->visualizacaoNamespace;
	}
		
	public function setVisualizacaoNamespace($visualizacaoNamespace)
	{
		$this->visualizacaoNamespace = $visualizacaoNamespace;
	}
	
	public function getControleNamespace()
	{
		return $this->controleNamespace;
	}
		
	public function setControleNamespace($controleNamespace)
	{
		$this->controleNamespace = $controleNamespace;
	}
	
	/*
	 * Registra essa classe na pilha de carregamento automático.
	 */
	public function registrar() 
	{
		spl_autoload_register(array($this, "carregarApp"));
		spl_autoload_register(array($this, "carregarLibs"));
	}
	
	/*
	* Remove essa classe da pilha de carregamento automático.
	*/
	public function desregistrar() 
	{
		spl_autoload_unregister(array($this, "carregarApp"));
		spl_autoload_unregister(array($this, "carregarLibs"));
	}
	
	/*
	 * Carrega determinada classe ou interface.
	 *
	 * @parametro string $classe Nome da classe para carregar.
	 */
	public function carregarApp($classe) 
	{
		if ((strpos($classe, $this->modeloNamespace . '\\') !== 0) && 
		    (strpos($classe, $this->visualizacaoNamespace . '\\') !== 0) &&
		    (strpos($classe, $this->controleNamespace . '\\') !== 0)) {
            return false;
        }
        
		require ($this->diretorioApp !== null ? $this->diretorioApp . DIRECTORY_SEPARATOR : '')
               . str_replace('\\', DIRECTORY_SEPARATOR, $classe)
               . $this->extensao;
        
		return true;
	}
	
	public function carregarLibs($classe) 
	{
		if (strpos($classe, $this->libsNamespace . '\\') !== 0) {
            return false;
        }
        
		require ($this->diretorioLibs !== null ? $this->diretorioLibs . DIRECTORY_SEPARATOR : '')
               . str_replace('\\', DIRECTORY_SEPARATOR, $classe)
               . $this->extensao;

        return true;
	}
    
}

?>