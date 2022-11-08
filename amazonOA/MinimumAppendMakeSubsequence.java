public class MinimumAppendMakeSubsequence {
    public static void main(String[] args) {
        System.out.println(solve("armaze", "amazon"));
        System.out.println(solve("e", "amazon"));
    }

    // searchword末尾添加几个字符 可以使 result成为其subsequence
    public static int solve(String search, String result){
        // 双指针check差异

        // 当前i==j, 同时inc
        // i!=j , i++

        // 走完其中一个串为止，result.len~j差异
        // 还需要匹配多少个result的字符
        int i=0,j=0;
        while(i<search.length() && j<result.length()){
            if (search.charAt(i)==result.charAt(j)){
                i++; j++;
            }else{
                i++;
            }
        }
        return result.length()-j;
    }
}
