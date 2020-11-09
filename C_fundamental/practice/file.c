#include <stdio.h>
int main(int argc, char const *argv[])
{
    FILE *fp = fopen("12.in", "r");
    if (fp)
    {
        int num;
        fscanf(fp, "%d", &num);
        printf("%d\n", num);
        fclose(fp);
    }else
    {
        printf("cannot open the file");
    }
    return 0;
}
