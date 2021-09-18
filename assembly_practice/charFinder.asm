	.data
string_buffer: .space 1024 
msg_prompt:  .asciiz "Please type a string:\n"
msg_prompt2:  .asciiz "Please type a char for searching, '?' to exit:\n"
msg_fail:  .asciiz "Fail!\n"
msg_success:  .asciiz "Success!Location: "
newline: .asciiz "\n"
	.text
main:
## print prompt & read string
  li $v0, 4
  la $a0, msg_prompt
  syscall
  la $a0, string_buffer
  li $a1, 1024
  li $v0, 8
  syscall
  move $t0, $a0 # $t0:= string
#########################################
Loop:
## print prompt & read char circularly
  li $v0, 4
  la $a0, msg_prompt2
  syscall
  li $v0, 12
  syscall
  move $t1, $v0 # $t1:= char
  li $v0, 4
  la $a0, newline
  syscall

  beq $t1, 63, endLoop
  move $a0, $t0
  move $a1, $t1
  jal charFinder # $v0 = charFinder(prompt, char)
  beq $v0, -1,Fail # flag=index=-1
  move $t2, $v0
Success:
  la $a0, msg_success
  li $v0, 4
  syscall

  move $a0, $t2
  li $v0, 1
  syscall
  
  la $a0, newline
  li $v0, 4
  syscall
  
  j Loop
Fail:
  li $v0, 4
  la $a0, msg_fail
  syscall
  j Loop
endLoop:
  li $v0, 10
  syscall
#########################################
charFinder:# int charFinder($a0: prompt, $a1: char)
  addi $sp, $sp, -16 # allocate 20B contiguous block of memory
  sw $ra, 16($sp)
  sw $t0, 12($sp)
  sw $t1, 8($sp)
  sw $t2, 4($sp)
  sw $t3, 0($sp)
  
  li $t0, -1 # flag = -1
  li $t1, 0 # i = 0
  
  readByte:
    move $t2, $a0
    add $t2, $t2, $t1
    add $t1, $t1, 1 # i++
    lb $t3, ($t2) # current char
    beq $t3, $zero, endReadByte
    bne $t3, $a1, readByte
    move $t0, $t1
  endReadByte:
  move $v0, $t0 # return flag
  lw $ra, 16($sp)
  lw $t0, 12($sp)
  lw $t1, 8($sp)
  lw $t2, 4($sp)
  lw $t3, 0($sp)
  addi $sp, $sp, 16
  jr $ra
  