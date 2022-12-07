function split(inputstr)
    local t={}
    for str in inputstr:gmatch("%S+") do table.insert(t, str) end
    return t
end

root = { size = 0 }

for line in io.lines "input.txt" do
    if line == "$ ls" then goto continue end
    local split_line = split(line)
   
    if split_line[1] == "$" then -- cd
        local dir = split_line[3]
        if dir == "/" then
            current_dir = root
        else
            current_dir = current_dir[dir]
        end
    elseif split_line[1] == "dir" then
        current_dir[split_line[2]] = { size = 0, [".."] = current_dir }
    else -- file
        current_dir.size = current_dir.size + split_line[1]
    end
    ::continue::
end

-- Part 1

total_size = 0

function get_size_and_add(dir)
    local size = 0
    for k, v in pairs(dir) do
        if k == "size" then size = size + v
        else
            if k ~= ".." then
                size = size + get_size_and_add(v)
            end
        end
    end
    if size <= 100000 then total_size = total_size + size end
    dir.size = size -- used in part 2
    return size
end

root_size = get_size_and_add(root)
print(total_size)

-- Part 2

min_delete_size = 30000000 - (70000000 - root_size)
smallest_valid = root_size

function find_smallest_delete(dir)
    for k, v in pairs(dir) do
        if k == "size" then 
            if v < smallest_valid and v > min_delete_size then
                smallest_valid = v
            end
        else
            if k ~= ".." then find_smallest_delete(v) end
        end
    end
end

find_smallest_delete(root)
print(smallest_valid)
