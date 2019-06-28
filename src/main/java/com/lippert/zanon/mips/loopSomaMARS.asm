.text
la $t0, resultado
lw $t1,0($t0)
addiu $t2,$t2,10
loop:beq $t1,$t2,saida
addiu $t1,$t1,1
j loop
saida:addiu $t1, $t1, -1
sw $t1, 0($t0)
.data			
resultado: .word 000
