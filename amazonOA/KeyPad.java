import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyPad {
    public static void main(String[] args) {
        System.out.println(minimumKeypadClickCount("abcghdiefjoba")); // expected 15
    }

    /**
     * 自定义9宫格，9个数字对应任意k个(3>=k>=2)unique字符
     * 求能组成str 的最小按键次数
     * 
     * 比如1号键对应ajs，则输入a需要1次，j需要2次
     * 
     * 贪心，频率高的字符放最前面位置方便一次完成
     */
    public static int minimumKeypadClickCount(String text){
        Map<Character, Integer> map = new HashMap<>();

        for(char c: text.toCharArray()){
            map.put(c, map.getOrDefault(c, 0)+1);
        }
        List<Integer> freq = new ArrayList<>(map.values());
        Collections.sort(freq);

        // 前9个频率高的为1次，剩下的看剩余倍数如18个unique, 则 9*1 + (18-9) *2
        int multi=1;
        int press = 0;
        int key=0;
        for(int i=freq.size()-1; i>=0;
         i--){  // 先处理高频
            press +=freq.get(i) * multi;
            key++;

            if (key==9){ // next number属于第10高频
                multi++;
                key=0; //重置当前层被处理过的num
            }
         }
         return press;
    }
}
