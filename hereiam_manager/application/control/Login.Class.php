<?php

namespace Control;

use Control\Base,
	View\Login as FormLogin,
	Libs\Autenticacao;

class Login extends Base
{

	public function __construct() 
	{
		$this->setForm(new FormLogin());
		parent::__construct();
	}
	
	public function principal()
	{
		$this->ver();
	}
	
	public function ver()
	{
		$this->getForm()->mostrar();
	}
		
	public function entrar()
	{
		try {
			$email = !empty($_POST['usuario']) ? $_POST['usuario'] : "";
			$senha = !empty($_POST['senha']) ? $_POST['senha'] : "";
			
			if (($email == "admin@admin") && ($senha == "admin")) {
				// Define os valores para o usuário global
				$_SESSION['usuario'] = $email;
				$_SESSION['autenticado'] = true;
				// Direciona o cliente/usuário para a página padrão do sistema
				header("Location: index.php?controle=inicial");
				die();
			} else {
				throw new \Exception("Usuário ou senha inválido(a).");
			}
		} catch (\Exception $e) {
			$this->getForm()->atribuirValor('mensagem', array('tipo' => 'danger', 'texto' => Utf8_decode($e->getMessage())));
			$this->getForm()->atribuirValor('valores', $_POST);
			$this->ver();
		}
	}
	
	public function sair()
	{
		Autenticacao::sairdosistema();
	}
}

?>