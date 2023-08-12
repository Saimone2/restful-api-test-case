# restful-api-test-case

(Allowed to be posted)

## Test task condition:
Create REST API (JSON HTTP API).
What to use at work:
- Development tools: Java 17
- Framework: Spring boot 2.7.14 (or higher)
- Database: MySQL
- Protocol: HTTP, port 80

## Create functionality (requests):
1. Adding a new user:
- We transfer the user's personal data to the server (URI of the picture, username, email, etc.);
- We save information in the database;
- Server response - unique ID of the new user;

2. Getting information about the user:
- We send a unique user ID to the server;
- Reading information from the database;
- Server response - user's personal data (see above);

3. Changing user status (Online, Offline):
- We send a unique user ID and a new status (Online, Offline) to the server;
- Change the status of the user;
- Server response - unique user ID, new and previous status;

Note: The server is making a request to an external API/database. Since this is a simplified test task, it is necessary to implement a "stub" with an imitation of access and a time delay of 5-10 seconds.

## Additional mandatory requirements:
- RESTful
- All data in JSON format
- Error processing

## Additional optional requirements (desirable):
- Code documentation
- Creation of tests

## When the work is done:
The result of the test task should be provided in an archive with detailed instructions for its deployment. Send me the result by replying to this email. At the top of the letter there is a line with a code, please answer above them. This will help us process all responses and test items faster.
