#include<iostream>
using namespace std;
// omit first column & line, last column & line
int main(){
    int line;
    cin >> line;
    int a[100][100];
    for (int i=0;i<line;i++){
        for (int j=0;j<line;j++){
            cin >> a[i][j];
        }
    }
    int count =0, temp;
    for (int i =1;i<line-1;i++){
        for (int j=1;j<line-1;j++){
            temp = a[i][j];
            int up = a[i-1][j] - temp;       
            int down = a[i+1][j] - temp;       
            int left = a[i][j-1] - temp;       
            int right = a[i][j+1] - temp;       
            if (up>=50 && down>=50 &&left>=50 &&right>=50) count++;
        }
    }
    cout << count;
    return 0;
}