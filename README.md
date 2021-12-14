# PomoToDo

**PomoToDo** is a *to-do list application* with an integrated *Pomodoro Technique Timer*. The Pomodoro Timer is a time
management technique that is popular amongst students because it helps one to better focus on a task and promotes
productivity. The application will have the basic functionality of a to-do list, including adding task items to the list,
deleting task items from the list and viewing the list as a whole. It will also have a Pomodoro Timer that can be set
for 25 minutes of working or studying, 5 minutes of a short break and 10 minutes of a long break.

I have always found keeping a to-do list can help me to better manage my time
and plan for my schedule. And the fact that the Pomodoro Technique is something that I use every day to keep myself
focused on my work brings me the idea of designing an application that combines a to-do list and a Pomodoro Timer.

###

#### User Stories
As a user, I want to ...
- **view** all the tasks in the to-do list and the completed list
- **add** a task to the to-do list
- **complete** task from the to-do list
- **delete** a task from the to-do list or the completed list
- **set a timer** to 25 minutes, 10 minutes or 5 minutes
######
- **save** my to-do list and completed list to file
- **load** my to-do list and completed list from file

###
#### Phase 4: Task 2
EventLog Sample from PomoToDoUI:

Fri Nov 26 15:04:20 PST 2021
Loading saved PomoToDo...

Fri Nov 26 15:04:20 PST 2021
A task was set to completed

Fri Nov 26 15:04:20 PST 2021
A task was added to the PomoList

Fri Nov 26 15:04:20 PST 2021
A task was added to the PomoList

Fri Nov 26 15:04:20 PST 2021
A task was set to completed

Fri Nov 26 15:04:20 PST 2021
A task was added to the PomoList

Fri Nov 26 15:04:20 PST 2021
Loading finished...

Fri Nov 26 15:04:23 PST 2021
A task was added to the PomoList

Fri Nov 26 15:04:25 PST 2021
A task was set to completed

Fri Nov 26 15:04:26 PST 2021
Deleted a task from PomoList

Fri Nov 26 15:04:27 PST 2021
A incompletion filtering list was created...

Fri Nov 26 15:04:27 PST 2021
A task was added to the incompletion filtering list

Fri Nov 26 15:04:27 PST 2021
The PomoList was filtered by incompletion...

Fri Nov 26 15:04:27 PST 2021
A completion filtering list was created...

Fri Nov 26 15:04:27 PST 2021
A task was added to the completion filtering list

Fri Nov 26 15:04:27 PST 2021
A task was added to the completion filtering list

Fri Nov 26 15:04:27 PST 2021
The PomoList was filtered by completion...

Fri Nov 26 15:04:30 PST 2021
The PomoList was saved

Note: The design in PomoToDoApp functions differently compared to the design PoMoToDoUI so the Event get logged differently. 
One main difference is that in the PomoToDoApp, whenever the user views the tasks, the application will show all tasks in
the list based on the completion of the task. So, whenever a task gets added into the PomoToDoList, it will also go
through the incompletion filtering list in order for the application to display the to-do and completed lists in the PomoToDo.


###
#### Phase 4: Task 3
Possible refactoring for improvements:

- PomoToDoUI has a relationship with ToDoUI, which has relationship with TaskUI. All three UI classes have a relationship
with the ToDoList class. If capable, I would like to simplify the relationship between the UI classes and the ToDoList to
reduce coupling and improve the design (could potentially utilize Observable and Observer).
- There are duplicating codes in both the PomoToDo App and in the PomoToDoUI (eg. saving and loading methods). Coupling could be improved by abstracting 
duplicated codes in to methods.
- Make the UI action buttons more organized by possibly creating a Button class that consists all the action buttons.
- Make use of Timer and TimerTask, which are java util classes, to make the Timer class simplier, or possibly deleting
the current Timer class.