# GSG-Youtube
Demo project for GSG 


Database Access : H2


API technologies : Application is running on Spring Boot with Java 8 and Tomcat for Application Server

 * Every Method is Documented

SECURITY :
* Application uses the JWT authorization system. Which can be found in "JwtRequestFilter". 

PROBLEMS : 

1) Biggest problem I encountered was JUnit Testing. Right now im fairly new to Testing environments and depending on my deadline, I could only write 1 simple test. 
in any other scenarios I would take some time to research the JUnit Testing and then write it for the Application


Used external APIs :
 * youtube-video-api-url -> https://www.googleapis.com/youtube/v3/videos?
 * youtube-comment-api-url -> https://www.googleapis.com/youtube/v3/commentThreads?

Solution for JobTriggerTime : 

Right now , its solved on Clients side via Angular Interval. But , I also have a @Schedule which updates Youtube for user (this is only to show how I would solve it in API )
