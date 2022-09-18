#include<iostream>
#include<iomanip>
using namespace std;
// reverse func
void Reverse(int R[], int l, int r){
    int temp;
    while (l<r){
        temp = R[l];
        R[l] = R[r];
        R[r] = temp;
        l++;
        r--;
    }
}
int main(){
    int n,m;
    cin >> n >> m;
    int a[500];
    for (int i = 0; i < n; i++)
    {
        cin >> a[i];
    }
    Reverse(a,0,n-1);
    Reverse(a,0,m-1);
    Reverse(a,m,n-1);
    for (int i = 0; i < n; i++)
    {
        cout<< a[i] << ' ';
    }
    return 0;
}