#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <arpa/inet.h>

int main(int argc, char *argv[])
{
    struct addrinfo hints;
    struct addrinfo *ai0, *ai;
    int i;
    char* hostname;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = PF_UNSPEC; // unspecified protocol both v4/6 okay
    hints.ai_socktype = SOCK_STREAM; // TCP socket

    for (int count=1; count < argc; count++)
    {
        // each hostname from commandline
        hostname = argv[count];

        // DNSlookup
        if ((i= getaddrinfo(hostname, "80", &hints, &ai0))!=0)
        {
            printf("Error: unable to lookup IP address: %s", gai_strerror(i));
        }
        
        // ai0 head pointer, ai pointer used to iterate the linked list
        for (ai = ai0; ai!=NULL; ai= ai->ai_next)
        {
            if(ai->ai_family==AF_INET){
                // IPV4 type
                char ip_addr[INET_ADDRSTRLEN];
                struct sockaddr_in *infov4 = (struct sockaddr_in *) ai->ai_addr;
                inet_ntop(AF_INET, &infov4->sin_addr, ip_addr, sizeof(ip_addr));
                // print out the information for each ip node
                // hostname, address type, IP addr
                printf("%s IPv4 %s \n", argv[count], ip_addr);
            } else {
                // IPV6 type
                char ip_addr[INET6_ADDRSTRLEN];
                struct sockaddr_in6 *infov6 = (struct sockaddr_in6 *) ai->ai_addr;
                inet_ntop(AF_INET6, &infov6->sin6_addr, ip_addr, sizeof(ip_addr));
                // print out the information for each ip node
                // hostname, address type, IP addr
                printf("%s IPv6 %s \n", argv[count], ip_addr);
            }
        }
    }
    return 0;
}
