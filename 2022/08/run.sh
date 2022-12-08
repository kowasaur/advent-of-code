# idk if there's a better way
kotlinc solution.kt -include-runtime -d temp.jar
java -jar temp.jar
rm temp.jar
