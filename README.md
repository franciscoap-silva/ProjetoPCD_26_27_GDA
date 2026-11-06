<<<<<<< HEAD
# ProjetoPCD_26_27_IscoT
=======
# IscoT – Message Broker para Domótica

**IscoT** é um sistema distribuído de gestão doméstica (smart home) baseado num *message broker* desenvolvido de raiz em Java. Sensores, decisores e atuadores ligam-se a um servidor central que gere toda a comunicação através de filas de eventos organizadas por tópicos.

Projeto desenvolvido no âmbito da unidade curricular **Programação Concorrente e Distribuída (PCD)** – 2026/27.

---

## Visão geral

Os *message brokers* (como MQTT, Kafka ou RabbitMQ) são muito usados na indústria para processamento paralelo e assíncrono, incluindo automação doméstica. Neste projeto implementamos um broker com protocolo próprio, que simula uma casa inteligente e serve para aplicar conceitos de **concorrência** e **distribuição**.

### Tipos de clientes

| Cliente | Publica | Consome |
|---|---|---|
| **Sensor** | eventos `Sensor` | – |
| **Decisor** | eventos `HighLevel` | eventos `Sensor` |
| **Atuador** | – | eventos `HighLevel` |

### Hierarquia de eventos

- **`SensorEvent`**: `GroundMoistureSensorEvent`, `HumiditySensorEvent`, `LightSensorEvent`, `TemperatureSensorEvent`
- **`HighLevelOrderEvent`**: `AreaIrrigationOrderEvent`, `AreaLightingOrderEvent`, `AreaTemperatureOrderEvent`, `AreaVentilationOrderEvent`
- `RegistrationEvent`: registo inicial dos clientes no servidor
- `ReceptionConfirmationMessage`: confirmação de execução enviada pelos atuadores (extra)

### Áreas da casa

`LIVINGROOM` · `BEDROOM` · `KITCHEN` · `WC` · `GARDEN`

---

## Como funciona

1. **Ligação**: cada cliente liga-se ao servidor indicando o seu tipo e a área onde opera. O servidor atribui-lhe um identificador único e recusa tipos inexistentes.
2. **Publicação**: sensores emitem leituras em intervalos arbitrários; o servidor publica-as na fila do tópico correspondente.
3. **Decisão**: os decisores consomem leituras e emitem ordens de alto nível (ligar luzes, ventilação, rega, climatização).
4. **Atuação**: os atuadores subscrevem o tópico e a área dos dispositivos que controlam, executam a ordem e registam-na no histórico (ex.: `Lâmpada desligada - HH:MM:SS`).
5. **Remoção e avanço**: um evento só sai da fila quando todos os clientes subscritos confirmarem o consumo, ou ao fim de um tempo de espera pré-definido. Só então o servidor avança para o evento seguinte.

### Exemplos de automações

- Pouca luminosidade numa divisão → o decisor ordena acender as luzes e aguarda que todos os atuadores de iluminação confirmem.
- Ar húmido → ventiladores ou ar condicionado em modo ventilação, apenas se não estiver já em aquecimento ou arrefecimento.
- Solo seco → ativação da rega do jardim.
- Temperatura acima/abaixo de um limiar → ar condicionado a aquecer ou arrefecer.

---

## Características técnicas

- **Arquitetura cliente-servidor** com comunicação por canais de objetos (sockets).
- **Uma thread dedicada por cliente** no servidor, para gerir envio e receção de eventos.
- **Mecanismos de coordenação implementados de raiz**, sem recurso aos equivalentes da biblioteca padrão do Java.
- **`ModifiedCountdownLatch`** (implementação própria) para controlar quais os clientes que já consumiram cada evento:

  ```java
  public class ModifiedCountdownLatch {
      public ModifiedCountdownLatch(int count);
      public synchronized void countdown();
      public synchronized void await(int waitPeriod) throws InterruptedException;
  }
  ```
- **Interface gráfica (GUI)** com componentes que simulam sensores (luz, temperatura, humidade, humidade do solo) e atuadores (luzes, ar condicionado, ventilação, rega), e histórico de ações por atuador.
- **Confirmações de execução** (extra): o servidor aguarda as confirmações `OK`/`NOK` dos atuadores antes de registar o sucesso da interação.

---

## Execução

O cliente recebe o endereço e porto do servidor, a área e o tipo de dispositivo como argumentos:

```bash
java iscot <IP> <PORT> <AreaDispositivo> <TipoDispositivo>
```

> Ajustar este comando ao nome real do pacote/classe principal e acrescentar aqui as instruções de compilação e de arranque do servidor.

---

## Fases de desenvolvimento

1. GUI e estrutura de tópicos e áreas (`SENSOR`, `DECIDER`, `ACTUATOR`)
2. Hierarquia de eventos
3. Representação de eventos na GUI
4. Servidor e ligação inicial dos clientes
5. Troca de eventos entre clientes e servidor
6. Troca simples de eventos (1 sensor, 1 decisor, 1 atuador)
7. Gestão do consumo de eventos com `ModifiedCountdownLatch`
8. *Extra*: consumo confirmado de eventos de alto nível

---

## Autores

- _Nome 1 – n.º_
- _Nome 2 – n.º_

Grupo _X_ · Programação Concorrente e Distribuída · 2026/27
>>>>>>> c66a1d470e3828a00ea7298b24e1cf0346012657
