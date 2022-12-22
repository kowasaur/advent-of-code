struct Blueprint 
    number::Int
    costs::Array{Int, 2}
end

function canAfford(type::Int, items::Vector{Int}, blueprint::Blueprint)
    return all(a -> a >= 0, items - blueprint.costs[type, :])
end

function buy!(type::Int, robots::Vector{Int}, blueprint::Blueprint)
    robots[type] += 1
    return blueprint.costs[type, :]
end

function timeTillAford(type::Int, robots::Vector{Int}, blueprint::Blueprint, items::Vector{Int})
    need = blueprint.costs[type, :] - items
    time = 0
    for i in [1:4;]
        if robots[i] != 0 time = max(time, ceil(need[i] / robots[i])) end
    end
    return time
end

function worthBuyingClay(robots::Vector{Int}, blueprint::Blueprint, items::Vector{Int})
    if items[2] == 0 return true end
    new_robots = copy(robots)
    new_robots[2] += 1
    new_time = timeTillAford(3, new_robots, blueprint, items - blueprint.costs[2, :])
    return new_time <= timeTillAford(3, robots, blueprint, items)
end

function worthBuyingObsidian(robots::Vector{Int}, blueprint::Blueprint, items::Vector{Int})
    if items[3] == 0 return true end
    new_robots = copy(robots)
    new_robots[3] += 1
    new_time = timeTillAford(4, new_robots, blueprint, items - blueprint.costs[3, :])
    return new_time <= timeTillAford(4, robots, blueprint, items)
end

function qualityLevel(blueprint::Blueprint)
    minutes_left = 24
    items = [0, 0, 0, 0]
    robots = [1, 0, 0, 0]

    while minutes_left > 0
        minutes_left -= 1
        new_robots = [0, 0, 0, 0]

        println("\n==Minute $(24 - minutes_left)==")

        while canAfford(4, items, blueprint)
            items -= buy!(4, new_robots, blueprint)
        end

        while canAfford(3, items, blueprint) && worthBuyingObsidian(robots, blueprint, items)
            items -= buy!(3, new_robots, blueprint)
        end

        while canAfford(2, items, blueprint) && worthBuyingClay(robots, blueprint, items)
            items -= buy!(2, new_robots, blueprint)
        end

        if canAfford(1, items, blueprint)
            items -= buy!(1, new_robots, blueprint)
        end

        items += robots
        robots += new_robots

        println(items)
        println(robots)
    end

    blueprint.number * items[4]
end

blueprints = Blueprint[]

file = open("example.txt", "r")
for line in readlines(file)
    nums = [parse(Int, n) for n in [filter(isdigit, word) for word in split(line, " ")] if n != ""]
    push!(blueprints, Blueprint(nums[1], [
        nums[2]       0       0 0;
        nums[3]       0       0 0;
        nums[4] nums[5]       0 0;
        nums[6]       0 nums[7] 0
    ]))
end
close(file)

# println(qualityLevel(blueprints[2]))
println(reduce((a, b) -> a + qualityLevel(b), blueprints; init=0))
