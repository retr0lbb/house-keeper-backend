# House Keeper Backend 🏠

---

### Henrique Barbosa Sampaio
##### Pedro Henrique Gabriel de Lima
##### Gabriel Henrique Vieira Patriota

---

## 📌 Como rodar o projeto

Pra rodar essa linda API na sua máquina, você vai precisar ter instalado:

- **JDK 17+**
- **Maven**
- **Docker**
- **OpenSSL** (pra gerar as chaves, se quiser usar as suas)

---

## 🧱 Passo 1 - Clonar o projeto

Clone o repositório:

```bash
git clone https://github.com/retr0lbb/house-keeper-backend.git
```


## 🐘 Passo 2 - Subir o banco de dados
O projeto usa um banco PostgreSQL que está configurado no docker-compose.yml. Pra subir ele, rode:
``` bash
docker-compose up -d
```


## 🔐 Passo 3 - Gerar (ou criar) as chaves para o JWT
o projeto requer chaves privadas e publicas que por motivos obvios nao podem ser colocadas no github para colocar elas no seu projeto acesse a pasta
``src/main/resorces``
e crie 2 arquivos, um chamado app.key e outro chamado app.pub para a geracao dessas chaves eh necessario ter o openssl instalado caso nao tenha podera usar as chaves de exemplo abaixo

### app.key
```
-----BEGIN PRIVATE KEY-----
MIICXgIBAAKBgQC8ifwuzX9pAt0Yup/nPbIeNJn1Nddwxgi/ailzJqrce+9Daa0q
j3gLIXsNbqw04YaRZz/z1utrz291d8KRAZniNir495+EJrcUYb9lj2Gzn5HzdoMq
OQs93SY4bNk+9KLgRmJoM6x2dl8L+5QoRhhzuORIadf43stDocVu0PDT3wIDAQAB
AoGBAI+QwucgtBRSiel6sqhy4l79zv5ZsWO+aIrZXBbDKvgZp2e94I64EMDpDbp8
u8TItp+h76DN1xaRLl5Ug2PHxFSdggKM2wV16KHKzo97XrfQ1wBAZLe9dQ2T0+vV
/TlMKPdM2HRtKawwwFjoC1Bk3GneAYd/qBTDJKA5yBUFw/yBAkEA6l4E97OYHorB
P0YL9PsZ1Us8/YVkh8lJdoJuSkRHfxNkUDSvDzq1dDQv16JaRiPGmOWg6oJfKssD
f24bj0YR2wJBAM3xESSfae8mNiTFOBCFCQHuT0Nef/9Ucpc42B6Yf+f3DGNs1Zgu
+hS8FnRb40HMYfh0+1/mm9yUyy6HLr42700CQQCAw81I5oRya1vHTs3s+DT2SlMl
Gy/jpsTU9s5vwVniyJ25r2hrHp9pS+7pM4sTtD497RPfJGfzE/1E+xLrQd03AkEA
sycnNqjHSxf+ufYnU5enwlpB8eVfjAs2tH7q45LekjjEOioPlhgnzaniDs9w32ih
QbbihwbiR/3Y010eOLMpcQJAPZvzILWJFaa4lmqOmyPWOtf0Z1L3zOmbRAvfZWgi
yvNTES/5znGdU9PtCmxbHGUODoTEDkIcpMc5Eoh6fJnxow==
-----END PRIVATE KEY-----
```

### app.pub
```
-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8ifwuzX9pAt0Yup/nPbIeNJn1
Nddwxgi/ailzJqrce+9Daa0qj3gLIXsNbqw04YaRZz/z1utrz291d8KRAZniNir4
95+EJrcUYb9lj2Gzn5HzdoMqOQs93SY4bNk+9KLgRmJoM6x2dl8L+5QoRhhzuORI
adf43stDocVu0PDT3wIDAQAB
-----END PUBLIC KEY-----
```

## 🚀 Passo 4 - Rodar a aplicação
Rode o arquivo HouseKeeperApplication dentro de src/main/java/com.retr0lbb/housekeeper
e o programa deve funcionar

OU

rode pelo maven usando o comando

```
mvn spring-boot:run
```




