total_score = 0

File.foreach('./input.txt') do |line|
    opponent = line[0].ord - 65
    necessary_result = line[2]
    
    case necessary_result
    when 'X' # lose
        total_score += (opponent - 1) % 3 + 1
    when 'Y' # draw
        total_score += 3 + opponent + 1
    when 'Z' # win
        total_score += 6 + (opponent + 1) % 3 + 1
    end
end

puts total_score
