#include<iostream>
using namespace std;
int main()
{
    int a, n, sum=0;
    for (int i = 0; i < 6; i++)
    {
        if (i==0) cin>>a;
        else
            cin >> n;
            if (n<a) sum+=n;
    }
    cout << sum;
    return 0;
}