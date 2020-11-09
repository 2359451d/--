/*
 * usage: ./collapws file
 *
 * copies standard input to standard output, collapsing sequences of white
 * space characters (as determined by isspace()) to a single blank character ' '
 *
 * sequences of white space characters in quoted strings are NOT collapsed
 */
#include <stdio.h>
#include <ctype.h>

#define INQUOTE 2	/* in quoted string */
#define IN 1		/* in a sequence of non-white space characters */
#define OUT 0		/* not in token */

#define UNUSED __attribute__ ((unused))

int main(UNUSED int argc, UNUSED char *argv[]) {
   int c, state;

   state = OUT;
   while ((c = getchar()) != EOF) {
      if (state == INQUOTE) {
         putchar(c);//output（引号模式，空格可以输出）
         if (c == '"')
            state = OUT;//out of the word
      } else if (state == IN) {
         if (isspace(c)) {// if 空格（此状态本应该输入单词，折叠空格）
            state = OUT; // 状态，OUT OF THE WORD
            putchar(' ');// 输出1个空格，折叠
	      } else if (c == '"') {// if “
            putchar(c);//输出 ”
	         state = INQUOTE;//状态 “”
         } else {
            putchar(c);//其他字符，直接输出
         }
      } else {		/* state == OUT */
         if (! isspace(c)) {
            putchar(c);// 如果读入c，有效字符，直接输出
         if (c == '"')//如果为引号
            state = INQUOTE;//引号状态
         else
            state = IN;//其他状态，in word
         }
      }
   }

   putchar('\n');
   return 0;
}
