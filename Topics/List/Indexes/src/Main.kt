fun solution(products: List<String>, product: String) {
    // put your code here
    for ((index, prod) in products.withIndex()) {
        if (prod == product) {
            print("$index ")
        }
    }
}