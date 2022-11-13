# TODO


* Boletin 6 optional exercises 2,3

* Test jpa1 meter

# IMPORTANTE

	```
	En usuario habra que añadir a la consulta

	@NamedQuery(name = "Usuario.findByEmailClave", query = " SELECT u FROM Usuario u WHERE u.email = :email and u.clave = :clave and u.validado=true"),

	```
