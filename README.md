# My Personal Project
## Travel Planner

The project that I want to design this term is a travel planner. My vision for 
this application is to have a Trip class, in which one can create an instance of
a new trip that they are planning, or are already on. The Trip class would contain
a list of destinations, which would include a *string* City and a *string* Country, a 
*method of travel* (train, plane, bus), and *integer* of 
travel costs, and a list of *activities* to do at that destination. The user would be able would 
be able to add a destination to a **list of destinations already visited**, 
to a **list of destinations planned to visit**,
or to a **wishlist of destinations they would like to travel to**. They would also be
able to add or remove a destination from their planned destinations or wishlist
destination, and once they set a destination as *visited*, that will remove
the destination from the *planned destinations list* and into the *already 
visited list*. The user would also be able to add or remove activities from the list of
activities to do at each destination, or just be able to access the list. A *track expenses*
method would also allow the user to total up all the expenses spent in their trip so far.

Anyone who wishes to travel with organization and a cool itinerary would 
want to use this application. Being able to track the places that one has
already been to, places they will go to, and projected travel costs, as well
as keeping track of money already spent allows for easier budgeting in 
one's endeavours, to maximize the time spent enjoying travel, and minimizing
the headache of trying to keep track of all travel factors.

This topic is of interest to me as I have never really traveled much, 
however I am planning to do a long multi-country trip by myself next year,
and having a planned itinerary and a way to track my budget seems very
useful.


### User Stories 
- As a user, I want to be able to create a Destination and add it to 
my list of Destinations in my trip.
- As a user, I want to be able to view the list of activities for each destination.
- As a user, I want to be able to sum up my total expenses in my trip so far.
- As a user, I want to be able to mark a Destination as "visited" and remove it
off my planned list and place it onto my already visited list.

- As a user, I want to be able to save my Trip to the file, if I want to.
- As a user, I want to be able to be able to load my Trip from file, if I want to.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by going to the 
Destinations dropdown menu in the top left corner of the screen, and clicking on Add New destination.
  - This will take you to a screen where you can fill in the information to add a new destination, and click the
  Create New Destination! button to create the destination. You can add as many destinations to the trip as wanted.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by going to the 
Main menu, and clicking on any of the view Visited, Planned, or Wishlist buttons, which will take you to a screen
where you are able to see each destination in the form of (city, county) that have the status of the selected button.
- You can also update the status of any destination by clicking on the Destinations dropdown menu and clicking
Change Status of a Destination, which will take you to a new screen that has the list of all destinations in the form
(city, ID), and by typing the ID of the destination you want to change, this will select the destination and bring you
to another screen with the destination in the form (city, status), and you can select the status that you wish to 
update this selected destination to, and then click the button Update Status to update it.
- You can locate my visual component by looking at the home screen and there is a picture of a pixel cherry
- You can save the state of my application by going to the Options dropdown menu and clicking Save and Quit
- You can reload the state of my application by clicking the Load button on the home screen 

# Phase 4: Task 2
- Thu Nov 30 14:35:56 PST 2023
- Adding new destination: Canberra, Australia
- Thu Nov 30 14:36:16 PST 2023
- New activity was added: Tanning
- Thu Nov 30 14:36:37 PST 2023
- Setting new status as: VISITED
- Thu Nov 30 14:36:59 PST 2023
- Adding new destination: Suva, Fiji
- Thu Nov 30 14:37:06 PST 2023
- New activity was added: Swimming
- Thu Nov 30 14:37:20 PST 2023
- Setting new status as: WISHLIST

# Phase 4: Task 3
If I had more time for this project, I would focus on refactoring the existing code to eliminate duplication,
specifically in the TravelPlannerApp UI class. I have some duplicated code with my 3 buttons in my GUI where the
user can click each button to see either their planned, visited, or wishlist destinations. I think to refactor this
I would want to make that into an abstract class, so I can do the same process of displaying each screen just with
that one specification of destination status. There is also duplication to set each screen with my selected settings
(colour, size, etc), so I would like to remove that duplication as well. This way, I would have a much cleaner and
compact program that's easier to add more features in one place, rather than multiple places.

Looking at my UML diagram, I can see class dependencies that i would prefer to minimize if I were to update this 
project, specifically reducing the dependencies between the Travel Planner app and the Destination class. Ideally,
I would aim for a design where the Travel Planner app primarily depends on the Trip class, and the Trip class,
in turn, relies on the Destination class, rather than TravelPlannerApp depending on both Trip *and* destination.
This hierarchical structure would enhance code organization, and create a more cohesive program.
