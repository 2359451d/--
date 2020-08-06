#include<iostream>
using namespace std;
int main(){
    int n;
    int a[15000];
    while (cin>>n && n!=0){
        for (int i = 0; i < n; i++)
            cin >> a[i];
        // bubble sorting
        int temp;
        for (int i = 0; i < n; i++)
        {
            for (int j = 1; j < n-i; j++)
            {
                if (a[j-1]>a[j])
                {
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                }
            }
        }
        if (n%2==0){
            cout<< (a[n/2-1]+a[n/2])/2<<endl;
        }else {
            cout<< a[n/2]<<endl;
        }
    }
}