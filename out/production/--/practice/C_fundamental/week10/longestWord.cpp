
#include<iostream>
using namespace std;
int main() {
	char input[500];
	cin.getline(input, 500);
	int end = 0;//这个用来标记最大单词结尾在字符数组中的位子
	int count = 0;//这个用来数每个单词的长度
	int longest = 0;//用来记录当前所找到的最长单词的长度
	int i = 0;
	while (i < 500)
	{
		if (input[i] != ' '&&input[i] != ','&&input[i] != '.') {
			count++;
			i++;
			if (count > longest) {//如果这里是》=，那么如果有相同长度的单词，就会打印最后面的。
				longest = count;
				end = i;
 
			}
		}
 
		if (input[i] == ' ' || input[i] == ',' ) {			
			count = 0;
			i++;
		}
		if (input[i] == '.'|| input[i] == '\0' )
			break;
	}
	for (int j = end - longest; j < end; j++) {
		cout << input[j];//end-longest就是最长单词的开始位子，然后输出整个单词。
	}
	cout << endl;
	return 0;

// #include<iostream>
// using namespace std;
// int main(){
//     int num=0, max=0, start_index=0;
//     char result;
//     bool flag =0; // 1 represents whitespace, 0 represents char
//     char str[500];
//     cin.getline(str,500,'.'); //read sentence
//     for (int i =0;str[i]!='\0';i++){
//         // cout << str[i]<<endl;
//         if(str[i]!=' ' && str[i+1]!='\0'){
//             num++;
//         }else{
//             if (str[i]==' ' || str[i+1]=='\0'){
//                 if (str[i+1]=='\0')
//                     num++;
//                 if (num>max){
//                     start_index = i - num;
//                     if (str[start_index]==' ') start_index++;
//                     max = num;
//                 }
//                 num = 0;
//             }
//         }
//     }
//     for (int i =start_index;str[i]!='\0'&& str[i]!=' ';i++) cout<<str[i];
//     return 0;
// }