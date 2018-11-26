# EDD2Laboratorio2
Aplicación en android studio para métodos de cifrado

### Uso de la aplicación (Parte 1 - Zig zag)
1. Descargar contenido de la rama "Zigzag" (NO rama dd ZigZag) 
2. Al momento de correr la app, seleccionar botón zigzag
3. La ventana de navegación permite hacer "swipe" hacia la izquierda pada elegir entre cifrar o descifrar 
4. Al cifrar se genera un archivo <nombreDelArchivoLeido>_cifrado.txt que se almacena en la carpeta raiz del dispositivo
5. Los resultados siempre se muestran dentro de la app

### SDES (Parte 2)
Aplicación contiene dentro de la misma rama Zigzag el código para este cifrado
Uso es igual que el de la parte 1 
El proceso de cifrado y descifrado es tardado pero sí cumple el procedimiento.

### RSA (Parte 3)
Aplicación en la rama RSA 
1. Si no se tiene generadas las llaves se generan eligiendo dos numeros primos y luego se presiona el botón "Crear llaves"
2. Si ya se tienen generadas las llaves se busca el documento en el botón a la izquierda de "Leer llave" y luego se presiona ese botón
3. Al tener las llaves creadas, se busca ahora el documento a cifrar y luego se presiona el botón "Cifrar" 
------------------------
Proceso para descifrar: 
1. Se busca el documento que incluya la llave privada y luego se presiona el botón "Leer llave"
2. Se presiona el botón "Leer llave" 
3. Se busca el archivo que se va a descifrar y luego se presiona el botón descifrar 
-----------------------
####Limitaciones: 
1. No muestra mensajes de confirmación al cifrar o descifrar
2. Se valída que los números ingresados sean primos pero no notifica si no son primos aunque no opera en caso se diera que no son primos 
3. Probar con númeross primos que sean grandes, ejemplo: 11 y 19 funciona 
