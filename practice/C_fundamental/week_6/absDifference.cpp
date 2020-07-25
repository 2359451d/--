#include <iostream>
using namespace std;

int main()
{
    int min=0, max=0, n, abs;
    for (int i=0; i<6; i++){
        cin >> n;
        // even: min
        if (n%2==0){
            if(min==0)
            {
                min = n;   
            }else if (n<min){
                min = n;
            }
        }
        // odd: max
        if (n%2==1){
            if(n>max)
            max =n;
        }
    }

    if (max>min){
        abs =max-min;
    }else{
        abs = min- max;
    }
    cout<< abs;
    return 0;
}
