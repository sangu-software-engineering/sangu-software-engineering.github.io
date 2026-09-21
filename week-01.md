---
layout: lesson
title: Software engineering and programming foundations
description: Start with a real problem, refresh the programming tools you already know, and connect a small Java program to the decisions that make software useful and dependable.
permalink: /lessons/week-01/
week: 1
category_label: Foundations
reading_time: 25
sections:
  - { id: learning-goals, title: Learning goals }
  - { id: engineering-software, title: What we are engineering }
  - { id: lifecycle, title: The software lifecycle }
  - { id: constraints, title: Constraints and quality }
  - { id: java-toolkit, title: Your Java toolkit }
  - { id: programming-foundations, title: Programming foundations }
  - { id: readable-code, title: Readable code }
  - { id: walkthrough, title: A complete Java example }
  - { id: checking-behaviour, title: Checking behaviour }
  - { id: responsibility, title: Professional responsibility }
  - { id: check-understanding, title: Check your understanding }
  - { id: further-reading, title: Further reading }
---

## Learning goals
{: #learning-goals }

By the end of this chapter, you should be able to:

- Explain how programming contributes to the wider work of software engineering.
- Describe the main lifecycle activities and how a change can affect several of them.
- Discuss scope, cost, schedule, and quality using a concrete example.
- Compile and run a small Java program and explain its inputs, decisions, and outputs.
- Use functions, parameters, return values, arrays, conditionals, and loops to express a simple rule.
- Make code easier to understand through naming and clear responsibilities.
- Compare observed output with expected behaviour and take responsibility for your work.

**Starting point:** you have already written programs in a high-level language. This chapter refreshes those skills using Java. It introduces enough Java syntax to follow the examples; classes, interfaces, and object-oriented design receive dedicated treatment in week 6.

**Route through the chapter:** begin with a room-booking problem, examine the engineering decisions around it, then implement and inspect one small part. The running examples need a JDK and an editor. Downloading files is sufficient: Git starts in week 2. Build tools follow in week 3, UML and Mermaid in week 4, and automated testing begins in week 7.

## What are we engineering?
{: #engineering-software }

A university wants students to find a room for a study group. Someone suggests a simple program: enter the number of attendees and show rooms that are large enough.

The programming work looks straightforward. Store capacities, compare numbers, and display a result. But the first conversation already raises questions:

- Does a group of 24 fit in a room with exactly 24 seats?
- Does “suitable” mean only large enough, or also available at a requested time?
- Who may reserve a room, and who can cancel a reservation?
- What happens if two groups request the same room?
- How will students know whether a booking succeeded?

Some of these questions concern a calculation. Others concern users, system boundaries, reliability, or responsibility. A correct comparison alone does not answer them all.

**Software engineering** is the disciplined work of understanding, building, verifying, delivering, and evolving software under constraints. Programming gives those decisions executable form. Good engineering connects the code to the problem it is supposed to solve and to evidence that the solution works.

### A program and a useful system

Our first program answers a deliberately narrow question: **can this room seat this many people?** A room-booking system would also need room identities, a timetable, access rules, persistence, and ways to recover from failures.

That distinction matters when describing a result. We can honestly say that a room passes a capacity check. We cannot yet say it is available or reserved. Names, messages, and documentation should reflect what the software actually knows.

| Work | Example in the room-booking system |
| --- | --- |
| Understand the problem | Ask students and staff how rooms are currently requested. |
| Describe the behaviour | Decide whether an exact-capacity group is accepted. |
| Design a solution | Separate the seating rule from how results are displayed. |
| Implement | Write the function and the code that uses it. |
| Verify | Compare results for smaller, equal, and larger groups. |
| Deliver | Make the application and its run instructions available to users. |
| Maintain | Correct defects and adapt the rules when requirements change. |

The amount of process should fit the problem. A short script does not need the same documentation as a campus-wide service. Both still benefit from clear assumptions and a way to check the result.

## The software lifecycle
{: #lifecycle }

The lifecycle describes activities that take software from an idea through use and change. These activities provide a useful vocabulary; they do not imply that every project completes each one exactly once.

### Understand and specify

Start with the people affected and the outcome they need. “Make room booking better” gives direction, but it does not tell a programmer what a correct result looks like.

For our first example, we agree on these rules:

1. Capacity and attendee count are whole numbers.
2. Both must be greater than zero.
3. A group is suitable when its size is less than or equal to capacity.
4. The function checks seating capacity only.

These rules make the example precise. They are teaching assumptions, not university booking regulations. A different application might report invalid inputs separately rather than return the same negative answer for every rejected case.

### Design and implement

Choose how to divide the work into understandable parts. One function can answer the seating question. Another can count how many rooms satisfy that rule. The program's entry point can display results.

Then write the code. Keep the parts small enough that another person can relate them to the rule. This chapter uses ordinary methods and arrays; we do not need a framework or a hierarchy of classes to express a small calculation.

### Verify and deliver

Verification asks whether the implementation follows its stated rules. Validation asks whether those rules and the resulting system address the user's real need. Rejecting a group of exactly 24 from a 24-seat room violates our stated rule. Accepting the group into an already occupied room exposes a different problem: capacity alone does not solve booking.

Delivery includes everything needed to use the result. A Java source file with no explanation of how to run it may be correct but difficult for another student to use. Later chapters extend this idea to reproducible environments and automated release pipelines.

### Observe and change

After release, users discover needs that were missed, environments change, and defects appear. A maintenance change should preserve the behaviour that still matters while altering the part that no longer fits.

Suppose the university asks the system to reserve two seats for equipment. That request changes the seating rule. It also changes expected results, examples, and possibly messages. Revisiting earlier decisions is a normal part of engineering.

> A change is complete when the implementation and the relevant evidence and documentation agree about the new behaviour.

We will compare development processes in week 5. For now, recognise the lifecycle activities and the connections between them.

## Constraints and quality
{: #constraints }

Engineering decisions are made with limited time, people, and resources. A useful first version therefore needs an explicit boundary.

| Concern | Question | Example decision |
| --- | --- | --- |
| Scope | What behaviour is included? | Start with a capacity finder; defer reservations. |
| Schedule | When is it needed? | Agree on a small version that can be reviewed next week. |
| Cost | What effort and resources are available? | Use a simple local program while evaluating the rules. |
| Quality | What must be true of the result? | Handle exact capacity correctly and explain rejected inputs. |
| Change | Which assumptions may move? | Keep the capacity rule in one place so it can be revised. |

These concerns interact. Adding a calendar, login system, and notifications increases the work. A deadline does not make that extra work disappear. Possible responses include reducing scope, changing the schedule, or obtaining resources, while discussing the consequences openly.

### Quality has several dimensions

**Correctness** asks whether results follow the agreed rules. **Usability** asks whether people can understand and use the result. **Maintainability** concerns how readily developers can understand and change it. **Reliability** concerns continued correct operation under stated conditions. **Security and privacy** concern protecting access and information.

The priorities depend on context. For today's example, a clear seating rule and readable code matter more than supporting thousands of concurrent requests. For an actual booking service, preventing conflicting reservations would become essential.

Quality claims need a context and evidence. “The system is fast” is vague. A useful claim identifies the operation, the data and workload, the environment, and an observed response time. We will formalise these kinds of requirements later.

## Your Java toolkit
{: #java-toolkit }

The examples use **Java 21 or newer**, without preview features or external libraries. Install a full **JDK**, which provides development tools, rather than only a runtime. An IDE is convenient, but a text editor and terminal are enough.

- `javac` compiles a `.java` source file into `.class` bytecode.
- `java` starts the Java Virtual Machine, or JVM, to execute the compiled program.
- The source file is the editable program; the class file is a generated result.

See the official [Java getting-started guide](https://dev.java/learn/getting-started/) for setup guidance. Check that the two commands are available:

```shell
java --version
javac --version
```

We compile these small examples directly so the compiler's role is visible. In week 3, Maven will organise a project and automate compilation and packaging; we will compare Gradle and npm and briefly discuss Yarn. That setup comes before larger application development. No build-tool installation is needed for this chapter.

### Run a first program

Download [HelloCourse.java]({{ '/examples/week-01/HelloCourse.java' | relative_url }}) and save it in a working folder. This is the complete source:

{% highlight java %}
{% include_relative examples/week-01/HelloCourse.java %}
{% endhighlight %}

Open a terminal in the folder containing the file and run:

```shell
javac HelloCourse.java
java HelloCourse
```

Expected output:

```text
Welcome to Software Engineering
Lecture weeks: 13
```

`HelloCourse` is the container for this program's methods. A public top-level class and its source file must have matching names. `main` is the entry point used by this example; `System.out.println` writes one line to the console. The `+` operator combines text with another value in these messages.

For now, treat `public static void main(String[] args)` as the entry-point declaration. `void` means the method returns no value, and `args` contains command-line arguments, which this program does not use. `static` lets these example methods be called without creating an object. Object construction and access-control design come later.

### If the program does not run

| Symptom | What to check first |
| --- | --- |
| `javac` is not recognised | Install a JDK and ensure its tools are available in your terminal. |
| The source file cannot be found | Check the current folder and the filename, including its extension. |
| The compiler reports a syntax error | Read its line number and inspect the nearby braces, quotation marks, and semicolons. |
| Java cannot find the main class | Run from the folder containing the compiled file and use the class name without `.class`. |
| Old output still appears | Save the source and compile it again before running. |

These are basic run checks. We will learn a systematic debugging workflow in week 7.

## Programming foundations
{: #programming-foundations }

### Values and variables

Java gives each variable a type. `int` holds a whole number, `boolean` holds `true` or `false`, and `String` holds text. Use names that explain the role of a value:

```java
int capacity = 24;
int attendees = 18;
boolean suitable = attendees <= capacity;
```

The comparison produces a boolean value. Assignment with `=` stores a value; comparison with `==` checks equality for primitive values such as these integers. For the complete rule, we also need to reject invalid values.

### Functions, parameters, and return values

In Java, functions declared inside a class are called methods. A method gives a computation a name, accepts inputs through parameters, and may return a result:

```java
static boolean canSeat(int capacity, int attendees) {
    return capacity > 0 && attendees > 0 && attendees <= capacity;
}
```

The parameters are `capacity` and `attendees`. In `canSeat(24, 18)`, the arguments are the actual values 24 and 18. The result is a `boolean`. `&&` means all the conditions must hold; Java evaluates from left to right and stops when a condition is false.

The method describes one decision and produces no console output. A caller can use the answer in a message, a count, or a later interface. Keeping calculation separate from presentation makes the rule easier to reuse and explain.

### Conditionals

An `if` statement chooses whether to execute a block. An `else` provides an alternative:

```java
if (canSeat(24, 18)) {
    System.out.println("The group fits.");
} else {
    System.out.println("The room is too small or the input is invalid.");
}
```

Notice the second message. Our boolean result does not distinguish invalid input from insufficient capacity. The message avoids claiming more information than the function provides. A later design could return a more detailed result.

### Collections and loops

A collection groups related values. This introductory example uses an array: a fixed-length sequence of values of the same type.

```java
int[] capacities = {12, 24, 40};

for (int capacity : capacities) {
    System.out.println(capacity);
}
```

The enhanced `for` loop visits each value in order. Its loop variable refers to the current integer value. Changing that variable would not change the corresponding array element.

To count suitable rooms, begin at zero, visit each capacity, and increase the count when the rule returns true. An empty array has no elements, so the loop runs zero times and the count remains zero. We assume the array itself exists; handling a missing (`null`) array is outside this first example.

## Readable code and clear responsibilities
{: #readable-code }

Code is read during review, debugging, and change. The reader should be able to connect its names and structure to the problem.

Compare the expression `a <= b` with `attendees <= capacity`. Both may compute the same result, but the second makes the intended relationship visible. A misleading name can be worse than a short one: `isAvailable` would be misleading for a function that checks only seats.

### Keep one rule in one place

If the seating comparison appears in both the display loop and the counting function, one copy might later be changed without the other. Calling `canSeat` from both places gives that rule one clear home.

This is a useful application of avoiding duplicated knowledge. It does not mean every repeated line needs a new abstraction. Two pieces of code that look alike may represent different rules and change for different reasons.

### Separate calculation from presentation

Our complete example has three small responsibilities:

- `canSeat` decides whether one room meets the rule.
- `countSuitableRooms` counts rooms using that decision.
- `main` supplies example data and displays results.

The functions communicate through parameters and return values. A function named `countSuitableRooms` should not also save files or send notifications. Hidden extra work makes the caller's expectations harder to satisfy.

### Use comments to explain what code cannot

A comment can explain an assumption or a reason: “This example checks capacity only.” A comment that simply repeats `suitableCount++` as “increase the count” adds little. Keep comments accurate when behaviour changes.

Formatting also carries structure. Indent nested blocks consistently, use braces, and keep names consistent. A formatter can later automate much of that work; understanding the structure remains the programmer's responsibility.

## A complete Java example
{: #walkthrough }

Download [RoomCapacityDemo.java]({{ '/examples/week-01/RoomCapacityDemo.java' | relative_url }}). The example uses three room capacities and a group of 24 people.

{% highlight java %}
{% include_relative examples/week-01/RoomCapacityDemo.java %}
{% endhighlight %}

Compile and run from the directory containing the downloaded file:

```shell
javac RoomCapacityDemo.java
java RoomCapacityDemo
```

Expected output:

```text
Capacity 12: too small or invalid
Capacity 24: suitable
Capacity 40: suitable
Suitable rooms: 2
Zero attendees accepted: false
```

### Trace the result

For capacity 12, the positive-input conditions hold, but `24 <= 12` is false. For capacity 24, `24 <= 24` is true, so the exact-capacity room is accepted. Capacity 40 also passes. The counting function starts with zero and increments twice, returning 2.

The final line is a separate call with zero attendees. That value violates our stated rule, so `canSeat` returns false. Rejecting zero is an explicit decision for this example; it should not be inferred merely from what a room usually contains.

The program deliberately stores no reservations. Two rooms with the same capacity would count as two entries in the supplied array, but the program has no room identifiers. Persistent records, booking times, permissions, and concurrent requests would require additional requirements and design.

## Checking behaviour before claiming success
{: #checking-behaviour }

A compiler checks whether a program follows language rules. It cannot tell whether our seating policy matches the intended policy. For example, this expression compiles:

```java
return capacity > 0 && attendees > 0 && attendees < capacity;
```

It rejects an exact-capacity group. That is a logic error relative to our agreed rule. The corrected expression uses `<=`.

Choose expected results from the rule before looking at the program's output. Otherwise it is easy to explain away an incorrect result as intended behaviour.

| Capacity | Attendees | Expected result | Reason |
| --- | --- | --- | --- |
| 24 | 18 | `true` | The group fits with seats left over. |
| 24 | 24 | `true` | Exact capacity is allowed. |
| 24 | 25 | `false` | The group exceeds capacity. |
| 24 | 0 | `false` | Attendee count must be positive. |
| 0 | 1 | `false` | Capacity must be positive. |
| 24 | -1 | `false` | Negative attendee counts are invalid. |

Use these cases to reason through the function or change the example inputs, recompile, and compare the displayed results. This is manual checking, not a complete test strategy. Automated tests, boundary-value techniques, and regression protection are introduced in week 7.

### A change request

Now consider “reserve two seats for equipment.” A group of 24 would no longer fit in the 24-seat room. Before editing code, clarify whether the reserve applies to every room and whether a two-seat room can seat any attendees at all. Then revise the rule and its expected results together.

The important lesson is the chain of reasoning: understand the change, identify the affected rule, modify the implementation, and check the consequences. Increasing complexity before clarifying the request does not resolve the ambiguity.

## Professional responsibility
{: #responsibility }

Software decisions affect the people using a system. Even a student example should distinguish demonstrated behaviour from assumptions and unfinished work.

### Be clear about limitations

Describe this program as a capacity checker. Saying “the room is booked” would mislead a user because no reservation has been made. Report defects and limitations clearly, including the input and the observed result, so another person can reproduce the issue.

### Use only the information you need

The capacity calculation needs seat counts and group size. It does not need names, student identifiers, or personal contact details. Use synthetic example data while learning. If a later feature requires personal information, identify the need and access rules before collecting it.

### Make the result understandable

Use plain messages and do not rely on colour alone to convey success or failure. A technically correct result is less useful when its meaning is unclear. Accessibility and usability should influence the first examples, even though the course explores them in more detail later.

### Use assistance with accountability

You may consult documentation, discuss concepts, or use AI assistance when course rules permit it. You remain responsible for explaining and verifying what you submit. An AI-generated implementation using `<` instead of `<=` is still wrong for our seating rule, even if its explanation sounds confident.

Record substantial assistance and credit reused work as required. Do not upload private information, credentials, or someone else's unpublished work to an external tool. Never claim you ran a check that you did not run. When uncertain, identify the uncertainty and the evidence needed to resolve it.

## Check your understanding
{: #check-understanding }

Try answering before opening the discussion notes.

<details markdown="1">
<summary>Why does a correct capacity calculation not make a complete booking system?</summary>

It answers only whether a group fits. It does not check time conflicts, permissions, room identity, persistence, or whether a reservation succeeded. Those require additional requirements and implementation.

</details>

<details markdown="1">
<summary>Why is the exact-capacity case useful when checking the program?</summary>

It distinguishes the intended `<=` rule from the plausible but incorrect `<` implementation. A smaller group would pass under both expressions and would not expose that error.

</details>

<details markdown="1">
<summary>Why does canSeat return a boolean instead of printing a message?</summary>

Its responsibility is to answer a question. Returning the result allows callers to count rooms, display a message, or use another interface without duplicating the rule. Presentation remains the caller's responsibility.

</details>

<details markdown="1">
<summary>What does countSuitableRooms return for an empty array?</summary>

Zero. The counter starts at zero and the loop has no elements to visit. This is different from a missing array, which the example does not handle.

</details>

<details markdown="1">
<summary>What should happen when the deadline stays fixed but more features are requested?</summary>

Discuss the effects on effort, risk, and quality. Agree on priorities and a realistic scope, or change the available resources or schedule. Silently accepting extra work does not make the original plan feasible.

</details>

<details markdown="1">
<summary>How would you verify an AI-generated version of this program?</summary>

Read and explain its logic, compare it with the agreed rule, compile and run it, and compare results with independently chosen cases. A confident explanation or compilation success alone does not establish correctness. Record substantial assistance according to course rules.

</details>

## Further reading
{: #further-reading }

- Pankaj Jalote, [A Concise Introduction to Software Engineering: With Open Source and GenAI](https://link.springer.com/book/10.1007/978-3-031-74318-4), second edition, 2025 — introductory material on software engineering and its context.
- [Getting started with Java](https://dev.java/learn/getting-started/) — the official guide to the tools and a first program.
- [Java language basics](https://dev.java/learn/language-basics/) — variables, arrays, operators, expressions, and control flow.
- [ACM Code of Ethics](https://www.acm.org/code-of-ethics) — professional responsibility, honesty, privacy, and quality of work.

Next week introduces **Git and collaborative development**: saving meaningful changes, understanding history, working with branches, and reviewing contributions. Bring the ability to run and explain a small program; Git knowledge is not assumed.
