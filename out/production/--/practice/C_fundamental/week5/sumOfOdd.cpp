/*
calculating the sum of all the odd numbers between m~n
0<=m<=n && n<=300
*/
#include <iostream>
using namespace std;
int main()
{
    int m, n, sum = 0;
    cin >> m >> n;
    while (m<=n){
        if (m % 2 == 1)
            sum += m;
        m++;
    }
    cout << sum << endl;
    return 0;
}
