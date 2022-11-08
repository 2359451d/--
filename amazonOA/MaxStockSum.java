import java.util.HashSet;
import java.util.Set;

class MaxStockSum{
    public static void main(String[] args) {
        int[] stocks = {1,2,3,4};
        System.out.println(getMaxSum(stocks, 2));
    }

    /**
     * 
     * 给一个长度为n的数组表示n个月的股价，给定k值，给连续k月并且k个值各不一样的区间求和，找到最大和。例子：｛1，2，3，4｝，k=2，那总共有(1,2) (2,3)(3,4)三个长度为k的连续区间并且每个区间没有重复数值，最大和是7。
     */
    public static long getMaxSum(int[] stocks, int k){
        //Set, 滑窗
        Set<Integer> set = new HashSet<>();

        long res = 0L;
        int l=0;
        long sum=0L;
        for(int r=0; r<stocks.length;
         r++){
            if (set.contains(stocks[r])){
                l++;
                continue;
            }

            set.add(stocks[r]);
            sum+=stocks[r];

            if (r-l+1==k){ // k win
                res = Math.max(res, sum);
                sum-=stocks[l++];
            }
         }
         return res;
    }
}