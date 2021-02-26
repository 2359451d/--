// NS3 Formative Exercise 1 - hello_client.c
// Worked Answer
// 
// Copyright (c) 2009-2017 University of Glasgow
// All rights reserved.

#include <arpa/inet.h>
#include <time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>

#define BUFLEN 1500

int
main(int argc, char *argv[])
{
  int                      i; 
  ssize_t                  wlen, rlen;
  int                      fd;
  char                    *buf = malloc(BUFLEN);
  struct addrinfo          hints, *ai, *ai0;
#ifdef __APPLE__
  int flags = 0;  // MacOS X 10.11 doesn't support MSG_NOSIGNAL
#else
  int flags = MSG_NOSIGNAL;
#endif

  if (argc != 2) {
    printf("Usage: %s <hostname>\n", argv[0]);
    return 1;
  }

  // Look up the IP address of the hostname specified on the command line
  memset(&hints, 0, sizeof(hints));
  hints.ai_family    = PF_UNSPEC;
  hints.ai_socktype  = SOCK_STREAM;
  if ((i = getaddrinfo(argv[1], "80", &hints, &ai0)) != 0) {
    printf("Unable to look up IP address: %s", gai_strerror(i));
    return 2;
  }

  // Try to connect to each address in turn
  for (ai = ai0; ai != NULL; ai = ai->ai_next) {
    struct timespec before_connect;
    struct timespec after_connect;

    // Create a TCP/IP socket
    fd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
    if (fd == -1) {
      continue;       // Try the next address
    }

    // Connect to the server
    clock_gettime(CLOCK_MONOTONIC, &before_connect);
    if (connect(fd, ai->ai_addr, ai->ai_addrlen) == -1) {
      close(fd);
      continue;       // Try the next address
    }
    clock_gettime(CLOCK_MONOTONIC, &after_connect);
    printf("before: %ld.%09ld\n", (long) before_connect.tv_sec, before_connect.tv_nsec);
    printf(" after: %ld.%09ld\n", (long)  after_connect.tv_sec,  after_connect.tv_nsec);

    // Send "Hello, world!"
    // 发送数据， %s用 servername替换，即命令行数据argv[1]
    sprintf(buf, "GET / HTTP/1.1\r\nHost: %s\r\nConnection: close\r\n\r\n", argv[1]);
    buf[strlen(buf)-1] = '\0';
    wlen = send(fd, buf, strlen(buf), flags);
    if (wlen == -1) {
      perror("Unable to send request");
      close(fd);
      return 2;
    }

    // 循环接收数据
    while (1) {
      rlen = recv(fd, buf, BUFLEN, 0);
      // 无可读数据，退出循环，准备关闭fd
      if (rlen == 0) {
        printf("\n");
        break;
      }
      if (rlen  < 0) {
        perror("Cannot read");
        break;
      }
      //避免缓冲区溢出
      buf[strlen(buf)-1] = '\0';

      for (i = 0; i < rlen; i++) {
        printf("%c", buf[i]);
      }
    }


    // Close the connection and exit
    freeaddrinfo(ai0);
    close(fd);
    free(buf);
    return 0;
  }

  // Couldn't connect
  printf("Unable to connect\n");
  return 1;
}

// vim: set ts=2 tw=0 ai et:
