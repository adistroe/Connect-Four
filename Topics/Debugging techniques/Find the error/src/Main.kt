import java.util.Scanner

fun swapInts(ints: IntArray) {
    ints[1] = ints[0].also { ints[0] = ints[1] }
}

fun main() {
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        val ints = intArrayOf(
            scanner.nextLine().toInt(),
            scanner.nextLine().toInt(),
        )
        swapInts(ints)
        println(ints[0])
        println(ints[1])
    }
}