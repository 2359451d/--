#include<iostream>
#include <iomanip>
using namespace std;
int main(){
    int counts=80, a=0, e=0, i=0, o=0, u=0;
    char s[80];
    cin.getline(s, counts);
    for (int j=0;j<counts;j++){
        switch (s[j])
        {
        case 'a':
            a++;
            break;
        case 'e': e++; break;
        case 'i': i++; break;
        case 'o': o++; break;
        case 'u': u++; break;
        default:
            continue;
        }
    }
    cout<<a<<" "<<e<<" "<<i<<" "<<o<<" "<<u;
    return 0;
}