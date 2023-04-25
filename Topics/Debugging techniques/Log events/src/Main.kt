fun String?.capitalize(): String? {
    println("Before: $this")
    val string = when {
        isNullOrBlank() -> this
        length == 1 -> uppercase()
        else -> this[0].uppercase() + substring(1)
    }
    println("After: $string")
    return string
}