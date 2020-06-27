#Tweet-feed-service

SpringBoot based microservice to fetch tweets, based on search criteria.
using hbc API to get tweet stream, and sending to Elastic search database by using KAFKA pipeline.

#Pre-requisites:
1. Runtime properties needs to be updated with twitter API details:
*  twitter.token
*  twitter.secret
*  twitter.consumerKey
*  twitter.consumerSecret

2. Kafka broker running with a topic name 'tweet_topic'.
3. Elastic server up and running running 


#Uses:
Feed controller API has been exposed to start fetching tweets and stopping the connection
http://localhost:8080/tweet-feed-service/swagger-ui.html

#Improvement:
* Dynamic search capabilities (Hard coded for time being).
* Exposing search result to graphical interface of Kibana.
* Outsourcing capabilities to create and close connection with twitter rest API.