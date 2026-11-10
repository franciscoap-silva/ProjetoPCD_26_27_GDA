# Guia de testes locais — fase 3

Os quatro sensores geram eventos ao alterar os sliders. Os quatro atuadores permitem criar ordens locais através de botões, escolher a área destinatária e observar o resultado na interface e na consola.

Não é necessário um servidor. Os argumentos de endereço e porto mantêm-se para o arranque dos clientes, mas ainda não são utilizados para estabelecer uma ligação.

## 1. Preparação e compilação

Se estiveres na pasta que contém o PDF e a pasta do código, entra na pasta do código:

```bash
cd ProjetoPCD_26_27_GDA
```

Os restantes comandos devem ser executados nesta pasta, que contém `src`. Se já estiveres nela, não repitas o comando anterior.

Confirma o Java e o compilador:

```bash
java -version
javac -version
```

Compila todos os ficheiros Java:

```bash
javac -encoding UTF-8 -d bin \
  src/iscot/enums/*.java \
  src/iscot/events/*.java \
  src/iscot/client/gui/*.java \
  src/iscot/client/*.java
```

**O que faz:** lê o código em UTF-8 e guarda as classes compiladas em `bin`. O comando deve terminar sem erros. Repete-o sempre que alterares o código.

## 2. Significado do comando de arranque

Exemplo:

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 Kitchen
```

| Parte | Significado |
| --- | --- |
| `java` | Executa o programa. |
| `-cp bin` | Procura as classes compiladas em `bin`. |
| `iscot.client.HumiditySensor` | Classe cujo método `main` é executado. |
| `localhost` | Endereço de exemplo; a ligação ainda não está implementada. |
| `12345` | Porto de exemplo; a ligação ainda não está implementada. |
| `Kitchen` | Área do dispositivo. |

As áreas válidas são `Kitchen`, `WC`, `LivingRoom`, `BedRoom` e `Garden`, respeitando as maiúsculas.

Cada comando mantém o programa em execução enquanto a janela estiver aberta. Fecha a janela antes de usar o mesmo terminal para outro programa, abre outro terminal ou termina com `Ctrl+C`.

## 3. Testar os quatro sensores

Executa os comandos individualmente ou em terminais diferentes:

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 Kitchen
java -cp bin iscot.client.LightSensor localhost 12345 Kitchen
java -cp bin iscot.client.TemperatureSensor localhost 12345 Kitchen
java -cp bin iscot.client.GroundMoistureSensor localhost 12345 Garden
```

**O que fazem:** abrem as interfaces de humidade do ar, luminosidade, temperatura e humidade do solo.

| Sensor | Configuração atual | Leituras a experimentar |
| --- | --- | --- |
| Humidade do ar | 0 a 100%, inicialmente 50% | 80, 20, 0 e 100 |
| Luminosidade | 0 a 1000 lux, passo de 10, inicialmente 500 | 100, 900, 0 e 1000 |
| Temperatura | -50 a 50 °C, inicialmente 20 °C | -10, 25, -50 e 50 |
| Humidade do solo | 0 a 100%, inicialmente 50% | 20, 80, 0 e 100 |

Para cada sensor:

1. Confirma o título e a unidade apresentados.
2. Move o slider e verifica o valor na etiqueta.
3. Larga o slider num novo valor.
4. Confirma que a consola apresenta o valor a verde em `Mudou para:` e uma descrição do evento correspondente.
5. Repete com os extremos da escala.

**Resultado esperado:** o valor do evento corresponde ao valor escolhido, e o tipo do evento corresponde ao sensor. O identificador do evento muda a cada criação; a área e o identificador do sensor mantêm-se durante essa execução.

A separação decimal pode variar com as definições do computador. O código atual utiliza o identificador de sensor de exemplo `10` nos quatro clientes.

As etiquetas da escala mostram valores reais: na temperatura aparecem -50, -25, 0, 25 e 50; na luminosidade aparecem 0, 250, 500, 750 e 1000. A etiqueta acompanha o arrasto, mas o evento é criado quando terminares de arrastar.

## 4. Testar luz, rega e ventilador

Executa cada comando num terminal ou fecha a janela anterior antes de executar o seguinte:

```bash
java -cp bin iscot.client.Lights localhost 12345 Kitchen
java -cp bin iscot.client.Irrigation localhost 12345 Garden
java -cp bin iscot.client.Ventilator localhost 12345 WC
```

**O que fazem:** abrem os atuadores com o estado inicial desligado/inativo. Cada janela tem uma seleção de `Área da ordem` e botões `Ligar` e `Desligar`. A área da ordem começa igual à do atuador; alterá-la não muda a localização do dispositivo.

Para cada atuador:

1. Mantém a área da ordem igual à área indicada no título da janela.
2. Carrega em `Ligar`.
3. Confirma que o ícone passa ao estado ativo e que o texto de estado muda.
4. Carrega em `Desligar`.
5. Confirma que regressa ao estado inicial.
6. Repete várias vezes.

**Resultado esperado:**

| Atuador | Ao ligar | Ao desligar |
| --- | --- | --- |
| Luz | Lâmpada amarela; texto `Estado: Ligada` | Lâmpada cinzenta; texto `Estado: Desligada` |
| Rega | Gota azul; texto `Estado: Ativa` | Gota cinzenta; texto `Estado: Inativa` |
| Ventilador | Pás verdes; texto `Estado: Ligado` | Pás cinzentas; texto `Estado: Desligado` |

Cada clique cria uma instância da ordem correspondente, que é interpretada pelo atuador. A consola apresenta a área, o estado aplicado e o identificador do evento. Não há ligação automática por tempo nem comunicação com os sensores.

## 5. Testar ordens para outra área

Usa qualquer um dos três atuadores do teste anterior:

1. Liga o dispositivo enviando uma ordem para a sua área.
2. Em `Área da ordem`, escolhe outra área.
3. Carrega em `Desligar`.
4. Confirma que o dispositivo continua ligado e que o texto de estado não muda.
5. Confirma na consola a indicação de ordem ignorada.
6. Volta a selecionar a área correta e desliga o dispositivo.
7. Seleciona outra área e carrega em `Ligar`: o dispositivo deve continuar desligado.

**O que verifica:** uma ordem para outra divisão não altera o estado anterior, esteja o dispositivo ligado ou desligado.

## 6. Testar o ar condicionado

```bash
java -cp bin iscot.client.AC localhost 12345 BedRoom
```

**O que faz:** abre o ar condicionado do quarto, inicialmente no modo `Desligado`.

1. Mantém `Área da ordem` em `BedRoom`.
2. Seleciona `Aquecimento` e carrega em `Aplicar modo`.
3. Confirma o texto `Modo: Aquecimento` e o ícone ativo.
4. Repete para `Frio` e `Ventilação`, confirmando o texto de cada modo.
5. Aplica `Desligado` e confirma o texto e o ícone cinzento.
6. Volta a aplicar `Aquecimento`.
7. Muda a área da ordem para `Kitchen`, seleciona `Desligado` e aplica.
8. Confirma que o modo aplicado continua a ser `Aquecimento` e que a consola indica que a ordem foi ignorada.

**Resultado esperado:** cada clique cria um `AreaTemperatureOrderEvent`. O atuador aplica o seu modo apenas quando a área corresponde. O ícone distingue ativo/desligado; o texto de estado identifica qual dos modos ativos está aplicado.

Este teste demonstra a aplicação local de ordens de climatização. As decisões automáticas baseadas nos sensores e a comunicação entre clientes pertencem às fases seguintes.

## 7. Confirmar a independência entre sensor e atuador

Em dois terminais diferentes:

```bash
java -cp bin iscot.client.HumiditySensor localhost 12345 Kitchen
```

```bash
java -cp bin iscot.client.Ventilator localhost 12345 Kitchen
```

**O que fazem:** iniciam dois clientes independentes associados à cozinha.

**O que observar:** mudar a humidade cria eventos na consola do sensor, mas não altera o ventilador. Os botões do ventilador criam as suas ordens locais. Partilhar área e argumentos de rede não estabelece uma ligação entre os programas.

O contador de eventos também pertence a cada execução de Java: dois programas separados podem ambos começar no evento 1.

## 8. Experimentar eventos no JShell

```bash
jshell --class-path bin
```

**O que faz:** abre uma consola interativa de Java com acesso às classes compiladas. Introduz nela as instruções seguintes:

```java
import iscot.events.*;
import iscot.enums.*;
var leitura = new HumiditySensorEvent(10, Areas.Kitchen, 80.0);
System.out.println(leitura);
var segundaLeitura = new HumiditySensorEvent(10, Areas.Kitchen, 20.0);
System.out.println(segundaLeitura);
System.out.println(leitura.getValue());
var ordem = new AreaVentilationOrderEvent(Areas.Kitchen, OnOffMode.ON);
System.out.println(ordem.getId() + " | " + ordem.getArea() + " | " + ordem.getState());
```

**O que observar numa sessão nova:** as duas leituras recebem identificadores 1 e 2; a ordem recebe 3. O primeiro evento mantém o valor 80. Os identificadores são gerados automaticamente pelo construtor de `Event`.

Criar uma ordem no JShell não a entrega à janela de outro programa. Para testar a interpretação na interface, usa os botões dos atuadores.

Termina com:

```text
/exit
```

## Problemas frequentes

| Mensagem ou sintoma | O que verificar |
| --- | --- |
| `Could not find or load main class` | Confirma a pasta atual e volta a compilar. |
| `UnsupportedClassVersionError` | Confirma que o Java de execução suporta a versão usada na compilação. |
| `Invalid arguments!` | Passa endereço, porto e área. |
| `No enum constant ...` | Respeita os nomes e as maiúsculas das áreas. |
| Uma ordem não muda o atuador | Confirma se a área selecionada corresponde à área do título. |
| O programa não reflete uma alteração ao código | Compila novamente antes de executar. |
| O terminal não aceita outro comando | Fecha a janela, usa `Ctrl+C` ou abre outro terminal. |

## Registo dos resultados

- [ ] Compilação sem erros.
- [ ] Os quatro sensores geram eventos com os valores escolhidos.
- [ ] As escalas e as unidades das janelas estão corretas.
- [ ] Luz, rega e ventilador ligam e desligam através de ordens.
- [ ] As ordens para outra área mantêm o estado anterior.
- [ ] O ar condicionado aplica e apresenta os quatro modos.
- [ ] Uma ordem de climatização para outra área é ignorada.
- [ ] Sensor e atuador continuam independentes nesta demonstração local.
