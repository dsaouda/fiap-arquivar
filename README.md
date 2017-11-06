# fiap-hack

Projeto destinado a alunos que desejam realizar download das apostilas de seu curso e seus trabalhos enviados (individual ou em grupo) para guardar de forma organizada.

# compilar o projeto

`./gradlew build` e para gerar o jar `./gradlew fatJar`. O arquivo gerado está localizado no diretório build/libs

# modo de uso

## pegar um cookie autenticado na FIAP

1. Acessar o site da fiap (https://www2.fiap.com.br/) e realizar o login através de um navegador;
2. Abrir o modo de inspesionar elemento do navegador;
3. Ir até a aba "Network" (para o chrome ou firefox);
4. Dê um F5 na página;
5. Clique no log da primeira página (Home por exemplo);
6. Clique no Header
7. Copie o valor do cookie (Tudo que vier depois do "Cookie: " - Essa imagem pode ajudar -> )

## download trabalhos (opção -t)

Crie um diretório e informe na opção -d 

```bash 
java -jar fiap-hack.jar -t -d '<informe um diretório>' -c '<cole o valor do cookie aqui>'
```

## download apostilas (opção -a)

Crie um diretório e informe na opção -d 

```bash 
java -jar fiap-hack.jar -a -d '<informe um diretório>' -c '<cole o valor do cookie aqui>'
```
