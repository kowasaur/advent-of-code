<?php

function parse_line($str) {
    return array_map("intval", str_split(rtrim($str)));
}

$heightmap = array_map("parse_line", file("./input.txt"));
$basins = array();

$width = count($heightmap[0]);
$height = count($heightmap);

define("CHECKED", 9);

function basin_size($x, $y) {
    global $heightmap, $width, $height;
    $size = 1;
    $heightmap[$y][$x] = CHECKED;
    if ($x != 0 && $heightmap[$y][$x - 1] != CHECKED) $size += basin_size($x - 1, $y);
    if ($x != $width - 1 && $heightmap[$y][$x + 1] != CHECKED) $size += basin_size($x + 1, $y);
    if ($y != 0 && $heightmap[$y - 1][$x] != CHECKED) $size += basin_size($x, $y - 1);
    if ($y != $height - 1 && $heightmap[$y + 1][$x] != CHECKED) $size += basin_size($x, $y + 1);
    return $size;
}

for ($y = 0; $y < $height; $y++) { 
    for ($x = 0; $x < $width; $x++) { 
        if ($heightmap[$y][$x] != CHECKED) array_push($basins, basin_size($x, $y));
    }
}

rsort($basins);

$multipied_sizes = $basins[0] * $basins[1] * $basins[2];

print_r($multipied_sizes);
