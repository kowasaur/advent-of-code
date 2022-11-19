<?php

function parse_line($str) {
    return array_map("intval", str_split(rtrim($str)));
}

$heightmap = array_map("parse_line", file("./input.txt"));
$low_points = array();

$width = count($heightmap[0]);
$height = count($heightmap);

foreach ($heightmap as $yi => $y) {
    foreach ($y as $xi => $x) {
        $surroundings = array();
        if ($xi != 0) array_push($surroundings, $heightmap[$yi][$xi - 1]);
        if ($xi != $width - 1) array_push($surroundings, $heightmap[$yi][$xi + 1]);
        if ($yi != 0) array_push($surroundings, $heightmap[$yi - 1][$xi]);
        if ($yi != $height - 1) array_push($surroundings, $heightmap[$yi + 1][$xi]);

        if (min($surroundings) > $x) array_push($low_points, $x);
    }
}

function risk_reducer($a, $b) {
    return $a + $b + 1;
}

$risk_levels_sum = array_reduce($low_points, "risk_reducer", 0);

print_r($risk_levels_sum);
