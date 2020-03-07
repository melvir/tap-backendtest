package contracts.employee

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

UUID_REGEX = "([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})";
DATE_TIME_REGEX = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) "
Contract.make {
    description "Get Employee"
    request {
        method GET()
        url ("/api/v1/employees/retrieve_employee/" + consumer(regex("([0-9]{5})")))
    }
    response {
        body(
                id: value(producer(regex("$UUID_REGEX"))),
                name: value(producer(regex("[a-z A-Z]+"))),
                department: value(producer(regex("[a-z A-Z]+"))),
                createdDateTime: value(producer(regex("$DATE_TIME_REGEX"))),
                modifiedDateTime: value(producer(regex("$DATE_TIME_REGEX"))),
        )
        headers {
            contentType(applicationJson())
        }
        status HttpStatus.OK.value()
    }
}