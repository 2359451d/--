package karat;

import javafx.util.Pair;

import java.util.*;

public class BadgeAccess {
    public static void main(String[] args) {
        /**
         * We want to find employees who badged into our secured room unusually often. We have an unordered list of names and access times over a single day. Access times are given as three or four-digit numbers using 24-hour time, such as "800" or "2250"
         * Write a function that finds anyone who badged into the room 3 or more times in a 1-hour period, and returns each time that they badged in during that period. (If there are multiple 1-hour periods where this was true, just return the first one.
         */
        /**
         We want to find employees who badged into our secured room unusually often. We have an unordered list of names and access times over a single day. Access times are given as three or four-digit numbers using 24-hour time, such as "800" or "2250"
         Write a function that finds anyone who badged into the room 3 or more times in a 1-hour period, and returns each time that they badged in during that period. (If there are multiple 1-hour periods where this was true, just return the first one.
         **/
        List<Pair<String, Integer>> badgeAccess = List.of(
                new Pair<>("Paul", 1355),
                new Pair<>("Paul", 1315),
                new Pair<>("Paul", 1405),
                new Pair<>("Paul", 1630),
                new Pair<>("Jennifier", 1910),
                new Pair<>("John", 830),
                new Pair<>("John", 835),
                new Pair<>("John", 855),
                new Pair<>("John", 915),
                new Pair<>("John", 930),
                new Pair<>("Jennifier", 1335),
                new Pair<>("Jennifier", 730),
                new Pair<>("John", 1630)
        );

        Map<String, PriorityQueue<Integer>> nameToTime = new HashMap<>();
        for(Pair<String, Integer> each: badgeAccess){
            String name = each.getKey();
            int time = each.getValue();
            PriorityQueue<Integer> temp = nameToTime.getOrDefault(name, new PriorityQueue<>());
            temp.offer(time);

            nameToTime.put(name, temp);
        }

        Map<String, List<Integer>> res = new HashMap<>();
        for(String name: nameToTime.keySet()){
            PriorityQueue<Integer> pq = nameToTime.get(name);

            while(!pq.isEmpty()){
                int curTime = pq.poll();
                int nextTime = pq.isEmpty() ? -1 : pq.peek();

                if (isWithinOneHour(curTime, nextTime)){
                    List<Integer> temp = res.getOrDefault(name, new ArrayList<>());
                    if (temp.isEmpty() || temp.get(temp.size()-1)!=curTime) temp.add(curTime);
                    temp.add(nextTime);

                    res.put(name, temp);
                }
            }
        }

        for(String name: res.keySet()){
            System.out.printf("%s: %s\n", name, res.get(name).toString());
        }
    }

    public static boolean isWithinOneHour(int time1, int time2){
        if (time2==-1) return false;

        int hour1 = time1/100;
        int min1 = time1%100;
        min1 +=60;
        int carry = min1/60;
        hour1 += carry;

        int timeBound = hour1*100 + min1;
        return time2<=timeBound;
    }
}
