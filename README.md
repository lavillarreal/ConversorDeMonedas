# ConversorDeMonedas

Este proyecto es una aplicación Java que permite realizar conversiones de monedas utilizando tasas locales o en vivo mediante una API de intercambio de tasas de conversión. Además, ofrece un menú interactivo para que los usuarios seleccionen entre diferentes opciones.

## Características

- **Conversión Local**: Utiliza un conjunto de tasas de conversión almacenadas localmente.
- **Conversión en Vivo**: Realiza solicitudes a la API de intercambio de tasas para obtener las tasas de conversión más recientes.
- **Interfaz Interactiva**: Un menú que permite a los usuarios imprimir mensajes y realizar conversiones de monedas.

## Requisitos

- Java 8 o superior.
- Librería Gson para parsear JSON. Puedes incluirla en tu proyecto utilizando un gestor de dependencias como Maven o descargando el archivo JAR desde [Maven Central](https://mvnrepository.com/artifact/com.google.code.gson/gson).

## Configuración

1. Asegúrate de tener Java instalado en tu sistema.
2. Descarga e incluye la librería Gson en tu proyecto.
3. Compila el código fuente.

## Uso

### Ejecución del programa

1. Ejecuta el archivo compilado.
2. Sigue las instrucciones del menú principal:
    - **1: Imprimir Mensaje**: Ingresa un mensaje y el programa lo mostrará.
    - **2: Conversor de Monedas**: Selecciona el modo de conversión (Local o API).
    - **0: Salir**: Termina el programa.

### Conversión de Monedas

1. Selecciona "Conversor de Monedas" en el menú principal.
2. Elige el modo de conversión:
    - **Local**: Usa tasas de conversión predefinidas obtenidas de la API previamente.
    - **API**: Obtiene las tasas en tiempo real desde la API de ExchangeRate.
3. Ingresa las monedas de origen y destino en formato abreviado (ej., USD, EUR).
4. Ingresa la cantidad a convertir.

## Código Principal

El código contiene las siguientes funciones:

- **`parseConversionRates`**: Extrae y analiza las tasas de conversión desde una respuesta JSON.
- **`LocalConversion`**: Realiza la conversión utilizando tasas locales.
- **`LiveConversion`**: Consulta la API para obtener tasas actuales y realiza la conversión.
- **`ShowCurrencies`**: Muestra las monedas disponibles en el mapa de tasas.
- **`clearConsole`**: Limpia la consola para mejorar la experiencia del usuario.

## API Utilizada

La aplicación utiliza la API de [ExchangeRate-API](https://www.exchangerate-api.com/) para obtener las tasas de conversión. El endpoint utilizado en el código es:

```
https://v6.exchangerate-api.com/v6/{api_key}/latest/{base_currency}
```

## Consideraciones

- **Excepciones**: Se manejan errores de entrada del usuario y fallos en las solicitudes HTTP.
- **Formato de Salida**: Los valores convertidos se redondean a 4 decimales.
- **Compatibilidad**: La función `clearConsole` soporta tanto sistemas operativos Windows como Unix.

## Ejemplo

```
Menu principal
1) Imprimir mensaje
2) Conversor monedas

0) Salir

Ingrese la moneda de origen en formato abreviado:
USD

Ingrese la moneda de destino en formato abreviado:
EUR

Ingrese la cantidad de USD a convertir:
100

La cantidad de EUR equivalente a 100 USD es de: 92.1234
```

## Licencia

Este proyecto es de uso libre para fines educativos o personales. No se ofrece soporte para implementaciones en producción.

## Contacto

Para preguntas o comentarios sobre el proyecto, por favor contacta a [Lourdes Villarreal](mailto:lourdesaldanavillarreal@gmail.com).
