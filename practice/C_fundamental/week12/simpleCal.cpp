#include<iostream>
using namespace std;
int main(){
    /* 
        supporting the arithmetic operation of +,-*,/
    */
    int x,y,result;
    char op;
    cin>>x>>y>>op;
    switch (op)
    {
        case '+':
            result = x +y;
            cout<<result;
            break;
        case '-':
            result = x +y;
            cout<<result;
            break;
        case '*':
            result = x * y;
            cout<<result;
            break;
        case '/':
            if (y==0) cout<<"Divided by zero!";
            else cout<<x/y;
            break;
        default: cout<< "Invalid operator!";
    }
    return 0;
}