package karat;

import java.util.*;

public class FindCoursePairs {
        /**
     * Sample Input:

        student_course_pairs_1 = [
        ["58", "Software Design"],
        ["58", "Linear Algebra"],
        ["94", "Art History"],
        ["94", "Operating Systems"],
        ["17", "Software Design"],
        ["58", "Mechanics"],
        ["58", "Economics"],
        ["17", "Linear Algebra"],
        ["17", "Political Science"],
        ["94", "Economics"],
        ["25", "Economics"],
        ]

        Sample Output (pseudocode, in any order):

        find_pairs(student_course_pairs_1) =>
        {
        [58, 17]: ["Software Design", "Linear Algebra"]
        [58, 94]: ["Economics"]
        [58, 25]: ["Economics"]
        [94, 25]: ["Economics"]
        [17, 94]: []
        [17, 25]: []
        }

        Additional test cases:

        Sample Input:

        student_course_pairs_2 = [
        ["42", "Software Design"],
        ["0", "Advanced Mechanics"],
        ["9", "Art History"],
        ]

        Sample output:

        find_pairs(student_course_pairs_2) =>
        {
        [0, 42]: []
        [0, 9]: []
        [9, 42]: []
        }
         */

        public static void main(String[] args) {
                String[][] stuCourses = {
                        {"58", "Software Design"},
                        {"58", "Linear Algebra"},
                        {"94", "Art History"},
                        {"94", "Operating Systems"},
                        {"17", "Software Design"},
                        {"58", "Mechanics"},
                        {"58", "Economics"},
                        {"17", "Linear Algebra"},
                        {"17", "Political Science"},
                        {"94", "Economics"},
                        {"25", "Economics"},
                };

                //------------
                Map<String, Set<String>> stuToCourse = new HashMap<>();
                for(String[] each: stuCourses){
                        String id = each[0];
                        String course = each[1];
                        Set<String> temp = stuToCourse.getOrDefault(id, new HashSet<>());
                        temp.add(course);
                        stuToCourse.put(id, temp);
                }

                ArrayList<String> ids = new ArrayList<>(stuToCourse.keySet());
                for(int i=0; i<ids.size(); i++){

                        String id1 = ids.get(i);
                        Set<String> set1 = stuToCourse.get(id1);
                        for(int j=i+1; j<ids.size(); j++){
                                ArrayList<String> intersection = new ArrayList<>();
                                String id2 = ids.get(j);
                                for (String each : stuToCourse.get(id2)) {
                                        if (set1.contains(each)) {
                                                intersection.add(each);
                                        }
                                }

                                System.out.printf("[%s, %s]: [%s]\n",
                                        id1,id2, String.join(",",intersection));
                        }
                }

        }
    
}
