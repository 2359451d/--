import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PackageDelivery {
    public static void main(String[] args) {
        int[] locations = {1,7,6,8};
        int[] from = {1,7,2};
        int[] to = {2,9,5};
        solve(locations, from, to);
    }

    /**
     * 放松n个包裹
     * 移动m次
     * 
     * ith操作，包从movedFrom[i] 移动到movedTo[i]
     * 
     * 求m次移动操作后，包裹位置， 升序返回
     * locations = [1,7,6,8] , 包裹初始位置
     * movedFrom = [1,7,2]
     * movedTo = [2,9,5]
     */
    public static void solve(int[] locations, int[] from, int[] to){
        Set<Integer> set = new HashSet<>();
        
        for(int l: locations){
            set.add(l);
         }

         for(int i=0;i<to.length;i++){
            int fromLo = from[i];
            int toLo = to[i];
            if (set.contains(fromLo)){
                set.remove(fromLo);
                set.add(toLo);
            }
         }
         
         new ArrayList<>(set).stream().sorted().forEach(System.out::println);
    }
}
