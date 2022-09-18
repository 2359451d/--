#include<iostream>
#include<iomanip>
#include<cmath>
using namespace std;
int main()
{
    int n;
    cin>>n;
    float *x,*y;
    double max=0;
    double t;
    int i,j;
    x=new float[n];
    y=new float[n];
    for(i=0;i<n;i++)
        cin>>x[i]>>y[i];
    for(i=0;i<n-1;i++)
    {
        for(j=i+1;j<n;j++)
        {
            t=(x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]);
            if(max<t) max=t;
        }
    }
    max=sqrt(max);
    cout<<setiosflags(ios::fixed)<<setprecision(4)<<max<<endl;
    delete []x;
    delete []y;
}