This parent pom includes plugins I've found useful and repository
information. The DependencyManagement section defines known good
versions of libraries. 

# How to create archetypes

To make a archetype, first create a maven project that uses this as
the parent. Then, use this command to convert the project into a archetype:

    mvn archetype:create-from-project 


