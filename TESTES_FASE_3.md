# Guia de testes locais — preparação e fase 3

Este guia acompanha o estado atual do projeto. As classes dos eventos já existem, mas as janelas ainda não criam nem interpretam esses eventos. Por enquanto, o sensor imprime o valor escolhido e o ventilador liga-se automaticamente após cinco segundos.

Não precisas de um servidor para estes testes. Os exemplos de eventos usados nos testes são dados de teste locais; não representam funcionalidades de comunicação já implementadas.

## 1. Preparar o terminal

Se estiveres na pasta que contém o PDF e a pasta do código, executa:

```bash
cd ProjetoPCD_26_27_GDA
```

**O que faz:** entra na pasta que contém `src`. Os restantes comandos deste guia são executados nessa pasta. Se já estiveres nela, não repitas este comando.

Confirma as ferramentas de Java:

```bash
java -version
javac -version
```

**O que faz:** mostra a versão que executa os programas (`java`) e a versão que os compila (`javac`).

**O que observar:** ambas devem apontar para a versão de Java que escolheste. Se configuraste Java 27, ambas devem mostrar uma versão 27. O projeto também compilou com Java 25.

## 2. Compilar o projeto

```bash
javac -encoding UTF-8 -d bin \
  src/iscot/enums/*.java \
  src/iscot/events/*.java \
  src/iscot/client/gui/*.java \
  src/iscot/client/*.java
```

**O que faz:** compila todos os ficheiros Java e guarda os ficheiros `.class` na pasta `bin`, sem alterar os ficheiros `.java`.

| Parte do comando | Significado |
| --- | --- |
| `javac` | Compilador de Java. |
| `-encoding UTF-8` | Lê corretamente os caracteres acentuados do código. |
| `-d bin` | Guarda o resultado da compilação em `bin`. |
| `*.java` | Seleciona os ficheiros Java de cada pasta indicada. |
| `\` no fim de uma linha | Permite continuar o mesmo comando na linha seguinte. |

**O que observar:** se tudo estiver correto, o comando termina sem mensagens de erro. Volta a compilar sempre que alterares o código, antes de repetir os testes.

## 3. Testar a janela do sensor de humidade

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 Kitchen
```

**O que faz:** inicia o sensor de humidade associado à cozinha.

| Parte do comando | Significado |
| --- | --- |
| `java` | Executa um programa Java. |
| `-cp bin` | Procura as classes compiladas na pasta `bin`. |
| `iscot.client.HumiditySensor` | Classe cujo método `main` será executado. |
| `localhost` | Endereço de exemplo do servidor; ainda não é utilizado pelo código atual. |
| `12345` | Porto de exemplo do servidor; ainda não é utilizado pelo código atual. |
| `Kitchen` | Área da casa, utilizada para identificar a janela. |

### Passos e observações

1. Confirma que aparece uma janela com o título `Humidade em Kitchen`.
2. Confirma que o valor inicial é 50% e o slider permite valores entre 0 e 100.
3. Move o slider para 80 e larga-o.
4. Move-o para 20 e larga-o.
5. Experimenta os extremos 0 e 100.

**O que observar atualmente:** a etiqueta da janela acompanha o valor. A separação decimal pode aparecer como vírgula ou ponto, conforme as definições do computador. Ao largares o slider num novo valor, a consola mostra, por exemplo:

```text
Mudou para:80.0
Mudou para:20.0
Mudou para:0.0
Mudou para:100.0
```

Durante o arrasto, a etiqueta muda, mas a comunicação do valor para a consola está programada para acontecer quando terminares de arrastar. O arranque, por si só, não imprime a leitura inicial.

**O que este teste verifica:** o funcionamento da interface e a entrega do valor ao trecho de código `valor -> { ... }`. Ainda não verifica a criação de um `HumiditySensorEvent`.

Fecha a janela antes de iniciares outro teste no mesmo terminal. Também podes terminar o programa com `Ctrl+C` no terminal onde o arrancaste.

## 4. Testar a janela do ventilador

```bash
java -cp bin iscot.client.Ventilator localhost 12345 Kitchen
```

**O que faz:** inicia a janela do ventilador da cozinha. Os argumentos têm o mesmo significado que no teste anterior.

### Passos e observações 

1. Confirma que aparece uma janela com o título `Ventilador em Kitchen`.
2. Observa o ícone inicialmente cinzento, que representa o estado desligado.
3. Aguarda aproximadamente cinco segundos.
4. Confirma que as pás passam a verde, indicando o estado ligado.

**O que observar atualmente:** a mudança é automática. Não depende da humidade nem de uma ordem recebida. O ícone muda de cor; o código atual não anima a rotação das pás nem imprime uma confirmação na consola.

**O que este teste verifica:** a interface consegue apresentar os estados desligado e ligado.

## 5. Testar argumentos em falta e uma divisão inválida

Fecha as janelas dos testes anteriores para não confundires janelas antigas com o resultado destes comandos.

### Argumentos em falta

```bash
java -cp bin iscot.client.HumiditySensor
```

**O que faz:** tenta iniciar o sensor sem os três argumentos que o `main` exige atualmente.

**O que observar:** aparece `Invalid arguments!` no terminal e não se abre uma nova janela. Neste momento, essa mensagem não corresponde a um código de saída de erro explícito.

### Divisão com maiúsculas incorretas

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 kitchen
```

**O que faz:** passa uma área que não corresponde exatamente a nenhum valor de `Areas`.

**O que observar:** surge uma `IllegalArgumentException` com uma indicação semelhante a `No enum constant iscot.enums.Areas.kitchen`. Não se abre uma nova janela. Este é o comportamento atual; não existe ainda uma mensagem própria para explicar o erro ao utilizador.

Os valores válidos são exatamente `Kitchen`, `WC`, `LivingRoom`, `BedRoom` e `Garden`.

## 6. Experimentar os eventos da fase 2 sem criar uma classe de teste

```bash
jshell --class-path bin
```

**O que faz:** abre uma consola interativa de Java com acesso às classes compiladas. As instruções seguintes são introduzidas nessa consola, no local onde aparece `jshell>`, e não no terminal normal. Não copies o texto `jshell>`.

Começa por importar os tipos:

```java
import iscot.events.*;
import iscot.enums.*;
```

**O que faz:** permite escrever os nomes curtos das classes e dos enums nos exemplos seguintes.

### Leitura de humidade e herança

```java
var leitura = new HumiditySensorEvent(1, 10, Areas.Kitchen, 80.0);
System.out.println(leitura.getId() + " | " + leitura.getSensorId() + " | " + leitura.getArea() + " | " + leitura.getValue());
System.out.println(leitura instanceof SensorEvent);
System.out.println(leitura instanceof Event);
```

**O que faz:** cria uma leitura local com identificador de evento 1, identificador de sensor 10, área cozinha e valor 80. Depois consulta os dados e confirma a relação de herança.

**O que observar nas três impressões:**

```text
1 | 10 | Kitchen | 80.0
true
true
```

O JShell também apresenta mensagens próprias ao avaliar instruções. Na criação do objeto, pode mostrar algo como `iscot.events.HumiditySensorEvent@...`, porque ainda não definiste uma representação textual própria. Isso não significa que os dados estejam incorretos.

### Duas leituras do mesmo sensor

```java
var segundaLeitura = new HumiditySensorEvent(2, 10, Areas.Kitchen, 20.0);
System.out.println(segundaLeitura.getId() + " | " + segundaLeitura.getSensorId() + " | " + segundaLeitura.getValue());
System.out.println(leitura.getValue());
```

**O que faz:** representa uma nova leitura do mesmo sensor através de outro objeto.

**O que observar:**

```text
2 | 10 | 20.0
80.0
```

O identificador do sensor mantém-se. O identificador e o valor do novo evento são diferentes, e a primeira leitura mantém o seu valor. Os identificadores foram escolhidos manualmente para este teste; a geração automática ainda não está implementada.

### Ordem de ventilação

```java
var ordem = new AreaVentilationOrderEvent(3, Areas.Kitchen, OnOffMode.ON);
System.out.println(ordem.getId() + " | " + ordem.getArea() + " | " + ordem.getState());
System.out.println(ordem instanceof HighLevelOrderEvent);
```

**O que faz:** cria uma ordem local para ligar a ventilação da cozinha e consulta o seu conteúdo.

**O que observar:**

```text
3 | Kitchen | ON
true
```

Criar esta ordem no JShell não altera a janela do ventilador. Ainda falta o código que interpreta a ordem e atualiza o componente gráfico.

### Registo de um cliente

```java
var registo = new RegistrationEvent(4, Areas.Kitchen, Type.SENSOR);
System.out.println(registo.getId() + " | " + registo.getArea() + " | " + registo.getType());
```

**O que faz:** cria os dados de um pedido de registo de um cliente sensor na cozinha. Não estabelece uma ligação nem atribui um identificador de dispositivo.

**O que observar:**

```text
4 | Kitchen | SENSOR
```

Para terminar o JShell:

```text
/exit
```

## 7. Cenário conjunto: humidade e ventilador na cozinha

Abre dois terminais e coloca ambos na pasta que contém `src` e `bin`.

No primeiro terminal:

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 Kitchen
```

No segundo terminal:

```bash
java -cp bin iscot.client.Ventilator localhost 12345 Kitchen
```

**O que fazem:** iniciam dois programas independentes, ambos associados à cozinha. As janelas aparecem na mesma posição; arrasta uma delas para o lado para conseguires observar ambas.

### O que observar com o código atual

- A humidade escolhida aparece na consola do sensor.
- O ventilador liga-se ao fim de cinco segundos, mesmo sem mexeres no sensor.
- Baixar ou subir a humidade não altera o ventilador.

Isto é esperado neste momento: ainda não existe comunicação entre os dois programas. Usar a mesma área e os mesmos argumentos de rede não cria essa comunicação.

### O que deverás conseguir demonstrar ao desenvolver a fase 3

| Parte a desenvolver | Situação de teste | Resultado a observar |
| --- | --- | --- |
| Sensor | Escolher 80% na cozinha. | Criar um `HumiditySensorEvent` com a área e o valor corretos e imprimir os seus dados na consola. |
| Sensor | Escolher depois 20%. | Criar outro evento, mantendo a identificação do sensor e preservando os dados do primeiro evento. |
| Atuador | Criar e entregar localmente uma ordem de ventilação `ON` para a área do ventilador. | O ventilador interpreta a ordem e apresenta o estado ligado. |
| Atuador | Criar e entregar localmente uma ordem de ventilação `OFF` para a mesma área. | O ventilador interpreta a ordem e apresenta o estado desligado. |

Estes são objetivos para o código que vais desenvolver. Depois de o implementares, volta a compilar e usa os mesmos comandos de arranque dos clientes. A mudança no ventilador deverá resultar da interpretação da ordem de teste, em vez da chamada direta que o liga após cinco segundos.

Na fase 3, as ordens podem ser criadas no próprio programa do atuador para testar a sua representação. Ligar automaticamente o sensor ao decisor e ao ventilador através do servidor pertence às fases seguintes.

Este cenário cobre humidade e ventilação como primeiro passo. Para completar a fase 3, aplica a mesma ideia aos restantes sensores e atuadores; atualmente só existem pontos de entrada executáveis para `HumiditySensor` e `Ventilator`.

## 8. Problemas frequentes

| Mensagem ou sintoma | O que verificar |
| --- | --- |
| `Could not find or load main class` | Confirma que estás na pasta que contém `bin` e que a compilação terminou sem erros. |
| `UnsupportedClassVersionError` | O Java que executa o programa é mais antigo do que o usado para compilar. Confirma `java -version` e `javac -version`. |
| `Invalid arguments!` | Passa os três argumentos: endereço, porto e área. O código atual exige três, apesar de a comunicação ainda não estar implementada. |
| `No enum constant ...` | Escreve a área exatamente como aparece em `Areas`, respeitando maiúsculas e minúsculas. |
| O programa parece não refletir uma alteração ao código | Volta a compilar antes de o executar. |
| O terminal não aceita outro comando enquanto a janela está aberta | O programa está em execução. Fecha a janela, usa `Ctrl+C` ou abre outro terminal. |

## Registo dos resultados

- [ ] Compilação sem erros.
- [ ] Janela do sensor com valor inicial 50%.
- [ ] Leituras 80, 20, 0 e 100 apresentadas corretamente.
- [ ] Ventilador inicialmente desligado e ligado após cinco segundos.
- [ ] Argumentos em falta e área inválida com os comportamentos descritos.
- [ ] Dados e herança dos eventos confirmados no JShell.
- [ ] Independência atual entre as duas janelas confirmada.
- [ ] Depois de desenvolver: sensor cria e apresenta eventos.
- [ ] Depois de desenvolver: ventilador interpreta ordens locais `ON` e `OFF`.
- [ ] Depois de desenvolver: simulações equivalentes para os restantes sensores e atuadores.
