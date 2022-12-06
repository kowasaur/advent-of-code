file <- as.list(strsplit(readChar("input.txt", nchars=1e6), "")[[1]])

first_marker <- function(marker_len) {
    i <- marker_len
    j <- marker_len - 1
    while (length(unique(file[(i-j):i])) != length(file[(i-j):i])) {
        i <- i + 1
    }
    return(i)
}

cat("Part 1: ", first_marker(4), "\n")
cat("Part 1: ", first_marker(14), "\n")
