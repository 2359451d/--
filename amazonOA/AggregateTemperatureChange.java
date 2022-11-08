public class AggregateTemperatureChange {

    /**
     * 到ith天的温度变化和
     * 后n-i天的温度变化总和（包括i天
     * 
     * 求最大aggregate temp变化 即到每天为止上面两个值其中的最大值，最后取全局最大
     * 
     * 如[6,-2,5]
     * output: 9
     * 
     * 
     * @return
     */
    public static long solve(int[] tempChange){
        int curSum=tempChange[0];
        int totalSum=0;
        for(int change:tempChange){
            totalSum+=change;
        }
        int res =Math.max(curSum, totalSum);

        for(int i=1; i<tempChange.length;i++){
            curSum += tempChange[i];
            totalSum -= tempChange[i-1];
            res = Math.max(curSum, totalSum);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(solve(
            new int[]{
                6,-2,5
            }
        ));
    }
}
