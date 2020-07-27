#include<iostream>
using namespace std;
int main()
{
    int n, distance;
    float v1=1.2, v2=3.0;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin>>distance;
        int tWalk = distance/v1, tBike=distance/v2+50;
        if (tWalk<tBike){
            cout<<"Walk"<<endl;
        } 
        else if (tWalk>tBike){
            cout<<"Bike"<<endl;
        }
        else{
            cout<<"All"<<endl;
        }
    }
    return 0;
}
