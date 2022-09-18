#include<iostream>
using namespace std;
int main(){
    int L,M;
    cin >> L >> M;
    int tree[10000];
    for (int i=0;i<L+1;i++){
        tree[i] = 1;
    }
    for (int i = 0; i < M; i++)
    {
        int a,b;
        cin>> a>>b;
        for (int j = a; j < b+1; j++){
            tree[j] = 0;
        }
    }
    int count=0;
    for (int i = 0; i < L+1; i++)
        if (tree[i]) count++;
    cout << count;
    return 0;    
}