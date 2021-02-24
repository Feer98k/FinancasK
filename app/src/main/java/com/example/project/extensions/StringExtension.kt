package com.example.project.extensions


fun String.formataCategoria(caracteres: Int): String {
    val primeiroCaractere = 0
    if (this.length > caracteres) {
        return "${this.substring(primeiroCaractere, caracteres)}..."

    } else {
        return this
    }

}
