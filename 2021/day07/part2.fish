#! /bin/fish

# This solution is extremely slow but it works

set FILE input.txt

set crabs (string split ',' (cat $FILE))

function fuel-cost --argument-names target
    set cost 0
    for pos in $crabs
        set steps (math 'abs('$pos '-' $target')')
        set fuel (math $steps / 2 x '(1 +' $steps ')') # sum of arithmetic sequence
        set cost (math $cost + $fuel)
    end
    echo $cost
end

set max 0
for pos in $crabs
    if [ $pos -gt $max ]
        set max $pos
    end
end

set min (fuel-cost 0)
for i in (seq $max)
    set new (fuel-cost $i)
    if [ $new -lt $min ]
        set min $new 
    end
end

echo $min
