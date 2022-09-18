/*
给定一个十进制整数N，求其对应2进制数中1的个数
Input: 第一个整数表示有N组测试数据，其后N行是对应的测试数据，每行为一个整数
Output: N行，每行输出对应一个输入。
*/

#include <iostream>
using namespace std;

int main() {
    int n, k;
    cin >> n;
    for (int i = 0; i<n; i++){
        int x = 0, ans = 0;
        cin >> x; 
        while (x>0){
            ans += x % 2;
            x/=2; // floor division
        }
        cout << ans << endl;
    }
    return 0;
}    
