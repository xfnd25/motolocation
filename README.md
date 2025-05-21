# Integrantes: RM555317 - Fernando Fontes | RM 556814 - Guilherme Jardim 

# 🏍️ Motolocation API

API REST desenvolvida em Java com Spring Boot para rastreamento de motos em pátios da Mottu utilizando tecnologia IoT. O sistema simula a leitura de tags RFID por sensores fixos, permitindo o controle da localização das motos em tempo real.

---

## 📌 Objetivo

Rastrear a posição de motos em um pátio por meio de sensores fixos que detectam **RFIDs**. A API recebe esses dados e registra as **movimentações**, associando motos a sensores com **data/hora**.

---

## 🧱 Visão Geral das Entidades

### 🏍️ `Moto`
Representa uma moto da frota da Mottu.

| Campo       | Descrição                    |
|-------------|------------------------------|
| `id`        | Identificador único          |
| `placa`     | Placa da moto                |
| `modelo`    | Modelo da moto               |
| `ano`       | Ano de fabricação            |
| `rfidTag`   | Código RFID (gerado pela API)|
| `status`    | Estado (ex: OK, Avariada)    |
| `observacoes` | Observações gerais         |

---

### 📍 `Sensor`
Representa a posição física no pátio.

| Campo        | Descrição                 |
|--------------|---------------------------|
| `id`         | Identificador único       |
| `codigo`     | Código do sensor (ex: S01)|
| `posicaoX`   | Coordenada X              |
| `posicaoY`   | Coordenada Y              |
| `descricao`  | Descrição da posição      |

---

### 📅 `Movimentacao`
Cada vez que um sensor detecta uma moto via RFID.

| Campo       | Descrição                  |
|-------------|----------------------------|
| `id`        | Identificador              |
| `moto`      | Referência à moto          |
| `sensor`    | Sensor que detectou a moto |
| `dataHora`  | Data e hora da leitura     |

---

## 🔁 Fluxo IoT

1. A Mottu cadastra uma moto via API.
2. A API gera automaticamente um `rfidTag` para a moto.
3. A moto é equipada com essa tag RFID.
4. Sensores fixos (simulados) detectam a presença da moto e fazem um `POST /movimentacoes`.
5. A API armazena as informações: **qual moto**, **qual sensor**, **quando**.

---

## 🚀 Funcionalidades

- ✅ Cadastro e consulta de motos
- ✅ Cadastro e consulta de sensores
- ✅ Registro de movimentações por sensor
- ✅ Histórico completo da movimentação de uma moto
- ✅ Busca por parâmetros, ordenação e paginação
- ✅ Validação de campos e tratamento de exceções
- ✅ Cache para requisições frequentes

---

## 🧪 Testes (via Postman ou Python)

### ▶️ Criar uma moto
POST /motos
{
"placa": "ABC1234",
"modelo": "Honda CG 160",
"ano": 2022,
"status": "OK",
"observacoes": "Nova moto"
}

### ▶️ Criar um sensor

POST /sensores
{
"codigo": "SENSOR01",
"posicaoX": 10,
"posicaoY": 20,
"descricao": "Parede leste"
}

### ▶️ Registrar movimentação (simulando leitura do sensor)

POST /movimentacoes
{
"motoId": 1,
"sensorId": 1
}

### ▶️ Buscar últimas movimentações de uma moto

GET /movimentacoes/motos/{motoId}
