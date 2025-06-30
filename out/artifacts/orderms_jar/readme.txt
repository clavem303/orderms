* Executar no terminal (para subir o docker com as configurações do arquivo docker-compose.yml):

> docker compose up
________________________________________________________________
* Executar no terminal (para rodar a aplicação):

> java -jar orderms.jar 
_______________________________________________________________
* Use o RabbitMQ, no navegador, para enviar a mensagem com o pedido para o servidor do MongoDB:

Endereço: http://localhost:15672/
User e Senha: guest e guest 
Aba: Queues and Streams
Campo Name: clicar em btg-pactual-order-created
Menu Publish message digite a mensagem no formato:

   {
       "codigoPedido": 1001,
       "codigoCliente":1,
       "itens": [
           {
               "produto": "lápis",
               "quantidade": 100,
               "preco": 1.10
           },
           {
               "produto": "caderno",
               "quantidade": 10,
               "preco": 1.00
           }
       ]
   }
   
   Publicar: Publish message
   
___________________________________________________________________
   * Abra a aplicação MongoDB Compass e confira se a API orderms.jar criou o banco "btgpactualdb" e persistiu o pedido no MongoDB.
   
___________________________________________________________________
   * Abra a aplicação API Bruno e acesse o URL de cliente para verificar a resposta do endpoint da API orderms.jar com o dados recuperados do MongoDB com suas os retornos exigidos.
   
   
