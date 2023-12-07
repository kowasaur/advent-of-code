import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static int totalWinnings(ArrayList<Hand> hands, Comparator<? super Hand> c) {
        int winnings = 0;
        hands.sort(c);
        for (int i = 0; i < hands.size(); i++) winnings += (i + 1) * hands.get(i).bid;
        return winnings;
    }

    public static void main(String[] _args) throws IOException {
        var hands = new ArrayList<Hand>();
        for (var line : AoC.readLines("07/input.txt")) {
            hands.add(new Hand(line));
        }

        AoC.printResult(1, totalWinnings(hands, Hand::compare1));
        AoC.printResult(2, totalWinnings(hands, Hand::compare2));
    }
}

class Hand {
    static String labels1 = "23456789TJQKA";
    static String labels2 = "J23456789TQKA";

    int bid;
    int type1;
    int type2;
    String str;

    public Hand(String line) {
        str = line;
        var parts = line.split(" ");
        bid = Integer.parseInt(parts[1]);

        var frequency = new HashMap<Character, Integer>();
        for (int i = 0; i < 5; i++) {
            var c = parts[0].charAt(i);
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);   
        }

        int mostNoJ = 0;
        int jokers = frequency.getOrDefault('J', 0);

        for (var c : frequency.keySet()) {
            if (c == 'J') continue;
            mostNoJ = Math.max(mostNoJ, frequency.get(c));
        }

        int most = Math.max(frequency.getOrDefault('J', 0), mostNoJ);
        int types = frequency.size();
        
        type1 = bestType(most, types);

        if (jokers == 0) {
            type2 = type1;
        } else {
            type2 = Math.max(type1, bestType(mostNoJ + jokers, types - 1));
        }
    }

    public static int compare1(Hand h1, Hand h2) {
        return compare(h1, h2, h1.type1, h2.type1, labels1);
    }

    public static int compare2(Hand h1, Hand h2) {
        return compare(h1, h2, h1.type2, h2.type2, labels2);
    }

    static int compare(Hand h1, Hand h2, int t1, int t2, String labels) {
        if (t1 != t2) return Integer.compare(t1, t2);

        for (int i = 0; i < 5; i++) {
            int s1 = labels.indexOf(h1.str.charAt(i));
            int s2 = labels.indexOf(h2.str.charAt(i));
            if (s1 != s2) return Integer.compare(s1, s2);
        }

        return 0;
    }

    static int bestType(int most, int types) {
        if (most == 5) return 7;                    // five of a kind
        else if (most == 4) return 6;               // four of a kind
        else if (most == 3 && types == 2) return 5; // full house
        else if (most == 3 && types == 3) return 4; // three of a kind
        else if (most == 2 && types == 3) return 3; // two pair
        else if (most == 2) return 2;               // one pair
        return 1;                                   // high card
    }
}
