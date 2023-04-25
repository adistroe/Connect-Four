fun names(namesList: List<String>): List<String> {
    val text = "The name %s is repeated %d times"
    var count = 0
    val nameCount = mutableListOf<String>()
    //
    // add your code here
    val namesWithoutDuplicates = namesList.toSet()
    for (name in namesWithoutDuplicates) {
        count = namesList.count { it == name }
        nameCount.add(String.format(text, name, count))
    }
    //
    return nameCount
}