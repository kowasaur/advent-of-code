use strict;
use warnings;

sub letterPriority {
    my $value = ord($_[0]) - ord('a') + 1;
    if ($value < 1) { $value += ord("a") - ord("A") + 26; }
    return $value;
}

open(FH, '<', "input.txt") or die $!;

my $priority_sum = 0;
while(<FH>){
    my $half = (length($_) - 1) / 2;
    my $first = substr($_, 0, $half);
    my $second = substr($_, $half);
    
    my %in_first = ();
    foreach my $char (split //, $first) {
        $in_first{$char} = 1;
    }
    foreach my $char (split //, $second) {
        if ($in_first{$char}) {
            $priority_sum += letterPriority($char);
            last;
        }
    }
}
print "Part 1: " . $priority_sum . "\n";

open(FH, '<', "input.txt") or die $!;
$priority_sum = 0;

# Read it character by character
while (read FH, my $char, 1) {
    my %elf_occurrences = (); # number of elves that have at least one of the item
    while ($char ne "\n") {
        $elf_occurrences{$char} = 1;
        read FH, $char, 1;
    }
    read FH, $char, 1;
    while ($char ne "\n") {
        if ($elf_occurrences{$char}) { $elf_occurrences{$char} = 2; }
        read FH, $char, 1;
    }
    read FH, $char, 1;
    while (!(defined $elf_occurrences{$char}) || $elf_occurrences{$char} != 2) { read FH, $char, 1; }
    $priority_sum += letterPriority($char);
    while ($char ne "\n") { read FH, $char, 1; }
}
print "Part 2: " . $priority_sum . "\n"; 
