<?php

namespace Control;

use Libs\Roteador,
	Libs\Autenticacao;

abstract class Base
{

	protected $form;
	
	abstract protected function principal();
	
	public function __construct()
	{
		// Está autenticado
		if (Autenticacao::autenticado()) {
			if ((Roteador::getInstancia()->getControle() == 'Login') && (Roteador::getInstancia()->getAcao() != 'sair')) {
				header('Location: index.php?controle=inicial');
			}
			else {
				// Faz com que todas as páginas possam acessar o objeto para exibir nome, empresa e etc.
				$this->getForm()->atribuirValor('usuario', Autenticacao::getUsuario());
			}
		}
		// Não está autenticado
		else {
			// Se não está nas páginas brancas, envia para tela de login	
			if (Roteador::getInstancia()->getControle() != 'Login') {
				Autenticacao::sairdosistema();
			}
		}
		
		$this->getForm()->atribuirValor('controle', Roteador::getInstancia()->getControle());
		$this->getForm()->atribuirValor('acao', Roteador::getInstancia()->getAcao());
		$this->getForm()->atribuirValor('parametros', Roteador::getInstancia()->getParametros());
		$this->getForm()->atribuirValor('usuario', Autenticacao::getUsuario());
		$this->getForm()->atribuirValor('tipoUsuario', Autenticacao::getTipoUsuario());
	}
	
	public function setForm($form)
	{
		$this->form = $form;
	}
	
	public function getForm()
	{
		return $this->form;
	}

}

?>