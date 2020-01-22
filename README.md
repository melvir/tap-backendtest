# TAP-Guide to Backend Test

## Objective
* Make testing easy for developers to improve **code quality**
* Adopt testing to systems which are deemed enterprise grade, not POCs

## Test Layers
The example applicationn shows different test layers according to the [Test Pyramid](https://martinfowler.com/bliki/TestPyramid.html).

```
      ╱╲
  End-to-End
    ╱────╲
   ╱ Inte-╲
  ╱ gration╲
 ╱──────────╲
╱   Unit     ╲
──────────────
```

## Application Architecture
```
 ╭┄┄┄┄┄┄┄╮      ┌──────────┐      ┌──────────┐
 ┆   ☁   ┆  ←→  │    ☕     │  ←→  │    💾     │
 ┆  Web  ┆ HTTP │  Spring  │      │ Database │
 ╰┄┄┄┄┄┄┄╯      │  Service │      └──────────┘
                └──────────┘
                     ↑ JSON/HTTP
                     ↓
                ┌──────────┐
                │    ☁     │
                │ Weather  │
                │   API    │
                └──────────┘
```

## Concepts
* Isolation : test only 1 layer each time and mock everything else
* 

## Benefits
* Improve code quality
* Increase comfort when doing hand take over
* Decrease the amount of time to create new feature on top of existing system 
* Lesser help desk call


## What to test & why test / Minimal Code coverage
* API
  * Test other APIs that your app is consuming and asser the result to be the same
  * Test JSON response

* Controllers
  * Do integration testing from end to end up till persisting data into database. 
  * Do not only do unit testing of controller.
  
* Services
  * mock the repository methods
  * test business logic
  
* Repostories
  * only applicable for custom query methods (e.g. jpa /jpl)
 
* Model
  * Do not test

## Do's and Don't's
* Do not test the parameter 


## Mocking
* Create your own java mock classes 
   * Easy readabiliy
   * Low learning curve
* Using Mockito
   * Power mockito

## Techniques
* Use rest-assured to test specific JSON response by specifying the path.

## List of guides
* Web Layer test : https://spring.io/guides/gs/testing-web/
