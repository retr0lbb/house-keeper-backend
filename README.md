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
MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDC5S6ea74tgQK0
0oVoWe5AT5V3I/pVEXprP4w3vwL6XrsLJ/p1Qw+DC7UagTSscsEHWVvBfeQDgb35
IiCGO/Hnn9wkVetAFuEoe428XhjJf9OqzbVWt7YjlywELa7B0oAZb1mjLVdKKncL
uL0QI1tyWR9lNRWiXeq5PjVxx/yiI3lJfcRRZkFdTSSZ/MhFkvRGDO6YUb+Dm4aj
vZNBGrSH5tF8PZ0MdvVVFRbbDEA0E3tUqH4MbU6N8DREZK2dmXNi0UFwEGKCJ9N3
UPmMkhX8fTQoI2f/NWU3f59qC1L8Y6r+VkbevMcfWR892KnOnzNgkCxTuBfJ7P7K
JJ4lXfzZAgMBAAECggEAWnA2O15c8EmzA0zDimJc7Cvh/FjOI+aJ67Wf8AhrrdoC
qtEg6IQ3cQSgRA3b7wALMclWCkpMYhI1ISQkepXbkbO9WIEkOufDEOGQqm3xy6NN
CjAN65ExbH+Mdd3q/9caCXOB4XY7UNe2yVIvLYtytnPD3VeOU/uH3fEPpN58DZJe
bZcVaj0YtlTSWxT4c49UdDGXFMU2te9YNWaQiobwziEheEx7fzpw0LdE1emnzjoO
9JoWbowscm/epQhlVjjioH2ECJ7fWnlJMyQcBX6wkJV4m189I/qCWiuXQfUJLbNL
mmxNaMYGiC+asU+Lr+MLZZe6Ae50PAPChk3g2dvwVwKBgQD+z2cPTf319qLosVC+
2HizY+6PrWTxMSmvdSB7l3ujvRP2gWrA3PV8TaxKIngwvt/ygzo5mU+FIY+I5O6C
ncHwmTvKEul30oHEA+te0gm3GSgbRc41887quM2Mnxpckj8kL71kRWuwyMrru2MV
CaXd1fuHsvL8P/nIOoVAX7NdzwKBgQDDziho6b7UpHGEXdIcTZEdFx3+bdVAvrCJ
HwJCRNNxZ0/3Eh/ufh7pazX+Q0m+3+Lk2Ws+vcCjkThPLD0P71hcOeo2hAOOxdDi
giWHyrF3WAVF7fAo8FPCMmEzCGOkagd9pYO8ll3oyHTdTHk3uRx58ox22sG7CWR1
WFwrWwOM1wKBgFvlyTGH21PeA5sQeCKcR8vK+rruuj0Skgopn/FzDLBc/Nomiq3M
Au01xjFmhRpvfK0mDtW/KSLGm9GvXyXUZ6GyYhHbCECitxJOp5CnB8H1XLWZU2m5
6KJWzCsvJn7Eb+lJHnEPaGt1v1mNV4q61AtRpUmnjGLVX+yKzowM98+ZAoGANEgo
vBKltKuP7/MJNitz8tuIU7ZJ+baw9nwEI2OzPZWDYYpCYnDkmhjZWuHmoAOx1r9D
BoauGD6fVWyPhWC4LC/+nL3VIVsqckCj+sUha6diCREGTjgVdUX0dCbqTC1PgLog
GcvgmVZk9e4Ec9+Gff1IijfRALr6Subj/WOY6BMCgYBfkqVeAuGabWuLGEC+Z6qe
CzR7D9syBgn2vH6rxev0RGeZ4A7Ym/8FN7dm7hh5vKwZBGBhDy4veWB70G599Bpa
qxCiI+tTCRzTx8AWbcGF7nqbZ3Gc/e9q0KhskORxBj4gkDZ37oAhvGdnRpViqw0L
OULpCJMfs61eoAfgyvOOnw==
-----END PRIVATE KEY-----
```

### app.pub
```
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwuUunmu+LYECtNKFaFnu
QE+VdyP6VRF6az+MN78C+l67Cyf6dUMPgwu1GoE0rHLBB1lbwX3kA4G9+SIghjvx
55/cJFXrQBbhKHuNvF4YyX/Tqs21Vre2I5csBC2uwdKAGW9Zoy1XSip3C7i9ECNb
clkfZTUVol3quT41ccf8oiN5SX3EUWZBXU0kmfzIRZL0RgzumFG/g5uGo72TQRq0
h+bRfD2dDHb1VRUW2wxANBN7VKh+DG1OjfA0RGStnZlzYtFBcBBigifTd1D5jJIV
/H00KCNn/zVlN3+fagtS/GOq/lZG3rzHH1kfPdipzp8zYJAsU7gXyez+yiSeJV38
2QIDAQAB
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




