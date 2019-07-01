.text
la $s0, A #Qual o numero da sequencia de fibo que queremos
la $s1, resultado
la $s2, impar #endereco do resultado
la $s3, mul2 #endereco do resultado
la $s4, div2 #endereco do resultado
lw $s0,0($s0) #load n que queremos
addiu $t0, $t0, 1 #primeiro numero da sequencia de fibo
addiu $t1, $t1, 1 #segundo numero da sequencia
addiu $t2, $t2, 1 #Quantos numeros ja foram calculados
addu $t3, $t3, $t0 #Ultimo numero calculado da sequencia, comecando com o 1 e 2 caso seja esse o que se quer
beq $s0, $t2, achou #se for o primeiro numero
addiu $t2, $t2, 1 #passa para o segundo numero
addu $t3, $t1, $zero #adiciona o segundo ao numero resultado
beq $s0, $t2, achou #se for o segundo
loop: addiu $t2, $t2, 1 #avanca o enesimo numero
addu $t3, $t0, $t1 #soma os dois anteriores em um novo
addu $t0, $t1, $zero #coloca o antigo segundo para a primeira posicao
addu $t1, $t3, $zero #coloca o novo numero somado na segunda posicao
slt $t4, $t2, $s0 #se o numero sendo testado eh menor que o que se quer, devemos continuar
beq $t4, $zero, achou
j loop
achou: sw $t3,0($s1)
addiu $t8, $zero, 1 #adiciona um no reg para fazer a mascara e descobrir se eh impar
and $t8, $t8, $t3 #se for impar o numero fica em 1
sw $t8, 0($s2) #guarda se eh impar
sll $t8, $t3, 1 #multiplica por 2
sw $t8, 0($s3) #guarda
srl $t8, $t3, 1 #divide por 2
sw $t8, 0($s4) #guarda
.data
A: .word 8			
resultado: .word 000
impar: .word 0
mul2: .word 0
div2: .word 0