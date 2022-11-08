import java.util.Arrays;

public class DivideMovieGroup {

    /**
     * 分割子序列，保证任意元素间最大差异不超过k
     * 求最小分组数
     * 如 [1,5,4,6,8,9,2] , k=3
     * output: 3
     * 
     * 排序 + 贪心
     * 先尽可能的把小的元素分配在一起，一个组能容纳更多的小元素
     * 差异超过k时划分新分组
     * 
     * @return
     */
    public static int solve(int[] awards, int k){
        Arrays.sort(awards);
        int res=1;
        int curMin = awards[0];
        for(int i=1; i<awards.length;
            i++){
                if (awards[i]-curMin>k){
                    res++;
                    //新分组最小值
                    curMin = awards[i];
                    
                }
            }
        return res;


    }

    public static void main(String[] args) {
        System.out.println(solve(
            new int[]{1,5,4,6,8,9,2}, 3));
    }
}
