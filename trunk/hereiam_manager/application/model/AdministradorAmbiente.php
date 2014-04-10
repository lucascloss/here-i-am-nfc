<?php
	class AdministradorAmbiente{
	
		private $id;
		private $nome;
		private $usuario;
		private $email;
		private $senha;
		
		public function __get($propriedade) {
			if (property_exists($this, $propriedade)) {
				return $this->$propriedade;
			}
		}
		
		public function __set($propriedade, $valor) {
			if (property_exists($this, $propriedade)) {
				$this->$propriedade = $valor;
			}		
			return $this;
		}						
	}
?>