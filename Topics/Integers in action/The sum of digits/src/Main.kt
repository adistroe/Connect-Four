fun main() {
    // put your code here
    var num = readln().toInt()
    var sum = 0
    do {
        sum += num % 10
        num /= 10
    } while (num != 0)
    println(sum)
}