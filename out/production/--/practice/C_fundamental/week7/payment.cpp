#include<iostream>
using namespace std;

int main(){
    int a[6] ={100,50,20,10,5,1}, size=5, n, q, r;
    cin >> n;
    for (int i =0; i<size;i++){
        q = n/a[i];
        r = n - q*a[i];
        if (r==0){
            cout << "0";
            break;
        }
        cout << q << endl;
        n = r;
    }
    return 0;
}