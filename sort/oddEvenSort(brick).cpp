#include <iostream>
using namespace std;

int main()
{
	int a[10], temp;
	bool flag = true;
	int size = 10;
	for (int i = 0; i < 10; i++) {
		 // read 10 int
		cin >> a[i];
	}

	while (flag) // when to end looping?
	{
		flag = false;
		// sort odd index
		for (int i = 1; i < 10-1;i+=2) 
		{
			if (a[i]>a[i+1]) {
				temp = a[i];
				a[i] = a[i + 1];
				a[i + 1] = temp;
				// not sorted
				flag = true;
			}
		}
		// sort even index
		for (int j = 0; j < 10-1;j+=2)
		{
			if (a[j] > a[j + 1]) {
				temp = a[j];
				a[j] = a[j + 1];
				a[j + 1] = temp;
				// not sorted
				flag = true;
			}
		}
	}

	for (int i = 0; i < 10;i++) {
		cout << a[i] << " ";
	}

	return 0;
}