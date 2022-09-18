#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>

int main(int argc, char const *argv[])
{
    // 硬编码
    // .. client socket
    struct sockaddr_inaddr;
    // fd接入至server套接字ip & port
    if (connect(fd, (struct sockaddr *)&addr, sizeof(addr))==-1)
    {
        //error
    }

    /* 
        使用DNS(addrinfo)查找server可能的ip
    */
   struct addrinfo hints;
   struct addrinfo *ai0;
   int i;

//    see hints? 表示地址类型之类的信息
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = PF_UNSPEC; // unspecified protocol both v4/6 okay
    hints.ai_socktype = SOCK_STREAM; // TCP socket

    // DNS查找， ai0为addrinfo值链表（server的可能ip）的头指针
    if ((i= getaddrinfo('www.example.com', "80", &hints, &ai0))!=0)
    {
        printf("Error: unable to lookup IP address: %s", gai_streeror(i));
    }
    
    struct addreinfo *ai;
    int fd;

    // 遍历DNS查找结果链表，依此进行连接尝试
    // 一旦成功连入一个，loop结束
    // ai0是头指针，ai指针用于遍历
    for (ai = ai0; ai!=NULL; ai= ai->ai_next)
    {
        // 每个ip节点尝试建立socket
        fd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
        if (fd==-1)
        {
            // unable to create socket
            continue;
        }
        if (connect(fd, ai->ai_addr, ai->ai_addrlen)==-1)
        {
            // unable to connect to the address
            close(fd);
            continue;
        }
        break; // 成功连接
    }

    // 写
    char *data = "Hello, world"
    size_t data_len = 1500;
    int flags = MSG_NOSIGNAL;

    //send成功，返回发送的bytes数（<data_len）
    if (send(fd, data, data_len, flags)==-1)
    {
        //error
    }

    return 0;
}
