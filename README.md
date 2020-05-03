# account-manager

Build project:
mvn clean install

Run application after build
java -jar target/account-manager-0.0.1-SNAPSHOT.jar

DB location
~/data/account-manager

API to get account balance

GET /v1/accounts/{accountNumber}

Sample request
GET http://localhost:8080/v1/accounts/12345678

Sample response
Response status = 200
{
"balance":999901.00,
"currency":"HKD",
"lastUpdatedAt":"2020-05-03T07:46:40.517"
}





API to transfer money from one account to another

POST

Sample request
POST http://localhost:8080/v1/transactions
Request Body:
{
	"accountFrom":"12345678",
	"accountTo":"88888888",
	"amount":"99",
	"currency":"HKD"
}

Sample response
Response status : 201
bad8af57-2a7a-4318-971c-6d6bd7809c97