CC = clang
str: str.o
	$(CC) str.o -o str
%.o: %.c
	$(CC) -c $< -o $@

.PHONY:clean
clean:
	rm *.exe *.out *.i *.o *.s *.e
