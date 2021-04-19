## API REST para verificar si un ADN es mutante o humano



### Instrucciones de uso

La API fue implementada usando Spring Boot . Versión 11 de JAVA. Fue creada para ser desplegada en el servicio App Engine de Google Cloud. Utiliza el servicio MongoDB Atlas y se conecta a un Cluster para el almacenamiento de los datos.  Se implementaron pruebas automatizadas mediante la herramienta Cucumber.
Uso de la API

* Expone un servicio obtener estadísticas - petición HTTP GET
```
curl -X GET http://dna-analyzer-project.uc.r.appspot.com/stats
```

* Expone un servicio para verificar un ADN - petición HTTP POST. Si el ADN es de un mutante responde estado HTTP 200, de lo contrario estado HTTP 403
```
curl -H "Content-type: application/json" -X POST -d '{"dna":["ATGCTA","CAGGGG","GTTTAA","AATAGA","CTGCAT","FCGGRA"]}' http://dna-analyzer-project.uc.r.appspot.com/mutant/
```
### Instalación de la aplicación
#### Requisitos

-Java JDK 11

-Gradle

-Servidor MongoDB local usando el perfil local o usar el servicio en la nube y modificar propiedad de conexión
```
-Dspring.profiles.active=local
```
