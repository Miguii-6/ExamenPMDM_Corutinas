package com.example.examencorutinas.ui.theme

// Clase para almacenar una frase y su valor verdadero/falso
data class Frase(var texto: String, var verdadero: Boolean)


// Función para cargar una lista de frases
object Datos {
    val frases = mutableListOf(
        Frase("el torneo de rugby cinco naciones, ahora es seis naciones", true),
        Frase("en el cielo hay cinco estrellas", false),
        Frase("el dia cinco de diciembre del 2023 es martes", true),
        Frase("cinco más cinco son diez", true),
        Frase("dos mas dos son cinco", false),
        Frase("los elefantes tienen cinco patas", false),
        Frase("las estaciones climáticas son cinco", false),
        Frase("tenemos cinco dedos los humanos", true),
        Frase("cinco días tiene la semana sin el Domingo y el Sábado", true),
        Frase("una gallina pesa menos que cinco toneladas", true)
    )
}
