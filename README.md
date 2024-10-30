
# Projeto: Simulador de Escalonamento de Processos - FIFO e SJF

Este projeto é um simulador de escalonamento de processos usando os algoritmos **FIFO** (First In, First Out) e **SJF** (Shortest Job First). O código foi desenvolvido em **Java** e permite inserir processos com seus tempos de chegada e execução, exibindo os resultados com métricas de desempenho.

## Índice

-   [Descrição](#descri%C3%A7%C3%A3o)
-   [Pré-requisitos](#pr%C3%A9-requisitos)
-   [Como usar](#como-usar)
-   [Algoritmos Implementados](#algoritmos-implementados)
-   [Exemplos de Saída](#exemplos-de-sa%C3%ADda)
-   [Notas](#notas)
-   [Licença](#licen%C3%A7a)

## Descrição

O projeto foi criado para simular dois algoritmos de escalonamento de processos:

-   **FIFO (First In, First Out)**: Os processos são executados na ordem de chegada.
-   **SJF (Shortest Job First)**: Os processos com menor tempo de execução são priorizados, desde que já tenham chegado.

O simulador permite a entrada de uma lista de processos com os tempos de chegada e execução e, em seguida, calcula e exibe o gráfico de execução, além das métricas de **Tempo Médio de Espera (TME)** e **Tempo Médio de Processamento (TMP)**.

## Algoritmos Implementados

### FIFO (First In, First Out)

-   Executa processos na ordem de chegada.
-   O tempo de espera é determinado pela ordem de chegada dos processos.

### SJF (Shortest Job First)

-   Prioriza processos com menor tempo de execução, desde que tenham chegado.
-   Se não houver processos disponíveis para execução no momento, o tempo avança para o próximo processo.

## Exemplos de Saída

### Entrada:

-   **Número de processos**: 3
-   **Processo 1**: Chegada = 0, Execução = 5
-   **Processo 2**: Chegada = 2, Execução = 3
-   **Processo 3**: Chegada = 4, Execução = 2

### Saída:

------------------- FIFO -------------------
P1 ■■■■■ 5
P2  P2 ■■■ 8
P3     P3 ■■ 10
TMP (FIFO): 6.33	TME (FIFO): 3.33

------------------- SJF --------------------
P1 ■■■■■ 5
P3  P3 ■■ 7
P2     P2 ■■■ 10
TMP (SJF): 5.67	TME (SJF): 2.67` 

## Notas

-   O projeto foi desenvolvido como parte de exercícios acadêmicos para praticar a implementação de algoritmos de escalonamento.
-   As métricas calculadas ajudam a comparar a eficiência de cada algoritmo.

