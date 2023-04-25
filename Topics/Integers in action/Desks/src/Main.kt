fun main() {
    // put your code here
    val (a,b,c)=List(3){readln().toInt()}
    println(
        a/2+a%2+b%2+b/2+c%2+c/2
    )
}
