# Testing External Services
sources: 
1. https://www.simform.com/microservice-testing-strategies/#contract
2. https://reflectoring.io/consumer-driven-contract-provider-spring-cloud-contract/
3. https://aboullaite.me/a-practical-introduction-to-spring-cloud-contract/
4. https://piotrminkowski.com/2018/07/04/continuous-integration-with-jenkins-artifactory-and-spring-cloud-contract/


## Introduction
We adopt various types of testing strategies to test our backend services:  
1. Unit Tests - Isolate each unit, and make sure it does what it is supposed to to  
2. Component Tests - To Test flow of events in a use case
3. Integration Tests - To test the linkages between the different layers and ensure they connect well between the layers

This would be adequate for a monolith backend. For microservice architecture, consider the following:

![Microservices Architecture](https://www.simform.com/wp-content/uploads/2019/09/image-2.png)  

Service A is talking to Service B, also involves service B talking to Redis and service C.
There are interactions between the different microservices. How do we test the interactions between them??  
How do we ensure both services are talking to each other properly??

## Some Terms First....
In backend services, where API calls are the channel of communication between different micorservices,
the one _providing_ the API is termed as the **Producer**, while the one _using_ the API is termed as the **Consumer**  

![Producer-Consumer](https://www.bbva.com/wp-content/uploads/en/2017/06/ConsumerDriven.png)  

## Testing Strategies

When two or more services interact, they form a contract of what will be **received** and what would be **returned** in each API call.

These are some of the few strategies for testing the fulfillment of a contract (From a consumer point of view)
1. Calling the actual Producer API and assert actual response in Consumer
2. Record the actual response,  use it as a mock response when tests are run
3. Using Consumer Driven Contracts

### Consumer calling Actual Producer
 Pros: 
 - Actual representation of the data response
 - Actual representation of the connection and speed of call   
 
 Cons:
 - Tests are slow
 - Tests dirties the Producer's data
 - Tests are potentially non-repeatable due to dirty data
 
 ### Record and Replay response
 _Please look at test package external/beer/withoutspringcloudcontract/ for examples on this approach_
 
 Pros:
 - Tests are easy to set up for beginner practitioners
 - Tests are fast with mock servers/ mock return (using mock file)
 
 Cons:
 - No feedback if Producer are going to change the contract
 - No feedback = **pass** in tests + **fail** in actual environment
 
 ### Consumer Driven Contracts (CDC)
 By using CDC, we can circumvent the issue of the Consumer not having feedback when the Producer's API changes  
 Both Producer and Consumer would have to agree on a contract and **share** it across services

 _Please look at test package external/beer/withspringcloudcontract/ for examples on this approach_

 Pros:
  - Feedback when contract changes and tests fails
  - Compatible with Continuous Testing in pipeline (to be explored):
  https://piotrminkowski.com/2018/07/04/continuous-integration-with-jenkins-artifactory-and-spring-cloud-contract/
  
  Cons:
  - Slower Tests
  - Many Dependencies and a lot more learning and setup to do (which may not be suitable for beginners)

