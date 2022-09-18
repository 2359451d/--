#include <iostream>
using namespace std;

int main()
{
    int h, r, result;
    double volume;
    cin >> h >> r;
    volume= (3.14159 * r * r * h) / 1000;
    result = (int) (20/volume);
    if (result==volume){
        cout << result << endl;
    }else{
        cout << result+1<<endl;
    }
    return 0;
}
