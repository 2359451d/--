public class MainClass {
    public static String stringToString(String input) {
        return JsonArray.readFrom("[" + input + "]").get(0).asString();
    }
    
    public static void main(String[] args) throws IOException {
        /**BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String text1 = stringToString(line);
            line = in.readLine();
            String text2 = stringToString(line);
            
            int ret = new Solution().longestCommonSubsequence(text1, text2);
            
            String out = String.valueOf(ret);
            
            System.out.print(out);
        }**/
        
        String[] history1 = new String[]{"3232.html", "1234"};
        String[] history2 = new String[]{"1", "3232.html", "1234"};
        
        int len1 = history1.length, len2 = history2.length;
        int[][] dp = new int[len1+1][len2+1];
        //dp[0][0], 0个公共subarr
        
        int max = 0, maxStart=0;
        String[] res= new String[0];
        for(int i=1; i<=len1; i++){
            for(int j=1; j<=len2; j++){
                // 当前subarr相等
                // count+1
                if (history1[i-1]==history2[j-1]){
                    dp[i][j] = dp[i-1][j-1] +1;
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
                if (dp[i][j]>max){
                    max = dp[i][j];
                    res = Arrays.copyOfRange(history1, i-max, i);
                }
            }
        }
        for(String each: res){
            System.out.println(each);
        }
    }
}