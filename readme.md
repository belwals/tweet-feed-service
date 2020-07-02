#Tweet-feed-service

------------------------------------------------------------------------------------------------------------------------
SpringBoot based microservice to fetch tweets, based on search criteria.
using hbc API to get tweet stream, and sending to Elastic search database by using KAFKA pipeline.

##Pre-requisites:
1. Runtime properties needs to be updated with twitter API details:
    *  twitter.token
    *  twitter.secret
    *  twitter.consumerKey
    *  twitter.consumerSecret

2. Kafka broker running with a topic, and provide topic name on the properties file
    *  tweets.topic

3. Elastic server up and running  
   Publishing to elastic search commented out at the moment uncomment a line of code from class ProcessKafkaMessageToElasticSearch


##Uses:
Feed controller API has been exposed to start fetching tweets and stopping the connection
http://localhost:8080/tweet-feed-service/swagger-ui.html

------------------------------------------------------------------------------------------------------------------------
##V1.0.0
* Fixed search capabilities (Hard coded for time being).
* Singleton connection client, as it takes queries as a parameter to build connection object

------------------------------------------------------------------------------------------------------------------------
##V1.1.0
* Dynamic search capabilities ("/fetch-tweets") takes queries as request param
* Once it fetches tweets with new search criteria then app disconnects the previous connection if present already
* Outsourcing capabilities to create and close connection with twitter rest API.
------------------------------------------------------------------------------------------------------------------------