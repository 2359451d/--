#include<iostream>
#include <iomanip>
using namespace std;

int main()
{
    int n, a=0, b=0, c=0, d=0, x;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> x;
        if (x>=1 && x<=18)
        a+= 1;
        if (x>=19 && x<=35)
        b+= 1;
        if (x>=36 && x<=60)
        c+= 1;
        if (x>=60)
        d+= 1;
    }
    double result_a, result_b, result_c, result_d;
    result_a = 100.0 * a / n;
    result_b = 100.0 * b / n;
    result_c = 100.0 * c / n;
    result_d = 100.0 * d / n;
    cout<<fixed<<setprecision(2) << "1-18: " << result_a<<  '%'<<endl
        << "19-35: " << result_b << "%" << endl    
        << "36-60: " << result_c << "%" << endl    
        << "60-: " << result_d << "%" << endl;    
    return 0;
}
