#include <iostream>
#include <stdio.h>
#include <string.h>
using namespace std;

typedef struct _Aluno Aluno;
 
struct _Aluno
{
    char id_inscricao;
    char curso[15];
    char cpf[14];
    char dataNascimento[10];
    char sexo;
    char email[25];
    char opcaoQuadro;
};

//padrao = %c%s%s%s%c%s%c

int main()
{
 
 char cpf_buscado[9];
 cout << "Digite o CPF: ";
 cin >> cpf_buscado;
 
 File f* = fopen("alunos.csv","r");
 int qtd_atributos =0;
 
 char * pch;
 char linha[60];
 
pch = fgets(linha, 100, f);
 
 while (pch != EOF)
 {
	//A função strtoc recebe o endereço dos dados e os caracteres de separação
	id_inscricao = strtok(linha, ","); 
	curso =  strtok(NULL, ", ");
	cpf = strtok(NULL, ", ");

	if(cpf_buscado == cpf)
	{
		cout << "Curso:  %s", curso;
		break;
	}

	pch = fgets(linha, 100, f);
}

  return 0;
}