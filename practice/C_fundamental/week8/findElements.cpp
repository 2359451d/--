#include<iostream>
using namespace std;

int main(){
    int n, sum, a[1000];
    bool flag=false;
    cin >> n >> sum;
    for (int i=0;i<n;i++){
        cin >> a[i];
    }
    for (int i=0;i<n;i++){
        for (int j = 0; j < n; j++)
        {
            if (a[i]+a[j]==sum && i!=j){
                flag=true;
                break;
            }
        }
        if(flag==true) break;
    }
    if(flag==false) cout<< "no";
    else cout<< "yes";
    return 0;
}