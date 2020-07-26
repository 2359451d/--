#include <iostream>
#include <iomanip>
using namespace std;

int main(){
    int n, k, count;
    cin >> n;
    cin >> k;
    int a[100];
    for (int i = 0; i < n; i++)
    cin >> a[i];

    for (int i =0; i<n; i++){
        count=0;
        for (int j=0; j<n;j++){
            if (a[j]>a[i] && i!=j)
                count++;
            if (count>=k)
                break;
        }
        if (count==k-1){
            cout << a[i];
            break;
        }
    }
    return 0;
}