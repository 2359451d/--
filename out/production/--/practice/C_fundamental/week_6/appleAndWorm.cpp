#include <iostream>
using namespace std;

int main()
{
    int n, x, y, result;
    cin >> n >> x >> y;
    int remainder = y % x;
    int q = y/x;
    if (q>=n){
        result = 0;
    }
    else if (remainder==0){
        result = n - q;
    }else{
        result = (n - q) - remainder;
    }
    cout << result << endl;
    return 0;
}