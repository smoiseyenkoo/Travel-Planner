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