import java.io.File

fun treeColSubList(trees: List<List<Int>>, x: Int, a: Int, b: Int): List<Int> {
    return trees.map({ it[x] }).subList(a, b)
}

fun isVisible(trees: List<List<Int>>, x: Int, y: Int): Boolean {
    if (x == 0 || y == 0 || x == trees.size - 1 || y == trees.size - 1) return true
    val height = trees[y][x]
    if (trees[y].subList(0, x).max() < height) return true
    if (trees[y].subList(x+1, trees.size).max() < height) return true
    if (treeColSubList(trees, x, 0, y).max() < height) return true
    return treeColSubList(trees, x, y+1, trees.size).max() < height
}

// In `trees` the leftmost side is closest to the tree
fun viewingDistance(height: Int, trees: List<Int>): Int {
    val index = trees.indexOfFirst { it >= height }
    return if (index == -1) trees.size else index + 1
}

fun scenicScore(trees: List<List<Int>>, x: Int, y: Int): Int {
    val height = trees[y][x]
    val left = viewingDistance(height, trees[y].subList(0, x).reversed())
    val right = viewingDistance(height, trees[y].subList(x+1, trees.size))
    val up = viewingDistance(height, treeColSubList(trees, x, 0, y).reversed())
    val down = viewingDistance(height, treeColSubList(trees, x, y+1, trees.size))
    return left * right * up * down
}

fun main() {
    val file = File("input.txt").readText()
    val trees = file.split('\n').map { it.map { it - '0' } }

    var visible_trees = 0
    var best_scenic_score = 0

    for (y in 0..(trees.size - 1)) {
        for (x in 0..(trees.size - 1)) {
            if (isVisible(trees, x, y)) visible_trees++ // part 1
            best_scenic_score = maxOf(best_scenic_score, scenicScore(trees, x, y)) // part 2
        }
    }

    print("Part 1: ")
    println(visible_trees)
    print("Part 2: ")
    println(best_scenic_score)
}
