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


int main()
{
 
 char cpf_buscado[9];
 cout << "Digite o CPF: ";
 cin >> cpf_buscado;
 
 File f* = fopen("alumos.csv","r");
 int qtd_atributos =0;
 int qt;
 char ch;
char * pch;
 qt = fread(&e,sizeof(Aluno),1,f);
 
 
 while (fgetc(f)) != EOF)
 {
	linha = getc(f);

	//A função strtoc recebe o endereço dos dados e os caracteres de separação
	id_inscricao = strtok(linha, ","); 
	curso =  strtok(NULL, ", ");
	cpf = strtok(NULL, ", ");

	if(cpf_buscado == cpf)
	{
		cout << "Curso:  %s", curso;
		break;
	}
}

  return 0;
}