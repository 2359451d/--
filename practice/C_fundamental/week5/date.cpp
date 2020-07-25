// 1,3,5 have classes and only free on 2,4,6,7
// use 1-7 to represent Monday to Sunday
// input: the date of invitation
// output: YES - free/NO - not free
#include<iostream>
using namespace std;
int main()
{
    int date;
    cin >> date;
    if (date%2==0 || date==7)
        cout << "YES" << endl;
    else
        cout << "NO" << endl;
    return 0;
}
