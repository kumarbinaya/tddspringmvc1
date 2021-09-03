Feature: Verifying CRUD operation in Employee Rest Api Testing

   Scenario: Add Employee record
      Given I Set POST employee service api endpoint
      When I Set request HEADER
      And Send a POST HTTP request
      Then I receive valid Response

   Scenario: Update Employee record
      Given I Set PUT employee service api endpoint
      When I Set Update request Body
      And Send a PUT HTTP request
      Then I receive valid HTTP Response code 200

   Scenario: Get Employee record
      Given I Set GET employee service api endpoint
      When I Set request HEADER
      And Send a GET HTTP request
      Then I receive valid HTTP Response

   Scenario: Delete Employee record
      Given I Set DELETE employee service api endpoint
      When I Send a DELETE HTTP request
      Then I receive valid Response code 200
