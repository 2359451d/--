/*
给定k（1<k<100）个正整数，其中每个数都是大于等于1，小于等于10的数。写程序计算给定的k个正整数中，1，5和10出现的次数。

输入两行：第一行包含一个正整数k，第二行包含k个正整数，每两个正整数用一个空格分开。

输出三行，第一行为1出现的次数，，第二行为5出现的次数，第三行为10出现的次数。
*/

#include <iostream>
using namespace std;

int main()
{
    int k, n, x=0, y=0, z=0;
    cin >> k;
    for (int i = 0; i < k; i++)
    {
        cin >> n;
        if(n==1)
        x++;
        if(n==5)
        y++;
        if(n==10)
        z++;
    }
    cout << x << endl;
    cout << y << endl;
    cout << z << endl;
    return 0;
}
