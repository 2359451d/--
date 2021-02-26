// NS3 Formative Exercise 1 - hello_server.c
// Worked Answer
// 
// Copyright (c) 2009-2017 University of Glasgow
// All rights reserved.

#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <unistd.h>
// 缓冲大小
#define BUFLEN 3000

int 
main(void)
{
  int                 fd,   conn_fd; 
  ssize_t             rlen; //实际读取字节
  struct sockaddr_in  addr, conn_addr;
  socklen_t           conn_addr_len;
  char                buf[BUFLEN];
  int                 flags = 0;

  // Create a TCP/IP socket， ipv4
  fd = socket(AF_INET, SOCK_STREAM, 0);
  if (fd == -1) {
    perror("Unable to create socket");
    return 1;
  }

  // Bind to port 5000
  // 使用struct sockaddr_in
  addr.sin_family      = AF_INET;
  addr.sin_port        = htons(5000);
  addr.sin_addr.s_addr = INADDR_ANY;

  if (bind(fd, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
    perror("Unable to bind to port");
    return 1;
  }

  // Listen for a connection
  //backlog=1,一次监听的链接数
  if (listen(fd, 1) == -1) {
    perror("Unable to listen for connection");
    return 1;
  }

  // Accept the first connection
  conn_addr_len= sizeof(struct sockaddr);
  if ((conn_fd = accept(fd, (struct sockaddr *) &conn_addr, &conn_addr_len)) == -1){
    perror("Unable to accept connection");
    return 1;
  }

  // Retrieve the data
  // 接收数据
  while ((rlen = recv(conn_fd, buf, BUFLEN, flags)) > 0) {
    // 打印数据
    int i;
    for (i = 0; i < rlen; i++) {
      printf("%c", buf[i]);
    }
    printf("\n");
  }

  // Close the connection, and the underlying listening socket
  close(conn_fd);
  close(fd);

  return 0;
}

// vim: set ts=2 tw=0 ai et:
