fun main() {
    // put your code here
    val inputNumber = readln().toInt()
    // if inputNumber is even => nextEvenNumber = inputNumber - 0 + 2
    // if inputNumber is odd => nextEvenNumber = inputNumber - 1 + 2
    val nextEvenNumber = inputNumber - inputNumber % 2 + 2
    print(nextEvenNumber)
}