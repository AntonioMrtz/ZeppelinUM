# TODO



* Boletin 5 optional exercises

* Boletin 6 optional exercises

* Test jpa1 meter

# IMPORTANTE

	```
	En usuario habra que añadir a la consulta

	@NamedQuery(name = "Usuario.findByEmailClave", query = " SELECT u FROM Usuario u WHERE u.email = :email and u.clave = :clave and u.validado=true"),

	```