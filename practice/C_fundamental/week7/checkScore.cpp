#include <iostream>
using namespace std;

int main()
{
    int n, result;
    cin >> n;
    if (n>=95 && n<=100)
    result=1;
    if (n>=90 && n<95)
    result=2;
    if (n>=85 && n<90)
    result=3;
    if (n>=80 && n<85)
    result=4;
    if (n>=70 && n<80)
    result=5;
    if (n>=60 && n<70)
    result=6;
    if (n<60)
    result=7;
    cout << result;
    return 0;
}
