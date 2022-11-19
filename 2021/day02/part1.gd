# necessary to run as script instead of in godot
extends SceneTree

var horizontal_pos = 0
var depth = 0

func _init():
    var file = File.new()
    file.open("input.txt", file.READ)

    while !file.eof_reached():
        var instructions = file.get_line().split(" ")
        var change = int(instructions[1])
        match (instructions[0]):
            "forward":
                horizontal_pos += change
            "down":
                depth += change
            "up":
                depth -= change
    file.close()

    print(horizontal_pos * depth)

    quit()
