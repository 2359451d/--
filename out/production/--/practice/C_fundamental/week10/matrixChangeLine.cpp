#include<iostream>
#include<iomanip>
using namespace std;

int main(){
    int a[5][5];
    int m,n;
    // array input: cin
    for (int i = 0; i < 5; i++)
    {
        for (int j = 0; j < 5; j++)
        {
            cin >> a[i][j];
        }
    }
    cin >> n >> m;
    if (m>=0 && n>=0 && n<=4 && m<=4){
        // swap n line with m line;
        int temp[5];
        for (int i =0;i<5;i++){
            temp[i] =a[n][i];
            a[n][i] = a[m][i];
        }

        for (int i =0;i<5;i++){
            a[m][i] = temp[i];
        }

            for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                cout <<setw(4)<< a[i][j];
            }
            cout<<endl;
        }
    }else {
        cout<< "error";
    }
    return 0;
}