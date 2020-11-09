// #include <stdio.h>

// %[flags][width][.prec][hlL]type
int main(int argc, char const *argv[])
{
    // flags
    printf("%9d\n",123);//该str占用9个字节空间
    printf("%09d\n",123);//该str占用9个字节空间，0填充前面
    printf("%-9d\n",123);//该str占用9个字节空间，左对齐
    printf("%+9d\n",123);//该str占用9个字节空间，且强制输出+

    /* 
        width prec1 含义
        number      最小字符数
        *           下一个参数是字符数
        .number     小数点后的位数
        .*          下一个参数是小数点后的位数
     */
    printf("%9.2f\n", 123.0);//浮点数，9位，小数点后2位
    printf("%*d",6,123);//字符6填*，123填d

    /* 
        type类型修饰  含义
        hh          单个字节
        hh          SHORT
        l           long
        ll          long long
        L           long double

        i/d         int
        u           unsigned int
        o           octal
        x           hexidecimal
        X           hexidecimal(capital)
        f/F         float, 6
        e/E         exponent
        g           float
        G           float
        a/A         float, in hexidecimal
        c           char
        s           string
        p           pointer
        n           in/output counts
     */
    printf("%hhd\n",12345);//12345-16进制，取最低2bit（39）-decimal:57
    int num;
    printf("%dty\n", 12345, &num);// 7个char 输入num
    printf("num=%d",num);

    /* 
        scanf： %[flag]type

         flag   含义
         *      跳过
         数字   最大字符数
         hh    char
         h      short
         l      long,double
        ll      long long
        L       long double

        type     
        d       int
        i       integer(数字可能为10/16/8进制)
        u       unsigned int 
        o       octal
        x       hexidecimal
        a,e,f,g float
        c       char
        s       string
        [...]   alloed char
        p       pointer
     */
    scanf("%*d%d", &num);//跳过第一个%d，第二个存入&num


    return 0;
}
