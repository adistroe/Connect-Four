const val one_minute = 60
const val one_hour = 60 * one_minute
const val one_day = 24 * one_hour

fun main() {
    val totalSeconds = System.currentTimeMillis() / 1000 // dont change this line
    // enter your code
    val seconds = totalSeconds % one_minute
    val minutes = totalSeconds % one_hour / one_minute
    val hours = totalSeconds % one_day / one_hour
    print("$hours:$minutes:$seconds")
}