#include<iostream>
using namespace std;

int main()
{
    int n, x, y, z;
    cin >>n;
    x = n/100;
    y = (n-x*100)/10;
    z = n-x*100-y*10;
    cout << x << endl;
    cout << y << endl;
    cout << z << endl;
    return 0;
}
