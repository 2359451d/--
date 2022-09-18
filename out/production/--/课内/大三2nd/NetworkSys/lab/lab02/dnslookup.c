#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>

int main(int argc, char const *argv[])
{
    // 硬编码
    // .. client socket
    struct sockaddr_inaddr;
    /* 
        使用DNS(addrinfo)查找命令行域名的所有可能的ip
    */

    struct addrinfo hints;
    struct addrinfo *ai0;
    int i, count;

    // see hints? 表示地址类型之类的信息
    memset(&hints, 0, sizeof(hints));

    hints.ai_family = PF_UNSPEC; // unspecified protocol both v4/6 okay
    hints.ai_socktype = SOCK_STREAM; // TCP socket

    for (count=1; count < argv; ++count)
    {
        // each hostname from commandline
        char hostname = argv[count];

        // DNS查找，ai0为addrinfo值链表（命令行的可能ip）的头指针
        if ((i= getaddrinfo(hostname, "80", &hints, &ai0))!=0)
        {
            printf("Error: unable to lookup IP address: %s", gai_streeror(i));
        }
        
        struct addrinfo *ai;
        const char* ip_addr;
        memset(&ip_addr, 0, sizeof(ip_addr));

        // 遍历DNS查找结果链表，依此进行连接尝试
        // 一旦成功连入一个，loop结束
        // ai0是头指针，ai指针用于遍历
        for (ai = ai0; ai!=NULL; ai= ai->ai_next)
        {
            inet_ntop(AF_INET, ai->ai_addr, ip_addr, sizeof(ip_addr));
            // print out the information for each ip node
            // hostname, address type, IP addr
            printf("%s %s %s", argv[count], ai->ai_protocol, ip_addr);
                continue;
        }
    }
    // 写

    return 0;
}
