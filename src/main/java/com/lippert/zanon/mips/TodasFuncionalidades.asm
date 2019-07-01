#
#   este programa calcula o enesimo numero da sequencia de fibonacci
#   verifica se o mesmo é impar, e calula ele multiplicado por 2 e 
#   dividido por 2
#   autores: Bruno Lippert e Matheus Zanon
#   Observacao: o codigo asm original esta disponivel no arquivo TodasFuncionalidadesMARS.asm
#
.text
0x3c011001#carrega Qual o numero da sequencia de fibo que queremos
0x34300000
0x3c011001#Endereco do resultado do enesimo numero da sequencia de fibo
0x34310004
0x3c011001#Endereco do resultado se eh impar
0x34320008
0x3c011001#Endereco do result da multiplicacao por 2
0x3433000c
0x3c011001#Endereco do result da divisao por 2
0x34340010
0x8e100000#Lw o numero que queremos
0x25080001#primeiro numero da sequencia de fibo
0x25290001#segundo numero da sequencia
0x254a0001#Quantos numeros ja foram calculados
0x01685821#Ultimo numero calculado da sequencia, comecando com o 1 e 2 caso seja esse o que se quer
0x120a000a#se for o primeiro numero "beq"
0x254a0001#passa para o segundo numero
0x01205821#adiciona o segundo ao numero resultado
0x120a0007#se for o segundo
0x254a0001#avanca o enesimo numero(loop)
0x01095821#soma os dois anteriores em um novo
0x01204021#coloca o antigo segundo para a primeira posicao
0x01604821#coloca o novo numero somado na segunda posicao
0x0150602a#se o numero sendo testado eh menor que o que se quer, devemos continuar
0x11800001#beq caso achou o enesimo numero
0x08100013#Jump para o loop caso nao achou
0xae2b0000#Achou - sw armazena
0x24180001#adiciona um no reg para fazer a mascara e descobrir se eh impar
0x030bc024#se for impar o numero fica em 1 (and)
0xae580000#guarda se eh impar (sw)
0x000bc040#multiplica sll
0xae780000#guarda
0x000bc042#divide srl
0xae980000#guarda
.data
A: .word 0x00000008#enesimo numero que queremos achar	
resultado: .word 0x00000000#qual eh o enesimo numero da sequencia de fibbonaci
impar: .word 0x00000000#este enesimo numero eh impar? se sim vai pra 1
mul2: .word 0x00000000#este numero multiplicado por 2
div2: .word 0x00000000#este numero dividido por 2