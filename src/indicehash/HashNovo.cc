#include <stdio.h>
#include <string.h>
#include <windows.h>

#pragma pack(1)

int main(int argc, char** argv){

	FILE *entrada, *saida;
    	
	entrada = fopen("bolsafamilia.dat","r");

	if(!entrada)
	{
		fprintf(stderr,"\nArquivo de leitura %s não conseguiu ser aberto", "bolsafamilia.dat");
		return 1;
	}
	saida = fopen("hash.dat","w+");
	if(!saida)
	{
		fclose(entrada);
		fprintf(stderr,"\nArquivo de escrita %s não conseguiu ser aberto", "hash.dat");
		return 1;
	}

	// volta ao ultimo caracter do arquivo.
	fseek(entrada,0,SEEK_END);	
	
	char temp[11];
	char nis[11];
	char EntradaRua[73];
	char testeNIS, testeProx;
	int x = 0, aux, cont, pos, TB_SIZE = 900001, colisao = 0, maiorColisao = 0;
	long GuardaPosicao, Proximo, SaidaNIS, EntradaNIS, ChaveNIS, Ponteiro;
	long Linha = 0, i = 0, chaveColisao;
	long ultimoRegistro = ftell(entrada)/300;   // fim = ultima linha do arquivo bolsafamilia.dat.
	fseek(entrada,0,SEEK_SET);				//volta ao inicio 

	for(i=0;i<TB_SIZE;i++)
	{				    // Completa os campos no arquivo com "-00000000" (9 bytes).
		fprintf(saida,"%s","-00000000");	// NIS inicial - 11 bytes
		fputc(' ',saida);                   // 1 byte
		fprintf(saida,"%s","-00000000");	// Posicao inicial - 11 bytes
		fputc(' ',saida);					// 1 byte
		fprintf(saida,"%s","                                                                        ");	// Rua - 72 bytes
		fputc('\n',saida);                   // 1 byte
	}

	for(i=0;i<ultimoRegistro;i++)
	{
		fgets(EntradaRua, 73, entrada);
		fseek(entrada,218,SEEK_CUR);
		fscanf(entrada, "%08ld", &EntradaNIS);
		fseek(entrada,2,SEEK_CUR);//espaco branco e quebra de linha
		printf("linha atual: %i\n",i);
				
		ChaveNIS = EntradaNIS%TB_SIZE;			// linha com o nis
		Ponteiro = (ChaveNIS*94);			
		
		fseek(saida,Ponteiro,SEEK_SET);  	// Vai para a linha (no arquivo de saida) onde vai ser gravado o NIS. (informacao da linha inteira)
		fread(&testeNIS,1,1,saida);    		// '-' = vazio e '+' = ocupado.
        fscanf(saida,"%08ld",&SaidaNIS);    // nis 
		fseek(saida,1,SEEK_CUR);         	// Pular espaco em branco
        fread(&testeProx,1,1,saida);        // prox sinal 
        fscanf(saida,"%08ld",&Proximo);  	// prox campo	
		fseek(saida,1,SEEK_CUR);         	// Pula espaco em branco
		fseek(saida,72,SEEK_CUR);         	// Pula  endereço
        fseek(saida,1,SEEK_CUR);         	// Pula \n             
		
		
		//comparacao para saber se há colisão
		if(testeNIS == '-')
		{           	// Caso verdade o campo valor esta vazio.
            fseek(saida,Ponteiro,SEEK_SET);       // Volta ao inicio da linha.
            fputc('+',saida);
            fprintf(saida,"%08ld",EntradaNIS);			// Coloca o nis vindo do arquivo do bolsafamilia
			fseek(saida,10,SEEK_CUR);			//Vai para a area de endereco
			fputc(' ',saida);
			fprintf(saida,"%d",i);
		}
		if(testeNIS == '+')
		{
            colisao = 0;
			if(testeProx == '-'){
				fseek(saida,0,SEEK_END);	// Vai para a ultima linha do arquivo
				Linha = (ftell(saida)/94)+1;	// Grava a linha na qual o item foi gravado
            	fputc('+',saida);
            	fprintf(saida,"%08ld",EntradaNIS);				
				fputc(' ',saida);
				fprintf(saida,"%s","-00000000");
				fputc('\n',saida);
				
				fseek(saida,Ponteiro+10,SEEK_SET);	// Retorna para gravar o local onde o NIS foi armazenado
				fputc('+',saida);
                fwrite(temp,11,1,saida);
				fseek(saida,11,SEEK_CUR);
				fputc('\n',saida);
			}
            if(testeProx == '+')
            {
            		while(testeProx == '+')
            		{						// neste ponto o while vai ser sempre iniciado
						colisao++;
						if(colisao>maiorColisao){	maiorColisao = colisao;	chaveColisao = ChaveNIS;	}
						
						GuardaPosicao = Proximo;
						fseek(saida,((Proximo*94)-94)+10,SEEK_SET);	// vai para vai para a linha do informada no campo "proximo"
						fread(&testeProx,1,1,saida);
						fscanf(saida,"%08ld",&Proximo);
						//printf("tprox %c | Proximo %ld\n",testeProx,Proximo);
						
						if(testeProx == '-'){
							
							fseek(saida,0,SEEK_END);			// Vai para a ultima linha do arquivo
							Linha = (ftell(saida)/94)+1;			// Grava a linha na qual o proximo item foi gravado
							fputc('+',saida);
				            fprintf(saida,"%08ld",EntradaNIS);			// Transforma Linha(long) em temp(char[8])
							fputc(' ',saida);
							fprintf(saida,"%s","-00000000");
							fputc(' ',saida);
							fprintf(saida,"%s",EntradaRua);
							fputc('\n',saida);
							
							fseek(saida,((GuardaPosicao*94))+10,SEEK_SET);	// Retorna para gravar o local onde o novo nis foi armazenado
							fputc('+',saida);
				            fwrite(temp,8,1,saida);
							fseek(saida,73,SEEK_CUR);	
							fputc('\n',saida);
						}
                 	}

            }
		}
	}

	printf("A chave %ld teve maior colisao: %i.", chaveColisao,maiorColisao);
	system("pause");
		
	fclose(entrada);
	fclose(saida);
}
