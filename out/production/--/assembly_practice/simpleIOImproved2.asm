	.data
  newline:	.asciiz "\n"
  msg_prompt:	.asciiz "Please type a character, '?' to exit:"
  capitalListLen:	.space 104 # 26 * 4B = 104B
  capitalList: .asciiz "Alpha","Bravo","China","Delta","Echo","Foxtrot","Golf","Hotel","India","Juliet","Kilo","Lima","Mary","November","Oscar","Paper","Quebec","Research","Sierra","Tango","Uniform","Victor","Whisky","X-ray","Yankee","Zulu"
  numericListLen:	.space 40
  numericList: .asciiz "zero","First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Nineth"
  lowerListLen:		.space 104
  lowerList:  .asciiz  "alpha","bravo","china","delta","echo","foxtrot","golf","hotel","india","juliet","kilo","lima","mary","november","oscar","paper","quebec","research","sierra","tango","uniform","victor","whisky","x-ray","yankee","zulu"
	.text
main:
  ## print prompt & read char
  li $v0, 4
  la $a0, msg_prompt
  syscall
  li $v0, 4
  la $a0, newline
  syscall
  li $v0, 12 
  syscall
  
  ## check if input=='?'
  move $a0, $v0 # $a0 = prompt
  beq $a0, 63, Terminate # prompt=='?' ,type '?' to terminate

########################
checkIfCapital:
  jal isCapital # isCapital(prompt)
  seq $t0, $v0, 1 # set $t0=1 if $v0 == 1
  bne $t0, 1, checkIfNumeric # branch if not isCapital(prompt)
  
  ## if is capital
  subi $a0, $a0, 65 # prompt-65, arg1: index
  la $a1, capitalList # arg2: &List
  jal getElement
  move $v1, $v0
  la $a0, newline
  li $v0, 4
  syscall
  la $a0, ($v1)
  li $v0, 4
  syscall
  la $a0, newline
  li $v0, 4
  syscall
  j main
########################
checkIfNumeric:
  jal isNumeric # isNumeric(prompt)
  seq $t0, $v0, 1 # set $t0=1 if $v0 == 1
  bne $t0, 1, checkIfLower # branch if not isNumeric(prompt)
  
  ## if is numeric
  subi $a0, $a0, 48 # prompt-48, arg1: index
  la $a1, numericList # arg2: &List
  jal getElement
  move $v1, $v0
  la $a0, newline
  li $v0, 4
  syscall
  la $a0, ($v1)
  li $v0, 4
  syscall
  la $a0, newline
  li $v0, 4
  syscall
  j main
########################
checkIfLower:
  jal isUncapitalized # isUncapitalized(prompt)
  seq $t0, $v0, 1 # set $t0=1 if $v0 == 1
  bne $t0, 1, other # branch if not isNumeric(prompt)
  
  ## if is lower case
  subi $a0, $a0, 97 # prompt-97, arg1: index
  la $a1, lowerList # arg2: &List
  jal getElement
  move $v1, $v0
  la $a0, newline
  li $v0, 4
  syscall
  la $a0, ($v1)
  li $v0, 4
  syscall
  la $a0, newline
  li $v0, 4
  syscall
  j main
########################
other: # print ¡®*¡¯
  li $v0, 4
  la $a0, newline
  syscall
  li $a0, 42
  li $v0, 11
  syscall
  li $v0, 4
  la $a0, newline
  syscall
  j main
########################
Terminate:
  li $v0, 10
  syscall
########################
getElement: # int getElement($a0: index, $a1: list), return the element address
  addi $sp, $sp, -12 # reuse 3 reg, allocating 12B of contiguous block
  sw $ra, 12($sp)
  sw $t0, 8($sp)
  sw $t1, 4($sp)
  sw $t2, 0($sp)

  ## initialise
  move $t0, $a1 # $t0=i=start address of List
  li $t1, 0 # $t1=j=0
  Loop: # find the index(byte)
  	beq $t1, $a0, endLoop # branch if $t1==$a0: index, (scan finished at the position expected)
  	lb $t2, ($t0) # $t2:= capitalList[i](byte)
  	addi $t0, $t0, 1
  	beq $t2, $zero, nextElement # $t2?= '\0'
  	j Loop
  nextElement:
  	addi $t1, $t1, 1
  	j Loop
  endLoop:
        move $v0, $t0
  lw $ra, 12($sp)
  lw $t0, 8($sp)
  lw $t1, 4($sp)
  lw $t2, 0($sp)
  addi $sp, $sp, 12
  jr $ra
########################
isCapital:
  # one arg: char allocate 4B Stack
  addi $sp, $sp, -4
  sw $t0, 0($sp)
  sw $ra, 4($sp)
  
  move $t0, $a0 # char = $a0
  blt $t0, 65, notNumeric # branch if char<=65
  bgt $t0, 90, notNumeric # branch if char>=90
  li $v0, 1
  j endIsCapital
notNumeric:
  li $v0, 0
endIsCapital:
  lw $ra, 4($sp)
  lw $t0, 0($sp)
  addi $sp, $sp, 4
  jr $ra
########################
isNumeric:
  # arg: char allocate 4B  contiguous block of memory for stack
  addi $sp, $sp, -4
  sw $ra, 4($sp)
  sw $t0, 0($sp) # char
  
  move $t0, $a0
  blt $t0, 48, isAlphabet
  bgt $t0, 57, isAlphabet
  li $v0, 1
  j endIsNumeric
isAlphabet:
  li $v0, 0
endIsNumeric:
  lw $ra, 4($sp)
  lw $t0, 0($sp)
  addi $sp, $sp, 4
  jr $ra
########################
isUncapitalized:
  # arg: char allocate 4B contiguous block of memory for stack
  addi $sp, $sp, -4
  sw $ra, 4($sp)
  sw $t0, 0($sp)
  
  move $t0, $a0
  blt $t0, 97, notUncapitalized
  bgt $t0, 122, notUncapitalized
  li $v0, 1
  j endIsUncapitalized
notUncapitalized:
  li $v0, 0
endIsUncapitalized:
  lw $ra, 4($sp)
  lw $t0, 0($sp)
  addi $sp, $sp, 4
  jr $ra
  


    
  