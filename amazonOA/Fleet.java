public class Fleet {

    /**
     *
     * 给定轮子数组，[4,5,6] 求2轮/4轮车情况下 能组成n个轮子的组合数
     * 比如对应输出[2,0,2]
     * @return
     */
    // static Map<Integer, Integer> memo;
    // 用记忆化搜索/dp搜每个位置应该会超时= =
    public static int[] solve(int[] wheels){
        int[] res = new int[wheels.length];
        int i=0;
        for(int each: wheels){
            if (each%2!=0){ //奇数
                res[i++] = 0;
            }else{
                int temp = each/4;
                res[i++] = temp+1;
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        // memo = new HashMap<>();
        solve(new int[]{
            4,5,6
        });
    }
}
