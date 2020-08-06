#include<iostream>
using namespace std;
int main(){
    int n;
    while(cin >> n){
        int a[3] = {0}, count=0;
        if (n%3==0) a[0] = 3;
        if (n%5==0) a[1] = 5;
        if (n%7==0) a[2] = 7;
        for (int i = 0; i < 3; i++)
            if (a[i]!=0) cout<< a[i]<<' ';
            else count++;
        if (count==3) cout<<'n';
        cout<< endl;
    }
    return 0;
}