# Command-Line-Task-Management-System

This project aims to develop a command-line-based task management system (TMS) that will
enable its users to define, query, and modify the simple tasks they need to complete.
A task can be simple or composite. Simple tasks are the smallest unions of work to manage
using the system, while each composite task is a collection of other (simple or composite) tasks.
Each task has a unique name, a description, a duration, and a group of prerequisites. A
task name 1) may contain only English letters and digits, 2) cannot start with digits, and 3) may
contain at most eight characters. A description may contain English letters, digits, and the
hyphen letter (-). A duration is a positive real number, and it gives the minimum amount of
time in hours required to complete the task. The prerequisites is a comma-separated list of task
names. Each task name in the list should be defined already, and a task can only start when all
its prerequisite tasks have finished. An empty list is denoted using a single comma (i.e., “,”).
Note that the prerequisite relation between tasks is transitive. Consider three tasks t1, t2, and
t3 for example. If task t1 is a prerequisite for task t2, and task t2 is a prerequisite for task t3,
task t1 is also a prerequisite for task t3 since task t3 cannot start before task t1 is finished. In
this example, task t1 is a direct prerequisite for task t2 and an indirect prerequisite for task t3.
When creating or changing a simple task, you should only specify the direct prerequisites of the
task under consideration. The indirect prerequisites will be derived by the TMS automatically.
