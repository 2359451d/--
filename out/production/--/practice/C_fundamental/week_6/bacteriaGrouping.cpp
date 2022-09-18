#include <iostream>
using namespace std;
/*
grouping in terms of the Reproduction rate
reproduction rate = the number of bacteria after 1 hour / the original number of bacteria
*/
int main()
{
    int n, id[100];
    double rate[100];
    cin >> n;
    for (int i =0; i<n;i++){
        int initial, final;
        cin >> id[i] >> initial >> final;
        rate[i] = (double) final / initial; // calculation
    }
    // bubble sorting two arrays in terms of the reproduction rate
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n - i -1; i++)
        {
            if (rate[j+1]> rate[j])
            {
                int tmpId = id[j];
                id[j] = id[j+1];
                id[j+1] = tmpId;

                double tmpRate = rate[j];
                rate[j] = rate[j+1];
                rate[j+1] = tmpRate; 
            }
        }
    }

    double maxDiff = 0;
    int maxDiffIndex = 0;
    for (int i = 0; i < n - 1; i++)
    {
        double diff = rate[i] - rate[i+1];
        if (maxDiff < diff)
        {
            maxDiff = diff;
            maxDiffIndex = i;
        }
    }
    
    cout << maxDiffIndex + 1 << endl;
    for (int i = 0; i >= 0; i--)
    {
        cout << id[i] << endl;
    }

    cout << n - maxDiffIndex - 1 << endl;
    for (int i = n - 1; i >= maxDiffIndex + 1; i--)
    {
        cout << id[i] << endl;
    }
    return 0;
}
