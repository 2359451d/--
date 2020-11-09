#include <stdio.h>
#include <ctype.h>

#define IN 1	/* inside a word */
#define OUT 0	/* outside a word */

/* writes each word from standard input, one per line, on standard output */
int main()
{
	int c, state;

	state = OUT;
	while ((c = getchar()) != EOF)
		if (isalpha(c)) {
			putchar(c);
			state = IN;
		} else {
			// if c非有效字符
			if (state == IN)
				// only appears at the case when a word ends,and next char is a space
				putchar('\n');// converts the space into the newline
			state = OUT;//change the state
		}
	// before EOF, effective charcter
	if (state == IN)
		putchar('\n');//insert a new line
	return 0;
}
