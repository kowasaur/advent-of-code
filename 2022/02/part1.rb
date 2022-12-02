total_score = 0

File.foreach('./input.txt') do |line|
    # 0 is rock, 1 is paper, 2 is scissors
    # 65 is A and 88 is X
    opponent = line[0].ord - 65
    you = line[2].ord - 88

    total_score += you + 1

    case (you - opponent) % 3
    when 1 # win
        total_score += 6
    when 0 # draw
        total_score += 3
    end
end

puts total_score
