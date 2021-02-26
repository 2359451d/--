#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#define BUFLEN 1500

int main(int argc, char const *argv[])
{
    // create socket,The final paramet\er is not used with TCP or UDP sockets, and is set to zero
    int fd = socket(AF_INET, SOCK_STREAM, 0);
    if (fd==-1)
    {
        // error handling
    }
    // bind the socket to well-known port for listening
    // 客户端随后用IP & port连入server
    // &addr struct sockaddr泛型 - 指定IP & 应绑定的port 【再socket & netine头文件中定义了 struct sockaddr结构体】
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = INADDR_ANY;//默认地址,让server使用任意可用网络接口
    addr.sin_port = htons(80);

    if (bind(fd, (struct sockaddr *) &addr, sizeof(addr))==-1){
        //error
    }

    // 绑定好fd套接字于ip&port后，开始监听fd
     // 指定等待队列中client连接数量，不i是最大连接数量
     //是服务器一次性【接收连接】的最大数量
    int backlog=10;
    if(listen(fd, backlog)==-1)
    {
        // error
    }

    //创建，绑定，监听完成后，loop循环等待连接接入
    while (...){
        struct sockaddr_in cliaddr; //client ip & port信息
        socklen_t cliaddr_len = sizeof(cliaddr);
        //连接接入成功，返回connfd，&cliaddr
        // fd还可以继续接入client新连接 （<backlog）
        // 如果无新连接，accept阻塞
        int connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddr_len);
        if (connfd == -1){
            // an error occurred
        }

        //读
        ssize_t rcount;
        char buf[BUFLEN];
        int flags=0; //not to set flags
        rcount = recv(fd, buf, BUFLEN, flags);
        if (rcount``=1)
        {
            //error
        }
        

    }

    return 0;
}

