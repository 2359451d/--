import java.util.HashMap;
import java.util.Map;

public class RiceBag {

    // 购买perfect set of rice bags
    // 每个set至少两袋米
    // 米排序后，数量满足 rice[i] * rice[i] = rice[i+1]
    // 找到perfect set最大大小
    // 否则-1
    public static int solve(int[] rices){
        Map<Integer, Integer> dp = new HashMap<>();
        // dp[i], 装下i米后，set最大大小
        // 来源于dp[rices[i]*rices[i]] 的集合大小 + 当前米袋

        int res=0;
        for(int rice: rices){
            int canSuit = rice * rice;
            int setSize = dp.getOrDefault(canSuit, 1);

            dp.put(rice, setSize+1);
            res = Math.max(res, dp.get(rice));
        }
        return res<=1 ? -1 : res;
    }
    public static void main(String[] args) {
        int[] rices = {3, 9, 4, 2, 16};
        System.out.println(solve(rices));
        System.out.println(solve(new int[]{625,4,2,5,25}));
    }
}
